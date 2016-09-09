<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>指标列表</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/select.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery.idTabs.min.js"></script>
<script type="text/javascript" src="js/select-ui.min.js"></script>
<script type="text/javascript" src="editor/kindeditor.js"></script>

<script type="text/javascript">

    KE.show({
        id : 'content7',
        cssPath : './index.css'
    });
  </script>
  
<script type="text/javascript">
$(document).ready(function(e) {

	$(".select3").uedSelect({
		width : 100
	});
});

	function department(){
	$("#userlist").html("<option ></option>");
	$.ajax({
	    url: "Department_listDepartmentUserJson.action",    //请求的url地址
	    dataType: "json",   //返回格式为json
	    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
	    data: { "department.did": $("#department_did").val() },    //参数值
	    type: "POST",   //请求方式
	    //beforeSend: function() {},
	    success: function(result) {//请求成功时处理
	    	$.each(result.userList,function(i,user){
	    		$("#userlist").append("<option value='"+user.uid+"' >"+user.name+"</option>");
	    	});
	    }
	    //complete: function() {//请求完成的处理},
	    //error: function() {//请求出错处理 }
	});
	}
	
function setFillUser(){
	$.ajax({
	    url: "KPI_setFillUser.action",    //请求的url地址
	    dataType: "json",   //返回格式为json
	    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
	    data: { "user.uid": parseInt($("#userlist").val()) },    //参数值
	    type: "GET",   //请求方式
	    //beforeSend: function() {},
	    success: function(result) {//请求成功时处理
	    	alert("授权成功");
	    }
	    //complete: function() {//请求完成的处理},
	    //error: function() {//请求出错处理 }
	});
}

</script>
<script  type="text/javascript">
$(document).ready(function(){
  $(".click").click(function(){
  $(".tip").fadeIn(200);
  });
  
  $(".tiptop a").click(function(){
  $(".tip").fadeOut(200);
});

  $(".sure").click(function(){
  $(".tip").fadeOut(100);
});

  $(".cancel").click(function(){
  $(".tip").fadeOut(100);
});

});

</script>

</head>

<body class="sarchbody">

	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="User_listForKPI">指标列表</a></li>
    </ul>
    </div>
    <div class="rightinfo">
	<div class="tools">
	<form action="">
    <ul class="toolbarm">

	    <li><label>季度:</label></li>		<li><s:property value="#session.quarter.name" /></li>
	    <li><label></label> </li>
	    <li><label>KPI指标</label> </li>
        </ul>
	    

    </form>
    
  	
   </div>
    <table class="tablelist">
    	<thead>
    	<tr>
	       
	        <th style="width:70px">用户名<i class="sort"><img src="images/px.gif" /></i></th>
	        <th style="width:60px">角色</th>
	        <th style="width:80px">指标名称</th>
	        <th style="width:10px">权重</th>
	        <th style="width:200px">定义</th>
	        <th style="width:50px">数据来源</th>
	        <th style="width:100px">实际完成情况</th>
	        <th style="width:90px">操作</th>
        </tr>
        </thead>
        <tbody>
        <s:iterator value="contentKPIs" var="c">
        <s:if test="#c.state=='kpi'"> 	
        <tr>
	        
	        <td><pre><s:property value="#c.kpi.aimUser.name"  /></pre></td>
	        <td><pre><s:property value="#c.kpi.aimUser.role.name"  /></pre>  </td>
	        <td><pre><s:property value="#c.name"  /> </pre></td>
	        <td><pre><s:property value="#c.weight"  /></pre> </td>
	        <td><pre><s:property value="#c.definde"  /></pre></td>
	        <td><pre><s:property value="#c.sourceUser.name"  /></pre> </td>
	        <td><pre><s:property value="#c.actual"  /></pre></td>
	        <td>
	        	<div class="tools">
			    	<ul class="toolbar">
			        <li onclick="window.location.href='KPI_toFillContentKPI.action?contentKPI.cid='+<s:property value="#c.cid" /> ">
			    	<span><img src="images/t02.png" /></span>填写</li>
			        </ul>
		    	</div>
	        </td>
        </tr> 
        </s:if>
       </s:iterator>
        </tbody>
    </table>
    </div>


	   <div class="rightinfo">
	<div class="tools">
    <ul class="toolbarm">
	    <li><label></label> </li>
	    <li><label></label> </li>
	    <li><label></label></li>
	     <li><label>GS指标</label></li>
	  </ul>
  	
   </div>
    <table class="tablelist">
    	<thead>
    	<tr>
	        <th width="70px">用户名</th>
	        <th width="50px">角色</th>
	        <th width="150px">指标名称<i class="sort"><img src="images/px.gif" /></i></th>
	        <th width="35px">权重</th>
	        <th width="200px">关键行动计划及评分方法</th>
	        <th width="200px">实际完成情况</th>
	        <th width="90px">操作</th>
        </tr>
        </thead>
        <tbody>
        <s:iterator value="contentKPIs" var="c">
        <s:if test="#c.state=='gs'">
        <tr>
	      
	        <td><pre><s:property value="#c.kpi.aimUser.name"  /></pre></td>
	        <td><pre><s:property value="#c.kpi.aimUser.role.name"  /></pre></td>
	        <td><pre><s:property value="#c.name"  /></pre></td>
	        <td><pre><s:property value="#c.weight"  /></pre></td>
	        <td><pre><s:property value="#c.definde"  /></pre></td>
	        <td><pre><s:property value="#c.actual"  /></pre></td>
	        <td>
	        	<div class="tools">
			    	<ul class="toolbar">
			        <li onclick="window.location.href='KPI_toFillContentGS.action?contentKPI.cid='+<s:property value="#c.cid" /> ">
			    	<span><img src="images/t02.png" /></span>填写</li>
			       
			        </ul>
		    	</div>
	        </td>
        </tr> 
        </s:if>
       </s:iterator>
        </tbody>
    </table>
    </div>
   
</body>

</html>
