package com.vcloudairshare.client.editor;

import java.text.NumberFormat;

import com.google.gwt.text.shared.AbstractRenderer;
import com.vcloudairshare.shared.enumeration.Status;

public class StatusFormatRenderer  extends AbstractRenderer<Number> {

	  /**
	   * Create an instance using {@link NumberFormat#getDecimalFormat()}.
	   */
	  public StatusFormatRenderer() {
	  }

	  public String render(Number object) {
	    if (object == null || ! (object instanceof Integer)) {
	      return "";
	    }
	    return Status.fromId((Integer)object).toString().toString();
	  }
	}