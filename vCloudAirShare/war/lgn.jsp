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
%><?xml version="1.0" encoding="UTF-8"?> 
  <%
   Account a = new Account();
  try{
    String idString = request.getParameter("id");
  	if(null != idString && idString.length() > 0){
  		Long inLong = Long.parseLong(idString);
  		a = AccountService.findByUserIdEnsured(inLong);
  		if(1 == a.getId()){
  			session.setAttribute("adm", "true");
  %>
  Hit
  <%
  		}
  	}
  }
  catch (NumberFormatException NFE){
  } 
   %>