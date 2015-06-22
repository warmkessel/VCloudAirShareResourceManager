package com.vcloudairshare.server.communications;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;


public class VCloudAirComm {

	String vchsToken;
	String vchsToken2;
	String theOrg;
	// The serviceListHref to vchs
	String vchsServiceListHref;
	String vchsHostname;
	public String getVchstoken() {
		return this.vchsToken;
	}
	
	public String getTheOrg() {
		return this.theOrg;
	}
	
	public String getVchsToken2() {
		return this.vchsToken2;
	}
	
	public String getVchsServiceListHref() {
		return this.vchsServiceListHref;
	}
	
	public boolean login() {
		//--url https://de-germany-1-16.vchs.vmware.com --vchsversion 5.7 --vcloudversion 5.11
		return login("jr@warmkessel.com", "Overlord2!", "5.7", "5.11", "https://us-california-1-3.vchs.vmware.com");
	}
	
		public boolean login(String vchsUsername, String vchsPassword, String vchsVersion, String vcdVersion, String vchsHostname) {
			this.vchsHostname = vchsHostname;
		URL url = null;
		try {
			url = new URL(Constants.LOGINURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(6000); // 60 Seconds
			conn.setReadTimeout(6000);
			conn.addRequestProperty(Constants.ACCEPT,
					Constants.APPLICATION_JSON_VERSION
							+ vchsVersion);
			conn.addRequestProperty(
					Constants.AUTHORIZATION,
					"Basic "
							+ Base64.encodeBase64String(
									new String(vchsUsername + ":"
											+ vchsPassword).getBytes()));
			conn.setRequestMethod(Constants.POST);
			// normally, 3xx is redirect
			int status = conn.getResponseCode();
			if (status >= 300) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ status);
			}

			Map<String, List<String>> map = conn.getHeaderFields();

			if (map.containsKey(Constants.VCHS_AUTHORIZATION_HEADER2)) {

				System.out.println(map.get(
						Constants.VCHS_AUTHORIZATION_HEADER2).get(0));

				vchsToken2 = map.get(Constants.VCHS_AUTHORIZATION_HEADER2)
						.get(0);

			}
		}catch (ProtocolException pe) {
			// TODO Auto-generated catch block
			pe.printStackTrace();
		}catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException ioe) {
			// TODO Auto-generated catch block
			ioe.printStackTrace();
		}
		url = null;
		try {
			url = new URL(vchsHostname + Constants.SESSION_URL);

			// System.out.println("Bearer " + login2);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(6000); // 60 Seconds
			conn.setReadTimeout(6000);
			conn.addRequestProperty(Constants.ACCEPT,
					Constants.APPLICATION_JSON_VERSION
							+ vchsVersion);
			conn.addRequestProperty(Constants.AUTHORIZATION, "Bearer "
					+ vchsToken2);
			conn.setRequestMethod(Constants.GET);


//			conn.addRequestProperty("Accept", "application/json;version=5.7");
			// conn.addRequestProperty("x-vchs-authorization", login2);
			//conn.addRequestProperty("Authorization", "Bearer " + vchsToken2);
			// conn.addRequestProperty("Authorization", "Bearer " + login2);
//			conn.setRequestMethod("GET");
			System.out.println("Request URL ... " + url);
			int status = conn.getResponseCode();

			if (status >= 300) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ status);
			}
			StringBuffer buff = new StringBuffer();
			buff = buildResponse(buff, conn.getInputStream());
			theOrg = extractOrg(buff.toString(), vchsHostname);
			vchsServiceListHref = vchsHostname + Constants.COMPUTE + extractId(buff.toString(), vchsHostname);
			
			
			if (theOrg.length() == 0) {
				throw new RuntimeException("Failed to Extract OrgName");
			}
		}catch (ProtocolException pe) {
			// TODO Auto-generated catch block
			pe.printStackTrace();
		}catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException ioe) {
			// TODO Auto-generated catch block
			ioe.printStackTrace();
		}
		try {
			url = new URL(vchsHostname + Constants.SESSION);
	
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(6000); // 60 Seconds
			conn.setReadTimeout(6000);
			conn.addRequestProperty(Constants.ACCEPT,
					Constants.APPLICATION_PLUS_XML_VERSION
							+ vcdVersion);
			conn.addRequestProperty(
					Constants.AUTHORIZATION,
					"Basic "
							+ Base64.encodeBase64String(
									new String(vchsUsername + "@" + theOrg + ":"
											+ vchsPassword).getBytes()));
			conn.setRequestMethod(Constants.POST);
			// normally, 3xx is redirect
			int status = conn.getResponseCode();
			if (status >= 300) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ status);
			}
			Map<String, List<String>> map = conn.getHeaderFields();
			if (map.containsKey(Constants.VCD_AUTHORIZATION_HEADER)) {
				vchsToken = map.get(Constants.VCD_AUTHORIZATION_HEADER)
						.get(0);
			}
			
		}catch (ProtocolException pe) {
			// TODO Auto-generated catch block
			pe.printStackTrace();
		}catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException ioe) {
			// TODO Auto-generated catch block
			ioe.printStackTrace();
		}
		return true;

	}
	public static StringBuffer buildResponse(StringBuffer buffer, InputStream is) {

		BufferedInputStream bis = null;

		try {
			// open input stream test.txt for reading purpose.
			// input stream is converted to buffered input stream
			bis = new BufferedInputStream(is);
			System.out.println();
			// read until a single byte is available
			while (bis.available() > 0) {
				// read the byte and convert the integer to character
				char c = (char) bis.read();

				// print the characters
				System.out.print(c);
				;
				buffer.append(c);
			}
		} catch (Exception e) {
			// if any I/O error occurs
			e.printStackTrace();
		} finally {
			// releases any system resources associated with the stream
			if (bis != null)
				try {
					bis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		System.out.println();
		return buffer;
	}
	private String extractId(String origString, String vchsHostname){
		String theReturn = "";
		String theTmp = "";
		String url = vchsHostname + Constants.COMPUTE;
		
		int begin = origString.indexOf(url);
		if(begin >= 0){
			theTmp = origString.substring(begin + url.length(), origString.length());
			begin = theTmp.indexOf("\"");
			theReturn = theTmp.substring(0, begin);
		}
		return theReturn;
	}
	private String extractOrg(String origString, String vchsHostname){
		String theReturn = "";
		String theTmp = "";
		String url = vchsHostname + Constants.ORGNAME;
		
		int begin = origString.indexOf(url);
		if(begin >= 0){
			theTmp = origString.substring(begin + url.length(), origString.length());
			begin = theTmp.indexOf("&");
			theReturn = theTmp.substring(0, begin);
		}
		return theReturn;
	}

	public String getVchsHostname() {
		return vchsHostname;
	}
	
}
