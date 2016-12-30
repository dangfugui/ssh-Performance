<%@page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>季度管理</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/jquery.js"></script>

<script type="text/javascript">
$(function(){	
	//导航切换
	$(".menuson .header").click(function(){
		var $parent = $(this).parent();
		$(".menuson>li.active").not($parent).removeClass("active open").find('.sub-menus').hide();
		
		$parent.addClass("active");
		if(!!$(this).next('.sub-menus').size()){
			if($parent.hasClass("open")){
				$parent.removeClass("open").find('.sub-menus').hide();
			}else{
				$parent.addClass("open").find('.sub-menus').show();	
			}
			
			
		}
	});
	
	// 三级菜单点击
	$('.sub-menus li').click(function(e) {
        $(".sub-menus li.active").removeClass("active")
		$(this).addClass("active");
    });
	
	$('.title').click(function(){
		var $ul = $(this).next('ul');
		$('dd').find('.menuson').slideUp();
		if($ul.is(':visible')){
			$(this).next('.menuson').slideUp();
		}else{
			$(this).next('.menuson').slideDown();
		}
	});
})	
</script>


</head>

<body style="background:#f0f9fd;">
	<div class="lefttop"><span></span>菜单</div>
    
    <dl class="leftmenu">
        
    <dd>
    <div class="title">
    <span><img src="images/leftico01.png" /></span>考核查询
    </div>
    	<ul class="menuson">
    	
    	<s:if test="(null==#application.proportion||#application.proportion=='show')&&#session.login_user.powerGroup.powerString.contains('B1')">
        <li>	<div class="header">	<cite></cite>
            <a href="Proportion_lookProportion.action" target="rightFrame">我的评分比例</a>
       </div></li></s:if>
       	<s:if test="(null==#application.fill||#application.fill=='show')&&#session.login_user.powerGroup.powerString.contains('B2')">
       <li>	<div class="header">	<cite></cite>
            <a href="KPI_listForFillContent.action" target="rightFrame">我要填表</a>
       </div></li></s:if>
       	<s:if test="(null==#application.quarter||#application.quarter=='show')&&#session.login_user.powerGroup.powerString.contains('B3')">
       <li>	<div class="header">	<cite></cite>
            <a href="Proportion_listForScore.action" target="rightFrame">季度打分</a>
       </div></li></s:if>
       	<s:if test="(null==#application.year||#application.year=='show')&&#session.login_user.powerGroup.powerString.contains('B4')">
        <li>	<div class="header">	<cite></cite>
            <a href="Average_listUser.action" target="rightFrame">年度打分</a>
       </div></li></s:if>
       	<s:if test="(null==#application.my||#application.my=='show')&&#session.login_user.powerGroup.powerString.contains('B5')">
        <li>	<div class="header">	<cite></cite>
            <a href="KPI_kpiResult.action" target="rightFrame">我的指标</a>
       </div></li></s:if>
        </ul>    
    </dd>
    
    <dd>
	    <div class="title">
	    <span><img src="images/leftico01.png" /></span>个人信息
	    </div>
	    	<ul class="menuson">
	     	       <li>	<div class="header">	<cite></cite>
	            <a href="user_change_password.jsp" target="rightFrame">密码修改</a>
	       </div></li>
        </ul>    
    </dd>
    	
       <dd>
	    <div class="title">
	    <span><img src="images/leftico01.png" /></span>季度考核
	    </div>
	    	<ul class="menuson">
	    	<s:if test="#session.login_user.powerGroup.powerString.contains('C1')">
	        <li>	<div class="header">	<cite></cite>
	            <a href="Quarter_list.action" target="rightFrame">季度管理</a>
	       </div></li></s:if>
	       <s:if test="#session.login_user.powerGroup.powerString.contains('C2')">
	       <li>	<div class="header">	<cite></cite>
	            <a href="User_listForKPI.action" target="rightFrame">拟定指标</a>
	       </div></li></s:if>
	       <s:if test="#session.login_user.powerGroup.powerString.contains('C3')">
	         <li>	<div class="header">	<cite></cite>
	            <a href="KPI_listTemplate.action" target="rightFrame">指标模板</a>
	       </div></li></s:if>
	       <s:if test="#session.login_user.powerGroup.powerString.contains('C4')">
	       <li>	<div class="header">	<cite></cite>
	            <a href="User_listForResult.action" target="rightFrame">考核结果</a>
	       </div></li></s:if>
	       <s:if test="#session.login_user.powerGroup.powerString.contains('C5')">
	        <li>	<div class="header">	<cite></cite>
	            <a href="User_listForDetail.action" target="rightFrame">互评详表</a>
	       </div></li></s:if>
        </ul>    
    </dd>
    
       <dd>
	    <div class="title">
	    <span><img src="images/leftico01.png" /></span>年度考核
	    </div>
	    	<ul class="menuson">
	    	 <s:if test="#session.login_user.powerGroup.powerString.contains('D1')">
	        <li>	<div class="header">	<cite></cite>
	            <a href="Year_list.action" target="rightFrame">年度管理</a>
	       </div></li></s:if>
	        <s:if test="#session.login_user.powerGroup.powerString.contains('D2')">
	       <li>	<div class="header">	<cite></cite>
	            <a href="User_listForYearScore.action" target="rightFrame">考核结果</a>
	       </div></li></s:if>
	        <s:if test="#session.login_user.powerGroup.powerString.contains('D3')">
	       <li>	<div class="header">	<cite></cite>
	            <a href="User_listForAverage.action" target="rightFrame">比例调整</a>
	       </div></li></s:if>
        </ul>    
    </dd>

        
   
  	<dd>
	    <div class="title">
	    <span><img src="images/leftico01.png" /></span>人员管理
	    </div>
	    	<ul class="menuson">
	    	<s:if test="#session.login_user.powerGroup.powerString.contains('E1')">
	    	<li>	<div class="header">	<cite></cite>
	            <a href="User_list.action" target="rightFrame">人员列表</a>
	       </div></li></s:if>
	       <s:if test="#session.login_user.powerGroup.powerString.contains('E2')">
	         <li>	<div class="header">	<cite></cite>
	            <a href="User_listForProportion.action" target="rightFrame">比例设定</a>
	       </div></li></s:if>
	      <s:if test="#session.login_user.powerGroup.powerString.contains('E3')">
	        <li>	<div class="header">	<cite></cite>
	            <a href="Department_list.action" target="rightFrame">部门列表</a>
	       </div></li></s:if>
	       <s:if test="#session.login_user.powerGroup.powerString.contains('E4')">
	       <li>	<div class="header">	<cite></cite>
	            <a href="Role_list.action" target="rightFrame">角色列表</a>
	       </div></li></s:if>
	       <s:if test="#session.login_user.powerGroup.powerString.contains('E5')">
	       <li>	<div class="header">	<cite></cite>
	            <a href="PowerGroup_list.action" target="rightFrame">权限组列表</a>
	       </div></li></s:if>
	     
        </ul>    
    </dd>

    </dl>
    
</body>
</html>
