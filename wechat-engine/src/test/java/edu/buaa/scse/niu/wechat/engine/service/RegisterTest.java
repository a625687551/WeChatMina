package edu.buaa.scse.niu.wechat.engine.service;

import java.util.Set;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.buaa.scse.niu.wechat.engine.entity.request.RegisterRequest;
import edu.buaa.scse.niu.wechat.engine.entity.response.RegisterResponse;
import edu.buaa.scse.niu.wechat.engine.entity.response.RegisterResponse.ErrorCode;
import edu.buaa.scse.niu.wechat.engine.service.validator.RegisterValidator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/test-context.xml" })
@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false) 
@Transactional 
public class RegisterTest{

	@Autowired
	private RegisterValidator validator;
	
	@Autowired
	private UserService userService;

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void validate1() {
		RegisterRequest request = new RegisterRequest("niu1", "123", "", "");

		Set<ErrorCode> errors = validator.checkUnique(request);
		System.out.println(errors.toString());
		
		assertEquals(0,errors.size());
	}
	
	
	@Test
	public void validate2() {
		RegisterRequest request = new RegisterRequest("name000", "123", "000email@126.com","15810766000");

		Set<ErrorCode> errors = validator.checkUnique(request);
		System.out.println(errors.toString());
		
		if(!errors.contains(ErrorCode.Duplicate_email)){
			fail("should contain errorcode Duplicate_email");
		}
		if(!errors.contains(ErrorCode.Duplicate_phone)){
			fail("should contain errorcode Duplicate_phone");
		}
		if(!errors.contains(ErrorCode.Duplicate_name)){
			fail("should contain errorcode Duplicate_name");
		}
	}
	
	@Test
	public void validate3() {
		RegisterRequest request = new RegisterRequest("", "123", "", "");

		Set<ErrorCode> errors = validator.checkUnique(request);
		System.out.println(errors.toString());
		
		assertEquals(1, errors.size());
	}

	@Rollback(true)
	@Test
	public void register1(){
		RegisterRequest request = new RegisterRequest("niu1", "123", "ntf1@126.com", "12345678901");
		RegisterResponse response=userService.register(request);
		assertTrue(response.isSucc());
	}
	
	@Rollback(true)
	@Test
	public void register2(){
		RegisterRequest request = new RegisterRequest("niu2", "123", "ntf@163.com", "12345678");
		RegisterResponse response=userService.register(request);
		
		RegisterRequest duplicate = new RegisterRequest("niu3", "123", "ntf@163.com", "12345678");
		RegisterResponse duplicate_res=userService.register(duplicate);
		
		System.out.println(duplicate_res.toString());
		assertTrue(!duplicate_res.isSucc());
	}
	
}
