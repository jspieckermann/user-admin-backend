package org.spieckermann.useradminbackend.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.spieckermann.useradminbackend.config.KeycloakAdminConfig;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	public List<User> getUser() {
		List<User> result = new ArrayList<User>();
		UsersResource usersResource = KeycloakAdminConfig.getInstance().realm(KeycloakAdminConfig.realm).users();
		for (UserRepresentation user : usersResource.list()) {
			result.add(convert(user));
		}
		return result;
	}

	public void addUser(User user) {
		UsersResource usersResource = KeycloakAdminConfig.getInstance().realm(KeycloakAdminConfig.realm).users();
		CredentialRepresentation credentialRepresentation = createPasswordCredentials(user.getPassword());

		UserRepresentation kcUser = new UserRepresentation();
		kcUser.setUsername(user.getName());
		kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
		kcUser.setFirstName(user.getFirstname());
		kcUser.setLastName(user.getLastname());
		kcUser.setEmail(user.getEmail());
		kcUser.setEnabled(true);
		kcUser.setEmailVerified(false);
		usersResource.create(kcUser);

	}

	private CredentialRepresentation createPasswordCredentials(String password) {
		CredentialRepresentation passwordCredentials = new CredentialRepresentation();
		passwordCredentials.setTemporary(false);
		passwordCredentials.setType(CredentialRepresentation.PASSWORD);
		passwordCredentials.setValue(password);
		return passwordCredentials;
	}
	
	/**
	 * Convert a UserRepresentation to User class.
	 * 
	 * @param userRep The UserRepresentation
	 * @return User
	 */
	private User convert(UserRepresentation userRep) {
		return new User(userRep.getUsername(), userRep.getEmail(), userRep.getFirstName(), userRep.getLastName());
	}

}
