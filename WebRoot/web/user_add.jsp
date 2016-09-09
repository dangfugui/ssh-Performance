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
<title>用户详情</title>
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
</head>

<body>

	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="User_list.action">用户列表</a></li>
    <li><a href="#"><s:property value="info" />  </a></li>
    </ul>
    </div>
    <div class="formbody">
    <div id="usual1" class="usual"> 
  	<div id="tab1" class="tabson">  
  	<form action="User_addOrUpdate.action" method="post">
  	
  	<input type="hidden" name="info" value="<s:property value="info" />" />
  	<input type="hidden" name="user.uid" value="<s:property value="user.uid" />" />
    <ul class="forminfo">
     <li><label>标号<b>*</b></label><input name="user.agentId" type="text" class="dfinput" value="<s:property value="user.agentId" />"   /></li>
    <li><label>用户名<b>*</b></label><input name="user.name" type="text" class="dfinput" value="<s:property value="user.name" />"   /></li>
    <li><label>密码<b>*</b></label><input name="user.password" type="text" class="dfinput" value="123456"  /></li>
    <li><label>部门<b>*</b></label>  
	    <div class="vocation">
	    <select class="select1" name="departmentDid"  >
	     <s:iterator value="departmentlist" var="dep"  status="status">
	     	<s:if test="#dep.did!=user.department.did">
	     		<option value="<s:property value="#dep.did" />">	<s:property value="#dep.name" />	</option>
	     	</s:if>	
	     	<s:else>
	     		<option selected="selected" value="<s:property value="#dep.did" />">	<s:property value="#dep.name" />	</option>
	     	</s:else>
		</s:iterator>
	    </select>
	    </div>
    </li>
    <li><label>角色<b>*</b></label>   
	    <div class="vocation">
	    <select name="roleRid" class="select1">
	     	<s:iterator value="roleList" var="role" status="status">
	     	<s:if test="#role.rid!=user.role.rid">
			   <option value="<s:property value="#role.rid" /> ">	<s:property value="#role.name" />	</option>
			</s:if>
			<s:else>
				<option selected="selected" value="<s:property value="#role.rid" /> ">	<s:property value="#role.name" />	</option>
			</s:else>
			</s:iterator>
	    </select>
	    </div>
    </li>
    
      <li><label>权限组<b>*</b></label>   
	    <div class="vocation">
	    <select name="powerGroupPid" class="select1">
	     	<s:iterator value="powerGroupList" var="powerGroup" status="status">
				<option  <s:if test="#powerGroup.pid==user.powerGroup.pid">selected="selected"</s:if>
					 value="<s:property value="#powerGroup.pid" /> ">	<s:property value="#powerGroup.name" />	</option>
			</s:iterator>
	    </select>
	    </div>
    </li>
  </ul>
  
  
	<div class="tools">
    	<ul   class="toolbarm" style="margin-left: 40px;">
        <li><label>&nbsp;</label><input name="" type="submit" class="btn" value="提交"/></li>
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
