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
	initEditExpirDate();
	initEditAccount();
	
	//日期时间选择器
	$("#expirDate").datepicker({
	    language: 'zh-CN', 
	    format: "yyyy-mm-dd",
	    autoclose: true,
	    maxView: "decade",
	    todayBtn: true,
	    pickerPosition: "bottom-left"
	})
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
	$("button[name='resetPwdBtn']").on('click', function() {
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

/**
 * 禁用
 */
function disable(id){
	$.ajax({
		url:getRootPath()+"/user/updateState",
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
		url:getRootPath()+"/user/updateState",
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


/**
 * 编辑过期时期
 */
function initEditExpirDate(){
	$("button[name='editExpirDate']").on('click', function() {
	      $('#editExpirDate-prompt').modal({
	        relatedTarget: this,
	        onConfirm: function(options) {
	        	var $link = $(this.relatedTarget);
	        	$.ajax({
	        		url:getRootPath()+"/user/editExpirDate",
	           		data:{"id":$link.data("id"), "expirDate":options.data},
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
	      });
	    });
}

/**
 * 编辑账户余额
 */
function initEditAccount(){
	$("button[name='editAccountDate']").on('click', function() {
	      $('#editAccount-prompt').modal({
	        relatedTarget: this,
	        onConfirm: function(options) {
	        	var $link = $(this.relatedTarget);
	        	$.ajax({
	        		url:getRootPath()+"/user/editAccount",
	           		data:{"userName":$link.data("username"), "money":options.data},
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
	      });
	    });
}
