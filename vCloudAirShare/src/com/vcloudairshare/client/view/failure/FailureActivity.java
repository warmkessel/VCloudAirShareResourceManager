package com.vcloudairshare.client.view.failure;

import com.vcloudairshare.client.AbstractApplicationActivity;
import com.vcloudairshare.client.ClientFactory;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class FailureActivity extends
		AbstractApplicationActivity implements
		FailureView.Presenter {
	private ClientFactory clientFactory = null;


//	private static ViewMessages messages = GWT.create(ViewMessages.class);
	private IFailureView welcomeView = null;
	public FailureActivity(FailurePlace place,
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
		welcomeView = clientFactory.getFailureView();
		welcomeView.setPresenter(this);
		welcomeView.setDetails(clientFactory.getErrorMessage());
		containerWidget.setWidget(welcomeView.asWidget());
	}
}