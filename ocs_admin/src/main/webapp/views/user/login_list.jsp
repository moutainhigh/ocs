<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>

<div class="tpl-portlet-components">
	<form class="am-form am-form-horizontal form-border" id="queryForm" role="form" action="<%=basePath %>/user/loginList">
		<input type="hidden" id="page" name="page" value="${page.pageNumber}">
		<div class="am-g tpl-amazeui-form">
			<div class="am-u-lg-4">
				<label for="userName" class="am-u-sm-4 am-form-label">用户名：</label>
				<div class="am-input-group">
					<input type="text" class="am-form-field" name="userName" value="${userName}" placeholder="请输入用户名">
				</div>
			</div>

             <div class="am-u-lg-2 am-u-end">
                <button class="am-btn am-btn-secondary am-radius" type="button" onclick="doQuery();">查询</button>
            </div>
		</div>
	</form>
	<div class="tpl-block">
		<div class="am-g">
			<div class="am-u-sm-12 am-scrollable-horizontal">
				<table
					class="am-table am-table-striped am-table-hover table-main am-text-nowrap">
					<thead>
					    <tr>
                            <th colspan="3">今日登录：${countLoginToday}</th>
                        </tr>
						<tr>
							<th>用户名</th>
							<th>登录ip</th>
							<th>地址</th>
							<th>最后登录时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="item">
							<!-- 根据最近登录时间转化为天数 当天登录红色-->
							<c:set var="loginDays" value="${nowDate.time - item.login_time.time}" />
							<tr <c:if test="${loginDays <= 1*1000*60*60*24  }">style="color: red;"</c:if>>
								<td>${item.user_name }</td>
								<td>${item.login_ip }</td>
								<td>${item.city }</td>
								<td><fmt:formatDate value="${item.login_time }" pattern="yyyy-MM-dd HH:mm" /></td>
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
    initPage();//初始化分页
    initQueryForm();//初始化查询
});
</script>