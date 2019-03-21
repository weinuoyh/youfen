-- 创建库
CREATE DATABASE ace_data;
-- 使用库
USE ace_data;
-- 创建身份证电话信息表（三要素验证）
DROP TABLE IF EXISTS idcard_mobile_info;
CREATE TABLE IF NOT EXISTS  idcard_mobile_info(
  `id` BIGINT(21) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` VARCHAR(128) NOT NULL COMMENT '姓名',
  `id_card` VARCHAR(18) NOT NULL COMMENT '身份证号',
  `mobile` VARCHAR(11) NOT NULL COMMENT '手机号',
  `opration_user` INT(11) NOT NULL COMMENT '操作人',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `last_modify_time` DATETIME NOT NULL COMMENT '最近修改时间',
  `is_deleted` TINYINT(1) DEFAULT '1' COMMENT '是否删除（0：已删除  1：未删除）',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='身份证电话信息表';



-- 创建手机号归属地信息
DROP TABLE IF EXISTS mobile_attr_info;
CREATE TABLE IF NOT EXISTS  mobile_attr_info(
  `id` BIGINT(21) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `mobile` VARCHAR(11) NOT NULL COMMENT '手机号',  
  `mobile_isp` VARCHAR(128) NOT NULL COMMENT '手机号供应商',
  `mobile_card_type` VARCHAR(128) NOT NULL COMMENT '手机卡类型',
  `province_code` VARCHAR(32) NOT NULL COMMENT '所在省份编码',
  `province_name` VARCHAR(128) NOT NULL COMMENT '所在省份',
  `city_code` VARCHAR(32) NOT NULL COMMENT '所在城市编码',
  `city_name` VARCHAR(128)  NULL COMMENT '所在城市(预留字段)',    
  `area_code` VARCHAR(32) NOT NULL COMMENT '所在区域编码',
  `area_name` VARCHAR(128) NOT NULL COMMENT '所在区域',  
  `opration_user` INT(11) NOT NULL COMMENT '操作人',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `last_modify_time` DATETIME NOT NULL COMMENT '最近修改时间',
  `is_deleted` TINYINT(1) DEFAULT '1' COMMENT '是否删除（0：已删除  1：未删除）',
  PRIMARY KEY (`id`),
  INDEX idx_mai_mobile(`mobile`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='手机归属地信息表';



-- 创建个人法院失信信息(简项)
DROP TABLE IF EXISTS per_dish_sample;
CREATE TABLE IF NOT EXISTS  per_dish_sample(
  `id` BIGINT(21) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `dish_id` VARCHAR(255) NOT NULL COMMENT '失信公告编号',
  `name` VARCHAR(128) NOT NULL COMMENT '姓名',
  `id_card` VARCHAR(18) NOT NULL COMMENT '身份证号',
  `case_time` VARCHAR(128) COMMENT '立案时间', 
  `title` VARCHAR(255) NOT NULL COMMENT '标题',
  `content` TEXT NOT NULL COMMENT '内容',
  `match_ratio` VARCHAR(64) NOT NULL COMMENT '匹配度',
  `data_type` VARCHAR(64) NOT NULL COMMENT '数据类型（shixin）',
  `opration_user` INT(11) NOT NULL COMMENT '操作人',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `last_modify_time` DATETIME NOT NULL COMMENT '最近修改时间',
  `is_deleted` TINYINT(1) DEFAULT '1' COMMENT '是否删除（0：已删除  1：未删除）',
  PRIMARY KEY (`id`),
  INDEX idx_pds_dish_id(`dish_id`),
  INDEX idx_pds_id_card(`id_card`),
  INDEX idx_pds_name(`name`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='个人失信简项表';



-- 创建个人法院失信信息(详情)
DROP TABLE IF EXISTS per_dish_detail;
CREATE TABLE IF NOT EXISTS  per_dish_detail(
  `id` BIGINT(21) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `dish_id` VARCHAR(255) NOT NULL COMMENT '失信公告编号',  
  `case_time` VARCHAR(64) COMMENT '立案时间',  
  `gender` VARCHAR(16) COMMENT '性别',
  `perf_info`  VARCHAR(255)  COMMENT '被执行人的履行情况',
  `perf_basis_no` VARCHAR(255)  COMMENT '执行依据文号',
  `related_party`  VARCHAR(128)  COMMENT '相关当事人',
  `judg_cout_name`  VARCHAR(255)  COMMENT '执行法院名称',
  `id_card` VARCHAR(18)  COMMENT '身份证号',
  `exec_cout_name`  VARCHAR(128)  COMMENT '做出执行依据单位',
  `conc_situ`  VARCHAR(255)  COMMENT '失信被执行人行为具体情形',
  `obligation`  VARCHAR(255)  COMMENT '生效法律文件确定的义务',
  `age` INT(3) COMMENT '年龄',
  `exec_name`  VARCHAR(128)  COMMENT '被执行人姓名',  
  `province`  VARCHAR(128)   COMMENT '省份',  
  `case_num` VARCHAR(128)  COMMENT '案号',
  `publish_time`  VARCHAR(64) COMMENT '发布时间', 
  `data_type` VARCHAR(64) NOT NULL COMMENT '数据类型（shixin）',  
  `opration_user` INT(11) NOT NULL COMMENT '操作人',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `last_modify_time` DATETIME NOT NULL COMMENT '最近修改时间',
  `is_deleted` TINYINT(1) DEFAULT '1' COMMENT '是否删除（0：已删除  1：未删除）',
  PRIMARY KEY (`id`),
  INDEX idx_pdd_dish_id(`dish_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='个人失信详情表';



-- 多头借贷逾期信息表
DROP TABLE IF EXISTS overdule_info;
CREATE TABLE IF NOT EXISTS  overdule_info(
  `id` BIGINT(21) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `mobile` VARCHAR(11) NOT NULL COMMENT '手机号',
  `cycle` INT(11) NOT NULL COMMENT '时间周期(1,3,6,9,12,24)单位:月,默认12',  
  `platform_type` TINYINT(1) NOT NULL  COMMENT '机构类型编号',
  `platform_type_name`  VARCHAR(64) NOT NULL  COMMENT '机构类型名称',
  `platform_code` VARCHAR(64) NOT NULL  COMMENT '机构编码',
  `platform_name` VARCHAR(128)  COMMENT '机构名称',
  `overdue_time`  DATETIME NOT NULL COMMENT '贷款最近预期时间',
  `count` INT(11) NOT NULL COMMENT '贷款预期次数',
  `money` VARCHAR(64) NOT NULL COMMENT '贷款预期金额区间',  
  `opration_user` INT(11) NOT NULL COMMENT '操作人',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `last_modify_time` DATETIME NOT NULL COMMENT '最近修改时间',
  `is_deleted` TINYINT(1) DEFAULT '1' COMMENT '是否删除（0：已删除  1：未删除）',
  PRIMARY KEY (`id`),
  INDEX idx_oi_mobile(`mobile`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='多头借贷逾期信息表';



-- 多头借贷全量核查信息表
DROP TABLE IF EXISTS credit_detail_info;
CREATE TABLE IF NOT EXISTS  credit_detail_info(
  `id` BIGINT(21) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `mobile` VARCHAR(11) NOT NULL COMMENT '手机号',
  `cycle` INT(11) NOT NULL COMMENT '时间周期(1,3,6,9,12,24)单位:月,默认12',
  `prov_name`  VARCHAR(128) NOT NULL COMMENT '省', 
  `city_name` VARCHAR(128) NOT NULL COMMENT '市', 
  `data` TEXT NOT NULL COMMENT '数据(json格式)',  
  `opration_user` INT(11) NOT NULL COMMENT '操作人',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `last_modify_time` DATETIME NOT NULL COMMENT '最近修改时间',
  `is_deleted` TINYINT(1) DEFAULT '1' COMMENT '是否删除（0：已删除  1：未删除）',
  PRIMARY KEY (`id`),
  INDEX idx_cdi_mobile(`mobile`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='多头借贷全量核查信息表';



-- 创建操作日志表
DROP TABLE IF EXISTS operation_log_tbl;
CREATE TABLE IF NOT EXISTS `operation_log_tbl` (
  `id` BIGINT(21) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `api_name` VARCHAR(128) DEFAULT NULL COMMENT 'api名称（例：获取城市列表）',
  `api_uri`  VARCHAR(255) DEFAULT NULL  COMMENT 'api接口uri',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `is_succeed` TINYINT(1) DEFAULT '1' COMMENT '是否成功(0：失败  1：成功)',
  `message` TEXT DEFAULT NULL COMMENT '不成功时显示的信息',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='操作日志表';

-- 创建个人银联表
CREATE TABLE `per_bill_veriflation` (
  `id` bigint(21) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(128) NOT NULL COMMENT '姓名',
  `bank_card` varchar(32) NOT NULL COMMENT '银行卡号',
  `trans_time` datetime NOT NULL COMMENT '交易时间',
  `trans_amount` double NOT NULL COMMENT '交易金额（单位元）',
  `currency` varchar(32) NOT NULL COMMENT '币种',
  `opration_user` int(11) NOT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `last_modify_time` datetime NOT NULL COMMENT '最近修改时间',
  `is_deleted` tinyint(1) DEFAULT '1' COMMENT '是否删除（0：已删除  1：未删除）',
  PRIMARY KEY (`id`),
  KEY `idx_pbv_name_card_time` (`name`,`bank_card`,`trans_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='个人银联账单验证表';

-- 身份核查信息表（二要素、三要素、四要素）
CREATE TABLE `validation_info` (
  `id` BIGINT(21) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` VARCHAR(50) DEFAULT NULL COMMENT '姓名',
  `id_card` VARCHAR(18) DEFAULT NULL COMMENT '身份证号',
  `cell_phone` VARCHAR(11) DEFAULT NULL COMMENT '手机号',
  `bank_card` VARCHAR(50) DEFAULT NULL COMMENT '银行卡',
  `type` VARCHAR(2) NOT NULL COMMENT '查询类型',
  `type_name` VARCHAR(50) NOT NULL COMMENT '查询类型描述',
  `opration_user` INT(11) NOT NULL COMMENT '操作人',
  `create_time` DATETIME NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modify_time` DATETIME NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
  `is_deleted` INT(1) DEFAULT '1' COMMENT '是否删除（0：已删除  1：未删除）',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8 COMMENT='身份核查信息表'

#==========================================================================================