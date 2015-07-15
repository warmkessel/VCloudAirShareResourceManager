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
  Account a = null;
  try{
    String idString = request.getParameter("id");
  	if(null != idString && idString.length() > 0){
  		Long inLong = Long.parseLong(idString);
  		a = AccountService.findById(inLong);
  	}
  	if(a == null){
  		a = new Account();
  	}
  	boolean dirty = false;
    String screenName = request.getParameter("screenName");
  	if(null != screenName && screenName.length() > 0){
  		a.setScreen_name(screenName);
  		dirty = true;
  	}
  	String description = request.getParameter("description");
  	if(null != description && description.length() > 0){
  		a.setDescription(description);
  		dirty = true;
  	}
  	String language = request.getParameter("language");
  	if(null != language && language.length() > 0){
  		a.setLanguage(language);
  		dirty = true;
  	}
  	String status = request.getParameter("status");
  	if(null != status && status.length() > 0){
  		a.setStatus(status);
  		dirty = true;
  	}
  	if(dirty){
  		a = AccountService.persist(a);
  	}
  }
  catch (NumberFormatException NFE){
  }
   List<String> theLanguageKeys  = Language.idValues();
   List<Integer> theStatusKeys  = Status.idValues();
   %>
Last Loggedin Date: <%= new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z").format(a.getLastloggedin()) %>
<br>

<form action="account.jsp<% if(null != a.getId()){out.print("?id=" + a.getId().toString());} %>" method="post">
ID: <%= a.getId() %><% if(null != a.getId()){ %><input type="hidden" name="id" value="<%= a.getId() %>"><%}%>
<br>
Screen Name: <input type="text" name="screenName" value="<%= a.getScreen_name() %>"></input>
<br>
Description: <input type="text" name="description" value="<%= a.getDescription() %>"></input>
<br>
Language: 
<select name="language">
<% for(int x=0; x < theLanguageKeys.size(); x++){
String key = theLanguageKeys.get(x);%>
  <option value="<%= key %>"<%= (key.equals( a.getLanguage())) ? " selected " : "" %>><%= Language.fromId(key) %></option>
<%}%>
</select>
<br>
Status: 
<select name="status">
<% for(int x=0; x < theStatusKeys.size(); x++){
Integer key = theStatusKeys.get(x);%>
  <option value="<%= key %>"<%= (key.equals( a.getStatus())) ? " selected " : "" %>><%= Status.fromId(key) %></option>
<%}%>
</select>
<br>

<input type="submit" name="Save"></input>
</form>