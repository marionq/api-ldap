package mx.gob.autofin.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.gob.autofin.dto.User;
import mx.gob.autofin.response.ResponseHandler;
import mx.gob.autofin.service.UserRepo;

@RestController
@RequestMapping("/autofin/v1/userldap")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE,
		RequestMethod.OPTIONS })
public class LdapBindController {

	@Autowired
	private UserRepo userRepo;

	@PostMapping(value = "/crear", produces = "application/json")
	public ResponseEntity<Object> bindLdapUser(@RequestBody User User) {
            return userRepo.create(User);
		
	}

	@PutMapping(value = "/actualizar", produces = "application/json")
	public ResponseEntity<Object> rebindLdapUser(@RequestBody User User) {
		return userRepo.update(User);
	}

	@GetMapping(value = "/findall", produces = "application/json")
	public ResponseEntity<Object> retrieve() {
		List<User> findAll = userRepo.retrieve();
		if (findAll == null || findAll.isEmpty()) {
			return ResponseHandler.generateResponse(null, HttpStatus.NO_CONTENT, null);
		} else {
			return ResponseHandler.generateResponse("OK", HttpStatus.OK, findAll);
		}
	}

	@GetMapping(value = "/findbyuid/{uid}", produces = "application/json")
	public ResponseEntity<Object> findByuid(@PathVariable String uid) {
		List<User> findByUid = userRepo.findByUid(uid);

		if (findByUid == null || findByUid.isEmpty()) {
			return ResponseHandler.generateResponse(null, HttpStatus.NO_CONTENT, null);
		} else {
			return ResponseHandler.generateResponse("OK", HttpStatus.OK, findByUid);
		}
	}

	@DeleteMapping(value = "/borrar/{userId}", produces = "application/json")
	public ResponseEntity<Object> unbindLdapUser(@PathVariable String userId) {
		String result = userRepo.remove(userId);
		return ResponseHandler.generateResponse("OK", HttpStatus.OK, result);
	}
}