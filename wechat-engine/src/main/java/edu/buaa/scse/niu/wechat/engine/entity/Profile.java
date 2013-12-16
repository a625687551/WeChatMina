package edu.buaa.scse.niu.wechat.engine.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;

/**
 * 用户基本信息实体类
 * @author Niu
 *
 */
@Entity
@Table(name = Profile.TABLE_NAME)
public class Profile extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String TABLE_NAME="USER_PROFILE";
	
	public static final String ID="ID";
	public static final String EMAIL="EMAIL";
	public static final String PHONE="PHONE";
	public static final String NICKNAME="NICKNAME";
	public static final String ICON="ICON";
	public static final String SIGNATURE="SIGNATURE";
	public static final String MAC="MAC";
	
	public static final int INVALID_ID=-1;
	
	/**
	 * 用户Id
	 */
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = ID, updatable = false)
	private int id;
	
	@Email
	@Size(min = 6, max = 90)
	@Column(name = EMAIL, length = 90, unique = true)
	private String email;
	
	@Size(min = 8, max = 15)
	@Column(name = PHONE, length = 15, unique = true)
	private String phone;

	/**
	 * 昵称，显示名
	 */
	@Size(min=3,max=40)
	@Column(name=NICKNAME,length=40)
	private String nickName;
	
	/**
	 * 用户头像文件地址
	 */
	@Column(name=ICON,length=100)
	private String icon;
	
	/**
	 * 用户签名
	 */
	@Column(name=SIGNATURE,length=60)
	private String signature;
	
	/**
	 * 用户Mac地址
	 */
	@Column(name=MAC,length=20)
	private String mac;
	
	public Profile() {
	}

	public Profile(String email, String phone) {
		this();
		this.email = email;
		this.phone = phone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

}
