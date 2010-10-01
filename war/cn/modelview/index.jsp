<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/cn/common/common.jsp"%>
<%@page import="jp.crossnote.slim3.util.AppProperties"%>
<c:set var="elmLimit" value="<%= AppProperties.CNS3_VIEWER_ELEMENT_LIMIT %>"/>
<c:set var="firstPageUrl" value="index?_modelname_=${f:h(_modelname_)}&_page_=${f:h(first)}&_limit_=${f:h(_limit_)}" />
<c:set var="backPageUrl" value="index?_modelname_=${f:h(_modelname_)}&_page_=${f:h(back)}&_limit_=${f:h(_limit_)}" />
<c:set var="nextPageUrl" value="index?_modelname_=${f:h(_modelname_)}&_page_=${f:h(next)}&_limit_=${f:h(_limit_)}" />
<c:set var="lastPageUrl" value="index?_modelname_=${f:h(_modelname_)}&_page_=${f:h(last)}&_limit_=${f:h(_limit_)}" />
<c:set var="currentPageQuery" value="_modelname_=${f:h(_modelname_)}&_page_=${f:h(_page_)}&_limit_=${f:h(_limit_)}" />
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
<form method="post" action="index">
<input type="hidden" ${f:hidden("_limit_")} />
<div class="modelName">
Model : <input type="text" ${f:text("_modelname_")} class="model"/>
<input type="submit" value="OK" />
</div>
</form>
<c:if test="${xf:isEmpty(_propertyList_) && !xf:isEmpty(viewHistoryList)}">
<p class="history_title">View history</p>
  <c:forEach var="vh" items="${viewHistoryList}" varStatus="s" >
    <c:set var="historyUrl" value="index?_modelname_=${f:h(vh.modelClass.name)}&_limit_=${_limit_}"/>
    <li class="history"><a href="${f:url(historyUrl)}">${f:h(vh.modelClass.name)}</a></li>
  </c:forEach>
</c:if>
<c:if test="${isDev && xf:isEmpty(_propertyList_)}">
    <p><a href="${f:url('/_ah/admin/datastore')}">&gt;&gt; view on appengine dev console.</a></p>
</c:if>
<c:if test="${!xf:isEmpty(_propertyList_)}">
<div class="paging_top">
<c:if test="${backPage}">
  <a id="paginglink" href="${f:url(firstPageUrl)}">${f:h("|<")}</a>&nbsp;
  <a id="paginglink" href="${f:url(backPageUrl)}">${f:h("< back")}</a>&nbsp;
</c:if>
<c:if test="${nextPage}">
  <a id="paginglink" href="${f:url(nextPageUrl)}">${f:h("next >")}</a>&nbsp;
  <a id="paginglink" href="${f:url(lastPageUrl)}">${f:h(">|")}</a>
</c:if>
</div>
<table>
  <thead>
    <tr>
      <c:forEach var="p" items="${_propertyList_}" varStatus="ps" >
        <c:choose>
        <c:when test="${p.primaryKey}">
          <th colspan="2" class="pkey_head">${f:h(p.name)}</th>
        </c:when>
        <c:when test="${p.version}">
          <th class="version_head">${f:h(p.name)}</th>
        </c:when>
        <c:when test="${p.relationship}">
          <th class="modelref_head">${f:h(p.name)}</th>
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
            <c:set var="editUrl" value="edit?_key_=${f:key(xf:vi(ms.index, p.name))}&${f:h(currentPageQuery)}"/>
            <c:set var="delUrl" value="delete?_key_=${f:key(xf:vi(ms.index, p.name))}&${f:h(currentPageQuery)}"/>
            <c:set var="refUrl" value="refView?_key_=${f:key(xf:vi(ms.index, p.name))}&${f:h(currentPageQuery)}"/>
            <c:set var="keyProp" value="${xf:vi(ms.index, p.name)}"/>
            <td><div title="kind=${xf:key(keyProp).kind}, namespace=${xf:key(keyProp).namespace}, id=${xf:key(keyProp).id}, name=${xf:key(keyProp).name}, parent=${xf:key(keyProp).parent}">${xf:key(keyProp)}</div></td>
            <td><div title="kind=${xf:key(keyProp).kind}, namespace=${xf:key(keyProp).namespace}, id=${xf:key(keyProp).id}, name=${xf:key(keyProp).name}, parent=${xf:key(keyProp).parent}">${xf:h(xf:vi(ms.index, p.name))}</div></td>
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
          <c:when test="${p.user}">
            <c:set var="user" value="${xf:vi(ms.index, p.name)}"/>
            <c:choose>
              <c:when test="${xf:isEmpty(user)}">
                <td></td>
              </c:when>
              <c:otherwise>
                <td>
                <div>email&nbsp;:&nbsp;${xf:h(user.email)}</div>
                <div>authDomain&nbsp;:&nbsp;${xf:h(user.authDomain)}</div>
                <div>userId&nbsp;:&nbsp;${xf:h(user.userId)}</div>
                <div>federatedIdentity&nbsp;:&nbsp;${xf:h(user.federatedIdentity)}</div>
                </td>
              </c:otherwise>
            </c:choose>
          </c:when>
          <c:when test="${p.geoPt}">
            <c:set var="geoPt" value="${xf:vi(ms.index, p.name)}"/>
            <c:choose>
              <c:when test="${xf:isEmpty(geoPt)}">
                <td></td>
              </c:when>
              <c:otherwise>
                <td>
                <div>latitude&nbsp;:&nbsp;${xf:h(geoPt.latitude)}</div>
                <div>longitude&nbsp;:&nbsp;${xf:h(geoPt.longitude)}</div>
                </td>
              </c:otherwise>
            </c:choose>
          </c:when>
          <c:when test="${p.string || p.text}">
            <td>${f:br(xf:h(xf:vi(ms.index, p.name)))}</td>
          </c:when>
          <c:when test="${p.viewable}">
            <td>${xf:h(xf:vi(ms.index, p.name))}</td>
          </c:when>
          <c:otherwise>
            <td>${f:h(xf:toString(xf:vi(ms.index, p.name)))}</td>
          </c:otherwise>
        </c:choose>
      </c:forEach>
      <td>
        <a href="${f:url(editUrl)}">edit</a>
        <a href="${f:url(delUrl)}" onclick="return confirm('delete OK?')">delete</a>
      </td>
      </tr>
    </c:forEach>
  </tbody>
</table>
<div class="paging_bottom">
<c:if test="${backPage}">
  <a id="paginglink" href="${f:url(firstPageUrl)}">${f:h("|<")}</a>&nbsp;
  <a id="paginglink" href="${f:url(backPageUrl)}">${f:h("< back")}</a>&nbsp;
</c:if>
<c:if test="${nextPage}">
  <a id="paginglink" href="${f:url(nextPageUrl)}">${f:h("next >")}</a>&nbsp;
  <a id="paginglink" href="${f:url(lastPageUrl)}">${f:h(">|")}</a>
</c:if>
</div>

<a href="${f:url('edit?')}${f:h(currentPageQuery)}">insert</a>&nbsp;
<a href="${f:url('download?')}${f:h(currentPageQuery)}" onclick="return confirm('download OK?')">download</a>&nbsp;
<a href="${f:url('upload?')}${f:h(currentPageQuery)}">upload</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="${f:url('deleteAll?')}${f:h(currentPageQuery)}&dlimit=" onclick="return confirm('delete all OK?')">delete all entities</a>
</c:if>

<%@ include file="/cn/common/footer.jsp"%>
</body>
</html>