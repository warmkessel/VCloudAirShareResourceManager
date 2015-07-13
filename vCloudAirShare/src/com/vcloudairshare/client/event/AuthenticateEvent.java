package com.vcloudairshare.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AuthenticateEvent extends GwtEvent<AuthenticateEventHandler> {

    public static Type<AuthenticateEventHandler> TYPE = new Type<AuthenticateEventHandler>();


    public AuthenticateEvent() {
    }

    @Override
    public Type<AuthenticateEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AuthenticateEventHandler handler) {
        handler.onMessageReceived(this);
    }
}