<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>

<div class="tpl-portlet-components">
    <div class="portlet-title">
        <div class="caption font-green bold">
            <div class="am-btn-toolbar">
	            <div class="am-btn-group am-btn-group-xs">
	                <button type="button" id="addBtn" onclick="addMeal()" class="am-btn am-btn-default"><span class="am-icon-plus"></span> 新增</button>
	                <button type="button" id="addBtn" onclick="javascript:back('${backUrl}')" class="am-btn am-btn-default"><span class="am-icon-backward"></span> 返回</button>
	            </div>
            </div>
        </div>
    </div>
	<form class="am-form am-form-horizontal" id="queryForm" role="form" action="<%=basePath %>/meal/userMealList">
		<input type="hidden" id="page" name="page" value="${page.pageNumber}">
		<input type="hidden" id="userName" name="userName" value="${userName}">
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
							<th>开始时间</th>
							<th>结束时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="item">
							<tr>
								<td>${item.id }</td>
                                <td>${item.meal_name }</td>
								<td><fmt:formatDate value="${item.create_time }" pattern="yyyy-MM-dd HH:mm" /></td>
								<td><fmt:formatDate value="${item.expir_date }" pattern="yyyy-MM-dd 00:00" /></td>
								<td>
									<div class="am-btn-toolbar">
										<div class="am-btn-group am-btn-group-xs">
										    <button type="button" name="editUserMealExpirDate" data-id="${item.id }" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">修改过期时间</button>
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

<div class="am-modal am-modal-prompt" tabindex="-1" id="editUserMealExpirDate-prompt">
                    <div class="am-modal-dialog">
                        <div class="am-modal-hd">修改过期时间</div>
                        <div class="am-modal-bd">
                            <input type="text" class="am-modal-prompt-input" value="" placeholder="请填入过期时间 " name="expirDate" id="expirDate" required="required">
                        </div>
                        <div class="am-modal-footer">
                            <span class="am-modal-btn" data-am-modal-cancel>取消</span> <span
                                class="am-modal-btn" data-am-modal-confirm>提交</span>
                        </div>
                    </div>
                </div>
                
<div class="am-modal am-modal-prompt" tabindex="-1" id="meal-prompt">
                    <div class="am-modal-dialog">
                        <div class="am-modal-hd">新增套餐</div>
                        <div class="am-modal-bd">
                            <select name="mealId" id="mealId" required="required">
                                  <option value="0" >请选择套餐</option>                           
                            </select>
                        </div>
                        <div class="am-modal-footer">
                            <span class="am-modal-btn" data-am-modal-cancel>取消</span> <span
                                class="am-modal-btn" data-am-modal-confirm>提交</span>
                        </div>
                    </div>
                </div>

<script src="<%=basePath %>/js/mealList.js"></script>