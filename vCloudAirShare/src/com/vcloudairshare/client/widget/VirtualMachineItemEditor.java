package com.vcloudairshare.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.vcloudairshare.client.ClientFactory;
import com.vcloudairshare.client.editor.StatusLabel;
import com.vcloudairshare.client.editor.VirtualMachineTypeLabel;
import com.vcloudairshare.shared.enumeration.Status;
import com.vcloudairshare.shared.enumeration.VirtualMachineStatus;
import com.vcloudairshare.shared.proxy.VirtualMachineDTO;

public class VirtualMachineItemEditor extends Composite implements Editor<VirtualMachineDTO>{
	  interface Binder extends UiBinder<Widget, VirtualMachineItemEditor> {}
	 
	  interface EditorDriver
		extends
		RequestFactoryEditorDriver<VirtualMachineDTO, VirtualMachineItemEditor> {
}
	  
	  private ClientFactory clientFactory = null;

		private void setClientFactory(final ClientFactory clientFactory) {
			this.clientFactory = clientFactory;
		}

		private ClientFactory getClientFactory() {
			return clientFactory;
		}

		private static final Binder BINDER = GWT.create(Binder.class);
	

	  @UiField InlineLabel airId;
	  @UiField InlineLabel machinename;
	  @UiField VirtualMachineTypeLabel<Integer> machinetype;
	  @UiField StatusLabel<Integer> condition;
	  @UiField InlineLabel currentUserName;
	  
	  
	  @UiField InlineLabel publicIpAddress;
//	  
	  @UiField  Image poweron;
	  @UiField  Image poweroff;
	  @UiField InlineLabel pass;

//
	  @UiField  FlowPanel subPanel;
//	  
	  
	 @UiField Button checkout;
	 
	  public VirtualMachineItemEditor() {
	    initWidget(BINDER.createAndBindUi(this));
	  }
	  @UiHandler("checkout")
		void onClick(ClickEvent e) {
		  if(Status.AVAILABLE.getId() == condition.getValue().intValue()){
			  condition.setValue(Status.UPDATING.getId());
			  getClientFactory().getCommunicationsManager().requestCheckout(airId.getText(), new Boolean(true));
		  }
		  else{
			  condition.setValue(Status.PROVISIONING.getId());
			  getClientFactory().getCommunicationsManager().requestCheckout(airId.getText(), new Boolean(false));
		  }
		  checkout.setEnabled(false);

	  }
	  @UiHandler("poweron")
		void onClickOn(ClickEvent e) {
		  getClientFactory().getCommunicationsManager().requestPower(airId.getText(), VirtualMachineStatus.POWERON);
		 // condition.setValue(Status.UPDATING.getId());
//		  checkout.setEnabled(false);

	  }
	  @UiHandler("poweroff")
		void onClickOff(ClickEvent e) {
		  getClientFactory().getCommunicationsManager().requestPower(airId.getText(), VirtualMachineStatus.POWEROFF);
		//  condition.setValue(Status.UPDATING.getId());
//		  checkout.setEnabled(false);
	  }
	  public void init(ClientFactory clientFactory) {
			setClientFactory(clientFactory);
			
		}
	  public void setup(){
		  if(null != condition && Status.INUSE.getId() == condition.getValue().intValue()){
			  subPanel.setVisible(true);
		  }
		  if(Status.AVAILABLE.getId() == condition.getValue().intValue()){
			  checkout.setText("Checkout");
		  }
		  else{
			  if(currentUserName.getText().equals(getClientFactory().getEntityDepo().getUser().getScreen_name())){
				  checkout.setText("Release");

			  }
			  else{
				  checkout.setVisible(false); 
				  currentUserName.setVisible(true);
				  subPanel.setVisible(false);
			  }
			  
		  }
		  
		  
	  }
	}