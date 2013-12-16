package edu.buaa.scse.niu.wechat.engine.entity.msg;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import edu.buaa.scse.niu.wechat.common.utils.UUIDGenerator;
import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.entity.ChatGroup;
import edu.buaa.scse.niu.wechat.engine.entity.msg.msgdata.MsgData;


/**
 * 群聊消息实体类
 * @author niu
 *
 */
@Entity
@Table(name=GroupMessage.TABLE_NAME)
public class GroupMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME = "GROUP_MESSAGE";
	public static final String ID = "ID";
	public static final String SRC_USER_ID = "SRC_USER_ID";
	public static final String CHAT_GROUP_ID = "CHAT_GROUP_ID";

	/**
	 * 消息Id 36byte uuid String
	 */
	@Id
	@GeneratedValue(generator = "idGenerator")    
	@GenericGenerator(name = "idGenerator", strategy = "assigned")  
	@Column(name = ID, updatable = false,length=36)
	private String id;

	/**
	 * 消息所述聊天组
	 */
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = CHAT_GROUP_ID, nullable = false, updatable = false)
	private ChatGroup group;

	/**
	 * 消息发送者
	 */
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = SRC_USER_ID, nullable = false, updatable = false)
	private Account srcUser;

	/**
	 * 消息内容体
	 */
	@Embedded
	private MsgData message;

	public GroupMessage() {
		id=UUIDGenerator.getNext();
	}

	public GroupMessage(ChatGroup group, Account srcUser, MsgData message) {
		this();
		this.group = group;
		this.srcUser = srcUser;
		this.message = message;
	}
	
	public GroupMessage(String id, ChatGroup group, Account srcUser,
			MsgData message) {
		this(group, srcUser, message);
		this.id = id;
	}

	public ChatGroup getGroup() {
		return group;
	}

	public void setGroup(ChatGroup group) {
		this.group = group;
	}

	public MsgData getMessage() {
		return message;
	}

	public void setMessage(MsgData message) {
		this.message = message;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Account getSrcUser() {
		return srcUser;
	}

	public void setSrcUser(Account srcUser) {
		this.srcUser = srcUser;
	}


}
