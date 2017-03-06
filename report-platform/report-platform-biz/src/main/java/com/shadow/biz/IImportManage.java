package com.shadow.biz;

/**
 * @author guanliming
 *
 */
public interface IImportManage {

	boolean importData(String targetFile);
	
	
	boolean handleRowData(String lineData, String fileName);
}
