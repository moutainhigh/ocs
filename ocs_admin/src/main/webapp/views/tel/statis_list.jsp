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
							<td>已采集：</td>
						<c:forEach items="${statisCollection}" var="item">
							<td><c:if test="${item.platform eq '1'}">支付宝 </c:if>
								<c:if test="${item.platform eq '2'}">QQ </c:if>
								<c:if test="${item.platform eq '3'}">陆金所 </c:if>
								:${item.telCount }
							</td>
						</c:forEach>
					  </tr>
					   <tr>
							<td>未采集:</td>
						<c:forEach items="${statisUnCollection}" var="item">
							<td><c:if test="${item.platform eq '1'}">支付宝 </c:if>
								<c:if test="${item.platform eq '2'}">QQ </c:if>
								<c:if test="${item.platform eq '3'}">陆金所 </c:if>
								:${item.telCount }
							</td>
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
                    </select>
                </div>
            </div>  
            
            <div class="am-u-lg-3 am-u-end">
                <label for="state" class="am-u-sm-4 am-form-label">采集平台：</label> 
                <div class="am-input-group am-u-sm-8"> 
                    <select id="platform" name="platform" class="inline-block">
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
                    <select id="city" name="city" class="inline-block">
                        <option value="">-请选择-</option>
                        <option value="1" <c:if test="${city eq '1' }">selected</c:if>>是</option>
                        <option value="0" <c:if test="${city eq '0' }">selected</c:if>>否</option>
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
                <label for="profession" class="am-u-sm-4 am-form-label">职业：</label>
                <div class="am-input-group">
                    <input type="text" class="am-form-field" name="profession" value="${profession}" placeholder="职业">
                </div>
            </div>
            
         </div>
         
         <div class="am-g tpl-amazeui-form">
          	<div class="am-u-lg-3 am-u-end">
                <label for="profession" class="am-u-sm-4 am-form-label">学历：</label>
                <div class="am-input-group">
                    <input type="text" class="am-form-field" name="education" value="${education}" placeholder="学历">
                </div>
            </div>
            
             <div class="am-u-lg-3 am-u-end">
                <button class="am-btn am-btn-secondary am-radius" type="button" onclick="doStatis();">开始统计</button>
                <button class="am-btn am-btn-secondary am-radius" type="button" onclick="exportTxt();">导出</button>
            </div>
         </div>  
            
         
         <div class="am-g tpl-amazeui-form">
		    <div class="am-u-lg-4 am-u-end">
                <label for="state" class="am-u-sm-6 am-form-label">符合条件共：${sum}</label> 
                <label for="state" class="am-u-sm-6 am-form-label">统计时间：<fmt:formatDate value="${job.create_time }" pattern="yyyy-MM-dd HH:mm" /></label> 
                <label for="state" class="am-u-sm-6 am-form-label">统计耗时：${job_time}</label> 
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
							<c:if test="${item.city != 'sum' }">
							<tr>
								<td>${item.city }</td>
                                <td>${item.telCount } | <fmt:formatNumber type="number" value="${item.telCount / sum * 100 }" pattern="0.00" maxFractionDigits="2"/>%</td>
							</tr>
							</c:if>
						</c:forEach>
					</tbody>
				</table>
				<hr>
			</div>
		</div>
	</div>
</div>

