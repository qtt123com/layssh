package com.gt.utils;

/**
 * 
 * @功能说明：常量设置
 * @作者： herun
 * @创建日期：2014-2-27
 * @版本号：V1.0
 */
public class Contans {

	public static final String SESSION_BEAN = "sessionBean";

	// 编码格式
	public static final String CHARACTER_GBK = "GBK";

	// 机构级别
	public static final String INS_LVL = "ins_lvl";
	public static final String INS_LVL_TOP = "0";// 最高层机构级别
	public static final String INS_LVL_TWO = "1";// 第二级
	public static final String INS_LVL_THREE = "2";// 第三级
	public static final String INS_LVL_FOUR = "3";// 第四级

	// 系统默认密码
	public static final String DEFAULT_PASSWORD = "888888";

	// 系统用户状态
	public static final String OPER_ST = "oper_st";
	public static final String OPER_ST_NO = "1";// 禁用
	public static final String OPER_ST_CANCEL = "2";// 注销
	// 机构类型
	public static final String INS_TP = "ins_tp";

	// 岗位类型
	public static final String JOB_TP = "job_tp";

	// 入库类型
	public static final String IN_OPERATETP = "in_operateTp";

	// 出库库类型
	public static final String OUT_OPERATETP = "out_operateTp";

	// 超级管理员
	public static final String ADMIN = "admin";

	// 字典类型
	public static final String ADMINOPER = "adminOper";

	// 是否删除
	public static final String[] ISDEL = { "0", "1" };// 是否删除,0存在,1删除

	// 机构类型
	public static final String INSTP0 = "0";// 管理机构

	// 定时器类型
	public static final String TIMER_TYPE = "timer_type";

	// 定时器开关
	public static final String TIMER_STAR_CLOSED = "timer_star_closed";

	// 定时器状态
	public static final String TIMER_STATE = "timer_state";

	// 调度器状态
	public static final String QUARZ_STATE = "quarz_state";
	public static final String QUARZ_STATE_ARRY[] = { "0", "1" };// 0运行中,1暂停

	// 调度器所属组
	public static final String QUARZ_JOB_GROUP = "quarz_job_group";

	// 数据字典-性别类型
	public static final String T_TEST_SEX = "t_test_sex";
	
	// ////////////////////////////以上为系统设置所需要/////////////////////////////////////////////////////


}
