<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctx"
       value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}"/>
<c:set var="r" value="${ctx }/resources"/>
<link rel="stylesheet" id='skin' type="text/css" href="${r }/prompt/skin/qq/ymPrompt.css"/>
<link rel="stylesheet" href="${r }/common/css/sapar.css"/>
<div id="saper-container">
    <div id="saper-hd"></div>
    <div id="saper-bd">
        <div class="subfiled clearfix">
            <h2>空调温度上报列表</h2>
        </div>
        <div class="subfiled-content">
            <form action="${ctx }/airCondition/list" method="post" id="searchForm">
                <div class="search-box clearfix">
                    <div class="kv-item clearfix">
                        <label>名称：</label>
                        <div class="kv-item-content">
                            <input type="text" name="sysReport.boxId" value="${entity.equipmentName}"
                                   placeholder="请输入设备名" maxlength="20">
                        </div>
                        <label>&nbsp;</label>
                        <div class="kv-item-content">
                            <a class="sapar-btn sapar-btn-recom query-btn search">查询</a>
                        </div>
                        <label>&nbsp;</label>
                        <div class="kv-item-content">
                            <a class="sapar-btn sapar-btn-recom query-btn add">绑定新设备</a>
                        </div>
                        <label>&nbsp;</label>
                        <div class="kv-item-content">
                            <a class="sapar-btn sapar-btn-recom query-btn refresh">刷新</a>
                        </div>
                    </div>
                </div>
            </form>
            <!--表格开始-->
            <div class="table">
                <!--表格具体内容-->
                <div class="table-box">
                    <table>
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th width="20%">设备名</th>
                            <th>当前温度(℃)</th>
                            <th>预期温度(℃)</th>
                            <th width="20%">上报时间</th>
                            <th>当前状态</th>
                            <th>设备绑定时间</th>
                            <th>生产商</th>
                            <th width="10%">操作</th>
                            <th width="10%">解绑设备</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${pager.totalRecord == 0 }">
                            <tr>
                                <td colspan="5">暂无记录</td>
                            </tr>
                        </c:if>
                        <c:forEach items="${requestScope.page.getContent()}" var="d" varStatus="v">
                            <tr>
                                <td>${(v.index + 1)+requestScope.page.getNumber()*5 }</td>
                                <td>${d.equipmentName }</td>
                                <td>${d.currTemperature }</td>
                                <td>${d.expTemperature }</td>
                                <td><fmt:formatDate value="${d.addTime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
                                <td>
                                    <c:if test="${d.status eq true}" var="status"><a class="status"
                                                                                     data="${d.id}">运行</a></c:if>
                                    <c:if test="${d.status eq false}" var="status"><a class="status"
                                                                                      data="${d.id}">关闭</a></c:if>
                                </td>
                                <td><fmt:formatDate value="${d.createTime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
                                <td>${d.producer}</td>
                                <td>
                                    <a class="adjust" data="${d.id}">调整温度</a>
                                </td>
                                <td>
                                    <a class="delete quit-btn exit" data="${d.id}">[解绑]</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                    <jsp:include page="../common/pager.jsp">
                        <jsp:param value="${requestScope.page.getTotalElements() }" name="items"/>
                    </jsp:include>
                </div>
            </div>
            <!--表格结束-->
        </div>
    </div>
</div>
<script type="text/javascript" src="${r }/common/js/jquery.js"></script>
<script type="text/javascript" src="${r }/prompt/ymPrompt.js"></script>
<script type="text/javascript" src="${r }/common/js/sapar.js"></script>
<script type="text/javascript">
    $(function () {
        $('.search').click(function () {
            alert("查询ok");
            var user = {"producer": "123"};

        })

        $('.adjust').click(function () {
            alert("调整温度成功！")

        })
        $('.delete').click(function () {
            delete_id = $(this).attr("data");
            ymPrompt.confirmInfo({message: '您确定解绑此设备吗', handler: handlerDel});
        })

        $(".status").click(function () {
            device_id = $(this).attr("data");
            $.ajax({
                type: "GET",
                url: '${ctx}/airCondition/changeStatus?id=' + device_id,
                dataType: "text",
                success: function (result) {
                    alert("设备设备状态切换成功!");
                    location.href = "${ctx }/airCondition/list?pageOffSet=${requestScope.page.getNumber() }";
                }
            });
        })

        $(".refresh").click(function () {
            alert("刷新成功");
            location.href = "${ctx }/airCondition/list?pageOffSet=${requestScope.page.getNumber() }";

        })
        $('.add').click(function () {
            ymPrompt.win({
                title: '绑定新设备',
                height: '320',
                width: '380',
                dragOut: false,
                iframe: true,
                message: '${ctx}/airCondition/toAdd',
                handler: function () {
                    location.href = "${ctx }/airCondition/list?pageOffSet=${requestScope.page.getNumber() }";
                }
            });
        });
    });

    var delete_id;

    function handlerDel(tp) {
        if (tp == 'ok') {
            $.ajax({
                type: "GET",
                url: '${ctx}/airCondition/unBindingDevice?id=' + delete_id,
                dataType: "text",
                success: function (result) {
                    alert("解绑设备成功!");
                    location.href = "${ctx }/airCondition/list?pageOffSet=${requestScope.page.getNumber() }";
                }
            });
        }
    }
</script>