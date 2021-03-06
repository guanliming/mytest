package com.qianlong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
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
//	select LAST_INSERT_ID() as id
	@SelectKey(keyProperty="id",keyColumn="id",resultType=Integer.class,statement={"SELECT @@IDENTITY "},before=false)
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
	@Select("SELECT * FROM dawn.borrow WHERE borrow_user_id=#{userId} order by borrow_time desc,id desc")
	List<BorrowEntity> query(@Param("userId")final long userId);
	
	@Select("SELECT * FROM dawn.borrow WHERE id=#{id} ")
	List<BorrowEntity> queryById(@Param("id")final long id);


	@Update("UPDATE dawn.`borrow` SET on_account = #{onAccount},completely_pay_off=#{completelyPayOff} where id =#{id}")
	void updateOnAccount(final BorrowEntity borrow);
}
