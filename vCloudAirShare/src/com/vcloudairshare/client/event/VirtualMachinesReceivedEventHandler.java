package com.vcloudairshare.client.event;

import com.google.gwt.event.shared.EventHandler;
  public interface VirtualMachinesReceivedEventHandler extends EventHandler {
    void onMessageReceived(VirtualMachinesReceivedEvent event);
}