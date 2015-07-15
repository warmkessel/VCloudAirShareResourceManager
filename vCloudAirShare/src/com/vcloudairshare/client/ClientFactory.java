package com.vcloudairshare.client;


import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.vcloudairshare.client.view.account.IAccountView;
import com.vcloudairshare.client.view.authenticate.IAuthenticateView;
import com.vcloudairshare.client.view.failure.IFailureView;
import com.vcloudairshare.client.view.home.IHomeView;
import com.vcloudairshare.client.view.login.ILoginView;
import com.vcloudairshare.shared.request.VMwareBlogsRequestFactory;


public interface ClientFactory {

  public IHomeView getHomeView();
  public IFailureView getFailureView();
  public ILoginView getLoginView();
  public IAuthenticateView getAuthenticateView();
  public IAccountView getAccountView();

  
  public EventBus getEventBus();

  public PlaceController getPlaceController();

  public VMwareBlogsRequestFactory getRequestFactory();
  
  public CommunicationsManager getCommunicationsManager();

  public EntityDepo getEntityDepo();
  
  public String getErrorMessage();
  public void setErrorMessage(String message);
 
  }
