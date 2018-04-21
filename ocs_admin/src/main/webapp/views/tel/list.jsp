<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>

<div class="tpl-portlet-components">
	<form class="am-form am-form-horizontal" id="queryForm" role="form" action="<%=basePath %>/tel/list">
		<div class="am-g tpl-amazeui-form">
		    <div class="am-u-lg-3 am-u-end">
                <label for="state" class="am-u-sm-4 am-form-label">运营商：</label> 
                <div class="am-input-group am-u-sm-8"> 
                    <select id="operator" name="operator" class="inline-block">
                        <option value="">-请选择-</option>
                        <option value="中国联通" <c:if test="${operator eq '中国联通' }">selected</c:if>>中国联通</option>
                        <option value="中国移动" <c:if test="${operator eq '中国移动' }">selected</c:if>>中国移动</option>
                        <option value="中国电信" <c:if test="${operator eq '中国电信' }">selected</c:if>>中国电信</option>
                    </select>
                </div>
            </div>  
		      
            <div class="am-u-lg-3">
                <label for="userName" class="am-u-sm-4 am-form-label">手机号码：</label>
                <div class="am-input-group">
                    <input type="text" class="am-form-field" name="tel" value="${tel}" placeholder="请输入手机号码">
                </div>
            </div>
            
            <div class="am-u-lg-3">
                <label for="projectId" class="am-u-sm-4 am-form-label">省份：</label>
                <div class="am-input-group">
                    <input type="text" class="am-form-field" name="province" value="${province}" placeholder="请输入归属地省份">
                </div>
            </div>
            
            <div class="am-u-lg-3">
                <label for="projectId" class="am-u-sm-4 am-form-label">城市：</label>
                <div class="am-input-group">
                    <input type="text" class="am-form-field" name="city" value="${city}" placeholder="请输入归属地城市">
                </div>
            </div>
            
        </div>
        
        <div class="am-g tpl-amazeui-form">
            <div class="am-u-lg-3 am-u-end">
                <label for="state" class="am-u-sm-4 am-form-label">采集平台：</label> 
                <div class="am-input-group am-u-sm-8"> 
                    <select id="platform" name="platform" class="inline-block">
                        <option value="">-请选择-</option>
                        <option value="支付宝" <c:if test="${platform eq '1' }">selected</c:if>>支付宝</option>
                        <option value="QQ" <c:if test="${platform eq '2' }">selected</c:if>>QQ</option>
                    </select>
                </div>
            </div>
            
             <div class="am-u-lg-3 am-u-end">
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
							<th>手机号码</th>
							<th>归属地</th>
							<th>区号</th>
							<th>运营商</th>
							<th>采集平台</th>
							<th>支付宝名称</th>
							<th>QQ昵称</th>
							<th>性别</th>
							<th>年龄</th>
							<th>地址</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="item">
							<tr>
								<td>${item.tel }</td>
                                <td>${item.tel_province } ${item.tel_city }</td>
								<td>${item.tel_area_code }</td>
								<td>${item.tel_operator }</td>
								<td>
								    <c:if test="${item.platform_collection eq 1 }">支付宝</c:if>
								    <c:if test="${item.platform_collection eq 2 }">QQ</c:if>
								    <c:if test="${empty item.platform_collection or item.platform_collection eq 0}">-</c:if>
								</td>
								<td>${item.alipay_name }</td>
								<td>${item.qq_nickname }</td>
								<td>${item.sex }</td>
								<td>${item.age }</td>
								<td>${item.addr }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<hr>
			</div>
		</div>
	</div>
</div>

<script src="<%=basePath %>/js/tel.js"></script>