<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%   
String mypath = request.getContextPath();   
String mybasePath = request.getScheme()+"://" +request.getServerName()+":" +request.getServerPort()+mypath ;   
%> 
<!-- 左边菜单栏 start -->
<div class="tpl-left-nav tpl-left-nav-hover">
	<div class="tpl-left-nav-title">管理后台</div>
	<div class="tpl-left-nav-list">
		<ul class="tpl-left-nav-menu">
			<li class="tpl-left-nav-item"><a
				href="<%=mybasePath %>/views/index.jsp" class="nav-link active"> <i
					class="am-icon-home"></i> <span>首页</span>
			</a></li>
			<shiro:hasPermission name="member">
				<li class="tpl-left-nav-item"><a href="javascript:void(0);"
					class="nav-link tpl-left-nav-link-list"> <i
						class="am-icon-user-md"></i> <span>用户管理</span> <i
						class="am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right tpl-left-nav-more-ico-rotate"></i>
				</a>
					<ul class="tpl-left-nav-sub-menu" style="display: none;">
						<li><a
							href="javascript:loadRight('<%=mybasePath %>/user/list','用户列表')">
								<i class="am-icon-angle-right"></i> <span>用户列表</span>
						</a> <a
							href="javascript:loadRight('<%=mybasePath %>/user/loginList','最近登录用户')">
								<i class="am-icon-angle-right"></i> <span>最近登录用户</span>
						</a></li>
					</ul></li>
			</shiro:hasPermission>

			<shiro:hasPermission name="recharge">
				<li class="tpl-left-nav-item"><a href="javascript:void(0);"
					class="nav-link tpl-left-nav-link-list"> <i
						class="am-icon-user-md"></i> <span>充值管理</span> <i
						class="am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right tpl-left-nav-more-ico-rotate"></i>
				</a>
					<ul class="tpl-left-nav-sub-menu" style="display: none;">
						<li><shiro:hasPermission name="recharge-list">
								<a
									href="javascript:loadRight('<%=mybasePath %>/recharge/list','充值列表')">
									<i class="am-icon-angle-right"></i> <span>充值列表</span>
								</a>
							</shiro:hasPermission> <shiro:hasPermission name="rechargeSet-list">
								<a
									href="javascript:loadRight('<%=mybasePath %>/rechargeSet/list','充值赠送管理')">
									<i class="am-icon-angle-right"></i> <span>充值赠送管理</span>
								</a>
							</shiro:hasPermission></li>
					</ul></li>
			</shiro:hasPermission>

			<shiro:hasPermission name="qq">
				<li class="tpl-left-nav-item"><a href="javascript:void(0);"
					class="nav-link tpl-left-nav-link-list"> <i
						class="am-icon-user-md"></i> <span>QQ管理</span> <i
						class="am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right tpl-left-nav-more-ico-rotate"></i>
				</a>
					<ul class="tpl-left-nav-sub-menu" style="display: none;">
						<li><a
							href="javascript:loadRight('<%=mybasePath %>/qq/list','QQ列表')">
								<i class="am-icon-angle-right"></i> <span>QQ列表</span>
						</a></li>
					</ul></li>
			</shiro:hasPermission>

			<shiro:hasPermission name="interfaceCall">
				<li class="tpl-left-nav-item"><a href="javascript:void(0);"
					class="nav-link tpl-left-nav-link-list"> <i
						class="am-icon-user-md"></i> <span>接口调用实况</span> <i
						class="am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right tpl-left-nav-more-ico-rotate"></i>
				</a>
					<ul class="tpl-left-nav-sub-menu" style="display: none;">
						<li><a
							href="javascript:loadRight('<%=mybasePath %>/interfaceCall/list','接口调用列表')">
								<i class="am-icon-angle-right"></i> <span>接口调用列表</span>
						</a></li>
					</ul></li>
			</shiro:hasPermission>

			<shiro:hasPermission name="project">
				<li class="tpl-left-nav-item"><a href="javascript:void(0);"
					class="nav-link tpl-left-nav-link-list"> <i
						class="am-icon-user-md"></i> <span>消费项目管理</span> <i
						class="am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right tpl-left-nav-more-ico-rotate"></i>
				</a>
					<ul class="tpl-left-nav-sub-menu" style="display: none;">
						<li><a
							href="javascript:loadRight('<%=mybasePath %>/project/list','消费项目列表')">
								<i class="am-icon-angle-right"></i> <span>消费项目列表</span>
						</a></li>
					</ul></li>
			</shiro:hasPermission>
			
			<shiro:hasPermission name="ad">
                <li class="tpl-left-nav-item"><a href="javascript:void(0);"
                        class="nav-link tpl-left-nav-link-list"> <i
                            class="am-icon-user-md"></i> <span>广告管理</span> <i
                            class="am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right tpl-left-nav-more-ico-rotate"></i>
                    </a>
                        <ul class="tpl-left-nav-sub-menu" style="display: none;">
                            <li><a
                                href="javascript:loadRight('<%=mybasePath %>/ad/list','广告列表')">
                                    <i class="am-icon-angle-right"></i> <span>广告列表</span>
                            </a></li>
                            
                            <li><a
                                href="javascript:loadRight('<%=mybasePath %>/ad/userAdList','用户提交广告')">
                                    <i class="am-icon-angle-right"></i> <span>用户提交广告</span>
                            </a></li>
                        </ul></li>
            </shiro:hasPermission>
			
			<shiro:hasPermission name="report">
				<li class="tpl-left-nav-item"><a href="javascript:void(0);"
	                    class="nav-link tpl-left-nav-link-list"> <i
	                        class="am-icon-user-md"></i> <span>报表</span> <i
	                        class="am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right tpl-left-nav-more-ico-rotate"></i>
	                </a>
	                    <ul class="tpl-left-nav-sub-menu" style="display: none;">
	                        <li><a
	                            href="javascript:loadRight('<%=mybasePath %>/report/rechargeList','充值报表')">
	                                <i class="am-icon-angle-right"></i> <span>充值报表</span>
	                        </a></li>
	                        
	                        <li><a
                                href="javascript:loadRight('<%=mybasePath %>/report/callList','接口调用报表')">
                                    <i class="am-icon-angle-right"></i> <span>接口调用报表</span>
                            </a></li>
	                    </ul></li>
			</shiro:hasPermission>

			<shiro:hasPermission name="system">
				<li class="tpl-left-nav-item"><a href="javascript:void(0);"
					class="nav-link tpl-left-nav-link-list"> <i class="am-icon-cog"></i>
						<span>系统管理</span> <i
						class="am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right tpl-left-nav-more-ico-rotate"></i>
				</a>
					<ul class="tpl-left-nav-sub-menu" style="display: none;">
						<li><shiro:hasPermission name="system-user">
								<a
									href="javascript:loadRight('<%=mybasePath %>/admin/userList','系统用户管理')">
									<i class="am-icon-angle-right"></i> <span>系统用户管理</span>
								</a>
							</shiro:hasPermission> <shiro:hasPermission name="system-role">
								<a
									href="javascript:loadRight('<%=mybasePath %>/role/list','系统角色管理')">
									<i class="am-icon-angle-right"></i> <span>系统角色管理</span>
								</a>
							</shiro:hasPermission> <shiro:hasPermission name="system-resource">
								<a
									href="javascript:loadRight('<%=mybasePath %>/resource/list','资源管理')">
									<i class="am-icon-angle-right"></i> <span>资源管理</span>
								</a>
							</shiro:hasPermission> <shiro:hasPermission name="system-config">
								<a
									href="javascript:loadRight('<%=mybasePath %>/sysConfig/list','系统配置')">
									<i class="am-icon-angle-right"></i> <span>系统配置</span>
								</a>
							</shiro:hasPermission></li>
					</ul></li>
			</shiro:hasPermission>

			<li class="tpl-left-nav-item"><a href="javascript:logout()"
				class="nav-link tpl-left-nav-link-list"> <i class="am-icon-key"></i>
					<span>注销</span>

			</a></li>
		</ul>
	</div>
</div>
<!-- 左边菜单栏 end -->