package edu.buaa.scse.niu.wechat.engine.entity.factory;

import java.util.Date;

import edu.buaa.scse.niu.wechat.engine.entity.ChatMessageType.MessageType;
import edu.buaa.scse.niu.wechat.engine.entity.msg.msgdata.ImageMsgData;
import edu.buaa.scse.niu.wechat.engine.entity.msg.msgdata.MsgData;
import edu.buaa.scse.niu.wechat.engine.entity.msg.msgdata.VoiceMsgData;

public class MsgDataFactory {

	/**
	 * 
	 * @param data
	 * @return
	 */
	public static MsgData newText(String data) {
		return new MsgData(MessageType.TEXT, data, 0, null, 0);
	}

	/**
	 * 
	 * @param path
	 * @param dataBytes
	 * @param length
	 * @return
	 */
	public static VoiceMsgData newVoice(String path, byte[] dataBytes, int length) {
		VoiceMsgData res=new VoiceMsgData(path, dataBytes, length);
		return res;
	}

	public static ImageMsgData newImage(String path, String filename,
			byte[] dataBytes) {
		ImageMsgData res = new ImageMsgData(path, filename, dataBytes);
		return res;
	}
	
	/**
	 * //文本消息：
			//	1消息类型 --4内容长度（文本长度）--内容
			//图片、语音、视频等文件消息：
			//	1消息类型--4内容长度（文件大小）--内容--文件名--声音、视频时长（对于图片则为0）
	 * @return
	 */
	public static MsgData rececveText(Date createTime,String data){
		MsgData res=new MsgData(MessageType.TEXT, createTime, data, 0, "", 0);
		return res;
	}
	
	public static VoiceMsgData receiveVoice(Date createTime,byte[] dataBytes, int length){
		VoiceMsgData res=new VoiceMsgData(createTime, "", dataBytes, length);
		return res;
	}
	
	public static ImageMsgData receiveImage(Date createTime,String filename,byte[] dataBytes){
		ImageMsgData res=new ImageMsgData(createTime, "", filename, dataBytes);
		return res;
	}
}
