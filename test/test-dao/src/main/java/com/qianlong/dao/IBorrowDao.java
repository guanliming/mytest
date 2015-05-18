package com.qianlong.dao;

import org.apache.ibatis.annotations.Insert;
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
	int save(final BorrowEntity toSave);
	
	
}
