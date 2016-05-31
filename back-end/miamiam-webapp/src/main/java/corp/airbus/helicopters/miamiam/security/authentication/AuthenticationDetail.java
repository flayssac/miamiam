package corp.airbus.helicopters.miamiam.security.authentication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import corp.airbus.helicopters.miamiam.security.Right;
import corp.airbus.helicopters.miamiam.security.Role;

/**
 * The details of the authentication used by spring security framework to
 * construct connected user object.
 */
public class AuthenticationDetail implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1330867537414120000L;

	/** The rights. */
	private final List<Right> rights;

	/** The roles. */
	private final List<Role> roles;

	private String login;

	/**
	 * Instantiates a new authentication detail.
	 * 
	 * @param rolesOrRights
	 *            the roles or rights codes of the authenticated user
	 */
	@SuppressWarnings("unchecked")
	public AuthenticationDetail(List<?> rolesOrRights) {
		if (!rolesOrRights.isEmpty()) {
			if (rolesOrRights.get(0) instanceof Role) {
				this.roles = (List<Role>) rolesOrRights;
				this.rights = new ArrayList<Right>();
			} else if (rolesOrRights.get(0) instanceof Right) {
				this.roles = new ArrayList<Role>();
				this.rights = (List<Right>) rolesOrRights;
			} else {
				this.roles = new ArrayList<Role>();
				this.rights = new ArrayList<Right>();
			}
		} else {
			this.roles = new ArrayList<Role>();
			this.rights = new ArrayList<Right>();
		}
	}

	/**
	 * Gets the rights.
	 * 
	 * @return the rights
	 */
	public List<Right> getRights() {
		return rights;
	}

	/**
	 * Gets the roles.
	 * 
	 * @return the roles
	 */
	public List<Role> getRoles() {
		return roles;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

}
