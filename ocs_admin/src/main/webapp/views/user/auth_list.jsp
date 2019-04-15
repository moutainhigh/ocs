<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>

<div class="tpl-portlet-components">
    <div class="portlet-title">
        <div class="caption font-green bold">
            <div class="am-btn-toolbar">
	            <div class="am-btn-group am-btn-group-xs">
	                <button type="button" id="addBtn" onclick="initUserAuth()" class="am-btn am-btn-default"><span class="am-icon-plus"></span> 软件授权</button>
	                <button type="button" id="addBtn" onclick="javascript:back('${backUrl}')" class="am-btn am-btn-default"><span class="am-icon-backward"></span> 返回</button>
	            </div>
            </div>
        </div>
    </div>
	<form class="am-form am-form-horizontal" id="queryForm" role="form" action="<%=basePath %>/userAuth/userAuthList">
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
							<th>软件key</th>
							<th>软件名称</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="item">
							<tr>
								<td>${item.id }</td>
                                <td>${item.auth_key }</td>
                                <td>${item.auth_name }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<hr>
			</div>
		</div>
	</div>
</div>

<div class="am-modal am-modal-prompt" tabindex="-1" id="auth-prompt">
                    <div class="am-modal-dialog">
                        <div class="am-modal-hd">软件授权</div>
                        <div class="am-modal-bd">
                        	<div class="am-form-group" id="checkBoxDiv">
						      <!--<label class="am-checkbox-inline">
						        <input type="checkbox"  name="authKeys" value=""> 请选择
						      </label>-->
						    </div>
                        </div>
                        <div class="am-modal-footer">
                            <span class="am-modal-btn" data-am-modal-cancel>取消</span> <span
                                class="am-modal-btn" data-am-modal-confirm>提交</span>
                        </div>
                    </div>
                </div>

<script src="<%=basePath %>/js/userAuth.js"></script>