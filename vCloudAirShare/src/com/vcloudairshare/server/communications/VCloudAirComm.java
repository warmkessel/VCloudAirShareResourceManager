package com.vcloudairshare.server.communications;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.vcloudairshare.server.datastore.entity.VirtualMachine;
import com.vcloudairshare.server.datastore.service.VirtualMachineService;
import com.vcloudairshare.shared.enumeration.DataCenter;
import com.vcloudairshare.shared.enumeration.VirtualMachineStatus;
import com.vcloudairshare.shared.enumeration.VirtualMachineType;

public class VCloudAirComm {
	private static final Logger log = Logger.getLogger(VCloudAirComm.class
			.getName());

	private static Map<DataCenter, VCloudAirComm> comm = new HashMap<DataCenter, VCloudAirComm>();

	public static VCloudAirComm getVCloudAirComm(DataCenter dc) {
		if (!comm.containsKey(dc)) {
			VCloudAirComm vca = new VCloudAirComm(dc);
			if (vca.login()) {
				comm.put(dc, vca);
			} else {
				log.severe("Failed : Login Failed");
				throw new RuntimeException("Failed : Login Failed");
			}
		}
		return comm.get(dc);
	}

	private DataCenter dc;

	protected VCloudAirComm(DataCenter dc) {
		this.dc = dc;
	}

	String vchsToken;
	String vchsToken2;
	String theOrg;
	// The serviceListHref to vchs
	String vchsServiceListHref;
	String vchsHostname;
	int connTimeout = 1200000;
	int readTimeout = 1200000;

	private String getVchsToken() {
		return this.vchsToken;
	}

	private String getVchsToken2() {
		return this.vchsToken2;
	}

	public DataCenter getDataCenter() {
		return dc;
	}

	private boolean login() {
		return login("jr@warmkessel.com", "Overlord2!", "5.7", "5.11");
	}

