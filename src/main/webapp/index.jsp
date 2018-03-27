<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx"
       value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}"/>
<c:set var="r" value="${ctx }/resources"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>智慧家居登陆界面</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes"/>


    <script type="text/javascript" src="${r }/common/js/jquery.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#checkCode").blur(function () {
                var checkCode = $(this).val();
                //去服务器校验用户名是否存在--ajax
                $.post(
                    "${pageContrext.request.contextPath}/checkCode",
                    {"checkCode": checkCode},
                    function (data) {
                        var isCorrect = data;
                        $("#checkCodeInfo").html(data);
                    }
                );
            });
        });

        function flushCheckCode(obj) {
            obj.src = (obj.src + '?' + new Date())
        }
    </script>

</head>

<body bgcolor=#00a650>

<center>
    <p style="font-size:40px; "><strong>智慧家居后台系统</strong></p><br>
    <h1>登陆界面</h1>
    <form action="${ctx}/loginValide" method="post">
        <table border=1>
            <tr>
                <td align="center">用户名:</td>
                <td><input type="text" name="userName" id="userName" style="width: 130px; "/></td>
            </tr>
            <tr>
                <td align="center">密 &nbsp;码:</td>
                <td><input type="password" name="passWord" id="passWord" style="width: 130px; "/></td>
            </tr>
            <tr>
                <td align="center">验证码:</td>
                <td align="center"><img src="${ctx}/verifyCode" onclick=flushCheckCode(this)
                                        alt="点击刷新验证码" style="cursor: hand"/></td>
            </tr>
            <tr>
                <td align="center">输入验证码:</td>
                <td><input type="text" name="checkCode" id="checkCode" style="width: 130px; "/></td>
            </tr>
        </table>
        </br>
        <span id="checkCodeInfo"></span></br>
        <input type="submit" value="登录" style="height: 36px; width: 101px; "/>
    </form>

    <c:set var="errInfo" value="${requestScope.err}"></c:set>
    <font color="red"> <c:out value="${errInfo}"></c:out></font>

    <br><br><br><br><br><br><br>
    <marquee color="red">欢迎来到智慧家居后台系统！</marquee>
    <div id="ft">CopyRight&nbsp;2018&nbsp;&nbsp;版权所有@物联网所有</div>
</center>
</body>

</html>
