package edu.buaa.scse.niu.wechat.engine.mina.codec;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;

import edu.buaa.scse.niu.wechat.common.utils.ByteUtils;
import edu.buaa.scse.niu.wechat.common.utils.ByteUtilsException;
import edu.buaa.scse.niu.wechat.engine.entity.msg.msgdata.FileMsgData;
import edu.buaa.scse.niu.wechat.engine.entity.msg.msgdata.MsgData;

public class MsgDataEncoder extends JsonEncoder<MsgData>{

	public MsgDataEncoder(){
		super();
	}
	
	public MsgDataEncoder(Charset charset) {
		super(charset);
	}

	@Override
	public byte[] encode(MsgData body) {
		
		fixNullField(body);
		
		byte[] content;
		int fileNameLength=0;
		if (body instanceof FileMsgData) {
			content = ((FileMsgData) body).getDataBytes();
			fileNameLength=((FileMsgData) body).getFilename().length();
		} else {
			content = body.getData().getBytes();
		}
		int length = content.length;
		
		IoBuffer buffer = IoBuffer.allocate(MsgData.HEADER_LENGTH + length+fileNameLength,
				false);
		buffer.setAutoExpand(true);

		try {
			//文本消息：
			//	1消息类型 --发送时间--4内容长度（文本长度）--内容
			//图片、语音、视频等文件消息：
			//	1消息类型--发送时间--4内容长度（文件大小）--内容--文件名--声音、视频时长（对于图片则为0）
			buffer.putEnum(body.getMsgType());
			//创建时间
			byte[] time = ByteUtils.dateToBytes(body.getCreateTime());
			buffer.put(time);
			
			buffer.putInt(content.length);
			buffer.put(content);
			if(body instanceof FileMsgData){
				//文件名
				buffer.putPrefixedString(body.getFilename(), 2, encoder);
				//声音、视频时长（对于图片则为0）
				buffer.putInt(body.getLength());
			}
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		} catch (ByteUtilsException e) {
			e.printStackTrace();
		}
		buffer.flip();
		byte[] ret = new byte[buffer.limit()];
		buffer.get(ret);
		return ret;
	}

	@Override
	public void fixNullField(MsgData data) {
		if(data.getData()==null){
			data.setData("");
		}
	}

}
