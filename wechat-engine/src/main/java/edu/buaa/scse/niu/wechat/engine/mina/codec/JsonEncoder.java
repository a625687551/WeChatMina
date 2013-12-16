package edu.buaa.scse.niu.wechat.engine.mina.codec;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import edu.buaa.scse.niu.wechat.common.utils.JacksonMapper;

public abstract class JsonEncoder<T> implements Encoder<T>{
	
	protected CharsetEncoder encoder;

	/**
	 * 设置encoder为系统默认charset的encoder
	 */
	public JsonEncoder(){
		encoder=Charset.defaultCharset().newEncoder();
	}
	
	public JsonEncoder(Charset charset) {
		encoder=charset.newEncoder();
	}

	@Override
	public byte[] encodeByJSON(T data) {
		byte[] res = null; 
		try {
			res=JacksonMapper.getMapperInstance().writeValueAsBytes(data);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public abstract void fixNullField(T data);
}
