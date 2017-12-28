package com.gt.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.gt.pageModel.BasePageForLayUI;

/**
 * @功能说明：学生管理
 * @作者： herun
 * @创建日期：2017-12-13
 * @版本号：V1.0
 */
@Entity
@Table(name = "t_app_student", schema = "")
public class Student extends BasePageForLayUI implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String uuid;//主键

	private String name;//名字
	
	private String nameNm;//名字

	private String sex;//性别

	private String state;//状态
	
	private String stateNm;//状态

	private String note;//备注

	private String dateBirth;//出生年月

	private String archives;//档案

	private Date createTime;//创建时间

	private Date updateTime;//修改时间


	//构造方法
	public Student() {
	}
	
	
	@Id @Column( name = "UUID" , unique = true  ,nullable = false  , length = 36  )
	public String getUuid() {
		return  uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Transient
	public String getNameNm() {
		return nameNm;
	}


	public void setNameNm(String nameNm) {
		this.nameNm = nameNm;
	}

	@Transient
	public String getStateNm() {
		return stateNm;
	}


	public void setStateNm(String stateNm) {
		this.stateNm = stateNm;
	}


	@Column( name = "NAME"  ,nullable = false  , length = 50  )
	public String getName() {
		return  name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	@Column( name = "SEX"  ,nullable = false  , length = 1  )
	public String getSex() {
		return  sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	
	@Column( name = "STATE"  ,nullable = false  , length = 1  )
	public String getState() {
		return  state;
	}

	public void setState(String state) {
		this.state = state;
	}

	
	@Column( name = "NOTE"  , length = 200  )
	public String getNote() {
		return  note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	
	@Column( name = "DATE_BIRTH"  ,nullable = false  , length = 10  )
	public String getDateBirth() {
		return  dateBirth;
	}

	public void setDateBirth(String dateBirth) {
		this.dateBirth = dateBirth;
	}

	
	@Column( name = "ARCHIVES"  ,nullable = false  , length = 500  )
	public String getArchives() {
		return  archives;
	}

	public void setArchives(String archives) {
		this.archives = archives;
	}

	
	 @Temporal(TemporalType.TIMESTAMP) @Column( name = "CREATE_TIME"  ,nullable = false  , length = 10  )
	public Date getCreateTime() {
		return  createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
	 @Temporal(TemporalType.TIMESTAMP) @Column( name = "UPDATE_TIME"  , length = 10  )
	public Date getUpdateTime() {
		return  updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
