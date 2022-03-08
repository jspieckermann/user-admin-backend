package org.spieckermann.useradminbackend.user;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	public List<User> getUser() {
		return Arrays.asList(new User("user1", "user1@mail.de"), new User("user2", "user2@mail.de"));
	}

}
