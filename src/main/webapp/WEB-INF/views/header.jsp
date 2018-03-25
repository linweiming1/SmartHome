<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx"
       value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}"/>
<div id="hd">
    <div class="hd-wrap clearfix">
        <div class="top-light"></div>
        <h1 class="logo"></h1>
        <div class="toolbar">
            <div class="login-info clearfix">
                <span>欢迎您:</span>
                <span class="user-name">${current_user.userName }</span></br>
                <span>上次登录时间:</span>
                <span class="user-name">${requestScope.loginTime }</span></br>
            </div>
            <div class="tool clearfix">
                <a class="tips" href="${ctx }/workbench" target="rightFrame">我的主页</a>&nbsp;
                &nbsp;
                <a class="quit-btn exit">退出</a>
            </div>
        </div>
    </div>
</div>

<div class="exitDialog">
    <div class="dialog-content">
        <div class="ui-dialog-icon"></div>
        <div class="ui-dialog-text">
            <p class="dialog-content">你确定要退出系统？</p>
            <p class="tips">如果是请点击“确定”，否则点“取消”</p>
            <div class="buttons">
                <input type="button" class="button long2 ok_" value="确定"/>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" class="button long2 normal_" value="取消"/>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${r }/common/js/jquery.js"></script>
<script type="text/javascript">
    $(function () {
        $(".ok_").click(function () {
            window.location.href = "${ctx}/logout";
        })
    });
</script>