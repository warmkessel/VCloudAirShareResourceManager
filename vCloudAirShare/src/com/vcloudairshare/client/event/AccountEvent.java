package com.vcloudairshare.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AccountEvent extends GwtEvent<AccountEventHandler> {

    public static Type<AccountEventHandler> TYPE = new Type<AccountEventHandler>();


    public AccountEvent() {
    }

    @Override
    public Type<AccountEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AccountEventHandler handler) {
        handler.onMessageReceived(this);
    }
}