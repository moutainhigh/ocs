/**
 * 广告管理
 */

$(function() {
	initPage();//初始化分页
	initQueryForm();
	initDelete();
	initAdd();
	initEdit();
	//日期时间选择器（开始、结束）
    $("#date").datepicker({
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
function initDelete() {
	$("button[name='delBtn']").on('click', function() {
		$('#confirm_msg').text("确定删除该广告？");
		$('#my_confirm').modal({
			relatedTarget : this,
			onConfirm : function(options) {
				var $link = $(this.relatedTarget);
				$.ajax({
					url : getRootPath() + "/ad/delete",
					data : {
						"id" : $link.data("id")
					},
					dataType : "text",
					success : function(data) {
						$('#my_confirm').modal("close");
						var obj = jQuery.parseJSON(data);
						alert(obj.resultDes);
						if (obj.resultCode == '1') {
							doQuery();
						}
					}
				})
			}
		});
	});
}

/**
 * 新增
 */
function initAdd(){
	$("#addBtn").on('click', function() {
	      $('#add-prompt').modal({
	        relatedTarget: this,
	        onConfirm: function(options) {
	        	$.ajax({
	        		url:getRootPath()+"/ad/save",
	           		data:{"content":$("#content").val()},
	           		dataType:"text",
	           		success:function(data){
	           			$('#add-prompt').modal("close");
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
 * 修改
 */
function initEdit(){
	$("button[name='editBtn']").on('click', function() {
	      $('#edit-prompt').modal({
	        relatedTarget: this,
	        onConfirm: function(options) {
	        	var $link = $(this.relatedTarget);
	        	$.ajax({
	        		url:getRootPath()+"/ad/edit",
	           		data:{"id" : $link.data("id"),"content":$("#content2").val()},
	           		dataType:"text",
	           		success:function(data){
	           			$('#add-prompt').modal("close");
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

function clearBtn(){
	$.ajax({
		url:getRootPath()+"/ad/delAll",
   		data:{},
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