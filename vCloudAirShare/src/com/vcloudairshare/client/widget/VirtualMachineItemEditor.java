package com.vcloudairshare.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.vcloudairshare.client.editor.MachineTypeLabel;
import com.vcloudairshare.client.editor.StatusLabel;
import com.vcloudairshare.shared.proxy.VirtualMachineDTO;

public class VirtualMachineItemEditor extends Composite implements Editor<VirtualMachineDTO>{
	  interface Binder extends UiBinder<Widget, VirtualMachineItemEditor> {}
	 
	  interface EditorDriver
		extends
		RequestFactoryEditorDriver<VirtualMachineDTO, VirtualMachineItemEditor> {
}
	  
	  private static final Binder BINDER = GWT.create(Binder.class);
		
	  @UiField InlineLabel machinename;
	  @UiField InlineLabel machineDesc;
	  @UiField StatusLabel<Integer> condition;
	  
	  Button checkout;
	 
	  public VirtualMachineItemEditor() {
	    initWidget(BINDER.createAndBindUi(this));
	  }
	  @UiHandler("checkout")
		void onClick(ClickEvent e) {
			Window.alert("Hello!");
		}
	}