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
			width : 150
		});
	});

	function newlist() {
		var name = $("#user.name").val();
		var departmentDid = $("#departmentDid").val();
		var roleRid = $("roleRid").val();
		/*$.post("User_list.action",
		{
		  	"user.name":name,
		  	"departmentDid":departmentDid,
		  	"roleRid":roleRid
		}); */
		myu("User_list.action", {
			"user.name" : name,
			"departmentDid" : departmentDid,
			"roleRid" : roleRid
		});

	}
</script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".click").click(function() {
			$(".tip").fadeIn(200);
		});

		$(".tiptop a").click(function() {
			$(".tip").fadeOut(200);
		});

		$(".sure").click(function() {
			$(".tip").fadeOut(100);
		});

		$(".cancel").click(function() {
			$(".tip").fadeOut(100);
		});

	});
</script>

<script type="text/javascript">
	function copyKPI() {
		var userids = $("#userIdList").val();
		alert(userIdList);
		var qid = $("#quarterQid").val();
		if (confirm("确认导入到本季度指标")) {
			$.ajax({
				url : "KPI_copy.action", //请求的url地址
				dataType : "html", //返回格式为json
				async : true, //请求是否异步，默认为异步，这也是ajax重要特性
				data : {
					"quarter.qid" : qid,
					"userIdList" : userIdList
				}, //参数值
				type : "GET", //请求方式
				//beforeSend: function() {alert("开始计算")},
				success : function(result) {//请求成功时处理
					alert("设置成功");
				}
			// complete: function() {alert("设置成功")}//请求完成的处理},
			//error: function() {//请求出错处理 }
			});
		}
	}
	
</script>

</head>

<body class="sarchbody">

	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="#">人员列表</a></li>
		</ul>
	</div>

	<div class="rightinfo">
		<div class="tools">
			<form action="User_listForKPI.action" method="get">
				<ul class="toolbarm">
					<li><label>季度</label>
						<div class="vocation">
						<select class="select3" name="quarterQid" >
								<s:iterator value="quarterList" var="quarter">
									<s:if test="#session.quarter.qid==#quarter.qid">
										<option selected="selected"
											value='<s:property value="#quarter.qid" />'>
											<s:property value="#quarter.name" />
										</option>
									</s:if>
									<s:else>
										<option value='<s:property value="#quarter.qid" />'>
											<s:property value="#quarter.name" />
										</option>
									</s:else>
								</s:iterator>
							</select>
						</div></li>

					<li><label>姓名</label><input name="user.name" type="text"
						class="scinput1" /></li>
					<li><label>部门</label>
						<div class="vocation">
							<select class="select3" name="departmentDid">
								<option value="0">全部</option>
								<s:iterator value="departmentlist" var="dep">
									<option value="<s:property value="#dep.did" />">
										<s:property value="#dep.name" />
									</option>
								</s:iterator>
							</select>
						</div></li>

					<li><label>角色</label>
						<div class="vocation">
							<select class="select3" name="roleRid">
								<option value="0">全部</option>
								<s:iterator value="roleList" var="role">
									<option value="<s:property value="#role.rid" />">
										<s:property value="#role.name" />
									</option>
								</s:iterator>
							</select>
						</div></li>
					<li><input type="submit" class="sure" value="查询" /></li>
				</ul>
			</form>
			
			<form action="KPI_copy.action" method="get" id="copy" >
			<s:if test="#session.login_user.role.grade<100">
				<ul class="toolbar1">
					<li>复用</li>
					<li><label>季度</label>
						<div class="vocation">
							<select class="select3" name="quarter.qid">
								<s:iterator value="quarterList" var="quarter">
									<s:if test="#session.quarter.qid==#quarter.qid">
										<option selected="selected"
											value='<s:property value="#quarter.qid" />'>
											<s:property value="#quarter.name" />
										</option>
									</s:if>
									<s:else>
										<option value='<s:property value="#quarter.qid" />'>
											<s:property value="#quarter.name" />
										</option>
									</s:else>
								</s:iterator>
							</select>
						</div></li>
					<li
						style="background:url(./images/toolbg.gif) repeat-x;border:solid 1px #d3dbde;"
						onclick="javascript:$('#copy').submit()" ><span><img src="images/t05.png" /></span>确认</li>
				</ul>
			</s:if>
		</div>

			<table class="tablelist">
				<thead>
					<tr>
						<th><input name="chk_all" type="checkbox"  /></th>
						<th>编号</th>
						<th>用户名</th>
						<th>部门</th>
						<th>角色</th>
						<th>指标数</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="list" var="u" status="status">
						<tr>
							<td><input name="userIdList" type="checkbox" value='<s:property value="#u.uid"/>' /></td>
							<td><s:property value="#status.index+1" /></td>
							<td><s:property value="#u.name" /></td>
							<td><s:property value="#u.department.name" /></td>
							<td><s:property value="#u.role.name" /></td>
							<td><s:property value="proportionMap[#u.uid]" /> </td>
							<td>
								<div class="tools">
									<ul class="toolbar">
										<li
											onclick="window.location.href='KPI_toKPI.action?user.uid=<s:property value="#u.uid"/>'">
											<span><img src="images/t02.png" /></span>拟定指标
										</li>
										<!--  <li onclick="javascript:if(confirm('确认删除人员吗?'))window.location.href='User_delete.action?user.uid='+<s:property value="#u.uid" /> ">
			        <span><img src="images/t03.png" /></span>删除</li>-->
									</ul>
								</div>
							</td>
						</tr>
					</s:iterator>
		
				</tbody>
			</table>
			</form>

		

	</div>



</body>

</html>
