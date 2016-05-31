package corp.airbus.helicopters.miamiam.facade.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sqli.commons.core.config.configuration.ReloadablePlaceholderConfig;

import io.swagger.annotations.Api;

/**
 * The Class ConfigurationController.
 */
@Controller
@RequestMapping("/regular-expressions")
@Api(value = "RegularExpression")
public class RegularExpressionController {

	/** The configuration file. */
	@Autowired
	private ReloadablePlaceholderConfig configurationFile;

	/**
	 * Gets the.
	 *
	 * @return the properties
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public Properties get() throws IOException {
		Properties prop = new Properties();
		Resource resource = getConfigFrontResource();
		if (resource != null) {
			prop.load(resource.getInputStream());
		}
		return prop;
	}

	/**
	 * Gets the config front resource.
	 *
	 * @return the config front resource
	 */
	private Resource getConfigFrontResource() {
		List<Resource> resources = new ArrayList<Resource>(configurationFile.getResources());
		Collections.reverse(resources);
		for (Resource resource : resources) {
			if ("regular-expression.properties".equals(resource.getFilename())) {
				return resource;
			}
		}

		return null;
	}
}
