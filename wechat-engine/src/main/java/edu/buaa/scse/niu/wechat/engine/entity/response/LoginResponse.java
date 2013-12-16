package edu.buaa.scse.niu.wechat.engine.entity.response;

import edu.buaa.scse.niu.wechat.engine.entity.Account;

public class LoginResponse {

	public enum ErrorCode {
		No_such_user, Wrong_pwd, Wrong_verif, Db_exception, Duplicate_login, Time_out, Interrupted, Unknown, Too_Much_Try
	};

	private boolean succ;
	private Account account;
	private ErrorCode error;

	public LoginResponse() {
		succ = false;
		error = ErrorCode.Unknown;
		account = null;
	}

	public LoginResponse(Account account) {
		super();
		this.succ = true;
		this.account = account;
		this.error = null;
	}

	public LoginResponse(ErrorCode error) {
		super();
		this.account = null;
		this.succ = false;
		this.error = error;
	}

	@Override
	public String toString() {
		return "LoginResponse [succ=" + succ + ", account=" + account
				+ ", error=" + error + "]";
	}

	public boolean isSucc() {
		return succ;
	}

	public void setSucc(boolean succ) {
		this.succ = succ;
	}

	public Account getUser() {
		return account;
	}

	public void setUser(Account user) {
		this.account = user;
	}

	public ErrorCode getError() {
		return error;
	}

	public void setError(ErrorCode error) {
		this.error = error;
	}
}
