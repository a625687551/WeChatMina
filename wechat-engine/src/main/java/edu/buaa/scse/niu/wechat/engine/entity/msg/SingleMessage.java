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
import edu.buaa.scse.niu.wechat.engine.entity.msg.msgdata.MsgData;



/**
 * 私聊消息（两人之间）
 * @author niu
 *
 */
@Entity
@Table(name=SingleMessage.TABLE_NAME)
public class SingleMessage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME="SINGLE_MESSAGE";
	public static final String ID = "ID";
	public static final String DEST_USER_ID = "DEST_USER_ID";
	public static final String SRC_USER_ID = "SRC_USER_ID";
	
	/**
	 * 头部长度：36位id+4位srcUserId+4位destUserId=44
	 */
	public static final int HEADER_LENGTH=44;
	public static final String NULL_ID="NULL_ID";
	
	/**
	 * 消息Id 36bytes UUID String
	 */
	@Id
	@GeneratedValue(generator = "idGenerator")    
	@GenericGenerator(name = "idGenerator", strategy = "assigned")  
	@Column(name = ID, updatable = false,length=36)
	private String id;
	
	@Embedded
	private MsgData message;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = DEST_USER_ID, nullable = false, updatable = false)
	private Account destUser;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = SRC_USER_ID, nullable = false, updatable = false)
	private Account srcUser;
	
	public SingleMessage() {
		this.id=UUIDGenerator.getNext();
	}

	public SingleMessage(MsgData message, Account destUser, Account srcUser) {
		this();
		this.message = message;
		this.destUser = destUser;
		this.srcUser = srcUser;
	}
	
	public SingleMessage(String id, MsgData message, Account destUser,
			Account srcUser) {
		this(message, destUser, srcUser);
		this.id = id;
	}

	@Override
	public String toString() {
		return "SingleMessage [id=" + id + ", message=" + message
				+ ", destUser=" + destUser + ", srcUser=" + srcUser + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof SingleMessage){
			return id.equals(((SingleMessage)obj).getId());
		}
		return false;
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

	public Account getDestUser() {
		return destUser;
	}

	public void setDestUser(Account destUser) {
		this.destUser = destUser;
	}

	public Account getSrcUser() {
		return srcUser;
	}

	public void setSrcUser(Account srcUser) {
		this.srcUser = srcUser;
	}
	
}
