package com.vcloudairshare.client.view.failure;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface IFailureView extends IsWidget {
	public interface Presenter {
		void goTo(Place place);
	}
	void setPresenter(FailureActivity welcomeActivity);
  public void setDetails(String txt);

}
