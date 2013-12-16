package edu.buaa.scse.niu.wechat.engine.entity;

import java.io.IOException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import edu.buaa.scse.niu.wechat.common.utils.JacksonMapper;
import edu.buaa.scse.niu.wechat.engine.entity.factory.MsgDataFactory;
import edu.buaa.scse.niu.wechat.engine.entity.msg.SingleMessage;
import edu.buaa.scse.niu.wechat.engine.entity.msg.msgdata.MsgData;

public class JSONtest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Account u=new Account("niutianfang", "11111", "abc");
		u.setId(1);
		Profile p=new Profile("niutianfang@126.com", "15810766570");
		p.setNickName("kevin");
		p.setSignature("code monkey");
		u.setProfile(p);
		
		Object uo=u;
		
		MsgData data=MsgDataFactory.newText("abcdefg");
		SingleMessage msg=new SingleMessage(data, u, u);
		
		ObjectMapper mapper=JacksonMapper.getMapperInstance();
		try {
			byte[] btUser=mapper.writeValueAsBytes(uo);
			Account u1=mapper.readValue(btUser, Account.class);
			byte[] btMsg=mapper.writeValueAsBytes(msg);
			String str_user=mapper.writeValueAsString(uo);
			String str_msg=mapper.writeValueAsString(msg);
			
			System.out.println(str_user);
			System.out.println(str_msg);
			
			System.out.println(u1);
			
			SingleMessage msg1=mapper.readValue(btMsg, SingleMessage.class);
			System.out.println(msg1);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
}
