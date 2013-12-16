package edu.buaa.scse.niu.wechat.engine.entity.msg;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import edu.buaa.scse.niu.wechat.engine.entity.Account;



/**
 * 群聊离线消息
 * 
 * @author Niu
 * 
 */
@Entity
@Table(name = OfflineGroupMessage.TABLE_NAME)
public class OfflineGroupMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME = "OFFLINE_GROUP_MSG";
	public static final String DEST_USER_ID = "DEST_USER_ID";
	public static final String MESSAGE_ID = "MESSAGE_ID";
	public static final String ID = "ID";

	/**
	 * 消息Id 32byte
	 */
	@Id
	@GenericGenerator(name = "uuidGenerator", strategy = "uuid")
	@GeneratedValue(generator = "uuidGenerator")
	private String id;

	/**
	 * 目的用户 
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = DEST_USER_ID, nullable = false, updatable = false)
	private Account destUser;

	/**
	 * 群发消息实体
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = MESSAGE_ID, nullable = false, updatable = false)
	private GroupMessage message;

	public OfflineGroupMessage() {

	}
	
	public OfflineGroupMessage(Account destUser, GroupMessage message) {
		super();
		this.destUser = destUser;
		this.message = message;
	}

	public Account getDestUser() {
		return destUser;
	}

	public void setDestUser(Account destUser) {
		this.destUser = destUser;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public GroupMessage getMessage() {
		return message;
	}

	public void setMessage(GroupMessage message) {
		this.message = message;
	}
}
