package edu.buaa.scse.niu.wechat.engine.mina.codec;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;

import edu.buaa.scse.niu.wechat.engine.entity.request.LoginRequest;

public class LoginRequestDecoder extends JsonDecoder<LoginRequest> {

	public LoginRequestDecoder() {
		super();
	}

	public LoginRequestDecoder(Charset charset) {
		super(charset);
	}

	@Override
	public LoginRequest decode(byte[] data) {
		LoginRequest ret = null;
		IoBuffer buffer = IoBuffer.wrap(data);
		try {
			String name = buffer.getPrefixedString(1, decoder);
			String pwd = buffer.getPrefixedString(1, decoder);
			String verification = buffer.getPrefixedString(1, decoder);
			ret = new LoginRequest(name, pwd, verification);
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}
		return ret;
	}

}
