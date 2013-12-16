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
 * 单聊离线消息
 * 
 * @author Niu
 * 
 */
@Entity
@Table(name = OfflineSingleMessage.TABLE_NAME)
public class OfflineSingleMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME = "OFFLINE_SINGLE_MSG";
	public static final String USER_ID = "USER_ID";
	public static final String MESSAGE_ID = "MESSAGE_ID";
	public static final String ID = "ID";

	/**
	 * 消息Id 32byte
	 */
	@Id
	@GenericGenerator(name = "uuidGenerator", strategy = "uuid")
	@GeneratedValue(generator = "uuidGenerator")
	private String id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = USER_ID, nullable = false, updatable = false)
	private Account destUser;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = MESSAGE_ID, nullable = false, updatable = false)
	private SingleMessage message;

	public OfflineSingleMessage() {

	}
	
	public OfflineSingleMessage(SingleMessage message) {
		this.destUser=message.getDestUser();
		this.message = message;
	}

	/**
	 * 通过SingleMessage构建对应的离线消息
	 * @param msg
	 */

	public SingleMessage getMessage() {
		return message;
	}

	public void setMessage(SingleMessage message) {
		this.message = message;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Account getDestUser() {
		return destUser;
	}

	public void setDestUser(Account destUser) {
		this.destUser = destUser;
	}
	
}
