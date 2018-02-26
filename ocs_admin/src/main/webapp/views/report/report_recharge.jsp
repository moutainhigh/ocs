<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>

<div class="tpl-portlet-components">
    <form class="am-form am-form-horizontal form-border" id="queryForm" role="form" action="<%=basePath %>/report/rechargeList">
        <input type="hidden" id="page" name="page" value="${page.pageNumber}">
        <div class="am-g tpl-amazeui-form">
            <div class="am-u-lg-4">
                <label for="date" class="am-u-sm-4 am-form-label">日期：</label>
                <div class="am-input-group">
                    <input type="text" class="am-form-field" placeholder="日期" name="date" value="${date}" id="date">
                </div>
            </div>

             <div class="am-u-lg-2 am-u-end">
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
                            <th>日期</th>
                            <th>充值总金额</th>
                            <th>次数</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>总收益：${sumMoney}</td>
                            <td>最近7天收益：${sumMoney7day }</td>
                            <td></td>
                        </tr>
                        <c:forEach items="${page.list}" var="item">
                            <tr>
                                <td><fmt:formatDate value="${item.reportDate }" pattern="yyyy-MM-dd" /></td>
                                <td>${item.sumRechargeMoney }</td>
                                <td>${item.countRecharge }</td>
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
    initQueryForm();
  //日期时间选择器（开始、结束）
    $("#date").datepicker({
        language: 'zh-CN', 
        format: "yyyy-mm-dd",
        autoclose: true,
        maxView: "decade",
        todayBtn: true,
        pickerPosition: "bottom-left"
    })
});
</script>