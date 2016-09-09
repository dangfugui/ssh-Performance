<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>评分比例 </title>
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
    <li><a href="User_listForProportion.action">比例设定</a></li>
     <li><a href=""><s:property value="user.name"/> 的评分比例</a></li>
    </ul>
    </div>
    
    <div class="rightinfo">
    
    <div class="tools">
    	<ul class="toolbarm">
    	<li><label>季度:</label></li>		<li> <s:property value="quarter.name" /> </li>
        </ul>
    </div>
    <table class="tablelist">
    	<thead>
    	<tr>
	      
	        <th>编号<i class="sort"><img src="images/px.gif" /></i></th>
	        <th>姓名</th>
	        <th>角色</th>
	        <th>比例</th>
        </tr>
        </thead>
        <tbody>
        <s:iterator value="proportionList" var="p">
        <tr>
	       
			<td><s:property value="#p.pid" />	</td>
			<td><s:property value="#p.sourceUser.name" /></td>
			<td><s:property value="#p.sourceUser.role.name" />	</td>
			<td><s:property value="#p.proportion" />	</td>
	      	<td>      	
			  
	      	
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
