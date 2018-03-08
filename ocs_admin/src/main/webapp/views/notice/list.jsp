<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>

<div class="tpl-portlet-components">
    <div class="portlet-title">
        <div class="caption font-green bold">
            <div class="am-btn-toolbar">
	            <div class="am-btn-group am-btn-group-xs">
	                <button type="button" id="addBtn" onclick="javascript:loadRight('<%=basePath %>/views/notice/add.jsp')" class="am-btn am-btn-default"><span class="am-icon-plus"></span> 新增</button>
	            </div>
            </div>
        </div>
    </div>
	<form class="am-form am-form-horizontal form-border" id="queryForm" role="form" action="<%=basePath %>/notice/list">
		<input type="hidden" id="page" name="page" value="${page.pageNumber}">
		<div class="am-g tpl-amazeui-form">
			<div class="am-u-lg-4">
				<label for="softwareCode" class="am-u-sm-4 am-form-label">软件code：</label>
				<div class="am-input-group">
					<input type="text" class="am-form-field" name="softwareCode" value="${softwareCode}" placeholder="请输入软件code">
				</div>
			</div>
			<div class="am-u-lg-4">
                <label for="content" class="am-u-sm-4 am-form-label">内容：</label>
                <div class="am-input-group">
                    <input type="text" class="am-form-field" name="content" value="${content}" placeholder="请输入内容">
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
							<th>内容</th>
							<th>软件code</th>
							<th>软件名称</th>
							<th>状态</th>
							<th>创建时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="item">
							<tr>
								<td>${item.id }</td>
                                <td>${item.content }</td>
								<td>${item.software_code }</td>
								<td>${item.software_name }</td>
								<td>
                                    <c:if test="${item.enable}">启用</c:if>
                                    <c:if test="${!item.enable}">停用</c:if>
                                </td>
								<td><fmt:formatDate value="${item.create_time }" pattern="yyyy-MM-dd HH:mm" /></td>
								<td>
									<div class="am-btn-toolbar">
										<div class="am-btn-group am-btn-group-xs">
										    <c:if test="${item.enable  }">
                                              <button type="button" name="disableBtn" onclick="disableNotice(${item.id })" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">停用</button>
                                            </c:if>   
                                            <c:if test="${!item.enable  }">
                                              <button type="button" name="enableBtn" onclick="enableNotice(${item.id })" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">启用</button>
                                            </c:if>      
										    <button type="button" name="editBtn" onclick="javascript:loadRight('<%=basePath %>/notice/toEdit?id=${item.id}')" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">修改</button>
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

<script src="<%=basePath %>/js/notice.js"></script>