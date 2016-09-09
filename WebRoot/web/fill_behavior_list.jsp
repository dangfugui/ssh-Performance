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
sum();
});
function sum(){
	var sum=0.0;
	$(".scinput1").each(function() {
		//alert($(this).val());
		sum=sum+parseFloat($(this).val());
	});
	$("#sum").text("总分："+sum);
}
</script>


</head>


<body>

	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="">人员列表</a></li>
			<li><a href=""><s:property value="#session.aim_user.name" /> </a></li>
		</ul>
	</div>

	<div class="rightinfo">

		<div class="tools">
		

			<ul class="toolbar1">
			<li id="sum"></li>
				<li onclick="javascript:$('#form').submit() "
					style="background:url(./images/toolbg.gif) repeat-x;border:solid 1px #d3dbde;">
					<span> <img src="images/t01.png" />
				</span> 提交
				</li>

			</ul>

		</div>
		 <form id="form" action="ScoreYear_scoreYear.action">
   			 <table class="tablelist">
			<thead>
				<tr>
					<th>评分项</th>
					<th>评分标准</th>
					<th>分数</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="year.behaviors" var="b">
					<tr>
						<td><s:property value="#b.name" /></td>
						<td><pre><s:property value="#b.standard" /></pre></td>
						  <td>
	        	<input name="map[<s:property value="#b.bid"/>]"   value="<s:property value='behaviorMap[#b.bid]' />"
	        	 onkeyup="value=value.replace(/[^\d+\.]/g,'')" onchange="sum()"
	        		type="text" class="scinput1"   />
	        	</td>

					</tr>
				</s:iterator>
			</tbody>
		</table>
		</form>





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
