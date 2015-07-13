package com.vcloudairshare.client.view.home;

import com.vcloudairshare.client.AbstractApplicationActivity;
import com.vcloudairshare.client.ClientFactory;
import com.vcloudairshare.client.view.authenticate.AuthenticatePlace;
import com.vcloudairshare.client.view.login.LoginPlace;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class HomeActivity extends AbstractApplicationActivity implements
		HomeView.Presenter {
	private ClientFactory clientFactory = null;

	// private static ViewMessages messages = GWT.create(ViewMessages.class);
	private IHomeView homeView = null;

	public HomeActivity(HomePlace place, ClientFactory clientFactory) {

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

		if (clientFactory.getEntityDepo().isUserLoggedIn()) {
			if (!clientFactory.getEntityDepo().isUserLoggedInReady()) {
				clientFactory.getPlaceController()
						.goTo(new AuthenticatePlace());
			} else {
				clientFactory.getPlaceController().goTo(new LoginPlace());
			}
		} else {
			homeView = clientFactory.getHomeView();
			homeView.setPresenter(this);
			containerWidget.setWidget(homeView.asWidget());
			homeView.setup();
		}
	}
}