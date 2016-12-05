CREATE TABLE `repay` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `borrow_id` bigint(20) NOT NULL COMMENT '借款表主键',
  `period` smallint(6) NOT NULL COMMENT '期数',
  `should_repay` decimal(10,2) DEFAULT NULL COMMENT '当期理论应偿金额',
  `should_repay_no_on_account` decimal(10,2) DEFAULT NULL COMMENT '当期理论应偿金额(不计算挂账)',
  `actual_should_repay` decimal(10,2) DEFAULT NULL COMMENT '当期实际应偿金额(计算挂账)',
  `repay` decimal(10,2) DEFAULT '0.00' COMMENT '当期偿还金额',
  `balance` varchar(2) DEFAULT 'N' COMMENT 'Y:销账 N未销账',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8
