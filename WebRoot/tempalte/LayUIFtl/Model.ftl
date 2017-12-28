package com.gt.model;

import com.gt.pageModel.BasePageForLayUI;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @功能说明：${functionComment}
 * @作者： ${author}
 * @创建日期：${date}
 * @版本号：V1.0
 */
@Entity
@Table(name = "${Table_NAME}", schema = "")
public class ${className} extends BasePageForLayUI implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	<#list cloums as c>
	private ${c.cloumsType} ${ c.columnName};//${ c.columnComment}

	</#list>

	//构造方法
	public ${className}() {
	}
	
	<#list cloums as c>
	
	${c.cloums_top}
	public ${c.cloumsType} get${ c.UpUmnName}() {
		return  ${ c.columnName};
	}

	public void set${ c.UpUmnName}(${c.cloumsType} ${ c.columnName}) {
		this.${ c.columnName} = ${ c.columnName};
	}

	</#list>
}
