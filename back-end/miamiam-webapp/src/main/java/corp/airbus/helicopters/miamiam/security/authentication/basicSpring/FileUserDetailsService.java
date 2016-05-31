package corp.airbus.helicopters.miamiam.security.authentication.basicSpring;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import corp.airbus.helicopters.miamiam.security.Right;
import corp.airbus.helicopters.miamiam.security.Role;

/**
 * The Class FileUserDetailsService.
 */
public class FileUserDetailsService implements UserDetailsService {

	/** The project name. */
	private String projectName;

	/** The configuration folder variable env. */
	private String configurationFolderVariableEnv;

	/** The file. */
	private String file;

	private Properties users;

	private void loadUsers() throws IOException {
		if (users == null) {
			users = new Properties();
			Resource classPathResource = new ClassPathResource(file);
			if (classPathResource.exists()) {
				users.load(classPathResource.getInputStream());
			}

			if (configurationFolderVariableEnv != null) {
				String confFolder = System.getenv(configurationFolderVariableEnv);
				if (confFolder != null) {
					Resource fileSystemResource = new FileSystemResource(confFolder + File.separator + projectName + File.separator + file);
					if (fileSystemResource.exists()) {
						users.load(fileSystemResource.getInputStream());
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetailsService#
	 * loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			loadUsers();
		} catch (IOException e) {
			throw new UsernameNotFoundException("User not found", e);
		}

		User user = null;
		String userData = (String) users.get(username);
		if (userData != null) {
			// #username=password,grantedAuthority[,grantedAuthority][,enabled|disabled]
			String[] data = userData.split(",");
			if (data.length >= 3) {
				String password = data[0].trim();
				List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
				for (int i = 1; i < data.length - 1; i++) {
					String roleOrRight = data[i].trim();
					if (Right.getByName(roleOrRight) != null) {
						roles.add(new SimpleGrantedAuthority("ROLE_" + roleOrRight));
					}
					if (Role.getByName(roleOrRight) != null) {
						for (Right right : Role.valueOf(roleOrRight).getRights()) {
							roles.add(new SimpleGrantedAuthority("ROLE_" + right.name()));
						}
					}
				}
				if ("enabled".equals(data[data.length - 1].trim())) {
					user = new User(username, password, roles);
				}
			}
		}

		return user;
	}

	/**
	 * Sets the project name.
	 *
	 * @param projectName
	 *            the new project name
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * Sets the configuration folder variable env.
	 *
	 * @param configurationFolderVariableEnv
	 *            the new configuration folder variable env
	 */
	public void setConfigurationFolderVariableEnv(String configurationFolderVariableEnv) {
		this.configurationFolderVariableEnv = configurationFolderVariableEnv;
	}

	/**
	 * Sets the file.
	 *
	 * @param file
	 *            the new file
	 */
	public void setFile(String file) {
		this.file = file;
	}

}
