package com.vcloudairshare.client.event;

import com.google.gwt.event.shared.EventHandler;
  public interface AccountEventHandler extends EventHandler {
    void onMessageReceived(AccountEvent event);
}