	private boolean login(String vchsUsername, String vchsPassword,
			String vchsVersion, String vcdVersion) {
		this.vchsHostname = dc.getURL();

		URL url = null;
		try {
			url = new URL(Constants.LOGINURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(connTimeout); // 60 Seconds
			conn.setReadTimeout(readTimeout);
			conn.addRequestProperty(Constants.ACCEPT,
					Constants.APPLICATION_JSON_VERSION + vchsVersion);
			conn.addRequestProperty(
					Constants.AUTHORIZATION,
					"Basic "
							+ Base64.encodeBase64String(new String(vchsUsername
									+ ":" + vchsPassword).getBytes()));
			conn.setRequestMethod(Constants.POST);
			// normally, 3xx is redirect
			int status = conn.getResponseCode();
			if (status >= 300) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ status);
			}

			Map<String, List<String>> map = conn.getHeaderFields();

			if (map.containsKey(Constants.VCHS_AUTHORIZATION_HEADER2)) {

				// System.out.println(map
				// .get(Constants.VCHS_AUTHORIZATION_HEADER2).get(0));

				vchsToken2 = map.get(Constants.VCHS_AUTHORIZATION_HEADER2).get(
						0);

			}
		} catch (ProtocolException pe) {
			log.severe("Failed : Login Failed ProtocolException"
					+ pe.getMessage());
			pe.printStackTrace();
		} catch (MalformedURLException e) {
			log.severe("Failed : Login Failed MalformedURLException"
					+ e.getMessage());
			e.printStackTrace();
		} catch (IOException ioe) {
			log.severe("Failed : Login Failed IOException" + ioe.getMessage());
			ioe.printStackTrace();
		}
		url = null;
		try {
			url = new URL(dc.getURL() + Constants.SESSION_URL);

			// System.out.println("Bearer " + login2);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(connTimeout); // 20 Seconds
			conn.setReadTimeout(readTimeout);
			conn.addRequestProperty(Constants.ACCEPT,
					Constants.APPLICATION_JSON_VERSION + vchsVersion);
			conn.addRequestProperty(Constants.AUTHORIZATION, "Bearer "
					+ vchsToken2);
			conn.setRequestMethod(Constants.GET);

			// conn.addRequestProperty("Accept",
			// "application/json;version=5.7");
			// conn.addRequestProperty("x-vchs-authorization", login2);
			// conn.addRequestProperty("Authorization", "Bearer " + vchsToken2);
			// conn.addRequestProperty("Authorization", "Bearer " + login2);
			// conn.setRequestMethod("GET");
			// System.out.println("Request URL ... " + url);
			int status = conn.getResponseCode();

			if (status >= 300) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ status);
			}
			StringBuffer buff = new StringBuffer();
			buff.append(buildResponse(conn.getInputStream()));
			theOrg = extractOrg(buff.toString());
			vchsServiceListHref = dc + Constants.COMPUTE
					+ extractId(buff.toString());

			if (theOrg.length() == 0) {
				throw new RuntimeException("Failed to Extract OrgName");
			}
		} catch (ProtocolException pe) {
			log.severe("Failed : Login Failed ProtocolException"
					+ pe.getMessage());
			pe.printStackTrace();
		} catch (MalformedURLException e) {
			log.severe("Failed : Login Failed MalformedURLException"
					+ e.getMessage());
			e.printStackTrace();
		} catch (IOException ioe) {
			log.severe("Failed : Login Failed IOException" + ioe.getMessage());
			ioe.printStackTrace();
		}
		try {
			url = new URL(dc.getURL() + Constants.SESSION);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(connTimeout); // 60 Seconds
			conn.setReadTimeout(readTimeout);
			conn.addRequestProperty(Constants.ACCEPT,
					Constants.APPLICATION_PLUS_XML_VERSION + vcdVersion);
			conn.addRequestProperty(
					Constants.AUTHORIZATION,
					"Basic "
							+ Base64.encodeBase64String(new String(vchsUsername
									+ "@" + theOrg + ":" + vchsPassword)
									.getBytes()));
			conn.setRequestMethod(Constants.POST);
			// normally, 3xx is redirect
			int status = conn.getResponseCode();
			if (status >= 300) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ status);
			}
			Map<String, List<String>> map = conn.getHeaderFields();
			if (map.containsKey(Constants.VCD_AUTHORIZATION_HEADER)) {
				vchsToken = map.get(Constants.VCD_AUTHORIZATION_HEADER).get(0);
			}

		} catch (ProtocolException pe) {
			log.severe("Failed : Login Failed ProtocolException"
					+ pe.getMessage());
			pe.printStackTrace();
		} catch (MalformedURLException e) {
			log.severe("Failed : Login Failed MalformedURLException"
					+ e.getMessage());
			e.printStackTrace();
		} catch (IOException ioe) {
			log.severe("Failed : Login Failed IOException" + ioe.getMessage());
			ioe.printStackTrace();
		}
		return true;

	}

	private static String buildResponse(InputStream is) {
		StringBuffer theReturn = new StringBuffer();
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(is);
			// read until a single byte is available
			while (bis.available() > 0) {
				// read the byte and convert the integer to character
				char c = (char) bis.read();
				// print the characters
				theReturn.append(c);
			}
		} catch (Exception e) {
			// if any I/O error occurs
			e.printStackTrace();
		} finally {
			// releases any system resources associated with the stream
			if (bis != null)
				try {
					bis.close();
				} catch (IOException ioe) {
					log.severe("Failed : buildResponse IOException"
							+ ioe.getMessage());
					ioe.printStackTrace();
				}
		}
		return theReturn.toString();
	}

	private static Element buildResponseElement(InputStream is)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(is);
		// get the first element
		return doc.getDocumentElement();
	}

	private String extractId(String origString) {
		String theReturn = "";
		String theTmp = "";
		String url = vchsHostname + Constants.COMPUTE;

		int begin = origString.indexOf(url);
		if (begin >= 0) {
			theTmp = origString.substring(begin + url.length(),
					origString.length());
			begin = theTmp.indexOf("\"");
			theReturn = theTmp.substring(0, begin);
		}
		return theReturn;
	}

	private String extractOrg(String origString) {
		String theReturn = "";
		String theTmp = "";
		String url = vchsHostname + Constants.ORGNAME;

		int begin = origString.indexOf(url);
		if (begin >= 0) {
			theTmp = origString.substring(begin + url.length(),
					origString.length());
			begin = theTmp.indexOf("&");
			theReturn = theTmp.substring(0, begin);
		}
		return theReturn;
	}

	// private InputStream getData() throws IOException, MalformedURLException {
	// return getData("/api/compute/api/admin/edgeGateway/",
	// "f1ce286e-791f-4603-bbda-c1b76c771dda", "/externalIpUsage");
	// }

	// private InputStream sendData() throws IOException, MalformedURLException
	// {
	// // This should never be run when this is in production
	// StringBuffer urlParameters = new StringBuffer();
	// urlParameters.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	// urlParameters
	// .append("<EdgeGatewayServiceConfiguration xmlns=\"http://www.vmware.com/vcloud/v1.5\">");
	// urlParameters.append(" <NatService>");
	// urlParameters.append("  <IsEnabled>true</IsEnabled>");
	// urlParameters.append("  <NatRule>");
	// urlParameters.append("   <RuleType>SNAT</RuleType>");
	// urlParameters.append("    <IsEnabled>true</IsEnabled>");
	// urlParameters.append("     <Id>65537</Id>");
	// urlParameters.append("     <GatewayNatRule>");
	// urlParameters
	// .append("          <Interface href=\"https://us-california-1-3.vchs.vmware.com/api/compute/api/admin/network/088dbe33-f5fe-44c2-bbfe-55115347b165\" name=\"d2p3v29-ext\" type=\"application/vnd.vmware.admin.network+xml\"/>");
	// urlParameters
	// .append("          <OriginalIp>192.169.109.2</OriginalIp>");
	// urlParameters
	// .append("          <TranslatedIp>107.189.95.228</TranslatedIp>");
	// urlParameters.append("     </GatewayNatRule>");
	// urlParameters.append("    </NatRule>");
	// urlParameters.append("   <NatRule>");
	// urlParameters.append("   <RuleType>DNAT</RuleType>");
	// urlParameters.append("   <IsEnabled>true</IsEnabled>");
	// urlParameters.append("   <Id>65538</Id>");
	// urlParameters.append("   <GatewayNatRule>");
	// urlParameters
	// .append("        <Interface href=\"https://us-california-1-3.vchs.vmware.com/api/compute/api/admin/network/088dbe33-f5fe-44c2-bbfe-55115347b165\" name=\"d2p3v29-ext\" type=\"application/vnd.vmware.admin.network+xml\"/>");
	// urlParameters.append("        <OriginalIp>107.189.95.228</OriginalIp>");
	// urlParameters.append("        <OriginalPort>Any</OriginalPort>");
	// urlParameters
	// .append("        <TranslatedIp>192.168.109.2</TranslatedIp>");
	// urlParameters.append("        <TranslatedPort>Any</TranslatedPort>");
	// urlParameters.append("        <Protocol>Any</Protocol>");
	// urlParameters.append("   </GatewayNatRule>");
	// urlParameters.append("  </NatRule>");
	// urlParameters.append(" </NatService>");
	// urlParameters.append("</EdgeGatewayServiceConfiguration>");
	//
	// return sendData(
	// "/api/compute/api/admin/edgeGateway/",
	// "f1ce286e-791f-4603-bbda-c1b76c771dda",
	// "/action/configureServices",
	// "application/vnd.vmware.admin.edgeGatewayServiceConfiguration+xml",
	// urlParameters.toString());
	// }

	public String deleteDataString(String command, String asset) {

		try {
			return buildResponse(deleteData(command, asset));
		} catch (IOException e) {
			log.severe("Failed : getDataString" + e.getMessage() + " "
					+ buildString(command, asset, ""));
		}
		return "";
	}

	public Element deleteDataElement(String command, String asset) {

		try {
			return buildResponseElement(deleteData(command, asset));
		} catch (IOException | ParserConfigurationException | SAXException e) {
			log.severe("Failed : getDataElement" + e.getMessage() + " "
					+ buildString(command, asset, ""));
		}
		return null;
	}

	public String getDataString(String command, String asset,
			String secondCommand) {

		try {
			return buildResponse(getData(command, asset, secondCommand));
		} catch (IOException e) {
			log.severe("Failed : getDataString" + e.getMessage() + " "
					+ buildString(command, asset, secondCommand));
		}
		return "";
	}

	public Element getDataElement(String command, String asset,
			String secondCommand) {

		try {
			return buildResponseElement(getData(command, asset, secondCommand));
		} catch (IOException | ParserConfigurationException | SAXException e) {
			log.severe("Failed : getDataElement" + e.getMessage() + " "
					+ buildString(command, asset, secondCommand));
		}
		return null;
	}

	public String sendDataString(String command, String asset,
			String secondCommand, String requestProperty, String thePayload) {

		try {
			return buildResponse(sendData(command, asset, secondCommand,
					requestProperty, thePayload));
		} catch (IOException e) {
			log.severe("Failed : getDataString" + e.getMessage() + " "
					+ buildString(command, asset, secondCommand));
		}
		return "";
	}

	public Element sendDataElement(String command, String asset,
			String secondCommand, String requestProperty, String thePayload) {

		try {
			return buildResponseElement(sendData(command, asset, secondCommand,
					requestProperty, thePayload));
		} catch (IOException | ParserConfigurationException | SAXException e) {
			log.severe("Failed : sendDataElement" + e.getMessage() + " "
					+ buildString(command, asset, secondCommand));
		}
		return null;
	}

	private InputStream getData(String command, String asset,
			String secondCommand) throws IOException {
		URL url = new URL(dc.getURL() + command + asset + secondCommand);
		return getData(url);
	}

	private InputStream deleteData(String command, String asset)
			throws IOException {
		URL url = new URL(dc.getURL() + command + asset);
		return deleteData(url);
	}

	private String buildString(String command, String asset,
			String secondCommand) {
		return dc.getURL() + command + asset + secondCommand;
	}

	private InputStream getData(URL url) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(connTimeout); // 60 Seconds
		conn.setReadTimeout(readTimeout);
		conn.addRequestProperty("Accept", "application/*+xml;version=5.11");
		conn.addRequestProperty("x-vcloud-authorization", getVchsToken());
		conn.addRequestProperty("Authorization", "Bearer " + getVchsToken2());
		conn.setRequestMethod("GET");
		// normally, 3xx is redirect
		int status = conn.getResponseCode();
		if (status >= 300) {
			throw new RuntimeException("Failed : HTTP error code : " + status);
		}
		return conn.getInputStream();
	}

	private InputStream deleteData(URL url) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(connTimeout); // 60 Seconds
		conn.setReadTimeout(readTimeout);
		conn.addRequestProperty("Accept", "application/*+xml;version=5.11");
		conn.addRequestProperty("x-vcloud-authorization", getVchsToken());
		conn.addRequestProperty("Authorization", "Bearer " + getVchsToken2());
		conn.setRequestMethod("DELETE");
		// normally, 3xx is redirect
		int status = conn.getResponseCode();
		if (status >= 300) {
			throw new RuntimeException("Failed : HTTP error code : " + status);
		}
		return conn.getInputStream();
	}

	// "application/vnd.vmware.admin.edgeGatewayServiceConfiguration+xml"
	private InputStream sendData(String command, String asset,
			String secondCommand, String requestProperty, String thePayload)
			throws IOException, MalformedURLException {
		URL url = new URL(dc.getURL() + command + asset + secondCommand);
		return sendData(url, requestProperty, thePayload);
	}

	private InputStream sendData(URL url, String requestProperty, String payload)
			throws IOException {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(connTimeout); // 60 Seconds
		conn.setReadTimeout(readTimeout);
		conn.addRequestProperty("Accept", "application/*+xml;version=5.11");
		conn.addRequestProperty("x-vcloud-authorization", getVchsToken());
		conn.addRequestProperty("Authorization", "Bearer " + getVchsToken2());
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);

		byte[] postData = payload.getBytes(StandardCharsets.UTF_8);
		int postDataLength = postData.length;

		conn.setRequestProperty("Content-Type", requestProperty);
		conn.setRequestProperty("charset", "utf-8");
		conn.setRequestProperty("Content-Length",
				Integer.toString(postDataLength));
		conn.setUseCaches(false);
		try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
			wr.write(postData);
		}

		// System.out.println("Request URL ... " + url);

		// normally, 3xx is redirect
		int status = conn.getResponseCode();
		if (status >= 300) {
			throw new RuntimeException("Failed : HTTP error code : " + status);
		}
		// System.out.println("Response Code ... " + status);
		return conn.getInputStream();
	}

	public Boolean findAddress(VirtualMachine vm) {
		log.info("Find Address - "
				+ getDataString("/api/compute/api/admin/edgeGateway/",
						"f1ce286e-791f-4603-bbda-c1b76c771dda",
						"/externalIpUsage"));
		Element element = getDataElement("/api/compute/api/admin/edgeGateway/",
				"f1ce286e-791f-4603-bbda-c1b76c771dda", "/externalIpUsage");
		// log.info("Find Address - Finished");

		NodeList ns = element
				.getElementsByTagName("nws_1_0:EdgeGatewayExternalConnection");
		for (int x = 0; x < ns.getLength(); x++) {
			Element ce = (Element) ns.item(x);

			log.info("isUsedInRule"
					+ Boolean.parseBoolean(ce.getAttribute("isUsedInRule")));
			if (!Boolean.parseBoolean(ce.getAttribute("isUsedInRule"))) {
				log.info("ipAddress" + ce.getAttribute("ipAddress"));

				vm.setPublicIpAddress(ce.getAttribute("ipAddress"));
				return true;
			}
		}
		return false;
	}

	public boolean createRemoteMachine(VirtualMachineType machineType,
			VirtualMachine vm, String name, String Desc) {
		log.info("createRemoteMachine");
		StringBuffer urlParameters = new StringBuffer();
		urlParameters.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		urlParameters.append("<InstantiateVAppTemplateParams");
		urlParameters.append("   xmlns=\"http://www.vmware.com/vcloud/v1.5\"");
		// urlParameters.append("   name=\"").append(name).append("-")
		// .append(new Date().getTime() + "\"");
		urlParameters.append("   name=\"").append(name).append("\"");
		urlParameters.append("   deploy=\"true\"");
		urlParameters.append("   powerOn=\"true\"");
		urlParameters
				.append("   xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		urlParameters
				.append("   xmlns:ovf=\"http://schemas.dmtf.org/ovf/envelope/1\">");
		urlParameters.append("   <Description>").append(Desc)
				.append("</Description>");
		urlParameters.append("   <InstantiationParams>");
		
		
		
//		
//		 urlParameters.append("      <NetworkConfigSection>");
//		 urlParameters.append("         <ovf:Info>Configuration parameters for logical networks</ovf:Info>");
//		 urlParameters.append("         <NetworkConfig\">");
//		 urlParameters.append("         <NetworkConfig  networkName=\"default-routed-network\" href=\"https://us-california-1-3.vchs.vmware.com/api/compute/api/network/3bcd14f0-bbe8-4de3-b4c8-79ad68676866\">");
//		 urlParameters.append("           <Configuration>");
////		 urlParameters.append("               <ParentNetwork");
////		 urlParameters.append("                  href=\"https://us-california-1-3.vchs.vmware.com/api/compute/api/network/3bcd14f0-bbe8-4de3-b4c8-79ad68676866\" />");
//		 urlParameters.append("            </Configuration>");
//		 urlParameters.append("         </NetworkConfig>");
//		 urlParameters.append("      </NetworkConfigSection>");
		
		
		
		// urlParameters.append("      <LeaseSettingsSection");
		// urlParameters.append("         type=\"application/vnd.vmware.vcloud.leaseSettingsSection+xml\">");
		// urlParameters.append("        <ovf:Info>Lease Settings</ovf:Info>");
		// urlParameters.append("         <StorageLeaseInSeconds>172800</StorageLeaseInSeconds>");
		// urlParameters.append("         <StorageLeaseExpiration>2020-04-11T08:08:16.438-07:00</StorageLeaseExpiration>");
		// urlParameters.append("      </LeaseSettingsSection>");
		urlParameters.append("   </InstantiationParams>");
		urlParameters.append("   <Source");
		urlParameters.append("      href=\"").append(dc.getURL())
				.append("/api/compute/api/vAppTemplate/")
				.append(machineType.getPath()).append("\"/>");
		// urlParameters.append("      href=\"https://us-california-1-3.vchs.vmware.com/api/compute/api/vAppTemplate/vappTemplate-5f0be466-278d-4eac-984e-b20ee9954d54\"/>");
		urlParameters.append("   <AllEULAsAccepted>true</AllEULAsAccepted>");
		urlParameters.append("</InstantiateVAppTemplateParams>");

		// log.severe(sendDataString(dc, "/api/compute/api/vdc/",
		// "5c52bf05-28b7-45d3-9dc5-55780db11a42","/action/instantiateVAppTemplate",
		// "application/vnd.vmware.vcloud.instantiateVAppTemplateParams+xml",
		// urlParameters.toString()));

		log.info("Sending Data");

		Element element = sendDataElement(
				"/api/compute/api/vdc/",
				"5c52bf05-28b7-45d3-9dc5-55780db11a42",
				"/action/instantiateVAppTemplate",
				"application/vnd.vmware.vcloud.instantiateVAppTemplateParams+xml",
				urlParameters.toString());
		// https://us-california-1-3.vchs.vmware.com/api/compute/api/vdc/5c52bf05-28b7-45d3-9dc5-55780db11a42/action/instantiateVAppTemplate
		log.info("element extracted " + element.getTagName());

		// extract id
		vm.setAirId(extractId(element));

		// // log.info("extractPass(element) " + extractPass(element));
		//
		// vm.setPass(extractPass(element));
		// // log.info("vm.getPass() " + vm.getPass());

		// Wait until the machine is ready
		getExpectedStatus(vm, VirtualMachineStatus.POWERON);
		// while (!machineReady(vm)) {
		// // log.info("Waiting for the machine to be ready");
		// try {
		// Thread.sleep(10000);
		// } catch (InterruptedException e) {
		// log.severe("InterruptedException " + e.getMessage());
		// e.printStackTrace();
		// }
		// }
		// log.info("vm.getAirId() " + vm.getAirId());
		log.info("extractLocalAddress ");
		if (!extractLocalAddress(vm)) {
			log.severe("No Local Address Found");
			return false;
		}
		log.info("extractRootPass ");

		if (!extractRootPass(vm)) {
			log.severe("No Root Password Found");
			return false;
		}
//		power(vm, VirtualMachineStatus.POWEROFF);

		log.info("finished createRemoteMachine");
		return true;
	}

	public boolean power(VirtualMachine vm, VirtualMachineStatus status) {
		log.info("Power ");
		
		if(getVirtualMachineStatus(vm).equals(status)){
			return true;
		}
		
		if (VirtualMachineStatus.POWERON.equals(status)) {
			log.info("PowerOn ");
			log.info(sendDataString("/api/compute/api/vApp/", vm.getAirId(),
					"/power/action/powerOn",
					"application/vnd.vmware.vcloud.task+xm", ""));

			// element = sendDataElement("/api/compute/api/vApp/",
			// vm.getAirId(), "/power/action/powerOn",
			// "application/vnd.vmware.vcloud.task+xm", "");
			return getExpectedStatus(vm, VirtualMachineStatus.POWERON);

		} else if(VirtualMachineStatus.POWEROFF.equals(status)) {
			log.info("PowerOff ");
			log.info(sendDataString("/api/compute/api/vApp/", vm.getAirId(),
					"/power/action/powerOff",
					"application/vnd.vmware.vcloud.task+xm", ""));
			// element = sendDataElement("/api/compute/api/vApp/",
			// vm.getAirId(), "/power/action/powerOff",
			// "application/vnd.vmware.vcloud.task+xm", "");
			return getExpectedStatus(vm, VirtualMachineStatus.POWEROFF);
		}

		return false;
	}

	public boolean decommission(VirtualMachine vm) {
		log.info("decommission " + vm.getAirId());

		log.info(getDataString("/api/compute/api/vApp/", vm.getAirId(), ""));

		Element element = getDataElement("/api/compute/api/vApp/",
				vm.getAirId(), "");

		
		NodeList ns = element.getElementsByTagName("Tasks");
		if(ns.getLength() > 0){
			try {
				log.info("waiting " + vm.getAirId());

				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return decommission(vm);
		}	
		ns = element.getElementsByTagName("Link");
		for (int x = 0; x < ns.getLength(); x++) {
			Element e = (Element) ns.item(x);
			log.info(e.getAttribute("rel"));

			if ("remove".equals(e.getAttribute("rel"))) {
				log.info("remove" + vm.getAirId());
				deleteDataString("/api/compute/api/vApp/",
						vm.getAirId());
				return true;
			}
			if ("undeploy".equals(e.getAttribute("rel"))) {
				log.info("undeploy" + vm.getAirId());
				sendDataElement("/api/compute/api/vApp/", vm.getAirId(),
						"/action/undeploy",
						"application/vnd.vmware.vcloud.undeployVAppParams+xml",
						buildDecommString());
				return decommission(vm);
				
			}
		}
		return true;
	}

	public boolean updateNAT() {
		// https://us-california-1-3.vchs.vmware.com/api/compute/api/admin/edgeGateway/f1ce286e-791f-4603-bbda-c1b76c771dda/action/configureServices
		// log.info(sendDataString(dc, "/api/compute/api/vdc/",
		// "5c52bf05-28b7-45d3-9dc5-55780db11a42","/action/instantiateVAppTemplate",
		// "application/vnd.vmware.vcloud.instantiateVAppTemplateParams+xml",
		// urlParameters.toString()));
		buildString("/api/compute/api/admin/edgeGateway/",
				"f1ce286e-791f-4603-bbda-c1b76c771dda",
				"/action/configureServices");

		log.info("updateNAT" + buildNatString());

		// Element element = sendDataElement(
		// "/api/compute/api/vdc/",
		// "5c52bf05-28b7-45d3-9dc5-55780db11a42",
		// "/action/instantiateVAppTemplate",
		// "application/vnd.vmware.admin.edgeGatewayServiceConfiguration+xml",
		// buildNatString(vm.getPublicIpAddress(),
		// vm.getPrivateIpAddress()));

		String data = sendDataString(
				"/api/compute/api/admin/edgeGateway/",
				"f1ce286e-791f-4603-bbda-c1b76c771dda",
				"/action/configureServices",
				"application/vnd.vmware.admin.edgeGatewayServiceConfiguration+xml",
				buildNatString());
		log.info("data " + data);
		return true;

	}

	private String buildNatString() {
		StringBuffer urlParameters = new StringBuffer();
		urlParameters.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		urlParameters.append("<EdgeGatewayServiceConfiguration");
		urlParameters.append("   xmlns=\"http://www.vmware.com/vcloud/v1.5\">");
		urlParameters.append("    <NatService>");
		urlParameters.append("                <IsEnabled>true</IsEnabled>");
		int id = 65536;
		List<VirtualMachine> machines = VirtualMachineService.findByIP();

		for (VirtualMachine vm : machines) {
			id = id + 1;

			urlParameters.append("                <NatRule>");
			urlParameters
					.append("                    <RuleType>SNAT</RuleType>");
			urlParameters
					.append("                    <IsEnabled>true</IsEnabled>");
			urlParameters.append("                    <Id>").append(id)
					.append("</Id>");
			urlParameters.append("                    <GatewayNatRule>");
			urlParameters
					.append("                        <Interface href=\"https://us-california-1-3.vchs.vmware.com/api/compute/api/admin/network/088dbe33-f5fe-44c2-bbfe-55115347b165\" name=\"d2p3v29-ext\" type=\"application/vnd.vmware.admin.network+xml\"/>");
			urlParameters.append("                        <OriginalIp>")
					.append(vm.getPrivateIpAddress()).append("</OriginalIp>");
			urlParameters.append("                        <TranslatedIp>")
					.append(vm.getPublicIpAddress()).append("</TranslatedIp>");
			urlParameters.append("                    </GatewayNatRule>");
			urlParameters.append("                </NatRule>");
			urlParameters.append("                <NatRule>");
			urlParameters
					.append("                    <RuleType>DNAT</RuleType>");
			urlParameters
					.append("                    <IsEnabled>true</IsEnabled>");

			id = id + 1;
			urlParameters.append("                    <Id>").append(id)
					.append("</Id>");
			urlParameters.append("                    <GatewayNatRule>");
			urlParameters
					.append("                        <Interface href=\"https://us-california-1-3.vchs.vmware.com/api/compute/api/admin/network/088dbe33-f5fe-44c2-bbfe-55115347b165\" name=\"d2p3v29-ext\" type=\"application/vnd.vmware.admin.network+xml\"/>");
			urlParameters.append("                        <OriginalIp>")
					.append(vm.getPublicIpAddress()).append("</OriginalIp>");
			urlParameters
					.append("                        <OriginalPort>Any</OriginalPort>");
			urlParameters.append("                        <TranslatedIp>")
					.append(vm.getPrivateIpAddress()).append("</TranslatedIp>");
			urlParameters
					.append("                        <TranslatedPort>Any</TranslatedPort>");
			urlParameters
					.append("                        <Protocol>Any</Protocol>");
			urlParameters.append("                    </GatewayNatRule>");
			urlParameters.append("                </NatRule>");

		}
		urlParameters.append("            </NatService>");
		urlParameters.append("</EdgeGatewayServiceConfiguration>");

		return urlParameters.toString();
	}

	private static String buildDecommString() {
		StringBuffer urlParameters = new StringBuffer();

		urlParameters.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		urlParameters.append("<UndeployVAppParams");
		urlParameters.append("   xmlns=\"http://www.vmware.com/vcloud/v1.5\">");
		urlParameters
				.append("   <UndeployPowerAction>powerOff</UndeployPowerAction>");
		urlParameters.append("</UndeployVAppParams>");

		return urlParameters.toString();
	}

	public boolean extractLocalAddress(VirtualMachine vm) {
		Element element = getDataElement("/api/compute/api/vApp/",
				vm.getAirId(), "/ovf");

		NodeList ns = element.getElementsByTagName("vcloud:IpAddress");
		for (int b = 0; b < ns.getLength(); b++) {
			Element ce5 = (Element) ns.item(b);
			log.info("ce5.getNodeValue()" + ce5.getTextContent());
			if (ce5.getTextContent() != null
					&& ce5.getTextContent().length() > 0) {
				vm.setPrivateIpAddress(ce5.getTextContent());
				// log.info("end " + vm.getPrivateIpAddress());
				return true;
			}
		}
		return false;
	}

	public boolean extractRootPass(VirtualMachine vm) {
		Element element = getDataElement("/api/compute/api/vApp/",
				vm.getAirId(), "/ovf");

		NodeList ns = element.getElementsByTagName("vcloud:AdminPassword");
		for (int b = 0; b < ns.getLength(); b++) {
			Element ce5 = (Element) ns.item(b);
			if (ce5.getTextContent() != null
					&& ce5.getTextContent().length() > 0) {
				vm.setPass(ce5.getTextContent());
				// log.info("end " + vm.getPrivateIpAddress());
				return true;
			}
		}
		return false;
	}

	private String extractId(Element element) {
		String theHref = element.getAttribute("href");
		// "https://us-california-1-3.vchs.vmware.com/api/compute/api/vApp/vapp-c0401bcf-d08b-409a-bdc8-50c104bb6706
		log.info("theHref " + theHref);

		int loc = theHref.lastIndexOf("/");
		if (loc >= 0) {
			log.info("theHref.substring(loc + 1, theHref.length()) "
					+ theHref.substring(loc + 1, theHref.length()));

			return theHref.substring(loc + 1, theHref.length());
		} else {
			return "";
		}

	}

	// private String extractPass(Element element) {
	// //
	// "https://us-california-1-3.vchs.vmware.com/api/compute/api/vApp/vapp-c0401bcf-d08b-409a-bdc8-50c104bb6706
	// //
	// https://us-california-1-3.vchs.vmware.com/api/compute/api/vAppTemplate/vappTemplate-f24a7458-b7b3-48a3-87ab-2965345c93e1
	// log.info("extractPass");
	//
	// NodeList ns = element.getElementsByTagName("AdminPassword");
	//
	// log.info("ns.getLength()" + ns.getLength());
	//
	// for (int b = 0; b < ns.getLength(); b++) {
	// log.info("b" + b);
	// Element ce = (Element) ns.item(b);
	// log.info("ce.getNodeValue()" + ce.getTextContent());
	// if (ce.getTextContent() != null && ce.getTextContent().length() > 0) {
	// return ce.getTextContent();
	// }
	// }
	// return "";
	// }

	public Boolean getExpectedStatus(VirtualMachine vm,
			VirtualMachineStatus status) {
		for (int x = 0; x < 100; x++) {
			VirtualMachineStatus theState = getVirtualMachineStatus(vm);
			if (status.equals(theState)) {
				log.info("Finished");
				return true;
			}
			log.info("Waiting for " + status.toString() + " Current:" + theState.toString());
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				log.severe("InterruptedException " + e.getMessage());
				e.printStackTrace();
			}
		}
		log.info("Failed to get the expected result");
		return false;
	}

	public VirtualMachineStatus getVirtualMachineStatus(VirtualMachine vm) {
		Element element = getDataElement("/api/compute/api/vApp/",
				vm.getAirId(), "");
		return VirtualMachineStatus.fromId(element.getAttribute("status"));
	}

	public static void main(String[] args) {
		try {
			log.info("Started Test");
			// log.info("getData()");
			// log.info(buildResponse(vca.getData()));
			// VIR
			// VCloudAirComm vca =
			// VCloudAirComm.getVCloudAirComm(DataCenter.VIR);

			// log.info("getDataString" + vca.getDataString("/api/org","", ""));
			// log.info("getDataString" +
			// vca.getDataString("/api/compute/api/org/","ed19f623-6b78-41ef-8e5b-50b461e13149",
			// ""));
			// log.info("getDataString" +
			// vca.getDataString("/api/compute/api/vdc/","c6e2e992-b077-4795-92aa-d1facb3a1e95",
			// ""));
			// log.info("getDataString" +
			// vca.getDataString("/api/compute/api/vApp/","vapp-e41c1c14-594f-4026-98ae-5e2545c07755",
			// ""));
			// log.info("getDataString" +
			// vca.sendDataString("/api/compute/api/vApp/","vapp-e41c1c14-594f-4026-98ae-5e2545c07755",
			// "/power/action/powerOff",
			// "application/vnd.vmware.vcloud.task+xm", ""));

			// https://us-virginia-1-4.vchs.vmware.com
			// CAL
			VCloudAirComm vca = VCloudAirComm.getVCloudAirComm(DataCenter.CAL);
			
//			 log.info("getDataString"
//			 + vca.getDataString("/api/compute/api/network/",
//			 "3bcd14f0-bbe8-4de3-b4c8-79ad68676866", ""));
			
			 log.info("getDataString"
					 + vca.getDataString("/api/compute/api/vdc/",
					 "5c52bf05-28b7-45d3-9dc5-55780db11a42", ""));
			 
			 
//			 VirtualMachine vm = new VirtualMachine();
//			 vm.setDatacenter(DataCenter.getDefault().getId());
//			 
//			 vca.createRemoteMachine(VirtualMachineType.getDefault(),vm, "test" + new Date(), "desc");
				 
				 
				 //createRemoteMachine
			 

//			 log.info(vca.getDataString("/api/compute/api/vApp/",
//			 "vapp-11b7149d-daaf-4677-a77f-64fab57b153e", ""));

//			 log.info(vca.deleteDataString("/api/compute/api/vApp/",
//			 "vapp-11b7149d-daaf-4677-a77f-64fab57b153e"));

			// log.info("getDataString" +
			// vca.getDataString("/api/compute/api/vApp/","vapp-747ff0ea-29b0-490c-bed7-84267ce38e75",
			// ""));
			// /api/compute/api/vApp/vm-3e699cd1-cd77-4c94-8434-105fc318cd79/power/action/powerOff
			// log.info("getDataString" +
			// vca.getDataString("/api/compute/api/vApp/","vm-3e699cd1-cd77-4c94-8434-105fc318cd79",
			// "/power/action/powerOff"));
			// log.info("getDataString" +
			// vca.getDataString("/api/compute/api/vApp/","vm-3e699cd1-cd77-4c94-8434-105fc318cd79",
			// "/power/action/powerOn"));

			// GER
			// VCloudAirComm vca =
			// VCloudAirComm.getVCloudAirComm(DataCenter.GER);

			// log.info("getDataString" + vca.getDataString("/api/org","", ""));
			// log.info("getDataString" +
			// vca.getDataString("/api/compute/api/org/","658f5497-78d0-469b-8ad6-1206148cb0bf",
			// ""));
			// log.info("getDataString" +
			// vca.getDataString("/api/compute/api/vdc/","a29e6d09-022b-4a07-93e8-b58705099cd1",
			// ""));
			// log.info("getDataString" +
			// vca.getDataString("/api/compute/api/vApp/","vapp-18bc7a3c-255e-40a0-bab0-ca7aaf400c4d",
			// ""));
			// log.info("getDataString" +
			// vca.getDataString("/api/compute/api/vApp/","vapp-18bc7a3c-255e-40a0-bab0-ca7aaf400c4d",
			// "/power/action/powerOff"));

			// Working Test

			// VCloudAirComm vca =
			// VCloudAirComm.getVCloudAirComm(DataCenter.CAL);

			// log.info(vca.getDataString("/api/compute/api/vApp/",
			// "vapp-11b7149d-daaf-4677-a77f-64fab57b153e",
			// ""
			// ));

			// log.info(vca.sendDataString("/api/compute/api/vApp/",
			// "vapp-11b7149d-daaf-4677-a77f-64fab57b153e",
			// "/action/undeploy",
			// "application/vnd.vmware.vcloud.undeployVAppParams+xml",
			// buildDecommString()));

			// log.info(vca.getDataString("/api/compute/api/vApp/",
			// "vm-ae69ef22-a2ee-4473-9a66-584a323e16e8",
			// ""
			// ));
		} catch (Exception e) {
			// if any I/O error occurs
			e.printStackTrace();
		}
	}
}
