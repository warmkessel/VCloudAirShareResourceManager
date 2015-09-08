package com.vcloudairshare.client.event;

import com.google.gwt.event.shared.EventHandler;
  public interface ToManyMachinesEventHandler extends EventHandler {
    void onMessageReceived(ToManyMachinesEvent event);
}