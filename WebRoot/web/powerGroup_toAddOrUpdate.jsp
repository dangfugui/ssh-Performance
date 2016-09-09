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
		width : 100
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
alert(44);
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
    	<li><a href="PowerGroup_list.action">权限组列表</a></li>
    	<li>添加或修改权限组</li>
    </ul>
    </div>
    <form action="PowerGroup_addOrUpdate.action" method="post">
    	
    <div class="rightinfo">
	<div class="tools">
    <ul class="toolbarm">

	    <li><label>权限组名</label></li>
	    <li><input name="powerGroup.name"  type="text"  class="dfinput"  value="<s:property value='powerGroup.name' />"/></li>
	   <li><input type="hidden" name="powerGroup.pid" value="<s:property value='powerGroup.pid' />" /> </li>
        </ul>
   </div>
   		 <table class="tablelist">
    	<thead>
    	<tr>
    		
    		<th>管理范围</th>
    		<th>考核查询</th>
    		<th>季度考核</th>
    		<th>年度考核</th>
    		<th>人员管理</th>
    	</tr>
    	<tr>
    		<td><input type="checkbox" name="powerList" value="A1" <s:if test="powerGroup.powerString.contains('A1')">checked="checked"</s:if> /> <label> </label>超级管理员</td>
    		<td><input type="checkbox" name="powerList" value="B1" <s:if test="powerGroup.powerString.contains('B1')">checked="checked"</s:if>  /> <label> </label>我的评分比例 </td>
    		<td><input type="checkbox" name="powerList" value="C1" <s:if test="powerGroup.powerString.contains('C1')">checked="checked"</s:if> /> <label> </label>季度管理</td>
    		<td><input type="checkbox" name="powerList" value="D1" <s:if test="powerGroup.powerString.contains('D1')">checked="checked"</s:if>  /> <label> </label>年度考核  </td>
    		<td><input type="checkbox" name="powerList" value="E1" <s:if test="powerGroup.powerString.contains('E1')">checked="checked"</s:if>  /> <label> </label>人员列表 </td>
    	</tr>
    	<tr>
    		<td><input type="checkbox" name="powerList" value="A2" <s:if test="powerGroup.powerString.contains('A2')">checked="checked"</s:if>  /> <label> </label>仅本部门</td>
    		<td><input type="checkbox" name="powerList" value="B2" <s:if test="powerGroup.powerString.contains('B2')">checked="checked"</s:if>  /> <label> </label>我要填表 </td>
    		<td><input type="checkbox" name="powerList" value="C2" <s:if test="powerGroup.powerString.contains('C2')">checked="checked"</s:if>  /> <label> </label>拟定指标</td>
    		<td><input type="checkbox" name="powerList" value="D2" <s:if test="powerGroup.powerString.contains('D2')">checked="checked"</s:if>  /> <label> </label>考核结果</td>
    		<td><input type="checkbox" name="powerList" value="E2" <s:if test="powerGroup.powerString.contains('E2')">checked="checked"</s:if>  /> <label> </label>比例设定 </td>
    	</tr>
    	<tr>
    		<td><input type="checkbox" name="powerList" value="A3" <s:if test="powerGroup.powerString.contains('A3')">checked="checked"</s:if>  /> <label> </label>非本部门</td>
    		<td><input type="checkbox" name="powerList" value="B3" <s:if test="powerGroup.powerString.contains('B3')">checked="checked"</s:if>  /> <label> </label>季度打分 </td>
    		<td><input type="checkbox" name="powerList" value="C3" <s:if test="powerGroup.powerString.contains('C3')">checked="checked"</s:if>  /> <label> </label>指标模板</td>
    		<td> </td>
    		<td><input type="checkbox" name="powerList" value="E3" <s:if test="powerGroup.powerString.contains('E3')">checked="checked"</s:if>  /> <label> </label>部门列表</td>
    	</tr>
    	<tr>
    		<td><input type="checkbox" name="powerList" value="A4" <s:if test="powerGroup.powerString.contains('A4')">checked="checked"</s:if>  /> <label> </label>角色比自己小的</td>
    		<td><input type="checkbox" name="powerList" value="B4" <s:if test="powerGroup.powerString.contains('B4')">checked="checked"</s:if>  /> <label> </label>年度打分</td>
    		<td><input type="checkbox" name="powerList" value="C4" <s:if test="powerGroup.powerString.contains('C4')">checked="checked"</s:if>  /> <label> </label>考核结果</td>
    		<td> </td>
    		<td><input type="checkbox" name="powerList" value="E4" <s:if test="powerGroup.powerString.contains('E4')">checked="checked"</s:if>  /> <label> </label>角色列表 </td>
    	</tr>
    	<tr>
    		<td><input type="checkbox" name="powerList" value="A5" <s:if test="powerGroup.powerString.contains('A5')">checked="checked"</s:if>  /> <label> </label>计算结果</td>
    		<td><input type="checkbox" name="powerList" value="B5" <s:if test="powerGroup.powerString.contains('B5')">checked="checked"</s:if>  /> <label> </label>我的指标 </td>
    		<td><input type="checkbox" name="powerList" value="C5" <s:if test="powerGroup.powerString.contains('C5')">checked="checked"</s:if>  /> <label> </label>互评详表</td>
    		<td> </td>
    		<td><input type="checkbox" name="powerList" value="E5" <s:if test="powerGroup.powerString.contains('E5')">checked="checked"</s:if>  /> <label> </label>权限组列表 </td>
    	</tr>
    	</thead>
    	</table>
     </div>
	<div class="tools">
    	<ul   class="toolbarm" style="margin-left: 40px;">
        <li><label></label><input name="" type="submit" class="btn" value="提交"/></li>
        </ul>    
    </div>
	</form>

	
</body>

</html>
