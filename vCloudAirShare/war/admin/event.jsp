<%@ page language="java" import="java.util.Date" import="java.util.List"
	import="com.vcloudairshare.shared.util.DateUtil"
	import="com.vcloudairshare.server.datastore.entity.Event"
	import="com.vcloudairshare.server.datastore.service.EventService"
	import="com.vcloudairshare.server.datastore.service.DataServices"
	import="java.text.SimpleDateFormat"
	import="java.lang.StringBuffer"
	import="com.vcloudairshare.shared.enumeration.Status"
	import="com.vcloudairshare.shared.enumeration.EventType"
	import="com.vcloudairshare.shared.util.DateUtil"
	errorPage="/error.jsp"
	pageEncoding="UTF-8"%>
	<%	
	response.setHeader("Cache-Control","max-age=0");
	response.setHeader("Content-type", "text/html; charset=UTF-8");	
%><?xml version="1.0" encoding="UTF-8"?> 
  <%
  Event a = null;
  try{
    String idString = request.getParameter("id");
  	if(null != idString && idString.length() > 0){
  		Long inLong = Long.parseLong(idString);
  		a = DataServices.getEventService().findById(inLong);
  	}
  	if(a == null){
  		a = new Event();
  	}
  	boolean dirty = false;
    String eventUser = request.getParameter("eventUser");
  	if(null != eventUser && eventUser.length() > 0){
  		a.setEventUser(Long.parseLong(eventUser));
  		dirty = true;
  	}
  
  	String eventDate = request.getParameter("eventDate");
  	if(null != eventDate && eventDate.length() > 0){
  	  	a.setEventDate(eventDate);
  		dirty = true;
  	}
  	String eventType = request.getParameter("eventType");
  	if(null != eventType && eventType.length() > 0){
  		a.setEventType(eventType);
  		dirty = true;
  	}
  	
  	String status = request.getParameter("status");
  	if(null != status && status.length() > 0){
  		a.setStatus(status);
  		dirty = true;
  	}
  	if(dirty){
  		a = DataServices.getEventService().persist(a);
  	}
  }
  catch (NumberFormatException NFE){
  }
   List<Integer> theEventTypeKeys  = EventType.idValues();
   List<Integer> theStatusKeys  = Status.idValues();
   %>
<br>

<form action="event.jsp<% if(null != a.getId()){out.print("?id=" + a.getId().toString());} %>" method="post">
ID: <%= a.getId() %><% if(null != a.getId()){ %><input type="hidden" name="id" value="<%= a.getId() %>"><%}%>
<br>
Status: 
<select name="status">
<% for(int x=0; x < theStatusKeys.size(); x++){
Integer key = theStatusKeys.get(x);%>
  <option value="<%= key %>"<%= (key.equals( a.getStatus())) ? " selected " : "" %>><%= Status.fromId(key) %></option>
<%}%>
</select>
<br>
Event User <input type="text" name="eventUser" value="<%=a.getEventUser()%>"></input>

<br>
Event Date <input type="text" name="eventDate" value="<%= new SimpleDateFormat("dd MMM yyyy HH:mm:ss Z").format(a.getEventDate()) %>"></input>
<br>
EventType: 
<select name="eventType">
<% for(int x=0; x < theEventTypeKeys.size(); x++){
Integer key = theEventTypeKeys.get(x);%>
  <option value="<%= key %>"<%= (key.equals( a.getEventType())) ? " selected " : "" %>><%= EventType.fromId(key) %></option>
<%}%>
</select>
<br>
Linked Event <input type="text" name="linkedEvent" value="<%=a.getLinkedEvent()%>"></input>

<input type="submit" name="Save"></input>
</form>