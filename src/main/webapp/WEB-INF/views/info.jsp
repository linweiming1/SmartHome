<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx"
       value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}"/>
<c:set var="r" value="${ctx }/resources"/>
<link rel="stylesheet" href="${r }/common/css/sapar.css"/>
<div id="saper-container">
    <div id="saper-hd"></div>
    <div id="saper-bd">
        <div class="subfiled clearfix">
            <h2>个人信息</h2>
        </div>
        <div class="subfiled-content">
            <div class="search-box clearfix">
                <div class="kv-item clearfix">
                    <label>用户：</label>
                    <div class="kv-item-content">
                        ${current_user.userName }
                    </div>
                </div>

                <div class="kv-item clearfix">
                    <label>角色：</label>
                    <div class="kv-item-content">
                        <c:if test="${current_user.authLevel eq '1'}">管理员</c:if>
                        <c:if test="${current_user.authLevel eq '2'}">普通用户</c:if>
                        <c:if test="${current_user.authLevel eq '3'}">游客</c:if>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>