package com.shadow;

import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author guanliming
 *
 */
@Data
@EqualsAndHashCode
public class CommonEntity {

	private Integer id;

	private String tableName;

	private Map<String, String> pairs;

	public CommonEntity() {

	}

	public CommonEntity(String tableName, Map<String, String> pairs) {
		this.tableName = tableName;
		this.pairs = pairs;
	}

}
