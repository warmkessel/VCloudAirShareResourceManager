package com.vcloudairshare.client.editor;

import java.io.IOException;

import com.google.gwt.text.shared.Renderer;
import com.google.gwt.user.client.ui.ValueListBox;
import com.vcloudairshare.shared.enumeration.Status;

public class StatusListBox extends ValueListBox<Integer> {

	public StatusListBox() {
		super(new Renderer<Integer>(){
			@Override
			public String render(Integer arg0) {			
				return Status.fromId(arg0).toString();
			}

			@Override
			public void render(Integer arg0, Appendable arg1) throws IOException {
				arg1.append(render(arg0));				
			}
			
		});
		 setValue(Status.getDefault().getId());  // default - IMPORTANT: set before the following call, might be a gwt bug
     setAcceptableValues(Status.idValues());	}
}
