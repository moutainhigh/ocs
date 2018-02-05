<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>

<div class="tpl-portlet-components">
    <div class="portlet-title">
        <div class="caption font-green bold">
            <div class="am-btn-toolbar">
	            <div class="am-btn-group am-btn-group-xs">
	                <button type="button" id="addBtn" onclick="javascript:loadRight('<%=basePath %>/views/recharge/set_add.jsp')" class="am-btn am-btn-default"><span class="am-icon-plus"></span> 新增</button>
	            </div>
            </div>
        </div>
    </div>
	<form class="am-form am-form-horizontal" id="queryForm" role="form" action="<%=basePath %>/rechargeSet/list">
		<input type="hidden" id="page" name="page" value="${page.pageNumber}">
	</form>
	<div class="tpl-block">
		<div class="am-g">
			<div class="am-u-sm-12 am-scrollable-horizontal">
				<table
					class="am-table am-table-striped am-table-hover table-main am-text-nowrap">
					<thead>
						<tr>
							<th>充值金额</th>
							<th>赠送金额</th>
							<th>状态</th>
							<th>创建时间</th>
							<th>备注</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="item">
							<tr>
								<td>${item.rechargeMoney }</td>
                                <td>${item.give }</td>
								<td>
								    <c:if test="${item.state}">有效</c:if>
                                    <c:if test="${!item.state}">无效</c:if>
                                </td>
                                <td><fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd HH:mm" /></td>
								<td>${item.giveRemark }</td>
								<td>
                                    <div class="am-btn-toolbar">
                                        <div class="am-btn-group am-btn-group-xs">
                                             <button type="button" name="editBtn" onclick="javascript:loadRight('<%=basePath %>/rechargeSet/toEdit?id=${item.id}')" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">修改</button>
                                            <c:if test="${item.state  }">
                                              <button type="button" name="disableBtn" onclick="disable(${item.id })" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">禁用</button>
                                            </c:if>   
                                            <c:if test="${!item.state  }">
                                              <button type="button" name="enableBtn" onclick="enable(${item.id })" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">启用</button>
                                            </c:if>             
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

<script src="<%=basePath %>/js/rechargeSet.js"></script>