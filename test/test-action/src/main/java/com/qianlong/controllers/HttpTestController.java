package com.qianlong.controllers;

import java.util.List;

import lombok.Data;
import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.qianlong.BorrowEntity;
import com.qianlong.biz.IBorrowBiz;

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
	public String a1(final TransferParams params) {
		final List<BorrowEntity> entity = borrowBiz.query(10);
		return JSONObject.toJSONString(new MyResponse("OK", "fejaijfi"));
	}

	// @ModelAttribute
	// public void a2(final Model m,final TransferParams params){
	// m.addAttribute(params);
	// }

}

@Data
class TransferParams {
	private String param1;
	private String param2;

	public TransferParams() {
	}
}
