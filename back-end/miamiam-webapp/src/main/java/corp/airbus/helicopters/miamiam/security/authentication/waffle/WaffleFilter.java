package corp.airbus.helicopters.miamiam.security.authentication.waffle;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import waffle.spring.NegotiateSecurityFilter;
import corp.airbus.helicopters.miamiam.security.Right;
import corp.airbus.helicopters.miamiam.security.authentication.AuthenticationDetail;

/**
 * The Class SecurityFilter.
 */
public class WaffleFilter extends NegotiateSecurityFilter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * waffle.spring.NegotiateSecurityFilter#setAuthentication(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * org.springframework.security.core.Authentication)
	 */
	@Override
	protected boolean setAuthentication(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(),
				authentication.getAuthorities());
		authenticationToken.setDetails(buildDetails(request, authentication.getName()));
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		return true;
	}

	/**
	 * Builds the details.
	 *
	 * @param context
	 *            the context
	 * @param login
	 *            the login
	 * @return the authentication detail
	 */
	public AuthenticationDetail buildDetails(final HttpServletRequest context, String login) {
		final List<Right> rights = new LinkedList<Right>();

		for (Right code : Right.values()) {
			if (context.isUserInRole(code.name())) {
				rights.add(Right.valueOf(code.name()));
			}
		}

		AuthenticationDetail auth = new AuthenticationDetail(rights);
		auth.setLogin(login);

		return auth;
	}
}
