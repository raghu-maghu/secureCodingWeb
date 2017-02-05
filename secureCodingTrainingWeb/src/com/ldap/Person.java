package com.ldap;

/**
 *  com.ldap.Person
 *
 *  @author raghu maghu
 */
public class Person {
    
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	private String name;
    private String address;    
    private String password;

	// other setter and getter
}

