package com.shadow;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author guanliming
 *
 */
@Data
@EqualsAndHashCode
public class Base1Entity {
	private Integer id;
	private Date createTime;
	private Date updateTime;
	private String createUser;
	private String updateUser;
}
