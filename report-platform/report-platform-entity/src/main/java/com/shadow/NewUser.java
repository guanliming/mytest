package com.shadow;

import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.mobanker.framework.entity.BaseEntity;
import com.shadow.annos.Required;

/**
 * @author guanliming
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
@ToString(callSuper = true)
@Table(name = "user")
public class NewUser extends BaseEntity {
	@Required
	private String userName;
	@Required
	private String idCard;
	@Required
	private String sex;
	@Required
	private String marrage;
	@Required
	private String nativePlace;
	@Required
	private String oriAddress;
	@Required
	private String topEdu;
	@Required
	private String telPhone;
	@Required
	private String email;
	@Required
	private String contactAddress;
	@Required
	private String mobile;
	@Required
	private String company;
	@Required
	private String companyAddress;
	@Required
	private String duty;
	@Required
	private String yearlyIncoming;
	@Required
	private String ralatives;
	@Required
	private String ralativesMobile;
	@Required
	private String freind;
	@Required
	private String freindMobile;
	private String deleteTag;
}
