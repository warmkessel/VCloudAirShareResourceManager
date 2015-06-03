package com.vcloudairshare.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;
import com.vcloudairshare.client.event.ResizeEvent;
import com.vcloudairshare.client.view.home.HomePlace;
import com.vcloudairshare.client.view.VMwareBlogsConstants;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class vCloudAirShare implements EntryPoint, ResizeHandler {
  private static VMwareBlogsConstants constants = GWT.create(VMwareBlogsConstants.class);

  private Place defaultPlace = new HomePlace();
  private SimplePanel appWidget = new SimplePanel();
  ClientFactory clientFactory = null;

  /**
   * This is the entry point method.
   */

  public void onModuleLoad() {
    clientFactory = GWT.create(ClientFactory.class);
    EventBus eventBus = clientFactory.getEventBus();
    PlaceController placeController = clientFactory.getPlaceController();

    // Start ActivityManager for the main widget with our ActivityMapper
    ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
    ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
    activityManager.setDisplay(appWidget);

    // Start PlaceHistoryHandler with our PlaceHistoryMapper
    AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);
    PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
    historyHandler.register(placeController, eventBus, defaultPlace);
    RootPanel.get(constants.rootpanel_blogs()).add(appWidget);

    // RootPanel.get(constants.rootpanel_blogs()).add(appWidget);
    // Goes to the place represented on URL else default place
    historyHandler.handleCurrentHistory();
    Window.addResizeHandler(this);
  }

  @Override
  public void onResize(com.google.gwt.event.logical.shared.ResizeEvent event) {
    clientFactory.getEventBus().fireEvent(new ResizeEvent());
  }
}