package com.shadow.biz.util;

import com.mobanker.framework.dto.ResponseEntity;

public class ResponseEntityUtil {
	
	
	public static void setSuccess(ResponseEntity re,Object o){
		re.setData(o);
		re.setError("00000000");
		re.setStatus("1");
	}
	
	public static void setFail(ResponseEntity re,String msg){
		re.setError("00000001");
		re.setStatus("0");
		re.setMsg(msg);
	}

}
