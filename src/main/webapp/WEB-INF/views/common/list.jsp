<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}"/>
<script type="text/javascript">
function refresh(){
    alert("刷新");
  location.href="${ctx }/airCondition/list?pageOffSet=${requestScope.page.getNumber() + 1}";
}
</script>