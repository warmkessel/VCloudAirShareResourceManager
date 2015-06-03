package com.vcloudairshare.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ErrorEvent extends GwtEvent<ErrorEventHandler> {

    public static Type<ErrorEventHandler> TYPE = new Type<ErrorEventHandler>();


    public ErrorEvent() {
    }

    @Override
    public Type<ErrorEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ErrorEventHandler handler) {
        handler.onMessageReceived(this);
    }
}