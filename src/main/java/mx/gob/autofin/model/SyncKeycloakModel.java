package mx.gob.autofin.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SyncKeycloakModel {

    private String ignored;
    private String added;
    private String updated;
    private String removed;
    private String failed;
    private String status;

}
