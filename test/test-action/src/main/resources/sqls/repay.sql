CREATE TABLE `repay` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `borrow_id` bigint(20) NOT NULL COMMENT '借款表主键',
  `period` smallint(6) NOT NULL COMMENT '期数',
  `should_repay` decimal(10,2) NOT NULL COMMENT '当期应该偿还金额',
  `repay` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '当期偿还金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
