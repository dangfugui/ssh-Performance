<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>角色列表 </title>
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
function update(rid){
	var name=$("#role_name"+rid).val();
	var grade=$("#role_grade"+rid).val();
	if(!isNaN(grade)){
		if(confirm('确认修改信息?')){
			 $.post("Role_update.action",
	    		{
	      			"role.rid":rid,
	      			"role.name":name,
	      			"role.grade":grade
	    		}); 
    	}
	}else{
	  alert("角色等级必须是整数");
	}
}

function addRole(){
	//alert(isNaN($('#grade').val())+">>>"+$('#grade').val());
	var t=$("#grade").val();//这个就是我们要判断的值了
	if(!isNaN(t)){
	  document.getElementById('myform').submit();
	}else{
	  alert("角色等级必须是整数");
	}
}

</script>


</head>


<body>

	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="">角色列表</a></li>
    </ul>
    </div>
    
    <div class="rightinfo">
    
    <div class="tools">
    	<ul class="toolbarm">
    	<li>
    		<form id="myform" action="Role_add.action" method="post" >
    			<input class="dfinput" type="text" name="role.name" value="添加角色名" onfocus="javascript:this.value=''" /> 
    			<input class="dfinput" type="text" id="grade"  onkeyup="this.value=this.value.replace(/\D/g,'')"
    			name="role.grade" value="角色等级" onfocus="javascript:this.value=''"/>		
    	</form>
    	 </li>
    	 
        <li class="click" style="background:url(./images/toolbg.gif) repeat-x;border:solid 1px #d3dbde;" ><span><img src="images/t01.png"/></span>添加角色</li>
        </ul>
        <ul class="toolbar1">
        </ul>
    </div>
    <table class="tablelist">
    	<thead>
    	<tr>
	       
	        <th>序号</th>
	        <th>角色名称</th>
	        <th>角色等级</th>
	        <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <s:iterator value="list" var="role" status="status">
        <tr>
	       
			 <td>	<s:property value="#status.index+1"  />  	</td>
			<td><input class="dfinput" type="text" id="role_name<s:property value="#role.rid" />" value="<s:property value="#role.name" />"/></td>
			<td><input   onkeyup="this.value=this.value.replace(/\D/g,'')"
				class="scinput1" type="text" id="role_grade<s:property value="#role.rid" />" value="<s:property value="#role.grade" />"/></td>
	      	<td>      	
			    <div class="tools">
		    	<ul class="toolbar">
		        <li onclick="update(<s:property value="#role.rid" />)">
		    	<span><img src="images/t02.png" /></span>修改</li>
		        <li onclick="javascript:if(confirm('确认删除角色?'))window.location.href='Role_delete.action?role.rid='+<s:property value="#role.rid" /> ">
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
		        <p>是否确认添加角色?</p>
		        <cite>如果是请点击确定按钮 ，否则请点取消。</cite>
	        </div>
        </div>
        <div class="tipbtn">
	        <input  type="button"  class="sure" value="确定" onclick="addRole()"/>&nbsp;
	        <input name="" type="button"  class="cancel" value="取消" />
        </div>
  		</div>
    </div>
    

    
    <script type="text/javascript">
	$('.tablelist tbody tr:odd').addClass('odd');	
	</script>

</body>

</html>
