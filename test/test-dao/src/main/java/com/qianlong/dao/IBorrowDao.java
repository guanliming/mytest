package com.qianlong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.qianlong.BorrowEntity;

/**
 * @author 管黎明
 *
 *         All rights reserved.
 */
@Repository
public interface IBorrowDao {
	
	@Insert(
			"INSERT INTO dawn.borrow "
            +"( "
            +" borrow_amount, "
            +" borrow_type, "
            +" period, "
            +" borrow_user_id, "
            +" borrow_time, "
            +" on_account, "
            +" repay_all, "
            +" should_repay_all) "
			+"VALUES ( "
			+"        #{borrowAmount}, "
			+"        #{borrowType}, "
			+"        #{period}, "
			+"        #{borrowUserId}, "
			+"        #{borrowTime}, "
			+"        #{onAccount},  "
			+"        #{repayAll}, "
			+"        #{shouldRepayAll}) "
			)
	long save(final BorrowEntity toSave);
	
	
	@Results({@Result(column="borrow_amount",property="borrowAmount"),
			@Result(column="borrow_time",property="borrowTime"),
			@Result(column="borrow_type",property="borrowType"),
			@Result(column="borrow_user_id",property="borrowUserId"),
			@Result(column="on_account",property="onAccount"),
			@Result(column="repay_all",property="repayAll"),
			@Result(column="should_repay_all",property="shouldRepayAll"),
			@Result(column="completely_pay_off",property="completelyPayOff")
	})
	@Select("SELECT * FROM DAWN.BORROW WHERE borrow_user_id=#{userId}")
	List<BorrowEntity> query(@Param("userId")final long userId); 
}
