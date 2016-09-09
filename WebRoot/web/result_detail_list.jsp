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
<title>用户列表</title>
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
		width : 150
	});
});

function newlist(){
	var name=$("#user.name").val();
	var departmentDid=$("#departmentDid").val();
	var roleRid=$("roleRid").val();
	/*$.post("User_list.action",
    {
      	"user.name":name,
      	"departmentDid":departmentDid,
      	"roleRid":roleRid
    }); */
  	myu("User_list.action",{	"user.name":name,
      	"departmentDid":departmentDid,
      	"roleRid":roleRid});
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
<script type="text/javascript">
	function calculateKPI(){
	$.ajax({
	    url: "KPI_calculateKPI.action",    //请求的url地址
	    dataType: "html",   //返回格式为json
	    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
	   // data: { "department.did": $("#department_did").val() },    //参数值
	    type: "GET",   //请求方式
	    beforeSend: function() {alert("开始计算")},
	    //success: function(result) {//请求成功时处理
	    
	    complete: function() {alert("计算完成")}//请求完成的处理},
	    //error: function() {//请求出错处理 }
	});
	}
</script>

</head>

<body class="sarchbody">

	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">互评详表</a></li>
    </ul>
    </div>
    
    <div class="rightinfo">
	<div class="tools">
	<form action="User_listForDetail.action" method="post" >
    <ul class="toolbarm">
		 <li><label>季度</label>  
		    <div class="vocation">
		    <select class="select3"	name="quarterQid">
		     <s:iterator value="quarterList" var="quarter">
		     <s:if test="#quarter.qid==#session.quarter.qid">
		     	 <option selected = "selected" value='<s:property value="#quarter.qid" />' >
		    	 		<s:property value="#quarter.name" />	</option>
		     </s:if>
		     <s:else>
		    	 <option value='<s:property value="#quarter.qid" />' >
		    	 		<s:property value="#quarter.name" />	</option>
		    </s:else>
		    </s:iterator>
		    </select>
		    </div>
	    </li>    	
		
	   
	    <li><label>部门</label>
		    <div class="vocation">
		    <select class="select3" name="departmentDid" >
		    	
		    <s:iterator value="departmentlist" var="dep">
		    <s:if test="departmentDid==#dep.did">
		    	 <option selected="selected" value="<s:property value="#dep.did" />">	<s:property value="#dep.name" />	</option>
		    </s:if><s:else>
		    	 <option value="<s:property value="#dep.did" />">	<s:property value="#dep.name" />	</option>
		  	</s:else>
		    </s:iterator>
		    </select>
		    </div>
	    </li>
	    
	    <li><label>角色</label>  
		    <div class="vocation">
		    <select class="select3"	name="roleRid">
		    <option value="0">全部</option>
		     <s:iterator value="roleList" var="role">
		     <s:if test="roleRid==#role.rid">
		     <option selected="selected" value="<s:property value="#role.rid" />" >	<s:property value="#role.name" />	</option>
		    </s:if> <s:else>
		    	 <option value="<s:property value="#role.rid" />" >	<s:property value="#role.name" />	</option>
		    </s:else>
		    </s:iterator>
		    </select>
		    </div>
	    </li>    
	    <li><input  type="submit" class="sure" value="查询"/></li>
	      <li><input  type="button" class="sure" value="导出"
	    	onclick="window.location.href='User_listForDetailDownload.action?departmentDid=<s:property value="departmentDid" />'"
	    /></li>

    </ul>
    </form>
   <%--  <s:if test="#session.login_user.powerGroup.powerString.contains('A5')">
    <ul class="toolbar1">
        <li style="background:url(./images/toolbg.gif) repeat-x;border:solid 1px #d3dbde;" 
        onclick="calculateKPI()">
        <span><img src="images/t05.png"/></span>计算本季度成绩</li>
     </ul>
     </s:if> --%>
    
  	
   </div>
    <table class="tablelist">
    	<thead>
    	<tr>
	        <th>部门</th>
	        <th>姓名</th>
	       	<s:iterator value="list" var="t">
	       	<th><s:property value="#t.name"/>  </th>
	       	</s:iterator>
        </tr>
        </thead>
        <tbody>
         <s:iterator value="list" var="u">
        <tr>
	        <td>	<s:property value="#u.department.name"  />  	</td>
	        <td>	<s:property value="#u.name"  />  </td>
	       	<s:iterator value="list" var="x">
	       	<td><s:property value="map[#u.uid][#x.uid]"/>  </td>
	       	</s:iterator>
	    </tr>
       </s:iterator>
    
        </tbody>
    </table>
     </div>
	

 

    
    
   


</body>

</html>
