# user-admin-backend
App to administrate users based on a central KEYCLOAK server (v.17.0). Case study to learn more about the interaction between KEYCLOAK, ANGULAR and SPRING BOOT security. 

## Preconditions
### Keycloak installation and configuration
* Install Keycloak v17.0
* Add realm '__user-admin-app__'
* Create client '__user-admin-frontend__'
  * Enabled: __On__
  * Client protocol: **openid-connect**
  * Access Type: **public**
  * Standard workflow enabled: **On**
  * Direct Access Grants Enabled: **On**
  * Root URL: **http://localhost:4200**
  * Valid Redirect URIs: **http://localhost:4200/***
  * Admin URL: **http://localhost:4200**
  * Web Origins: **http://localhost:4200**
* Create client '__user-admin-backend__'
  * Enabled: __On__
  * Client protocol: **openid-connect**
  * Access Type: **bearer-only**
  * Add role __user-admin-user__ and __user-admin-admin__
* Create client '__user-admin-backend-admin'
  * Enabled: __On__
  * Client protocol: **openid-connect**
  * Access Type: **confidential**
  * Standard workflow enabled: **On**
  * Direct Access Grants Enabled: **On**
  * Root URL: **http://localhost:9090**
  * Valid Redirect URIs: **http://localhost:9090/***
  * Admin URL: **http://localhost:9090**
  * Web Origins: **http://localhost:9090**
  * Generate __Secret__ in __Credentials tab__
* Create at least one user with all __realm management__, __user-admin-user__ and __user-admin-admin__ permissions
* Used by application: Username: __user2__, Password: __password__
