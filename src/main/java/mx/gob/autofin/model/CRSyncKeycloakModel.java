package mx.gob.autofin.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import mx.gob.autofin.model.SyncKeycloakModel;

@Getter
@Setter
public class CRSyncKeycloakModel {
    
    @JsonProperty("CustomResponse")
    private SyncKeycloakModel customResponse;
    
    private String message;
    private String status;
    
}
