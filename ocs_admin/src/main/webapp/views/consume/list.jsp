<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>

<div class="tpl-portlet-components">
	<form class="am-form am-form-horizontal form-border" id="queryForm" role="form" action="<%=basePath %>/consume/list">
		<input type="hidden" id="page" name="page" value="${page.pageNumber}">
		<div class="am-g tpl-amazeui-form">
			<div class="am-u-lg-3">
				<label for="userName" class="am-u-sm-4 am-form-label">用户名：</label>
				<div class="am-input-group">
					<input type="text" class="am-form-field" name="userName" value="${userName}" placeholder="请输入用户名">
				</div>
			</div>
			
			<div class="am-u-lg-3">
                <label for="projectId" class="am-u-sm-4 am-form-label">项目名：</label>
                <div class="am-input-group">
                    <input type="text" class="am-form-field" name="projectName" value="${projectName}" placeholder="输入项目名称或'开通套餐'">
                </div>
            </div>
            
            <div class="am-u-lg-3">
                <label for="date" class="am-u-sm-4 am-form-label">日期：</label>
                <div class="am-input-group">
                    <input type="text" class="am-form-field" placeholder="日期" name="date" value="${date}" id="datetimeStart">
                </div>
            </div>

             <div class="am-u-lg-3 am-u-end">
                <button class="am-btn am-btn-secondary am-radius" type="button" onclick="doQuery();">查询</button>
                <button class="am-btn am-btn-default" type="button" onclick="back('<%=basePath %>/project/list')">返回</button>
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
							<th>日期</th>
							<th>用户名</th>
							<th>项目ID</th>
							<th>项目名</th>
							<th>金额</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="item">
							<tr>
								<td><fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd HH:mm" /></td>
                                <td>${item.userName }</td>
                                <td>${item.projectId }</td>
                                <td>${item.projectName }</td>
                                <td>${item.money }</td>
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

<script>
$(function() {
    initPage();//初始化分页
    initQueryForm();
    
    //日期时间选择器（开始、结束）
    $("#datetimeStart").datepicker({
        language: 'zh-CN', 
        format: "yyyy-mm-dd",
        autoclose: true,
        maxView: "decade",
        todayBtn: true,
        pickerPosition: "bottom-left"
    })
});
</script>