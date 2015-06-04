package com.vcloudairshare.client.widget;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.vcloudairshare.client.ClientFactory;
import com.vcloudairshare.client.event.VirtualMachinesReceivedEvent;
import com.vcloudairshare.client.event.VirtualMachinesReceivedEventHandler;
import com.vcloudairshare.shared.enumeration.MachineType;
import com.vcloudairshare.shared.enumeration.Status;
import com.vcloudairshare.shared.proxy.VirtualMachineDTO;

public class VirtualMachineItemView extends Composite {

	interface Binder extends UiBinder<Widget, VirtualMachineItemView> {
	}

	interface EditorDriver
			extends
			RequestFactoryEditorDriver<VirtualMachineDTO, VirtualMachineItemEditor> {
	}

	private final Binder BINDER = GWT.create(Binder.class);

	private final EditorDriver editorDriver = GWT.create(EditorDriver.class);

	private static VirtualMachineItemViewUiBinder uiBinder = GWT
			.create(VirtualMachineItemViewUiBinder.class);

	interface VirtualMachineItemViewUiBinder extends
			UiBinder<Widget, VirtualMachineItemView> {
	}

	// public VirtualMachineItemView() {
	// initWidget(uiBinder.createAndBindUi(this));
	// }

	// @UiField
	// VirtualMachineItemEditor editor;

	@UiField
	FlowPanel machinePanel;

	private ClientFactory clientFactory = null;

	private void setClientFactory(final ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	private ClientFactory getClientFactory() {
		return clientFactory;
	}

	public void init(ClientFactory clientFactory) {
		setClientFactory(clientFactory);
	}

	public void registerHandler() {

		getClientFactory().getEventBus().addHandler(
				VirtualMachinesReceivedEvent.TYPE,
				new VirtualMachinesReceivedEventHandler() {
					@Override
					public void onMessageReceived(
							VirtualMachinesReceivedEvent event) {

						machinePanel.clear();

						List<VirtualMachineDTO> theMachines = getClientFactory()
								.getEntityDepo().getVm();
						for (VirtualMachineDTO theVM : theMachines) {

							VirtualMachineItemEditor editor = new VirtualMachineItemEditor();
							editor.init(getClientFactory());
							editorDriver.initialize(getClientFactory()
									.getEventBus(), getClientFactory()
									.getRequestFactory(), editor);
							editorDriver.display(theVM);
							machinePanel.add(editor);
							editor.setup();
						}

					}
				});
	}

	public VirtualMachineItemView() {
		initWidget(BINDER.createAndBindUi(this));
	}

	public void display() {
		getClientFactory().getCommunicationsManager().requestVirtualMacines(0,
				10, MachineType.VCLOUDAIR, Status.APPROVED);
		registerHandler();
	}
}
