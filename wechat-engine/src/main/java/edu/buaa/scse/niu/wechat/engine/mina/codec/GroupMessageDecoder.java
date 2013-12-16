package edu.buaa.scse.niu.wechat.engine.mina.codec;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;

import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.entity.ChatGroup;
import edu.buaa.scse.niu.wechat.engine.entity.msg.GroupMessage;
import edu.buaa.scse.niu.wechat.engine.entity.msg.msgdata.MsgData;

public class GroupMessageDecoder extends JsonDecoder<GroupMessage> {

	private MsgDataDecoder dataDecoder;

	public GroupMessageDecoder() {
		super();
		dataDecoder = new MsgDataDecoder();
	}

	public GroupMessageDecoder(Charset charset) {
		super(charset);
		dataDecoder = new MsgDataDecoder(charset);
	}

	@Override
	public GroupMessage decode(byte[] data) {
		GroupMessage msg = null;
		IoBuffer buffer = IoBuffer.wrap(data);

		// 消息Id--发送者Id--接收组Id--消息内容
		String id = null;
		try {
			id = buffer.getString(36, decoder);
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}
		Account src = new Account();
		src.setId(buffer.getInt());
		ChatGroup group = new ChatGroup();
		group.setId(buffer.getInt());

		byte[] body = new byte[buffer.remaining()];
		buffer.get(body);
		MsgData msgdata = dataDecoder.decode(body);

		msg = new GroupMessage(id, group, src, msgdata);

		return msg;
	}

}
