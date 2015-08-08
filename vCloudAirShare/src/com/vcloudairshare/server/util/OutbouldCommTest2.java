package com.vcloudairshare.server.util;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class OutbouldCommTest2 {
	public static String login = "";
	public static String login2 = "";

	public static void main(String[] args) {
		test();
	}

	public static String test() {
		StringBuffer theReturn = new StringBuffer();
		try {
			System.out.println("Request 1");
			buildResponse(theReturn, request1());

			System.out.println("Request 2");
			buildResponse(theReturn, request2());

			System.out.println("Request 3");
			buildResponse(theReturn, request3());

			System.out.println("Request 4");
			buildResponse(theReturn, request4());

			System.out.println("Request 5");

			buildResponse(theReturn, request5());
			System.out.println("Request 6");

			buildResponse(theReturn, request6());
			 System.out.println("Request 7");
			
			 buildResponse(theReturn, request7());
			 System.out.println("Request 7a");
				
			 buildResponse(theReturn, request7a());
			 System.out.println("Request 7b");
				
			 buildResponse(theReturn, request7b());
			//System.out.println("Request 8");

			//buildResponse(theReturn, request8());

			 System.out.println("Request 9");
			 buildResponse(theReturn, request9());
			// buildResponse2(theReturn, request9());
			 
			 System.out.println("Request 10");
			 buildResponse(theReturn, request10());
			  
			 
		} catch (Exception e) {
			// if any I/O error occurs
			e.printStackTrace();
		}
		return theReturn.toString();
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

	public static StringBuffer buildResponse2(StringBuffer buffer,
			InputStream is) {

		BufferedInputStream bis = null;

		// try {
		// // open input stream test.txt for reading purpose.
		// // input stream is converted to buffered input stream
		// bis = new BufferedInputStream(is);
		// System.out.println();
		// // read until a single byte is available
		// while (bis.available() > 0) {
		// // read the byte and convert the integer to character
		// char c = (char) bis.read();
		//
		// // print the characters
		// System.out.print(c);
		// ;
		// buffer.append(c);
		// }
		// } catch (Exception e) {
		// // if any I/O error occurs
		// e.printStackTrace();
		// } finally {
		// // releases any system resources associated with the stream
		// if (bis != null)
		// try {
		// bis.close();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			// use the factory to create a documentbuilder
			DocumentBuilder builder = factory.newDocumentBuilder();

			Document doc = builder.parse(is);

			// get the first element
			Element element = doc.getDocumentElement();

			// get all child nodes

			NodeList nodes = element.getElementsByTagName("Owner");

			// print the text content of each child
			for (int i = 0; i < nodes.getLength(); i++) {
				System.out.println("" + nodes.item(i).getTextContent());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.out.println();
		return buffer;
	}

	private static InputStream request3() throws IOException {
		URL url = null;
		try {
			url = new URL(
			// "https://us-virginia-1-4.vchs.vmware.com/api/compute/api/sessions");
					"https://us-california-1-3.vchs.vmware.com/api/compute/api/sessions");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(60000); // 60 Seconds
		conn.setReadTimeout(60000);
		conn.addRequestProperty("Accept", "application/*+xml;version=5.11");
		conn.addRequestProperty(
				"Authorization",
				"Basic "
						+ Base64.encodeBase64String(new String(
								"jr@warmkessel.com@a3c5bb97-7a7b-4055-97d5-8a040a14ee2e:Overlord2!")
								.getBytes()));
		// ce962f03-ae78-4d80-b217-8c363889921b
		// 0ba27faa-5486-42cc-acc6-0d521ec578cd
		// System.out.println("Basic " +
		// Base64.encodeString("jrwarmkessel@gmail.com:Overlord2!"));
		System.out
				.println("Basic "
						+ Base64.encodeBase64String(new String(
								"jr@warmkessel.com@ce962f03-ae78-4d80-b217-8c363889921b:Overlord2!")
								.getBytes()));

		conn.setRequestMethod("POST");
		System.out.println("Request URL ... " + url);

		// normally, 3xx is redirect
		int status = conn.getResponseCode();

		System.out.println("Response Code ... " + status);

		Map<String, List<String>> map = conn.getHeaderFields();
		// for(String s : map.keySet()){
		// System.out.println(s);
		// }

		if (map.containsKey("x-vcloud-authorization")) {
			// System.out.println("map.get(\"x-vchs-authorization\");" +
			// map.get("x-vchs-authorization"));

			// System.out.println("!!!!"+
			// map.get("x-vcloud-authorization").get(0));

			login = map.get("x-vcloud-authorization").get(0);
			// login = processString(map.get("x-vchs-authorization").get(0));

		}

		return conn.getInputStream();
	}

	private static InputStream request1() throws IOException {
		URL url = null;
		try {
			url = new URL("https://vca.vmware.com/api/iam/login");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(60000); // 60 Seconds
		conn.setReadTimeout(60000);
		conn.addRequestProperty("Accept", "application/json;version=5.7");
		conn.addRequestProperty(
				"Authorization",
				"Basic "
						+ Base64.encodeBase64String(new String(
								"jr@warmkessel.com:Overlord2!").getBytes()));
		// System.out.println("Basic " +
		// Base64.encodeString("jrwarmkessel@gmail.com:Overlord2!"));
		System.out.println("Basic "
				+ Base64.encodeBase64String(new String(
						"jr@warmkessel.com:Overlord2!").getBytes()));

		conn.setRequestMethod("POST");
		System.out.println("Request URL ... " + url);

		// normally, 3xx is redirect
		int status = conn.getResponseCode();

		System.out.println("Response Code ... " + status);

		Map<String, List<String>> map = conn.getHeaderFields();

		if (map.containsKey("vchs-authorization")) {

			System.out.println(map.get("vchs-authorization").get(0));

			login2 = map.get("vchs-authorization").get(0);

		}

		return conn.getInputStream();
	}

	// private static String processString(String theString) {
	// if (theString.indexOf(",") >= 0) {
	// theString = theString.substring(0, theString.indexOf(",") - 1);
	// }
	//
	// return theString;
	// }

	private static InputStream request4() throws IOException {
		URL url = null;
		try {
			url = new URL("https://us-california-1-3.vchs.vmware.com/api/org");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("Bearer " + login2);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(60000); // 60 Seconds
		conn.setReadTimeout(60000);
		conn.addRequestProperty("Accept", "application/*+xml;version=5.11");
		conn.addRequestProperty("x-vcloud-authorization", login);
		// conn.addRequestProperty("Authorization", "Bearer " + login);
		conn.addRequestProperty("Authorization", "Bearer " + login2);
		conn.setRequestMethod("GET");
		System.out.println("Request URL ... " + url);

		// normally, 3xx is redirect
		int status = conn.getResponseCode();

		System.out.println("Response Code ... " + status);
		return conn.getInputStream();
	}

	private static InputStream request2() throws IOException {
		URL url = null;
		try {
			url = new URL(
			// "https://us-virginia-1-4.vchs.vmware.com/api/sc/instances");
					"https://us-california-1-3.vchs.vmware.com/api/sc/instances");

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("Bearer " + login2);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(60000); // 60 Seconds
		conn.setReadTimeout(60000);
		conn.addRequestProperty("Accept", "application/json;version=5.7");
		// conn.addRequestProperty("x-vchs-authorization", login2);
		conn.addRequestProperty("Authorization", "Bearer " + login2);
		// conn.addRequestProperty("Authorization", "Bearer " + login2);
		conn.setRequestMethod("GET");
		System.out.println("Request URL ... " + url);

		// normally, 3xx is redirect
		int status = conn.getResponseCode();

		System.out.println("Response Code ... " + status);
		return conn.getInputStream();
	}

	private static InputStream request5() throws IOException {
		URL url = null;
		try {
			url = new URL(
			// "https://us-virginia-1-4.vchs.vmware.com/api/compute/api/org/ed19f623-6b78-41ef-8e5b-50b461e13149");
					"https://us-california-1-3.vchs.vmware.com/api/compute/api/org/c42b525f-720e-4f90-aba9-256d5e60a57b");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println("Bearer " + login2);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(60000); // 60 Seconds
		conn.setReadTimeout(60000);
		conn.addRequestProperty("Accept", "application/*+xml;version=5.11");
		conn.addRequestProperty("x-vcloud-authorization", login);
		// conn.addRequestProperty("Authorization", "Bearer " + login);
		conn.addRequestProperty("Authorization", "Bearer " + login2);
		conn.setRequestMethod("GET");
		System.out.println("Request URL ... " + url);

		// normally, 3xx is redirect
		int status = conn.getResponseCode();

		System.out.println("Response Code ... " + status);
		return conn.getInputStream();
	}

	private static InputStream request6() throws IOException {
		URL url = null;
		try {
			url = new URL(
			// "https://us-virginia-1-4.vchs.vmware.com/api/compute/api/catalog/c6e2e992-b077-4795-92aa-d1facb3a1e95");
					"https://us-california-1-3.vchs.vmware.com/api/compute/api/catalog/64736b28-e508-482b-8438-a0ba33654c97");

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println("Bearer " + login2);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(60000); // 60 Seconds
		conn.setReadTimeout(60000);
		conn.addRequestProperty("Accept", "application/*+xml;version=5.11");
		conn.addRequestProperty("x-vcloud-authorization", login);
		// conn.addRequestProperty("Authorization", "Bearer " + login);
		conn.addRequestProperty("Authorization", "Bearer " + login2);
		conn.setRequestMethod("GET");
		System.out.println("Request URL ... " + url);

		// normally, 3xx is redirect
		int status = conn.getResponseCode();

		System.out.println("Response Code ... " + status);
		return conn.getInputStream();
	}

	private static InputStream request7() throws IOException {
			URL url = null;
			try {
				url =  new URL(
//						"https://us-virginia-1-4.vchs.vmware.com/api/compute/api/vdc/c6e2e992-b077-4795-92aa-d1facb3a1e95");
							//"https://us-california-1-3.vchs.vmware.com/api/compute/api/vdc/5c52bf05-28b7-45d3-9dc5-55780db11a42");
//						"https://us-california-1-3.vchs.vmware.com/api/compute/api/vdcTemplates");
//				"https://us-california-1-3.vchs.vmware.com/api/compute/api/catalog/64736b28-e508-482b-8438-a0ba33654c97");
//"https://us-california-1-3.vchs.vmware.com/api/compute/api/vdc/5c52bf05-28b7-45d3-9dc5-55780db11a42");	
						"https://us-california-1-3.vchs.vmware.com/api/compute/api/network/3bcd14f0-bbe8-4de3-b4c8-79ad68676866");
///admin/vdc/id
				
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			//System.out.println("Bearer " + login2);

					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setConnectTimeout(60000); // 60 Seconds
					conn.setReadTimeout(60000);
					conn.addRequestProperty("Accept", "application/*+xml;version=5.11");
					 conn.addRequestProperty("x-vcloud-authorization", login);
//					conn.addRequestProperty("Authorization", "Bearer " + login);
					 conn.addRequestProperty("Authorization", "Bearer " + login2);
					conn.setRequestMethod("GET");
					System.out.println("Request URL ... " + url);

					// normally, 3xx is redirect
					int status = conn.getResponseCode();

					System.out.println("Response Code ... " + status);
					return conn.getInputStream();
		}

	private static InputStream request7a() throws IOException {
		URL url = null;
		try {
			url =  new URL(
//					"https://us-virginia-1-4.vchs.vmware.com/api/compute/api/vdc/c6e2e992-b077-4795-92aa-d1facb3a1e95");
						//"https://us-california-1-3.vchs.vmware.com/api/compute/api/vdc/5c52bf05-28b7-45d3-9dc5-55780db11a42");
//					"https://us-california-1-3.vchs.vmware.com/api/compute/api/vdcTemplates");
//			"https://us-california-1-3.vchs.vmware.com/api/compute/api/catalog/64736b28-e508-482b-8438-a0ba33654c97");
//"https://us-california-1-3.vchs.vmware.com/api/compute/api/vdc/5c52bf05-28b7-45d3-9dc5-55780db11a42");	
					"https://us-california-1-3.vchs.vmware.com/api/compute/api/vAppTemplate/vappTemplate-f24a7458-b7b3-48a3-87ab-2965345c93e1");
///admin/vdc/id
			
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		//System.out.println("Bearer " + login2);

				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(60000); // 60 Seconds
				conn.setReadTimeout(60000);
				conn.addRequestProperty("Accept", "application/*+xml;version=5.11");
				 conn.addRequestProperty("x-vcloud-authorization", login);
//				conn.addRequestProperty("Authorization", "Bearer " + login);
				 conn.addRequestProperty("Authorization", "Bearer " + login2);
				conn.setRequestMethod("GET");
				System.out.println("Request URL ... " + url);

				// normally, 3xx is redirect
				int status = conn.getResponseCode();

				System.out.println("Response Code ... " + status);
				return conn.getInputStream();
	}
	private static InputStream request7b() throws IOException {
		URL url = null;
		try {
			url =  new URL(
//					"https://us-virginia-1-4.vchs.vmware.com/api/compute/api/vdc/c6e2e992-b077-4795-92aa-d1facb3a1e95");
						//"https://us-california-1-3.vchs.vmware.com/api/compute/api/vdc/5c52bf05-28b7-45d3-9dc5-55780db11a42");
//					"https://us-california-1-3.vchs.vmware.com/api/compute/api/vdcTemplates");
//			"https://us-california-1-3.vchs.vmware.com/api/compute/api/catalog/64736b28-e508-482b-8438-a0ba33654c97");
//"https://us-california-1-3.vchs.vmware.com/api/compute/api/vdc/5c52bf05-28b7-45d3-9dc5-55780db11a42");	
					"https://us-california-1-3.vchs.vmware.com/api/compute/api/network/3bcd14f0-bbe8-4de3-b4c8-79ad68676866");
///admin/vdc/id
			
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		//System.out.println("Bearer " + login2);

				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(60000); // 60 Seconds
				conn.setReadTimeout(60000);
				conn.addRequestProperty("Accept", "application/*+xml;version=5.11");
				 conn.addRequestProperty("x-vcloud-authorization", login);
//				conn.addRequestProperty("Authorization", "Bearer " + login);
				 conn.addRequestProperty("Authorization", "Bearer " + login2);
				conn.setRequestMethod("GET");
				System.out.println("Request URL ... " + url);

				// normally, 3xx is redirect
				int status = conn.getResponseCode();

				System.out.println("Response Code ... " + status);
				return conn.getInputStream();
	}
	private static InputStream request7c() throws IOException {
		URL url = null;
		try {
			url =  new URL(
//					"https://us-virginia-1-4.vchs.vmware.com/api/compute/api/vdc/c6e2e992-b077-4795-92aa-d1facb3a1e95");
						//"https://us-california-1-3.vchs.vmware.com/api/compute/api/vdc/5c52bf05-28b7-45d3-9dc5-55780db11a42");
//					"https://us-california-1-3.vchs.vmware.com/api/compute/api/vdcTemplates");
//			"https://us-california-1-3.vchs.vmware.com/api/compute/api/catalog/64736b28-e508-482b-8438-a0ba33654c97");
//"https://us-california-1-3.vchs.vmware.com/api/compute/api/vdc/5c52bf05-28b7-45d3-9dc5-55780db11a42");	
					"https://us-california-1-3.vchs.vmware.com/api/compute/api/network/3bcd14f0-bbe8-4de3-b4c8-79ad68676866");
///admin/vdc/id
			
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		//System.out.println("Bearer " + login2);

				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(60000); // 60 Seconds
				conn.setReadTimeout(60000);
				conn.addRequestProperty("Accept", "application/*+xml;version=5.11");
				 conn.addRequestProperty("x-vcloud-authorization", login);
//				conn.addRequestProperty("Authorization", "Bearer " + login);
				 conn.addRequestProperty("Authorization", "Bearer " + login2);
				conn.setRequestMethod("GET");
				System.out.println("Request URL ... " + url);

				// normally, 3xx is redirect
				int status = conn.getResponseCode();

				System.out.println("Response Code ... " + status);
				return conn.getInputStream();
	}
	private static InputStream request8() throws IOException {
		URL url = null;
		try {
			url = new URL(
			// "https://us-virginia-1-4.vchs.vmware.com/api/compute/api/vApp/vapp-8ac36b2e-9df4-4718-a9a7-59ff7d239250");
			// "https://us-california-1-3.vchs.vmware.com/api/compute/api/vApp/vapp-f8c1b7c3-1ede-4d51-b4e9-4126093bc59e");
					"https://us-california-1-3.vchs.vmware.com/api/compute/api/vdc/5c52bf05-28b7-45d3-9dc5-55780db11a42/action/instantiateVAppTemplate");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println("Bearer " + login2);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(60000); // 60 Seconds
		conn.setReadTimeout(60000);
		conn.addRequestProperty("Accept", "application/*+xml;version=5.11");
		conn.addRequestProperty("x-vcloud-authorization", login);
		conn.addRequestProperty("Authorization", "Bearer " + login2);
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		StringBuffer urlParameters = new StringBuffer();
		urlParameters.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		urlParameters.append("<InstantiateVAppTemplateParams");
		urlParameters.append("   xmlns=\"http://www.vmware.com/vcloud/v1.5\"");
		urlParameters.append("   name=\"Linux FTP server " + new Date().getTime() + "\"");
		urlParameters.append("   deploy=\"true\"");
		urlParameters.append("   powerOn=\"true\"");
		urlParameters.append("   xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		urlParameters.append("   xmlns:ovf=\"http://schemas.dmtf.org/ovf/envelope/1\">");
		urlParameters.append("   <Description>Example FTP Server</Description>");
		urlParameters.append("   <InstantiationParams>");
//		urlParameters.append("      <NetworkConfigSection>");
//		urlParameters.append("         <ovf:Info>Configuration parameters for logical networks</ovf:Info>");
//		urlParameters.append("         <NetworkConfig\">");
//		urlParameters.append("         <NetworkConfig  networkName=\"default-routed-network\">");
//		urlParameters.append("           <Configuration>");
//		urlParameters.append("               <ParentNetwork");
//		urlParameters.append("                  href=\"https://us-california-1-3.vchs.vmware.com/api/compute/api/network/3bcd14f0-bbe8-4de3-b4c8-79ad68676866\" />");
//		urlParameters.append("            </Configuration>");
//		urlParameters.append("         </NetworkConfig>");
//		urlParameters.append("      </NetworkConfigSection>");
//		urlParameters.append("      <LeaseSettingsSection");
//		urlParameters.append("         type=\"application/vnd.vmware.vcloud.leaseSettingsSection+xml\">");
//		urlParameters.append("        <ovf:Info>Lease Settings</ovf:Info>");
//		urlParameters.append("         <StorageLeaseInSeconds>172800</StorageLeaseInSeconds>");
//		urlParameters.append("         <StorageLeaseExpiration>2020-04-11T08:08:16.438-07:00</StorageLeaseExpiration>");
//		urlParameters.append("      </LeaseSettingsSection>");
		urlParameters.append("   </InstantiationParams>");
		urlParameters.append("   <Source");
			urlParameters.append("      href=\"https://us-california-1-3.vchs.vmware.com/api/compute/api/vAppTemplate/vappTemplate-f24a7458-b7b3-48a3-87ab-2965345c93e1\"/>");
//			urlParameters.append("      href=\"https://us-california-1-3.vchs.vmware.com/api/compute/api/vAppTemplate/vappTemplate-5f0be466-278d-4eac-984e-b20ee9954d54\"/>");
		urlParameters.append("   <AllEULAsAccepted>true</AllEULAsAccepted>");
		urlParameters.append("</InstantiateVAppTemplateParams>");		
		byte[] postData = urlParameters.toString().getBytes(StandardCharsets.UTF_8);
		int postDataLength = postData.length;

		conn.setRequestProperty("Content-Type",
				"application/vnd.vmware.vcloud.instantiateVAppTemplateParams+xml");
		conn.setRequestProperty("charset", "utf-8");
		conn.setRequestProperty("Content-Length",
				Integer.toString(postDataLength));
		conn.setUseCaches(false);
		try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
			wr.write(postData);
		}

		System.out.println("Request URL ... " + url);

		// normally, 3xx is redirect
		int status = conn.getResponseCode();

		System.out.println("Response Code ... " + status);
		return conn.getInputStream();
	}
//	private static InputStream request8a() throws IOException {
//		URL url = null;
//		try {
//			url = new URL(
//			// "https://us-virginia-1-4.vchs.vmware.com/api/compute/api/vApp/vapp-8ac36b2e-9df4-4718-a9a7-59ff7d239250");
//			// "https://us-california-1-3.vchs.vmware.com/api/compute/api/vApp/vapp-f8c1b7c3-1ede-4d51-b4e9-4126093bc59e");
//					"https://us-california-1-3.vchs.vmware.com/api/compute/api/vApp/vapp-8fef9c96-a170-4fb9-897e-7caccab2f95a/action/cloneVApp");
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		// System.out.println("Bearer " + login2);
//
//		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//		conn.setConnectTimeout(60000); // 60 Seconds
//		conn.setReadTimeout(60000);
//		conn.addRequestProperty("Accept", "application/*+xml;version=5.11");
//		conn.addRequestProperty("x-vcloud-authorization", login);
//		conn.addRequestProperty("Authorization", "Bearer " + login2);
//		conn.setRequestMethod("POST");
//		conn.setDoOutput(true);
//		StringBuffer urlParameters = new StringBuffer();
//		urlParameters.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><InstantiateVAppTemplateParams   xmlns=\"http://www.vmware.com/vcloud/v1.5\"   name=\"Linux FTP server\"   deploy=\"true\"   powerOn=\"true\"   xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"   xmlns:ovf=\"http://schemas.dmtf.org/ovf/envelope/1\">   <Description>Example FTP Server</Description>   <InstantiationParams>      <NetworkConfigSection>         <ovf:Info>Configuration parameters for logical networks</ovf:Info>         <NetworkConfig            networkName=\"default-routed-network\">            <Configuration>               <ParentNetwork                  href=\"https://us-california-1-3.vchs.vmware.com/api/compute/api/network/3bcd14f0-bbe8-4de3-b4c8-79ad68676866\" />               <FenceMode>bridged</FenceMode>            </Configuration>         </NetworkConfig>      </NetworkConfigSection>      <LeaseSettingsSection         type=\"application/vnd.vmware.vcloud.leaseSettingsSection+xml\">         <ovf:Info>Lease Settings</ovf:Info>         <StorageLeaseInSeconds>172800</StorageLeaseInSeconds>         <StorageLeaseExpiration>2010-04-11T08:08:16.438-07:00</StorageLeaseExpiration>      </LeaseSettingsSection>   </InstantiationParams>   <Source      href=\"https://us-california-1-3.vchs.vmware.com/api/compute/api/media/f1efd421-b06f-42a8-af1f-5fb60a2f9bb4\" />   <AllEULAsAccepted>true</AllEULAsAccepted></InstantiateVAppTemplateParams>");		
//		
//		
//		byte[] postData = urlParameters.toString().getBytes(StandardCharsets.UTF_8);
//		int postDataLength = postData.length;
//
//		conn.setRequestProperty("Content-Type",
//				"application/vnd.vmware.vcloud.cloneVAppParams+xml");
//		conn.setRequestProperty("charset", "utf-8");
//		conn.setRequestProperty("Content-Length",
//				Integer.toString(postDataLength));
//		conn.setUseCaches(false);
//		try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
//			wr.write(postData);
//		}
//
//		System.out.println("Request URL ... " + url);
//
//		// normally, 3xx is redirect
//		int status = conn.getResponseCode();
//
//		System.out.println("Response Code ... " + status);
//		return conn.getInputStream();
//	}

	private static InputStream request9() throws IOException {
		URL url = null;
		try {
			url =  new URL(
//					"https://us-virginia-1-4.vchs.vmware.com/api/compute/api/vdc/c6e2e992-b077-4795-92aa-d1facb3a1e95");
						//"https://us-california-1-3.vchs.vmware.com/api/compute/api/vdc/5c52bf05-28b7-45d3-9dc5-55780db11a42");
//					"https://us-california-1-3.vchs.vmware.com/api/compute/api/vdcTemplates");
//			"https://us-california-1-3.vchs.vmware.com/api/compute/api/catalog/64736b28-e508-482b-8438-a0ba33654c97");
//"https://us-california-1-3.vchs.vmware.com/api/compute/api/vdc/5c52bf05-28b7-45d3-9dc5-55780db11a42");	
					//"https://us-california-1-3.vchs.vmware.com/api/compute/api/vApp/vapp-0144317c-5e3c-4a2a-89d5-05010fa4dfed/ovf");
//"https://us-california-1-3.vchs.vmware.com/api/compute/api/vchs/query?type=edgeGateway");
			"https://us-california-1-3.vchs.vmware.com/api/compute/api/admin/edgeGateway/f1ce286e-791f-4603-bbda-c1b76c771dda");
///admin/vdc/id
			
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		//System.out.println("Bearer " + login2);

				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(60000); // 60 Seconds
				conn.setReadTimeout(60000);
				conn.addRequestProperty("Accept", "application/*+xml;version=5.11");
				 conn.addRequestProperty("x-vcloud-authorization", login);
//				conn.addRequestProperty("Authorization", "Bearer " + login);
				 conn.addRequestProperty("Authorization", "Bearer " + login2);
				conn.setRequestMethod("GET");
				System.out.println("Request URL ... " + url);

				// normally, 3xx is redirect
				int status = conn.getResponseCode();

				System.out.println("Response Code ... " + status);
				return conn.getInputStream();
	}
	private static InputStream request9a() throws IOException {
		URL url = null;
		try {
			url =  new URL(
//					"https://us-virginia-1-4.vchs.vmware.com/api/compute/api/vdc/c6e2e992-b077-4795-92aa-d1facb3a1e95");
						//"https://us-california-1-3.vchs.vmware.com/api/compute/api/vdc/5c52bf05-28b7-45d3-9dc5-55780db11a42");
//					"https://us-california-1-3.vchs.vmware.com/api/compute/api/vdcTemplates");
//			"https://us-california-1-3.vchs.vmware.com/api/compute/api/catalog/64736b28-e508-482b-8438-a0ba33654c97");
//"https://us-california-1-3.vchs.vmware.com/api/compute/api/vdc/5c52bf05-28b7-45d3-9dc5-55780db11a42");	
					//"https://us-california-1-3.vchs.vmware.com/api/compute/api/vApp/vapp-0144317c-5e3c-4a2a-89d5-05010fa4dfed/ovf");
					"https://us-california-1-3.vchs.vmware.com/api/compute/api/admin/edgeGateway/f1ce286e-791f-4603-bbda-c1b76c771dda/externalIpUsage");
///admin/vdc/id
			
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		//System.out.println("Bearer " + login2);

				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(60000); // 60 Seconds
				conn.setReadTimeout(60000);
				conn.addRequestProperty("Accept", "application/*+xml;version=5.11");
				 conn.addRequestProperty("x-vcloud-authorization", login);
//				conn.addRequestProperty("Authorization", "Bearer " + login);
				 conn.addRequestProperty("Authorization", "Bearer " + login2);
				conn.setRequestMethod("GET");
				System.out.println("Request URL ... " + url);

				// normally, 3xx is redirect
				int status = conn.getResponseCode();

				System.out.println("Response Code ... " + status);
				return conn.getInputStream();
	}
	
	
//	private static InputStream request9() throws IOException {
//		URL url = null;
//		try {
//			url = new URL(
//					"9e/power/action/powerOn");
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		// System.out.println("Bearer " + login2);
//
//		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//		conn.setConnectTimeout(60000); // 60 Seconds
//		conn.setReadTimeout(60000);
//		conn.addRequestProperty("Accept", "application/*+xml;version=5.11");
//		conn.addRequestProperty("x-vcloud-authorization", login);
//		// conn.addRequestProperty("Authorization", "Bearer " + login);
//		conn.addRequestProperty("Authorization", "Bearer " + login2);
//		conn.setRequestMethod("POST");
//		
//		
//		System.out.println("Request URL ... " + url);
//
//		// normally, 3xx is redirect
//		int status = conn.getResponseCode();
//
//		System.out.println("Response Code ... " + status);
//		return conn.getInputStream();
//	}
	private static InputStream request10() throws IOException {
		URL url = null;
		try {
			url = new URL(
			// "https://us-virginia-1-4.vchs.vmware.com/api/compute/api/vApp/vapp-8ac36b2e-9df4-4718-a9a7-59ff7d239250");
			// "https://us-california-1-3.vchs.vmware.com/api/compute/api/vApp/vapp-f8c1b7c3-1ede-4d51-b4e9-4126093bc59e");
		//			"https://us-california-1-3.vchs.vmware.com/api/compute/api/admin/edgeGateway/f1ce286e-791f-4603-bbda-c1b76c771dda");
					"https://us-california-1-3.vchs.vmware.com/api/compute/api/admin/edgeGateway/f1ce286e-791f-4603-bbda-c1b76c771dda/action/configureServices");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println("Bearer " + login2);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(60000); // 60 Seconds
		conn.setReadTimeout(60000);
		conn.addRequestProperty("Accept", "application/*+xml;version=5.11");
		conn.addRequestProperty("x-vcloud-authorization", login);
		conn.addRequestProperty("Authorization", "Bearer " + login2);
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		StringBuffer urlParameters = new StringBuffer();
		urlParameters.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		urlParameters.append("<EdgeGatewayServiceConfiguration");
		urlParameters.append("   xmlns=\"http://www.vmware.com/vcloud/v1.5\">");
		urlParameters.append("    <NatService>");
		urlParameters.append("                <IsEnabled>true</IsEnabled>");
		urlParameters.append("                <NatRule>");
		urlParameters.append("                    <RuleType>SNAT</RuleType>");
		urlParameters.append("                    <IsEnabled>true</IsEnabled>");
		urlParameters.append("                    <Id>65537</Id>");
		urlParameters.append("                    <GatewayNatRule>");
		urlParameters.append("                        <Interface href=\"https://us-california-1-3.vchs.vmware.com/api/compute/api/admin/network/088dbe33-f5fe-44c2-bbfe-55115347b165\" name=\"d2p3v29-ext\" type=\"application/vnd.vmware.admin.network+xml\"/>");
		urlParameters.append("                        <OriginalIp>192.169.109.2</OriginalIp>");
		urlParameters.append("                        <TranslatedIp>107.189.95.228</TranslatedIp>");
		urlParameters.append("                    </GatewayNatRule>");
		urlParameters.append("                </NatRule>");
		urlParameters.append("                <NatRule>");
		urlParameters.append("                    <RuleType>DNAT</RuleType>");
		urlParameters.append("                    <IsEnabled>true</IsEnabled>");
		urlParameters.append("                    <Id>65538</Id>");
		urlParameters.append("                    <GatewayNatRule>");
		urlParameters.append("                        <Interface href=\"https://us-california-1-3.vchs.vmware.com/api/compute/api/admin/network/088dbe33-f5fe-44c2-bbfe-55115347b165\" name=\"d2p3v29-ext\" type=\"application/vnd.vmware.admin.network+xml\"/>");
		urlParameters.append("                        <OriginalIp>107.189.95.228</OriginalIp>");
		urlParameters.append("                        <OriginalPort>Any</OriginalPort>");
		urlParameters.append("                        <TranslatedIp>192.168.109.2</TranslatedIp>");
		urlParameters.append("                        <TranslatedPort>Any</TranslatedPort>");
		urlParameters.append("                        <Protocol>Any</Protocol>");
		urlParameters.append("                    </GatewayNatRule>");
		urlParameters.append("                </NatRule>");
		urlParameters.append("            </NatService>");
		urlParameters.append("</EdgeGatewayServiceConfiguration>");	
		byte[] postData = urlParameters.toString().getBytes(StandardCharsets.UTF_8);
		int postDataLength = postData.length;

		conn.setRequestProperty("Content-Type",
				"application/vnd.vmware.admin.edgeGatewayServiceConfiguration+xml");
		conn.setRequestProperty("charset", "utf-8");
		conn.setRequestProperty("Content-Length",
				Integer.toString(postDataLength));
		conn.setUseCaches(false);
		try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
			wr.write(postData);
		}

		System.out.println("Request URL ... " + url);

		// normally, 3xx is redirect
		int status = conn.getResponseCode();

		System.out.println("Response Code ... " + status);
		return conn.getInputStream();
	}
}
