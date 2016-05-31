package corp.airbus.helicopters.miamiam.security.authentication.basicSpring;

import java.util.LinkedList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import corp.airbus.helicopters.miamiam.security.Right;
import corp.airbus.helicopters.miamiam.security.authentication.AuthenticationDetail;

/**
 * The Class BasicAuthenticationProvider.
 */
public class BasicAuthenticationProvider extends DaoAuthenticationProvider {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.authentication.dao.
	 * AbstractUserDetailsAuthenticationProvider
	 * #authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) super.authenticate(authentication);

		authenticationToken.setDetails(buildDetails(authenticationToken));

		return authenticationToken;
	}

	/**
	 * Builds the details.
	 *
	 * @param res
	 *            the res
	 * @return the authentication detail
	 */
	public AuthenticationDetail buildDetails(Authentication res) {
		final List<Right> rights = new LinkedList<Right>();

		for (Right code : Right.values()) {
			for (GrantedAuthority authority : res.getAuthorities()) {
				if (authority.getAuthority().replace("ROLE_", "").equals(code.name())) {
					rights.add(Right.valueOf(code.name()));
				}
			}
		}

		AuthenticationDetail auth = new AuthenticationDetail(rights);
		auth.setLogin(res.getName());

		return auth;
	}
}
