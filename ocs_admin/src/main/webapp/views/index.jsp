<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<%@ include file="/views/common/meta.jsp"%>
<meta charset="utf-8">
<!-- 不缓存 -->
<META HTTP-EQUIV="pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"> 
<META HTTP-EQUIV="expires" CONTENT="0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>管理后台</title>
<meta name="description" content="admin">
<meta name="keywords" content="index">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="icon" type="image/png" href="<%=basePath%>/assets/i/favicon.png">
<link rel="apple-touch-icon-precomposed" href="a<%=basePath%>/ssets/i/app-icon72x72@2x.png">
<meta name="apple-mobile-web-app-title" content="Amaze UI" />
<link rel="stylesheet" href="<%=basePath%>/assets/css/amazeui.min.css" />
<link rel="stylesheet" href="<%=basePath%>/assets/css/admin.css">
<link rel="stylesheet" href="<%=basePath%>/assets/css/amazeui.page.css">
<link rel="stylesheet" href="<%=basePath%>/assets/css/app.css">
<link rel="stylesheet" href="<%=basePath%>/assets/css/amazeui.datetimepicker.css"/>
</head>

<body data-type="index">


	<header class="am-topbar am-topbar-inverse admin-header">
		<div class="am-topbar-brand">
			<a href="javascript:;" class="tpl-logo"> <img
				src="<%=basePath%>/assets/img/logo.png" alt="">
			</a>
		</div>
		<div class="am-icon-list tpl-header-nav-hover-ico am-fl am-margin-right"></div>
		<button
			class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only"
			data-am-collapse="{target: '#topbar-collapse'}">
			<span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span>
		</button>
		<div class="am-collapse am-topbar-collapse" id="topbar-collapse">
			<ul
				class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list tpl-header-list">
				<li class="am-hide-sm-only"><a href="javascript:;"
					id="admin-fullscreen" class="tpl-header-list-link"><span
						class="am-icon-arrows-alt"></span> <span class="admin-fullText">开启全屏</span></a></li>

				<li class="am-dropdown" data-am-dropdown data-am-dropdown-toggle>
					<a class="am-dropdown-toggle tpl-header-list-link"
					href="javascript:;"> <span class="tpl-header-list-user-nick">${ADMIN_USER.userName}</span><span
						class="tpl-header-list-user-ico"> <img
							src="<%=basePath%>/assets/img/user.png"></span>
				</a>
					<ul class="am-dropdown-content">
						<li><a href="javascript:onuserInfo()"><span class="am-icon-bell-o"></span> 资料</a></li>
						<li><a href="javascript:onuserInfoEdit()"><span class="am-icon-cog"></span> 设置</a></li>
						<li><a href="javascript:onuserpasswordEdit()"><span class="am-icon-cog"></span> 修改密码</a></li>
						<li><a href="javascript:logout()"><span class="am-icon-power-off"></span>
								退出</a></li>
					</ul>
				</li>
				<li><a href="javascript:logout()" class="tpl-header-list-link"><span
						class="am-icon-sign-out tpl-header-list-ico-out-size"></span></a></li>
			</ul>
		</div>
	</header>

	<div class="tpl-page-container tpl-page-header-fixed">
		<!-- 菜单栏 -->
        <%@ include file="/views/common/menu.jsp"%>

		<!-- 右边内容 start -->
		<div class="tpl-content-wrapper">
			<ol class="am-breadcrumb">
				<li><a href="<%=basePath%>/views/index.jsp" class="am-icon-home">首页</a></li>
				<li><a href="javascript:void(0)" id="menu1">-</a></li>
				<li class="am-active"><a href="#" id="menu2">-</a></li>
			</ol>
			<!-- 右边主要更新内容 start -->
			<div id="right">
				<div class="row">
					<!-- 充值报表统计 start-->
                <div class="am-u-md-6 am-u-sm-12 row-mb">
                    <div class="tpl-portlet">
                        <div class="tpl-portlet-title">
                            <div class="tpl-caption font-green ">
                                <i class="am-icon-cloud-download"></i> <span> 充值报表</span>
                            </div>
                        </div>
                        <!--此部分数据请在 js文件夹下中的 app.js 中的 “百度图表A” 处修改数据 插件使用的是 百度echarts-->
                        <div class="tpl-echarts" id="tpl-echarts-rechargeReport"></div>
                    </div>
                </div>
                <!-- 充值报表统计 end-->
				</div>
			</div>
		<!-- 右边主要更新内容 end -->
			<footer data-am-widget="footer" class="am-footer am-footer-default"
				data-am-footer="{  }">

				<div class="am-footer-miscs ">

					<p>
						由 <a href="http://www.gwemall.cn/" title="广州涌智" target="_blank"
							class="">rongwq</a> 提供技术支持
					</p>
					<p>CopyRight©2017 XK Inc.</p>
				</div>
			</footer>
		</div>
		<!-- 右边内容 end -->
	</div>
	<!-- amaze ui 模态窗口 start -->
	<!-- 弹出框alert -->
	<div class="am-modal am-modal-alert" tabindex="-1" id="my_alert">
		<div class="am-modal-dialog">
			<div class="am-modal-hd" id="alert_title">提示</div>
			<div class="am-modal-bd" id="alert_msg">ALERT</div>
			<div class="am-modal-footer">
				<span class="am-modal-btn">确定</span>
			</div>
		</div>
	</div>

	<!-- 弹出框confirm -->
	<div class="am-modal am-modal-confirm" tabindex="-1" id="my_confirm">
		<div class="am-modal-dialog">
			<div class="am-modal-hd" id="confirm_title">确认提示</div>
			<div class="am-modal-bd" id="confirm_msg">确定要删除这条记录吗？</div>
			<div class="am-modal-footer">
				<span class="am-modal-btn" data-am-modal-cancel>取消</span> <span
					class="am-modal-btn" data-am-modal-confirm>确定</span>
			</div>
		</div>
	</div>
	
	<!-- 弹出框confirm2 -->
    <div class="am-modal am-modal-confirm" tabindex="-1" id="my_confirm2">
        <div class="am-modal-dialog">
            <div class="am-modal-hd" id="confirm2_title">确认提示</div>
            <div class="am-modal-bd" id="confirm2_msg">确定要删除这条记录吗？</div>
            <div class="am-modal-footer">
                <span class="am-modal-btn" data-am-modal-cancel>取消</span> <span
                    class="am-modal-btn" data-am-modal-confirm>确定</span>
            </div>
        </div>
    </div>
	
		<!-- 用户资料查看弹出框 -->
