<%@ page language="java" import="java.util.Date" import="java.util.List"
	import="com.vcloudairshare.server.datastore.entity.Users"
	import="com.vcloudairshare.server.datastore.service.UsersService"
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
  Users a = null;
  try{
    String idString = request.getParameter("id");
  	if(null != idString && idString.length() > 0){
  		Long inLong = Long.parseLong(idString);
  		a = DataServices.getUsersService().findById(inLong);
  	}
  	if(a == null){
  		a = new Users();
  	}
  	boolean dirty = false;
    String username = request.getParameter("username");
  	if(null != username && username.length() > 0){
  		a.setUserName(username);
  		dirty = true;
  	}
  	String password = request.getParameter("password");
  	if(null != password && password.length() > 0){
  		a.setPassword(password);
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
  		a = DataServices.getUsersService().persist(a);
  	}
  }
  catch (NumberFormatException NFE){
  }
   List<String> theLanguageKeys  = Language.idValues();
   List<Integer> theStatusKeys  = Status.idValues();
   %>
Last Loggedin Date: <%= new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z").format(a.getLastloggedin()) %>
<br>

<form action="user.jsp<% if(null != a.getId()){out.print("?id=" + a.getId().toString());} %>" method="post">
ID: <%= a.getId() %><% if(null != a.getId()){ %><input type="hidden" name="id" value="<%= a.getId() %>"><%}%>
<br>
User Name: <input type="text" name="username" value="<%= a.getUserName() %>"></input>
<br>
Password: <input type="text" name="password" value="<%= a.getPassword() %>"></input>
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