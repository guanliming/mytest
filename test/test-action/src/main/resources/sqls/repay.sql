CREATE TABLE `repay` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `borrow_id` bigint(20) NOT NULL COMMENT '借款表主键',
  `period` smallint(6) NOT NULL COMMENT '期数',
  `should_repay` decimal(10,2) NOT NULL COMMENT '当期理论应偿金额',
  `actual_should_repay` decimal(10,2) NOT NULL COMMENT '当期实际应偿金额',
  `repay` decimal(10,2) DEFAULT '0.00' COMMENT '当期偿还金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
