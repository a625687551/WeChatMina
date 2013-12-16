package edu.buaa.scse.niu.wechat.engine.mina.codec;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;

import edu.buaa.scse.niu.wechat.engine.entity.ChatMessageType.MessageType;
import edu.buaa.scse.niu.wechat.engine.entity.msg.RespondMessage;

public class RespondMegEncoder extends JsonEncoder<RespondMessage> {

	public RespondMegEncoder() {
		super();
	}

	public RespondMegEncoder(Charset charset) {
		super(charset);
	}

	@Override
	public byte[] encode(RespondMessage data) {
		fixNullField(data);

		IoBuffer buffer = IoBuffer.allocate(100, false);
		buffer.setAutoExpand(true);

		try {
			buffer.putString(data.getMsgId(), 36, encoder);
			buffer.putEnum(data.getType());
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}
		buffer.flip();
		byte[] ret = new byte[buffer.limit()];
		buffer.get(ret);
		return ret;
	}

	@Override
	public void fixNullField(RespondMessage data) {
		if(data.getMsgId()==null){
			data.setMsgId("null id");
		}
		if(data.getType()==null){
			data.setType(MessageType.TEXT);
		}
	}

}
