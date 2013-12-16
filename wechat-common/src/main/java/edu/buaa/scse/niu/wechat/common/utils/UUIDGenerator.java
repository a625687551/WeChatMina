package edu.buaa.scse.niu.wechat.common.utils;

import java.util.UUID;

public class UUIDGenerator {

	/**
	 * generate 36bytes UUID string with '-'
	 * eg.3d13cba3-32c6-494c-996c-17a23b0d8085
	 * @return
	 */
	public static String getNext(){
		UUID uuid=UUID.randomUUID();
		return uuid.toString();
	}
	
	/**
	 * generate 32bytes UUID string without '-'
	 * eg.3d13cba332c6494c996c17a23b0d8085
	 * @return
	 */
	public static String getNext32(){
		String src=getNext();
		String res=src.replaceAll("-", "");
		return res;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(UUIDGenerator.getNext());
		System.out.println(UUIDGenerator.getNext());
		System.out.println(UUIDGenerator.getNext());
	}
}
