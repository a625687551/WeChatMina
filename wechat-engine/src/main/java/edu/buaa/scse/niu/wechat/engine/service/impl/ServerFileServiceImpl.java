package edu.buaa.scse.niu.wechat.engine.service.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import edu.buaa.scse.niu.wechat.engine.service.FileService;

@Service
public class ServerFileServiceImpl implements FileService {

	private static final String VOICE_SUFFIX = ".amr";
	private static final String VOICE_PREFIX = "VOC_";
	private static final String IMAGE_SUFFIX = ".jpg";
	private static final String IMAGE_PREFIX = "IMG_";

	private String baseFileDir = "";
	private static final String IMAGE_DIR_NAME = "Image";
	private static final String VOICE_DIR_NAME = "Voice";

	// 20130722-140801
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
	private SimpleDateFormat sdfDay = new SimpleDateFormat("yyyyMMdd");

	private Random random = new Random();
	private DecimalFormat dfRandom = new DecimalFormat("000");
	private DecimalFormat dfId = new DecimalFormat("00000000");

	public static void main(String[] args) {
		
		FileService service = new ServerFileServiceImpl();
		for (int i = 0; i < 10; i++) {
			System.out.println(service.getVoiceName(true, 12345, 20000));
			System.out.println(service.getImageName(true, 12345, 20000));
		}
		System.out.println(service.getImageDir(true, 12345, 20000));
		System.out.println(service.getVoiceDir(true, 12345, 20000));
		System.out.println(service.getImagePath(true, 12345, 20000));
		System.out.println(service.getVoicePath(true, 12345, 20000));
	}

	@Override
	public String getVoiceName(boolean single, int srcId, int destId) {
		String res = VOICE_PREFIX + getMiddle(single, srcId, destId)
				+ VOICE_SUFFIX;
		return res;
	}

	@Override
	public String getImageName(boolean single, int srcId, int destId) {
		String res = IMAGE_PREFIX + getMiddle(single, srcId, destId)
				+ IMAGE_SUFFIX;
		return res;
	}

	/**
	 * 20130722-140801-random(0-1000)-52995-12345
	 */
	private String getMiddle(boolean single, int srcId, int destId) {
		Date cur = new Date();
		String time = sdf.format(cur);
		String rand = dfRandom.format(random.nextInt(1000));
		return time + "_" + rand + "_" + dfId.format(srcId) + "_"
				+ dfId.format(destId);
	}

	@Override
	public String getVoiceDir(boolean single, int srcId, int destId) {
		Date cur = new Date();
		String res = baseFileDir + "/" + sdfDay.format(cur) + "/"
				+ VOICE_DIR_NAME;
		return res;
	}

	@Override
	public String getImageDir(boolean single, int srcId, int destId) {
		Date cur = new Date();
		String res = baseFileDir + "/" + sdfDay.format(cur) + "/"
				+ IMAGE_DIR_NAME;
		return res;
	}

	@Override
	public String getBaseFileDir() {
		return baseFileDir;
	}

	@Value("#{propertiesReader[engine.basefiledir]}")
	public void setBaseFileDir(String baseFileDir) {
		this.baseFileDir = baseFileDir;
	}

	@Override
	public String getVoicePath(boolean single, int srcId, int destId) {
		String dir=getVoiceDir(single, srcId, destId);
		String name=getVoiceName(single, srcId, destId);
		return dir+"/"+name;
	}

	@Override
	public String getImagePath(boolean single, int srcId, int destId) {
		String dir=getImageDir(single, srcId, destId);
		String name=getImageName(single, srcId, destId);
		return dir+"/"+name;
	}

}
