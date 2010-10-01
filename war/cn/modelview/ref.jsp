<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/cn/common/common.jsp"%>
<%@page import="jp.crossnote.slim3.util.AppProperties"%>
<c:set var="elmLimit" value="<%= AppProperties.CNS3_VIEWER_ELEMENT_LIMIT %>"/>
<c:set var="currentPageQuery" value="_modelname_=${f:h(_refModelName_)}&_page_=${f:h(_page_)}&_limit_=${f:h(_limit_)}" />
<html>
<head>
<%@ include file="/cn/common/header.jsp"%>
<title>CNMV - slim3 model viewer -</title>
<link rel="stylesheet" type="text/css" href="${f:url('/cn/css/cn.css')}" />
</head>
<body>
<a class="top_title" href="${f:url('index?')}_limit_=${f:h(_limit_)}">CNMV - slim3 model viewer -</a>
<hr />
<c:forEach var="e" items="${f:errors()}">
<p>${f:h(e)}</p>
</c:forEach>
<c:forEach var="m" items="${xf:msgs()}">
<p class="success">${f:h(m)}</p>
</c:forEach>
<div class="modelName">
Relationship : ${f:h(_refModelName_)} on ${_key_}.
</div>
<c:if test="${!xf:isEmpty(_propertyList_)}">
<table>
  <thead class="ref_head">
    <tr>
      <c:forEach var="p" items="${_propertyList_}" varStatus="ps" >
        <c:choose>
        <c:when test="${p.primaryKey}">
          <th class="pkey_ref_head">${f:h(p.name)}</th>
          <th class="pkey_ref_head">ID/NAME</th>
        </c:when>
        <c:when test="${p.version}">
          <th class="version_ref_head">${f:h(p.name)}</th>
        </c:when>
        <c:when test="${p.relationship}">
          <th class="modelref_ref_head">${f:h(p.name)}</th>
        </c:when>
        <c:otherwise>
          <th>${f:h(p.name)}</th>
        </c:otherwise>
        </c:choose>
      </c:forEach>
      <td></td>
    </tr>
  </thead>
  <tbody>
    <c:forEach var="m" items="${modelList}" varStatus="ms" >
      <tr>
      <c:forEach var="p" items="${_propertyList_}" varStatus="ps" >
        <c:choose>
          <c:when test="${p.primaryKey}">
            <td>${xf:h(xf:vi(ms.index, p.name))}</td>
            <c:set var="editUrl" value="edit?_key_=${f:key(xf:vi(ms.index, p.name))}&${f:h(currentPageQuery)}&_owner_model_=${f:h(_modelname_)}&_owner_key_=${f:h(_key_)}&_owner_param_=${f:h(_ref_)}"/>
            <c:set var="refUrl" value="refView?_key_=${f:key(xf:vi(ms.index, p.name))}&${f:h(currentPageQuery)}"/>
            <c:set var="keyProp" value="${xf:vi(ms.index, p.name)}"/>
            <td>${f:h(keyProp.id)}/${f:h(keyProp.name)}</td>
          </c:when>
          <c:when test="${p.modelRef}">
            <td><a href="${f:url(refUrl)}&_ref_=${f:h(p.name)}">Relationship to ${f:h(p.genericType.simpleName)}(${xf:vi(ms.index, p.name).key}).</a></td>
          </c:when>
          <c:when test="${p.relationship}">
            <td><a href="${f:url(refUrl)}&_ref_=${f:h(p.name)}">Relationship from ${f:h(p.genericType.simpleName)}.</a></td>
          </c:when>
          <c:when test="${(p.array || p.collection) && (elmLimit < xf:sizei(ms.index, p.name))}">
            <td><div class="toomany">* too many elements *</div></td>
          </c:when>
          <c:otherwise>
            <td>${xf:h(xf:vi(ms.index, p.name))}</td>
          </c:otherwise>
        </c:choose>
      </c:forEach>
      <td>
        <a href="${f:url(editUrl)}">edit</a>
      </td>
      </tr>
    </c:forEach>
  </tbody>
</table>
</c:if>
<c:set var="backUrl" value="index?_modelname_=${f:h(_modelname_)}&_page_=${f:h(_page_)}&_limit_=${f:h(_limit_)}" />
<a href="${f:url(backUrl)}">back</a>

<%@ include file="/cn/common/footer.jsp"%>
</body>
</html>