package com.vcloudairshare.client.view.home;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface IHomeView extends IsWidget {
	public interface Presenter {
		void goTo(Place place);
	}
	void setPresenter(HomeActivity homeActivity);
	void setup();

}
