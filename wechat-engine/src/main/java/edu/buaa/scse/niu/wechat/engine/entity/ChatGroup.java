package edu.buaa.scse.niu.wechat.engine.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import edu.buaa.scse.niu.wechat.engine.entity.msg.GroupMessage;



@Entity
@Table(name = ChatGroup.TABLE_NAME)
public class ChatGroup extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String TABLE_NAME = "CHAT_GROUP";

	public static final String ID = "ID";
	public static final String NAME = "NAME";


	/**
	 * 聊天组Id
	 */
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = ID, updatable = false)
	private int id;

	@Size(min = 1, max = 40)
	@Column(name = NAME, length = 40, nullable = false)
	private String name;

	/**
	 * 聊天组成员 一对多映射 通过一张第三方表来实现一对多的单向关联 级联刷新、延迟加载
	 */
	@OneToMany(mappedBy="chatGroup",fetch=FetchType.LAZY,orphanRemoval=true)
	private List<ChatGroupAccount> chatGroupAccounts;
	
	@Transient
	private List<GroupMessage> messages;

	public ChatGroup() {
		chatGroupAccounts=new ArrayList<ChatGroupAccount>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<GroupMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<GroupMessage> messages) {
		this.messages = messages;
	}

	public List<ChatGroupAccount> getChatGroupAccounts() {
		return chatGroupAccounts;
	}

	public void setChatGroupAccounts(List<ChatGroupAccount> chatGroupAccounts) {
		this.chatGroupAccounts = chatGroupAccounts;
	}
}
