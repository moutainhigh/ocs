/**
 * 用户软件权限管理
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
		$('#confirm_msg').text("确定删除该广告？");
		$('#my_confirm').modal({
			relatedTarget : this,
			onConfirm : function(options) {
				var $link = $(this.relatedTarget);
				$.ajax({
					url : getRootPath() + "/userAuth/delete",
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
 * 软件授权
 */
function initUserAuth() {
	//预加载软件信息
	$.ajax({
		url : getRootPath() + "/userAuth/userAuthData",
		data : {"userName" : $("#userName").val()},
		dataType : "text",
		async:false,
		success : function(data) {
			var obj = jQuery.parseJSON(data);
			var arr = obj.resultData;
			var str = "";
			for (var i = 0; i < arr.length; i++) {
				if(arr[i].isHas){
					str += '<label class="am-checkbox-inline">'
				        +'<input type="checkbox"  name="authKeys" checked=true value="'+arr[i].auth_key+'">'
				        +arr[i].auth_name
				        +'</label>'
				}else{
					str += '<label class="am-checkbox-inline">'
				        +'<input type="checkbox"  name="authKeys" value="'+arr[i].auth_key+'">'
				        +arr[i].auth_name
				        +'</label>'
				}
			}
			$("#checkBoxDiv").html(str);
		}
	})
	//弹出框提交
	$('#auth-prompt').modal({
		relatedTarget : this,
		onConfirm : function(options) {
			var keys = getCheckKeys();
			$.ajax({
				url : getRootPath() + "/userAuth/saveUserAuth",
				data : {
					"authKeys" : keys,
					"userName" : $("#userName").val()
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

/**
 * 获取所有key
 * @returns id
 */
function getCheckKeys(){
	var allCheckIds = "";
	$.each($('input[name="authKeys"]'),function(){
		var checkId = "";
        if($(this).is(':checked')){
        	var id = $(this).val();
        	checkId = id +",";
        	allCheckIds += checkId;
        }
    });
	if(allCheckIds==""){
		return "";
	}
	return allCheckIds.substr(0,allCheckIds.length-1);
}