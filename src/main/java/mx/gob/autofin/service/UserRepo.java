package mx.gob.autofin.service;

import java.util.List;

import mx.gob.autofin.dto.User;
import org.springframework.http.ResponseEntity;

public interface UserRepo {

    public List<User> retrieve();

    public List<User> findByUid(String uid);

    public ResponseEntity<Object> create(User p);

    public ResponseEntity<Object> update(User p);

    public String remove(String userId);

}
