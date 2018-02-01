/**
 *qq数据管理
 */

/**
 * 初始化
 */
$(function() {
	initPage();//初始化分页
	initQueryForm();//初始化查询
	initDelete();
	initResetPwd();
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
	           		url:getRootPath()+"/user/delete",
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
 * 初始化重置密码事件
 */
function initResetPwd(){
	$("button[name='disableBtn']").on('click', function() {
	      $('#resetPwd-prompt').modal({
	        relatedTarget: this,
	        onConfirm: function(options) {
	        	var $link = $(this.relatedTarget);
	        	$.ajax({
	        		url:getRootPath()+"/user/resetPwd",
	           		data:{"id":$link.data("id"), "userPwd":options.data},
	           		dataType:"text",
	           		success:function(data){
	           			var obj = jQuery.parseJSON(data);
	           			alert(obj.resultDes);
	           		}
	           	})
	        }
	      });
	    });
}