<!-- 导出TXT -->
				<div class="am-modal am-modal-confirm" tabindex="-1" id="my-popup-exportTxt">
                    <div class="am-modal-dialog">
                        <div class="am-modal-hd">导出TXT（选择导出列）：</div>
                        <div class="am-modal-bd">
                            <div class="am-form-group">
						      <label class="am-checkbox-inline">
						        <input type="checkbox" name="checkCol" value="tel" checked="checked" disabled="disabled"> 手机号码
						      </label>
						      <label class="am-checkbox-inline">
						        <input type="checkbox" name="checkCol" value="tel_province"> 手机归属(省份)
						      </label>
						      <label class="am-checkbox-inline">
						        <input type="checkbox" name="checkCol" value="tel_city"> 手机归属(城市)
						      </label>
						       <label class="am-checkbox-inline">
						        <input type="checkbox" name="checkCol" value="tel_area_code"> 区号
						      </label>
						       <label class="am-checkbox-inline">
						        <input type="checkbox" name="checkCol" value="tel_operator"> 运营商
						      </label>
						    </div>
						    
						    <div class="am-form-group">
						      <label class="am-checkbox-inline">
						        <input type="checkbox" name="checkCol" value="platform_collection"> 采集平台
						      </label>
						      <label class="am-checkbox-inline">
						        <input type="checkbox" name="checkCol2" value="qq"> QQ
						      </label>
						      <label class="am-checkbox-inline">
						        <input type="checkbox" name="checkCol" value="qq_nickname"> QQ昵称
						      </label>
						       <label class="am-checkbox-inline">
						        <input type="checkbox" name="checkCol" value="sex"> 性别
						      </label>
						       <label class="am-checkbox-inline">
						        <input type="checkbox" name="checkCol" value="age"> 年龄
						      </label>
						    </div>
						    
						    <div class="am-form-group">
						      <label class="am-checkbox-inline">
						        <input type="checkbox" name="checkCol" value="addr"> 地址
						      </label>
						      <label class="am-checkbox-inline">
						        <input type="checkbox" name="checkCol" value="register"> 是否注册
						      </label>
						      <label class="am-checkbox-inline">
						        <input type="checkbox" name="checkCol" value="alipay_name"> 支付宝名称
						      </label>
						       <label class="am-checkbox-inline">
						        <input type="checkbox" name="checkCol2" value="trueName"> 真实姓名
						      </label>
						       <label class="am-checkbox-inline">
						        <input type="checkbox" name="checkCol2" value="idCard"> 身份证
						      </label>
						    </div>
						    
						     <div class="am-form-group">
						      <label class="am-checkbox-inline">
						        <input type="checkbox" name="checkCol2" value="userAccount"> 账号
						      </label>
						      <label class="am-checkbox-inline">
						        <input type="checkbox" name="checkCol2" value="userAccountPwd"> 密码
						      </label>
						      <label class="am-checkbox-inline">
						        <input type="checkbox" name="checkCol2" value="email"> 邮箱
						      </label>
						       <label class="am-checkbox-inline">
						        <input type="checkbox" name="checkCol2" value="profession"> 职业
						      </label>
						       <label class="am-checkbox-inline">
						        <input type="checkbox" name="checkCol2" value="education"> 学历
						      </label>
						    </div>
						    
						    <div class="am-form-group">
						      <label for="doc-ipt-email-1">导出数量</label>
						      <input type="text" id="exportLimit" placeholder="输入导出数量,默认10000">
						    </div>
						    
                        </div>
                        <div class="am-modal-footer">
                            <span class="am-modal-btn" data-am-modal-cancel>取消</span> <span
                                class="am-modal-btn" data-am-modal-confirm>确定</span>
                        </div>
                    </div>
                </div>

<script>
$(function() {
	initQueryForm();
});

function doStatis(){
	$("#queryForm").submit();
}

/**
 * 导出txt
 */
function exportTxt() {
	$('#my-popup-exportTxt').modal({
        relatedTarget: this,
        onConfirm: function(options) {
        	var dataStr = $('#queryForm').formSerialize();
        	var col_array=new Array();  
        	$('input[name="checkCol"]:checked').each(function(){  
        		col_array.push($(this).val());//向数组中添加元素  
        	});  
        	var colStr=col_array.join(',');//将数组元素连接起来以构建一个字符串
        	var exportLimit = $("#exportLimit").val();
        	var col_array2=new Array();  
        	$('input[name="checkCol2"]:checked').each(function(){  
        		col_array2.push($(this).val());//向数组中添加元素  
        	});  
        	var colStr2=col_array2.join(',');//将数组元素连接起来以构建一个字符串
        	if(col_array2.length>0){
        		colStr += ",col1&col2="+colStr2;
        	}
        	dataStr += "&col="+colStr+"&exportLimit="+exportLimit;
        	$('#my-modal-loading').modal('open');
        	$.ajax({
        		url : getRootPath() + "/telStatis/exportTxt",
        		data : dataStr
        	}).done(function(data) {
        		$('#my-modal-loading').modal('close');
        		window.open(getRootPath()+"/导出数据.txt");
        	});
        }
      });
}

</script>