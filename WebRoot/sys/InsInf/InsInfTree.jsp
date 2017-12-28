<%@ page language="java" pageEncoding="UTF-8"%>

<div align="left">
	<div style="width: auto;overflow: auto;height: auto; border: 1;float: left;" align="left">
		<ul id="sys_InsInf_InsInfTree_orgTree" class="ztree"></ul>
	</div>
</div>
<script>
	
	var sys_InsInf_InsInfTree_setting = {
		check : {
			enable : true,
			chkStyle : "radio",
			radioType : "all"
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId",
				rootPId : null
			}
		}
	};
	function sys_InsInf_InsInfTree_init(insTp) {
		//打开进程
		parent.$.messager.progress({
			text : '正在执行，请稍后....',
			interval : 100
		});
		$.ajax({
			url : 'insInf/getTree.do?insTp='+insTp,
			type : 'POST',
			dataType : "text",
			ContentType : "application/json; charset=UTF-8",
			success : function(data) {
				//关闭进程对话框
				parent.$.messager.progress('close');
				zNodes = data;
				$.fn.zTree.init($("#sys_InsInf_InsInfTree_orgTree"), sys_InsInf_InsInfTree_setting, eval('(' + zNodes + ')'));
			},
			error : function(msg) {
				$.messager.alert('错误提示', '系统错误', 'error');
				parent.$.messager.progress('close');
			}
		});
	}
</script>
