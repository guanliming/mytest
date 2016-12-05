package com.shadow.demo.yearly;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shadow.demo.yearly.bo.PayBo;
import com.shadow.demo.yearly.bo.QueryBo;
import com.shadow.demo.yearly.dao.YearlyDataDao;
import com.shadow.demo.yearly.dto.QueryDataDto;
import com.shadow.demo.yearly.dto.response.Result;
import com.shadow.demo.yearly.entity.YearlyData;
import com.shadow.demo.yearly.util.Utils;

/**
 * @author guanliming
 *
 */
@Controller
public class MainController {
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	@Autowired
	private YearlyDataDao yearlyDataDao;

	private static final String TYPE_CRAWLER = "CRAWLER_DATA";
	private static final String TYPE_QUERY_DATA = "USER_DATA";
	private static final String TYPE_CONFIG_DATA = "CONFIG";
	private static final String FAIL = "0";
	private static final String SUCCESS = "1";
	private static final String SUCCESS_ERROR = "00000000";
	private static final List<String> TYPELIST = Arrays.asList(TYPE_CRAWLER, TYPE_QUERY_DATA);

	@RequestMapping("queryData")
	@ResponseBody
	public String queryData(QueryBo request) {
		System.out.println(JSONObject.toJSONString(request));
		Result re = new Result();
		if (!TYPELIST.contains(request.getType())) {
			re.setStatus(FAIL);
			re.setMessage("参数不合法");
			re.setError("9001");
		} else {
			YearlyData data = yearlyDataDao.query(request.getType(), request.getOrder());
			QueryDataDto dto = new QueryDataDto();
			dto.setName(data.getParam1());
			dto.setIdCard(masaike(data.getParam2(), 4, 13, request.getType()));
			dto.setAge(data.getParam3());
			dto.setSex(data.getParam4());
			dto.setBankcard(masaike(data.getParam5(), 7, 15, request.getType()));
			dto.setMobile(masaike(data.getParam6(), 4, 6, request.getType()));
			dto.setUrl(data.getParam7());
			dto.setWeded(data.getParam8());
			dto.setEdu(data.getParam9());
			dto.setCollege(data.getParam10());
			dto.setAddress(data.getParam11());
			dto.setScore(data.getParam12());
			dto.setOverdue(data.getParam13());
			dto.setLimit(data.getParam14());
			if (data != null) {
				re.setData(dto);
				re.setStatus(SUCCESS);
				re.setError(SUCCESS_ERROR);
			} else {
				re.setStatus(FAIL);
				re.setError("9002");
			}
		}
		return JSONObject.toJSONString(re);
	}

	@RequestMapping("test")
	@ResponseBody
	public String test(QueryBo request) {
		return "哈哈";
	}

	/**
	 * 将start-end位替换成***
	 * 
	 * @param content
	 * @param start
	 * @param end
	 * @param type
	 *            TODO
	 * @return
	 */
	private String masaike(String content, Integer start, Integer end, String type) {
		if (!StringUtils.equals(TYPE_QUERY_DATA, type)) {
			return content;
		}
		if (content.toCharArray().length < end) {
			throw new RuntimeException("配置异常");
		}
		String prefix = content.substring(0, start - 1);
		String suffix = content.substring(end);
		return prefix + "***" + suffix;
	}

	@RequestMapping("pay")
	@ResponseBody
	public String pay(PayBo request) {
		Result re = new Result();
		try {
			Map<String, String> mm = new HashMap<String, String>();
			// TODO 调用范从春短信接口
			// mm.put("nid", value);
			// mm.put("phone", value);
			// mm.put("replaceParam", value);
			YearlyData data = yearlyDataDao.query(TYPE_CONFIG_DATA, "SMS_URL");
			if (data != null) {
				String result = "{}";
				try {
					 result = Utils.doPost(data.getParam1(), mm);
				} catch (Exception e) {
					e.printStackTrace();
				}
				JSONObject jo = JSONObject.parseObject(result);
				if (StringUtils.equals(jo.getString("status"), SUCCESS)
						&& StringUtils.equals(jo.getString("error"), SUCCESS_ERROR)) {
					re.setError(SUCCESS_ERROR);
					re.setStatus(SUCCESS);
					return JSONObject.toJSONString(result);
				} else {
					re.setError(FAIL);
					re.setStatus("9004");
					return JSONObject.toJSONString(result);
				}
			} else {
				throw new RuntimeException("短信地址为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			re.setError("9003");
			re.setStatus(FAIL);
			return JSONObject.toJSONString(re);
		}
	}

	@RequestMapping("faceCore")
	@ResponseBody
	public String faceCore(HttpServletRequest request) {
		String order = request.getParameter("order");
		Result re = new Result();
		if (StringUtils.isBlank(order)) {
			re.setStatus(FAIL);
			re.setMessage("参数不合法");
		} else {
			FileItemFactory fileItemFactory = new DiskFileItemFactory();
			ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
			try {
				List<FileItem> fileItems = servletFileUpload.parseRequest(request);
				if (null == fileItems || fileItems.size() <= 0) {
					return "please upload images";
				}
				YearlyData data = yearlyDataDao.query(TYPE_QUERY_DATA, order);
				String imageUrl = getImageUrl(data.getParam2());
				uploadFile(fileItems, imageUrl);
				data.setParam7(imageUrl);
				yearlyDataDao.update(data);
			} catch (Exception e) {
				e.printStackTrace();
				return "system exception";
			}
			re.setStatus(SUCCESS);
		}
		return JSON.toJSONString(re);
	}

	private void uploadFile(List<FileItem> fileItems, String imageUrl) throws IOException, FileNotFoundException {
		FileItem fileItem = fileItems.get(0);
		InputStream fs = fileItem.getInputStream();
		FileOutputStream fos = new FileOutputStream(imageUrl);
		byte[] b = new byte[1024];
		int len = 0;
		while ((len = fs.read(b)) != -1) {
			fos.write(b, 0, len);
		}
		fos.flush();
		fos.close();
		fs.close();
	}

	private String getImageUrl(String idCard) {
		boolean linux = getOSMatches("Linux") || getOSMatches("LINUX");
		YearlyData data = null;
		if (linux) {
			data = yearlyDataDao.query(TYPE_CONFIG_DATA, "LINUX_DIR");
		} else {
			data = yearlyDataDao.query(TYPE_CONFIG_DATA, "WINDOW_DIR");
		}
		if (StringUtils.isNotBlank(data.getParam1())) {
			return data.getParam1() + File.separator + idCard + ".jpg";
		} else {
			throw new RuntimeException("系统异常");
		}
	}

	private boolean getOSMatches(String osNamePrefix) {
		String os = System.getProperty("os.name");
		if (os == null) {
			return false;
		}
		return os.startsWith(osNamePrefix);
	}

}
