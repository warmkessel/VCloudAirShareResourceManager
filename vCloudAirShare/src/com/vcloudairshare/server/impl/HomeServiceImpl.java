package com.vcloudairshare.server.impl;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.vcloudairshare.server.communications.Constants;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.vcloudairshare.server.communications.VCloudAirComm;
import com.vcloudairshare.server.datastore.entity.VirtualMachine;
import com.vcloudairshare.server.datastore.service.DataServices;
import com.vcloudairshare.shared.enumeration.Status;
import com.vcloudairshare.shared.interfaces.HomeService;

public class HomeServiceImpl extends RemoteServiceServlet implements HomeService {
  /**
   * 
   */
  private static final long serialVersionUID = -5441875622545162286L;

//@Override
//public UserDTO authentication(String username, String password)
//		throws IllegalArgumentException {
//	
//	DataServices.getUsersService().findByCredential(username, password);
//	return new UserDTO();;
//}
	public Boolean checkout(String id, Boolean state, String userName){

		VirtualMachine vm = DataServices.getVirtualMachineService().findByAirId(id);
		if(vm != null){
			if(state){
				vm.setCondition(Status.INUSE.getId());
				vm.setCurrentUserName(userName);
			}
			else{
				vm.setCondition(Status.AVAILABLE.getId());
				vm.setCurrentUserName("");

			}
			DataServices.getVirtualMachineService().persist(vm);

			return power(id, state);
		   } 
		   return false;
	}
	public Boolean power(String id, Boolean state){
		VCloudAirComm comm = new VCloudAirComm();
		comm.login();
		
	    try {
	    	
	       String url = comm.getVchsHostname() + Constants.API_VAPP;
	       if(state) {
		       url = url + "/" + id +  Constants.POWER_ON;
	       } else {
		       url = url + "/" + id + Constants.POWER_OFF;
	       }
	       URL url_obj = new URL(url);
	       HttpURLConnection conn = (HttpURLConnection) url_obj.openConnection();
	       conn.setConnectTimeout(60000); // 60 Seconds
	       conn.setReadTimeout(60000);
	       conn.addRequestProperty(Constants.ACCEPT, 
	    		                   Constants.APPLICATION_PLUS_XML_VERSION+"5.11");		    		               
	       conn.addRequestProperty(Constants.VCD_AUTHORIZATION_HEADER, 
	    		                   comm.getVchstoken());
	       conn.addRequestProperty(Constants.AUTHORIZATION, 
	    		                   "Bearer " + comm.getVchsToken2());
	       conn.setRequestMethod(Constants.POST);
	       System.out.println("Request URL ... " + url);
	  

	       // normally, 3xx is redirect
	       int status = conn.getResponseCode();

	       System.out.println("Response Code ... " + status);
	       InputStream inStream = conn.getInputStream();	
	       StringBuffer sb = new StringBuffer();
	       sb = VCloudAirComm.buildResponse(sb, inStream);
	       System.out.println("response = " + sb.toString());
	       return state;
	    }
	    catch (Exception e){
	    	System.out.println(e.getMessage());
	    }
	    return false;
	    }

}
