<%@ page language="java" import="java.util.Date" import="java.util.List"
	import="com.vcloudairshare.server.datastore.entity.VirtualMachine"
	import="com.vcloudairshare.server.datastore.service.VirtualMachineService"
	import="com.vcloudairshare.server.datastore.service.DataServices"
	import="com.vcloudairshare.shared.enumeration.VirtualMachineType"
	import="java.text.SimpleDateFormat" import="java.lang.StringBuffer"
	import="com.vcloudairshare.shared.enumeration.Status"
	import="com.vcloudairshare.shared.enumeration.Language"
	errorPage="/error.jsp" pageEncoding="UTF-8"%>
<%
	response.setHeader("Cache-Control", "max-age=0");
	response.setHeader("Content-type", "text/html; charset=UTF-8");
%><?xml version="1.0" encoding="UTF-8"?>
<%
	String startString = request.getParameter("start");
	String limitString = request.getParameter("limit");
	String newMachineString = request.getParameter("newMachine");
	String natString = request.getParameter("nat");
	String countString = request.getParameter("count");

	int start = 0;
	int limit = 100;
	int count = 1;
	boolean created = false;
	if (null != startString && startString.length() > 0) {
		start = Integer.parseInt(startString);
	}
	if (null != limitString && limitString.length() > 0) {
		limit = Integer.parseInt(limitString);
	}
	List<VirtualMachine> usr = DataServices.getVirtualMachineService()
			.findRange(start, limit);

	if (null != newMachineString && newMachineString.length() > 0) {
		VirtualMachineType type = VirtualMachineType.fromId(Integer
				.parseInt(newMachineString));
		if (null != countString && countString.length() > 0) {
			count = Integer.parseInt(countString);

		}

		DataServices.getVirtualMachineService().createMachine(type);
	}
	if (Boolean.parseBoolean(natString)) {
		created = DataServices.getVirtualMachineService().updateNAT();
	}
	if (created) {
%><h1>New Machine Created</h1>
<%
	}
%>

<table style="border: black; border-width: 2px; border-style: solid;">

	<%
		for (int x = 0; x < usr.size(); x++) {
	%>
	<tr style="border: black; border-width: 1px; border-style: solid;">
		<td><a href="virtualmachine.jsp?id=<%=usr.get(x).getId()%>"><%=usr.get(x).getId()%></a>
		</td>
		<td><%=usr.get(x).getMachinename()%></td>
		<td><%=usr.get(x).getPublicIpAddress()%></td>
		<td><%=usr.get(x).getPrivateIpAddress()%></td>
		<td><%if(usr.get(x).getCurrentUser() > 0){ %>
		<a href="account.jsp?id=<%=usr.get(x).getCurrentUser()%>"><%=usr.get(x).getCurrentUserName()%></a>
		<%}%></td>
		<td><%=Status.fromId(usr.get(x).getCondition()).toString()%></td>
		<td><%=Status.fromId(usr.get(x).getStatus()).toString()%></td>
	</tr>
	<%
		}
	%>
</table>

<br>
<a href="/admin/virtualmachines.jsp?newMachine=0">New CentOS Machine</a>
<a href="/admin/virtualmachines.jsp?newMachine=1">New Photon Machine</a>
<br>
<a href="/admin/virtualmachines.jsp?nat=true">Update Nat</a>
<br>