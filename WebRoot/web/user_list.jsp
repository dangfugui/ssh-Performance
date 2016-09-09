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
    <li><a href="User_list.action">人员列表</a></li>
    </ul>
    </div>
    
    <div class="rightinfo">
	<div class="tools">
	<form action="User_list.action" method="post">
    <ul class="toolbarm">

	    <li><label>姓名</label><input name="user.name" type="text" class="scinput1" /></li>
	    <li><label>部门</label>  
		    <div class="vocation">
		    <select class="select3" name="departmentDid" >
		    	<option value="0">全部</option>
		    <s:iterator value="departmentlist" var="dep">
		    	 <option value="<s:property value="#dep.did" />">	<s:property value="#dep.name" />	</option>
		    </s:iterator>
		    </select>
		    </div>
	    </li>
	    
	    <li><label>角色</label>  
		    <div class="vocation">
		    <select class="select3"	name="roleRid">
		    	<option value="0">全部</option>
		     <s:iterator value="roleList" var="role">
		    	 <option value="<s:property value="#role.rid" />" >	<s:property value="#role.name" />	</option>
		    </s:iterator>
		    </select>
		    </div>
	    </li>    
	    <li><input  type="submit" class="sure" value="查询"/></li>
    </ul>
    </form>
     <ul class="toolbar1">
        <li style="background:url(./images/toolbg.gif) repeat-x;border:solid 1px #d3dbde;" 
        onclick="javascript:window.location.href='User_toAddUser.action'">
        <span><img src="images/t01.png"/></span>添加用户</li>
     </ul>
  	
   </div>
    <table class="tablelist">
    	<thead>
    	<tr>
	      
	        <th>序号</th>
	        <th>标号</th>
	        <th>用户名</th>
	        <th>密码</th>
	        <th>部门</th>
	        <th>角色</th>
	        <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <s:iterator value="list" var="u" status="status">
        <tr>
	        <td>	<s:property value="#status.index+1"  />  	</td>
	         <td>	<s:property value="#u.agentId"  />  	</td>
	        <td>	<s:property value="#u.name"  />  </td>
	        <td>	***  </td>
	        <td>	<s:property value="#u.department.name"  />  </td>
	        <td>	<s:property value="#u.role.name"  />  </td>
	        <td>
	        	<div class="tools">
			    	<ul class="toolbar">
			        <li onclick="window.location.href='User_toUpdate.action?user.uid=<s:property value="#u.uid"/>'" >
			    	<span><img src="images/t02.png" /></span>修改</li>
			        <li onclick="javascript:if(confirm('确认删除人员吗?'))window.location.href='User_delete.action?user.uid='+<s:property value="#u.uid" /> ">
			        <span><img src="images/t03.png" /></span>删除</li>
			        </ul>
		    	</div>
	        </td>
        </tr> 
       </s:iterator>
    
        </tbody>
    </table>
     </div>

	<div class="tip">
    	<div class="tiptop"><span>提示信息</span><a></a></div>
        
      <div class="tipinfo">
        <span><img src="images/ticon.png" /></span>
	        <div class="tipright">
		        <p>是否确认添加部门?</p>
		        <cite>如果是请点击确定按钮 ，否则请点取消。</cite>
	        </div>
        </div>
        <div class="tipbtn">
	        <input  type="button"  class="sure" value="确定" onclick="javascript:document.getElementById('myform').submit()"/>&nbsp;
	        <input name="" type="button"  class="cancel" value="取消" />
        </div>
  		</div>
    
 

    
    
   


</body>

</html>
