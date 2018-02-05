<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>

<div class="tpl-portlet-components">
    <div class="portlet-title">
        <div class="caption font-green bold">
            <div class="am-btn-toolbar">
	            <div class="am-btn-group am-btn-group-xs">
	                <button type="button" id="addBtn" onclick="javascript:loadRight('<%=basePath %>/views/recharge/add.jsp')" class="am-btn am-btn-default"><span class="am-icon-plus"></span> 充值</button>
	            </div>
            </div>
        </div>
    </div>
	<form class="am-form am-form-horizontal form-border" id="queryForm" role="form" action="<%=basePath %>/recharge/list">
		<input type="hidden" id="page" name="page" value="${page.pageNumber}">
		<div class="am-g tpl-amazeui-form">
			<div class="am-u-lg-4">
				<label for="userName" class="am-u-sm-4 am-form-label">用户名：</label>
				<div class="am-input-group">
					<input type="text" class="am-form-field" name="userName" value="${userName}" placeholder="请输入用户名">
				</div>
			</div>
			
			<div class="am-u-lg-4">
				<label for="date" class="am-u-sm-4 am-form-label">日期：</label>
				<div class="am-input-group">
					<input type="text" class="am-form-field" placeholder="日期" name="date" value="${date}" id="datetimeStart">
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
							<th>流水号</th>
							<th>充值时间</th>
							<th>用户</th>
							<th>类型</th>
							<th>充值额度</th>
							<th>赠送金额</th>
							<th>订单号</th>
							<th>充值状态</th>
							<th>备注</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="item">
							<tr>
								<td>${item.rechargeCode }</td>
                                <td><fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd HH:mm" /></td>
                                <td>${item.userName }</td>
								<td>
								    <c:if test="${item.type == 1}">支付宝</c:if>
								    <c:if test="${item.type == 2}">微信</c:if>
								</td>
								<td>${item.money }￥</td>
								<td>${item.giveMoney }￥</td>
								<td>${item.orderCode }</td>
								<td>
								    <c:if test="${item.useState}">成功</c:if>
                                    <c:if test="${!item.useState}">失败</c:if>
                                </td>
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

<script src="<%=basePath %>/js/recharge.js"></script>