package com.vcloudairshare.client.event;

import com.google.gwt.event.shared.EventHandler;
  public interface AuthenticateEventHandler extends EventHandler {
    void onMessageReceived(AuthenticateEvent event);
}