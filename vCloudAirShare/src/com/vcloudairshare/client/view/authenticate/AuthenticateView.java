package com.vcloudairshare.client.view.authenticate;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.vcloudairshare.client.event.AuthenticateEvent;
import com.vcloudairshare.client.event.AuthenticateEventHandler;
import com.vcloudairshare.client.event.LoginEvent;
import com.vcloudairshare.client.event.LoginEventHandler;
import com.vcloudairshare.client.view.home.HomePlace;
import com.vcloudairshare.shared.interfaces.HomeService;
import com.vcloudairshare.shared.interfaces.HomeServiceAsync;

public class AuthenticateView extends Composite implements IAuthenticateView {
	@UiTemplate("AuthenticateView.ui.xml")
	interface EpisodeDetailsViewUiBinder extends UiBinder<Widget, AuthenticateView> {
	}

	// private static ViewConstants constants = GWT.create(ViewConstants.class);
	// private static ViewMessages messages = GWT.create(ViewMessages.class);
	HomeServiceAsync homeService = (HomeServiceAsync) GWT
			.create(HomeService.class);
	private HandlerRegistration handlerRegistration = null;

	public static final String VMWAREBLOGSCOOKIEIDENTIFIER = "vmw";

	// Driver driver = GWT.create(Driver.class);

	AuthenticateActivity homeActivity;

	public AuthenticateView() {
		initWidget(GWT.<EpisodeDetailsViewUiBinder> create(
				EpisodeDetailsViewUiBinder.class).createAndBindUi(this));
	}

	@Override
	public void setPresenter(AuthenticateActivity loginActivity) {
		this.homeActivity = loginActivity;
		
		
		if (homeActivity
				.getClientFactory()
				.getEntityDepo()
				.isUserLoggedInReady()) {
			homeActivity.getClientFactory().getCommunicationsManager()
					.fetchAuthentication();
			registerHandler();
		}
	}

	

	public void registerHandler() {
		handlerRegistration = homeActivity
				.getClientFactory()
				.getEventBus()
				.addHandler(AuthenticateEvent.TYPE,
						new AuthenticateEventHandler() {
							@Override
							public void onMessageReceived(
									AuthenticateEvent event) {
								 homeActivity.getClientFactory().getPlaceController().goTo(new
								HomePlace());
								handlerRegistration.removeHandler();
							}
						});
	}
}