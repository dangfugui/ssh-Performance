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
    	<li><a href="User_listForProportion.action">年度列表</a></li>
    	<li>添加年度</li>
    </ul>
    </div>
    <form action="Year_add.action" method="post">
     <input type="hidden" name="year.yid" value='<s:property value="year.yid"/>' />
    <div class="rightinfo">
	<div class="tools">
    <ul class="toolbarm">
	    <li><label></label></li>		<li><label>  </label></li>
	    <li><label>年度名</label></li>
	    <li><input name="year.name"  type="text"  class="dfinput" value='<s:property value="year.name"/>' /></li>
        </ul>
   </div>
    <table class="tablelist">
    	<thead>
    	<tr>
	        <th>选择</th>
	        <th>季度</th>
	        <th>所占比例</th>
        </tr>
        </thead>
        <tbody>
        <s:iterator value="quarterList" var="q" status="status"> 
        <tr>
            <td><s:if test="#session.year.yid==#q.year.yid">
            <input name="quarterList.qid" type="checkbox" value='<s:property value="#q.qid"/>' checked="checked" /></s:if>
            <s:else> <input name="quarterList.qid" type="checkbox" value='<s:property value="#q.qid"/>' /></s:else>
            </td>

	        <td><s:property value="#q.name"/>	</td>
	        <td><label><input type="text" class="scinput1"  onkeyup="this.value=this.value.replace(/[^\-+\d+\.]/g,'')"  
	    	name="map[<s:property value="#q.qid"/>]" value='<s:property value="#q.proportion"/>'/></label></td>
	    </tr>
        </s:iterator>
        </tbody>
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
