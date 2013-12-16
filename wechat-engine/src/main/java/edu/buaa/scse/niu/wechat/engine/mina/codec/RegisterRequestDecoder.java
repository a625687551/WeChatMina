package edu.buaa.scse.niu.wechat.engine.mina.codec;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;

import edu.buaa.scse.niu.wechat.engine.entity.request.RegisterRequest;

public class RegisterRequestDecoder extends JsonDecoder<RegisterRequest> {

	public RegisterRequestDecoder() {
		super();
	}

	public RegisterRequestDecoder(Charset charset) {
		super(charset);
	}

	/**
	 * name--password--phone--email
	 */
	@Override
	public RegisterRequest decode(byte[] data) {
		RegisterRequest ret = null;
		IoBuffer buffer = IoBuffer.wrap(data);
		try {
			String name = buffer.getPrefixedString(1, decoder);
			String pwd = buffer.getPrefixedString(1, decoder);
			String phone = buffer.getPrefixedString(1, decoder);
			String email = buffer.getPrefixedString(1, decoder);
			ret = new RegisterRequest(name, pwd, email, phone);
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}
		return ret;
	}

}
