<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="dto.Post"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="sessionChk.jsp"%>
<%
	String userid = (String)session.getAttribute("sessionId"); 
%>
<!-- aside 부분 / *팔로우 추천, 광고등 -->
<div class="blog_aside">
	<div class="page_margin"></div>
	<div class="aside_follow">
		<h3>My Menu</h3>
		<hr>
		Profiles<br>
		Photos<br>
		Friend Lists<br>
		Settings<br>
	</div>
</div>