package com.vcloudairshare.client.editor;

import com.google.gwt.user.client.ui.ValueLabel;

public class MachineTypeLabel<T extends Number> extends ValueLabel<T> {

	  public MachineTypeLabel() {
	    super(new MachineTypeFormatRenderer());
	  }
	}