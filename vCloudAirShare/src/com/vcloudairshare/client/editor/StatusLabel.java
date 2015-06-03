package com.vcloudairshare.client.editor;

import com.google.gwt.user.client.ui.ValueLabel;

public class StatusLabel<T extends Number> extends ValueLabel<T> {

	  public StatusLabel() {
	    super(new StatusFormatRenderer());
	  }
	}