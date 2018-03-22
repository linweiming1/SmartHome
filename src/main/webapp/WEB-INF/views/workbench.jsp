<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
<c:set var="r" value="${ctx }/resources" />
<link rel="stylesheet" href="${r }/common/css/sapar.css" />
<div id="saper-container">
	<div id="saper-hd"></div>
	<div id="saper-bd">
		<div class="subfiled clearfix">
			<h2>个人主页</h2>
		</div>
		<div class="subfiled-content">
			<div class="search-box clearfix">
				<div class="kv-item clearfix">
					<label>城市：</label>
					<div class="kv-item-content">
						${entity.cityName }
					</div>
				</div>
				<div class="kv-item clearfix">
					<label>pm2.5：</label>
					<div class="kv-item-content">
						${entity.pm }
					</div>
				</div>
				<div class="kv-item clearfix">
					<label>时间：</label>
					<div class="kv-item-content">
						${entity.date }
					</div>
				</div>
				<div class="kv-item clearfix">
					<label>天气：</label>
					<div class="kv-item-content">
						${entity.weather }
					</div>
				</div>
				<div class="kv-item clearfix">
					<label>风力：</label>
					<div class="kv-item-content">
						${entity.wind }
					</div>
				</div>
				<div class="kv-item clearfix">
					<label>温度：</label>
					<div class="kv-item-content">
						${entity.temperature }
					</div>
				</div>
				<%-- <div class="kv-item clearfix">
					<label>更新时间：</label>
					<div class="kv-item-content">
						${entity.2017111214 }
					</div>
				</div> --%>
			</div>
		</div>
	</div>
</div>