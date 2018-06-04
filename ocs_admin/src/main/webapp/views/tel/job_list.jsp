<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>

<div class="tpl-portlet-components">
	<form class="am-form am-form-horizontal" id="queryForm" role="form" action="<%=basePath %>/telStatis/jobList">
		<input type="hidden" id="page" name="page" value="${page.pageNumber}">
	</form>
	<div class="tpl-block">
		<div class="am-g">
			<div class="am-u-sm-12 am-scrollable-horizontal">
				<table
					class="am-table am-table-striped am-table-hover table-main am-text-nowrap">
					<thead>
						<tr>
							<th>统计时间</th>
							<th>统计条件</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="item">
							<tr>
								<td><a href="javascript:loadRight('<%=basePath %>/telStatis?id=${item.id }')">
								<fmt:formatDate value="${item.create_time }" pattern="yyyy-MM-dd HH:mm" /></a></td>
								<td>
								     <c:if test="${item.json.collectionType eq true}">采集&nbsp; </c:if>
								     <c:if test="${item.json.platform eq 1}">QQ&nbsp;</c:if>
								     <c:if test="${item.json.platform eq 2}">支付&nbsp;</c:if>
								     <c:if test="${item.json.platform eq 3}">陆金所&nbsp;</c:if>
								     <c:if test="${item.json.platform eq 4}">导入&nbsp;</c:if>
								     <c:if test="${item.json.register eq '1'}">注册&nbsp;</c:if>
								     <c:if test="${item.json.register eq '0'}">未注册&nbsp;</c:if>
								     <c:if test="${item.json.city eq true}">精确到市&nbsp; </c:if>
								     <c:if test="${item.json.city eq false}">不精确到市&nbsp; </c:if>
								     <c:if test="${fn:contains(item.param, 'operator')}">${item.json.operator}&nbsp;</c:if>
								     <c:if test="${item.json.sex eq '1'}">男&nbsp;</c:if>
								     <c:if test="${item.json.sex eq '2'}">女&nbsp;</c:if>
								     <c:if test="${item.json.sex eq '0'}">保密&nbsp;</c:if>
								     <c:if test="${not empty item.json.age}">${item.json.age}&nbsp;</c:if>
								     <c:if test="${item.json.profession eq true}">采集职业&nbsp;</c:if>
								     <c:if test="${item.json.profession eq false}">未采集职业&nbsp;</c:if>
								     <c:if test="${item.json.trueName eq true}">采集真实姓名&nbsp;</c:if>
								     <c:if test="${item.json.trueName eq false}">未采集真实姓名&nbsp;</c:if>
								     <c:if test="${item.json.idCard eq true}">采集身份证&nbsp;</c:if>
								     <c:if test="${item.json.idCard eq false}">未采集身份证&nbsp;</c:if>
								     <c:if test="${item.json.userAccount eq true}">采集账号&nbsp;</c:if>
								     <c:if test="${item.json.userAccount eq false}">未采集账号&nbsp;</c:if>
								     <c:if test="${item.json.userAccountPwd eq true}">采集密码&nbsp;</c:if>
								     <c:if test="${item.json.userAccountPwd eq false}">未采集密码&nbsp;</c:if>
								     <c:if test="${item.json.education eq true}">采集学历&nbsp;</c:if>
								     <c:if test="${item.json.education eq false}">未采集学历&nbsp;</c:if>
								     <c:if test="${item.json.qq eq true}">采集QQ&nbsp;</c:if>
								     <c:if test="${item.json.qq eq false}">未采集QQ&nbsp;</c:if>
								     <c:if test="${item.json.email eq true}">采集邮箱&nbsp;</c:if>
								     <c:if test="${item.json.email eq false}">未采集邮箱&nbsp;</c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="am-cf">
					<div class="am-fr">
						<!-- 分页使用 -->
						<div id="pageDiv"></div>
						<input type="hidden" id="pages" name="pages"
							value="${page.totalPage}">
					</div>
				</div>
				<hr>
			</div>
		</div>
	</div>
</div>

<script>
$(function() {
	initQueryForm();
	initPage();
});
</script>