<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>

<div class="tpl-portlet-components">
	<div class="tpl-block">
		<div class="am-g">
			<div class="am-u-sm-12 am-scrollable-horizontal">
				<table
					class="am-table am-table-striped am-table-hover table-main am-text-nowrap">
					<thead>
						<tr>
							<th>项目</th>
							<th>今天(成功|失败)</th>
							<th>昨天(成功|失败)</th>
							<th>前天(成功|失败)</th>
							<th>总计(成功|失败)</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="item">
							<tr>
								<td>${item.projectName }</td>
								<td>${item.todaySuccess }|${item.todayFail }</td>
								<td>${item.yesterDaySuccess }|${item.yesterDayFail }</td>
								<td>${item.beforeYesterDaySuccess }|${item.beforeYesterDayFail }</td>
								<td>${item.allSuccess }|${item.allFail }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<hr>
			</div>
		</div>
	</div>
</div>