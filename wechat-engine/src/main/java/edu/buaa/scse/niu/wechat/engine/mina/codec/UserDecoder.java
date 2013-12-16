package edu.buaa.scse.niu.wechat.engine.mina.codec;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;

import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.entity.Profile;


public class UserDecoder extends JsonDecoder<Account> {

	public UserDecoder(){
		super();
	}
	public UserDecoder(Charset charset) {
		super(charset);
	}

	@Override
	public Account decode(byte[] data) {
		Account ret=null;
		IoBuffer buffer=IoBuffer.wrap(data);
		// AccountId--ProfileId--Name--NickName--Phone--Email--Signature--Icon
		int id=buffer.getInt();
		int pid=buffer.getInt();
		try {
			String name=buffer.getPrefixedString(1, decoder);
			String nickname=buffer.getPrefixedString(1, decoder);
			String phone=buffer.getPrefixedString(1, decoder);
			String email=buffer.getPrefixedString(1, decoder);
			String signature=buffer.getPrefixedString(1, decoder);
			String icon=buffer.getPrefixedString(1, decoder);
			
			ret=new Account();
			ret.setId(id);
			ret.setName(name);
			
			Profile p=new Profile();
			p.setId(pid);
			p.setNickName(nickname);
			p.setPhone(phone);
			p.setIcon(icon);
			p.setEmail(email);
			p.setSignature(signature);
			ret.setProfile(p);
			
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}
		return ret;
	}

}
