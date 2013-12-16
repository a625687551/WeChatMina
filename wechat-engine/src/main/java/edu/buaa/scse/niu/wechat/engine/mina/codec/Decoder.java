package edu.buaa.scse.niu.wechat.engine.mina.codec;

public interface Decoder<T> {

	public T decode(byte[] data);
	
	public T decodeByJSON(Class<T> clazz,byte[] data);
}
