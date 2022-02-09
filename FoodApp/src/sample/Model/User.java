package sample.Model;

public class User {
	private String username,pass,email;
	public User (String name,String pass,String email) {
        setUsername(name);
        setPass(pass);
        setEmail(email);
    }
	public String getUsername() {
		return username;	
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
