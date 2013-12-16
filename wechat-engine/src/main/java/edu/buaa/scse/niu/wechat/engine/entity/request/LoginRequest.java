package edu.buaa.scse.niu.wechat.engine.entity.request;

/**
 * 登录请求实体类
 * @author Niu
 *
 */
public class LoginRequest {

	private String userName;
	private String password;
	private String verification;

	public LoginRequest(){
		userName="";
		password="";
		verification="";
	}
	
	public LoginRequest(String userName, String password, String verification) {
		super();
		this.setUserName(userName);
		this.setPassword(password);
		this.setVerification(verification);
	}

	@Override
	public String toString() {
		return "LoginRequest [userName=" + userName + ", password=" + password
				+ ", verification=" + verification + "]";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVerification() {
		return verification;
	}

	public void setVerification(String verification) {
		this.verification = verification;
	}
}
