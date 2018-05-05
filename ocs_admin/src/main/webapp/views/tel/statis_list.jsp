<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>

<div class="tpl-portlet-components">

	<div class="tpl-block">
		<div class="am-g">
			<div class="am-u-sm-12 am-scrollable-horizontal">
				<table
					class="am-table am-table-striped am-table-hover table-main am-text-nowrap">
					<tbody>
					  <tr>
						<c:forEach items="${statisCollection}" var="item">
							<td>已采集：</td>
							<td><c:if test="${item.item eq '1'}">支付宝 </c:if>
								<c:if test="${item.item eq '2'}">QQ </c:if>
								<c:if test="${item.item eq '3'}">陆金所 </c:if>
							</td>
                            <td>${item.count }</td>
						</c:forEach>
					  </tr>
					   <tr>
						<c:forEach items="${statisUnCollection}" var="item">
							<td>未采集</td>
							<td><c:if test="${item.item eq '1'}">支付宝 </c:if>
								<c:if test="${item.item eq '2'}">QQ </c:if>
								<c:if test="${item.item eq '3'}">陆金所 </c:if>
							</td>
                            <td>${item.count }</td>
						</c:forEach>
					  </tr>
					</tbody>
				</table>
				<hr>
			</div>
		</div>
	</div>
	<form class="am-form am-form-horizontal" id="queryForm" role="form" action="<%=basePath %>/telStatis/statis">
		<div class="am-g tpl-amazeui-form">
		    <div class="am-u-lg-3 am-u-end">
                <label for="state" class="am-u-sm-4 am-form-label">是否采集：</label> 
                <div class="am-input-group am-u-sm-8"> 
                    <select id="collectionType" name="collectionType" class="inline-block">
                        <option value="1" <c:if test="${collectionType}">selected</c:if>>采集</option>
                        <option value="0" <c:if test="${!collectionType}">selected</c:if>>未采集</option>
                    </select>
                </div>
            </div>  
            
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
		      
            <div class="am-u-lg-3 am-u-end">
                <label for="register" class="am-u-sm-4 am-form-label">精确到市：</label> 
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
            
             <div class="am-u-lg-3 am-u-end">
                <button class="am-btn am-btn-secondary am-radius" type="button" onclick="doQuery();">查询</button>
            </div>
         </div>
         
         <div class="am-g tpl-amazeui-form">
		    <div class="am-u-lg-3 am-u-end">
                <label for="state" class="am-u-sm-4 am-form-label">符合条件共：${sum}</label> 
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
							<th>地区</th>
							<th>符合条件数</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${statisSearch}" var="item">
							<tr>
								<td>${item.key }</td>
                                <td>${item.value } | <fmt:formatNumber type="number" value="${item.value / sum * 100 }" pattern="0.00" maxFractionDigits="2"/>%</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<hr>
			</div>
		</div>
	</div>
</div>

<script>
$(function() {
	initQueryForm();
});
</script>