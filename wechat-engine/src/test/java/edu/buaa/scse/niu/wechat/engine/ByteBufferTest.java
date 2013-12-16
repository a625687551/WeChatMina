package edu.buaa.scse.niu.wechat.engine;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import org.apache.mina.core.buffer.IoBuffer;

public class ByteBufferTest {

	private CharsetEncoder encoder;
	private CharsetDecoder decoder;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ByteBufferTest test=new ByteBufferTest();
		
		TestData data=new TestData();
		data.init();
		System.out.println(data.toString());
		byte[] buffer=test.initBuffer(data);
		TestData ret=test.readBuffer(buffer);
		System.out.println(ret.toString());
		
	}
	public ByteBufferTest(){
		Charset charset=Charset.forName("UTF-8");
		encoder=charset.newEncoder();
		decoder=charset.newDecoder();
	}
	
	private byte[] initBuffer(TestData data){
		
		
		IoBuffer buffer=IoBuffer.allocate(10);
		buffer.setAutoExpand(true);
		buffer.put(data.bt);
		
		try {
			buffer.putString(data.str, 5, encoder);
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}
		buffer.putInt(data.num);
		buffer.putInt(data.data.length);
		buffer.put(data.data);
		return buffer.array();
	}
	
	private TestData readBuffer(byte[] bts){
		TestData data=new TestData();
		
		IoBuffer buffer=IoBuffer.wrap(bts);
		
		data.bt=buffer.get();
		try {
			data.str=buffer.getString(5, decoder);
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}
		data.num=buffer.getInt();
		int num=buffer.getInt();
		data.data=new byte[num];
		buffer.get(data.data);
		return data;
	}
	
}
