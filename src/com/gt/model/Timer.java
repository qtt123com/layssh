package com.gt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.gt.pageModel.BasePageForLayUI;

/**
 * @功能说明：定时任务
 * @作者： herun
 * @创建日期：2015-11-13
 * @版本号：V1.0
 */
@Entity
@Table(name = "T_SYS_TIMER", schema = "")
public class Timer extends BasePageForLayUI implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private Long nid;// 主键

	private String type;// 定时器类型

	private String timerNm;// 定时器名称

	private String starClosed;// 是否开启

	private String starClosedNm;// 是否开启-名称

	private String classPath;// 类路径

	private String methodNm;// 执行方法名称

	private String delay;// 延迟时间

	private String taskTime;// 执行时间

	private String period;// 执行间隔时间

	private String state;// 状态

	private String stateNm;// 状态

	private String mark;// 备注

	private String typeNm;// 类型-名称

	// 构造方法
	public Timer() {
	}

	@Transient
	public String getStarClosedNm() {
		return starClosedNm;
	}

	public void setStarClosedNm(String starClosedNm) {
		this.starClosedNm = starClosedNm;
	}

	@Transient
	public String getStateNm() {
		return stateNm;
	}

	public void setStateNm(String stateNm) {
		this.stateNm = stateNm;
	}

	@Transient
	public String getTypeNm() {
		return typeNm;
	}

	public void setTypeNm(String typeNm) {
		this.typeNm = typeNm;
	}

	@Id
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	//@SequenceGenerator(name = "generator", allocationSize = 1, sequenceName = "T_SYS_TIMER_SEQUENCE")
	@Column(name = "NID", nullable = false, precision = 22, scale = 0)
	public Long getNid() {
		return nid;
	}

	public void setNid(Long nid) {
		this.nid = nid;
	}

	@Column(name = "TYPE", length = 1)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "TIMER_NM", length = 50)
	public String getTimerNm() {
		return timerNm;
	}

	public void setTimerNm(String timerNm) {
		this.timerNm = timerNm;
	}

	@Column(name = "STAR_CLOSED", length = 1)
	public String getStarClosed() {
		return starClosed;
	}

	public void setStarClosed(String starClosed) {
		this.starClosed = starClosed;
	}

	@Column(name = "CLASS_PATH", length = 50)
	public String getClassPath() {
		return classPath;
	}

	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}

	@Column(name = "METHOD_NM", length = 50)
	public String getMethodNm() {
		return methodNm;
	}

	public void setMethodNm(String methodNm) {
		this.methodNm = methodNm;
	}

	@Column(name = "DELAY", length = 10)
	public String getDelay() {
		return delay;
	}

	public void setDelay(String delay) {
		this.delay = delay;
	}

	@Column(name = "TASK_TIME", length = 50)
	public String getTaskTime() {
		return taskTime;
	}

	public void setTaskTime(String taskTime) {
		this.taskTime = taskTime;
	}

	@Column(name = "PERIOD", length = 10)
	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	@Column(name = "STATE", length = 1)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "MARK", length = 100)
	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

}
