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
                <label for="sex" class="am-u-sm-4 am-form-label">性别：</label> 
                <div class="am-input-group am-u-sm-8"> 
                    <select id="sex" name="sex" class="inline-block">
                        <option value="">-请选择-</option>
                        <option value="0" <c:if test="${sex eq '0' }">selected</c:if>>保密</option>
                        <option value="1" <c:if test="${sex eq '1' }">selected</c:if>>男</option>
                        <option value="2" <c:if test="${sex eq '2' }">selected</c:if>>女</option>
                    </select>
                </div>
            </div>  
              
            <div class="am-u-lg-3">
                <label for="age" class="am-u-sm-4 am-form-label">年龄：</label>
                <div class="am-input-group">
                    <input type="text" class="am-form-field" name="age" value="${age}" placeholder="格式：年龄段采用-分割如25-32">
                </div>
            </div>
            
            <div class="am-u-lg-3">
                <label for="alipayName" class="am-u-sm-4 am-form-label">支付宝名：</label>
                <div class="am-input-group">
                    <input type="text" class="am-form-field" name="alipayName" value="${alipayName}" placeholder="请输入支付宝名称">
                </div>
            </div>
            
            <div class="am-u-lg-3">
                <label for="qqNickName" class="am-u-sm-4 am-form-label">QQ昵称：</label>
                <div class="am-input-group">
                    <input type="text" class="am-form-field" name="qqNickName" value="${qqNickName}" placeholder="请输入QQ昵称">
                </div>
            </div>
            
        </div>
        
        <div class="am-g tpl-amazeui-form">
            <div class="am-u-lg-3 am-u-end">
                <label for="state" class="am-u-sm-4 am-form-label">采集平台：</label> 
                <div class="am-input-group am-u-sm-8"> 
                    <select id="platform" name="platform" class="inline-block">
                        <option value="">-请选择-</option>
                        <option value="1" <c:if test="${platform eq '1' }">selected</c:if>>支付宝</option>
                        <option value="2" <c:if test="${platform eq '2' }">selected</c:if>>QQ</option>
                        <option value="3" <c:if test="${platform eq '3' }">selected</c:if>>陆金所</option>
                    </select>
                </div>
            </div>
            
             <div class="am-u-lg-3">
                <label for="projectId" class="am-u-sm-4 am-form-label">查询数量：</label>
                <div class="am-input-group">
                    <input type="text" class="am-form-field" name="pageSize" value="${pageSize}" placeholder="请输入查询数量">
                </div>
             </div>
             
             <div class="am-u-lg-3">
                <label for="projectId" class="am-u-sm-4 am-form-label">当前页：</label>
                <div class="am-input-group">
                    <input type="text" class="am-form-field" name="pageNumber" value="${pageNumber}" placeholder="请输入当前页">
                </div>
             </div>
             <div class="am-u-lg-3 am-u-end">
                <label for="register" class="am-u-sm-4 am-form-label">是否注册：</label> 
                <div class="am-input-group am-u-sm-8"> 
                    <select id="register" name="register" class="inline-block">
                        <option value="">-请选择-</option>
                        <option value="1" <c:if test="${register eq '1' }">selected</c:if>>是</option>
                        <option value="0" <c:if test="${register eq '0' }">selected</c:if>>否</option>
                    </select>
                </div>
            </div>
        </div>
        
        <div class="am-g tpl-amazeui-form">
            <div class="am-u-lg-3 am-u-end">
                <label for="unplatform" class="am-u-sm-4 am-form-label">未采集：</label> 
                <div class="am-input-group am-u-sm-8"> 
                    <select id="unplatform" name="unplatform" class="inline-block">
                        <option value="">-请选择-</option>
                        <option value="1" <c:if test="${unplatform eq '1' }">selected</c:if>>支付宝</option>
                        <option value="2" <c:if test="${unplatform eq '2' }">selected</c:if>>QQ</option>
                        <option value="3" <c:if test="${unplatform eq '3' }">selected</c:if>>陆金所</option>
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
							<th>是否注册</th>
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
								    <c:if test="${fn:contains(item.col1, '1')}">支付宝 </c:if>
								    <c:if test="${fn:contains(item.col1, '2')}">QQ </c:if>
								    <c:if test="${fn:contains(item.col1, '3')}">陆金所 </c:if>
								    <c:if test="${empty item.col1 or item.col1 eq '0'}">-</c:if>
								</td>
								<td>${item.alipay_name }</td>
								<td>${item.qq_nickname }</td>
								<td>
                                    <c:if test="${item.sex eq 1}">男 </c:if>
                                    <c:if test="${item.sex eq 2}">女 </c:if>
                                    <c:if test="${item.sex eq 0}">保密 </c:if>
                                    <c:if test="${empty item.sex}">-</c:if>
                                </td>
								<td>${item.ageStr }</td>
								<td>${item.addr }</td>
								<td>
                                     <c:if test="${item.col2 eq '1'}">注册</c:if>
                                    <c:if test="${empty item.col2}">未注册</c:if>
                                </td>
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