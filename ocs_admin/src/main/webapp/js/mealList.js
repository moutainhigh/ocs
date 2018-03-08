/**
 *用户套餐管理
 */

/**
 * 初始化
 */
$(function() {
	initPage();//初始化分页
	initQueryForm();//初始化查询
	initDelete();
	initEditExpirDate();
	
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
	           		url:getRootPath()+"/meal/deleteUserMeal",
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
 * 编辑过期时期
 */
function initEditExpirDate(){
	$("button[name='editUserMealExpirDate']").on('click', function() {
	      $('#editUserMealExpirDate-prompt').modal({
	        relatedTarget: this,
	        onConfirm: function(options) {
	        	var $link = $(this.relatedTarget);
	        	$.ajax({
	        		url:getRootPath()+"/meal/editUserMealExpirDate",
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
 * 新增用户套餐
 */
function addMeal() {
	//预加载套餐信息
	$.ajax({
		url : getRootPath() + "/meal/mealList",
		data : {},
		dataType : "text",
		async:false,
		success : function(data) {
			var obj = jQuery.parseJSON(data);
			var arr = obj.resultData;
			var str = "<option value='0' >请选择套餐</option>";
			for (var i = 0; i < arr.length; i++) {
				str += "<option value='"+arr[i].id+"' >"+arr[i].meal_name+"</option>";
			}
			$("#mealId").html(str);
		}
	})
	//弹出框提交
	$('#meal-prompt').modal({
		relatedTarget : this,
		onConfirm : function(options) {
			$.ajax({
				url : getRootPath() + "/meal/saveUserMeal",
				data : {
					"userName" : $("#userName").val(),
					"mealId" : $("#mealId").val(),
				},
				dataType : "text",
				success : function(data) {
					var obj = jQuery.parseJSON(data);
					alert(obj.resultDes);
					if (obj.resultCode == '1') {
						doQuery();
					}
				}
			})
		}
	});
}

