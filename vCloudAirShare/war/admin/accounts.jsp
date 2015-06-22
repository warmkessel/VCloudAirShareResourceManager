<%@ page language="java" import="java.util.Date" import="java.util.List"
	import="com.vcloudairshare.server.datastore.entity.Account"
	import="com.vcloudairshare.server.datastore.service.AccountService"
	import="com.vcloudairshare.server.datastore.service.DataServices"
	import="java.text.SimpleDateFormat"
	import="java.lang.StringBuffer"
	import="com.vcloudairshare.shared.enumeration.Status"
	import="com.vcloudairshare.shared.enumeration.Language"
	errorPage="/error.jsp"
	pageEncoding="UTF-8"%>
	<%	
	response.setHeader("Cache-Control","max-age=0");
	response.setHeader("Content-type", "text/html; charset=UTF-8");	
%><?xml version="1.0" encoding="UTF-8"?>  <%
	    String startString = request.getParameter("start");
	    String limitString = request.getParameter("limit");

		int start = 0;
		int limit = 100;
		if(null != startString && startString.length() > 0){
			start = Integer.parseInt(startString);
		}
		if(null != limitString && limitString.length() > 0){
			limit = Integer.parseInt(limitString);
		}
		List<Account> usr =  DataServices.getAccountService().findRange(start, limit);
		
   %>
   	<table style="border: black;border-width: 2px;border-style: solid;">

   <%
   		for(int x=0; x < usr.size(); x++){
%>
	<tr style="border: black;border-width: 1px;border-style: solid;">
	<td>
	<a href="account.jsp?id=<%= usr.get(x).getId()%>"><%= usr.get(x).getId()%></a>
	</td><td>
	<%= usr.get(x).getUsername()%>
	</td>
	<td>
	<%= Status.fromId(usr.get(x).getStatus()).toString()%>
	</td>
	</tr>
<%}%>
	</table>
Size: <%= usr.size()%><a href="account.jsp">New Account</a>