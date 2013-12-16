package edu.buaa.scse.niu.wechat.common.utils;

public class StringUtils {
	/**
	 * 将给定字符串截取或填补为固定长
	 * @param source	源字符串
	 * @param length	要求返回字符串的长度
	 * @param fillchar	用于填充的字符
	 * @return
	 */
	public static String getFixedLengthString(String source,int length,char fillchar){
		String res=source;
		int addsize=length-source.length();
		if(addsize>0){
			StringBuffer sb=new StringBuffer(source);
			for(int i=0;i<addsize;i++){
				sb.append(fillchar);
			}
			res=sb.toString();
		}else if(addsize<0){
			res=source.substring(0, length);
		}
		return res;
	}
}
