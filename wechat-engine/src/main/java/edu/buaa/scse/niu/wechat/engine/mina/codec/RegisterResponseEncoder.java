package edu.buaa.scse.niu.wechat.engine.mina.codec;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

import org.apache.mina.core.buffer.IoBuffer;

import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.entity.Profile;
import edu.buaa.scse.niu.wechat.engine.entity.response.RegisterResponse;
import edu.buaa.scse.niu.wechat.engine.entity.response.RegisterResponse.ErrorCode;

public class RegisterResponseEncoder extends JsonEncoder<RegisterResponse> {

	private UserEncoder userEncoder;

	public RegisterResponseEncoder() {
		super();
		userEncoder = new UserEncoder(encoder.charset());
	}

	public RegisterResponseEncoder(Charset charset) {
		super(charset);
		userEncoder = new UserEncoder(charset);
	}

	@Override
	public byte[] encode(RegisterResponse data) {
		fixNullField(data);
		IoBuffer buffer = IoBuffer.allocate(100, false);
		buffer.setAutoExpand(true);

		byte[] body;
		if (data.isSucc()) {
			buffer.put(CodecConsts.TRUE);
			body = userEncoder.encode(data.getAccount());
			buffer.put(body);
		} else {
			buffer.put(CodecConsts.FALSE);
			buffer.putEnumSet(data.getErrors());
		}
		buffer.flip();
		byte[] ret = new byte[buffer.limit()];
		buffer.get(ret);
		return ret;
	}

	@Override
	public void fixNullField(RegisterResponse data) {
		// TODO Auto-generated method stub
	}
}
