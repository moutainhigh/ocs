/**
 * 调用管理
 */

$(function() {
	initPage();//初始化分页
	initQueryForm();
	
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
