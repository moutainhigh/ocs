<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>

<div class="tpl-portlet-components">
    <div class="tpl-block">
        <div class="am-g tpl-amazeui-form">
        	<div class="am-u-sm-12 am-u-md-9">
            	<form id="dataForm" action="<%=basePath%>/userAuth/batchUpdate" method="POST" class="am-form am-form-horizontal">
                    <div class="am-form-group">
		    			<label for="qqData" class="am-u-sm-3 am-form-label">录入格式&nbsp;&nbsp;<a href="javascript:$('#doc').modal('open');">格式说明</a></label>
		    			<div class="am-u-sm-9">
		    				<textarea  name="userNames" id="qqData" rows="10" >${userName }</textarea>
		    			</div>
					</div>
					
					<div class="am-form-group">
                        <label for="mealName" class="am-u-sm-3 am-form-label">状态</label>
                        <div class="am-u-sm-9">
                            <input type="radio" name="state" value="0"/> 冻结
                            <input type="radio" name="state" value="1"/> 启用
                        </div>
                    </div>
                    
                    <div class="am-form-group">
                        <label for="mealTime" class="am-u-sm-3 am-form-label">过期时间</label>
                        <div class="am-u-sm-9">
                            <input type="text" class="am-modal-prompt-input" value="" placeholder="请填入过期时间 " name="expirDate" id="expirDate">
                        </div>
                    </div>
                    
                     <div class="am-form-group">
                        <label for="mealName" class="am-u-sm-3 am-form-label">余额</label>
                        <div class="am-u-sm-9">
                            <input class="am-form-field" type="text" name="money" />
                        </div>
                     </div>
                   
					
					<div class="am-form-group" id="authCheckBoxDiv">
						<label for="mealName" class="am-u-sm-3 am-form-label">软件授权</label>
						<div class="am-u-sm-9">
							<c:forEach items="${authList}" var="item">
							      <label class="am-checkbox-inline">
							        <input type="checkbox"  name="authKeys" value="${item.auth_key}"> ${item.auth_name}
							      </label>
							</c:forEach>
						</div>
					</div>
					
					<div class="am-form-group" id="mealCheckBoxDiv">
						<label for="mealName" class="am-u-sm-3 am-form-label">选择套餐</label>
						<div class="am-u-sm-9">
							<c:forEach items="${mealList}" var="item">
							      <label class="am-checkbox-inline">
							        <input type="checkbox"  name="mealIds" value="${item.id}"> ${item.meal_name}
							      </label>
							</c:forEach>
						</div>
					</div>
						    
					<div class="am-form-group">
						<div class="am-u-sm-9 am-u-sm-push-3">
		  					<input type="submit" class="am-btn am-btn-primary" value="提交">
		  					<input type="button" onclick="javascript:back('${backUrl}')" class="am-btn am-btn-default" value="返回">
	  					</div>
  					</div>
  				</form>	
          	</div>
      	</div>
  	</div>
</div>

<div class="am-modal am-modal-no-btn" tabindex="-1" id="doc">
  <div class="am-modal-dialog">
    <div class="am-modal-hd"><h1>录入格式</h1>
      <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
    </div>
    <div class="am-modal-bd">
<p>注1：用户名重复或系统存在会跳过</p>
<p>注：每个用户名使用英文,隔开,支持多行</p>
<p>格式如下：<br>
  用户A,用户B<br>
  用户C,用户D<br>
 <p>注2：支持号码段</p>
<p>格式如下：<br>
  开始,结束（如：1001-1010）<br>
    </div>
  </div>
</div>

<script>
  $(function() {
	//日期时间选择器
		$("#expirDate").datepicker({
		    language: 'zh-CN', 
		    format: "yyyy-mm-dd",
		    autoclose: true,
		    maxView: "decade",
		    todayBtn: true,
		    pickerPosition: "bottom-left"
		})
	  
    $("#dataForm").submit(function() {
		$(this).ajaxSubmit({
			method:"POST",
			data:$('#dataForm').formSerialize(),
			success:function(data) {
				alert(data.resultDes);
				loadRight(getRootPath()+"/user/list");
			}
		});
		return false;
	});
  });
</script>  	
