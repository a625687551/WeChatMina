package edu.buaa.scse.niu.wechat.engine.entity.msg.msgdata;

import java.util.Date;

import edu.buaa.scse.niu.wechat.engine.entity.ChatMessageType.MessageType;

public class VoiceMsgData extends FileMsgData {

	public VoiceMsgData(String path, byte[] dataBytes, int length) {
		super(MessageType.VOICE, path, "", dataBytes, length);
	}
	
	public VoiceMsgData(Date createTime,String path, byte[] dataBytes, int length){
		super(MessageType.VOICE, createTime, path, "", dataBytes, length);
	}

}
