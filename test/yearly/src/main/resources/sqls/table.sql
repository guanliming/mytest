drop table if exists yearly_data;
create table yearly_data
(
   id                   bigint not null auto_increment,
   type                 varchar(20) not null,
   param1               varchar(255),
   param2               varchar(255),
   param3               varchar(255),
   param4               varchar(255),
   param5               varchar(255),
   param6               varchar(255),
   param7               varchar(255),
   param8               varchar(255),
   param9               varchar(255),
   param10              varchar(255),
   param11              varchar(255),
   param12              varchar(255),
   param13              varchar(255),
   param14              varchar(255),
   param15              varchar(255),
   param16              varchar(255),
   param17              varchar(255),
   param18              varchar(255),
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   create_user          varchar(50) not null default '' comment '创建用户',
   update_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '最后变更时间',
   update_user          varchar(50) not null default '' comment '更改用户',
   primary key (id)
);
