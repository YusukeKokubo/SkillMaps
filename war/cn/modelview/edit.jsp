<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/cn/common/common.jsp"%>
<%@page import="jp.crossnote.slim3.util.AppProperties"%>
<c:set var="elmLimit" value="<%= AppProperties.CNS3_VIEWER_ELEMENT_LIMIT %>"/>
<c:set var="datePattern" value="<%= AppProperties.CNS3_DATE_PATTERN %>"/>
<html>
<head>
<%@ include file="/cn/common/header.jsp"%>
<title>CNMV - slim3 model viewer -</title>
<link rel="stylesheet" type="text/css" href="${f:url('/cn/css/cn.css')}" />
<script language="JavaScript">
$(function() {
	$("#pk_input input").click(function(){
	    togglePKInputForm();
	});
    $("input.pk_policy").click(function(){
    	switchPKInputTextClass();
    });
    togglePKInputForm();
    switchPKInputTextClass();
});

function togglePKInputForm() {
    if ($("#pk_input input").attr('checked')) {
        $("#pk_form").show();
    }
    else {
        $("#pk_form").hide();
    }
}

function switchPKInputTextClass() {
    var val =$("input.pk_policy:checked").val();
    if (val == 'stk') {
        $("input#pk_text").removeClass("normal").removeClass("numeric").addClass("key");
    }
    else if (val == 'iok') {
        $("input#pk_text").removeClass("normal").removeClass("key").addClass("numeric");
    }
    else if (val == 'nok') {
        $("input#pk_text").removeClass("numeric").removeClass("key").addClass("normal");
    }
}

