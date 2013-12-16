package edu.buaa.scse.niu.wechat.engine.entity.msg;

import java.util.List;

import edu.buaa.scse.niu.wechat.engine.entity.Account;

/**
 * 组播结果实体类
 * @author Niu
 *
 */
public class GroupCastResult {

	private GroupMessage msg;
	private List<Account> offlineUsers;

	public GroupCastResult(GroupMessage msg, List<Account> offlineUsers) {
		super();
		this.msg = msg;
		this.offlineUsers = offlineUsers;
	}

	public GroupMessage getMsg() {
		return msg;
	}

	public void setMsg(GroupMessage msg) {
		this.msg = msg;
	}

	public List<Account> getOfflineUsers() {
		return offlineUsers;
	}

	public void setOfflineUsers(List<Account> offlineUsers) {
		this.offlineUsers = offlineUsers;
	}

}
