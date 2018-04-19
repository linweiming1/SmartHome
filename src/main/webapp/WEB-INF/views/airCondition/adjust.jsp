<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx"
       value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}"/>
<c:set var="r" value="${ctx }/resources"/>
<link rel="stylesheet" href="${r }/common/css/sapar.css"/>
<div id="saper-container">
    <div id="saper-hd"></div>
    <div id="saper-bd">
        <div class="subfiled-content">
            <form id="editForm">
                <div class="search-box clearfix">
                    <div class="kv-item clearfix">
                        <label>预期温度：</label>
                        <div class="kv-item-content">
                            <input type="number" name="expTemperature" placeholder="请输入预期温度" id="expTemperature"
                                   maxlength="20">
                        </div>
                    </div>
                </div>
                <div class="kv-item clearfix" align="center">
                    <label>&nbsp;</label>
                    <label>&nbsp;</label>
                    <div class="kv-item-content">
                        <button type="button" class="sapar-btn sapar-btn-recom query-btn">调整</button>
                    </div>
                </div>
                <div id="error" class="red" align="center">
                    <div> ${msg }</div>
                </div>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript" src="${r }/common/js/jquery.js"></script>
<script type="text/javascript" src="${r }/common/js/WdatePicker.js"></script>
<script type="text/javascript">
    $(function () {
        $(".query-btn").click(function () {
            var expTemperature = $("#expTemperature").val();
            var deviceId =
            ${requestScope.deviceId}
            if (expTemperature == 0) {
                alert("请输入预期的温度")
            }
            else {
                $.ajax({
                    url:"${ctx}/airCondition/adjust?expTemperature=" + expTemperature + "&&deviceId=" + deviceId,
                    type:"get",
                    dataType:"text",
                    success:function (data) {
                    $('#error').text('温度调整成功');
                }
            });



              //  location.href = "${ctx}/airCondition/adjust?expTemperature=" + expTemperature + "&&deviceId=" + deviceId;

            }

        });

    })

</script>