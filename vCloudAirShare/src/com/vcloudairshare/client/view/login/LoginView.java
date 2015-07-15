package com.vcloudairshare.client.view.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.vcloudairshare.shared.interfaces.HomeService;
import com.vcloudairshare.shared.interfaces.HomeServiceAsync;

public class LoginView extends Composite implements ILoginView {
	@UiTemplate("LoginView.ui.xml")
	interface EpisodeDetailsViewUiBinder extends UiBinder<Widget, LoginView> {
	}

	// private static ViewConstants constants = GWT.create(ViewConstants.class);
	// private static ViewMessages messages = GWT.create(ViewMessages.class);
	HomeServiceAsync homeService = (HomeServiceAsync) GWT
			.create(HomeService.class);
//	private HandlerRegistration handlerRegistration = null;

	public static final String VMWAREBLOGSCOOKIEIDENTIFIER = "vmw";

	// Driver driver = GWT.create(Driver.class);

	LoginActivity homeActivity;

	@UiField
	Image twitter;

	public LoginView() {
		initWidget(GWT.<EpisodeDetailsViewUiBinder> create(
				EpisodeDetailsViewUiBinder.class).createAndBindUi(this));
	}

	@Override
	public void setPresenter(LoginActivity loginActivity) {
		this.homeActivity = loginActivity;
	}

	@UiHandler("twitter")
	void handleClick(ClickEvent e) {
		Window.Location.replace("/login.jsp");
	}

//	public void registerHandler() {
//		handlerRegistration = homeActivity
//				.getClientFactory()
//				.getEventBus()
//				.addHandler(LoginEvent.TYPE,
//						new LoginEventHandler() {
//							@Override
//							public void onMessageReceived(
//									LoginEvent event) {
//								 homeActivity.getClientFactory().getPlaceController().goTo(new
//								HomePlace());
//								handlerRegistration.removeHandler();
//							}
//						});
//	}
}