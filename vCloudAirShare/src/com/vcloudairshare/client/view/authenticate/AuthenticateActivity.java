package com.vcloudairshare.client.view.authenticate;

import com.vcloudairshare.client.AbstractApplicationActivity;
import com.vcloudairshare.client.ClientFactory;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class AuthenticateActivity extends
		AbstractApplicationActivity implements
		AuthenticateView.Presenter {
	private ClientFactory clientFactory = null;


//	private static ViewMessages messages = GWT.create(ViewMessages.class);
	private IAuthenticateView homeView = null;
	public AuthenticateActivity(AuthenticatePlace place,
			ClientFactory clientFactory) {
	
		this.clientFactory = clientFactory;
	}

	public ClientFactory getClientFactory() {
		return clientFactory;
	}

	/**
	 * Navigate to a new Place in the browser
	 */
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

	/**
	 * Ask user before stopping this activity
	 */
	public String mayStop() {
	   return null;
	}

	/**
	 * Invoked by the ActivityManager to start a new Activity
	 */
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
	  homeView = clientFactory.getAuthenticateView();
		homeView.setPresenter(this);
		containerWidget.setWidget(homeView.asWidget());
	}
}