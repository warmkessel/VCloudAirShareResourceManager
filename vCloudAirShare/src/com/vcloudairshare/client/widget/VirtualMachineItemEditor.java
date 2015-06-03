package com.vcloudairshare.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
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
		
	  @UiField Label machinename;
	  @UiField Label ipaddress;
	  @UiField StatusLabel<Integer> status;
	  @UiField MachineTypeLabel<Integer> machinetype;
	 
	  public VirtualMachineItemEditor() {
	    initWidget(BINDER.createAndBindUi(this));
	  }
	}