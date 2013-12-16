package edu.buaa.scse.niu.wechat.engine.mina.codec;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import org.apache.mina.core.buffer.IoBuffer;
import edu.buaa.scse.niu.wechat.engine.entity.msg.SingleMessage;
import edu.buaa.scse.niu.wechat.engine.entity.msg.msgdata.MsgData;
import edu.buaa.scse.niu.wechat.engine.entity.Account;

public class SingleMessageDecoder extends JsonDecoder<SingleMessage> {

	private MsgDataDecoder dataDecoder;

	public SingleMessageDecoder() {
		super();
		dataDecoder = new MsgDataDecoder();
	}

	public SingleMessageDecoder(Charset charset) {
		super(charset);
		dataDecoder = new MsgDataDecoder(charset);
	}

	@Override
	public SingleMessage decode(byte[] data) {
		SingleMessage msg = null;
		IoBuffer buffer = IoBuffer.wrap(data);

		String id = null;
		// 消息Id--发送者Id--接收者Id--消息内容
		try {
			id = buffer.getString(36, decoder);
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}
		Account src = new Account();
		src.setId(buffer.getInt());
		Account dest = new Account();
		dest.setId(buffer.getInt());

		byte[] body = new byte[buffer.remaining()];
		buffer.get(body);
		MsgData msgdata = dataDecoder.decode(body);
		msg = new SingleMessage(id, msgdata, dest, src);

		return msg;
	}

}
