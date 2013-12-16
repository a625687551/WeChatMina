package edu.buaa.scse.niu.wechat.engine.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import edu.buaa.scse.niu.wechat.engine.entity.msg.SingleMessage;



/**
 * 用户关系实体类 （好友）
 * 
 * @author NIU
 * 
 */
@Entity
@Table(name=UserRelationship.TABLE_NAME)
public class UserRelationship extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME = "USER_RELATIONSHIP";

	public static final String ID = "ID";
	public static final String GROUP = "GROUP_ID";
	public static final String ACCOUNT_ID = "ACCOUNT_ID";
	public static final String NOTE_NAME = "NOTE_NAME";
	public static final String FRIEND_ACCOUNT_ID = "FRIEND_ACCOUNT_ID";
	public static final String LAST_MSG_ID = "LAST_MSG_ID";
	/**
	 * F：好友关系表[Msg_UserRelationShip]： id： 主键做唯一标识（做排序、快速查询等用）
	 * 
	 * groupID: 隶属哪个组（好友、同学、陌生人等）
	 * 
	 * friendUserId: 好友ID (这个字段用户信息表ID对应)
	 * 
	 * UserId: 用户ID (表示是谁的好友)
	 * 
	 * status 好友关系状态，10：启用 20：黑名单30：删除等
	 * 
	 * createDate 好友绑定时间
	 */
	@Id
	@GenericGenerator(name = "uuidGenerator", strategy = "uuid")
	@GeneratedValue(generator = "uuidGenerator")
	@Column(name = ID, updatable = false)
	private String id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = GROUP)
	private UserGroup group;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = FRIEND_ACCOUNT_ID, nullable = false, updatable = false)
	private Account friendAccount;
	
	@Size(min = 1, max = 40)
	@Column(name=NOTE_NAME,length=40)
	private String noteName;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = ACCOUNT_ID, nullable = false, updatable = false)
	private Account account;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = LAST_MSG_ID)
	private SingleMessage lastMessage;

	@Transient
	private boolean isChatting;

	/**
	 * 属于该用户的消息集合
	 */
	@Transient
	private List<SingleMessage> messages;

	/**
	 * 未读消息数
	 */
	@Transient
	private int unreadMsgCount;

	public UserRelationship() {
		this.lastMessage=null;
		this.createTime=new Date();
		this.isChatting=false;
		this.messages=new ArrayList<>();
		this.status=DataStatus.ACTIVE;
	}

	public UserRelationship(UserGroup group, Account friendAccount,
			Account account) {
		this();
		this.group = group;
		this.friendAccount = friendAccount;
		this.account = account;
	}


	public void addMessage(SingleMessage sm) {
		if (messages == null) {
			messages = new ArrayList<SingleMessage>();
		}
		messages.add(sm);
		setLastMessage(sm);
	}

	public int getUnreadMsgCount() {
		return unreadMsgCount;
	}

	public void setUnreadMsgCount(int unreadMsgCount) {
		this.unreadMsgCount = unreadMsgCount;
	}

	public List<SingleMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<SingleMessage> messages) {
		this.messages = messages;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UserGroup getGroup() {
		return group;
	}

	public void setGroup(UserGroup group) {
		this.group = group;
	}

	public boolean isChatting() {
		return isChatting;
	}

	public void setChatting(boolean isChatting) {
		this.isChatting = isChatting;
	}

	public SingleMessage getLastMessage() {
		return lastMessage;
	}

	public void setLastMessage(SingleMessage lastMessage) {
		this.lastMessage = lastMessage;
	}

	public Account getFriendAccount() {
		return friendAccount;
	}

	public void setFriendAccount(Account friendAccount) {
		this.friendAccount = friendAccount;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getNoteName() {
		return noteName;
	}

	public void setNoteName(String noteName) {
		this.noteName = noteName;
	}

}
