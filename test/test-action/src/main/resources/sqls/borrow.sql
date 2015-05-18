CREATE TABLE `borrow` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `borrow_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '借款金额',
  `borrow_type` varchar(2) NOT NULL COMMENT '借款类型',
  `period` smallint(6) DEFAULT '0' COMMENT '期数',
  `borrow_user_id` bigint(20) NOT NULL COMMENT '借款人Id',
  `borrow_time` date NOT NULL COMMENT '借款时间',
  `on_account` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '挂账',
  `repay_all` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '已还总金额',
  `should_repay_all` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '应还总金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
