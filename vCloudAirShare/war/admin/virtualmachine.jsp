<%@ page language="java" import="java.util.Date" import="java.util.List"
	import="com.vcloudairshare.server.datastore.entity.VirtualMachine"
	import="com.vcloudairshare.server.datastore.service.VirtualMachineService"
	import="com.vcloudairshare.server.datastore.service.DataServices"
	import="java.text.SimpleDateFormat"
	import="java.lang.StringBuffer"
	import="com.vcloudairshare.shared.enumeration.Status"
	import="com.vcloudairshare.shared.enumeration.Language"
	import="com.vcloudairshare.shared.enumeration.MachineType"
	errorPage="/error.jsp"
	pageEncoding="UTF-8"%>
	<%	
	response.setHeader("Cache-Control","max-age=0");
	response.setHeader("Content-type", "text/html; charset=UTF-8");	
%><?xml version="1.0" encoding="UTF-8"?> 
  <%
  VirtualMachine a = null;
  try{
    String idString = request.getParameter("id");
  	if(null != idString && idString.length() > 0){
  		Long inLong = Long.parseLong(idString);
  		a = DataServices.getVirtualMachineService().findById(inLong);
  	}
  	if(a == null){
  		a = new VirtualMachine();
  	}
  	boolean dirty = false;
    String machinename = request.getParameter("machinename");
  	if(null != machinename && machinename.length() > 0){
  		a.setMachinename(machinename);
  		dirty = true;
  	}
  	String ipaddress = request.getParameter("ipaddress");
  	if(null != ipaddress && ipaddress.length() > 0){
  		a.setIpaddress(ipaddress);
  		dirty = true;
  	}
  	String currentUser = request.getParameter("currentUser");
  	if(null != currentUser && currentUser.length() > 0){
  		a.setCurrentUser(Long.parseLong(currentUser));
  		dirty = true;
  	}
  	
    	String machinetype = request.getParameter("machinetype");
  	if(null != machinetype && machinetype.length() > 0){
  		a.setMachinetype(Integer.parseInt(machinetype));
  		dirty = true;
  	}
  	String condition = request.getParameter("condition");
  	if(null != condition && condition.length() > 0){
  		a.setCondition(Integer.parseInt(condition));
  		dirty = true;
  	}
  	String status = request.getParameter("status");
  	if(null != status && status.length() > 0){
  		a.setStatus(status);
  		dirty = true;
  	}
  	if(dirty){
  		a = DataServices.getVirtualMachineService().persist(a);
  	}
  }
  catch (NumberFormatException NFE){
  }
   List<String> theLanguageKeys  = Language.idValues();
   List<Integer> theStatusKeys  = Status.idValues();
   List<Integer> theMachineKeys  = MachineType.idValues();
   %>
<br>

<form action="virtualmachine.jsp<% if(null != a.getId()){out.print("?id=" + a.getId().toString());} %>" method="post">
ID: <%= a.getId() %><% if(null != a.getId()){ %><input type="hidden" name="id" value="<%= a.getId() %>"><%}%>
<br>
Machine Name: <input type="text" name="machinename" value="<%= a.getMachinename() %>"></input>
<br>
IP Address: <input type="text" name="ipaddress" value="<%= a.getIpaddress() %>"></input>
<br>
Current User: <input type="text" name="currentUser" value="<%= a.getCurrentUser() %>"></input>
<br>
Machine Type: 
<select name="machinetype">
<% for(int x=0; x < theMachineKeys.size(); x++){
Integer key = theMachineKeys.get(x);%>
  <option value="<%= key %>"<%= (key.equals( a.getMachinetype())) ? " selected " : "" %>><%= MachineType.fromId(key) %></option>
<%}%>
</select>
<br>
Condition: 
<select name="condition">
<% for(int x=0; x < theStatusKeys.size(); x++){
Integer key = theStatusKeys.get(x);%>
  <option value="<%= key %>"<%= (key.equals( a.getCondition())) ? " selected " : "" %>><%= Status.fromId(key) %></option>
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