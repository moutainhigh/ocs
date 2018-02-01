<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>

<div class="tpl-portlet-components">
    <div class="tpl-block">
        <div class="am-g tpl-amazeui-form">
            <div class="am-u-sm-12 am-u-md-9">
                <form id="dataForm" action="<%=basePath%>/recharge/save" method="POST" class="am-form am-form-horizontal">
                    <div class="am-form-group">
                        <label for="userName" class="am-u-sm-3 am-form-label">用户名</label>
                        <div class="am-u-sm-9">
                            <input class="am-form-field" type="text" name="userName" required="required">
                        </div>
                    </div>
                    
                    <div class="am-form-group">
                        <label for="type" class="am-u-sm-3 am-form-label">充值类型</label>
                        <div class="am-u-sm-9">
                            <select name="type" required="required">
                                    <option value="1" selected>支付宝</option>                           
                                    <option value="2">微信</option>                           
                            </select>
                        </div>
                    </div>
                    
                    <div class="am-form-group">
                        <label for="money" class="am-u-sm-3 am-form-label">金额</label>
                        <div class="am-u-sm-9">
                            <input class="am-form-field" type="text" name="money" maxlength="8" required="required"/>
                        </div>
                    </div>
                    
                    <div class="am-form-group">
                        <label for="orderCode" class="am-u-sm-3 am-form-label">订单号</label>
                        <div class="am-u-sm-9">
                            <input class="am-form-field" type="text" name="orderCode" maxlength="50" required="required"/>
                        </div>
                    </div>
                    
                    <div class="am-form-group">
                        <label for="remark" class="am-u-sm-3 am-form-label">备注</label>
                        <div class="am-u-sm-9">
                            <textarea  name="remark" rows="10" maxlength="500"></textarea>
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

<script>
  $(function() {
    $("#dataForm").submit(function() {
        $(this).ajaxSubmit({
            method:"POST",
            data:$('#dataForm').formSerialize(),
            success:function(data) {
                alert(data.resultDes);
                if(data.resultCode == '1'){
                    loadRight(getRootPath()+"/recharge/list");
                }
            }
        });
        return false;
    });
  });
</script>   
