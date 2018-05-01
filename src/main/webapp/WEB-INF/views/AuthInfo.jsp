<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
<c:set var="r" value="${ctx }/resources" />
<link rel="stylesheet" id='skin' type="text/css" href="${r }/prompt/skin/qq/ymPrompt.css" />
<link rel="stylesheet" href="${r }/common/css/sapar.css" />
<div id="saper-container">
    <div id="saper-hd"></div>
    <div id="saper-bd">
        <div class="subfiled clearfix">
            <h2>被授权者列表</h2>
        </div>
        <div class="subfiled-content">

            <div class="search-box clearfix">
                <div class="kv-item clearfix">
                    <label>&nbsp;</label>
                    <div class="kv-item-content">
                        <a class="sapar-btn sapar-btn-recom query-btn add">授权新游客</a>
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
                            <th width="10%">编号</th>
                            <th>用户名</th>
                            <th>密码</th>
                            <th>角色</th>
                            <th>权限详情</th>
                            <th width="10%">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${pager.totalRecord == 0 }">
                            <tr><td colspan="4">暂无记录</td></tr>
                        </c:if>
                        <c:forEach items="${pager.datas}" var="d" varStatus="v">
                            <tr>
                                <td>${v.index + 1 }</td>
                                <td>${d.username }</td>
                                <td>${d.password }</td>
                                <td>${d.password }</td>
                                <td>${d.password }</td>
                                <td>
                                    <a class="edit" data="${d.id }">[编辑]</a>
                                    &nbsp;&nbsp;
                                    <a class="delete quit-btn exit" data="${d.id }">[删除]</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                    <jsp:include page="../views/common/pager.jsp">
                        <jsp:param value="${pager.totalRecord }" name="items"/>
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
    $(function() {
        $('.search').click(function() {
            $('#searchForm').submit();
        })

        $('.add').click(function() {
            ymPrompt.win({title:'新增',height:'170',width:'380',dragOut:false,iframe:true,message:'${ctx}/appUser/toAdd',handler:refresh});
        })

        $('.edit').click(function() {
            var id = $(this).attr("data");
            ymPrompt.win({title:'修改',height:'170',width:'380',dragOut:false,iframe:true,message:'${ctx}/appUser/toUpdate?appUser.id=' + id,handler:refresh});
        })

        $('.delete').click(function() {
            delete_id = $(this).attr("data");
            ymPrompt.confirmInfo({message:'您确定删除此信息？',handler:handlerDel});
        })
    });

    var delete_id;
    function handlerDel(tp){
        if(tp=='ok'){
            $.ajax({
                type : "GET",
                url : '${ctx}/appUser/delete?appUser.id=' + delete_id,
                dataType : "text",
                success : function(result) {

                    refresh();
                }
            });
        }
    }
</script>






</body>
</html>
