<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>

<div class="tpl-portlet-components">
	<form class="am-form am-form-horizontal" id="queryForm" role="form" action="<%=basePath %>/adtask/list">
		<input type="hidden" id="page" name="page" value="${page.pageNumber}">
		<div class="am-g tpl-amazeui-form">
            <div class="am-u-lg-3 am-u-end">
                <label for="state" class="am-u-sm-4 am-form-label">状态：</label> 
                <div class="am-input-group am-u-sm-8"> 
                    <select id="state" name="state" class="inline-block">
                        <option value="">-请选择-</option>
                        <option value="1" <c:if test="${not empty state and state == 1}">selected</c:if>>待执行</option>
                        <option value="2" <c:if test="${not empty state and state == 2}">selected</c:if>>执行中</option>
                        <option value="3" <c:if test="${not empty state and state == 3}">selected</c:if>>完成</option>
                    </select>
                </div>
            </div>
            <div class="am-u-lg-3">
                <label for="userName" class="am-u-sm-4 am-form-label">用户名：</label>
                <div class="am-input-group">
                    <input type="text" class="am-form-field" name="userName" value="${userName}" placeholder="请输入用户名">
                </div>
            </div>
            
            <div class="am-u-lg-3">
                <label for="userName" class="am-u-sm-4 am-form-label">订单号：</label>
                <div class="am-input-group">
                    <input type="text" class="am-form-field" name="orderCode" value="${orderCode}" placeholder="请输入订单号">
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
							<th>创建时间</th>
							<th>用户</th>
							<th>订单号</th>
							<th>内容</th>
							<th>消费项目id</th>
							<th>是否抢回</th>
							<th>金额</th>
							<th>已发送数|数量</th>
							<th>完成率</th>
							<th>状态</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="item">
							<tr>
								<td><fmt:formatDate value="${item.create_time }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td>${item.user_name }</td>
                                <td>${item.order_code }</td>
								<td>${item.content }</td>
								<td>${item.project_id }</td>
								<td>${item.back }</td>
								<td>${item.money }</td>
								<td>${item.count_called } | ${item.count_call }</td>
								<td>${item.count_called/item.count_call*100 }%</td>
								<td>
								    <c:if test="${item.state == 1}">待执行</c:if>
                                    <c:if test="${item.state == 2}">执行中</c:if>
                                    <c:if test="${item.state == 3}">完成</c:if>
							    </td>
							    <td>
							     <div class="am-btn-toolbar">
                                        <div class="am-btn-group am-btn-group-xs">
                                            <button type="button" name="editBtn" onclick="javascript:loadRight('<%=basePath %>/adtask/adDetailList?orderCode=${item.orderCode}')" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">详情</button>
                                        </div>
                                    </div>
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

<script src="<%=basePath %>/js/adtask.js"></script>