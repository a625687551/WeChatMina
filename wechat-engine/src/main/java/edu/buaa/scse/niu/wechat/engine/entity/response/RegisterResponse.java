package edu.buaa.scse.niu.wechat.engine.entity.response;

import java.util.Set;

import edu.buaa.scse.niu.wechat.engine.entity.Account;

public class RegisterResponse {

	public enum ErrorCode {
		Null_name, Duplicate_name, Duplicate_phone, Duplicate_email, Time_out, Db_exception, Unknown, Too_Much_Try
	};

	private boolean succ;
	private Account account;
	private Set<ErrorCode> errors;

	public RegisterResponse(Account account) {
		succ = true;
		errors = null;
		this.account = account;
	}

	public RegisterResponse(Set<ErrorCode> errors) {
		succ = false;
		account = null;
		this.errors = errors;
	}

	@Override
	public String toString() {
		if (succ) {
			return "RegisterResponse [succ=" + succ + ", account=" + account
					+ "]";
		} else {
			return "RegisterResponse [succ=" + succ + ", errors=" + errors
					+ "]";
		}
	}

	public boolean isSucc() {
		return succ;
	}

	public void setSucc(boolean succ) {
		this.succ = succ;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Set<ErrorCode> getErrors() {
		return errors;
	}

	public void setErrors(Set<ErrorCode> errors) {
		this.errors = errors;
	}

}
