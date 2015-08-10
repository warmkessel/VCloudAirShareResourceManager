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
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.vcloudairshare.shared.enumeration.DataCenter;

public class VCloudAirComm {
	private static final Logger log = Logger.getLogger(VCloudAirComm.class
			.getName());

	private static VCloudAirComm comm = null;

	public static VCloudAirComm getVCloudAirComm() {
		if (null == comm) {
			VCloudAirComm vca = new VCloudAirComm();
			if (vca.login()) {
				comm = vca;
			} else {
				log.severe("Failed : Login Failed");
				throw new RuntimeException("Failed : Login Failed");
			}
		}
		return comm;
	}

	protected VCloudAirComm() {

	}

	String vchsToken;
	String vchsToken2;
	String theOrg;
	// The serviceListHref to vchs
	String vchsServiceListHref;
	String vchsHostname;
	int readTimeout = 6000;
	int connTimeout = 6000;

	private String getVchsToken() {
		return this.vchsToken;
	}
	private String getVchsToken2() {
		return this.vchsToken2;
	}
	private boolean login() {
		return login("jr@warmkessel.com", "Overlord2!", "5.7", "5.11",
				DataCenter.CAL);
	}

	private boolean login(String vchsUsername, String vchsPassword,
			String vchsVersion, String vcdVersion, DataCenter dc) {
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
			url = new URL(dc + Constants.SESSION_URL);

			// System.out.println("Bearer " + login2);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(6000); // 60 Seconds
			conn.setReadTimeout(6000);
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
			theOrg = extractOrg(buff.toString(), dc);
			vchsServiceListHref = dc + Constants.COMPUTE
					+ extractId(buff.toString(), dc);

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
			url = new URL(dc + Constants.SESSION);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(6000); // 60 Seconds
			conn.setReadTimeout(6000);
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

	private String extractId(String origString, DataCenter dc) {
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

	private String extractOrg(String origString, DataCenter dc) {
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

	private InputStream getData() throws IOException, MalformedURLException {
		return getData(DataCenter.CAL, "/api/compute/api/admin/edgeGateway/",
				"f1ce286e-791f-4603-bbda-c1b76c771dda", "/externalIpUsage");
	}

	private InputStream sendData() throws IOException, MalformedURLException {
		// This should never be run when this is in production
		StringBuffer urlParameters = new StringBuffer();
		urlParameters.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		urlParameters
				.append("<EdgeGatewayServiceConfiguration xmlns=\"http://www.vmware.com/vcloud/v1.5\">");
		urlParameters.append(" <NatService>");
		urlParameters.append("  <IsEnabled>true</IsEnabled>");
		urlParameters.append("  <NatRule>");
		urlParameters.append("   <RuleType>SNAT</RuleType>");
		urlParameters.append("    <IsEnabled>true</IsEnabled>");
		urlParameters.append("     <Id>65537</Id>");
		urlParameters.append("     <GatewayNatRule>");
		urlParameters
				.append("          <Interface href=\"https://us-california-1-3.vchs.vmware.com/api/compute/api/admin/network/088dbe33-f5fe-44c2-bbfe-55115347b165\" name=\"d2p3v29-ext\" type=\"application/vnd.vmware.admin.network+xml\"/>");
		urlParameters
				.append("          <OriginalIp>192.169.109.2</OriginalIp>");
		urlParameters
				.append("          <TranslatedIp>107.189.95.228</TranslatedIp>");
		urlParameters.append("     </GatewayNatRule>");
		urlParameters.append("    </NatRule>");
		urlParameters.append("   <NatRule>");
		urlParameters.append("   <RuleType>DNAT</RuleType>");
		urlParameters.append("   <IsEnabled>true</IsEnabled>");
		urlParameters.append("   <Id>65538</Id>");
		urlParameters.append("   <GatewayNatRule>");
		urlParameters
				.append("        <Interface href=\"https://us-california-1-3.vchs.vmware.com/api/compute/api/admin/network/088dbe33-f5fe-44c2-bbfe-55115347b165\" name=\"d2p3v29-ext\" type=\"application/vnd.vmware.admin.network+xml\"/>");
		urlParameters.append("        <OriginalIp>107.189.95.228</OriginalIp>");
		urlParameters.append("        <OriginalPort>Any</OriginalPort>");
		urlParameters
				.append("        <TranslatedIp>192.168.109.2</TranslatedIp>");
		urlParameters.append("        <TranslatedPort>Any</TranslatedPort>");
		urlParameters.append("        <Protocol>Any</Protocol>");
		urlParameters.append("   </GatewayNatRule>");
		urlParameters.append("  </NatRule>");
		urlParameters.append(" </NatService>");
		urlParameters.append("</EdgeGatewayServiceConfiguration>");

		return sendData(
				DataCenter.CAL,
				"/api/compute/api/admin/edgeGateway/",
				"f1ce286e-791f-4603-bbda-c1b76c771dda",
				"/action/configureServices",
				"application/vnd.vmware.admin.edgeGatewayServiceConfiguration+xml",
				urlParameters.toString());
	}

	public String getDataString(DataCenter dc, String command, String asset,
			String secondCommand) {

		try {
			return buildResponse(getData(dc, command, asset, secondCommand));
		} catch (IOException e) {
			log.severe("Failed : getDataString" + e.getMessage());
		}
		return "";
	}

	public Element getDataElement(DataCenter dc, String command, String asset,
			String secondCommand) {

		try {
			return buildResponseElement(getData(dc, command, asset,
					secondCommand));
		} catch (IOException | ParserConfigurationException | SAXException e) {
			log.severe("Failed : getDataString" + e.getMessage());
		}
		return null;
	}
	
	
	public String sendDataString(DataCenter dc, String command, String asset,
			String secondCommand, String requestProperty, String thePayload) {

		try {
			return buildResponse(sendData(dc, command, asset, secondCommand, requestProperty, thePayload));
		} catch (IOException e) {
			log.severe("Failed : getDataString" + e.getMessage());
		}
		return "";
	}

	public Element sendDataElement(DataCenter dc, String command, String asset,
			String secondCommand, String requestProperty, String thePayload) {

		try {
			return buildResponseElement(sendData(dc, command, asset,
					secondCommand, requestProperty, thePayload));
		} catch (IOException | ParserConfigurationException | SAXException e) {
			log.severe("Failed : getDataString" + e.getMessage());
		}
		return null;
	}

	private InputStream getData(DataCenter dc, String command, String asset,
			String secondCommand) throws IOException {
		URL url = new URL(dc.getURL() + command + asset + secondCommand);
		return getData(url);
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

	// "application/vnd.vmware.admin.edgeGatewayServiceConfiguration+xml"
	private InputStream sendData(DataCenter dc, String command, String asset,
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

	public static void main(String[] args) {
		test();
	}

	public static String test() {
		StringBuffer theReturn = new StringBuffer();
		try {
			VCloudAirComm vca = VCloudAirComm.getVCloudAirComm();
			System.out.println("getData()");
			theReturn.append(buildResponse(vca.getData()));

			System.out.println("sendData()");
			theReturn.append(buildResponse(vca.sendData()));

		} catch (Exception e) {
			// if any I/O error occurs
			e.printStackTrace();
		}
		return theReturn.toString();
	}
}
