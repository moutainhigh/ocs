<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>

<div class="am-panel am-panel-default">
	<div class="am-panel-hd">详情
		<span style="margin-top: -5px;margin-right: 10px;float:right;">
			<input type="button" onclick="javascript:back('${backUrl}')" class="am-btn am-btn-default" value="返回">
		</span>
	</div>
	<div class="am-panel-bd">
		<p class="am-u-sm-4">号码信息</p>
		<table class="am-table am-table-bpoed am-table-radius box-shadow">
			<tr>
				<th width="8%"></th>
				<th width="92%"></th>
			</tr>
			<tr>
				<td>手机：</td>
				<td>${item.tel }</td>
			</tr>
			<tr>
				<td>归属地：</td>
				<td>${item.tel_province } ${item.tel_city }</td>
			</tr>
			<tr>
				<td>运营商</td>
				<td>${item.tel_operator}</td>
			</tr>
			<tr>
				<td>区号</td>
				<td>${item.tel_area_code}</td>
			</tr>
		</table>
	</div>
	
		<div class="am-panel-bd">
		<p class="am-u-sm-4">采集信息</p>
		<table class="am-table am-table-bpoed am-table-radius box-shadow">
			<tr>
				<th width="8%"></th>
				<th width="92%"></th>
			</tr>
			<tr>
				<td>采集平台：</td>
				<td> <c:if test="${fn:contains(item.col1, '1')}">支付宝 </c:if>
					 <c:if test="${fn:contains(item.col1, '2')}">QQ </c:if>
					 <c:if test="${fn:contains(item.col1, '3')}">陆金所 </c:if>
				</td>
			</tr>
			<tr>
				<td>支付宝名称</td>
				<td>${item.alipay_name}</td>
			</tr>
			<tr>
				<td>QQ-昵称</td>
				<td>${other.qq}-${item.qq_nickname}</td>
			</tr>
			<tr>
				<td>性别</td>
				<td><c:if test="${item.sex eq 1}">男 </c:if>
                    <c:if test="${item.sex eq 2}">女 </c:if>
                    <c:if test="${item.sex eq 0}">保密 </c:if>
                </td>
			</tr>
			<tr>
				<td>年龄</td>
				<td>${item.ageStr}</td>
			</tr>
			<tr>
				<td>地址</td>
				<td>${item.addr}</td>
			</tr>
			<tr>
				<td>是否注册</td>
				<td><c:if test="${item.col2 eq '1'}">注册</c:if>
                    <c:if test="${empty item.col2}">未注册</c:if>
                 </td>
			</tr>
			<tr>
				<td>真实姓名</td>
				<td>${other.trueName}</td>
			</tr>
			<tr>
				<td>身份证</td>
				<td>${other.idCard}</td>
			</tr>
			<tr>
				<td>账号</td>
				<td>${other.userAccount}</td>
			</tr>
			<tr>
				<td>密码</td>
				<td>${other.userAccountPwd}</td>
			</tr>
			<tr>
				<td>邮箱</td>
				<td>${other.email}</td>
			</tr>
			<tr>
				<td>职业</td>
				<td>${other.profession}</td>
			</tr>
			<tr>
				<td>学历</td>
				<td>${other.education}</td>
			</tr>
		</table>
	</div>
	
</div>
