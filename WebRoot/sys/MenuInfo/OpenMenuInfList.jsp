<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<input type="hidden" id="sys_MenuInfo_MenuInfoTree_tree">
<ul class="easyui-tree" id="layout_west_tree" data-options="
		  	   url:'menuInf/getIconsList.do',
				parentField:'pid',lines:true,
				onLoadSuccess: function(node, data){
					//$(this).tree('collapseAll');
				},
				onClick:function(node){
					var id=$('#sys_MenuInfo_MenuInfoTree_tree').val();
					if(node.text){
						$('#'+id).val(node.text);
						$('#'+id).removeAttr('readonly'); 
						$('#'+id).focus();
						$('#'+id).attr('readonly','readonly'); 
					}
				}"></ul>