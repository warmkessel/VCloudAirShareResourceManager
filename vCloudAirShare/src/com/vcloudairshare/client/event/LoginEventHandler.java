package com.vcloudairshare.client.event;

import com.google.gwt.event.shared.EventHandler;
  public interface LoginEventHandler extends EventHandler {
    void onMessageReceived(LoginEvent event);
}