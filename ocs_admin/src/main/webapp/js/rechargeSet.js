/**
 *充值赠送数据管理
 */

/**
 * 初始化
 */
$(function() {
	initPage();//初始化分页
	initQueryForm();//初始化查询
	initDelete();
});

/**
 * 初始化删除事件
 */
function initDelete(){
	$("button[name='delBtn']").on('click', function() {
		$('#confirm_msg').text("确定删除？");
	      $('#my_confirm').modal({
	        relatedTarget: this,
	        onConfirm: function(options) {
	        	var $link = $(this.relatedTarget);
	        	$.ajax({
	           		url:getRootPath()+"/rechargeSet/delete",
	           		data:{"id":$link.data("id")},
	           		dataType:"text",
	           		success:function(data){
	           			$('#my_confirm').modal("close");
	           			var obj = jQuery.parseJSON(data);
	           			alert(obj.resultDes);
	           			if(obj.resultCode == '1'){
	           				doQuery();
	           			}
	           		}
	           	})
	        }
	      });
	    });
}

/**
 * 禁用
 */
function disable(id){
	$.ajax({
		url:getRootPath()+"/rechargeSet/updateState",
   		data:{"id":id,"enable":false},
   		dataType:"text",
   		success:function(data){
   			var obj = jQuery.parseJSON(data);
   			alert(obj.resultDes);
   			if(obj.resultCode == '1'){
   				doQuery();
   			}
   		}
   	})
}

/**
 * 启用
 */
function enable(id){
	$.ajax({
		url:getRootPath()+"/rechargeSet/updateState",
   		data:{"id":id,"enable":true},
   		dataType:"text",
   		success:function(data){
   			var obj = jQuery.parseJSON(data);
   			alert(obj.resultDes);
   			if(obj.resultCode == '1'){
   				doQuery();
   			}
   		}
   	})
}
