package com.vcloudairshare.client.view.home;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.vcloudairshare.client.widget.VirtualMachineItemView;
import com.vcloudairshare.shared.interfaces.HomeService;
import com.vcloudairshare.shared.interfaces.HomeServiceAsync;

public class HomeView extends Composite implements IHomeView {
  @UiTemplate("HomeView.ui.xml")
  interface EpisodeDetailsViewUiBinder extends UiBinder<Widget, HomeView> {
  }

  // private static ViewConstants constants = GWT.create(ViewConstants.class);
  // private static ViewMessages messages = GWT.create(ViewMessages.class);
  HomeServiceAsync homeService = (HomeServiceAsync) GWT.create(HomeService.class);
  private HandlerRegistration handlerRegistration = null;
  
  public static final String VMWAREBLOGSCOOKIEIDENTIFIER = "vmw";

  // Driver driver = GWT.create(Driver.class);

  HomeActivity homeActivity;

  @UiField
  VirtualMachineItemView machinelist;
  
  public HomeView() {
    initWidget(GWT.<EpisodeDetailsViewUiBinder> create(EpisodeDetailsViewUiBinder.class)
        .createAndBindUi(this));	
  }

  @Override
  public void setPresenter(HomeActivity homeActivity) {
    this.homeActivity = homeActivity;
  }

	public void setup(){
	  machinelist.init(homeActivity.getClientFactory()); 
	  machinelist.display();
  }
 
}