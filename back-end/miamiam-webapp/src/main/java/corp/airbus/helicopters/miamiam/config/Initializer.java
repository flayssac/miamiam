package corp.airbus.helicopters.miamiam.config;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.FilterRegistration;
import javax.servlet.HttpConstraintElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletSecurityElement;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.sqli.commons.core.exception.TechnicalException;

import corp.airbus.helicopters.miamiam.status.StatusServlet;

/**
 * The Class Initializer.
 */
public class Initializer implements WebApplicationInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.WebApplicationInitializer#onStartup(javax.servlet
	 * .ServletContext)
	 */
	@Override
	public void onStartup(ServletContext container) {
		// Getting app version & spring profiles to be used
		Properties properties = new Properties();
		BufferedInputStream input = null;
		try {
			input = new BufferedInputStream(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
			properties.load(input);
			input.close();
		} catch (IOException e) {
			throw new TechnicalException("Impossible to load config.properties");
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				throw new TechnicalException("Impossible to close config.properties");
			}
		}
		String applicationVersion = properties.getProperty("application.version");
		String springProfiles = properties.getProperty("spring.profiles");
		if ("${project.version}".equals(applicationVersion)) {
			applicationVersion = "1";
		}

		// Get spring profiles
		if (System.getProperty("spring.profiles") != null) {
			springProfiles = System.getProperty("spring.profiles");
		}
		if ("${spring.profiles}".equals(springProfiles)) {
			springProfiles = "cors,swagger,swagger-ui,auth-basic-spring";
		}

		// Web app init
		container.setInitParameter("contextConfigLocation", "classpath:spring/servlet-context.xml, classpath:spring/persistence-context.xml, classpath:spring/application-context.xml");
		container.setInitParameter("log4jExposeWebAppRoot", "false");
		container.setInitParameter("spring.profiles.active", springProfiles);

		container.addListener("org.springframework.web.context.ContextLoaderListener");

		XmlWebApplicationContext appContext = new XmlWebApplicationContext();
		appContext.setConfigLocation("classpath:spring/servlet-context.xml");

		ServletRegistration.Dynamic springMVCServlet = container.addServlet("springMVCServlet", new DispatcherServlet(appContext));
		springMVCServlet.setLoadOnStartup(1);
		springMVCServlet.addMapping("/*", "/ws/v" + applicationVersion + "/*");

		ServletRegistration.Dynamic statusServlet = container.addServlet("statusServlet", new StatusServlet());
		statusServlet.setLoadOnStartup(2);
		statusServlet.addMapping("/dynamic/healthcheck.jsp");

		FilterRegistration corsFilter = container.addFilter("corsFilter", "org.springframework.web.filter.DelegatingFilterProxy");
		corsFilter.addMappingForUrlPatterns(null, false, "/ws/*");

		FilterRegistration httpFilter = container.addFilter("httpFilter", "org.springframework.web.filter.DelegatingFilterProxy");
		httpFilter.addMappingForUrlPatterns(null, false, "/ws/*");

		FilterRegistration expireFilter = container.addFilter("expireFilter", "org.springframework.web.filter.DelegatingFilterProxy");
		expireFilter.addMappingForUrlPatterns(null, false, "/*");

		FilterRegistration springSecurityFilterChain = container.addFilter("springSecurityFilterChain", "org.springframework.web.filter.DelegatingFilterProxy");
		springSecurityFilterChain.addMappingForUrlPatterns(null, false, "/ws/*");

		// Add security element only if auth-basic-server
		if (springProfiles.contains("auth-basic-server")) {
			HttpConstraintElement httpConstraintElement = new HttpConstraintElement(TransportGuarantee.NONE, "ADMIN");
			ServletSecurityElement servletSecurityElement = new ServletSecurityElement(httpConstraintElement);
			springMVCServlet.setServletSecurity(servletSecurityElement);
		}
	}
}
