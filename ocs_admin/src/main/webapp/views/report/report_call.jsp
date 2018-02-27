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
								<td>${item.todaySuccess==null?0:item.todaySuccess  } | ${item.todayFail==null?0:item.todayFail  }</td>
								<td>${item.yesterDaySuccess==null?0:item.yesterDaySuccess  } | ${item.yesterDayFail==null?0:item.yesterDayFail  }</td>
								<td>${item.beforeYesterDaySuccess==null?0:item.beforeYesterDaySuccess  } | ${item.beforeYesterDayFail==null?0:item.beforeYesterDayFail  }</td>
								<td>${item.allSuccess==null?0:item.allSuccess  } | ${item.allFail==null?0:item.allFail  }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<hr>
			</div>
		</div>
	</div>
</div>