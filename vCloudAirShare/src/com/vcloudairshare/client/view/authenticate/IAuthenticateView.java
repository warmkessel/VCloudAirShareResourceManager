package com.vcloudairshare.client.view.authenticate;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface IAuthenticateView extends IsWidget {
	public interface Presenter {
		void goTo(Place place);
	}
	void setPresenter(AuthenticateActivity homeActivity);

}
