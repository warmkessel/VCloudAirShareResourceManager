package com.vcloudairshare.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.vcloudairshare.client.view.failure.FailureView;
import com.vcloudairshare.client.view.failure.IFailureView;
import com.vcloudairshare.client.view.home.HomeView;
import com.vcloudairshare.client.view.home.IHomeView;
import com.vcloudairshare.client.view.login.ILoginView;
import com.vcloudairshare.client.view.login.LoginView;
import com.vcloudairshare.shared.request.VMwareBlogsRequestFactory;

public class ClientFactoryImpl implements ClientFactory {
  VMwareBlogsRequestFactory requestFactory = GWT.create(VMwareBlogsRequestFactory.class);
  private final EventBus eventBus = new SimpleEventBus();
  private final CommunicationsManager commManager = new CommunicationsManager(this);
  private final EntityDepo depo = new EntityDepo();
  private String message ="";
  
  private final PlaceController placeController = new PlaceController(eventBus);

  public ClientFactoryImpl() {
    requestFactory.initialize(getEventBus());
  }
  public IFailureView getFailureView() {
    return new FailureView();
  }

  public IHomeView getHomeView() {
    return new HomeView();
  }
  
  public ILoginView getLoginView() {
	    return new LoginView();
	  }
  public EventBus getEventBus() {
    return eventBus;
  }

  public PlaceController getPlaceController() {
    return placeController;
  }

  public VMwareBlogsRequestFactory getRequestFactory() {
    return requestFactory;
  }

  public CommunicationsManager getCommunicationsManager() {
    return commManager;
  }

  public EntityDepo getEntityDepo() {
    return depo;
  }
  public String getErrorMessage(){
    return message;
  }
  public void setErrorMessage(String message){
    this.message = message;
  }
}
