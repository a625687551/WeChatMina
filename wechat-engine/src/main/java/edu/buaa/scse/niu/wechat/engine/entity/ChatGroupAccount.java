package edu.buaa.scse.niu.wechat.engine.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * 用户帐户和聊天组的连接表对应的实体类
 * @author Niu
 *
 */
@Entity
@Table(name=ChatGroupAccount.TABLE_NAME)
public class ChatGroupAccount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME = "CHAT_GROUP_ACCOUNT"; 
	public static final String ACCOUNT_ID = "ACCOUNT_ID";
	public static final String CHATGROUP_ID = "CHATGROUP_ID";
	public static final String CREATE_TIME = "CREATE_TIME";

	@Embeddable
	public static class Id implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Column(name = ACCOUNT_ID)
		private int accountId;

		@Column(name = CHATGROUP_ID)
		private int chatGroupId;

		public Id() {
		}

		public Id(int accountId, int chatGroupId) {
			super();
			this.accountId = accountId;
			this.chatGroupId = chatGroupId;
		}

		@Override
		public boolean equals(Object obj) {
			if(obj!=null && obj instanceof Id){
				Id other=(Id) obj;
				return this.accountId==other.accountId && this.chatGroupId==other.chatGroupId;
			}else{
				return false;
			}
		}

		public int getAccountId() {
			return accountId;
		}

		public void setAccountId(int accountId) {
			this.accountId = accountId;
		}

		public int getChatGroupId() {
			return chatGroupId;
		}

		public void setChatGroupId(int chatGroupId) {
			this.chatGroupId = chatGroupId;
		}
		
	}

	@EmbeddedId
	private Id id;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = CHATGROUP_ID, insertable = false, updatable = false)
	private ChatGroup chatGroup;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = ACCOUNT_ID, insertable = false, updatable = false)
	private Account account;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@Column(name = CREATE_TIME)
	private Date createTime;

	public ChatGroupAccount(ChatGroup chatGroup, Account account) {
		this();
		this.id=new Id(account.getId(), chatGroup.getId());
		this.chatGroup = chatGroup;
		this.account = account;
	}

	public ChatGroupAccount(){
		this.createTime=new Date();
	}

	public Id getId() {
		return id;
	}

	public void setId(Id id) {
		this.id = id;
	}

	public ChatGroup getChatGroup() {
		return chatGroup;
	}

	public void setChatGroup(ChatGroup chatGroup) {
		this.chatGroup = chatGroup;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
