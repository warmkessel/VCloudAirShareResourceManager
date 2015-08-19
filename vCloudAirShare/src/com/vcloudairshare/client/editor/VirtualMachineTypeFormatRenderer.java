package com.vcloudairshare.client.editor;

import java.text.NumberFormat;

import com.google.gwt.text.shared.AbstractRenderer;
import com.vcloudairshare.shared.enumeration.VirtualMachineType;

public class VirtualMachineTypeFormatRenderer  extends AbstractRenderer<Number> {

	  /**
	   * Create an instance using {@link NumberFormat#getDecimalFormat()}.
	   */
	  public VirtualMachineTypeFormatRenderer() {
	  }

	  public String render(Number object) {
	    if (object == null || ! (object instanceof Integer)) {
	      return "";
	    }
	    return VirtualMachineType.fromId((Integer)object).getDesc().toString();
	  }
	}