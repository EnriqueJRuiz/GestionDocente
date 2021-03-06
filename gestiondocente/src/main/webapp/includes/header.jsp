<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.ipartek.formacion.controller.Constantes"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <c:set var="language" value="${not empty sessionScope.language ? sessionScope.language : 'es_ES'}" scope="page" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="com.ipartek.formacion.controller.i18nmessages"/>
<!DOCTYPE html>
 <html lang="${language}">
 	<head>
 		<meta charset="UTF-8">
 		<title>Gestor Dorcente </title>
 		<link href='<c:url value="/css/styles.css" />' type="text/css" rel="stylesheet" />
 		
 		<script src='<c:url value="/js/codigo.js" />' ></script>		
 	</head>
	<body>
		<header>
			<h1><a href="<c:url value="index.jsp" />"><fmt:message key="heard.title" /></a></h1>
			<jsp:include page="../includes/navBar.jsp" />
			<!--  
			<div class=""></div>
				<a href="<%=Constantes.SERVLET_IDIOMA%>?<%=Constantes.PAR_IDIOMA%>=<%=Constantes.IDIOMA_ES%>">Catellano</a>
				<a href="<%=Constantes.SERVLET_IDIOMA%>?<%=Constantes.PAR_IDIOMA%>=<%=Constantes.IDIOMA_EU%>">Euskera</a>
				<a href="<%=Constantes.SERVLET_IDIOMA%>?<%=Constantes.PAR_IDIOMA%>=<%=Constantes.IDIOMA_EN%>">Ingles</a>
			</div>
			-->
			
			 </header>
			 
			 
			