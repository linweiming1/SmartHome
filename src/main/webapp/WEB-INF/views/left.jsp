<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="sidebar">
    <h2 class="sidebar-header">
        <p>功能导航</p>
    </h2>
    <ul class="nav">
        <li class="agency hasChild current">
            <div class="nav-header">
                <a class="clearfix"> <span>个人管理</span><i class="icon"></i>
                </a>
            </div>
            <ul class="subnav">
                <li>
                    <a href="${ctx}/personalInfo" target="rightFrame">个人信息</a>
                </li>

                <li>
                    <a onclick="ymPrompt.win({title:'修改密码',height:'230',width:'380',dragOut:false,iframe:true,message:'${ctx}/toPsw'});">修改密码</a>
                </li>
            </ul>
        </li>
        <shiro:lacksRole  name="admin">
        <li class="agency">
            <div class="nav-header">
                <a class="clearfix"> <span>授权管理</span><i class="icon"></i>
                </a>
            </div>
            <ul class="subnav">
                <li>
                    <a href="${ctx }/authInfo" target="rightFrame">授权情况</a>
                </li>
            </ul>
        </li>
        <li class="agency">
            <div class="nav-header">
                <a class="clearfix"> <span>联网设备管理</span><i class="icon"></i>
                </a>
            </div>
            <ul class="subnav">
                <li><a href="${ctx }/airCondition/list" target="rightFrame">空调温度</a></li>
                <li><a href="${ctx }/freezer/list" target="rightFrame">电冰箱温度</a></li>
            </ul>
        </li>
        <li class="agency">
            <div class="nav-header">
                <a class="clearfix"> <span>家居设备管理</span><i class="icon"></i>
                </a>
            </div>
            <ul class="subnav">
                <li><a href="${ctx }/lighter/list"
                       target="rightFrame">开关灯</a></li>
                <li><a href="${ctx }/curtain/list"
                       target="rightFrame">窗帘</a></li>
                <li><a href="${ctx }/television/list"
                       target="rightFrame">电视</a></li>
            </ul>
        </li>
        <li class="agency">
            <div class="nav-header">
                <a class="clearfix"> <span>家庭其他情况</span><i class="icon"></i>
                </a>
            </div>
            <ul class="subnav">
                <li><a href="${ctx }/homeInfo/toEChart"
                       target="rightFrame">用电量</a></li>
            </ul>
        </li>
        <li class="agency">
            <div class="nav-header">
                <a class="clearfix"> <span>拓展功能</span><i class="icon"></i>
                </a>
            </div>
            <ul class="subnav">
                <li><a href="${ctx }/sysEquipment/type?sysEquipment.type=4"
                       target="rightFrame">报警器</a></li>
                <li><a href="${ctx }/sysEquipment/type?sysEquipment.type=4"
                       target="rightFrame">安防摄像头</a></li>
                <li><a href="${ctx }/sysEquipment/type?sysEquipment.type=5"
                       target="rightFrame">AR</a></li>
            </ul>
        </li>
        </shiro:lacksRole >
        <shiro:hasRole name="admin">
        <li class="agency">
            <div class="nav-header">
                <a class="clearfix"> <span>家庭用户管理</span><i class="icon"></i>
                </a>
            </div>
            <ul class="subnav">
                <li><a href="${ctx }/toUserList"
                       target="rightFrame">家庭用户列表</a></li>
            </ul>
        </li>
        </shiro:hasRole>
    </ul>
</div>
<script type="text/javascript" src="${r }/common/js/jquery.js"></script>
<script type="text/javascript">
    $(function () {
        $(".nav-header").click(function () {
            // 关闭所有的选中项
            $("li").removeClass("hasChild current");
            // 获取当前点击的父元素
            var current_ = $(this).parent();
            // 当前包含选中，则移除
            if (current_.attr('class').indexOf("hasChild current") > 0) {
                current_.removeClass("hasChild current");
            }
            // 当前不包含选中，则新增
            else {
                current_.addClass("hasChild current");
            }
        })
    });
</script>