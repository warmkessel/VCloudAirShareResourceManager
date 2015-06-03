package com.vcloudairshare.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ResizeEvent extends GwtEvent<ResizeEventHandler> {

    public static Type<ResizeEventHandler> TYPE = new Type<ResizeEventHandler>();


    public ResizeEvent() {
    }

    @Override
    public Type<ResizeEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ResizeEventHandler handler) {
        handler.onMessageReceived(this);
    }
}