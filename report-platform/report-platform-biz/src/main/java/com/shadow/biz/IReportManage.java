package com.shadow.biz;

/**
 * @author guanliming
 *
 */
public interface IReportManage {

	/**
	 * @param targetDir
	 * @param targetFile TODO
	 * @return
	 */
	String validateAndClassifyFile(String targetDir, String targetFile);

	String decryptAndDecompress(String targetDir, String targetFile);
}
