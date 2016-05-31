package corp.airbus.helicopters.miamiam.status;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.FrameworkServlet;

import com.sqli.commons.core.config.configuration.ReloadablePlaceholderConfig;

import corp.airbus.helicopters.miamiam.dao.BookDao;

/**
 * The Class StatusServlet.
 */
public class StatusServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5710376737401935025L;

	/** The Constant CONTENT_TYPE. */
	private static final String CONTENT_TYPE = "text/html;charset=utf-8";

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(StatusServlet.class);

	/** The TE status dao. */
	private BookDao daoDataSource;

	/** The error initialization. */
	private String errorInitialization;

	private String appVersion = "[DevMode]";

	@Autowired
	private ReloadablePlaceholderConfig propertyConfigurator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(final ServletConfig config) throws ServletException {
		try {

			final WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext(), FrameworkServlet.SERVLET_CONTEXT_PREFIX + "springMVCServlet");

			if (springContext != null) {
				daoDataSource = (BookDao) springContext.getBean("bookDao");

				springContext.getAutowireCapableBeanFactory().autowireBean(this);

				String version = propertyConfigurator.getProperties().getProperty("application.version");
				if (version != null && !"${project.version}".equals(version)) {
					appVersion = version;
				}
			} else {
				errorInitialization = "Initialization failed";
			}
		} catch (final BeansException e) {
			LOGGER.error(e.getMessage(), e);
			errorInitialization = e.getMessage();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

		// header
		StringBuilder header = new StringBuilder("<html>\r<head>\r<title>miamiam - Status page</title>\r")
				.append("<style type=\"text/css\">")
				.append("body {font-family: Arial,sans-serif;font-size: 12px;text-align: center;}")
				.append(".panel {border-color: #dcdcdc;border-width: 1px;padding:10px;border-radius: 7px;border-style: solid;margin: 20 auto;width: 400px;background-image: linear-gradient(#e6e6e6,#fafafa);background-image: -webkit-gradient(linear,50% 0,50% 100%,color-stop(0%,#e6e6e6),color-stop(100%,#fafafa));background-image: -webkit-linear-gradient(#e6e6e6,#fafafa);background-image: -moz-linear-gradient(#e6e6e6,#fafafa);background-image: -o-linear-gradient(#e6e6e6,#fafafa);}")
				.append("h1{font-size:18px}").append(".result{font-weight:bold}").append(".ok{font-size: 24px;color:#8FD600;}").append(".ko{font-size: 14px;color:#FF3C14;}").append("</style>")
				.append("</head><body><h1>miamiam v").append(appVersion).append(" - Status page</h1>");

		// Test context initialization
		final StringBuilder contextBuilder = new StringBuilder();
		contextBuilder.append("<p class=\"panel\">Context initialization: ");
		if (errorInitialization != null && !errorInitialization.isEmpty()) {
			contextBuilder.append("<span class=\"result ko\"> ERROR</span>");
			contextBuilder.append("<br /> <br /><span class=\"result\">Error message:</span><br />");
			contextBuilder.append(errorInitialization);
			contextBuilder.append("<br /></p>");
		} else {
			contextBuilder.append("<span class=\"result ok\"> &#x2713;</span></p>");
		}

		// Test database GCH
		final StringBuilder statusGCHBuilder = new StringBuilder();
		final boolean isStatusDataSourceOk = testDatasource(statusGCHBuilder);

		final String footer = "</body>\r</html>";

		// Building result
		final StringBuilder resultBuilder = new StringBuilder().append(header.toString()).append(contextBuilder.toString()).append(statusGCHBuilder.toString()).append(footer);

		// format response
		response.setContentType(CONTENT_TYPE);
		response.getWriter().write(resultBuilder.toString());

		if (!isStatusDataSourceOk) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Test the database GCH.
	 * 
	 * @param statusGCHBuilder
	 *            the text of status
	 * @return true if the test is ok;otherwise false.
	 */
	private boolean testDatasource(final StringBuilder statusGCHBuilder) {
		boolean isStatusGCHOk = false;
		statusGCHBuilder.append("<p class=\"panel\">Database connexion test: ");
		try {
			if (daoDataSource == null) {
				throw new RuntimeException("Initialization failed");
			}
			daoDataSource.get(1L);

			isStatusGCHOk = true;
			statusGCHBuilder.append("<span class=\"result ok\"> &#x2713;</span></p>");
		} catch (final RuntimeException e2) {
			LOGGER.error(e2.getMessage(), e2);
			statusGCHBuilder.append("<span class=\"result ko\"> ERROR</span>");
			statusGCHBuilder.append("<br /> <br /><span class=\"result\">Error message:</span><br />");
			statusGCHBuilder.append(e2.getMessage());
			statusGCHBuilder.append("<br /></p>");
		}
		return isStatusGCHOk;
	}
}
