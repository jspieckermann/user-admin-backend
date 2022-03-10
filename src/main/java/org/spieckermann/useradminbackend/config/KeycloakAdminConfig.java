package org.spieckermann.useradminbackend.config;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;

public class KeycloakAdminConfig {

    static Keycloak keycloak = null;
    final static String serverUrl = "http://localhost:8080";
    public final static String realm = "user-admin-app";
    final static String clientId = "user-admin-backend-admin";
    final static String clientSecret = "pC7sCUDEr7F8qy16jkJPhkxCxIN45efC";
    final static String userName = "user1";
    final static String password = "password";

    public KeycloakAdminConfig() {
    }

    public static Keycloak getInstance(){
        if(keycloak == null){
           
            keycloak = KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(realm)
                    .grantType(OAuth2Constants.PASSWORD)
                    .username(userName)
                    .password(password)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .resteasyClient(new ResteasyClientBuilder()
                                   .connectionPoolSize(10)
                                   .build()
                                   )
                    .build();
        }
        return keycloak;
    }
}
