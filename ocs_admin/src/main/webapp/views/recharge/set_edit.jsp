<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>

<div class="tpl-portlet-components">
    <div class="tpl-block">
        <div class="am-g tpl-amazeui-form">
            <div class="am-u-sm-12 am-u-md-9">
                <form id="dataForm" action="<%=basePath%>/rechargeSet/edit" method="POST" class="am-form am-form-horizontal">
                    <input type="hidden" name="id" value="${item.id }">
                    
                    <div class="am-form-group">
                        <label for="rechargeMoney" class="am-u-sm-3 am-form-label">充值金额</label>
                        <div class="am-u-sm-9">
                            <input class="am-form-field" type="text" name="rechargeMoney" value="${item.rechargeMoney }" maxlength="8" required="required">
                        </div>
                    </div>
                    
                    <div class="am-form-group">
                        <label for="give" class="am-u-sm-3 am-form-label">赠送金额</label>
                        <div class="am-u-sm-9">
                            <input class="am-form-field" type="text" name="give" value="${item.give }" maxlength="8" required="required"/>
                        </div>
                    </div>
                    
                    <div class="am-form-group">
                        <label for="remark" class="am-u-sm-3 am-form-label">备注</label>
                        <div class="am-u-sm-9">
                            <textarea  name="remark" rows="10" maxlength="100">${item.giveRemark }</textarea>
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
                    loadRight(getRootPath()+"/rechargeSet/list");
                }
            }
        });
        return false;
    });
  });
</script>   
