<%@ page language="java" import="java.util.Date" import="java.util.List"
	import="com.vcloudairshare.server.datastore.entity.VirtualMachine"
	import="com.vcloudairshare.server.datastore.service.VirtualMachineService"
	import="com.vcloudairshare.shared.enumeration.VirtualMachineStatus"
	import="com.vcloudairshare.server.datastore.service.DataServices"
	import="java.text.SimpleDateFormat" import="java.lang.StringBuffer"
	import="com.vcloudairshare.shared.enumeration.Status"
	import="com.vcloudairshare.shared.enumeration.Language"
	import="com.vcloudairshare.shared.enumeration.VirtualHostType"
	errorPage="/error.jsp" pageEncoding="UTF-8"%>
<%
	response.setHeader("Cache-Control", "max-age=0");
	response.setHeader("Content-type", "text/html; charset=UTF-8");
%><?xml version="1.0" encoding="UTF-8"?>
<%
	VirtualMachine a = null;
	try {
		String idString = request.getParameter("id");
		String powerString = request.getParameter("power");
		String decommissionString = request.getParameter("decommission");
		Boolean power = false;
		Boolean decommission = false;
		if (null != idString && idString.length() > 0) {
			Long inLong = Long.parseLong(idString);
			if (null != powerString && powerString.length() > 0) {
				power = Boolean.parseBoolean(powerString);
				if(power){
					power = DataServices.getVirtualMachineService().power(inLong, VirtualMachineStatus.POWERON);
				}
				else{
					power = DataServices.getVirtualMachineService().power(inLong, VirtualMachineStatus.POWEROFF);

				}
						
			}
			if (null != decommissionString && decommissionString.length() > 0) {
				decommission = DataServices.getVirtualMachineService().decommission(
						inLong);
			}
			a = VirtualMachineService.findById(inLong);
		}
		if (a == null) {
			a = new VirtualMachine();
		}
		boolean dirty = false;
		String machinename = request.getParameter("machinename");
		if (null != machinename && machinename.length() > 0) {
			a.setMachinename(machinename);
			dirty = true;
		}
		String machineDesc = request.getParameter("machineDesc");
		if (null != machineDesc && machineDesc.length() > 0) {
			a.setMachineDesc(machineDesc);
			dirty = true;
		}
		String airId = request.getParameter("airId");
		if (null != airId && airId.length() > 0) {
			a.setAirId(airId);
			dirty = true;
		}
		String publicIpaddress = request
				.getParameter("publicIpaddress");
		if (null != publicIpaddress && publicIpaddress.length() > 0) {
			a.setPublicIpAddress(publicIpaddress);
			dirty = true;
		}
		String privateIpaddress = request
				.getParameter("privateIpaddress");
		if (null != privateIpaddress && privateIpaddress.length() > 0) {
			a.setPrivateIpAddress(privateIpaddress);
			dirty = true;
		}
		String currentUser = request.getParameter("currentUser");
		if (null != currentUser && currentUser.length() > 0) {
			a.setCurrentUser(Long.parseLong(currentUser));
			dirty = true;
		}
		String currentUserName = request
				.getParameter("currentUserName");
		if (null != currentUserName && currentUserName.length() > 0) {
			a.setCurrentUserName(currentUserName);
			dirty = true;
		}

		String machinetype = request.getParameter("machinetype");
		if (null != machinetype && machinetype.length() > 0) {
			a.setMachinetype(Integer.parseInt(machinetype));
			dirty = true;
		}
		String condition = request.getParameter("condition");
		if (null != condition && condition.length() > 0) {
			a.setCondition(Integer.parseInt(condition));
			dirty = true;
		}
		String pass = request.getParameter("pass");
		if (null != pass && pass.length() > 0) {
			a.setPass(pass);
			dirty = true;
		}
		String status = request.getParameter("status");
		if (null != status && status.length() > 0) {
			a.setStatus(status);
			dirty = true;
		}
		if (dirty) {
			a = DataServices.getVirtualMachineService().persist(a);
		}

	} catch (NumberFormatException NFE) {
	}
	List<String> theLanguageKeys = Language.idValues();
	List<Integer> theStatusKeys = Status.idValues();
	List<Integer> theMachineKeys = VirtualHostType.idValues();
%>
<br>

<form
	action="virtualmachine.jsp<%if (null != a.getId()) {
				out.print("?id=" + a.getId().toString());
			}%>"
	method="post">
	ID:
	<%=a.getId()%>
	<%
		if (null != a.getId()) {
	%><input type="hidden" name="id"
		value="<%=a.getId()%>">
	<%
		}
	%>
	<br> Machine Name: <input type="text" name="machinename"
		value="<%=a.getMachinename()%>"></input> <br> Machine Desc: <input
		type="text" name="machineDesc" value="<%=a.getMachineDesc()%>"></input>
	<br> AirId: <input type="text" name="airId"
		value="<%=a.getAirId()%>"></input> <br> Public IP Address: <input
		type="text" name="publicIpaddress"
		value="<%=a.getPublicIpAddress()%>"></input> <br> Private IP
	Address: <input type="text" name="privateIpaddress"
		value="<%=a.getPrivateIpAddress()%>"></input> <br> Current
	User: <input type="text" name="currentUser"
		value="<%=a.getCurrentUser()%>"></input> <br> Current User
	Name: <input type="text" name="currentUserName"
		value="<%=a.getCurrentUserName()%>"></input> <br> Root
	Password: <input type="text" name="pass" value="<%=a.getPass()%>"></input>
	<br> Machine Type: <select name="machinetype">
		<%
			for (int x = 0; x < theMachineKeys.size(); x++) {
				Integer key = theMachineKeys.get(x);
		%>
		<option value="<%=key%>"
			<%=(key.equals(a.getMachinetype())) ? " selected " : ""%>><%=VirtualHostType.fromId(key)%></option>
		<%
			}
		%>
	</select> <br> Condition: <select name="condition">
		<%
			for (int x = 0; x < theStatusKeys.size(); x++) {
				Integer key = theStatusKeys.get(x);
		%>
		<option value="<%=key%>"
			<%=(key.equals(a.getCondition())) ? " selected " : ""%>><%=Status.fromId(key)%></option>
		<%
			}
		%>
	</select> <br> Status: <select name="status">
		<%
			for (int x = 0; x < theStatusKeys.size(); x++) {
				Integer key = theStatusKeys.get(x);
		%>
		<option value="<%=key%>"
			<%=(key.equals(a.getStatus())) ? " selected " : ""%>><%=Status.fromId(key)%></option>
		<%
			}
		%>
	</select> <br> <input type="submit" name="Save"></input><br><a
		href="/admin/virtualmachine.jsp?power=true&id=<%=request.getParameter("id")%>">PowerOn</a><br><a
		href="/admin/virtualmachine.jsp?power=false&id=<%=request.getParameter("id")%>">PowerOff</a><br><a
		href="/admin/virtualmachine.jsp?decommission=true&id=<%=request.getParameter("id")%>">Decommission</a>

</form>