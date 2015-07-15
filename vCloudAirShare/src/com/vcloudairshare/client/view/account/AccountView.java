package com.vcloudairshare.client.view.account;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.vcloudairshare.client.event.AccountEvent;
import com.vcloudairshare.client.event.AccountEventHandler;
import com.vcloudairshare.client.view.home.HomePlace;
import com.vcloudairshare.shared.interfaces.HomeService;
import com.vcloudairshare.shared.interfaces.HomeServiceAsync;

public class AccountView extends Composite implements IAccountView {
	@UiTemplate("AccountView.ui.xml")
	interface EpisodeDetailsViewUiBinder extends UiBinder<Widget, AccountView> {
	}

	// private static ViewConstants constants = GWT.create(ViewConstants.class);
	// private static ViewMessages messages = GWT.create(ViewMessages.class);
	HomeServiceAsync homeService = (HomeServiceAsync) GWT
			.create(HomeService.class);
	private HandlerRegistration handlerRegistration = null;

	public static final String VMWAREBLOGSCOOKIEIDENTIFIER = "vmw";

	// Driver driver = GWT.create(Driver.class);

	AccountActivity homeActivity;

	public AccountView() {
		initWidget(GWT.<EpisodeDetailsViewUiBinder> create(
				EpisodeDetailsViewUiBinder.class).createAndBindUi(this));
	}

	@Override
	public void setPresenter(AccountActivity loginActivity) {
		this.homeActivity = loginActivity;
		
		
		if (homeActivity
				.getClientFactory()
				.getEntityDepo()
				.isUserLoggedInReady()) {
			homeActivity.getClientFactory().getCommunicationsManager()
					.fetchAccount();
			registerHandler();
		}
	}

	

	public void registerHandler() {
		handlerRegistration = homeActivity
				.getClientFactory()
				.getEventBus()
				.addHandler(AccountEvent.TYPE,
						new AccountEventHandler() {
							@Override
							public void onMessageReceived(
									AccountEvent event) {
								 homeActivity.getClientFactory().getPlaceController().goTo(new
								HomePlace());
								handlerRegistration.removeHandler();
							}
						});
	}
}