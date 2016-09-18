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
}

function checkedAll(roleid){
	//$(".c"+roleid+"[checked!='checked']").attr("checked","checked");
	//$(".c"+roleid+":checked").removeAttr("checked");
	$(".c"+roleid).each(function() {
		//此处用Jquery写法
      	//$(this).attr("checked", !$(this).attr("checked"));
     	//直接使用JS原生代码，简单实用
      this.checked = !this.checked;
	});
	//$(".c"+roleid).attr("checked", !$(".c"+roleid).attr("checked"));
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
    	<li><a href="User_listForProportion.action">比例列表</a></li>
    	<li><a href="#"><s:property value="user.name" />的比例</a></li>
    </ul>
    </div>
    <form action="Proportion_setProportion.action">
     <s:iterator value="roleList" var="role">
    <div class="rightinfo">
	<div class="tools">
    <ul class="toolbarm">

	   <li></li>
		<li style="background-color:#d9ebf3;" onclick="checkedAll('<s:property value="#role.rid"/>')" >反选</li>
		 <li><label></label></li>		
	    <li><label>角色名</label></li>
	    <li><input value="<s:property value='#role.name'/>"  type="text" disabled="disabled" class="scinput1" /></li>
	    <li><label></label> </li>
	 	<li><label>比例<b>%</b></label> </li>
	    <li><label><input type="text" class="scinput1"  onkeyup="this.value=this.value.replace(/[^\-+\d+\.]/g,'')"  
	    	name="map[<s:property value="#role.rid"/>]" /></label> </li>
	    <li><label></label> </li>
	   <!--  </ul>
	     <ul class="toolbar">
       	 <li onclick="setFillUser()"><span><img src="images/t05.png" /></span>设置</li>
        </ul>-->
        </ul>
   </div>
    <table class="tablelist">
    	<thead>
    	<tr>
	        <th>选择</th>
	        <th>姓名</th>
	        <th>选择</th>
	        <th>姓名</th>	        
	        <th>选择</th>
	        <th>姓名</th>
	        <th>选择</th>
	        <th>姓名</th>
        </tr>
        </thead>
        <tbody>
        <s:set name="h" value="0"></s:set> 
        <s:iterator value="userList" var="u" status="status"> 
        <s:if test="#u.role==#role">
	        <s:if test="#h%4==0"> <tr> </s:if>
	        	<td><input name="userIDs" class="c<s:property value="#role.rid"/>" type="checkbox" value='<s:property value="#u.uid"/>' /></td>
	        	<td><s:property value="#u.name"/>	</td>
	        <s:if test="#h%4==3"> </tr> </s:if>
	        <s:set name="h" value="#h+1" />
        </s:if>
        </s:iterator>
        </tbody>
    </table>
     </div>
	</s:iterator>
	<div class="tools">
    	<ul   class="toolbarm" style="margin-left: 40px;">
        <li><label></label><input name="" type="submit" class="btn" value="提交"/></li>
        </ul>    
    </div>
	</form>

	
</body>

</html>
