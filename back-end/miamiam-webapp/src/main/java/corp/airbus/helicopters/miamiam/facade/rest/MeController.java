package corp.airbus.helicopters.miamiam.facade.rest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import springfox.documentation.annotations.ApiIgnore;
import corp.airbus.helicopters.miamiam.security.Right;
import corp.airbus.helicopters.miamiam.security.UserHasRight;
import corp.airbus.helicopters.miamiam.security.authentication.AuthenticationDetail;

/**
 * The Class MeController.
 */
@Controller
@RequestMapping("/me")
@ApiIgnore
public class MeController {

	/**
	 * Retrieve current user.
	 * 
	 * @return the user dto
	 */
	@UserHasRight(Right.GET_ME)
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public AuthenticationDetail get() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		AuthenticationDetail detail = null;
		if (auth.getDetails() != null) {
			detail = (AuthenticationDetail) auth.getDetails();
		}
		return detail;
	}
}
