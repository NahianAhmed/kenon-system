<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<%@ include file="partials/head.jsp" %>

<body class="container" >

<%--<%@ include file="partials/navbar.jsp" %>--%>
<jsp:include page="${nav}" />
<%-- this will be injected--%>
<jsp:include page="${content}" />
<%-- this will be injected--%>
<%@ include file="partials/footer.jsp" %>

</body>
</html>