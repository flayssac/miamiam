package corp.airbus.helicopters.miamiam.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * The Class SwaggerUIConfig.
 */
@Profile("swagger-ui")
@Configuration
@EnableWebMvc
public class SwaggerUIConfig extends WebMvcConfigurerAdapter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
	 * #addResourceHandlers(org.springframework.web.servlet.config.annotation.
	 * ResourceHandlerRegistry)
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/api-docs").addResourceLocations("classpath:/swagger-ui/");
		registry.addResourceHandler("/api-docs/**").addResourceLocations("classpath:/swagger-ui/");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
	 * #addViewControllers(org.springframework.web.servlet.config.annotation.
	 * ViewControllerRegistry)
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/api-docs").setViewName("redirect:/api-docs/index.html");
		registry.addViewController("/api-docs/").setViewName("redirect:index.html");
	}

}