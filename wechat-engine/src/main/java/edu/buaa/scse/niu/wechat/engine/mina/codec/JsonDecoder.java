package edu.buaa.scse.niu.wechat.engine.mina.codec;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import edu.buaa.scse.niu.wechat.common.utils.JacksonMapper;

public abstract class JsonDecoder<T> implements Decoder<T> {

	protected CharsetDecoder decoder;

	public JsonDecoder(){
		decoder=Charset.defaultCharset().newDecoder();
	}
	
	public JsonDecoder(Charset charset) {
		decoder = charset.newDecoder();
	}

	@Override
	public T decodeByJSON(Class<T> clazz, byte[] data) {
		T ret = null;
		try {
			ret = (T) JacksonMapper.getMapperInstance().readValue(data, clazz);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
}
