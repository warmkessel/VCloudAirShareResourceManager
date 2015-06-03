package com.vcloudairshare.client;

import com.google.gwt.activity.shared.Activity;

/**
 * Top level abstract class for all Activities in the application.
 * 
 * Simple Activity implementation that is always willing to stop, and does
 * nothing onStop and onCancel. (Extension from Google's default implementation
 * of AbstractActivity)
 * 
 * However, this abstract class is enhanced with: - implementation to navigate
 * to a location
 * 
 * @author
 */
public abstract class AbstractApplicationActivity implements Activity {

	@Override
	public String mayStop() {
		return null;
	}

	@Override
	public void onCancel() {
	}

	@Override
	public void onStop() {
	}
}
