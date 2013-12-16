package edu.buaa.scse.niu.wechat.engine;

import java.util.Arrays;

public class TestData {

	public byte bt;
	public int num;
	public String str;
	public byte[] data;

	public TestData() {

	}

	public void init() {
		bt = 0x02;
		num = 1024;
		str = "test";
		data = "abcdefg".getBytes();
	}

	@Override
	public String toString() {
		return "TestData [bt=" + bt + ", num=" + num + ", str=" + str
				+ ", data=" + Arrays.toString(data) + "]";
	}
}
