<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>

<div class="tpl-portlet-components">
    <div class="tpl-block">
        <div class="am-g tpl-amazeui-form">
            <div class="am-u-sm-12 am-u-md-9">
                <form id="dataForm" action="<%=basePath%>/project/edit" method="POST" class="am-form am-form-horizontal">
                    <input type="hidden" name="id" value="${item.id }">
                    <div class="am-form-group">
                        <label for="projectName" class="am-u-sm-3 am-form-label">项目名</label>
                        <div class="am-u-sm-9">
                            <input class="am-form-field" type="text" name="projectName" value="${item.projectName }" required="required">
                        </div>
                    </div>
                    
                    <div class="am-form-group">
                        <label for="price" class="am-u-sm-3 am-form-label">价格（￥/次）</label>
                        <div class="am-u-sm-9">
                            <input class="am-form-field" type="text" name="price" value="${item.price }" min="1" max="10" required="required"/>
                        </div>
                    </div>
                    
                    <div class="am-form-group">
                        <label for="remark" class="am-u-sm-3 am-form-label">备注</label>
                        <div class="am-u-sm-9">
                            <textarea  name="remark" rows="10" >${item.remark }</textarea>
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
                    loadRight(getRootPath()+"/project/list");
                }
            }
        });
        return false;
    });
  });
</script>   
