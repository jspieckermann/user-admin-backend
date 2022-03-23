package org.spieckermann.useradminbackend.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.spieckermann.useradminbackend.config.KeycloakAdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private RoleService roleService;

	public List<User> getUser() {
		List<User> result = new ArrayList<User>();
		UsersResource usersResource = KeycloakAdminConfig.getInstance().realm(KeycloakAdminConfig.realm).users();
		for (UserRepresentation user : usersResource.list()) {
			result.add(convert(user));
		}
		return result;
	}
	
	public User getUser(String id) {
		UserRepresentation userRep = KeycloakAdminConfig.getInstance().realm(KeycloakAdminConfig.realm).users().get(id).toRepresentation();
		return convert(userRep);
	}
	
	public void updateUser(String id, User user) {
		UserResource userResource = KeycloakAdminConfig.getInstance().realm(KeycloakAdminConfig.realm).users().get(id);
		UserRepresentation userRep = userResource.toRepresentation();
		userRep.setUsername(user.getName());
		userRep.setFirstName(user.getFirstname());
		userRep.setLastName(user.getLastname());
		userRep.setEmail(user.getEmail());
		if (user.getPassword() != null && user.getPassword().length() > 0) {
			CredentialRepresentation credentialRepresentation = createPasswordCredentials(user.getPassword());
			userRep.setCredentials(Collections.singletonList(credentialRepresentation));
		}
	    userResource.update(userRep);
	    roleService.updateRoles(id, user.isAdministrator());
	}
	
	public void deleteUser(String id) {
		UsersResource usersResource = KeycloakAdminConfig.getInstance().realm(KeycloakAdminConfig.realm).users();
		usersResource.delete(id);
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
		kcUser.setEmailVerified(true);
		usersResource.create(kcUser);
		
		roleService.updateRolesByUsername(kcUser.getUsername(), user.isAdministrator());

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
		boolean admin = roleService.hasRole(userRep.getId(), RoleService.ROLE_ADMIN);
		return new User(userRep.getId(), userRep.getUsername(), userRep.getEmail(), userRep.getFirstName(), userRep.getLastName(), admin);
	}

}
