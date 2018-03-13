<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>

<div class="tpl-portlet-components">
    <div class="portlet-title">
        <div class="caption font-green bold">
            <div class="am-btn-toolbar">
	            <div class="am-btn-group am-btn-group-xs">
	                <button type="button" id="addBtn" onclick="javascript:loadRight('<%=basePath %>/views/project/add.jsp')" class="am-btn am-btn-default"><span class="am-icon-plus"></span> 新增</button>
	            </div>
            </div>
        </div>
    </div>
	<form class="am-form am-form-horizontal form-border" id="queryForm" role="form" action="<%=basePath %>/project/list">
		<input type="hidden" id="page" name="page" value="${page.pageNumber}">
		<div class="am-g tpl-amazeui-form">
			<div class="am-u-lg-4">
				<label for="projectName" class="am-u-sm-4 am-form-label">项目名：</label>
				<div class="am-input-group">
					<input type="text" class="am-form-field" name="projectName" value="${projectName}" placeholder="请输入项目名">
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
							<th>项目名</th>
							<th>计费</th>
							<th>总消费次数</th>
							<th>总消费金额</th>
							<th>创建时间</th>
							<th>状态</th>
							<th>备注</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="item">
							<tr>
								<td>${item.id }</td>
                                <td><a href="javascript:loadRight('<%=basePath %>/interfaceCall/list?projectId=${item.id }')">${item.project_name }</a></td>
								<td>${item.price }￥/次</td>
								<td>${item.count }次</td>
								<td>${item.count*item.price }￥</td>
								<td><fmt:formatDate value="${item.create_time }" pattern="yyyy-MM-dd HH:mm" /></td>
								<td>${item.enable?"启用":"停用" }</td>
								<td>${item.remark }</td>
								<td>
									<div class="am-btn-toolbar">
										<div class="am-btn-group am-btn-group-xs">
										   <c:if test="${item.enable  }">
                                              <button type="button" name="disableBtn" onclick="enableProject(${item.id },false)" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">停用</button>
                                            </c:if>   
                                            <c:if test="${!item.enable  }">
                                              <button type="button" name="enableBtn" onclick="enableProject(${item.id },true)" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">启用</button>
                                            </c:if>  
										    <button type="button" name="editBtn" onclick="javascript:loadRight('<%=basePath %>/project/toEdit?id=${item.id}')" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">修改</button>
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

<script src="<%=basePath %>/js/project.js"></script>