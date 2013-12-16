package edu.buaa.scse.niu.wechat.engine;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;

public class IoBufferTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		CharsetEncoder encoder=Charset.defaultCharset().newEncoder();
		CharsetDecoder decoder=Charset.defaultCharset().newDecoder();
		IoBuffer buffer=IoBuffer.allocate(100);
		try {
			buffer.putPrefixedString("", 1, encoder);
			buffer.flip();
			String ret=buffer.getPrefixedString(1, decoder);
			System.out.println(ret);
			
			byte[] bts=buffer.array();
			
			buffer.putPrefixedString(null, 1, encoder);
			buffer.flip();
			String ret1=buffer.getPrefixedString(1, decoder);
			System.out.println(ret);
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}
	}

}
