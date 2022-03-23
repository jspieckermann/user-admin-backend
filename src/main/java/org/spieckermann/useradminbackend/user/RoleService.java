package org.spieckermann.useradminbackend.user;

import java.util.Arrays;
import java.util.List;

import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.spieckermann.useradminbackend.config.KeycloakAdminConfig;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

	
	public static final String ROLE_ADMIN = "user-admin-admin";
	public static final String ROLE_USER = "user-admin-user";

	public void updateRolesByUsername(String username, boolean admin) {
		UserRepresentation userRep = KeycloakAdminConfig.getInstance().realm(KeycloakAdminConfig.realm).users().search(username, true).get(0);
		this.updateRoles(userRep.getId(), admin);
	}
	
	public void updateRoles(String userId, boolean admin) {
		RoleRepresentation userRole = getClientRole(ROLE_USER);
		RoleRepresentation adminRole = getClientRole(ROLE_ADMIN);
		if (!hasRole(userId, ROLE_USER)) {
			addRole(userId, userRole);
		}
		if (admin && !hasRole(userId, ROLE_ADMIN)) {
			addRole(userId, adminRole);
		}
		if (!admin && hasRole(userId, ROLE_ADMIN)) {
			removeRole(userId, adminRole);
		}
	}
	
	public boolean hasRole(String userId, String roleName) {
		boolean result = false;
		List<RoleRepresentation> roles = getRoles(userId);
		for (RoleRepresentation role : roles) {
			if (roleName.equalsIgnoreCase(role.getName())) {
				result = true;
			}
		}
		return result;
	}
	
	private void removeRole(String userId, RoleRepresentation role) {
		UserResource userRes = this.getUserResource(userId);
		ClientRepresentation clientRep = this.getClientRepresentation();
		userRes.roles().clientLevel(clientRep.getId()).remove(Arrays.asList(role));
	}
	
	private void addRole(String userId, RoleRepresentation role) {
		UserResource userRes = this.getUserResource(userId);
		ClientRepresentation clientRep = this.getClientRepresentation();
		userRes.roles().clientLevel(clientRep.getId()).add(Arrays.asList(role));
	}
	
	private List<RoleRepresentation> getRoles(String userId) {
		UserResource userResource = this.getUserResource(userId);
		ClientRepresentation clientRep = this.getClientRepresentation();
		return userResource.roles().clientLevel(clientRep.getId()).listAll();
	}

	private List<RoleRepresentation> getClientRoles() {
		ClientRepresentation clientRep = this.getClientRepresentation();
		ClientResource clientRes = KeycloakAdminConfig.getInstance().realm(KeycloakAdminConfig.realm).clients().get(clientRep.getId());
		return clientRes.roles().list();
	}
	
	private RoleRepresentation getClientRole(String roleName) {
		RoleRepresentation result = null;
		List<RoleRepresentation> roles = getClientRoles();
		for (RoleRepresentation role : roles) {
			if (roleName.equalsIgnoreCase(role.getName())) {
				result = role;
			}
		}
		return result;
	}
	
	private ClientRepresentation getClientRepresentation() {
		return KeycloakAdminConfig.getInstance().realm(KeycloakAdminConfig.realm).clients()
				.findByClientId("user-admin-backend").get(0);
	}
	
	private UserResource getUserResource(String userId) {
		return KeycloakAdminConfig.getInstance().realm(KeycloakAdminConfig.realm).users().get(userId);
	}
}
