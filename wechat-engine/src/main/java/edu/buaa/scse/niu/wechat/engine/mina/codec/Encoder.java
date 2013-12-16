package edu.buaa.scse.niu.wechat.engine.mina.codec;

public interface Encoder<T> {

	public byte[] encode(T data);
	
	public byte[] encodeByJSON(T data);
}
