package edu.buaa.scse.niu.wechat.engine.entity.request;

/**
 * 注册请求实体类
 * 
 * @author Niu
 * 
 */
public class RegisterRequest {

	private String name;
	private String password;
	private String email;
	private String phone;

	public RegisterRequest(String name, String password, String email,
			String phone) {
		super();
		this.name = name;
		this.password = password;
		this.email = email;
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "RegisterRequest [name=" + name + ", password=" + password
				+ ", email=" + email + ", phone=" + phone + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
