package edu.buaa.scse.niu.wechat.engine.entity.msg;

import edu.buaa.scse.niu.wechat.engine.entity.ChatMessageType.MessageType;



public class RespondMessage {

	private String msgId;
	private MessageType type;

	public RespondMessage(String msgId, MessageType type) {
		super();
		this.msgId = msgId;
		this.type = type;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}
}
