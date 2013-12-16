package edu.buaa.scse.niu.wechat.engine.entity.msg;

public class SingleCastResult {

	private SingleMessage msg;
	private boolean isOnline;

	public SingleCastResult() {

	}

	public SingleCastResult(SingleMessage msg, boolean isOnline) {
		super();
		this.msg = msg;
		this.isOnline = isOnline;
	}

	public SingleMessage getMsg() {
		return msg;
	}

	public void setMsg(SingleMessage msg) {
		this.msg = msg;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
}
