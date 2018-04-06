<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
<c:set var="r" value="${ctx }/resources" />
<link rel="stylesheet" href="${r }/common/css/sapar.css" />
<div id="saper-container">
    <div id="saper-hd"></div>
    <div id="saper-bd">
        <div class="subfiled-content">
            <form action="${ctx }/updatePsw" method="post" id="editForm">
                <div class="search-box clearfix">
                    <div class="kv-item clearfix">
                        <label>账号：</label>
                        <div class="kv-item-content">
                           ${current_user.userName }
                        </div>
                    </div>
                    <div class="kv-item clearfix">
                        <label>原密码：</label>
                        <div class="kv-item-content">
                            <input type="password" name="oldPassword" placeholder="请输入原密码" id="old">
                        </div>
                    </div>
                    <div class="kv-item clearfix">
                        <label>新密码：</label>
                        <div class="kv-item-content">
                            <input type="password" name="newPassword"  placeholder="请输入新密码" id="password">
                        </div>
                    </div>
                    <div class="kv-item clearfix">
                        <label>&nbsp;</label>
                        <div class="kv-item-content">
                            <button type="button" class="sapar-btn sapar-btn-recom query-btn">保存</button>
                        </div>
                        &nbsp;&nbsp;
                        <span id="error" class="red">${requestScope.msg }</span>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript" src="${r }/common/js/jquery.js"></script>
<script type="text/javascript">
    $(function(){
        $(".query-btn").click(function(){
            var old = $('#old').val().trim();
            var password = $('#password').val().trim();
            if (old.length == 0){
                $('#error').text('原密码不能为空');
                return;
            }
            if (password.length == 0){
                $('#error').text('新密码不能为空');
                return;
            }
            $('#editForm').submit();
        })
    });
</script>