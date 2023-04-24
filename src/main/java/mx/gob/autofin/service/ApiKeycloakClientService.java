package mx.gob.autofin.service;

import mx.gob.autofin.client.ApiKeycloakClient;
import mx.gob.autofin.model.CRSyncKeycloakModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiKeycloakClientService implements ApiKeycloakClient {

    @Autowired
    private ApiKeycloakClient apiKeycloakClient;

    @Override
    public CRSyncKeycloakModel syncLdapKeycloak() {
        return apiKeycloakClient.syncLdapKeycloak();
    }

}
