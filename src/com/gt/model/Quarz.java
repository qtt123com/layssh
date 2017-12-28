package com.gt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.gt.pageModel.BasePageForLayUI;

/**
 * @功能说明：任务调度
 * @作者： herun
 * @创建日期：2015-11-18
 * @版本号：V1.0
 */
@Entity
@Table(name = "T_SYS_QUARZ", schema = "")
public class Quarz extends BasePageForLayUI implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String nid;// 主键

	private String jobName;// 定时器名称

	private String jobGroup;// 所属组

	private String classPath;// 类路径

	private String cronStr;// 执行方法名称

	private String state;// 状态

	private String mark;// 备注

	private String nidStr;// ID

	private String stateNm;// 状态-名称
	
	private String jobGroupNm;// 所属组-名称
	
	

	// 构造方法
	public Quarz() {
		
	}
	
	@Transient
	public String getJobGroupNm() {
		return jobGroupNm;
	}

	public void setJobGroupNm(String jobGroupNm) {
		this.jobGroupNm = jobGroupNm;
	}

	@Transient
	public String getStateNm() {
		return stateNm;
	}

	public void setStateNm(String stateNm) {
		this.stateNm = stateNm;
	}

	@Transient
	public String getNidStr() {
		return nidStr;
	}

	public void setNidStr(String nidStr) {
		this.nidStr = nidStr;
	}

	@Id
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	//@SequenceGenerator(name = "generator", allocationSize = 1, sequenceName = "T_SYS_QUARZ_SEQUENCE")
	@Column(name = "NID", nullable = false, precision = 22, scale = 0)
	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	@Column(name = "JOB_NAME", length = 50)
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	@Column(name = "JOB_GROUP", length = 50)
	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	@Column(name = "CLASS_PATH", length = 50)
	public String getClassPath() {
		return classPath;
	}

	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}

	@Column(name = "CRON_STR", length = 50)
	public String getCronStr() {
		return cronStr;
	}

	public void setCronStr(String cronStr) {
		this.cronStr = cronStr;
	}

	@Column(name = "STATE", length = 1)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "MARK", length = 50)
	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

}
