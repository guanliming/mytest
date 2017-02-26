package com.shadow.biz.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Scanner;

import com.shadow.biz.excepton.ReportBaseException;

/**
 * @author guanliming
 *
 */
public class FileUtils {

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
			throw new ReportBaseException(baseDir+"不是目录或者没有写操作");
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
