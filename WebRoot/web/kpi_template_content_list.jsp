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
<title>模板指标列表</title>
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

function update(kid){
	
	if(confirm('确认修改模板名?')){
		 $.post("KPI_addOrUpdateTemplate.action",
    		{
      			"kpi.kid":kid,
      			"kpi.state":$("#kpistate").val()
    	});
	}
}

</script>

</head>

<body class="sarchbody">

	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="KPI_listTemplate.action">模板列表</a></li>
     <li><a href="#"><s:property value="kpi.state"/> 模板</a></li>
    </ul>
    </div>
    <div class="rightinfo">
	<div class="tools">
	<form action="">
    <ul class="toolbarm">

	  <li><label></label> </li>
	    <li><label></label> </li>
	    <li><label>KPI指标</label> </li>
	    <li><label></label></li>
	    <li><label></label>	<input type="text"   class="dfinput"  value="<s:property value='kpi.state'/>"  id="kpistate"/>	</li>
	    <li> 
	    </li>    
	    </ul>
	     <ul class="toolbar">
       	 <li onclick="update(<s:property value='kpi.kid'/>)"><span><img src="images/t05.png" /></span>更改名称 </li>
        </ul>
	    

    </form>
     <ul class="toolbar1">
        <li style="background:url(./images/toolbg.gif) repeat-x;border:solid 1px #d3dbde;" 
        onclick="javascript:window.location.href='KPI_toAddKPI.action'">
        <span><img src="images/t01.png"/></span>添加KPI</li>
     </ul>
  	
   </div>
    <table class="tablelist">
    	<thead>
    	<tr>
	        
	        <th>指标名称<i class="sort"><img src="images/px.gif" /></i></th>
	        <th>权重</th>
	        <th>定义、计算公式</th>
	        <th>目标值</th>
	        <th>计分方法</th>
	        <th>数据来源</th>
	        <th>实际完成情况</th>
	         <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <s:iterator value="#session.kpi.contents" var="c">
        <s:if test="#c.state=='kpi'"> 	
        <tr>
	       
	        <td>	<pre><s:property value="#c.name"  /></pre>  	</td>
	        <td>	<pre><s:property value="#c.weight"  /></pre>  </td>
	        <td>	<pre><s:property value="#c.definde"  /></pre>  </td>
	        <td>	<pre><s:property value="#c.aim"  /></pre>  </td>
	        <td>	<pre><s:property value="#c.process"  /></pre>  </td>
	        <td>	<pre><s:property value="#c.sourceUser.name"  /></pre>  </td>
	        <td>	<pre><s:property value="#c.actual"  /></pre>  </td>
	        <td>
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

	    <li><label></label></li>
	    <li><label></label> </li>
	     <li><label>GS指标</label></li>
	  </ul>
	    
  
     <ul class="toolbar1">
        <li style="background:url(./images/toolbg.gif) repeat-x;border:solid 1px #d3dbde;" 
        onclick="javascript:window.location.href='KPI_toAddGS.action'" >
        <span><img src="images/t01.png"/></span>添加GS</li>
     </ul>
  	
   </div>
    <table class="tablelist">
    	<thead>
    	<tr>
	        <th><input name="" type="checkbox" value="" checked="checked"/></th>
	        <th>指标名称<i class="sort"><img src="images/px.gif" /></i></th>
	        <th>权重</th>
	        <th>关键行动计划及评分方法</th>
	        <th>数据来源</th>
	        <th>实际完成情况</th>
	         <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <s:iterator value="#session.kpi.contents" var="c">
        <s:if test="#c.state=='gs'">
        <tr>
	        <td><input name="" type="checkbox" value="" /></td>
	        <td>	<pre><s:property value="#c.name"  /></pre>  	</td>
	        <td>	<pre><s:property value="#c.weight"  /></pre>  </td>
	        <td>	<pre><s:property value="#c.definde"  /></pre>  </td>
	        <td>	<pre><s:property value="#c.sourceUser.name"  /></pre>  </td>
	        <td>	<pre><s:property value="#c.actual"  /></pre>  </td>
	        <td>
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
