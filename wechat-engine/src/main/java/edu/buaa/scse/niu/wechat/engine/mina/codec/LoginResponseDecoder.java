package edu.buaa.scse.niu.wechat.engine.mina.codec;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;

import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.entity.response.LoginResponse;
import edu.buaa.scse.niu.wechat.engine.entity.response.LoginResponse.ErrorCode;

public class LoginResponseDecoder extends JsonDecoder<LoginResponse> {

	private UserDecoder userDecoder;

	public LoginResponseDecoder() {
		super();
		userDecoder = new UserDecoder(decoder.charset());
	}

	public LoginResponseDecoder(Charset charset) {
		super(charset);
		userDecoder = new UserDecoder(charset);
	}

	@Override
	public LoginResponse decode(byte[] data) {
		LoginResponse ret = null;
		IoBuffer buffer = IoBuffer.wrap(data);
		byte succ = buffer.get();
		if (succ == CodecConsts.TRUE) {
			byte[] btuser = new byte[buffer.remaining()];
			buffer.get(btuser);
			Account user = userDecoder.decode(btuser);
			ret = new LoginResponse(user);
		} else {
			ErrorCode error = buffer.getEnum(ErrorCode.class);
			ret = new LoginResponse(error);
		}
		return ret;
	}

}
