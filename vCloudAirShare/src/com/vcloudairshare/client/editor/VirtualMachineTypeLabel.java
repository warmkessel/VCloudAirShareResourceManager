package com.vcloudairshare.client.editor;

import com.google.gwt.user.client.ui.ValueLabel;

public class VirtualMachineTypeLabel<T extends Number> extends ValueLabel<T> {

	  public VirtualMachineTypeLabel() {
	    super(new VirtualMachineTypeFormatRenderer());
	  }
	}