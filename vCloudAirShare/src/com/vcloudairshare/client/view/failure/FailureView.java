package com.vcloudairshare.client.view.failure;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class FailureView extends Composite implements IFailureView {
	@UiTemplate("FailureView.ui.xml")
	interface EpisodeDetailsViewUiBinder extends UiBinder<Widget, FailureView> {
	}

//  private HandlerRegistration handlerRegistration = null;

  // private static ViewConstants constants = GWT.create(ViewConstants.class);
	// private static ViewMessages messages = GWT.create(ViewMessages.class);

	public static final String VMWAREBLOGSCOOKIEIDENTIFIER = "vmw";

	// Driver driver = GWT.create(Driver.class);

	FailureActivity welcomeActivity;

  @UiField
  Label details;
  
	public FailureView() {
		initWidget(GWT.<EpisodeDetailsViewUiBinder> create(
				EpisodeDetailsViewUiBinder.class).createAndBindUi(this));
	}
	@Override
	public void setPresenter(FailureActivity welcomeActivity) {
		this.welcomeActivity = welcomeActivity;
	}
	public void setDetails(String txt){
	  details.setText(txt);
	}
}