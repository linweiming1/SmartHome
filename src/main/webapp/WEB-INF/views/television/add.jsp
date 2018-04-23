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
                        <label>设备名：</label>
                        <div class="kv-item-content">
                            <input type="text" name="equipmentName" placeholder="请输入设备名" id="equipmentName"
                                   maxlength="20">
                        </div>
                    </div>

                    <div class="kv-item clearfix">
                        <label>mac地址：</label>
                        <div class="kv-item-content">
                            <input type="text" name="macAddress" placeholder="请输入mac地址" id="macAddress"
                                   maxlength="20">
                        </div>
                    </div>
                    <div class="kv-item clearfix" align="center">
                        <label>&nbsp;</label>
                        <label>&nbsp;</label>
                        <div class="kv-item-content">
                            <button type="button" class="sapar-btn sapar-btn-recom query-btn">绑定</button>
                        </div>
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
            var equipmentName = $('#equipmentName').val().trim();
            if (equipmentName.length == 0) {
                $('#error').text('设备名不能为空');
                return;
            }

            var macAddress = $('#macAddress').val().trim();
            if (macAddress.length == 0) {
                $('#error').text('mac地址不能为空');
                return;
            }

            $.fn.serializeObject = function () {
                var o = {};
                var a = this.serializeArray();
                $.each(a, function () {
                    if (o[this.name]) {
                        if (!o[this.name].push) {
                            o[this.name] = [o[this.name]];
                        }
                        o[this.name].push(this.value || '');
                    } else {
                        o[this.name] = this.value || '';
                    }
                });
                return o;
            };

            var jsonData = $('#editForm').serializeObject();
            $.ajax({
                contentType: 'application/json;charset=UTF-8',//关键是要加上这行
                traditional: true,//这使json格式的字符不会被转码
                data: JSON.stringify(jsonData),
                type: "POST",
                url: "${ctx}/television/binding",
                success:
                    function (data) {
                        $('#error').text("设备绑定成功！");
                    },
                error: function (data) {
                    $('#error').text("设备绑定失败！");
                }
            });
        })
    });
</script>