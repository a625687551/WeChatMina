package edu.buaa.scse.niu.wechat.engine.mina.codec;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.entity.Profile;
import edu.buaa.scse.niu.wechat.engine.entity.request.RegisterRequest;
import edu.buaa.scse.niu.wechat.engine.entity.response.RegisterResponse;
import edu.buaa.scse.niu.wechat.engine.entity.response.RegisterResponse.ErrorCode;

public class RegisterCodecTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRequest() {
		RegisterRequestDecoder reqDecoder=CodecFactory.getRegisterRequestDecoder();
		RegisterRequestEncoder reqEncoder=CodecFactory.getRegisterRequestEncoder();
		
		RegisterRequest req=new RegisterRequest("name", "123456", "", null);
		byte[] bts=reqEncoder.encode(req);
		RegisterRequest req1=reqDecoder.decode(bts);
		
		System.out.println(req1.toString());
		
		assertEquals(req1.getPhone(), "");
		assertNotEquals(req1.getPhone(), null);
	}
	
	@Test
	public void testResponse(){
		RegisterResponseDecoder resDecoder=CodecFactory.getRegisterResponseDecoder();
		RegisterResponseEncoder resEncoder=CodecFactory.getRegisterResponseEncoder();
		
		Account account=new Account("niu", "123", "salt");
		account.setId(10);
		Profile profile=new Profile("niu@126.com", "12345");
		profile.setId(9);
		account.setProfile(profile);
		
		ErrorCode error1=ErrorCode.Duplicate_name;
		ErrorCode error2=ErrorCode.Duplicate_email;
		Set<ErrorCode> errors=new HashSet<ErrorCode>();
		errors.add(error1);
		errors.add(error2);
		
		RegisterResponse res = new RegisterResponse(account);
		byte[] bts = resEncoder.encode(res);

		RegisterResponse ret = resDecoder.decode(bts);
		
		System.out.println("res:"+res);
		System.out.println("ret:"+ret);
		assertEquals(res, ret);
		
		
		res=new RegisterResponse(errors);
		bts = resEncoder.encode(res);
		ret = resDecoder.decode(bts);
		
		System.out.println("res:"+res);
		System.out.println("ret:"+ret);
		assertEquals(res, ret);
	}

}
