package com.vcloudairshare.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ToManyMachinesEvent extends GwtEvent<ToManyMachinesEventHandler> {

    public static Type<ToManyMachinesEventHandler> TYPE = new Type<ToManyMachinesEventHandler>();


    public ToManyMachinesEvent() {
    }

    @Override
    public Type<ToManyMachinesEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ToManyMachinesEventHandler handler) {
        handler.onMessageReceived(this);
    }
}