<div class="am-modal am-modal-no-btn" tabindex="-1" id="my-popup1">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">查看用户基本资料
    <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
    </div>
    <div class="am-modal-bd">
  		<div class="am-g tpl-amazeui-form">
        	<div class="am-u-sm-16 am-u-md-12">
            	<form id="dataForm1" action="" method="POST" class="am-form am-form-horizontal">
  					<div class="am-form-group">
  						<label for="userName" class="am-u-sm-5 am-form-label">账号 </label>
		    			<div class="am-u-sm-6">
		      				<label id="userName" class="am-u-sm-2 am-form-label">aa</label>
		    			</div>
					</div>
  					<div class="am-form-group">
		   				<label for="role" class="am-u-sm-5 am-form-label">角色</label>
		    			<div class="am-u-sm-6">
		      				<label id="role" class="am-u-sm-2 am-form-label">cc</label>
		    			</div>
					</div>
		
					<div class="am-form-group">
		    			<label for="mobile" class="am-u-sm-5 am-form-label">联系电话</label>
		    			<div class="am-u-sm-6">
		    				<label id="mobile" class="am-u-sm-2 am-form-label">11</label>
		    			</div>
					</div>
		
					<div class="am-form-group">
		    			<label for="email" class="am-u-sm-5 am-form-label">邮箱</label>
		    			<div class="am-u-sm-6">
		    				<label id="email" class="am-u-sm-2 am-form-label">wwssdfdsafsafsdf1354564356456</label>
		    			</div>
					</div>
  				</form>
          	</div>
      	</div>
    </div>
  </div>
</div>

	<!-- 用户资料修改弹出框 -->
