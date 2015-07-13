package com.vcloudairshare.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.URL;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.jsonp.client.TimeoutException;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.vcloudairshare.client.event.ErrorEvent;
import com.vcloudairshare.client.event.LoginEvent;
import com.vcloudairshare.client.event.VirtualMachinesReceivedEvent;
import com.vcloudairshare.client.jso.FeedJSO;
import com.vcloudairshare.shared.enumeration.MachineType;
import com.vcloudairshare.shared.enumeration.Status;
import com.vcloudairshare.shared.interfaces.HomeService;
import com.vcloudairshare.shared.interfaces.HomeServiceAsync;
import com.vcloudairshare.shared.proxy.UserDTO;
import com.vcloudairshare.shared.proxy.VirtualMachineDTO;

public class CommunicationsManager {  
	 public static String OAUTHTOKEN = "oauth_token=";
		public static String OAUTHVERIFIER = "oauth_verifier=";
	private final HomeServiceAsync homeService = GWT
			.create(HomeService.class);
	
	public ClientFactory factory;
  public CommunicationsManager(ClientFactory factory){
    this.factory = factory;
  }
 
  public void authenticateUser(String tid) {
	  factory.getRequestFactory().userRequest().findByTwitterCredential2(tid).fire(
    new Receiver<UserDTO>() {
		@Override
		public void onSuccess(UserDTO result) {
			 factory.getEntityDepo().setUser(result);
	        factory.getEventBus().fireEvent(new LoginEvent());			
		}
	  });
  }
	  
	  public void requestVirtualMacine(int from, int count, MachineType machineType,
			  Status status) {
		  factory.getRequestFactory().virtualMacineRequest().findFirstByAvialable(from, count, machineType, status).fire(
	    new Receiver<VirtualMachineDTO>() {
			@Override
			public void onSuccess(VirtualMachineDTO result) {
				 factory.getEntityDepo().setTest(result);
		        factory.getEventBus().fireEvent(new VirtualMachinesReceivedEvent());			
			}
		  });
	  }
	  
	  public void requestVirtualMacines(int from, int count, MachineType machineType,
			  Status status) {
		  factory.getRequestFactory().virtualMacineRequest().findByAvialable(from, count, machineType, status).fire(
	    new Receiver<List<VirtualMachineDTO>>() {
			@Override
			public void onSuccess(List<VirtualMachineDTO> result) {
				 factory.getEntityDepo().setVm(result);
		        factory.getEventBus().fireEvent(new VirtualMachinesReceivedEvent());			
			}
		  });
	  }
//    String url = URL.encode(ExternalJNSI.getJSNI().getCallback());
//    JsonpRequestBuilder requestBuilder = new JsonpRequestBuilder();
//    requestBuilder.setTimeout(60000);
//    requestBuilder.requestObject(url, new AsyncCallback<FeedJSO>() {
//      @Override
//      public void onFailure(Throwable caught) {
////        Window.alert(caught.getMessage());
//        if(caught instanceof TimeoutException){
//          factory.setErrorMessage("We did not get the list of articles in the allotted amount of time.  Please refresh the page, perhaps that will work.  Here are some details..." + caught.getMessage() );
//          factory.getEventBus().fireEvent(new ErrorEvent());        }
//        else{
//          factory.setErrorMessage("We seems to have a problem getting the Articles. Here are some details..." + caught.getMessage() );
//          factory.getEventBus().fireEvent(new ErrorEvent());
//        }
//      }
//
//      @Override
//      public void onSuccess(FeedJSO feed) {
//        factory.getEntityDepo().setArticles(feed.getArticle());
//        factory.getEventBus().fireEvent(new ArticlesReceivedEvent());
//      }
//    });
	  public void requestPower(String machine, Boolean power){

		  homeService.power(machine, power,
					new AsyncCallback<Boolean>() {
						
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onSuccess(Boolean result) {
				if(result){
					Window.alert("Machine Powered On");
				}
				else{
					Window.alert("Machine Powered Off");
				}
		}
					
		});
	  }
	  public void requestCheckout(String machine, Boolean power){

		  homeService.checkout(machine, power,factory.getEntityDepo().getUser().getUsername(),
					new AsyncCallback<Boolean>() {
						
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onSuccess(Boolean result) {
				requestVirtualMacines(0,
						10, MachineType.VCLOUDAIR, Status.APPROVED);			}
					
		});
	  }
	  public void fetchAuthentication() {
		    String url = URL.encode(ExternalJNSI.getJSNI().getCallback() + "?"+ OAUTHTOKEN + factory.getEntityDepo().getoAuthToken() + "&" + OAUTHVERIFIER + factory.getEntityDepo().getoAuthVerifier());
		    JsonpRequestBuilder requestBuilder = new JsonpRequestBuilder();
		    requestBuilder.setTimeout(60000);
		    requestBuilder.requestObject(url, new AsyncCallback<FeedJSO>() {
		      @Override
		      public void onFailure(Throwable caught) {
//		        Window.alert(caught.getMessage());
		        if(caught instanceof TimeoutException){
		          factory.setErrorMessage("We did not get the user authentication.  Please refresh the page, perhaps that will work.  Here are some details..." + caught.getMessage() );
		          factory.getEventBus().fireEvent(new ErrorEvent());        }
		        else{
		          factory.setErrorMessage("We did not get the user authentication. Here are some details..." + caught.getMessage() );
		          factory.getEventBus().fireEvent(new ErrorEvent());
		        }
		      }

		      @Override
		      public void onSuccess(FeedJSO feed) {
		    	  GWT.log("feed" + feed);
		        factory.getEntityDepo().setFeed(feed);
		        factory.getEventBus().fireEvent(new LoginEvent());
		      }
		    });
		  }
	 

  }
