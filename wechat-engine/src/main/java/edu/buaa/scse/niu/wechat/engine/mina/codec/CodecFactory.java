package edu.buaa.scse.niu.wechat.engine.mina.codec;

import java.nio.charset.Charset;

public class CodecFactory {

	private static Charset charset = Charset.defaultCharset();

	private static RegisterRequestEncoder registerRequestEncoder;
	private static RegisterRequestDecoder registerRequestDecoder;
	private static RegisterResponseEncoder registerResponseEncoder;
	private static RegisterResponseDecoder registerResponseDecoder;
	private static LoginRequestDecoder loginRequestDecoder;
	private static LoginRequestEncoder loginRequestEncoder;
	private static LoginResponseDecoder loginResponseDecoder;
	private static LoginResponseEncoder loginResponseEncoder;
	private static SingleMessageDecoder singleMessageDecoder;
	private static SingleMessageEncoder singleMessageEncoder;
	private static GroupMessageEncoder groupMessageEncoder;
	private static GroupMessageDecoder groupMessageDecoder;
	private static RespondMsgDecoder respondMsgDecoder;
	private static RespondMegEncoder respondMegEncoder;

	public static Charset getCharset() {
		return charset;
	}

	/**
	 * 
	 * @param charset
	 */
	public static void setCharset(Charset charset) {
		if (!charset.equals(CodecFactory.charset)) {
			loginRequestDecoder = null;
			loginRequestEncoder = null;
			loginResponseDecoder = null;
			loginResponseEncoder = null;
			singleMessageDecoder = null;
			singleMessageEncoder = null;
			groupMessageDecoder=null;
			groupMessageEncoder=null;
			respondMegEncoder=null;
			respondMsgDecoder=null;
		}
		CodecFactory.charset = charset;
	}

	public static LoginRequestDecoder getLoginRequestDecoder() {
		if (loginRequestDecoder == null) {
			loginRequestDecoder = new LoginRequestDecoder(charset);
		}
		return loginRequestDecoder;
	}

	public static void setLoginRequestDecoder(
			LoginRequestDecoder loginRequestDecoder) {
		CodecFactory.loginRequestDecoder = loginRequestDecoder;
	}

	public static LoginRequestEncoder getLoginRequestEncoder() {
		if (loginRequestEncoder == null) {
			loginRequestEncoder = new LoginRequestEncoder(charset);
		}
		return loginRequestEncoder;
	}

	public static void setLoginRequestEncoder(
			LoginRequestEncoder loginRequestEncoder) {
		CodecFactory.loginRequestEncoder = loginRequestEncoder;
	}

	public static LoginResponseDecoder getLoginResponseDecoder() {
		if (loginResponseDecoder == null) {
			loginResponseDecoder = new LoginResponseDecoder(charset);
		}
		return loginResponseDecoder;
	}

	public static void setLoginResponseDecoder(
			LoginResponseDecoder loginResponseDecoder) {
		CodecFactory.loginResponseDecoder = loginResponseDecoder;
	}

	public static LoginResponseEncoder getLoginResponseEncoder() {
		if (loginResponseEncoder == null) {
			loginResponseEncoder = new LoginResponseEncoder(charset);
		}
		return loginResponseEncoder;
	}

	public static void setLoginResponseEncoder(
			LoginResponseEncoder loginResponseEncoder) {
		CodecFactory.loginResponseEncoder = loginResponseEncoder;
	}

	public static SingleMessageEncoder getSingleMessageEncoder() {
		if (singleMessageEncoder == null) {
			singleMessageEncoder = new SingleMessageEncoder(charset);
		}
		return singleMessageEncoder;
	}

	public static void setSingleMessageEncoder(
			SingleMessageEncoder singleMessageEncoder) {
		CodecFactory.singleMessageEncoder = singleMessageEncoder;
	}

	public static SingleMessageDecoder getSingleMessageDecoder() {
		if (singleMessageDecoder == null) {
			singleMessageDecoder = new SingleMessageDecoder(charset);
		}
		return singleMessageDecoder;
	}

	public static void setSingleMessageDecoder(
			SingleMessageDecoder singleMessageDecoder) {
		CodecFactory.singleMessageDecoder = singleMessageDecoder;
	}

	public static RespondMsgDecoder getRespondMsgDecoder() {
		if (respondMsgDecoder == null) {
			respondMsgDecoder = new RespondMsgDecoder(charset);
		}
		return respondMsgDecoder;
	}

	public static void setRespondMsgDecoder(RespondMsgDecoder respondMsgDecoder) {
		CodecFactory.respondMsgDecoder = respondMsgDecoder;
	}

	public static RespondMegEncoder getRespondMegEncoder() {
		if (respondMegEncoder == null) {
			respondMegEncoder = new RespondMegEncoder(charset);
		}
		return respondMegEncoder;
	}

	public static void setRespondMegEncoder(RespondMegEncoder respondMegEncoder) {
		CodecFactory.respondMegEncoder = respondMegEncoder;
	}

	public static GroupMessageEncoder getGroupMessageEncoder() {
		if (groupMessageEncoder == null) {
			groupMessageEncoder = new GroupMessageEncoder(charset);
		}
		return groupMessageEncoder;
	}

	public static void setGroupMessageEncoder(
			GroupMessageEncoder groupMessageEncoder) {
		CodecFactory.groupMessageEncoder = groupMessageEncoder;
	}

	public static GroupMessageDecoder getGroupMessageDecoder() {
		if (groupMessageDecoder == null) {
			groupMessageDecoder = new GroupMessageDecoder(charset);
		}
		return groupMessageDecoder;
	}

	public static void setGroupMessageDecoder(
			GroupMessageDecoder groupMessageDecoder) {
		CodecFactory.groupMessageDecoder = groupMessageDecoder;
	}

	public static RegisterResponseDecoder getRegisterResponseDecoder() {
		if(registerResponseDecoder==null){
			registerResponseDecoder=new RegisterResponseDecoder(charset);
		}
		return registerResponseDecoder;
	}

	public static void setRegisterResponseDecoder(
			RegisterResponseDecoder registerResponseDecoder) {
		CodecFactory.registerResponseDecoder = registerResponseDecoder;
	}

	public static RegisterRequestEncoder getRegisterRequestEncoder() {
		if(registerRequestEncoder==null){
			registerRequestEncoder=new RegisterRequestEncoder(charset);
		}
		return registerRequestEncoder;
	}

	public static void setRegisterRequestEncoder(
			RegisterRequestEncoder registerRequestEncoder) {
		CodecFactory.registerRequestEncoder = registerRequestEncoder;
	}

	public static RegisterRequestDecoder getRegisterRequestDecoder() {
		if(registerRequestDecoder==null){
			registerRequestDecoder=new RegisterRequestDecoder(charset);
		}
		return registerRequestDecoder;
	}

	public static void setRegisterRequestDecoder(
			RegisterRequestDecoder registerRequestDecoder) {
		CodecFactory.registerRequestDecoder = registerRequestDecoder;
	}

	public static RegisterResponseEncoder getRegisterResponseEncoder() {
		if(registerResponseEncoder==null){
			registerResponseEncoder=new RegisterResponseEncoder(charset);
		}
		return registerResponseEncoder;
	}

	public static void setRegisterResponseEncoder(
			RegisterResponseEncoder registerResponseEncoder) {
		CodecFactory.registerResponseEncoder = registerResponseEncoder;
	}
}
