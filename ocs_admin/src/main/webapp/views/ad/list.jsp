<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>

<div class="tpl-portlet-components">
    <div class="portlet-title">
        <div class="caption font-green bold">
            <div class="am-btn-toolbar">
	            <div class="am-btn-group am-btn-group-xs">
	                <button type="button" id="addBtn" class="am-btn am-btn-default"><span class="am-icon-plus"></span> 新增</button>
	            </div>
            </div>
        </div>
    </div>
	<form class="am-form am-form-horizontal" id="queryForm" role="form" action="<%=basePath %>/ad/list">
		<input type="hidden" id="page" name="page" value="${page.pageNumber}">
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
							<th>调用次数</th>
							<th>创建时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="item">
							<tr>
								<td>${item.id }</td>
                                <td>${item.content }</td>
								<td>${item.count_call }</td>
								<td><fmt:formatDate value="${item.create_time }" pattern="yyyy-MM-dd HH:mm" /></td>
								<td>
									<div class="am-btn-toolbar">
										<div class="am-btn-group am-btn-group-xs">
										    <button type="button" name="editBtn" data-id="${item.id }" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">修改</button>
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

<div class="am-modal am-modal-prompt" tabindex="-1" id="add-prompt">
                    <div class="am-modal-dialog">
                        <div class="am-modal-hd">新增广告</div>
                        <div class="am-modal-bd">
                            <textarea  id="content" cols="50" rows="10" ></textarea>
                        </div>
                        <div class="am-modal-footer">
                            <span class="am-modal-btn" data-am-modal-cancel>取消</span> <span
                                class="am-modal-btn" data-am-modal-confirm>提交</span>
                        </div>
                    </div>
                </div>
                
<div class="am-modal am-modal-prompt" tabindex="-1" id="edit-prompt">
                    <div class="am-modal-dialog">
                        <div class="am-modal-hd">修改广告</div>
                        <div class="am-modal-bd">
                            <textarea  id="content2" cols="50" rows="10" ></textarea>
                        </div>
                        <div class="am-modal-footer">
                            <span class="am-modal-btn" data-am-modal-cancel>取消</span> <span
                                class="am-modal-btn" data-am-modal-confirm>提交</span>
                        </div>
                    </div>
                </div>

<script src="<%=basePath %>/js/ad.js"></script>