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
<title>kpi打分</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/select.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery.idTabs.min.js"></script>
<script type="text/javascript" src="js/select-ui.min.js"></script>
<script type="text/javascript" src="editor/kindeditor.js"></script>
<script type="text/javascript">
</script>
<style type="text/css">
	.table{border:solid #add9c0; border-width:1px 0px 0px 1px;}
	.td{border:solid #add9c0; border-width:0px 1px 1px 0px;}
</style>

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
sum();
});

function sum(){
	var sum=0.0;
	$(".scinput1").each(function() {
		//alert($(this).val());
		if($(this).val().length>0){
			sum=sum+parseFloat($(this).val());
		}
	});
	$("#sum").text("总分："+sum);
}
</script>

</head>

<body class="sarchbody">

	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="Proportion_listForScore">评分列表</a></li>
     <li><a href="#"><s:property value="kpi.aimUser.name"/> 的指标</a></li>
    </ul>
    </div>
    <div class="rightinfo">
	<div class="tools">
    <ul class="toolbarm">
	    <li><label>季度:</label></li>		<li><s:property value="kpi.quarter.name" /></li>
	    <li><label></label> </li>
	    <li><label>KPI</label> </li>
	    <li><label>被考核人</label> </li>
	    <li><label><s:property value="kpi.aimUser.name" /> </label></li>
	    <li><label></label></li>
	    <li><label>拟表人</label> </li>
	    <li><label> <s:property  value="kpi.fillUser.name"/>  </label></li>
	 </ul>
     <ul class="toolbar1">
       	<li id="sum"></li>
        <li style="background:url(./images/toolbg.gif) repeat-x;border:solid 1px #d3dbde;" onclick="javascript:$('#form').submit()" >
        <span><img src="images/t01.png"/></span>提交</li>
        <li onclick="javascript:window.location.href='KPI_toDownloadScoreKPI.action?user.uid=<s:property value="user.uid"/>'" >
        <span></span>下载</li>
     </ul>
  	
   </div>
   <form id="form" action="ScoreKPI_saveScoreKPIs.action">
    <table class="tablelist" >
    	<thead>
    	<tr>  
	       <th style="width:50px">指标名称<i class="sort"><img src="images/px.gif" /></i></th>
	        <th style="width:50px">权重</th>
	        <th style="width:100px">定义、计算公式</th>
	        <th style="width:50px">目标值</th>
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
	      
	        <td><pre><s:property value="#c.name"  />  	</pre></td>
	        <td><s:property value="#c.weight"  />  </td>
	        <td><pre><s:property value="#c.definde"  />  </pre></td>
	        <td><pre><s:property value="#c.aim"  /> </pre> </td>
	        <td><pre><s:property value="#c.process"  />  </pre></td>
	        <td><pre><s:property value="#c.sourceUser.name"  /> </pre> </td>
	        <td><pre><s:property value="#c.actual"  />  </pre></td>
	        <td>
	        	<input name="map[<s:property value="#c.cid"/>]"  value="<s:property value='kpiContentScore[#c.cid]'/>"  onchange="sum()"
	        	type="text" class="scinput1" onkeyup="this.value=this.value.replace(/[^\-+\d+\.]/g,'')"  />
	        </td>
        </tr> 
        </s:if>
       </s:iterator>
        </tbody>
    </table>
    
    <div class="tools">
    <ul class="toolbarm">
	    <li><label></label></li>		<li><label></label></li>
	    <li><label></label> </li>
	    <li><label>GS</label> </li>

	 </ul>
   </div>
    
        <table class="tablelist" >
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
	        <td><pre><s:property value="#c.name"  />  	</pre></td>
	        <td><s:property value="#c.weight" /> </td>
	        <td><pre><s:property value="#c.definde"  />  </pre></td>
	        <td><pre><s:property value="#c.sourceUser.name"  /> </pre> </td>
	        <td><pre><s:property value="#c.actual"  />  </pre></td>
	        <td>
	        	<input name="map[<s:property value="#c.cid"/>]"  onchange="sum()"  value="<s:property value='kpiContentScore[#c.cid]'/>"
	        	type="text" class="scinput1" onkeyup="this.value=this.value.replace(/[^\-+\d+\.]/g,'')"  />
	        </td>
        </tr> 
        </s:if>
       </s:iterator>
        </tbody>
    </table>
    
   </form>
   
   
   
   
     </div>
</body>

</html>
