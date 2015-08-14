package com.vcloudairshare.shared.request;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.vcloudairshare.server.datastore.service.VirtualMachineService;
import com.vcloudairshare.shared.enumeration.VirtualHostType;
import com.vcloudairshare.shared.enumeration.Status;
import com.vcloudairshare.shared.proxy.VirtualMachineDTO;

@Service(VirtualMachineService.class)
public interface VirtualMacineRequest extends RequestContext {
	Request<VirtualMachineDTO> findById(Long key);
	Request<List<VirtualMachineDTO>> findByAvialable(int from, int count, VirtualHostType machineType, Status status);
	Request<VirtualMachineDTO> findFirstByAvialable(int from, int count, VirtualHostType machineType, Status status);
}