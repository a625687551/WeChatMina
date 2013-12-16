package edu.buaa.scse.niu.wechat.engine.mina.codec;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.util.Date;

import org.apache.mina.core.buffer.IoBuffer;

import edu.buaa.scse.niu.wechat.common.utils.ByteUtils;
import edu.buaa.scse.niu.wechat.common.utils.ByteUtilsException;
import edu.buaa.scse.niu.wechat.engine.entity.ChatMessageType.MessageType;
import edu.buaa.scse.niu.wechat.engine.entity.factory.MsgDataFactory;
import edu.buaa.scse.niu.wechat.engine.entity.msg.msgdata.MsgData;

public class MsgDataDecoder extends JsonDecoder<MsgData> {

	public MsgDataDecoder() {
		super();
	}

	public MsgDataDecoder(Charset charset) {
		super(charset);
	}

	@Override
	public MsgData decode(byte[] data) {
		IoBuffer buffer = IoBuffer.wrap(data);

		String text=null;
		MsgData body = null;
		String filename=null;
		int time=0;
		try {
			//文本消息：
			//	1消息类型 --发送时间--4内容长度（文本长度）--内容
			//图片、语音、视频等文件消息：
			//	1消息类型--发送时间--4内容长度（文件大小）--内容--文件名--声音、视频时长（对于图片则为0）
			MessageType type = buffer.getEnum(MessageType.class);
			//发送时间
			byte[] date=new byte[7];
			buffer.get(date, 0, 7);
			Date createTime=new Date();
			try {
				createTime = ByteUtils.bytesToDate(date);
			} catch (ByteUtilsException e) {
				e.printStackTrace();
			}
			
			int size = buffer.getInt();
			byte[] content = new byte[size];
			buffer.get(content);
			if(type==MessageType.VOICE ||type==MessageType.IMAGE || type==MessageType.VIDEO){
				filename=buffer.getPrefixedString(2, decoder);
				time=buffer.getInt();
			}
			switch (type) {
			case TEXT:
				text=new String(content);
				body=MsgDataFactory.rececveText(createTime, text);
				break;
			case IMAGE:
				body=MsgDataFactory.receiveImage(createTime, filename, content);
				break;
			case VIDEO:
				//TODO
				break;
			case VOICE:
				body=MsgDataFactory.receiveVoice(createTime, content, time);
				break;
			case TEXTICON:
				text=new String(content);
				body=MsgDataFactory.rececveText(createTime, text);
				break;
			case HYPERLINK:
				text=new String(content);
				body=MsgDataFactory.rececveText(createTime, text);
				break;
			case LOCATION:
				break;
			}

		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}
		return body;
	}

}
