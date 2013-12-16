package edu.buaa.scse.niu.wechat.engine.repository;

import java.io.Serializable;

/**
 * 用于定义field
 * @author NIU
 *
 */
public class Condition implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String field;

	public String getField() {
		return field;
	}

}