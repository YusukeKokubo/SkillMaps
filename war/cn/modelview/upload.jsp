<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/cn/common/common.jsp"%>
<%@page import="jp.crossnote.slim3.util.AppProperties"%>
<html>
<head>
<%@ include file="/cn/common/header.jsp"%>
<title>CNMV - slim3 model viewer -</title>
<link rel="stylesheet" type="text/css" href="${f:url('/cn/css/cn.css')}" />
</head>
<body>
<a class="nolink_view" href="${f:url('index?')}_limit_=${f:h(_limit_)}">CNMV - slim3 model viewer -</a>
<hr />
<div class="modelName">
Model : ${f:h(_modelname_)}
</div>
<c:if test="${!xf:isEmpty(_propertyList_)}">
<p>the properties on ${f:h(_modelname_)}.</p>
<table class="upload">
  <thead>
    <tr>
      <th>Name</th>
      <th>:</th>
      <c:forEach var="p" items="${_propertyList_}" varStatus="ps" >
        <c:if test="${!p.unsupported}">
          <th>${f:h(p.name)}</th>
        </c:if>
      </c:forEach>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>Type</td>
      <td>:</td>
      <c:forEach var="p" items="${_propertyList_}" varStatus="ps" >
        <c:choose>
          <c:when test="${p.unsupported}">
          </c:when>
          <c:when test="${p.modelRef}">
            <td>${f:h(p.type.simpleName)}&lt;${f:h(p.genericType.simpleName)}&gt;
              <div style="text-align: center;">(<span title="the value of 'KeyFactory.keyToString( key )' can be used." style="text-decoration: underline;">Key</span>)</div>
            </td>
          </c:when>
          <c:when test="${p.key}">
            <td><span title="the value of 'KeyFactory.keyToString( key )' can be used." style="text-decoration: underline;">${f:h(p.type.simpleName)}</span></td>
          </c:when>
          <c:when test="${p.collection}">
            <td>${f:h(p.type.simpleName)}&lt;${f:h(p.genericType.simpleName)}&gt;</td>
          </c:when>
          <c:when test="${p.blob}">
            <td><span title="the value of 'byte[]'. can not editable." style="text-decoration: underline;">${f:h(p.type.simpleName)}</span></td>
          </c:when>
          <c:when test="${p.serializable}">
            <td><div class="upload_noteditable">${f:h(p.type.simpleName)}</div>
              <div style="text-align: center;">(<span title="the value of 'byte[]'. can not editable." style="text-decoration: underline;">Blob</span>)</div>
            </td>
          </c:when>
          <c:otherwise>
            <td>${f:h(p.type.simpleName)}</td>
          </c:otherwise>
        </c:choose>
      </c:forEach>
    </tr>
  </tbody>
</table>
<c:forEach var="e" items="${f:errors()}">
<p class="err">${f:h(e)}</p>
</c:forEach>
<form method="post" action="upload" enctype="multipart/form-data">
<input type="hidden" ${f:hidden("up")}/>
<input type="hidden" ${f:hidden("_modelname_")}/>
<input type="hidden" ${f:hidden("_page_")} />
<input type="hidden" ${f:hidden("_limit_")} />
<p>Choose policy of uploading.</p>
 <input type="radio" ${f:radio("policy", "C")}/> Insert only. (If the key overlaps, it throws error.)<br/> 
 <input type="radio" ${f:radio("policy", "CS")}/> Insert or Skip. (the overlaps key is skipped.)<br/> 
 <input type="radio" ${f:radio("policy", "CU")}/> Insert or Update.<br/>
 <br/>
<p>Select upload file.</p>
<input type="file" name="csvFile"/><br/>
<input type="submit" value="UPLOAD" />
</form>
</c:if>
<br/>
<c:set var="backUrl" value="index?_modelname_=${f:h(_modelname_)}&_page_=${f:h(_page_)}&_limit_=${f:h(_limit_)}" />
<a href="${f:url(backUrl)}">back</a>

<%@ include file="/cn/common/footer.jsp"%>
</body>
</html>