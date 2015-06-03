package com.vcloudairshare.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class VirtualMachinesReceivedEvent extends GwtEvent<VirtualMachinesReceivedEventHandler> {

    public static Type<VirtualMachinesReceivedEventHandler> TYPE = new Type<VirtualMachinesReceivedEventHandler>();


    public VirtualMachinesReceivedEvent() {
    }

    @Override
    public Type<VirtualMachinesReceivedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(VirtualMachinesReceivedEventHandler handler) {
        handler.onMessageReceived(this);
    }
}