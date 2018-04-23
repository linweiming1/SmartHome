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
            <h2>电灯控制中心</h2>
        </div>
        <div class="subfiled-content">
            <div class="search-box clearfix">
                <div class="kv-item clearfix">
                    <div class="kv-item-content">
                        <a class="sapar-btn sapar-btn-recom query-btn add">绑定新设备</a>
                    </div>
                    <label>&nbsp;</label>
                    <div class="kv-item-content">
                        <a class="sapar-btn sapar-btn-recom query-btn refresh">刷新</a>
                    </div>
                </div>
            </div>
            <!--表格开始-->
            <div class="table">
                <!--表格具体内容-->
                <div class="table-box">
                    <table>
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th width="20%">设备名</th>
                            <th>当前状态</th>
                            <th>设备绑定时间</th>
                            <th width="20%">刷新时间</th>
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
                                <td>
                                    <c:if test="${d.status eq true}" var="status"><a class="status"
                                                                                     data="${d.id}">运行</a></c:if>
                                    <c:if test="${d.status eq false}" var="status"><a class="status"
                                                                                      data="${d.id}">关闭</a></c:if>
                                </td>
                                <td><fmt:formatDate value="${d.createTime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
                                <td><fmt:formatDate value="${d.addTime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
                                <td>
                                    <a class="delete quit-btn exit" data="${d.id}">[解绑]</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <jsp:include page="../lighter/pager.jsp">
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

        $('.delete').click(function () {
            delete_id = $(this).attr("data");
            ymPrompt.confirmInfo({message: '您确定解绑此设备吗', handler: handlerDel});
        })

        $(".status").click(function () {
            device_id = $(this).attr("data");
            $.ajax({
                type: "GET",
                url: '${ctx}/lighter/changeStatus?id=' + device_id,
                dataType: "text",
                success: function (result) {
                    ymPrompt.alert({
                        message: '设备状态切换成功', title: '成功信息', handler: function () {
                            location.href = "${ctx }/lighter/list?pageOffSet=${requestScope.page.getNumber() }";
                        }
                    })
                }
            });
        })

        $(".refresh").click(function () {
            ymPrompt.alert({
                message: '刷新成功', title: '成功信息', handler: function () {
                    location.href = "${ctx }/lighter/list?pageOffSet=${requestScope.page.getNumber() }";
                }
            })
        })
        $('.add').click(function () {
            ymPrompt.win({
                title: '绑定新设备',
                height: '320',
                width: '380',
                dragOut: false,
                iframe: true,
                message: '${ctx}/lighter/toAdd',
                handler: function () {
                    location.href = "${ctx }/lighter/list?pageOffSet=${requestScope.page.getNumber() }";
                },
                btn: [['确定', 'yes']]
            });
        });
    });

    var delete_id;

    function handlerDel(tp) {
        if (tp == 'ok') {
            $.ajax({
                type: "GET",
                url: '${ctx}/lighter/unBindingDevice?id=' + delete_id,
                dataType: "text",
                success: function (result) {
                    ymPrompt.alert({
                        message: '解绑成功', title: '成功信息', handler: function () {
                            location.href = "${ctx }/lighter/list?pageOffSet=${requestScope.page.getNumber() }";
                        }
                    })
                }
            });
        }
    }
</script>
