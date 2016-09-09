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
<title>kpi表</title>
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


function change(contentKpiid,input){
	//alert($(input).val());
	if($("#sourceUserid").val()==0){
		alert("不能修改综合成绩");
		return;
	}
	$.ajax({
	    url: "KPI_updateByRoot.action",    //请求的url地址
	    dataType: "html",   //返回格式为json
	    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
	    data: {"contentKPI.cid":contentKpiid,"sourceUser.uid":$("#sourceUserid").val(),"ContentKPI.result":$(input).val() },    //参数值
	    type: "GET",   //请求方式
	    complete: function() {alert("修改成功")}//请求完成的处理},
	    //error: function() {//请求出错处理 }
	});
}
</script>

</head>

<body class="sarchbody">

	<div class="place">
	   	 <span>位置：</span>
	    <ul class="placeul">
	   
	     <li><a href="#"><s:property value="user.name"/> 的KPI</a></li>
	    </ul>
	    </div>
    <div class="rightinfo">
    
	<div class="tools">
  	<form id="form" action="KPI_kpiResult.action" method="get">
    <ul class="toolbarm">
		 <li><label>季度</label>  
		    <div class="vocation">
		    <input type="hidden" name="user.uid" value="<s:property value="user.uid" />" /> 
		    <select class="select3"	name="quarter.qid" id="quarterid" >
		     <s:iterator value="quarterList" var="quarter">
		     <s:if test="#quarter.qid==#session.quarter.qid">
		     	 <option selected = "selected" value='<s:property value="#quarter.qid" />' >
		    	 		<s:property value="#quarter.name" />	</option>
		     </s:if>
		     <s:else>
		    	 <option value='<s:property value="#quarter.qid" />' >
		    	 		<s:property value="#quarter.name" />	</option>
		    </s:else>
		    </s:iterator>
		    </select>
		    </div>
	    </li> 
		<s:if test="user.uid!=#session.login_user.uid">
	     <li><label>填表人</label>  
		    <div class="vocation">
		    <select class="select3"	name="sourceUser.uid" id="sourceUserid" onchange="javascript:$('#form').submit()">
		    	<option value='0' >综合成绩</option>
		     <option value='00' >   </option>
		     <s:iterator value="sourceUserList" var="u">
		     <s:if test="#u.uid==sourceUser.uid">
		     	 <option selected = "selected" value='<s:property value="#u.uid" />' >
		    	 		<s:property value="#u.name" />	</option>
		     </s:if>
		     <s:else>
		    	 <option value='<s:property value="#u.uid" />' >
		    	 		<s:property value="#u.name" />	</option>
		    </s:else>
		    </s:iterator>
		    </select>
		    </div>
	    </li>  </s:if>   
		<li><label>KPI</label> </li>
	    <li><label>被考核人</label> </li>
	    <li><label><s:property value="kpi.aimUser.name" /> </label></li>
	    <li><label>拟表人</label> </li>
	    <li><label> <s:property  value="kpi.fillUser.name"/>  </label></li>
	     <li><label>综合成绩</label> </li>
	    <li><label> <s:property  value="kpiContentScore[0]"/>  </label></li>
    </ul>
    </form>

  	
   </div>
   
    <table class="tablelist">
    	<thead>
    	<tr>
	        
	        <th   style="width:50px">指标名称<i class="sort"><img src="images/px.gif" /></i> </th>
	        <th style="width:10px">权重</th>
	        <th style="width:100px">定义、计算公式</th>
	        <th style="width:30px">目标值</th>
	        <th style="width:100px">计分方法</th>
	        <th style="width:50px">数据来源</th>
	        <th style="width:180px">实际完成情况</th>
	        <th width="90px">得分</th>
        </tr>
        </thead>
        <tbody>
        <s:iterator value="kpi.contents" var="c">
        <s:if test="#c.state=='kpi'">
        <tr>
	      
	        <td><pre><s:property value="#c.name"  /></pre></td>
	        <td><pre><s:property value="#c.weight"  />%</pre></td>
	        <td><pre><s:property value="#c.definde"  /> </pre> </td>
	        <td><pre><s:property value="#c.aim"  /></pre></td>
	        <td><pre><s:property value="#c.process"  /></pre></td>
	        <td><pre><s:property value="#c.sourceUser.name"  /></pre> </td>
	        <td><pre><s:property value="#c.actual"  /> </pre></td>
	        <td><input type="text" value="<s:property value="kpiContentScore[#c.cid]" /> "  onkeyup="this.value=value.replace(/[^\d+\.]/g,'')"  onchange="change(<s:property value='#c.cid'/>,this) "  /></td>
	        <!-- <a href="KPI_lookScore.action?contentKPI.cid=<s:property value="#c.cid"/>">详情</a> -->
        </tr> 
        </s:if>
       </s:iterator>
        </tbody>
    </table>
     </div>

	<div class="tools">
  	<form id="form" action="KPI_kpiResult.action" method="get">
    <ul class="toolbarm">
    		<li><label>	</label> </li>
    		<li><label>		</label> </li>
		<li><label>GS</label> </li>
    </ul>
    </form>

  	
   </div>
   
    <table class="tablelist">
    	<thead>
    	<tr>
	       
	       <th width="150px">指标名称<i class="sort"><img src="images/px.gif" /></i></th>
	        <th width="50px">权重</th>
	        <th width="300px">关键行动计划及评分方法  </th>
	        <th width="100px">数据来源</th>
	        <th width="300px">实际完成情况</th>
	        <th width="160px">得分</th>
        </tr>
        </thead>
        <tbody>
        <s:iterator value="kpi.contents" var="c">
        <s:if test="#c.state=='gs'">
        <tr>
	      
	        <td><pre><s:property value="#c.name"  /></pre></td>
	        <td><pre><s:property value="#c.weight"  />%</pre></td>
	        <td><pre><s:property value="#c.definde"  /> </pre></td>
	        <td><pre><s:property value="#c.sourceUser.name"  /></pre> </td>
	        <td><pre><s:property value="#c.actual"  /></pre></td>
	        <td>	
	        <input type="text" value="<s:property value="kpiContentScore[#c.cid]" /> "  onkeyup="this.value=value.replace(/[^\d+\.]/g,'')"  onchange="change(<s:property value='#c.cid'/>,this) "  /></td>
	        <!-- <a href="KPI_lookScore.action?contentKPI.cid=<s:property value="#c.cid"/>">详情</a> -->
        </tr> 
        </s:if>
       </s:iterator>
        </tbody>
    </table>
</body>

</html>
