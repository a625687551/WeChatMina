package edu.buaa.scse.niu.wechat.engine.mina.codec;

import java.nio.charset.Charset;
import java.util.Set;

import org.apache.mina.core.buffer.IoBuffer;

import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.entity.response.RegisterResponse;
import edu.buaa.scse.niu.wechat.engine.entity.response.RegisterResponse.ErrorCode;

public class RegisterResponseDecoder extends JsonDecoder<RegisterResponse> {

	private UserDecoder userDecoder;

	public RegisterResponseDecoder() {
		super();
		userDecoder = new UserDecoder(decoder.charset());
	}

	public RegisterResponseDecoder(Charset charset) {
		super(charset);
		userDecoder = new UserDecoder(charset);
	}

	@Override
	public RegisterResponse decode(byte[] data) {
		RegisterResponse ret = null;

		IoBuffer buffer = IoBuffer.wrap(data);
		byte succ = buffer.get();
		if (succ == CodecConsts.TRUE) {
			byte[] btuser = new byte[buffer.remaining()];
			buffer.get(btuser);
			Account user = userDecoder.decode(btuser);
			ret =new RegisterResponse(user);
		} else {
			Set<ErrorCode> errors = buffer.getEnumSet(ErrorCode.class);
			ret =new RegisterResponse(errors);
		}
		return ret;
	}

}
