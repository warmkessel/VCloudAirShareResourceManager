package com.vcloudairshare.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.URL;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.jsonp.client.TimeoutException;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.vcloudairshare.client.event.AccountEvent;
import com.vcloudairshare.client.event.AuthenticateEvent;
import com.vcloudairshare.client.event.ErrorEvent;
import com.vcloudairshare.client.event.VirtualMachinesReceivedEvent;
import com.vcloudairshare.client.jso.FeedJSO;
import com.vcloudairshare.client.view.home.HomePlace;
import com.vcloudairshare.shared.enumeration.VirtualHostType;
import com.vcloudairshare.shared.enumeration.Status;
import com.vcloudairshare.shared.interfaces.HomeService;
import com.vcloudairshare.shared.interfaces.HomeServiceAsync;
import com.vcloudairshare.shared.proxy.UserDTO;
import com.vcloudairshare.shared.proxy.VirtualMachineDTO;
import com.vcloudairshare.shared.request.UserRequest;

public class CommunicationsManager {
	public static String OAUTHTOKEN = "oauth_token=";
	public static String OAUTHVERIFIER = "oauth_verifier=";
	private final HomeServiceAsync homeService = GWT.create(HomeService.class);

	public ClientFactory factory;

	public CommunicationsManager(ClientFactory factory) {
		this.factory = factory;
	}

	public void fetchAccount() {
		if(null != factory.getEntityDepo().getFeed()){
			fetchAccount(factory.getEntityDepo().getFeed().getId());

		}
		else{
			fetchAccount(factory.getEntityDepo().getAccountId());
		}
	}

	public void fetchAccount(String theID) {
		fetchAccount(Long.parseLong(theID));
	}
	public void fetchAccount(Long theID) {
		factory.getRequestFactory()
				.userRequest()
				.findByUserIdEnsured(theID)
				.fire(new Receiver<UserDTO>() {
					@Override
					public void onSuccess(UserDTO result) {
						factory.getEntityDepo().setUser(result);
						factory.getEventBus().fireEvent(new AccountEvent());
						if(null != factory.getEntityDepo().getFeed()){
							updateAccount(factory.getEntityDepo().getFeed(), result);
						}
					}
				});
	}
	public void updateAccount(FeedJSO feed, UserDTO user) {
		UserRequest req1 = factory.getRequestFactory().userRequest();  
		UserDTO s2 = req1.edit(user);
		
		s2.setName(feed.getName());
		s2.setScreen_name(feed.getScreenName());
		s2.setDescription(feed.getDescription());
		s2.setName(feed.getName());		
		s2.setLocation(feed.getLocation());
		s2.setUrl(feed.getUrl());		

		GWT.log("UserId" +( s2.getUserId()));
		GWT.log("Id" +( s2.getId()));
		GWT.log("name" +( s2.getName()));
		
		req1
				.persist(s2)
				.fire(new Receiver<UserDTO>() {
					@Override
					public void onSuccess(UserDTO result) {
						factory.getEntityDepo().setUser(result);
					}
				});
	}
	

	public void requestVirtualMacine(int from, int count,
			VirtualHostType machineType, Status status) {
		factory.getRequestFactory().virtualMacineRequest()
				.findFirstByAvialable(from, count, machineType, status)
				.fire(new Receiver<VirtualMachineDTO>() {
					@Override
					public void onSuccess(VirtualMachineDTO result) {
						factory.getEntityDepo().setTest(result);
						factory.getPlaceController().goTo(new HomePlace());
						// factory.getEventBus().fireEvent(new
						// VirtualMachinesReceivedEvent());
					}
				});
	}

	public void requestVirtualMacines(int from, int count,
			VirtualHostType machineType, Status status) {
		factory.getRequestFactory().virtualMacineRequest()
				.findByAvialable(from, count, machineType, status)
				.fire(new Receiver<List<VirtualMachineDTO>>() {
					@Override
					public void onSuccess(List<VirtualMachineDTO> result) {
						factory.getEntityDepo().setVm(result);
						factory.getEventBus().fireEvent(
								new VirtualMachinesReceivedEvent());
					}
				});
	}

	// String url = URL.encode(ExternalJNSI.getJSNI().getCallback());
	// JsonpRequestBuilder requestBuilder = new JsonpRequestBuilder();
	// requestBuilder.setTimeout(60000);
	// requestBuilder.requestObject(url, new AsyncCallback<FeedJSO>() {
	// @Override
	// public void onFailure(Throwable caught) {
	// // Window.alert(caught.getMessage());
	// if(caught instanceof TimeoutException){
	// factory.setErrorMessage("We did not get the list of articles in the allotted amount of time.  Please refresh the page, perhaps that will work.  Here are some details..."
	// + caught.getMessage() );
	// factory.getEventBus().fireEvent(new ErrorEvent()); }
	// else{
	// factory.setErrorMessage("We seems to have a problem getting the Articles. Here are some details..."
	// + caught.getMessage() );
	// factory.getEventBus().fireEvent(new ErrorEvent());
	// }
	// }
	//
	// @Override
	// public void onSuccess(FeedJSO feed) {
	// factory.getEntityDepo().setArticles(feed.getArticle());
	// factory.getEventBus().fireEvent(new ArticlesReceivedEvent());
	// }
	// });
	public void requestPower(String machine, Boolean power) {

		homeService.power(machine, power, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onSuccess(Boolean result) {
				if (result) {
					Window.alert("Machine Powered On");
				} else {
					Window.alert("Machine Powered Off");
				}
			}

		});
	}

	public void requestCheckout(String machine, Boolean power) {

		homeService.checkout(machine, power, factory.getEntityDepo().getUser()
				.getScreen_name(), new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onSuccess(Boolean result) {
				requestVirtualMacines(0, 10, VirtualHostType.VCLOUDAIR,
						Status.APPROVED);
			}
		});
	}

	public void fetchAuthentication() {
		String url = URL.encode(ExternalJNSI.getJSNI().getCallback() + "?"
				+ OAUTHTOKEN + factory.getEntityDepo().getoAuthToken() + "&"
				+ OAUTHVERIFIER + factory.getEntityDepo().getoAuthVerifier());
		JsonpRequestBuilder requestBuilder = new JsonpRequestBuilder();
		requestBuilder.setTimeout(60000);
		requestBuilder.requestObject(url, new AsyncCallback<FeedJSO>() {
			@Override
			public void onFailure(Throwable caught) {
				// Window.alert(caught.getMessage());
				if (caught instanceof TimeoutException) {
					factory.setErrorMessage("We did not get the user authentication.  Please refresh the page, perhaps that will work.  Here are some details..."
							+ caught.getMessage());
					factory.getEventBus().fireEvent(new ErrorEvent());
				} else {
					factory.setErrorMessage("We did not get the user authentication. Here are some details..."
							+ caught.getMessage());
					factory.getEventBus().fireEvent(new ErrorEvent());
				}
			}

			@Override
			public void onSuccess(FeedJSO feed) {
				GWT.log("feed is not null " + (null != feed));
				GWT.log("feed.getId() " + feed.getId());

				factory.getEntityDepo().setAccountId(feed.getId());
				factory.getEntityDepo().setFeed(feed);
				factory.getEventBus().fireEvent(new AuthenticateEvent());
			}
		});
	}

}
