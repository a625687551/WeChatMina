package edu.buaa.scse.niu.wechat.common.utils;

import java.util.Calendar;
import java.util.Date;


public class ByteUtils {
	
	public static final String DEFAULT_CHARSET="UTF-8";

	public static Date bytesToDate(byte[] bts) throws ByteUtilsException {
		if (bts.length != 7) {
			throw new ByteUtilsException(
					"bytesToDate: byteArray Length illegal:" + bts.length
							+ " should be 7");
		}
		byte[] y = copyByte(bts, 0, 2);
		int year = BCDToInt(y);
		int month = BCDToInt(bts[2]);
		int day = BCDToInt(bts[3]);
		int hour = BCDToInt(bts[4]);
		int minute = BCDToInt(bts[5]);
		int second = BCDToInt(bts[6]);
		Calendar c = Calendar.getInstance();
		c.set(year, month, day, hour, minute, second);
		return c.getTime();
	}

	public static byte[] dateToBytes(Date date) throws ByteUtilsException {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		byte[] bytes = new byte[7];
		int y = c.get(Calendar.YEAR);
		byte[] year = IntToBCD(y, 2);
		bytes[0] = year[0];
		bytes[1] = year[1];
		bytes[2] = IntToBCD(c.get(Calendar.MONTH));
		bytes[3] = IntToBCD(c.get(Calendar.DAY_OF_MONTH));
		bytes[4] = IntToBCD(c.get(Calendar.HOUR_OF_DAY));
		bytes[5] = IntToBCD(c.get(Calendar.MINUTE));
		bytes[6] = IntToBCD(c.get(Calendar.SECOND));
		return bytes;
	}

