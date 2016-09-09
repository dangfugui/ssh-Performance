<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>模板列表 </title>
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
function update(kid){
	
	if(confirm('确认修改模板名?')){
		 $.post("KPI_addOrUpdateTemplate.action",
    		{
      			"kpi.kid":kid,
      			"kpi.state":$("#kpi"+kid).val()
    	});
	}
}
</script>


</head>


<body>

	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="KPI_listTemplate.action">模板列表</a></li>
    </ul>
    </div>
    
    <div class="rightinfo">
    
    <div class="tools">
    	<ul class="toolbarm">
    	<li>
    		<form id="myform" action="KPI_addOrUpdateTemplate.action" method="post" >
    		<input class="dfinput" type="text" name="kpi.state" value="模板名" onfocus="javascript:this.value=''"/> </form>
    	 </li>
    	 
        <li class="click" style="background:url(./images/toolbg.gif) repeat-x;border:solid 1px #d3dbde;"><span><img src="images/t01.png"/></span>添加模板</li>
        
        </ul>
        <ul class="toolbar1">
        </ul>
    </div>
    <table class="tablelist">
    	<thead>
    	<tr>
	      
	        <th>序号</th>
	        <th>模板名称</th>
	        <th>创建时间</th>
	        <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <s:iterator value="kpiList" var="kpi" status="status">
        <tr>
	      
			 <td>	<s:property value="#status.index+1"  />  	</td>
			<td><s:property value='#kpi.state'/> </td>
			<td><s:property value="#kpi.date" />	</td>
	      	<td>      	
			    <div class="tools">
		    	<ul class="toolbar">
		        <li onclick="javascript:window.location.href='KPI_toTemplate.action?kpi.kid=<s:property value="#kpi.kid"/>' "> <!--  -->
		    	<span><img src="images/t02.png" /></span>编辑模板</li>
		        <li onclick="javascript:if(confirm('确认删除季度?'))window.location.href='KPI_deleteTemplate.action?kpi.kid='+<s:property value="#kpi.kid" /> ">
		        <span><img src="images/t03.png" /></span>删除</li>
		        </ul>
		    </div>
	      	
	      	</td>
    	

        </tr> 
        </s:iterator>
        </tbody>
    </table>
    
   
    
    
    
    <div class="tip">
    	<div class="tiptop"><span>提示信息</span><a></a></div>
        
      <div class="tipinfo">
        <span><img src="images/ticon.png" /></span>
	        <div class="tipright">
		        <p>是否确认添加模板?</p>
		        <cite>如果是请点击确定按钮 ，否则请点取消。</cite>
	        </div>
        </div>
        <div class="tipbtn">
	        <input  type="button"  class="sure" value="确定" onclick="javascript:document.getElementById('myform').submit()"/>&nbsp;
	        <input name="" type="button"  class="cancel" value="取消" />
        </div>
  		</div>
    </div>
    

    
    <script type="text/javascript">
	$('.tablelist tbody tr:odd').addClass('odd');
	</script>

</body>

</html>
