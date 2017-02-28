package com.shadow.biz.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.icfcc.batch.ui.PreConditionCheck;
import com.shadow.biz.excepton.ReportBaseException;

/**
 * @author guanliming
 *
 */
public class ReportCheckUtils {

	private static String PROJECTHOME = ReportCheckUtils.class.getResource("/").getPath();
	private static Logger logger = LoggerFactory.getLogger(ReportCheckUtils.class);

	/**
	 * 检查报文名称
	 * 
	 * @param f
	 *            报文文件
	 */
	public static void checkFileName(File f) {
		String fileName = f.getName();
		if (StringUtils.isNotBlank(fileName)) {
			String result = PreConditionCheck.checkfilename(getNameWithoutExt(fileName));
			if (StringUtils.isNotBlank(result)) {
				throw new ReportBaseException(result);
			} else {
				// success
			}
		} else {
			throw new ReportBaseException("文件名为空");
		}
	}

	private static String getNameWithoutExt(String name) {
		String[] fileNames = name.split("\\.");
		if (fileNames.length >= 1) {
			return fileNames[0];
		} else {
			throw new ReportBaseException("文件名:" + name + "格式不正确");
		}
	}

	/**
	 * 检查报文头
	 * 
	 * @param f
	 *            报文文件
	 */
	public static void checkHead(File f) {
		String result = new PreConditionCheck(PROJECTHOME).checkHead(f.getAbsolutePath());
		if (StringUtils.isNotBlank(result)) {
			throw new ReportBaseException(result);
		}
	}

	/**
	 * 检查报文体
	 * 
	 * @param f
	 */
	public static void checkBody(File f) {
		PreConditionCheck prc = new PreConditionCheck(PROJECTHOME);
		String result = prc.checkHead(f.getAbsolutePath());
		if (StringUtils.isNotBlank(result)) {
			throw new ReportBaseException(result);
		} else {
			prc.checkbody();
		}
	}

	/**
	 * 检查报文头
	 * 
	 * @param f
	 * @param preCheck
	 */
	public static void checkHead(File f, PreConditionCheck preCheck) {
		String result = preCheck.checkHead(f.getAbsolutePath());
		if (StringUtils.isNotBlank(result)) {
			throw new ReportBaseException(result);
		}
	}

	/**
	 * 检查报文体
	 * 
	 * @param f
	 * @param preCheck
	 */
	public static void checkBody(File f, PreConditionCheck preCheck) {
		preCheck.checkbody();
	}

	public static void checkRecords(List<String> records, PreConditionCheck preCheck) throws Exception {
		if (!CollectionUtils.isEmpty(records)) {
			ArrayList<byte[]> list = new ArrayList<byte[]>();
			for (int i = 0; i < records.size(); i++) {
				list.add(records.get(i).getBytes("gbk"));
			}
			List<String> result = preCheck.checkrecord(list);
			for (int i = 0; i < result.size(); i++) {
				System.out.println("checkrecordpack=" + i + ":" + result.get(i));
			}
		} else {
			// empty record
		}
	}

	public static void compressFile(File f, PreConditionCheck preCheck, String baseDir) {
		String successDir = FileUtils.mkdirIfNotExist(baseDir, "success_encrypt_file");
		String failDir = FileUtils.mkdirIfNotExist(baseDir, "fail_file");
		String tempDir = FileUtils.mkdirIfNotExist(baseDir, "temp");

		String compressDir = FileUtils.mkdirIfNotExist(tempDir, "compress");
		String decryptDir = FileUtils.mkdirIfNotExist(tempDir, "decrypt");

		String compressFile = compressDir + File.separator + getNameWithoutExt(f.getName()) + ".tmp";
		String succFile = successDir + File.separator + getNameWithoutExt(f.getName()) + ".enc";
		if (preCheck.compressFile(f.getAbsolutePath(), compressFile)) {
			logger.info(compressFile + "压缩成功");
		} else {
			throw new ReportBaseException(compressFile + "压缩失败");
		}
		if (preCheck.cryptMsg(compressFile, succFile)) {
			logger.info(succFile + "加密成功");
		} else {
			throw new ReportBaseException(succFile + "加密失败");
		}
	}


	/**
	 * @param f
	 *            解密文件
	 * @param preCheck
	 * @param baseDir
	 *            基目录
	 */
	public static void decryptCompressFile(File f, PreConditionCheck preCheck, String baseDir) {
		String successDir = FileUtils.mkdirIfNotExist(baseDir, "success_decompress_file");
		// String failDir = mkdirIfNotExist(baseDir, "fail_file");
		String tempDir = FileUtils.mkdirIfNotExist(baseDir, "temp");
		String decryptDir = FileUtils.mkdirIfNotExist(tempDir, "decrypt");

		String decryptFile = decryptDir + File.separator + getNameWithoutExt(f.getName()) + ".dec";
		String succFile = successDir + File.separator + getNameWithoutExt(f.getName()) + ".txt";
		if (preCheck.decryptMsg(f.getAbsolutePath(), decryptFile)) {
			logger.info(decryptFile + "解密成功");
		} else {
			throw new ReportBaseException(decryptFile + "解密失败");
		}
		// 解压缩
		if (preCheck.deCompressFile(decryptFile, succFile)) {
			logger.info(succFile + "解压缩成功");
		} else {
			throw new ReportBaseException(succFile + "解压缩失败");
		}
	}

}
