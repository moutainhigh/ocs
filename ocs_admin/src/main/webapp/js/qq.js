/**
 * QQ管理
 */

$(function() {
	initPage();//初始化分页
	initQueryForm();
	initDelete();
	
	//日期时间选择器（开始、结束）
	$("#datetimeStart").datepicker({
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
		$('#confirm_msg').text("确定删除QQ？");
		$('#my_confirm').modal({
			relatedTarget : this,
			onConfirm : function(options) {
				var $link = $(this.relatedTarget);
				$.ajax({
					url : getRootPath() + "/qq/delete",
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
 * 清空数据
 */
function clearQq() {
	$('#confirm2_msg').text("确定清空所有QQ数据？");
	$('#my_confirm2').modal({
		onConfirm : function(options) {
			$.ajax({
				url : getRootPath() + "/qq/clear",
				data : {},
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
}

