package com.shadow.demo.yearly.dto.response;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author guanliming
 *
 */
@Data
@EqualsAndHashCode
public class Result implements Serializable {

	/**
	 * 1-成功 2-失败
	 */
	private String status;
	
	private Object data;
	
	private String message;
	
	private String error;

}
