package org.spieckermann.useradminbackend.user;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@GetMapping("/user")
	@RolesAllowed("user-admin-user")
	public List<User> getUser() {
		return service.getUser();
	}
	
	@GetMapping("/user/{id}")
	@RolesAllowed("user-admin-user")
	public User getUser(@PathVariable String id) {
		return service.getUser(id);
	}
	
	@PutMapping("/user/{id}")
	@RolesAllowed("user-admin-admin")
	public void updateUser(@PathVariable String id, @RequestBody User user) {
		service.updateUser(id, user);
	}
	
	@DeleteMapping("/user/{id}")
	@RolesAllowed("user-admin-admin")
	public void deleteUser(@PathVariable String id) {
		service.deleteUser(id);
	}
	
	@PostMapping("/user")
	@RolesAllowed("user-admin-user")
	public void addUser(@RequestBody User user) {
		service.addUser(user);
	}
	
//	@GetMapping("/roles/{id}")
//	public List<String> getRolesByUser(@PathVariable String id) {
//		return service.getRoles(id);
//	}
//	
//	@GetMapping("/roles")
//	public List<String> getRoles() {
//		return service.getRoles();
//	}

}
