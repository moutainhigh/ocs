<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>

<div class="tpl-portlet-components">
    <form class="am-form am-form-horizontal form-border" id="queryForm" role="form" action="<%=basePath %>/user/list">
        <input type="hidden" id="page" name="page" value="${page.pageNumber}">
        <div class="am-g tpl-amazeui-form">
            <div class="am-u-lg-4">
                <label for="userName" class="am-u-sm-4 am-form-label">用户名：</label>
                <div class="am-input-group">
                    <input type="text" class="am-form-field" name="userName" value="${userName}" placeholder="请输入用户名">
                </div>
            </div>

            <div class="am-u-lg-4 am-u-end">
                <label for="state" class="am-u-sm-4 am-form-label">状态：</label> 
                <div class="am-input-group am-u-sm-8"> 
                    <select id="state" name="state" class="inline-block">
                        <option value="">-请选择-</option>
                        <option value="1" <c:if test="${state == 1 }">selected</c:if>>正常</option>
                        <option value="0" <c:if test="${not empty state and state != 1}">selected</c:if>>冻结</option>
                    </select>
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
                            <th>编号</th>
                            <th>用户名</th>
                            <th>手机</th>
                            <th>注册时间</th>
                            <th>余额</th>
                            <th>累计消费</th>
                            <th>最后消费时间</th>
                            <th>状态</th>
                            <th>所属代理</th>
                            <th>登录ip</th>
                            <th>最后登录时间</th>
                            <th>过期时间</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${page.list}" var="item">
                            <tr>
                                <td>${item.id }</td>
                                <td>${item.user_name }</td>
                                <td>${item.mobile }</td>
                                <td><fmt:formatDate value="${item.create_time }" pattern="yyyy-MM-dd HH:mm" /></td>
                                <td>${item.account }￥</td>
                                <td>${item.consumed_sum }￥</td>
                                <td><fmt:formatDate value="${item.last_consumed_time }" pattern="yyyy-MM-dd HH:mm" /></td>
                                <td>
                                    <c:if test="${item.state  }">正常</c:if>
                                    <c:if test="${!item.state  }">冻结</c:if>
                                </td>
                                <td>${item.agent_id }</td>
                                <td>${item.login_ip }</td>
                                <td><fmt:formatDate value="${item.login_time }" pattern="yyyy-MM-dd HH:mm" /></td>
                                <td><fmt:formatDate value="${item.expir_date }" pattern="yyyy-MM-dd 00:00" /></td>
                                <td>
                                    <div class="am-btn-toolbar">
                                        <div class="am-btn-group am-btn-group-xs">
                                            <button type="button" name="editExpirDate" data-id="${item.id }"  data-expirDate="${item.expir_data }" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">修改过期时间</button>
                                            <button type="button" name="editAccountDate" data-username="${item.user_name }" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">修改余额</button>
                                            <button type="button" name="resetPwdBtn" data-id="${item.id }" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">重置密码</button>
                                            <c:if test="${item.state  }">
                                              <button type="button" name="disableBtn" onclick="disable(${item.id })" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">冻结</button>
                                            </c:if>   
                                            <c:if test="${!item.state  }">
                                              <button type="button" name="enableBtn" onclick="enable(${item.id })" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">启用</button>
                                            </c:if>             
                                            <button type="button" name="delBtn" data-id="${item.id }" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">删除</button>
                                            <button type="button" onclick="loadRight('<%=basePath %>/meal/userMealList?userName=${item.user_name}','用户套餐')" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">套餐</button>
                                        </div>
                                    </div>
                                </td>
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

                <div class="am-modal am-modal-prompt" tabindex="-1" id="resetPwd-prompt">
                    <div class="am-modal-dialog">
                        <div class="am-modal-hd">重置密码</div>
                        <div class="am-modal-bd">
                            请输入登录密码 <input type="text" class="am-modal-prompt-input" maxlength="30">
                        </div>
                        <div class="am-modal-footer">
                            <span class="am-modal-btn" data-am-modal-cancel>取消</span> <span
                                class="am-modal-btn" data-am-modal-confirm>提交</span>
                        </div>
                    </div>
                </div>
                
                <div class="am-modal am-modal-prompt" tabindex="-1" id="editExpirDate-prompt">
                    <div class="am-modal-dialog">
                        <div class="am-modal-hd">修改过期时间</div>
                        <div class="am-modal-bd">
                            <input type="text" class="am-modal-prompt-input" value="" placeholder="请填入过期时间 " name="expirDate" id="expirDate" required="required">
                        </div>
                        <div class="am-modal-footer">
                            <span class="am-modal-btn" data-am-modal-cancel>取消</span> <span
                                class="am-modal-btn" data-am-modal-confirm>提交</span>
                        </div>
                    </div>
                </div>
                
                <div class="am-modal am-modal-prompt" tabindex="-1" id="editAccount-prompt">
                    <div class="am-modal-dialog">
                        <div class="am-modal-hd">修改账户余额</div>
                        <div class="am-modal-bd">
                            <input type="text" class="am-modal-prompt-input" value="" placeholder="请填入余额 " name="money" id="money" required="required">
                        </div>
                        <div class="am-modal-footer">
                            <span class="am-modal-btn" data-am-modal-cancel>取消</span> <span
                                class="am-modal-btn" data-am-modal-confirm>提交</span>
                        </div>
                    </div>
                </div>
                
                
            </div>
        </div>
    </div>
</div>

<script src="<%=basePath %>/js/user.js"></script>