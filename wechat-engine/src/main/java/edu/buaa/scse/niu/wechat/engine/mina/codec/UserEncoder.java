package edu.buaa.scse.niu.wechat.engine.mina.codec;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import org.apache.mina.core.buffer.IoBuffer;

import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.entity.Profile;

public class UserEncoder extends JsonEncoder<Account> {
	public UserEncoder() {
		super();
	}

	public UserEncoder(Charset charset) {
		super(charset);
	}

	@Override
	public byte[] encode(Account data) {
		fixNullField(data);

		IoBuffer buffer = IoBuffer.allocate(100, false);
		buffer.setAutoExpand(true);

		// AccountId--ProfileId--Name--NickName--Phone--Email--Signature--Icon
		buffer.putInt(data.getId());
		Profile profile=data.getProfile();
		buffer.putInt(profile.getId());
		try {
			buffer.putPrefixedString(data.getName(), 1, encoder);
			buffer.putPrefixedString(profile.getNickName(), 1, encoder);
			buffer.putPrefixedString(profile.getPhone(), 1, encoder);
			buffer.putPrefixedString(profile.getEmail(), 1, encoder);
			buffer.putPrefixedString(profile.getSignature(), 1, encoder);
			buffer.putPrefixedString(profile.getIcon(), 1, encoder);
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}
		buffer.flip();
		byte[] ret = new byte[buffer.limit()];
		buffer.get(ret);
		return ret;
	}

	@Override
	public void fixNullField(Account data) {
		Profile profile=data.getProfile();
		if(profile==null){
			profile=new Profile();
		}
		if (profile.getEmail() == null) {
			profile.setEmail("");
		}
		if (data.getName() == null) {
			data.setName("");
		}
		if (profile.getNickName() == null) {
			profile.setNickName("");
		}
		if (profile.getPhone() == null) {
			profile.setPhone("");
		}
		if (profile.getSignature() == null) {
			profile.setSignature("");
		}
		if(profile.getIcon()==null){
			profile.setIcon("");
		}
	}

}
