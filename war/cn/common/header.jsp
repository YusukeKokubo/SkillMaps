<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="java.util.*" %>
<%
Calendar objCal1=Calendar.getInstance();
Calendar objCal2=Calendar.getInstance();
objCal2.set(2000,0,1,0,0,0);
response.setDateHeader("Last-Modified",objCal1.getTime().getTime());
response.setDateHeader("Expires",objCal2.getTime().getTime());
response.setHeader("pragma","no-cache");
response.setHeader("Cache-Control","no-cache");
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="Sat, 01 Jan 2000 00:00:00 GMT">
<meta name="description" content="slim3 model viewer on Google app engine." />
<meta name="keywords" content="cnmv,gae,appengine,java,slim3,crossnote,kilvistyle" />
<script type="text/javascript" src="/cn/js/jquery-1.4.2.min.js"></script>