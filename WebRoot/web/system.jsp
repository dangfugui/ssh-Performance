<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统设置</title>
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
			<li><a href="#">系统设置</a></li>
		</ul>
	</div>

	<div class="formbody">
		<div id="usual1" class="usual">

			<div class="itab">
				<ul>
					<li><a href="#tab1" class="selected">流程控制</a></li>
					<li><a href="#tab2">子系统设置</a></li>
				</ul>
			</div>

			<div id="tab1" class="tabson">
			<h1>选择要显示的菜单</h1><br/>
			<form action="System_setMenu.action" method="post">
				<ul class="forminfo">
					<li><input type="checkbox" name="menuMap['proportion']" value="show" /> <label> </label>我的评分比例  </li>
					<li><input type="checkbox" name="menuMap['fill']"  value="show" /> <label> </label>我要填表  </li>
					<li><input type="checkbox" name="menuMap['quarter']" value="show" /> <label> </label>季度打分 </li>
					<li><input type="checkbox" name="menuMap['year']" value="show" /> <label> </label>年度打分  </li>
					<li><input type="checkbox" name="menuMap['my']"  value="show" /> <label> </label>我的指标 </li>
					<li><label>&nbsp;</label><input name="" type="submit"
						class="btn" value="提交" /></li>
				</ul>
			</form>
			</div>


			<div id="tab2" class="tabson">
			<form action="System_setSubsystem.action">
				<ul class="seachform">
					<h1>子系统设定</h1><br/>
					<li><label>总子系统总数</label><input name="total" type="text" onkeyup="value=value.replace(/[^\d]/g,'')"
						class="scinput" /></li>
					<li><label>子系统编号</label><input name="subsystem" type="text" onkeyup="value=value.replace(/[^\d]/g,'')"
						class="scinput" /></li>
					
					<li><label>&nbsp;</label><input name="" type="submit"
						class="scbtn" value="部署" /></li>

				</ul>
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

