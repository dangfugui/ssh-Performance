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
		width : 150
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

function template(){
	var id=$("#templatesid").val();
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
    <li><a href="User_listForKPI">人员列表</a></li>
     <li><a href="#"><s:property value="#session.kpi_user.name"/> 的指标</a></li>
    </ul>
    </div>
    <div class="rightinfo">
	<div class="tools">
    <ul class="toolbarm">
	    <li><label>季度:</label></li>		<li><s:property value="#session.quarter.name" /></li>
	    <li><label>KPI指标</label> </li>
	    <li><label>拟表人</label> </li>
	    <li> 
		    <div class="vocation">
		    <select class="select3" name="departmentDid" onchange="department()" id="department_did" >
		    	<option value="<s:property value="#session.kpi.fillUser.department.did" /> "> 
		    	<s:property value="#session.kpi.fillUser.department.name" /> </option>
		    <s:iterator value="departmentlist" var="dep">
		    	 <option value="<s:property value="#dep.did" />">	<s:property value="#dep.name" />	</option>
		    </s:iterator>
		    </select>
		    </div>
	    </li>    
	    <li><label></label>  
		    <div class="vocation">
		    <select class="select3"	name="fillUser.uid" id="userlist">
		    	<option value="<s:property value="#session.kpi.fillUser.uid" /> "> 
		    	<s:property value="#session.kpi.fillUser.name" /> </option>
		    </select>
		    </div>
		      <div onclick="setFillUser()"><span><img src="images/t05.png" /></span>授权</div> 	
	    </li>    
        	  <li><label></label> </li>
         	 <li><label>模板：</label> </li>
         	 <li  ><label></label>  
		    <div class="vocation">
		    <form action="KPI_copyTemplate.action"  id="form">
		    <select class="select3" id="templatesid" name="kpi.kid">
		    	<s:iterator value="templates" var="t">
		    		<option value="<s:property value="#t.kid" /> "> 
		    	<s:property value="#t.state" /> </option>
		    	</s:iterator>
		    </select>
		    </form>
		    </div>
		    <div onclick="javascript:$('#form').submit()"><span><img src="images/t05.png" /></span>导入</div> 	
	    </li>    
        </ul>
  
     <ul class="toolbar1">
        <li style="background:url(./images/toolbg.gif) repeat-x;border:solid 1px #d3dbde;" 
        onclick="javascript:window.location.href='KPI_toAddKPI.action'">
        <span><img src="images/t01.png"/></span>添加KPI</li>
     </ul>
  	
   </div>
    <table class="tablelist">
    	<thead>
    	<tr>
	       
	        <th style="width:50px">指标名称<i class="sort"><img src="images/px.gif" /></i></th>
	        <th style="width:10px">权重</th>
	        <th style="width:100px">定义、计算公式</th>
	        <th style="width:30px">目标值</th>
	        <th style="width:100px">计分方法</th>
	        <th style="width:50px">数据来源</th>
	        <th style="width:180px">实际完成情况</th>
	        <th style="width:20px">操作</th>
        </tr>
        </thead>
        <tbody>
        <s:iterator value="#session.kpi.contents" var="c">
        <s:if test="#c.state=='kpi'"> 	
        <tr>
	       
	        <td >	<s:property value="#c.name"  />  	</td>
	        <td >	<s:property value="#c.weight"  />  </td>
	        <td ><pre>	<s:property value="#c.definde"  /> </pre> </td>
	        <td ><pre>	<s:property value="#c.aim"  />  </pre></td>
	        <td><pre>	<s:property value="#c.process"  />  </pre></td>
	        <td ><pre>	<s:property value="#c.sourceUser.name"  />  </pre></td>
	        <td ><pre>	<s:property value="#c.actual"  />  </pre></td>
	        <td >
	        	<div class="tools">
			    	<ul class="toolbar">
			        <li onclick="window.location.href='KPI_toUpdateKPI.action?contentKPI.cid='+<s:property value="#c.cid" /> ">
			    	<span><img src="images/t02.png" /></span>修改</li>
			        <li onclick="javascript:if(confirm('确认删除指标行吗?'))window.location.href='KPI_delete.action?contentKPI.cid='+<s:property value="#c.cid" /> ">
			        <span><img src="images/t03.png" /></span>删除</li>
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
	    <li><label></label></li>
	     <li><label>GS指标</label></li>
	  </ul>
	    
  
     <ul class="toolbar1">
        <li style="background:url(./images/toolbg.gif) repeat-x;border:solid 1px #d3dbde;" 
        onclick="javascript:window.location.href='KPI_toAddGS.action'">
        <span><img src="images/t01.png"/></span>添加GS</li>
     </ul>
  	
   </div>
    <table class="tablelist">
    	<thead>
    	<tr>
	       
	        <th width="100px">指标名称<i class="sort"><img src="images/px.gif" /></i></th>
	        <th width="50px">权重</th>
	        <th width="300px">关键行动计划及评分方法  </th>
	        <th width="70px">数据来源</th>
	        <th width="300px">实际完成情况</th>
	        <th width="160px">操作</th>
        </tr>
        </thead>
        <tbody>
        <s:iterator value="#session.kpi.contents" var="c">
        <s:if test="#c.state=='gs'">
        <tr>       
	        <td ><s:property value="#c.name"  /> 	</td>
	        <td ><s:property value="#c.weight"  /> </td>
	        <td  ><pre><s:property value="#c.definde"  />   </pre></td>
	        <td >	<s:property value="#c.sourceUser.name"  /> </td>
	        <td  ><pre>	<s:property value="#c.actual"  />  </pre></td>
	        <td >
	        	<div class="tools">
			    	<ul class="toolbar">
			        <li onclick="window.location.href='KPI_toUpdateGS.action?contentKPI.cid='+<s:property value="#c.cid" /> ">
			    	<span><img src="images/t02.png" /></span>修改</li>
			        <li onclick="javascript:if(confirm('确认删除指标行吗?'))window.location.href='KPI_delete.action?contentKPI.cid='+<s:property value="#c.cid" /> ">
			        <span><img src="images/t03.png" /></span>删除</li>
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
