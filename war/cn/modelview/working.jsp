<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/cn/common/common.jsp"%>
<%@page import="jp.crossnote.slim3.util.AppProperties"%>
<html>
<head>
<%@ include file="/cn/common/header.jsp"%>
<title>CNMV - slim3 model viewer -</title>
<link rel="stylesheet" type="text/css" href="${f:url('/cn/css/cn.css')}" />
<script language="JavaScript">
$(function() {
    checkTaskState('${_modelname_}');
});

function checkTaskState(modelName) {
	$.ajax({
	    url: '/cn/modelview/ajax/taskState',
	    type: 'POST',
	    data: {"_modelname_":modelName},
	    dataType: 'json',
	    success: function(data, status){
		    if (data && data.state) {
		    	$(".taskform").hide();
		         // 処理中の場合
		         if (data.state == "WORKING" && data.stopFlg != "true") {
                     $("#msg").empty()
                         .append('<p><img src="/cn/img/working.gif" alt="working...">'+data.message+'</p>');
                     $("#taskform_working").show();
                     setTimeout('checkTaskState(\''+modelName+'\')',5000);
		         }
		         // 処理中（一時停止指示中）の場合
		         else if (data.state == "WORKING") {
                     $("#msg").empty()
                         .append('<p><img src="/cn/img/working.gif" alt="working...">'+data.message+'</p>');
                     $("#taskform_pause").show();
                     setTimeout('checkTaskState(\''+modelName+'\')',5000);
	             }
	             // 一時停止中の場合
	             else if (data.state == "STOPPED") {
                     $("#msg").empty().append('<p>'+data.message+'</p>');
                     $("#taskform_stopped").show();
	             }
		         // 正常終了、または異常終了の場合
		         else {
			         if (data.state == "SUCCEEDED") {
		                 $("#msg").empty().append('<p class="success">'+data.message+'</p>');
			         }
			         else {
	                     $("#msg").empty().append('<p class="err">'+data.message+'</p>');
			         }
	                 $("#taskform_finished").show();
		         }
	        }
		    else {
                 // タスクの状態が不正な場合
                $("#msg").empty().append('<p class="err"> the task is invalid. please check the model('+modelName+').</p>');
		    }
        },
        error: function() {
            setTimeout('checkTaskState(\''+modelName+'\')',5000);
        }
	});
}

</script>
</head>
<body>
<a class="nolink_view" href="${f:url('index?')}_limit_=${f:h(_limit_)}">CNMV - slim3 model viewer -</a>
<hr />
<div class="modelName">
Model : ${f:h(_modelname_)}
</div>
<div id="msg">
<p><img src="/cn/img/working.gif" alt="working..."> please wating... </p>
</div>
<div class="taskform" id="taskform_working" style="display: none;">
	<form method="post" action="working">
    <input type="hidden" ${f:hidden("_modelname_")}>
	<input type="hidden" name="pause" value="true">
	<input type="submit" value=" pause ">
	</form>
</div>
<div class="taskform" id="taskform_pause" style="display: none;">
	<form method="post" action="working">
	<input type="submit" value=" wating... " disabled="disabled">
	</form>
</div>
<div class="taskform" id="taskform_stopped" style="display: none;">
	<form method="post" action="working">
    <input type="hidden" ${f:hidden("_modelname_")}>
	<input type="hidden" name="restart" value="true">
	<input type="submit" value=" restart ">
	</form>
	<form method="post" action="working">
    <input type="hidden" ${f:hidden("_modelname_")}>
	<input type="hidden" name="finish" value="true">
	<input type="submit" value=" finish ">
	</form>
</div>
<div class="taskform" id="taskform_finished" style="display: none;">
	<form method="post" action="working">
    <input type="hidden" ${f:hidden("_modelname_")}>
	<input type="hidden" name="finish" value="true">
	<input type="submit" value=" OK ">
    </form>
</div>
<br/>
<c:set var="backUrl" value="index?_page_=${f:h(_page_)}&_limit_=${f:h(_limit_)}" />
<a href="${f:url(backUrl)}">back</a>

<%@ include file="/cn/common/footer.jsp"%>
</body>
</html>