package com.vcloudairshare.client.view.account;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface IAccountView extends IsWidget {
	public interface Presenter {
		void goTo(Place place);
	}
	void setPresenter(AccountActivity homeActivity);

}
