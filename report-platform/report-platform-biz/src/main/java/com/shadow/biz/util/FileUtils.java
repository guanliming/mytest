package com.shadow.biz.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shadow.biz.excepton.ReportBaseException;

/**
 * @author guanliming
 *
 */
public class FileUtils {
	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

	/**
	 * 如果待创建的文件夹dirName不存在，在目标文件下baseDir下创建dirName文件夹
	 * 
	 * @param baseDir
	 *            目标文件下
	 * @param dirName
	 *            待创建的文件夹
	 * @return
	 */
	public static String mkdirIfNotExist(String baseDir, String dirName) {
		File baseFile = new File(baseDir);
		if (baseFile.isDirectory() && baseFile.canWrite()) {
			String destName = baseDir + File.separator + dirName;
			File tempFile = new File(destName);
			if (!tempFile.exists()) {
				tempFile.mkdir();
				return destName;
			} else {
				return destName;
			}
		} else {
			throw new ReportBaseException(baseDir + "不是目录或者没有写操作");
		}
	}

	/**
	 * 从文件中读取所有的行
	 * 
	 * @param file
	 * @return
	 */
	public static List<String> readContent(File file) {
		List<String> records = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "gbk"));
			try {
				String line = null;
				while ((line = reader.readLine()) != null) {
					records.add(line);
				}
				return records;
			} catch (Exception e) {
				logger.error("读取文件记录失败,e:{}", e);
				try {
					reader.close();
				} catch (Exception e2) {
					logger.error("关闭BufferedReader失败,e:{}", e2);
				}
				throw e;
			} finally {
				try {
					reader.close();
				} catch (Exception e) {
					logger.error("close reader failed");
				}
			}
		} catch (Exception e) {
			logger.error("创建FileReader失败");
			throw new ReportBaseException("创建FileReader失败", e);
		}
	}

	public static String read(File srcFile) throws FileNotFoundException {
		Scanner in = new Scanner(srcFile);
		String result = "";
		while (in.hasNextLine()) {
			result += in.nextLine() + "\r\n";
		}
		in.close();
		return result;
	}

	public static void write(String result, String toFile) throws Exception {
		Writer w = new FileWriter(new File(toFile));
		w.write(result);
		w.flush();
		w.close();
	}

}
