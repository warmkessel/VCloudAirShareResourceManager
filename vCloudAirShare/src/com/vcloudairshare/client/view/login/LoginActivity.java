package com.vcloudairshare.client.view.login;

import com.vcloudairshare.client.AbstractApplicationActivity;
import com.vcloudairshare.client.ClientFactory;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class LoginActivity extends
		AbstractApplicationActivity implements
		LoginView.Presenter {
	private ClientFactory clientFactory = null;


//	private static ViewMessages messages = GWT.create(ViewMessages.class);
	private ILoginView homeView = null;
	public LoginActivity(LoginPlace place,
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
	  homeView = clientFactory.getLoginView();
		homeView.setPresenter(this);
		containerWidget.setWidget(homeView.asWidget());
	}
}