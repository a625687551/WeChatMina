package edu.buaa.scse.niu.wechat.engine.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 用户群组实体类
 * @author NIU
 *
 */
@Entity
@Table(name=UserGroup.TABLE_NAME)
public class UserGroup extends BaseEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String TABLE_NAME="USER_GROUP";
	
	public static final String ID="ID";
	public static final String ACCOUNT_ID="ACCOUNT_ID";
	public static final String GROUP_NAME="GROUP_NAME";
	
	@Id
	@GenericGenerator(name = "uuidGenerator", strategy = "uuid")
	@GeneratedValue(generator = "uuidGenerator")
	@Column(name = ID, updatable = false)
	private String id;
	
	@Column(name=GROUP_NAME,length=40,nullable=false)
	private String groupName;
	
	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name = ACCOUNT_ID, nullable = false, updatable = false)
	private Account account;

	public UserGroup() {
		createTime=new Date();
		status=DataStatus.ACTIVE;
	}

	public UserGroup(String groupName, Account account) {
		this();
		this.groupName = groupName;
		this.account = account;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
