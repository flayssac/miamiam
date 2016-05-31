package corp.airbus.helicopters.miamiam.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.sqli.commons.core.config.configuration.ReloadablePlaceholderConfig;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The Class SwaggerConfig.
 */
@Profile("swagger")
@Configuration
@EnableWebMvc
@EnableSwagger2
@PropertySource("classpath:swagger.properties")
public class SwaggerConfig extends WebMvcConfigurerAdapter {

	/** The property configurator. */
	@Autowired
	private ReloadablePlaceholderConfig propertyConfigurator;

	/** The app version. */
	private String appVersion = "[DevMode]";
	private String swaggerRootPathVersion = "v1";

	/**
	 * Api info.
	 *
	 * @return the api info
	 */
	private ApiInfo apiInfo() {
		String version = propertyConfigurator.getProperties().getProperty("application.version");
		if (version != null && !"${project.version}".equals(version)) {
			appVersion = version;
			swaggerRootPathVersion = "v" + version;
		}

		return new ApiInfo("miamiam API", "Description", appVersion, null, "dguerin@sqli.com", null, null);
	}

	/**
	 * Swagger spring mvc plugin.
	 *
	 * @return the docket
	 */
	@Bean
	public Docket swaggerSpringMvcPlugin() {

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).pathMapping("/ws/" + swaggerRootPathVersion).select().build();
	}

}
