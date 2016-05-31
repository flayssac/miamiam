package corp.airbus.helicopters.miamiam.facade.rest;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import springfox.documentation.annotations.ApiIgnore;

import com.sqli.commons.core.controller.AbstractController;
import com.sqli.commons.core.service.model.IdentifierDTO;

import corp.airbus.helicopters.miamiam.model.ErrorDTO;

/**
 * The Class ErrorController.
 */
@Controller
@RequestMapping("/errors")
@ApiIgnore
public class ErrorController extends AbstractController<ErrorDTO> {

	/**
	 * Creates the.
	 *
	 * @param error
	 *            the error
	 * @param response
	 *            the response
	 * @return the identifier dto
	 */
	/**
	 * Creates the.
	 *
	 * @param error
	 *            the error
	 * @param response
	 *            the response
	 * @return the identifier dto
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public IdentifierDTO<Long> create(@RequestBody ErrorDTO error, HttpServletResponse response) {
		StringBuilder globalError = new StringBuilder();
		if (error != null) {
			globalError.append(error.getMessage());
			if (error.getStacktrace() != null) {
				for (String line : error.getStacktrace()) {
					globalError.append("\n\t");
					globalError.append(line);
				}
			}
			logger.error(globalError.toString());
		}
		response.setStatus(HttpServletResponse.SC_CREATED);

		return null;
	}
}
