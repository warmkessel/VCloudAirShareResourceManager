package com.vcloudairshare.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.vcloudairshare.client.view.authenticate.AuthenticateActivity;
import com.vcloudairshare.client.view.authenticate.AuthenticatePlace;
import com.vcloudairshare.client.view.failure.FailureActivity;
import com.vcloudairshare.client.view.failure.FailurePlace;
import com.vcloudairshare.client.view.home.HomeActivity;
import com.vcloudairshare.client.view.home.HomePlace;
import com.vcloudairshare.client.view.login.LoginActivity;
import com.vcloudairshare.client.view.login.LoginPlace;

public class AppActivityMapper implements ActivityMapper {
    private ClientFactory clientFactory;

    public AppActivityMapper(ClientFactory clientFactory) {
        super();
        this.clientFactory = clientFactory;
    }

    @Override
    public Activity getActivity(Place place) {
    	if (place instanceof AuthenticatePlace)
            return new AuthenticateActivity((AuthenticatePlace) place, clientFactory);
    	if (place instanceof LoginPlace)
            return new LoginActivity((LoginPlace) place, clientFactory);
        if (place instanceof FailurePlace)
          return new FailureActivity((FailurePlace) place, clientFactory);
        return new HomeActivity((HomePlace) place, clientFactory);
    }
}