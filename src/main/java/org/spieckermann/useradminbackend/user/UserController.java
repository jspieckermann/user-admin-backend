package org.spieckermann.useradminbackend.user;

import java.util.List;

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
	public List<User> getUser() {
		return service.getUser();
	}
	
	@GetMapping("/user/{id}")
	public User getUser(@PathVariable String id) {
		return service.getUser(id);
	}
	
	@PutMapping("/user/{id}")
	public void getUser(@PathVariable String id, @RequestBody User user) {
		service.updateUser(id, user);
	}
	
	@DeleteMapping("/user/{id}")
	public void deleteUser(@PathVariable String id) {
		service.deleteUser(id);
	}
	
//	@GetMapping("/user/{id}")
//	@RolesAllowed("user-admin-user")
//	public List<User> getUser(@PathVariable Long id) {
//		return service.getUser();
//	}
	
	@PostMapping("/user")
	public void addUser(@RequestBody User user) {
		service.addUser(user);
	}

}
