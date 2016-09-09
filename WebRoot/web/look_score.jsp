<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>得分详情 </title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery.js"></script>

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


<body>

	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="KPI_kpiResult.action?user.uid=<s:property value="contentKPI.kpi.aimUser.uid"/>">
    <s:property value="contentKPI.kpi.aimUser.name"/>的KPI</a></li>
     <li><a href=""><s:property value="contentKPI.name"/> 评分详情</a></li>
    </ul>
    </div>
    
    <div class="rightinfo">
    
    <div class="tools">
    	<ul class="toolbarm">
    	<li><label>季度:</label></li> 	<li> <s:property value="contentKPI.kpi.quarter.name" /> </li>
    	<li></li>
    	<li><label>指标名称:</label></li>	<li> <s:property value="contentKPI.name" /> </li>
    	<li></li>
    	<li><label>指标权重:</label></li>	<li> <s:property value="contentKPI.weight" />% </li>
    	<li></li>
    	<li><label>汇总得分:</label></li>	<li> <s:property value="contentKPI.result" /> </li>
        </ul>
    </div>
    <table class="tablelist">
    	<thead>
    	<tr>
	     
	        <th>姓名</th>
	        <th>部门</th>
	        <th>角色</th>
	        <th>得分</th>
	        <th>所占比例%</th>
        </tr>
        </thead>
        <tbody>
        <s:iterator value="contentKPI.scores" var="s">
        <tr>
	       
			<td><s:property value="#s.fillUser.name" />	</td>
			<td><s:property value="#s.fillUser.department.name" /></td>
			<td><s:property value="#s.fillUser.role.name" />	</td>
			<td><s:property value="#s.score" />	</td>
	      	<td>      	
			 <s:iterator value="proportionList" var="proportion">
			 	<s:if test="#proportion.sourceUser.uid==#s.fillUser.uid">
			 		<s:property value="#proportion.proportion" />%
			 	</s:if>
			 </s:iterator>
	      	
	      	</td>
    	

        </tr> 
        </s:iterator>
        </tbody>
    </table>
    
   </div>
    
    
    

    
    <script type="text/javascript">
	$('.tablelist tbody tr:odd').addClass('odd');
	</script>

</body>

</html>