<div class="am-modal am-modal-no-btn" tabindex="-1" id="my-popup2">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">修改用户基本资料
    <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
    </div>
    <div class="am-modal-bd">
  		<div class="am-g tpl-amazeui-form">
        	<div class="am-u-sm-16 am-u-md-12">
            	<form id="dataForm2" action="" method="POST" class="am-form am-form-horizontal">
  					<div class="am-form-group">
  						<label for="userName" class="am-u-sm-5 am-form-label">账号 </label>
		    			<div class="am-u-sm-6">
		      				<label id="userName"  class="am-u-sm-2 am-form-label"></label>
		    			</div>
					</div>
  					<div class="am-form-group">
		   				<label for="role" class="am-u-sm-5 am-form-label">角色</label>
		    			<div class="am-u-sm-6">
		      				<label id="role"  class="am-u-sm-2 am-form-label"></label>
		    			</div>
					</div>
		
					<div class="am-form-group">
		    			<label for="mobile" class="am-u-sm-5 am-form-label">联系电话</label>
		    			<div class="am-u-sm-6">
		    				<input class="am-form-field" type="text" name="mobile" id="mobile" placeholder="输入联系电话" required="required" value="" maxlength="11">
		    			</div>
					</div>
		
					<div class="am-form-group">
		    			<label for="email" class="am-u-sm-5 am-form-label">邮箱</label>
		    			<div class="am-u-sm-6">
		    				<input class="am-form-field" type="text" name="email" id="email" placeholder="输入邮箱" required="required" value="">
		    			</div>
					</div>
					<div class="am-form-group">
		    			<div class="am-u-sm-6">
		    				<input type="button" onclick="javascript:infoSubmit()"  class="am-btn am-btn-default" value="确定"/>
		    			</div>
		    			<div class="am-u-sm-6">
		    				<input type="button" onclick="javascript: void(0)" data-am-modal-close class="am-btn am-btn-default " value="取消"/>
		    			</div>
					</div>
  				</form>
          	</div>
      	</div>
    </div>
  </div>
</div>

	<!-- 用户密码修改弹出框 -->
<div class="am-modal am-modal-no-btn" tabindex="-1" id="my-popup3">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">修改用户密码
    <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
    </div>
    <div class="am-modal-bd">
  		<div class="am-g tpl-amazeui-form">
        	<div class="am-u-sm-16 am-u-md-12">
            	<form id="dataForm3" action="" method="POST" class="am-form am-form-horizontal">
            		<input type="hidden" name="id" value="${ADMIN_USER.id}">
  					<div class="am-form-group">
		    			<label for="userName" class="am-u-sm-5 am-form-label">账号</label>
		    			<div class="am-u-sm-6">
		      				<label for="userName" class="am-u-sm-2 am-form-label">${ADMIN_USER.userName}</label>
		    			</div>
					</div>
					<div class="am-form-group">
		    			<label for="oldPassword" class="am-u-sm-5 am-form-label">旧密码</label>
		    			<div class="am-u-sm-6">
		      				<input type="password" name="oldPassword" id="oldPassword" placeholder="输入你的旧密码" required="required">
		    			</div>
					</div>
  					<div class="am-form-group">
		   				<label for="password" class="am-u-sm-5 am-form-label">密码</label>
		    			<div class="am-u-sm-6">
		      				<input type="password" name="password" id="password" placeholder="输入新密码" required="required">
		    			</div>
					</div>
  					<div class="am-form-group">
		   				<label for="confirmPassword" class="am-u-sm-5 am-form-label">确认密码</label>
		    			<div class="am-u-sm-6">
		      				<input type="password" name="confirmPassword" id="confirmPassword" placeholder="输入确认密码" required="required">
		    			</div>
					</div>
					<div class="am-form-group">
		    			<div class="am-u-sm-6">
		    				<input type="button" onclick="javascript:passwordSubmit()"  class="am-btn am-btn-default" value="确定"/>
		    			</div>
		    			<div class="am-u-sm-6">
		    				<input type="button" onclick="javascript: void(0)" data-am-modal-close class="am-btn am-btn-default " value="取消"/>
		    			</div>
					</div>
  				</form>
          	</div>
      	</div>
    </div>
  </div>
</div>
	
<!-- amaze ui 模态窗口 end -->
	<!-- 公共js -->
	<script src="<%=basePath%>/assets/js/jquery.min.js"></script>
	<script src="<%=basePath%>/assets/js/jquery-form.js"></script>
	<script src="<%=basePath%>/assets/js/amazeui.min.js"></script>
	<script src="<%=basePath%>/assets/js/amazeui.page.js"></script>
	<script src="<%=basePath%>/assets/js/echarts.min.js"></script>
	<script src="<%=basePath%>/assets/js/common.js"></script>
	<script src="<%=basePath%>/assets/js/page.js"></script>
	<script src="<%=basePath%>/assets/js/app.js"></script>
	<script src="<%=basePath%>/js/index.js"></script>
	<script src="<%=basePath%>/assets/js/amazeui.datetimepicker.min.js"></script>
	<script src="<%=basePath%>/assets/js/amazeui.datetimepicker.zh-CN.js"></script>
</body>

<shiro:notAuthenticated>
    <script>
        	alert("请重新登录");
        	location.href=getRootPath()+"/views/login.jsp";
        	
    </script>
</shiro:notAuthenticated>
<script>
$(function() {
	initRechargeReport(); 
});

</script>
<style>
#alert_msg{word-break:break-all}
</style>
</html>