</script>
</head>
<body>
<a class="top_title" href="${f:url('index?')}_limit_=${f:h(_limit_)}">CNMV - slim3 model viewer -</a>
<hr />
<c:forEach var="e" items="${f:errors()}">
<div class="err">${f:h(e)}</div><br/>
</c:forEach>
<form method="post" action="regist">
<input type="hidden" ${f:hidden("_key_")} />
<input type="hidden" ${f:hidden("_modelname_")} />
<input type="hidden" ${f:hidden("_page_")} />
<input type="hidden" ${f:hidden("_limit_")} />
<input type="hidden" ${f:hidden("_owner_model_")} />
<input type="hidden" ${f:hidden("_owner_key_")} />
<input type="hidden" ${f:hidden("_owner_param_")} />
<div class="modelName">
Model : ${f:h(_modelname_)}
</div>
<c:if test="${!xf:isEmpty(_modelname_)}">
<table>
  <thead>
    <tr>
      <th>PropertyName (Type)</th><th>Value</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach var="p" items="${_propertyList_}" varStatus="ps" >
      <tr>
        <td>
          ${f:h(p.name)} 
          <c:choose>
          <c:when test="${p.collection || p.relationship}">
            (${f:h(p.type.simpleName)}&lt;${f:h(p.genericType.simpleName)}&gt;)
          </c:when>
          <c:otherwise>
            (${f:h(p.type.simpleName)})
          </c:otherwise>
          </c:choose>
        </td>
        <td>
		  <c:choose>
		  <c:when test="${p.primaryKey}">
			<c:if test="${xf:isEmpty(_key_)}">
			  <div id="pk_input" ><input type="checkbox" ${f:checkbox("_pk_input_")}/>edit primary key</div>
			  <div id="pk_form">
	              <input type="text" name="${p.name}" value="${xf:h(xf:v(p.name))}" id="pk_text" class="normal" />
	              <div>
	                  <input type="radio" ${f:radio("_pk_policy_","stk")} class="pk_policy" /><span title="the return value of KeyFactory.keyToString( key )" style="text-decoration: underline;">encoded string</span>
	                  <input type="radio" ${f:radio("_pk_policy_","iok")} class="pk_policy" />id
	                  <input type="radio" ${f:radio("_pk_policy_","nok")} class="pk_policy" />name
	              </div>
			  </div>
			</c:if>
			<c:if test="${!xf:isEmpty(_key_)}">
			  <input type="hidden" ${f:hidden(p.name)}/>
			  <div title="kind=${xf:key(_key_).kind}, namespace=${xf:key(_key_).namespace}, id=${xf:key(_key_).id}, name=${xf:key(_key_).name}, parent=${xf:key(keyProp).parent}">
			    <div>${xf:key(xf:v(p.name))}</div>
			    <div>${xf:h(_key_)}</div>
			  </div>
			</c:if>
		  </c:when>
          <c:when test="${p.key}">
            <input type="text" name="${p.name}" value="${xf:h(xf:v(p.name))}" class="key" />
            <div><span title="the return value of KeyFactory.keyToString( key )" style="text-decoration: underline;">encoded string</span> can be used.</div>
          </c:when>
          <c:when test="${p.modelRef}">
            Relationship to ${f:h(p.genericType.name)}.
            <div><input type="text" name="${p.name}" value="${xf:h(xf:v(p.name))}" class="key" /></div>
            <div><span title="the return value of KeyFactory.keyToString( key )" style="text-decoration: underline;">encoded string</span> can be used.</div>
          </c:when>
          <c:when test="${p.relationship}">
            Relationship from ${f:h(p.genericType.name)}. <div class="noteditable"> * not editable *</div>
          </c:when>
          <c:when test="${p.boolean}">
		    <input type="radio" ${f:radio(p.name, "true")}/> true
			<input type="radio" ${f:radio(p.name, "false")}/> false
          </c:when>
          <c:when test="${p.enum}">
            <select name="${p.name}">
	          <c:forEach var="element" items="${p.enumElements}">
	            <option ${f:select(p.name, element.value)}>${f:h(element.label)}</option>
	          </c:forEach>
	        </select>
          </c:when>
          <c:when test="${p.date}">
            <input type="text" name="${p.name}" value="${xf:h(xf:v(p.name))}" class="date" /> ex) ${f:h(datePattern)}
          </c:when>
          <c:when test="${p.text || (p.string && p.lob)}">
            <textarea name="${p.name}" class="largetext">${xf:h(xf:v(p.name))}</textarea>
          </c:when>
          <c:when test="${p.number || p.decimal || p.rating}">
            <input type="text" name="${p.name}" value="${xf:h(xf:v(p.name))}" class="numeric"/>
          </c:when>
          <c:when test="${(p.collection || p.array) && (elmLimit < xf:size(p.name))}">
            <div class="toomany">* too many elements *</div>
            <input type="hidden" name="${p.tooManyElementsKey}" value="TRUE"/>
          </c:when>
          <c:when test="${p.collection || p.array}">
            <input type="text" name="${p.name}" value="${xf:h(xf:v(p.name))}" class="array" /> ex) aaa,bbb,ccc
          </c:when>
          <c:when test="${p.user}">
            <table class="struct_prop">
            <tr><td class="struct_prop_label">Email&nbsp;:</td><td><input type="text" name="${p.userEmailKey}" value="${xf:h(xf:v(p.userEmailKey))}" class="normal" /> *</td></tr>
            <tr><td class="struct_prop_label">AuthDomain&nbsp;:</td><td><input type="text" name="${p.userAuthDomainKey}" value="${xf:h(xf:v(p.userAuthDomainKey))}" class="normal" /> *</td></tr>
            <tr><td class="struct_prop_label">UserId&nbsp;:</td><td><input type="text" name="${p.userUserIdKey}" value="${xf:h(xf:v(p.userUserIdKey))}" class="normal" /></td></tr>
            <tr><td class="struct_prop_label">FederatedIdentity&nbsp;:</td><td><input type="text" name="${p.userFederatedIdentityKey}" value="${xf:h(xf:v(p.userFederatedIdentityKey))}" class="normal" /></td></tr>
            <tr><td></td><td>* Both email and authDomain are required.</td></tr>
            </table>
          </c:when>
          <c:when test="${p.geoPt}">
            <table class="struct_prop">
            <tr><td class="struct_prop_label">Latitude&nbsp;:</td><td><input type="text" name="${p.geoPtLatitudeKey}" value="${xf:h(xf:v(p.geoPtLatitudeKey))}" class="numeric" /> *</td></tr>
            <tr><td class="struct_prop_label">Longitude&nbsp;:</td><td><input type="text" name="${p.geoPtLongitudeKey}" value="${xf:h(xf:v(p.geoPtLongitudeKey))}" class="numeric" /> *</td></tr>
            <tr><td></td><td>* Both latitude and longitude are required.</td></tr>
            </table>
          </c:when>
          <c:when test="${!p.editable}">
            ${f:h(xf:toString(xf:v(p.name)))} <span class="noteditable">&nbsp;*&nbsp;not&nbsp;editable&nbsp;*</div>
          </c:when>
		  <c:otherwise>
            <input type="text" name="${p.name}" value="${xf:h(xf:v(p.name))}" class="normal" />
		  </c:otherwise>
		  </c:choose>
        </td>
      </tr>
    </c:forEach>
  </tbody>
</table>
<c:if test="${xf:isEmpty(_key_)}">
  <input type="submit" value="INSERT" />
</c:if>
<c:if test="${!xf:isEmpty(_key_)}">
  <input type="submit" value="UPDATE" />
</c:if>
<input type="reset" value="RESET" />
</c:if>
</form>
<c:set var="backUrl" value="index?_modelname_=${f:h(_modelname_)}&_page_=${f:h(_page_)}&_limit_=${f:h(_limit_)}" />
<a href="${f:url(backUrl)}">back</a>

<%@ include file="/cn/common/footer.jsp"%>
</body>
</html>