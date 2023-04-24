package mx.gob.autofin.client;

import mx.gob.autofin.model.CRSyncKeycloakModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "api-keycloak")
public interface ApiKeycloakClient {

    @GetMapping("/nafin/v1/keycloak/synckeycloakldap")
    public CRSyncKeycloakModel syncLdapKeycloak();
}
