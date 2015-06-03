package com.vcloudairshare.client.editor;

import java.text.NumberFormat;

import com.google.gwt.text.shared.AbstractRenderer;
import com.vcloudairshare.shared.enumeration.MachineType;

public class MachineTypeFormatRenderer  extends AbstractRenderer<Number> {

	  /**
	   * Create an instance using {@link NumberFormat#getDecimalFormat()}.
	   */
	  public MachineTypeFormatRenderer() {
	  }

	  public String render(Number object) {
	    if (object == null || ! (object instanceof Integer)) {
	      return "";
	    }
	    return MachineType.fromId((Integer)object).toString().toString();
	  }
	}