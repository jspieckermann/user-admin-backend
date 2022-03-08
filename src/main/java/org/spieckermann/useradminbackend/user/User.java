package org.spieckermann.useradminbackend.user;

public class User {
	
	private String name;
	private String email;
	
	public User() { }
	
	public User(String name, String email) {
		setName(name);
		setEmail(email);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	

}
