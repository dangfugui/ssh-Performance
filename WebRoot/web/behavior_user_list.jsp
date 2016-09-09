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
    <li><a href="#">年度人员列表</a></li>
    </ul>
    </div>
    
    <div class="rightinfo">
	<div class="tools">
	<form action="User_listForKPI.action" method="post" >
    <ul class="toolbarm">
		 <li><label>年度</label>  </li>
		 <li><label><s:property value="year.name" /> </label>  	</li>
		
    </ul>
    </form>
    
  	
   </div>
    <table class="tablelist">
    	<thead>
    	<tr>
	     
	        <th>序号</th>
	        <th>用户名</th>
	        <th>角色</th>
	        <th>比例</th>
	        <th>得分</th>
	        <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <s:iterator value="averageList" var="a" status="status">
        <tr>
	     
	         <td>	<s:property value="#status.index+1"  />  	</td>
	         <td>	<s:property value="#a.aimUser.name"  />  </td>
	        <td>	<s:property value="#a.aimUser.role.name"  />  </td>
	        <td>	<s:property value="#a.average"  />  </td>
	         <td>	<s:property value="#a.state"  />  </td>
	        <td>
	        	<div class="tools">
			    	<ul class="toolbar">
			        <li onclick="window.location.href='Average_toScoreYear.action?average.aid=<s:property value="#a.aid"/>'" >
			    	<span><img src="images/t02.png" /></span>评分</li>
			        </ul>
		    	</div>
	        </td>
        </tr> 
       </s:iterator>
    
        </tbody>
    </table>
     </div>
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
    </div>
 

    
    
   


</body>

</html>
