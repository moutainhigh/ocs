/**
 * 公告
 */

$(function() {
	initPage();//初始化分页
	initQueryForm();
	initDelete();
});

/**
 * 初始化删除事件
 */
function initDelete() {
	$("button[name='delBtn']").on('click', function() {
		$('#confirm_msg').text("确定删除该项目？");
		$('#my_confirm').modal({
			relatedTarget : this,
			onConfirm : function(options) {
				var $link = $(this.relatedTarget);
				$.ajax({
					url : getRootPath() + "/notice/delete",
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
 * 停用
 */
function disableNotice(id){
	$.ajax({
		url:getRootPath()+"/notice/enable",
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
function enableNotice(id){
	$.ajax({
		url:getRootPath()+"/notice/enable",
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