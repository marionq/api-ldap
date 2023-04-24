package mx.gob.autofin.controller;

import mx.gob.autofin.model.CRSyncKeycloakModel;
import mx.gob.autofin.service.ApiKeycloakClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autofin/v1/ldap/synckeycloakldap")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class SyncLdapKeycloakController {

    @Autowired
    ApiKeycloakClientService apiKeycloakClientService;
    
    @GetMapping(produces = "application/json")
    public CRSyncKeycloakModel findRealRoleByRoleName() {
        return apiKeycloakClientService.syncLdapKeycloak();
    }

}
