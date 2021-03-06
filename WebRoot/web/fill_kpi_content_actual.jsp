<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>编辑指标</title>
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
    $(".select1").uedSelect({
		width : 345			  
	});
	$(".select2").uedSelect({
		width : 167  
	});
	$(".select3").uedSelect({
		width : 100
	});
});
</script>
<script type="text/javascript">
	function department(){
	$("#userlist").html("<option value='0'>员工自报</option> <option value='0'></option>");
	$.ajax({
	    url: "Department_listDepartmentUserJson.action",    //请求的url地址
	    dataType: "json",   //返回格式为json
	    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
	    data: { "department.did": $("#department_did").val() },    //参数值
	    type: "GET",   //请求方式
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
</script>
</head>

<body>

	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="KPI_listForFillContent.action">指标列表</a></li>
    <li><a href="KPI_toKPI.action">填写完成情况 </a></li>
    </ul>
    </div>
    <div class="formbody">
    <div id="usual1" class="usual"> 
  	<div id="tab1" class="tabson">  
  	<form action="KPI_fillContent.action" method="post">
  	
  	<input type="hidden" name="contentKPI.cid" value="<s:property value="contentKPI.cid" />" />
    <ul class="forminfo">
    <li><label>被考核人</label><input disabled="disabled" type="text" class="dfinput" value="<s:property value="contentKPI.kpi.aimUser.name" />"   /></li>
    <li><label>指标名称</label><input disabled="disabled" type="text" class="dfinput" value="<s:property value="contentKPI.name" />"   /></li>
    <li><label>指标权重</label><input disabled="disabled" type="text" class="dfinput" value="<s:property value="contentKPI.weight" />"  /></li>
    <li><label>定义、公式</label>
    <textarea style="height: 100px" disabled="disabled" class="dfinput" ><s:property value="contentKPI.definde" /></textarea></li>
    <li><label>目标值</label><input disabled="disabled" type="text" class="dfinput" value="<s:property value="contentKPI.aim" />"  /></li>
    <li><label>计分方法</label>
    <textarea style="height: 100px" disabled="disabled" class="dfinput" ><s:property value="contentKPI.process" /></textarea></li>
    <li><label>数据来源<b>*</b></label>  
	    <div class="vocation">
	    <select class="select2" onchange="department()" id="department_did"  >
	    	<option value="<s:property value="contentKPI.sourceUser.department.did" /> "> 
	    		<s:property value="contentKPI.sourceUser.department.name" /> </option>
	    		<option value="0">部门自报</option>
	     <s:iterator value="departmentlist" var="dep"  status="status">
	     	<option value="<s:property value="#dep.did" />">	<s:property value="#dep.name" />	</option>
		</s:iterator>
	    </select>
	    </div>
	    <div class="vocation">
	    <select id="userlist" name="sourceUserUid" class="select2">
	    		<option value="<s:property value="contentKPI.sourceUser.uid" /> "> <s:property value="contentKPI.sourceUser.name" /> </option>
	    </select>
	    </div>
    </li>
    <li><label>实际完成情况<b>*</b></label>
    <textarea style="height: 100px" name="contentKPI.actual"  class="dfinput" ><s:property value="contentKPI.actual" /></textarea></li>
    
    
  </ul>
	<div class="tools">
    	<ul   class="toolbarm" style="margin-left: 40px;">
        <li><label>&nbsp;</label><input type="submit" class="btn" value="提交"/></li>
        </ul>    
    </div>
  </form>
   
    </div>
       
	</div> 
	
 
	<script type="text/javascript"> 
      $("#usual1 ul").idTabs(); 
    </script>
    
    <script type="text/javascript">
	$('.tablelist tbody tr:odd').addClass('odd');
	</script>
    </div>


</body>

</html>
