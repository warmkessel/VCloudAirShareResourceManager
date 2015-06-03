package com.vcloudairshare.client.view.login;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface ILoginView extends IsWidget {
	public interface Presenter {
		void goTo(Place place);
	}
	void setPresenter(LoginActivity homeActivity);

}
