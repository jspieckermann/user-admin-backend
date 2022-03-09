package org.spieckermann.useradminbackend.user;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	@RolesAllowed("user-admin-user")
	public List<User> getUser(@PathVariable Long id) {
		return service.getUser();
	}

}
