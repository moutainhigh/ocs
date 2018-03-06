<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>

<div class="tpl-portlet-components">
	<form class="am-form am-form-horizontal form-border" id="queryForm" role="form" action="<%=basePath %>/qq/list">
		<input type="hidden" id="page" name="page" value="${page.pageNumber}">
		<div class="am-g tpl-amazeui-form">
		    <div class="am-u-lg-4">
                <label for="qq" class="am-u-sm-4 am-form-label">QQ：</label>
                <div class="am-input-group">
                    <input type="text" class="am-form-field" placeholder="qq" name="qq" value="${qq}">
                </div>
            </div>  
			<div class="am-u-lg-4">
				<label for="date" class="am-u-sm-4 am-form-label">日期：</label>
				<div class="am-input-group">
					<input type="text" class="am-form-field" placeholder="日期" name="date" value="${date}" id="datetimeStart">
				</div>
			</div>

             <div class="am-u-lg-6 am-u-end">
                <button class="am-btn am-btn-secondary am-radius" type="button" onclick="doQuery();">查询</button>
                <button class="am-btn am-btn-secondary am-radius" type="button" onclick="clearQq();">清空数据</button>
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
							<th>QQ</th>
							<th>密码</th>
							<th>token</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="item">
							<tr>
                                <td><fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd HH:mm" /></td>
                                <td>${item.qq }</td>
								<td>${item.pwd }</td>
								<td>${item.token }</td>
								<td>
								    <div class="am-btn-toolbar">
                                        <div class="am-btn-group am-btn-group-xs">
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

<script src="<%=basePath %>/js/qq.js"></script>