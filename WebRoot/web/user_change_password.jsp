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
<title>信息修改</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/jquery.js"></script>
<script src="js/cloud.js" type="text/javascript"></script>
<script  type="text/javascript">

function toVaild(){

	if($("#password").val()==$("#password2").val())
    {
      return true; 
   }else{
         $("#message").html("两次密码不同");
    }
    return false;
}
</script>
</head>
<body>

	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
   	 	<li><a href="user_change_password.jsp">信息修改</a></li>
    </ul>
    </div>
    
    <div class="formbody">
    
    <div class="formtitle"><span>基本信息</span></div>
    <form onsubmit="return toVaild()" action="User_changePassword.action">
    <ul class="forminfo">
        <li><label>用户名</label><input disabled="disabled" name="user.name" type="text" class="dfinput" value="<s:property value="#session.login_user.name"  /> " /><i>不可编辑</i></li>
    <li><label>当前密码</label><input name="user.password" type="text" class="dfinput" /><i><s:property value="info" /> </i></li>
    <li><label>新密码</label><input id="password" name="info" type="password" class="dfinput" /><i></i></li>
    <li><label>重复密码</label><input id="password2" type="password" class="dfinput" /><i id="message"></i></li>
    <li><label>&nbsp;</label><input  type="submit" class="btn" value="确认保存"/></li>
    </ul>
    </form>
    
    </div>


</body>

</html>
