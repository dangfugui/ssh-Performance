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

function num(val){
	var weight=$(val).val();
	if(isNaN(weight)||weight>100){
		$(val).val("0");
	}
}


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
    <li><a href="User_listForKPI.action">人员列表</a></li>
    <li><a href="KPI_toKPI.action"><s:property value="#session.kpi_user.name" />  </a></li>
    <li><a href="#"><s:property value="info" />  </a></li>
    </ul>
    </div>
    <div class="formbody">
    <div id="usual1" class="usual"> 
  	<div id="tab1" class="tabson">  
  	<form action="KPI_addOrUpdateContent.action" method="post">
  	
  	<input type="hidden" name="contentKPI.cid" value="<s:property value="contentKPI.cid" />" />
  	<input type="hidden" name="contentKPI.state" value="gs" />
 <ul class="forminfo">
    <li><label>指标名称<b>*</b></label><input name="contentKPI.name" type="text" class="dfinput" value="<s:property value="contentKPI.name" />"   /></li>
    <li><label>指标权重<b>%</b></label><input  onkeyup="num(this)"  
    	name="contentKPI.weight" type="text" class="dfinput" value="<s:property value="contentKPI.weight" />"  /></li>
    <li><label>关键行动计划及评分方法</label>
    <textarea style="height: 160px" name="contentKPI.definde"  class="dfinput" 
    	 ><s:property value='contentKPI.definde'/></textarea>		</li>
    <li><label>数据来源</label>  
	    <div class="vocation">
	    <select class="select2" onchange="department()" id="department_did"  >
	   		<s:if test="info=='更改指标'&&contentKPI.sourceUser!=null">
	    		<option value="<s:property value="contentKPI.sourceUser.department.did" /> "> 
	    		<s:property value="contentKPI.sourceUser.department.name" /> </option>
	    		<option value="0">部门自报</option>
	    	</s:if>
	    	<s:else>
	    		 <option value="0">部门自报</option>
	    	</s:else>
	     <s:iterator value="departmentlist" var="dep"  status="status">
	     	<option value="<s:property value="#dep.did" />">	<s:property value="#dep.name" />	</option>
		</s:iterator>
	    </select>
	    </div>
	    <div class="vocation">
	    <select id="userlist" name="sourceUserUid" class="select2">
	    	<s:if test="info=='更改指标'&&contentKPI.sourceUser!=null">
	    		<option value="<s:property value="contentKPI.sourceUser.uid" /> "> <s:property value="contentKPI.sourceUser.name" /> </option>
	    	</s:if>
	    	<s:else>
	    		 <option value="0">员工自报</option>
	    		 
	    	</s:else>
	    </select>
	    </div>
    </li>
    <li><label>实际完成情况<b>*</b></label><input name="contentKPI.actual" type="text" class="dfinput" value="<s:property value="contentKPI.actual" />"  /></li>
    
    
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
