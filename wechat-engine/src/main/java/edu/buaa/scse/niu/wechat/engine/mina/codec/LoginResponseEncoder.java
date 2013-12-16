package edu.buaa.scse.niu.wechat.engine.mina.codec;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;

import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.entity.response.LoginResponse;
import edu.buaa.scse.niu.wechat.engine.entity.response.LoginResponse.ErrorCode;

public class LoginResponseEncoder extends JsonEncoder<LoginResponse> {

	private UserEncoder userEncoder;

	public LoginResponseEncoder() {
		super();
		userEncoder = new UserEncoder(encoder.charset());
	}

	public LoginResponseEncoder(Charset charset) {
		super(charset);
		userEncoder = new UserEncoder(charset);
	}

	@Override
	public byte[] encode(LoginResponse data) {
		fixNullField(data);
		
		IoBuffer buffer = IoBuffer.allocate(100, false);
		buffer.setAutoExpand(true);

		byte[] body;
		if (data.isSucc()) {
			buffer.put(CodecConsts.TRUE);
			body = userEncoder.encode(data.getUser());
			buffer.put(body);
		} else {
			buffer.put(CodecConsts.FALSE);
			buffer.putEnum(data.getError());
		}
		buffer.flip();
		byte[] ret = new byte[buffer.limit()];
		buffer.get(ret);
		return ret;
	}

	@Override
	public void fixNullField(LoginResponse data) {
		if(data.isSucc()){
			if(data.getUser()==null){
				data.setUser(new Account());
			}
		}else{
			if(data.getError()==null){
				data.setError(ErrorCode.Unknown);
			}
		}
	}

}
