<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
<c:set var="r" value="${ctx }/resources" />
<link rel="stylesheet" href="${r }/common/css/sapar.css" />
<div id="saper-container">
    <div id="saper-hd"></div>
    <div id="saper-bd">
        <div class="subfiled clearfix">
            <h2>个人主页</h2>
        </div>
        <div class="subfiled-content">
            <div class="search-box clearfix">
                <div class="kv-item clearfix">
                 <h1>   网络不好，请重新连接后，<a href="${ctx }/workbench" target="rightFrame">刷新</a></h1>
                </div>
            </div>
        </div>
    </div>
</div>