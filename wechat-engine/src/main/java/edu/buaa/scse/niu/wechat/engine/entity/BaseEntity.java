package edu.buaa.scse.niu.wechat.engine.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Embeddable
public class BaseEntity {

	public static final String STATUS = "STATUS";
	public static final String CREATE_TIME = "CREATE_TIME";
	public static final String MODIFY_TIME="MODIFY_TIME";
	
	/**
	 * 实体状态 是否被激活
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = STATUS)
	protected DataStatus status;

	/**
	 * 实体创建时间
	 */
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@Column(name = CREATE_TIME)
	protected Date createTime;
	
	/**
	 * 实体修改时间
	 */
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@Column(name = MODIFY_TIME)
	protected Date modifyTime;

	public DataStatus getStatus() {
		return status;
	}

	public void setStatus(DataStatus status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	
}
