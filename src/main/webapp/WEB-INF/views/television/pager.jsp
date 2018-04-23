<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx"
       value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}"/>
<link rel="stylesheet" href="${ctx }/resources/pager/css/main.css">

<%--maxPageItems每页最多显示几条,maxIndexPages最多显示几个页码默认二者都是10--%>
<pg:pager>

    <div class="page mt10">
        <div class="pagination">
            <ul>
                <!-- 页码描述 -->
                <pg:last>
                    <li class="first-child disabled"><span>每页5条,共${requestScope.page.getTotalElements()  }条记录</span>
                    </li>
                    <li class="first-child disabled">
                        <span>当前第${requestScope.page.getNumber() + 1 }页，共${requestScope.page.getTotalPages() }页</span>
                    </li>
                </pg:last>

                <!-- 首页-->
                <pg:first>
                    <c:if test="${requestScope.page.getNumber()  == 0 }">
                        <li class="first-child disabled"><span>[首页]</span></li>
                    </c:if>
                    <c:if test="${requestScope.page.getNumber()  != 0 }">
                        <li class="first-child"><a href="${ctx }/television/list?pageOffSet=0">首页</a></li>
                    </c:if>
                </pg:first>

                <!-- 上一页 -->
                <pg:first>
                    <c:if test="${requestScope.page.getNumber()  == 0 }">
                        <li class="first-child disabled"><span>[上一页]</span></li>
                    </c:if>
                    <c:if test="${requestScope.page.getNumber()  != 0 }">
                        <li class="first-child"><a
                                href="${ctx }/television/list?pageOffSet=${requestScope.page.getNumber()-1}">上一页</a>
                        </li>
                    </c:if>
                </pg:first>


                <!-- 下一页 -->
                <pg:last>
                    <c:if test="${requestScope.page.getNumber()+1 == requestScope.page.getTotalPages()}">
                        <li class="first-child disabled"><span>[下一页]</span></li>
                    </c:if>
                    <c:if test="${requestScope.page.getNumber()+1 != requestScope.page.getTotalPages()}">
                        <li class="first-child"><a
                                href="${ctx }/television/list?pageOffSet=${requestScope.page.getNumber()+1}">下一页</a>
                        </li>
                    </c:if>
                </pg:last>
                <!-- 末页-->
                <pg:last>
                    <c:if test="${requestScope.page.getNumber()+1 == requestScope.page.getTotalPages()}">
                        <li class="first-child disabled"><span>[末页]</span></li>
                    </c:if>
                    <c:if test="${requestScope.page.getNumber()+1 != requestScope.page.getTotalPages()}">
                        <li class="first-child"><a
                                href="${ctx }/television/list?pageOffSet=${requestScope.page.getTotalPages()-1}">末页</a>
                        </li>
                    </c:if>
                </pg:last>

            </ul>
        </div>
    </div>
</pg:pager>