	public static String BytesToString(byte[] target) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0, j = target.length; i < j; i++) {
			buf.append((char) target[i]);
		}
		return buf.toString();
	}

	public static byte[] StringToBytes(String str) {
		byte[] buf = new byte[str.length()];
		for (int i = 0; i < str.length(); i++) {
			buf[i] = (byte) str.charAt(i);
		}
		return buf;
	}

	public static byte[] StringToBytes(String str, int length)
			throws ByteUtilsException {
		byte[] buf = new byte[length];
		int minL = Math.min(length, str.length());
		if (minL < str.length()) {
			throw new ByteUtilsException("StringToBytes:" + "string:" + str
					+ " longer than byte array length:" + length);
		}
		for (int i = 0; i < minL; i++) {
			buf[i] = (byte) str.charAt(i);
		}
		return buf;
	}

	/**
	 * BCD编码byte数组转换为对应的int值
	 * 
	 * @param bcd
	 * @return
	 */
	public static int BCDToInt(byte[] bcd) {
		int tmp;
		int dec = 0;
		int length = bcd.length;
		for (int i = length-1; i >=0; i--) {
			tmp = ((bcd[i] >> 4) & 0x0F) * 10 + (bcd[i] & 0x0F);
			dec += tmp * Math.pow(100, i);
		}

		return dec;
	}

	/**
	 * 1byte BCD编码的数转换成int值
	 * @param bcd
	 * @return
	 */
	public static int BCDToInt(byte bcd) {
		int dec = 0;
		// 高位×10+低位
		dec = ((bcd >> 4) & 0x0F) * 10 + (bcd & 0x0F);
		return dec;
	}

	/**
	 * int数值转换为对应BCD编码的byte数组 如果length不足以保存int中数值，则抛出异常
	 * 
	 * @param dec
	 * @param length
	 *            生成的byte数组的长度
	 * @return
	 */
	public static byte[] IntToBCD(int dec, int length)
			throws ByteUtilsException {
		int tmp;
		byte[] bcd = new byte[length];
		for (int i = 0; i <length; i++) {
			tmp = dec % 100;
			bcd[i] = (byte) (((tmp / 10) << 4) + ((tmp % 10) & 0x0F));
			dec /= 100;
		}
		if (dec > 0) {
			throw new ByteUtilsException("IntToBCD: value of int:" + dec
					+ " larger than byte array length:" + length);
		}
		return bcd;
	}

	/**
	 * int转为一位BCD编码byte值
	 * 
	 * @param dec
	 * @return
	 */
	public static byte IntToBCD(int dec) throws ByteUtilsException {
		byte bcd;
		if (dec > 255 || dec < 0) {
			// System.out.println();
			throw new ByteUtilsException("IntToBCD: int=" + dec
					+ "; can't transfrom to 1 byte");
		}

		bcd = (byte) (((dec / 10) << 4) + ((dec % 10) & 0x0F));
		return bcd;
	}

	/**
	 * 连接两个byte数组 返回组合后的byte数组
	 * 
	 * @param a1
	 * @param a2
	 * @return
	 */
	public static byte[] joinBytes(byte[] a1, byte[] a2) {
		byte[] result = new byte[a1.length + a2.length];
		System.arraycopy(a1, 0, result, 0, a1.length);
		System.arraycopy(a2, 0, result, a1.length, a2.length);
		return result;
	}

	// public static Date stringToDate(String str) {
	// return new Date();
	// }
	//
	// public static String dateToString(Date date) {
	// return date.toString();
	// }

	// public static int charsToInt(char[] cs) {
	// String s = String.valueOf(cs);
	// int res = Integer.parseInt(s);
	// return res;
	// }
	//
	// public static Date charsToDate(char[] cs) {
	// Date d = new Date();
	// return d;
	// }

	/**
	 * 复制byte数组
	 * 
	 * @param source
	 *            源byte数组
	 * @param start
	 *            复制起始位置
	 * @param size
	 *            要复制的大小
	 * @return
	 */
	public static byte[] copyByte(byte[] source, int start, int size)
			throws ByteUtilsException {
		if (source.length < start + 1 || source.length < start + size) {
			// 数组越界
			throw new ByteUtilsException("copyByte: source length:"
					+ source.length + " start:" + start + " size:" + size
					+ ". Array out of bound.");
		}
		if(size==0){
			return null;
		}
		byte[] b = new byte[size];
		for (int i = 0; i < size; i++) {
			b[i] = source[start + i];
		}
		return b;
	}

	/**
	 * int（4bytes）转换为byte[4]数组
	 * 
	 * @param num
	 * @return
	 */
	public static byte[] IntegerToBytes(int num) {
		byte[] b = new byte[4];
		// for (int i = 0; i <4; i++) {
		// b[i] = (byte) (num >>> (24 - i * 8));
		// }
		for (int i = 0; i < 4; i++) {
			b[i] = (byte) (num >> 8 * i & 0xFF);
		}
		return b;
	}

	/**
	 * short转换为byte[2]数组
	 * 
	 * @param num
	 * @return
	 */
	public static byte[] ShortToBytes(short num) {
		byte[] b = new byte[2];
		for (int i = 0; i < 2; i++) {
			b[i] = (byte) (num >>> (i * 8));

		}
		return b;
	}

	/**
	 * byte[4]数组转换为Integer
	 * 
	 * @param data
	 * @param offset
	 * @return
	 */
	public static int bytesToInteger(byte[] data, int offset) {
		int num = 0;
		for (int i = offset; i < offset + 4; i++) {
			num <<= 8;
			num |= (data[i] & 0xff);
		}
		return num;
	}

	/**
	 * byte数组中指定位数转换为int值
	 * @param data
	 * @param start
	 * @param end
	 * @return
	 * @throws ByteUtilsException
	 */
	public static int bytesToInteger(byte[] data, int start, int end)
			throws ByteUtilsException {
		if (data.length - 1 < start || data.length - 1 < end) {
			throw new ByteUtilsException(
					"bytesToInteger: ArrayOutOfBound:length=" + data.length
							+ " start:" + start + " end:" + end);
		}
		int num = 0;
		// for (int i = start; i < end; i++) {
		// num <<= 8;
		// num |= (data[i] & 0xff);
		// }

		for (int i = start; i < end; i++) {
			num += (data[i] & 0xFF) << (8 * i);
		}
		return num;
	}

	/**
	 * long类型数值转换为8位byte数组
	 * @param num
	 * @return
	 */
	public static byte[] LongToBytes(long num) {
		byte[] b = new byte[8];
		for (int i = 0; i < 8; i++) {
			b[i] = (byte) (num >>> (56 - i * 8));
		}
		return b;
	}

	/**
	 * 
	 * @param data
	 * @param offset
	 * @return
	 */
	public static long bytesToLong(byte[] data, int offset) {
		long num = 0;
		for (int i = offset; i < offset + 8; i++) {
			num <<= 8;
			num |= (data[i] & 0xff);
		}
		return num;
	}
}
