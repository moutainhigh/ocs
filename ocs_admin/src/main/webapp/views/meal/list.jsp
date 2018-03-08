<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>

<div class="tpl-portlet-components">
    <div class="portlet-title">
        <div class="caption font-green bold">
            <div class="am-btn-toolbar">
	            <div class="am-btn-group am-btn-group-xs">
	                <button type="button" id="addBtn" onclick="javascript:loadRight('<%=basePath %>/views/meal/add.jsp')" class="am-btn am-btn-default"><span class="am-icon-plus"></span> 新增</button>
	            </div>
            </div>
        </div>
    </div>
	<form class="am-form am-form-horizontal form-border" id="queryForm" role="form" action="<%=basePath %>/meal/list">
		<input type="hidden" id="page" name="page" value="${page.pageNumber}">
		<div class="am-g tpl-amazeui-form">
			<div class="am-u-lg-4">
				<label for="mealName" class="am-u-sm-4 am-form-label">套餐名称：</label>
				<div class="am-input-group">
					<input type="text" class="am-form-field" name="mealName" value="${mealName}" placeholder="请输入套餐名称">
				</div>
			</div>

             <div class="am-u-lg-6 am-u-end">
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
							<th>编号</th>
							<th>套餐名称</th>
							<th>套餐时间</th>
							<th>价格</th>
							<th>备注</th>
							<th>创建时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="item">
							<tr>
								<td>${item.id }</td>
                                <td>${item.meal_name }</td>
								<td>${item.meal_time }</td>
								<td>${item.money }</td>
								<td>${item.remark }</td>
								<td><fmt:formatDate value="${item.create_time }" pattern="yyyy-MM-dd HH:mm" /></td>
								<td>
									<div class="am-btn-toolbar">
										<div class="am-btn-group am-btn-group-xs">
										    <button type="button" onclick="javascript:loadRight('<%=basePath %>/meal/toSaveMealProject?id=${item.id}&mealName=${item.meal_name}')" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">消费项目管理</button>
										    <button type="button" name="editBtn" onclick="javascript:loadRight('<%=basePath %>/meal/toEdit?id=${item.id}')" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">修改</button>
											<button type="button" name="delBtn" data-id="${item.id }" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">删除</button>
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

<script src="<%=basePath %>/js/meal.js"></script>