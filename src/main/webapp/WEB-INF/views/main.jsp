<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx"
       value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}"/>
<c:set var="r" value="${ctx }/resources"/>

<!doctype html>
<head>
    <link rel="stylesheet" href="${r }/common/css/sapar.css"/>
    <link rel="stylesheet" href="${r }/static/css/index.css"/>
    <link rel="stylesheet" href="${r }/static/css/index_inner.css"/>
    <link rel="stylesheet" id='skin' type="text/css" href="${r }/prompt/skin/qq/ymPrompt.css"/>
    <title>智慧家居管理控制系统</title>
</head>
<body>
<div id="container">
    <!-- 头部信息 -->

    <%@ include file="header.jsp" %>

    <div id="bd">
        <div class="wrap clearfix">

            <!-- 左部信息 -->
            <%@ include file="left.jsp" %>

            <!-- 正文部分，即需要切换的更替的页面-->
            <div class="content" id="main">
                <iframe name="rightFrame" id="rightFrame" src="workbench"
                        width="100%" frameborder="0" scrolling="no" onload="setIframeHeight(this)"></iframe>
            </div>

        </div>
    </div>
</div>

<!-- 底部信息 -->
<%@ include file="footer.jsp" %>
</body>
<script type="text/javascript" src="${r }/common/js/jquery.js"></script>
<script type="text/javascript" src="${r }/common/js/sapar.js"></script>
<script type="text/javascript" src="${r }/static/js/index.js"></script>
<script type="text/javascript" src="${r }/prompt/ymPrompt.js"></script>
<script type="text/javascript">
    function setIframeHeight(iframe) {
        var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
        if (iframeWin.document.body) {
            iframe.height = document.body.clientHeight - 110;
        }
    };

</script>
</html>