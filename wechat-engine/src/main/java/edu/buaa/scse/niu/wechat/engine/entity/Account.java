package edu.buaa.scse.niu.wechat.engine.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * 用户帐户实体类 其中profile储存用户信息
 * 
 * @author Niu
 * 
 */
@Entity
@Table(name = Account.TABLE_NAME)
public class Account extends BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String TABLE_NAME = "USER_ACCOUNT";

	public static final String ID = "ID";
	public static final String NAME = "NAME";
	public static final String SALT = "SALT";
	public static final String PASSWORD = "PASSWORD";
	public static final String PROFILE_ID = "PROFILE_ID";
	public static final String LOGIN_TIME = "LOGIN_TIME";
	public static final String CHAT_GROUPS = "CHAT_GROUPS";

	public static final int INVALID_ID = -1;

	/**
	 * 用户Id
	 */
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = ID, updatable = false)
	private int id;

	/**
	 * 加密盐
	 */
	@Column(name = SALT, length = 32, updatable = false)
	private String salt;

	/**
	 * 加密后的密码
	 */
	@NotNull
	@Column(name = PASSWORD, length = 40, nullable = false)
	private String password;

	/**
	 * 登录用的用户名
	 */
	@NotNull
	@Size(min = 3, max = 40)
	@Column(name = NAME, length = 40, nullable = false)
	private String name;


	/**
	 * 用户基本信息
	 */
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = PROFILE_ID, unique = true, nullable = false)
	private Profile profile;

	/**
	 * 上次登录时间
	 */
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@Column(name = LOGIN_TIME)
	private Date loginTime;

	/**
	 * 属于该用户的聊天组集合
	 */
	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<ChatGroupAccount> chatGroupAccounts;

	public Account() {
		createTime = new Date();
		status = DataStatus.INACTIVE;
		chatGroupAccounts = new ArrayList<ChatGroupAccount>();
	}

	/**
	 * 
	 * @param salt
	 * @param password
	 * @param name
	 */
	public Account(String name, String password, String salt) {
		this();
		this.name = name;
		this.password = password;
		this.salt = salt;
	}
	
	/**
	 * 客户端接收到服务端发来的其它用户的信息 不包括密码、加密盐、登录时间、注册时间等
	 * @param id
	 * @param name
	 * @param profile
	 */
	public Account(int id, String name, Profile profile) {
		this.id = id;
		this.name = name;
		this.profile = profile;
		status=DataStatus.ACTIVE;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", salt=" + salt + ", password="
				+ password + ", name=" + name + ", status=" + status
				+ ", createTime=" + createTime
				+ ", loginTime=" + loginTime + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public List<ChatGroupAccount> getChatGroupAccounts() {
		return chatGroupAccounts;
	}

	public void setChatGroupAccounts(List<ChatGroupAccount> chatGroupAccounts) {
		this.chatGroupAccounts = chatGroupAccounts;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
}
