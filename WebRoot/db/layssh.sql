/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50523
Source Host           : localhost:3306
Source Database       : layssh

Target Server Type    : MYSQL
Target Server Version : 50523
File Encoding         : 65001

Date: 2017-12-25 16:06:02
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `t_app_student`
-- ----------------------------
DROP TABLE IF EXISTS `t_app_student`;
CREATE TABLE `t_app_student` (
  `UUID` varchar(36) NOT NULL DEFAULT '' COMMENT '主键',
  `NAME` varchar(50) NOT NULL COMMENT '名字',
  `SEX` varchar(1) NOT NULL COMMENT '性别',
  `STATE` varchar(1) NOT NULL COMMENT '状态',
  `NOTE` varchar(200) DEFAULT NULL COMMENT '备注',
  `DATE_BIRTH` varchar(10) NOT NULL COMMENT '出生年月',
  `ARCHIVES` varchar(500) NOT NULL COMMENT '档案',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`UUID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='学生表';

-- ----------------------------
-- Records of t_app_student
-- ----------------------------
INSERT INTO `t_app_student` VALUES ('cfea70be-0820-462e-a436-865fdca03848', '小明', '0', '1', '优秀学生', '2017-12-15', 'laysshframe.zip', '2017-12-15 16:56:08', '2017-12-15 19:22:05');
INSERT INTO `t_app_student` VALUES ('fe990467-9135-4e97-a797-ee3458be1f08', '小红', '1', '0', '差生', '2017-12-15', 'fallsea-fsLayuiPlugin-master.zip', '2017-12-15 18:16:03', '2017-12-18 10:32:54');

-- ----------------------------
-- Table structure for `t_app_test`
-- ----------------------------
DROP TABLE IF EXISTS `t_app_test`;
CREATE TABLE `t_app_test` (
  `myuuid` varchar(36) NOT NULL DEFAULT '' COMMENT '主键',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `sex` varchar(1) DEFAULT NULL COMMENT '性别',
  `age` varchar(1) DEFAULT NULL COMMENT '年纪',
  PRIMARY KEY (`myuuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of t_app_test
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sys_dict_cd`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dict_cd`;
CREATE TABLE `t_sys_dict_cd` (
  `UUID` varchar(36) NOT NULL COMMENT 'UUID',
  `DICT_CD` varchar(36) NOT NULL COMMENT '字典代码',
  `DICT_NM` varchar(100) DEFAULT NULL COMMENT '字典名称',
  `DICT_TYPE_CD` varchar(36) NOT NULL COMMENT '字典类型',
  PRIMARY KEY (`UUID`) USING BTREE,
  KEY `FK_Reference_1` (`DICT_TYPE_CD`) USING BTREE,
  CONSTRAINT `t_sys_dict_cd_ibfk_1` FOREIGN KEY (`DICT_TYPE_CD`) REFERENCES `t_sys_dict_tp` (`DICT_TYPE_CD`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='数据字典表';

-- ----------------------------
-- Records of t_sys_dict_cd
-- ----------------------------
INSERT INTO `t_sys_dict_cd` VALUES ('0013d932-ca44-4df6-bdc4-bdcb1f667947', '0', '运行中', 'quarz_state');
INSERT INTO `t_sys_dict_cd` VALUES ('01949f81-cfb8-4a0a-a455-b36a0e719fdf', 'quarz/add.do', 'true', 'adminOper');
INSERT INTO `t_sys_dict_cd` VALUES ('01ad3289-a03d-47e0-8dd1-99e3f288b38a', '2', '注销', 'oper_st');
INSERT INTO `t_sys_dict_cd` VALUES ('030f558d-d097-47d0-91b2-5ed63216fc8b', '0', '系统管理员', 'job_tp');
INSERT INTO `t_sys_dict_cd` VALUES ('03183bc4-6ae5-4fff-871e-efc097ecfbfa', 'menuInf/add.do', 'true', 'adminOper');
INSERT INTO `t_sys_dict_cd` VALUES ('06448028-911f-4f05-91f7-69a84f0e25a1', '2', '市公司', 'ins_lvl');
INSERT INTO `t_sys_dict_cd` VALUES ('081531fe-fd4a-45dc-bf50-beb228b3e0c5', '0', '男', 't_test_sex');
INSERT INTO `t_sys_dict_cd` VALUES ('08e3e5e7-4204-4376-98d3-0a4cb8deba93', 'menuInf/remove.do', 'true', 'adminOper');
INSERT INTO `t_sys_dict_cd` VALUES ('0ae3dd85-ad00-42b4-a58c-84eedccd865f', 'insInf/add.do', 'true', 'adminOper');
INSERT INTO `t_sys_dict_cd` VALUES ('0cae7414-9e80-465a-b150-e04869be1e54', 'sysFunctionInf/add.do', 'true', 'adminOper');
INSERT INTO `t_sys_dict_cd` VALUES ('0ead7704-ebdb-4883-b2ea-b03a20f0fcc9', 'menuInf/modify.do', 'true', 'adminOper');
INSERT INTO `t_sys_dict_cd` VALUES ('0ffed251-96e9-4a8a-919e-a98cb48b814d', 'dictTp/remove.do', 'true', 'adminOper');
INSERT INTO `t_sys_dict_cd` VALUES ('1447e7af-970b-4b00-bf17-76afccf230d8', 'dictTp/modify.do', 'true', 'adminOper');
INSERT INTO `t_sys_dict_cd` VALUES ('17a314b1-7470-4eaf-a3ef-4110f7599564', 'quarz/remove.do', 'true', 'adminOper');
INSERT INTO `t_sys_dict_cd` VALUES ('17fee7dd-2ad4-43b4-b8fb-912e821bb44d', 'dictCd/modify.do', 'true', 'adminOper');
INSERT INTO `t_sys_dict_cd` VALUES ('1a24e3b9-8eb6-479e-82e0-83bedcb529e5', '1', '关闭', 'timer_star_closed');
INSERT INTO `t_sys_dict_cd` VALUES ('1dd03710-66c6-4c33-a94d-2a61ace43e9c', 'insInf/remove.do', 'true', 'adminOper');
INSERT INTO `t_sys_dict_cd` VALUES ('298dda6e-3216-410f-b0b0-fe7f5fc2e932', '1', '定时执行', 'timer_type');
INSERT INTO `t_sys_dict_cd` VALUES ('2e924c5a-800d-43ae-a71a-ed710ef78fc0', '0', '在读', 'student_state');
INSERT INTO `t_sys_dict_cd` VALUES ('311b283a-d7d2-4655-b555-52818ee37652', '0', '正常', 'oper_st');
INSERT INTO `t_sys_dict_cd` VALUES ('31b816c6-7a13-44cf-9a54-1535dd71b325', '0', '开启', 'timer_star_closed');
INSERT INTO `t_sys_dict_cd` VALUES ('34a31c51-7760-49f3-85da-37496b84aa87', '1', '女', 't_test_sex');
INSERT INTO `t_sys_dict_cd` VALUES ('4163a2f9-5b3f-4502-a94d-1fc9e464bc3a', '1', '省公司', 'ins_lvl');
INSERT INTO `t_sys_dict_cd` VALUES ('45145bec-903b-4d6f-92c0-367addc5875c', '2', '异常', 'timer_state');
INSERT INTO `t_sys_dict_cd` VALUES ('5a8e7226-6755-4c9b-a5a5-fe6db4a8d7a9', 'dictTp/add.do', 'true', 'adminOper');
INSERT INTO `t_sys_dict_cd` VALUES ('5d915556-10fc-4a25-be73-0886be2d8299', '0', '男', 'student_sex');
INSERT INTO `t_sys_dict_cd` VALUES ('5fad9f38-487a-4143-b1ac-feaec636b29d', 'codeGenerator/createCode.do', 'true', 'adminOper');
INSERT INTO `t_sys_dict_cd` VALUES ('66607068-9d6a-4d57-ad35-0bb5fbed653d', 'dictCd/remove.do', 'true', 'adminOper');
INSERT INTO `t_sys_dict_cd` VALUES ('6a870049-2b9c-47b2-9e8e-8ff892439cc0', '0', '运行中', 'timer_state');
INSERT INTO `t_sys_dict_cd` VALUES ('6b2913a8-3e89-48f4-a1db-2722ec1e2b59', '0', '管理机构', 'ins_tp');
INSERT INTO `t_sys_dict_cd` VALUES ('6c2b3b11-c737-4892-92a2-6e397c714546', 'sysFunctionInf/remove.do', 'true', 'adminOper');
INSERT INTO `t_sys_dict_cd` VALUES ('6ecde32b-5dea-43c3-ac66-580e7c5283c1', '1', '仓库管理员', 'job_tp');
INSERT INTO `t_sys_dict_cd` VALUES ('6faa52a5-d85b-4182-8379-da5f75c6c8cd', '0', '集团公司', 'ins_lvl');
INSERT INTO `t_sys_dict_cd` VALUES ('72d513c4-5fcb-42b6-b915-05787b350894', 'sysFunctionInf/modify.do', 'true', 'adminOper');
INSERT INTO `t_sys_dict_cd` VALUES ('752d5f6c-b982-4a17-b6c1-f4b746df5fe5', '1', '女', 'student_sex');
INSERT INTO `t_sys_dict_cd` VALUES ('75e69cfd-0f13-4732-9907-844da202a030', '1', '销售', 'ins_tp');
INSERT INTO `t_sys_dict_cd` VALUES ('7922456b-e6b2-49b6-983e-013f37b7a668', '3', '县区', 'ins_lvl');
INSERT INTO `t_sys_dict_cd` VALUES ('7c326bfd-3ddf-4407-a5b1-d3160705a2c2', '2', '其他', 'ins_tp');
INSERT INTO `t_sys_dict_cd` VALUES ('8ee64ac2-f708-403b-9b84-8d3a3ba2d461', 'noInterceptor/modify.do', 'true', 'adminOper');
INSERT INTO `t_sys_dict_cd` VALUES ('8fbbd735-3422-4411-9033-57276508a1a8', '2', '定时执行+重复执行', 'timer_type');
INSERT INTO `t_sys_dict_cd` VALUES ('941c494c-4c88-48e7-abf9-104254fa77d1', 'quarz/modify.do', 'true', 'adminOper');
INSERT INTO `t_sys_dict_cd` VALUES ('94c8a285-14c3-4a8b-8f6b-dd59b7b48c0b', 'noInterceptor/add.do', 'true', 'adminOper');
INSERT INTO `t_sys_dict_cd` VALUES ('ba1f2e47-02e8-487d-9ad3-da3be7c5f123', '1', '暂停', 'timer_state');
INSERT INTO `t_sys_dict_cd` VALUES ('cb95b367-6f52-4b1a-821f-16ef7928e0d6', '1', '毕业', 'student_state');
INSERT INTO `t_sys_dict_cd` VALUES ('cba6590a-549a-4ab2-8443-72f8c239f540', 'dictCd/add.do', 'true', 'adminOper');
INSERT INTO `t_sys_dict_cd` VALUES ('cf681fff-180a-47ae-9758-22373b80556c', '0', '重复执行', 'timer_type');
INSERT INTO `t_sys_dict_cd` VALUES ('d2f640cc-be8a-407f-8304-344e1218cf33', '1', '第二组', 'quarz_job_group');
INSERT INTO `t_sys_dict_cd` VALUES ('d65959a6-8d48-4c01-8fc4-b7acaa6bbe65', 'insInf/modify.do', 'true', 'adminOper');
INSERT INTO `t_sys_dict_cd` VALUES ('dec87293-85b2-4b14-be12-9ca723cbbe35', '0', '第一组', 'quarz_job_group');
INSERT INTO `t_sys_dict_cd` VALUES ('e4db4b0f-1996-461c-aa16-22f3ed04d8ba', 'noInterceptor/remove.do', 'true', 'adminOper');
INSERT INTO `t_sys_dict_cd` VALUES ('fcbea470-7cef-4dc4-8cf9-da7b9fea2592', '1', '暂停', 'quarz_state');
INSERT INTO `t_sys_dict_cd` VALUES ('fdb404d3-b5f3-4060-9ce0-e770132251b9', '0', '后台管理系统', 'sys_nm');
INSERT INTO `t_sys_dict_cd` VALUES ('fde25881-0f1f-4e70-870f-15257135d94b', '2', '异常', 'quarz_state');

-- ----------------------------
-- Table structure for `t_sys_dict_tp`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dict_tp`;
CREATE TABLE `t_sys_dict_tp` (
  `DICT_TYPE_CD` varchar(36) NOT NULL COMMENT '字典类型编号',
  `DICT_TYPE_NM` varchar(100) NOT NULL COMMENT '字典类型名称',
  PRIMARY KEY (`DICT_TYPE_CD`) USING BTREE,
  KEY `DICT_TYPE_CD` (`DICT_TYPE_CD`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='字典类型表';

-- ----------------------------
-- Records of t_sys_dict_tp
-- ----------------------------
INSERT INTO `t_sys_dict_tp` VALUES ('adminOper', '超级管理员才能操作的模块');
INSERT INTO `t_sys_dict_tp` VALUES ('ins_lvl', '机构级别');
INSERT INTO `t_sys_dict_tp` VALUES ('ins_tp', '机构类型');
INSERT INTO `t_sys_dict_tp` VALUES ('job_tp', '岗位类型');
INSERT INTO `t_sys_dict_tp` VALUES ('oper_st', '用户状态');
INSERT INTO `t_sys_dict_tp` VALUES ('quarz_job_group', '调度任务组');
INSERT INTO `t_sys_dict_tp` VALUES ('quarz_state', '调度器状态');
INSERT INTO `t_sys_dict_tp` VALUES ('student_sex', '学生表-性别');
INSERT INTO `t_sys_dict_tp` VALUES ('student_state', '学生-状态');
INSERT INTO `t_sys_dict_tp` VALUES ('sys_nm', '系统名称');
INSERT INTO `t_sys_dict_tp` VALUES ('sys_url', '跳转后台');
INSERT INTO `t_sys_dict_tp` VALUES ('timer_star_closed', '定时器开关');
INSERT INTO `t_sys_dict_tp` VALUES ('timer_state', '定时器状态');
INSERT INTO `t_sys_dict_tp` VALUES ('timer_type', '定时器类型');
INSERT INTO `t_sys_dict_tp` VALUES ('t_test_sex', '性别');

-- ----------------------------
-- Table structure for `t_sys_function_inf`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_function_inf`;
CREATE TABLE `t_sys_function_inf` (
  `FUNCTION_CD` varchar(36) NOT NULL COMMENT '主键',
  `FUNCTION_NM` varchar(50) NOT NULL COMMENT '菜单名称',
  `FUNCTION_URL` varchar(200) DEFAULT NULL COMMENT '请求地址',
  `MENU_ID` varchar(36) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`FUNCTION_CD`) USING BTREE,
  KEY `FK_Reference_3` (`MENU_ID`) USING BTREE,
  CONSTRAINT `t_sys_function_inf_ibfk_1` FOREIGN KEY (`MENU_ID`) REFERENCES `t_sys_menu_inf` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统功能表';

-- ----------------------------
-- Records of t_sys_function_inf
-- ----------------------------
INSERT INTO `t_sys_function_inf` VALUES ('0393d871-052f-4913-bc53-503944c48b29', '修改', 'menuInf/modify.do', '6b0783ec-1a19-40cb-8ee7-15d5bcb0b422');
INSERT INTO `t_sys_function_inf` VALUES ('0469f9ec-7c1e-4eca-9181-5e503cce32d4', '增加', 'menuInf/add.do', '6b0783ec-1a19-40cb-8ee7-15d5bcb0b422');
INSERT INTO `t_sys_function_inf` VALUES ('073dbafb-a910-429e-9d95-bd1b7f5bef60', '增加', 'roleInf/add.do', 'ddd9ee7c-0518-4465-a42e-db795159a5b5');
INSERT INTO `t_sys_function_inf` VALUES ('096a5b8f-b162-47e5-a23c-2f2cece5cb27', '查询', 'sysFunctionInf/datagrid.do', '724df8a4-db6e-410f-b712-58a57c3fe1d9');
INSERT INTO `t_sys_function_inf` VALUES ('153dd4f4-ff5c-4759-a002-7115c574a76b', '增加', 'dictTp/add.do', '720f5f7f-f3d8-4881-ba30-d07a29029aac');
INSERT INTO `t_sys_function_inf` VALUES ('1763c122-ad6b-43e4-9500-e7095979f176', '查询', 'dictCd/datagrid.do', 'ed4794fa-5e27-442e-84c1-11d373ef83b5');
INSERT INTO `t_sys_function_inf` VALUES ('17f64087-0737-4681-ba9e-5ef8775c4adb', '查询', 'insInf/datagrid.do', '37d317f7-6417-4ea7-81e1-cf9f499ee273');
INSERT INTO `t_sys_function_inf` VALUES ('1b74c033-fce9-4a25-9d17-d08956925677', '增加', 'insInf/add.do', '37d317f7-6417-4ea7-81e1-cf9f499ee273');
INSERT INTO `t_sys_function_inf` VALUES ('1f4b7f46-eb67-4738-ab87-5e82f282f962', '修改', 'operInf/modify.do', 'd307c58f-37c6-498a-aa33-b343e2370290');
INSERT INTO `t_sys_function_inf` VALUES ('239efc32-4791-47a9-a5ab-129ddf8705dd', '修改', 'roleInf/modify.do', 'ddd9ee7c-0518-4465-a42e-db795159a5b5');
INSERT INTO `t_sys_function_inf` VALUES ('267df9fc-5e46-4dd0-b434-c92affb54e86', '删除', 'sysFunctionInf/remove.do', '724df8a4-db6e-410f-b712-58a57c3fe1d9');
INSERT INTO `t_sys_function_inf` VALUES ('2a763fe4-e1f1-4cba-9b83-01af20bbf18e', '新增', 'quarz/add.do', 'e4be5554-96ae-4d5b-ae52-ed8154627796');
INSERT INTO `t_sys_function_inf` VALUES ('2ae5e576-22ec-47c3-9816-c6b4d45888ba', '修改', 'dictCd/modify.do', 'ed4794fa-5e27-442e-84c1-11d373ef83b5');
INSERT INTO `t_sys_function_inf` VALUES ('2cdd63c7-3558-42e7-841d-2f563ff69ca8', '查询', 'operInf/datagrid.do', 'd307c58f-37c6-498a-aa33-b343e2370290');
INSERT INTO `t_sys_function_inf` VALUES ('37b286ee-d841-4be1-bf15-ac379b64c256', '新增', 'sysFunctionInf/add.do', '724df8a4-db6e-410f-b712-58a57c3fe1d9');
INSERT INTO `t_sys_function_inf` VALUES ('3bbb800f-2097-420e-a00b-08d480ff4996', '修改', 'student/modify.do', 'bf64165c-65ae-49ad-a530-77b9593cf96e');
INSERT INTO `t_sys_function_inf` VALUES ('3d5d5be3-5080-49ea-a439-86a091e3acc6', '删除', 'insInf/remove.do', '37d317f7-6417-4ea7-81e1-cf9f499ee273');
INSERT INTO `t_sys_function_inf` VALUES ('48df6106-d3d2-4ed3-8cc4-d5716fa8f18f', '删除', 'student/remove.do', 'bf64165c-65ae-49ad-a530-77b9593cf96e');
INSERT INTO `t_sys_function_inf` VALUES ('4d91aafc-366e-471c-855f-e4637c9dfdb8', '修改', 'sysFunctionInf/modify.do', '724df8a4-db6e-410f-b712-58a57c3fe1d9');
INSERT INTO `t_sys_function_inf` VALUES ('55dce07d-6069-4d7f-85e9-30788ac4e568', '删除', 'menuInf/remove.do', '6b0783ec-1a19-40cb-8ee7-15d5bcb0b422');
INSERT INTO `t_sys_function_inf` VALUES ('587fc6a6-9836-47fb-ac7b-ebdf80369765', '新增', 'noInterceptor/add.do', '7420eb7d-fa91-4e3c-b732-425740b583ea');
INSERT INTO `t_sys_function_inf` VALUES ('6239127e-9be1-4d74-ab14-f0c0afaecd80', '查询', 'noInterceptor/datagrid.do', '7420eb7d-fa91-4e3c-b732-425740b583ea');
INSERT INTO `t_sys_function_inf` VALUES ('69bcfc3e-286c-4ede-9799-2548ea520921', '授权', 'roleFunction/add.do', 'ddd9ee7c-0518-4465-a42e-db795159a5b5');
INSERT INTO `t_sys_function_inf` VALUES ('6a4a4fac-9887-4423-be16-d0c36cb1d5d3', '修改', 'quarz/modify.do', 'e4be5554-96ae-4d5b-ae52-ed8154627796');
INSERT INTO `t_sys_function_inf` VALUES ('7342eac9-5a8d-46a6-9b14-a3567ba41c45', '删除', 'dictTp/remove.do', '720f5f7f-f3d8-4881-ba30-d07a29029aac');
INSERT INTO `t_sys_function_inf` VALUES ('796a830e-0677-42e3-8f67-1379f90cd6a4', '修改', 'noInterceptor/modify.do', '7420eb7d-fa91-4e3c-b732-425740b583ea');
INSERT INTO `t_sys_function_inf` VALUES ('81361182-d73a-4548-a4f0-0582f42ae999', '查询', 'quarz/datagrid.do', 'e4be5554-96ae-4d5b-ae52-ed8154627796');
INSERT INTO `t_sys_function_inf` VALUES ('81a09fef-8b00-45dc-b284-142c6e869765', '查询', 'menuInf/datagrid.do', '6b0783ec-1a19-40cb-8ee7-15d5bcb0b422');
INSERT INTO `t_sys_function_inf` VALUES ('85bb6d0a-d43b-4f15-a437-e2ffd5857ff8', '修改', 'insInf/modify.do', '37d317f7-6417-4ea7-81e1-cf9f499ee273');
INSERT INTO `t_sys_function_inf` VALUES ('88b64c61-d1f7-4015-b548-9cdda3a9e1fa', '删除', 'operInf/remove.do', 'd307c58f-37c6-498a-aa33-b343e2370290');
INSERT INTO `t_sys_function_inf` VALUES ('9dc107be-ac92-42f7-8ba0-5123b8af6e30', '删除', 'noInterceptor/remove.do', '7420eb7d-fa91-4e3c-b732-425740b583ea');
INSERT INTO `t_sys_function_inf` VALUES ('a10b43cf-c9dd-4f4d-8ea3-d2dd472855e7', '查询', 'student/datagrid.do', 'bf64165c-65ae-49ad-a530-77b9593cf96e');
INSERT INTO `t_sys_function_inf` VALUES ('a1c5fc1c-848c-4fda-97b5-675949e07ef1', '增加', 'operInf/add.do', 'd307c58f-37c6-498a-aa33-b343e2370290');
INSERT INTO `t_sys_function_inf` VALUES ('c06c6158-9664-45ff-afcb-b588bb12a6f0', '查询', 'dictTp/datagrid.do', '720f5f7f-f3d8-4881-ba30-d07a29029aac');
INSERT INTO `t_sys_function_inf` VALUES ('c3fe0bbd-b799-47c9-8406-810a04fc6d36', '修改', 'dictTp/modify.do', '720f5f7f-f3d8-4881-ba30-d07a29029aac');
INSERT INTO `t_sys_function_inf` VALUES ('c48ad87e-097e-4936-98e2-a1959c277cd0', '查询', 'roleInf/datagrid.do', 'ddd9ee7c-0518-4465-a42e-db795159a5b5');
INSERT INTO `t_sys_function_inf` VALUES ('c5946918-dad5-4088-9cc4-c5eca71f8fdc', '查询', 'sysLog/datagrid.do', '3df4bb66-3e06-4f81-a020-7bc98e4bc5ac');
INSERT INTO `t_sys_function_inf` VALUES ('cff79ae9-f8f0-406d-8a6e-be0099df3df2', '删除', 'roleInf/remove.do', 'ddd9ee7c-0518-4465-a42e-db795159a5b5');
INSERT INTO `t_sys_function_inf` VALUES ('d8c2edd7-d5d8-4cba-82e1-ba2a3c3cdc80', '删除', 'quarz/remove.do', 'e4be5554-96ae-4d5b-ae52-ed8154627796');
INSERT INTO `t_sys_function_inf` VALUES ('dd966007-afad-4afa-bcbd-7c071ed3cb73', '删除', 'dictCd/remove.do', 'ed4794fa-5e27-442e-84c1-11d373ef83b5');
INSERT INTO `t_sys_function_inf` VALUES ('e0d38e1f-cdd3-4a0e-b7ea-3c525ac52101', '新增', 'student/add.do', 'bf64165c-65ae-49ad-a530-77b9593cf96e');
INSERT INTO `t_sys_function_inf` VALUES ('e29d1323-1ea4-4298-9ce8-9df239424023', '密码重置', 'operInf/redoPwd.do', 'd307c58f-37c6-498a-aa33-b343e2370290');
INSERT INTO `t_sys_function_inf` VALUES ('e91b055d-4332-4b36-9a46-8a8ad9079162', '增加', 'dictCd/add.do', 'ed4794fa-5e27-442e-84c1-11d373ef83b5');

-- ----------------------------
-- Table structure for `t_sys_ins_inf`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_ins_inf`;
CREATE TABLE `t_sys_ins_inf` (
  `INS_CD` varchar(10) NOT NULL COMMENT '机构编号',
  `INS_DETAIL` varchar(200) DEFAULT NULL COMMENT '机构详情',
  `INS_LVL` varchar(1) NOT NULL COMMENT '机构级别',
  `INS_NM` varchar(50) NOT NULL COMMENT '机构名称',
  `PARENT_INS_CD` varchar(36) DEFAULT NULL COMMENT '上一级机构编号',
  `UP_TWO_INS` varchar(36) DEFAULT NULL COMMENT '上二级机构编号',
  `UP_THREE_INS` varchar(36) DEFAULT NULL COMMENT '上三级机构编号',
  `UUID` varchar(36) NOT NULL COMMENT '主键',
  `INS_TP` varchar(2) DEFAULT NULL COMMENT '机构类型',
  `INS_OPER` varchar(50) DEFAULT NULL COMMENT '机构负责人姓名',
  `INS_ADDRES` varchar(100) DEFAULT NULL COMMENT '机构地址',
  `INS_PHONE` varchar(20) DEFAULT NULL COMMENT '机构联系电话',
  `MARK` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`UUID`) USING BTREE,
  KEY `FK_Reference_4` (`PARENT_INS_CD`) USING BTREE,
  CONSTRAINT `t_sys_ins_inf_ibfk_1` FOREIGN KEY (`PARENT_INS_CD`) REFERENCES `t_sys_ins_inf` (`UUID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='机构信息表';

-- ----------------------------
-- Records of t_sys_ins_inf
-- ----------------------------
INSERT INTO `t_sys_ins_inf` VALUES ('010000', ',1,', '0', '集团公司', null, null, null, '1', '0', 'admin', '福州', '13599097196', '备注');
INSERT INTO `t_sys_ins_inf` VALUES ('000002', ',1ecaab80-97b5-4d32-bdb2-9ba3bdc195ff,f0765ca9-9eba-4c78-a7bb-79a0989ec1ba,1,', '2', '市公司', 'f0765ca9-9eba-4c78-a7bb-79a0989ec1ba', '1', null, '1ecaab80-97b5-4d32-bdb2-9ba3bdc195ff', '0', '', '', '', '');
INSERT INTO `t_sys_ins_inf` VALUES ('000003', null, '3', '县区网点', 'f0765ca9-9eba-4c78-a7bb-79a0989ec1ba', null, null, '8b3bfb5e-4275-4972-909f-40c72d0fb5a3', '3', '', '', '', '');
INSERT INTO `t_sys_ins_inf` VALUES ('00001', ',a2c44030-5c89-48f6-8768-6a53b54dea30,1ecaab80-97b5-4d32-bdb2-9ba3bdc195ff,f0765ca9-9eba-4c78-a7bb-79a0989ec1ba,1,', '3', '县级网点', '1ecaab80-97b5-4d32-bdb2-9ba3bdc195ff', 'f0765ca9-9eba-4c78-a7bb-79a0989ec1ba', '1', 'a2c44030-5c89-48f6-8768-6a53b54dea30', '3', '', '', '', '');
INSERT INTO `t_sys_ins_inf` VALUES ('010001', ',f0765ca9-9eba-4c78-a7bb-79a0989ec1ba,1,', '1', '省公司', '1', null, null, 'f0765ca9-9eba-4c78-a7bb-79a0989ec1ba', '0', '黄先生', '福州', '13599098765', '已激活');

-- ----------------------------
-- Table structure for `t_sys_log`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_log`;
CREATE TABLE `t_sys_log` (
  `UUID` varchar(72) NOT NULL,
  `COMPARE_INF` varchar(4000) DEFAULT NULL,
  `OPER_DESC` varchar(4000) DEFAULT NULL,
  `OPER_DT` varchar(28) DEFAULT NULL,
  `OPER_IP` varchar(200) DEFAULT NULL,
  `OPER_METHOD` varchar(120) DEFAULT NULL,
  `OPER_MODULE` varchar(120) DEFAULT NULL,
  `OPER_USR_ID` varchar(60) DEFAULT NULL,
  `RESULT_MSG` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`UUID`) USING BTREE,
  UNIQUE KEY `UUID` (`UUID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of t_sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sys_menu_inf`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_menu_inf`;
CREATE TABLE `t_sys_menu_inf` (
  `ID` varchar(36) NOT NULL COMMENT 'ID',
  `ICON_CLS` varchar(20) DEFAULT NULL COMMENT 'ICO图标',
  `MENU_SORT` varchar(10) NOT NULL COMMENT '排序',
  `MENU_URL` varchar(200) DEFAULT NULL COMMENT 'URL',
  `TEXT` varchar(100) NOT NULL COMMENT '菜单名称',
  `PRAENT_ID` varchar(36) DEFAULT NULL COMMENT '上一级菜单id',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `FK_Reference_2` (`PRAENT_ID`) USING BTREE,
  CONSTRAINT `t_sys_menu_inf_ibfk_1` FOREIGN KEY (`PRAENT_ID`) REFERENCES `t_sys_menu_inf` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统菜单表';

-- ----------------------------
-- Records of t_sys_menu_inf
-- ----------------------------
INSERT INTO `t_sys_menu_inf` VALUES ('0928b643-2953-4223-96ad-ef796841849f', 'fa-cubes', '001', '', '首页', null);
INSERT INTO `t_sys_menu_inf` VALUES ('0cc33715-9084-4d12-902c-6964fdefc268', 'fa-cubes', '001', '', '业务管理', '0928b643-2953-4223-96ad-ef796841849f');
INSERT INTO `t_sys_menu_inf` VALUES ('24a407ae-3440-492b-a990-8d3b6db6f94e', 'fa-cubes', '013', '', '系统管理', '0928b643-2953-4223-96ad-ef796841849f');
INSERT INTO `t_sys_menu_inf` VALUES ('37d317f7-6417-4ea7-81e1-cf9f499ee273', 'fa-cubes', '006', 'sys/InsInf/InsInfList.jsp', '机构信息', '24a407ae-3440-492b-a990-8d3b6db6f94e');
INSERT INTO `t_sys_menu_inf` VALUES ('3df4bb66-3e06-4f81-a020-7bc98e4bc5ac', 'fa-cubes', '009', 'sys/SysLog/SysLogList.jsp', '日志管理', '24a407ae-3440-492b-a990-8d3b6db6f94e');
INSERT INTO `t_sys_menu_inf` VALUES ('6b0783ec-1a19-40cb-8ee7-15d5bcb0b422', 'fa-cubes', '001', 'sys/MenuInfo/MenuInfoList.jsp', '菜单管理', '24a407ae-3440-492b-a990-8d3b6db6f94e');
INSERT INTO `t_sys_menu_inf` VALUES ('6ea54859-479c-4eac-8bf2-daa6bfca6aa4', 'fa-cubes', '010', 'sys/CodeGenerator/CodeGenerator.jsp', '代码构建', '24a407ae-3440-492b-a990-8d3b6db6f94e');
INSERT INTO `t_sys_menu_inf` VALUES ('720f5f7f-f3d8-4881-ba30-d07a29029aac', 'fa-cubes', '004', 'sys/DictTp/DictTpList.jsp', '字典类型', '24a407ae-3440-492b-a990-8d3b6db6f94e');
INSERT INTO `t_sys_menu_inf` VALUES ('724df8a4-db6e-410f-b712-58a57c3fe1d9', 'fa-cubes', '003', 'sys/SysFn/SysFunctionInfList.jsp', '系统功能', '24a407ae-3440-492b-a990-8d3b6db6f94e');
INSERT INTO `t_sys_menu_inf` VALUES ('7420eb7d-fa91-4e3c-b732-425740b583ea', 'fa-cubes', '007', 'sys/NoInterceptor/NoInterceptorList.jsp', 'URL拦截', '24a407ae-3440-492b-a990-8d3b6db6f94e');
INSERT INTO `t_sys_menu_inf` VALUES ('bf64165c-65ae-49ad-a530-77b9593cf96e', 'fa-cubes', '001', 'app/Student/StudentList.jsp', '学生管理', '0cc33715-9084-4d12-902c-6964fdefc268');
INSERT INTO `t_sys_menu_inf` VALUES ('d307c58f-37c6-498a-aa33-b343e2370290', 'fa-cubes', '008', 'sys/OperInf/OperInfList.jsp', '用户管理', '24a407ae-3440-492b-a990-8d3b6db6f94e');
INSERT INTO `t_sys_menu_inf` VALUES ('ddd9ee7c-0518-4465-a42e-db795159a5b5', 'fa-cubes', '002', 'sys/RoleInf/RoleInfList.jsp', '角色管理', '24a407ae-3440-492b-a990-8d3b6db6f94e');
INSERT INTO `t_sys_menu_inf` VALUES ('e4be5554-96ae-4d5b-ae52-ed8154627796', 'fa-cubes', '012', 'sys/Quarz/QuarzList.jsp', '定时任务', '24a407ae-3440-492b-a990-8d3b6db6f94e');
INSERT INTO `t_sys_menu_inf` VALUES ('ed4794fa-5e27-442e-84c1-11d373ef83b5', 'fa-cubes', '005', 'sys/DictCd/DictCdList.jsp', '数据字典', '24a407ae-3440-492b-a990-8d3b6db6f94e');

-- ----------------------------
-- Table structure for `t_sys_nointerceptor`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_nointerceptor`;
CREATE TABLE `t_sys_nointerceptor` (
  `UUID` varchar(36) NOT NULL COMMENT 'UUID',
  `FUNCTION_URL` varchar(200) NOT NULL COMMENT '不需要拦截的URL',
  `NOTE` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`UUID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='不需要拦截的URL配置表';

-- ----------------------------
-- Records of t_sys_nointerceptor
-- ----------------------------
INSERT INTO `t_sys_nointerceptor` VALUES ('50d2551a-4add-4d78-b0c9-b05a284129ce', 'operInf/logout.do', '用户退出不需要拦截');
INSERT INTO `t_sys_nointerceptor` VALUES ('83385c35-8cb3-4a6a-9eb1-74761fbf5e71', 'operInf/login.do', '返回到用户登录界面不需要拦截');

-- ----------------------------
-- Table structure for `t_sys_oper_inf`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_oper_inf`;
CREATE TABLE `t_sys_oper_inf` (
  `OPER_CD` varchar(30) NOT NULL COMMENT '用户名',
  `EMAIL` varchar(100) DEFAULT NULL COMMENT 'EMAIL',
  `OPER_NM` varchar(30) NOT NULL COMMENT '用户真实姓名',
  `OPER_PWD` varchar(100) NOT NULL COMMENT '用户密码',
  `OPER_ST` varchar(1) NOT NULL COMMENT '用户状态',
  `REC_CRT_TS` varchar(14) DEFAULT NULL COMMENT '创建时间',
  `REC_UPD_TS` varchar(14) DEFAULT NULL COMMENT '修改时间',
  `TELEPHONE` varchar(12) DEFAULT NULL COMMENT '手机号',
  `INS_CD` varchar(36) DEFAULT NULL COMMENT '所属机构',
  `JOB` varchar(2) NOT NULL COMMENT '岗位',
  PRIMARY KEY (`OPER_CD`) USING BTREE,
  KEY `FK_Reference_5` (`INS_CD`) USING BTREE,
  CONSTRAINT `t_sys_oper_inf_ibfk_1` FOREIGN KEY (`INS_CD`) REFERENCES `t_sys_ins_inf` (`UUID`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统用户表';

-- ----------------------------
-- Records of t_sys_oper_inf
-- ----------------------------
INSERT INTO `t_sys_oper_inf` VALUES ('admin', '123@qq.com', 'admin', 'E00CF25AD42683B3DF678C61F42C6BDA', '0', '20150314221336', '20171219173417', '', '1', '0');
INSERT INTO `t_sys_oper_inf` VALUES ('test', '', '测试员', 'CC0E06322B8BB0BA483C8EC0295237E3', '0', '20171215182252', null, '', '1', '0');

-- ----------------------------
-- Table structure for `t_sys_quarz`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_quarz`;
CREATE TABLE `t_sys_quarz` (
  `NID` varchar(36) NOT NULL COMMENT '主键',
  `JOB_NAME` varchar(50) DEFAULT NULL COMMENT '定时器名称',
  `JOB_GROUP` varchar(50) DEFAULT NULL COMMENT '所属组',
  `CLASS_PATH` varchar(50) DEFAULT NULL COMMENT '类路径',
  `CRON_STR` varchar(50) DEFAULT NULL COMMENT '执行方法名称',
  `STATE` varchar(1) DEFAULT NULL COMMENT '状态',
  `MARK` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`NID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='调度任务表';

-- ----------------------------
-- Records of t_sys_quarz
-- ----------------------------
INSERT INTO `t_sys_quarz` VALUES ('f69e9705-4260-4799-8c77-4d56f58253db', '测试定时器', '1', 'com.gt.quartz.TestQuartz', '0 0/5 * * * ?', '0', '每隔5分运行一次');

-- ----------------------------
-- Table structure for `t_sys_role_function`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_function`;
CREATE TABLE `t_sys_role_function` (
  `UUID` varchar(36) NOT NULL COMMENT 'UUID',
  `ROLE_CD` varchar(36) NOT NULL COMMENT '角色ID',
  `FUNCTION_CD` varchar(36) NOT NULL COMMENT '功能代码ID',
  PRIMARY KEY (`UUID`) USING BTREE,
  KEY `FK_Reference_7` (`FUNCTION_CD`) USING BTREE,
  KEY `FK_Reference_8` (`ROLE_CD`) USING BTREE,
  CONSTRAINT `t_sys_role_function_ibfk_1` FOREIGN KEY (`FUNCTION_CD`) REFERENCES `t_sys_function_inf` (`FUNCTION_CD`) ON DELETE CASCADE,
  CONSTRAINT `t_sys_role_function_ibfk_2` FOREIGN KEY (`ROLE_CD`) REFERENCES `t_sys_role_inf` (`ROLE_CD`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色功能表';

-- ----------------------------
-- Records of t_sys_role_function
-- ----------------------------
INSERT INTO `t_sys_role_function` VALUES ('02f0156c-8af0-477e-912a-1e8339f45543', '50377ab8-b78b-4a2a-b0b1-cedf658ab945', 'c48ad87e-097e-4936-98e2-a1959c277cd0');
INSERT INTO `t_sys_role_function` VALUES ('0c2fdabf-a341-4eb2-a75b-a5eb9c31bb86', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', '6a4a4fac-9887-4423-be16-d0c36cb1d5d3');
INSERT INTO `t_sys_role_function` VALUES ('0fcda14d-72b0-4a9b-9c15-6ae6e56fa198', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', '587fc6a6-9836-47fb-ac7b-ebdf80369765');
INSERT INTO `t_sys_role_function` VALUES ('0feb783f-ccbd-4da7-956c-37cfb2a0a264', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', 'cff79ae9-f8f0-406d-8a6e-be0099df3df2');
INSERT INTO `t_sys_role_function` VALUES ('19257e40-22df-47d4-84f4-dd98b84b16ff', '50377ab8-b78b-4a2a-b0b1-cedf658ab945', '81a09fef-8b00-45dc-b284-142c6e869765');
INSERT INTO `t_sys_role_function` VALUES ('19538f62-3d73-4a09-80f1-920dbc3de76f', '50377ab8-b78b-4a2a-b0b1-cedf658ab945', '096a5b8f-b162-47e5-a23c-2f2cece5cb27');
INSERT INTO `t_sys_role_function` VALUES ('2001ad6c-930a-41f6-842d-0e3dfae25bc0', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', '1f4b7f46-eb67-4738-ab87-5e82f282f962');
INSERT INTO `t_sys_role_function` VALUES ('24b2c7f4-6499-4d87-8d0a-c4620906e19d', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', '2a763fe4-e1f1-4cba-9b83-01af20bbf18e');
INSERT INTO `t_sys_role_function` VALUES ('25b6416c-fb9e-4d07-93e2-955af452ffc6', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', '0469f9ec-7c1e-4eca-9181-5e503cce32d4');
INSERT INTO `t_sys_role_function` VALUES ('285c7267-cd5b-4c0c-9117-3b787bbadca1', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', 'c06c6158-9664-45ff-afcb-b588bb12a6f0');
INSERT INTO `t_sys_role_function` VALUES ('2a6e580d-51d9-4d67-b3d8-65123361cda2', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', '153dd4f4-ff5c-4759-a002-7115c574a76b');
INSERT INTO `t_sys_role_function` VALUES ('2c904580-608b-478e-a659-eeb58b4f195a', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', 'dd966007-afad-4afa-bcbd-7c071ed3cb73');
INSERT INTO `t_sys_role_function` VALUES ('2d224618-7eb0-4812-a40a-82af0727bf5d', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', '1b74c033-fce9-4a25-9d17-d08956925677');
INSERT INTO `t_sys_role_function` VALUES ('2f33a665-6ac8-43ab-883f-40cd73059e12', '50377ab8-b78b-4a2a-b0b1-cedf658ab945', 'c5946918-dad5-4088-9cc4-c5eca71f8fdc');
INSERT INTO `t_sys_role_function` VALUES ('36801e88-bee2-4170-a85f-652998edb200', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', '239efc32-4791-47a9-a5ab-129ddf8705dd');
INSERT INTO `t_sys_role_function` VALUES ('3d0f7cda-3bd1-46fd-bb25-d4584e3be8a0', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', 'c48ad87e-097e-4936-98e2-a1959c277cd0');
INSERT INTO `t_sys_role_function` VALUES ('4af6d93f-4576-40a5-9606-737713bc8967', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', '2cdd63c7-3558-42e7-841d-2f563ff69ca8');
INSERT INTO `t_sys_role_function` VALUES ('4c58407a-ae97-494a-8218-413620f2e5b0', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', '0393d871-052f-4913-bc53-503944c48b29');
INSERT INTO `t_sys_role_function` VALUES ('4e050c5a-0345-4757-a19d-75e1bb8dca86', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', 'c5946918-dad5-4088-9cc4-c5eca71f8fdc');
INSERT INTO `t_sys_role_function` VALUES ('507fab7a-ed74-4571-b47f-8aa3baf1f783', '50377ab8-b78b-4a2a-b0b1-cedf658ab945', 'c06c6158-9664-45ff-afcb-b588bb12a6f0');
INSERT INTO `t_sys_role_function` VALUES ('51608d57-ea75-40b4-984a-58f8757b11f9', '50377ab8-b78b-4a2a-b0b1-cedf658ab945', '6239127e-9be1-4d74-ab14-f0c0afaecd80');
INSERT INTO `t_sys_role_function` VALUES ('57feafdf-78ab-4f5f-bef4-02ec82fefbd8', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', '48df6106-d3d2-4ed3-8cc4-d5716fa8f18f');
INSERT INTO `t_sys_role_function` VALUES ('5aab40d2-fbd5-4702-882e-9c6a767cc462', '50377ab8-b78b-4a2a-b0b1-cedf658ab945', '48df6106-d3d2-4ed3-8cc4-d5716fa8f18f');
INSERT INTO `t_sys_role_function` VALUES ('5c9b9dc5-ab43-4825-a963-8c4fec8409c6', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', '88b64c61-d1f7-4015-b548-9cdda3a9e1fa');
INSERT INTO `t_sys_role_function` VALUES ('5e4c1494-bf4d-4e34-b25f-98b3d44a95c1', '50377ab8-b78b-4a2a-b0b1-cedf658ab945', 'a10b43cf-c9dd-4f4d-8ea3-d2dd472855e7');
INSERT INTO `t_sys_role_function` VALUES ('667685ba-835b-4947-bafe-bee01ae0c52c', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', '3bbb800f-2097-420e-a00b-08d480ff4996');
INSERT INTO `t_sys_role_function` VALUES ('66930bf5-5c1f-49fb-a966-823326fa496d', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', 'a10b43cf-c9dd-4f4d-8ea3-d2dd472855e7');
INSERT INTO `t_sys_role_function` VALUES ('6b90508b-bd1b-4843-8a92-6c690b25172b', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', '85bb6d0a-d43b-4f15-a437-e2ffd5857ff8');
INSERT INTO `t_sys_role_function` VALUES ('727dbb61-6fde-4bc5-bb82-a7bbc392c7e2', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', '81361182-d73a-4548-a4f0-0582f42ae999');
INSERT INTO `t_sys_role_function` VALUES ('7432157e-6869-42db-aa15-afdefc6d7989', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', 'e0d38e1f-cdd3-4a0e-b7ea-3c525ac52101');
INSERT INTO `t_sys_role_function` VALUES ('74eb7787-8059-41b1-a83c-412c15701258', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', '073dbafb-a910-429e-9d95-bd1b7f5bef60');
INSERT INTO `t_sys_role_function` VALUES ('760bbfc4-d64e-4b01-a9c5-6d977701c461', '50377ab8-b78b-4a2a-b0b1-cedf658ab945', '1763c122-ad6b-43e4-9500-e7095979f176');
INSERT INTO `t_sys_role_function` VALUES ('8349fb3b-6450-45b0-a0f2-4f3d371d0993', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', 'd8c2edd7-d5d8-4cba-82e1-ba2a3c3cdc80');
INSERT INTO `t_sys_role_function` VALUES ('88a46f3f-8adb-47cb-b99a-4c1916cad1d6', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', 'e29d1323-1ea4-4298-9ce8-9df239424023');
INSERT INTO `t_sys_role_function` VALUES ('8b47b9c1-8339-42db-afe9-3d7b80001d97', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', '6239127e-9be1-4d74-ab14-f0c0afaecd80');
INSERT INTO `t_sys_role_function` VALUES ('8dc6bfb2-23e0-40bd-850a-5cba45c3942f', '50377ab8-b78b-4a2a-b0b1-cedf658ab945', '3d5d5be3-5080-49ea-a439-86a091e3acc6');
INSERT INTO `t_sys_role_function` VALUES ('92637270-582e-4e28-b9cb-1e2ca8ca8de4', '50377ab8-b78b-4a2a-b0b1-cedf658ab945', '81361182-d73a-4548-a4f0-0582f42ae999');
INSERT INTO `t_sys_role_function` VALUES ('96e160af-fe9e-4723-a12b-78744b5925d5', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', '2ae5e576-22ec-47c3-9816-c6b4d45888ba');
INSERT INTO `t_sys_role_function` VALUES ('9824eaa9-ef84-494d-b4ce-3bc44cada4f7', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', '1763c122-ad6b-43e4-9500-e7095979f176');
INSERT INTO `t_sys_role_function` VALUES ('a1271a83-5dfa-4c91-bfb5-6d327b8732b5', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', '796a830e-0677-42e3-8f67-1379f90cd6a4');
INSERT INTO `t_sys_role_function` VALUES ('a54c8589-457b-485e-8734-ff93d958701b', '50377ab8-b78b-4a2a-b0b1-cedf658ab945', '3bbb800f-2097-420e-a00b-08d480ff4996');
INSERT INTO `t_sys_role_function` VALUES ('a7b84d00-8cec-4f35-ab67-d00f939406f0', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', '55dce07d-6069-4d7f-85e9-30788ac4e568');
INSERT INTO `t_sys_role_function` VALUES ('ac37924d-000d-4acf-abe4-582823a26d10', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', '096a5b8f-b162-47e5-a23c-2f2cece5cb27');
INSERT INTO `t_sys_role_function` VALUES ('b01aba17-45ed-4f87-bb3e-199f99443bec', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', 'e91b055d-4332-4b36-9a46-8a8ad9079162');
INSERT INTO `t_sys_role_function` VALUES ('b5488730-14db-49b9-83db-863a72f14a06', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', 'a1c5fc1c-848c-4fda-97b5-675949e07ef1');
INSERT INTO `t_sys_role_function` VALUES ('b6daacfd-ca08-4f84-bea2-86a151625cf8', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', '267df9fc-5e46-4dd0-b434-c92affb54e86');
INSERT INTO `t_sys_role_function` VALUES ('bd5e2cdc-2333-4dba-9d84-7adbbd5d36eb', '50377ab8-b78b-4a2a-b0b1-cedf658ab945', '2cdd63c7-3558-42e7-841d-2f563ff69ca8');
INSERT INTO `t_sys_role_function` VALUES ('c4d457b4-ded5-448f-a321-337845e150f2', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', '17f64087-0737-4681-ba9e-5ef8775c4adb');
INSERT INTO `t_sys_role_function` VALUES ('c7dcbb29-61f7-419c-9215-9e4995c24df9', '50377ab8-b78b-4a2a-b0b1-cedf658ab945', '17f64087-0737-4681-ba9e-5ef8775c4adb');
INSERT INTO `t_sys_role_function` VALUES ('cd163f88-572e-4d48-848d-5912aac3cd29', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', '69bcfc3e-286c-4ede-9799-2548ea520921');
INSERT INTO `t_sys_role_function` VALUES ('cddf3c95-cef0-4489-881e-d5ecd860905e', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', '4d91aafc-366e-471c-855f-e4637c9dfdb8');
INSERT INTO `t_sys_role_function` VALUES ('dbca7d91-afea-45fa-adf8-16ba6d690b57', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', '37b286ee-d841-4be1-bf15-ac379b64c256');
INSERT INTO `t_sys_role_function` VALUES ('e8f33924-6ee3-4dc9-b8d3-4ac2a267fbb2', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', '9dc107be-ac92-42f7-8ba0-5123b8af6e30');
INSERT INTO `t_sys_role_function` VALUES ('f1f2d18d-e8d9-4883-8c7d-89666a6bd52c', '50377ab8-b78b-4a2a-b0b1-cedf658ab945', 'e0d38e1f-cdd3-4a0e-b7ea-3c525ac52101');
INSERT INTO `t_sys_role_function` VALUES ('f471a983-cede-4ccb-95bb-5e8fc304507a', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', 'c3fe0bbd-b799-47c9-8406-810a04fc6d36');
INSERT INTO `t_sys_role_function` VALUES ('f56429b9-a276-4231-bba4-af849bc1600b', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', '7342eac9-5a8d-46a6-9b14-a3567ba41c45');
INSERT INTO `t_sys_role_function` VALUES ('f865d447-44d0-43a8-acf6-3b33dc691c01', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', '81a09fef-8b00-45dc-b284-142c6e869765');
INSERT INTO `t_sys_role_function` VALUES ('fffcba3b-3956-427e-8c68-a5c341b255ff', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', '3d5d5be3-5080-49ea-a439-86a091e3acc6');

-- ----------------------------
-- Table structure for `t_sys_role_inf`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_inf`;
CREATE TABLE `t_sys_role_inf` (
  `ROLE_CD` varchar(36) NOT NULL COMMENT '主键',
  `REMARK` varchar(200) DEFAULT NULL COMMENT '描述',
  `ROLE_NM` varchar(30) NOT NULL COMMENT '角色名称',
  `JUMP_URL` varchar(200) DEFAULT NULL COMMENT '页面跳转',
  `URL_NM` varchar(50) DEFAULT NULL COMMENT '页面名称',
  `WRITE_OPER_CD` varchar(36) DEFAULT NULL COMMENT '录入人',
  PRIMARY KEY (`ROLE_CD`) USING BTREE,
  KEY `FK398ACBD5F2B39A73` (`WRITE_OPER_CD`) USING BTREE,
  CONSTRAINT `t_sys_role_inf_ibfk_1` FOREIGN KEY (`WRITE_OPER_CD`) REFERENCES `t_sys_oper_inf` (`OPER_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户角色表';

-- ----------------------------
-- Records of t_sys_role_inf
-- ----------------------------
INSERT INTO `t_sys_role_inf` VALUES ('50377ab8-b78b-4a2a-b0b1-cedf658ab945', '', '测试员', null, null, 'admin');
INSERT INTO `t_sys_role_inf` VALUES ('e61ce89f-3e0e-4d33-acb8-16e623d86966', '只能超级管理使用', '超级管理员', null, null, 'admin');

-- ----------------------------
-- Table structure for `t_sys_role_oper`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_oper`;
CREATE TABLE `t_sys_role_oper` (
  `UUID` varchar(36) NOT NULL COMMENT 'UUID',
  `ROLE_CD` varchar(36) DEFAULT NULL COMMENT '人员编号',
  `OPER_CD` varchar(36) DEFAULT NULL COMMENT '角色编号',
  PRIMARY KEY (`UUID`) USING BTREE,
  KEY `FK_Reference_10` (`OPER_CD`) USING BTREE,
  KEY `FK_Reference_9` (`ROLE_CD`) USING BTREE,
  CONSTRAINT `t_sys_role_oper_ibfk_1` FOREIGN KEY (`OPER_CD`) REFERENCES `t_sys_oper_inf` (`OPER_CD`) ON DELETE CASCADE,
  CONSTRAINT `t_sys_role_oper_ibfk_2` FOREIGN KEY (`ROLE_CD`) REFERENCES `t_sys_role_inf` (`ROLE_CD`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色人员表';

-- ----------------------------
-- Records of t_sys_role_oper
-- ----------------------------
INSERT INTO `t_sys_role_oper` VALUES ('035af8da-c7fb-4f56-a773-b2504ae7083f', 'e61ce89f-3e0e-4d33-acb8-16e623d86966', 'admin');
INSERT INTO `t_sys_role_oper` VALUES ('03b9886c-2118-46dc-a729-8b73a11552d1', '50377ab8-b78b-4a2a-b0b1-cedf658ab945', 'test');
INSERT INTO `t_sys_role_oper` VALUES ('9682364a-2ec3-4653-8b92-7ed2cea4f15d', '50377ab8-b78b-4a2a-b0b1-cedf658ab945', 'admin');

-- ----------------------------
-- Table structure for `t_sys_timer`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_timer`;
CREATE TABLE `t_sys_timer` (
  `NID` decimal(8,0) NOT NULL COMMENT '主键',
  `TYPE` varchar(1) DEFAULT NULL COMMENT '定时器类型',
  `TIMER_NM` varchar(50) DEFAULT NULL COMMENT '定时器名称',
  `STAR_CLOSED` varchar(1) DEFAULT NULL COMMENT '是否开启',
  `CLASS_PATH` varchar(50) DEFAULT NULL COMMENT '类路径',
  `METHOD_NM` varchar(50) DEFAULT NULL COMMENT '执行方法名称',
  `DELAY` varchar(10) DEFAULT NULL COMMENT '延迟时间',
  `TASK_TIME` varchar(50) DEFAULT NULL COMMENT '执行时间',
  `PERIOD` varchar(10) DEFAULT NULL COMMENT '执行间隔时间',
  `STATE` varchar(1) DEFAULT NULL COMMENT '状态',
  `MARK` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`NID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='定时器表';

-- ----------------------------
-- Records of t_sys_timer
-- ----------------------------
