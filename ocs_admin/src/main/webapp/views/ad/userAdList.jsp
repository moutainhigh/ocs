<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>

<div class="tpl-portlet-components">
    <div class="portlet-title">
        <div class="caption font-green bold">
            <div class="am-btn-toolbar">
	            <div class="am-btn-group am-btn-group-xs">
	                <button type="button" id="addBtn" class="am-btn am-btn-default"><span class="am-icon-plus"></span> 新增</button>
	                <button type="button" id="clearBtn" onclick="clearBtn()" class="am-btn am-btn-default"><span class="am-icon-refresh"></span> 清除数据</button>
	            </div>
            </div>
        </div>
    </div>
	<form class="am-form am-form-horizontal form-border" id="queryForm" role="form" action="<%=basePath %>/ad/userAdList">
		<input type="hidden" id="page" name="page" value="${page.pageNumber}">
		<div class="am-g tpl-amazeui-form">
            <div class="am-u-lg-4">
                <label for="userName" class="am-u-sm-4 am-form-label">用户名：</label>
                <div class="am-input-group">
                    <input type="text" class="am-form-field" name="userName" value="${userName}" placeholder="请输入用户名">
                </div>
            </div>
            
            <div class="am-u-lg-4">
                <label for="projectId" class="am-u-sm-4 am-form-label">内容：</label>
                <div class="am-input-group">
                    <input type="text" class="am-form-field" name="content" value="${content}" placeholder="请输入内容">
                </div>
            </div>
            
            <div class="am-u-lg-4">
                <label for="date" class="am-u-sm-4 am-form-label">日期：</label>
                <div class="am-input-group">
                    <input type="text" class="am-form-field" placeholder="日期" name="date" value="${date}" id="date">
                </div>
            </div>

             <div class="am-u-lg-4 am-u-end">
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
							<th>用户</th>
							<th>内容</th>
							<th>创建时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="item">
							<tr>
								<td>${item.id }</td>
                                <td>${item.user_name==null?"admin": item.user_name}</td>
                                <td>${item.content }</td>
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