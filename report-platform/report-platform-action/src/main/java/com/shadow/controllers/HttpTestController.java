package com.shadow.controllers;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import lombok.Data;
import lombok.Getter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shadow.biz.IBorrowBiz;

/**
 * @author 管黎明
 *
 *         All rights reserved.
 */
@Controller
@RequestMapping("http")
public class HttpTestController {
	@Getter
	class MyResponse {
		private final Object data;
		private final String status;

		MyResponse(final String status, final Object data) {
			this.status = status;
			this.data = data;
		}
	}
	@Autowired
	private IBorrowBiz borrowBiz;

	@RequestMapping(value = "/a1", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String a1(@RequestBody final String params) throws Exception {
//		final List<BorrowEntity> entity = borrowBiz.query(10);
		TransferParams param = JSON.parseObject(params, TransferParams.class);

		 Cipher c = Cipher.getInstance("DESede");
		 c.init(Cipher.ENCRYPT_MODE, param.getKey());
	        byte[] src = param.getParam2().getBytes();
	        // 加密，结果保存进cipherByte
	      byte[]  cipherByte = c.doFinal(src);
	      param.setParam2(new String(cipherByte));
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update((param.getParam1()+param.getParam2()).getBytes());
		byte[] tmp =md5.digest();
		StringBuilder sb = new StringBuilder();
		for(byte b:tmp){
			sb.append(Integer.toHexString(b&0xFF));
		}
		if(StringUtils.equals(param.getMd5(), sb.toString())){
		return JSONObject.toJSONString(new MyResponse("OK", "fejaijfi"));
		}
		throw new RuntimeException("djfl");
	}

	// @ModelAttribute
	// public void a2(final Model m,final TransferParams params){
	// m.addAttribute(params);
	// }

}

@Data
class TransferParams {
	private SecretKey key;
	private String md5;
	private String param1;
	private String param2;
	public TransferParams() {
	}
}
