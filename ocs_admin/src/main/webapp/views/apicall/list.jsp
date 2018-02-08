<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>

<div class="tpl-portlet-components">
	<form class="am-form am-form-horizontal form-border" id="queryForm" role="form" action="<%=basePath %>/interfaceCall/list">
		<input type="hidden" id="page" name="page" value="${page.pageNumber}">
		<div class="am-g tpl-amazeui-form">
			<div class="am-u-lg-4">
				<label for="userName" class="am-u-sm-4 am-form-label">用户名：</label>
				<div class="am-input-group">
					<input type="text" class="am-form-field" name="userName" value="${userName}" placeholder="请输入用户名">
				</div>
			</div>
			
			<div class="am-u-lg-4">
                <label for="projectId" class="am-u-sm-4 am-form-label">项目ID：</label>
                <div class="am-input-group">
                    <input type="text" class="am-form-field" name="projectId" value="${projectId}" placeholder="请输入项目ID">
                </div>
            </div>
            
            <div class="am-u-lg-4">
                <label for="date" class="am-u-sm-4 am-form-label">日期：</label>
                <div class="am-input-group">
                    <input type="text" class="am-form-field" placeholder="日期" name="date" value="${date}" id="datetimeStart">
                </div>
            </div>

             <div class="am-u-lg-4 am-u-end">
                <button class="am-btn am-btn-secondary am-radius" type="button" onclick="doQuery();">查询</button>
                <button class="am-btn am-btn-default" type="button" onclick="back('<%=basePath %>/project/list')">返回</button>
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
							<th>编号</th>
							<th>用户名</th>
							<th>项目ID</th>
							<th>项目名</th>
							<th>日期</th>
							<th>调用状态</th>
							<th>调用确认</th>
							<th>备注</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="item">
							<tr>
								<td>${item.id }</td>
                                <td>${item.userName }</td>
                                <td>${item.projectId }</td>
                                <td>${item.projectName }</td>
								<td><fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd HH:mm" /></td>
								<td>${item.callSuccess?'成功':'失败' }</td>
								<td>${item.callbackSuccess?'确认':'未确认' }</td>
                                <td>${item.remark }</td>
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

<script src="<%=basePath %>/js/apicall.js"></script>