<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>行为绩效列表</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
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


<body>

	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="">年度列表</a></li>
			<li><a href="">行为绩效表</a></li>
		</ul>
	</div>

	<div class="rightinfo" style="height: 100px;" >

		<div class="tools" style="height: 100px;" >
		<form id="myform" action="Year_addBehavior.action" method="post">
			<input type="hidden" name="year.yid" value="<s:property value="year.yid" />" />
			<ul class="toolbarm">
				<li>					
						<input class="dfinput" type="text" name="behavior.name"
							value="评分项"  />				
				</li>
				<li>			
						
						<textarea  class="dfinput"  name="behavior.standard" style="width: 500px;height: 80px;"
						>评分标准</textarea>	
									
				</li>
				</ul>

		</form>

			<ul class="toolbar1">
				<li onclick="javascript:$('#myform').submit() "
					style="background:url(./images/toolbg.gif) repeat-x;border:solid 1px #d3dbde;">
					<span> <img src="images/t01.png" />
				</span> 添加
				</li>

			</ul>

		</div>
		<table class="tablelist">
			<thead>
				<tr>
					
					<th>序号</th>
					<th>评分项</th>
					<th>评分标准</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="year.behaviors" var="b" status="status">
					<tr>
						
						<td><s:property value="#status.index+1"  /> </td>
						<td><s:property value="#b.name" /></td>
						<td><pre><s:property value="#b.standard" /></pre></td>


						<td>
							<div class="tools">
								<ul class="toolbar">
									<li
										onclick="javascript:if(confirm('确认删除年度?'))window.location.href='Year_deleteBehavior.action?behavior.bid=<s:property value="#b.bid" />' ">
										<span><img src="images/t03.png" /></span>删除
									</li>
								</ul>
							</div>

						</td>


					</tr>
				</s:iterator>
			</tbody>
		</table>





		<div class="tip">
			<div class="tiptop">
				<span>提示信息</span><a></a>
			</div>

			<div class="tipinfo">
				<span><img src="images/ticon.png" /></span>
				<div class="tipright">
					<p>是否确认添加季度?</p>
					<cite>如果是请点击确定按钮 ，否则请点取消。</cite>
				</div>
			</div>
			<div class="tipbtn">
				<input type="button" class="sure" value="确定"
					onclick="javascript:document.getElementById('myform').submit()" />&nbsp;
				<input name="" type="button" class="cancel" value="取消" />
			</div>
		</div>
	</div>



	<script type="text/javascript">
	$('.tablelist tbody tr:odd').addClass('odd');
	</script>

</body>

</html>
