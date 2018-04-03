<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>

<div class="tpl-portlet-components">
    <div class="portlet-title">
        <div class="caption font-green bold">
            <div class="am-btn-toolbar">
                <div class="am-btn-group am-btn-group-xs">
                    <button type="button" onclick="loadRight('<%=basePath %>/adtask/list?orderCode=${orderCode}')" class="am-btn am-btn-default">返回</button>
                </div>
            </div>
        </div>
    </div>
	<form class="am-form am-form-horizontal" id="queryForm" role="form" action="<%=basePath %>/adtask/adDetailList">
		<input type="hidden" id="page" name="page" value="${page.pageNumber}">
		<input type="hidden" id="orderCode" name="orderCode" value="${orderCode}">
	</form>
	<div class="tpl-block">
		<div class="am-g">
			<div class="am-u-sm-12 am-scrollable-horizontal">
				<table
					class="am-table am-table-striped am-table-hover table-main am-text-nowrap">
					<thead>
						<tr>
							<th>创建时间</th>
							<th>QQ群名称</th>
							<th>QQ群号码</th>
							<th>被发送QQ号码</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="item">
							<tr>
								<td><fmt:formatDate value="${item.create_time }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td>${item.qq_group_name }</td>
                                <td>${item.qq_group_no }</td>
								<td>${item.qq }</td>
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

<script src="<%=basePath %>/js/adtask.js"></script>