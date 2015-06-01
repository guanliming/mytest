package com.qianlong.controllers;

import lombok.Data;
import lombok.Getter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

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

	@RequestMapping(value = "a1", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String a1( final TransferParams params) {
//		return new MyResponse("OK","fejaijfi");
		return JSONObject.toJSONString(new MyResponse("OK","fejaijfi"));
	}

}

@Data
class TransferParams {
	private String param1;
	private String param2;

	public TransferParams() {
	}
}
