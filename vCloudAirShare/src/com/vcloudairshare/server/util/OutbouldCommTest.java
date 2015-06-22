package com.vcloudairshare.server.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;


public class OutbouldCommTest {
	public static String login = "";
	public static String login2 = "";



	public static String test() {
		StringBuffer theReturn = new StringBuffer();
		try {
			System.out.println("Request");
			buildResponse(theReturn, request1());

			System.out.println("Next Request");
			buildResponse(theReturn, request2());


			System.out.println("Second Request");
			buildResponse(theReturn, request3());
			
			System.out.println("Third Request");
			buildResponse(theReturn, request4());
			
			System.out.println("Fourth Request");

			buildResponse(theReturn, request5());
			System.out.println("Fifth Request");

			buildResponse(theReturn, request6());
			System.out.println("Sixth Request");

			buildResponse(theReturn, request7());
			System.out.println("Seven Request");

			buildResponse(theReturn, request8());
			
			System.out.println("8 Request");

			buildResponse(theReturn, request9());
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

	private static InputStream request3() throws IOException {
		URL url = null;
		try {
			url =  new URL(
						//"https://us-virginia-1-4.vchs.vmware.com/api/compute/api/sessions");
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
						+ Base64.encodeBase64String(new String("jr@warmkessel.com@a3c5bb97-7a7b-4055-97d5-8a040a14ee2e:Overlord2!").getBytes()));
//ce962f03-ae78-4d80-b217-8c363889921b
//0ba27faa-5486-42cc-acc6-0d521ec578cd
		// System.out.println("Basic " +
		// Base64.encodeString("jrwarmkessel@gmail.com:Overlord2!"));
		System.out
				.println("Basic "
						+ Base64.encodeBase64String(new String("jr@warmkessel.com@ce962f03-ae78-4d80-b217-8c363889921b:Overlord2!").getBytes()));

		conn.setRequestMethod("POST");
		System.out.println("Request URL ... " + url);

		// normally, 3xx is redirect
		int status = conn.getResponseCode();

		System.out.println("Response Code ... " + status);

		Map<String, List<String>> map = conn.getHeaderFields();
		//for(String s : map.keySet()){
		//	System.out.println(s);
		//}
		
		if (map.containsKey("x-vcloud-authorization")) {
			// System.out.println("map.get(\"x-vchs-authorization\");" +
			// map.get("x-vchs-authorization"));

			//System.out.println("!!!!"+ map.get("x-vcloud-authorization").get(0));

			login = map.get("x-vcloud-authorization").get(0);
			// login = processString(map.get("x-vchs-authorization").get(0));

		}

		return conn.getInputStream();
	}

	private static InputStream request1() throws IOException {
		URL url = null;
		try {
			url =  new URL(
						"https://vca.vmware.com/api/iam/login");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(60000); // 60 Seconds
		conn.setReadTimeout(60000);
		conn.addRequestProperty("Accept", "application/json;version=5.7");
		conn.addRequestProperty("Authorization",
				"Basic " + Base64.encodeBase64String(new String("jr@warmkessel.com:Overlord2!").getBytes()));
		// System.out.println("Basic " +
		// Base64.encodeString("jrwarmkessel@gmail.com:Overlord2!"));
		System.out.println("Basic "
				+ Base64.encodeBase64String(new String("jr@warmkessel.com:Overlord2!").getBytes()));

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

	private static String processString(String theString) {
		if (theString.indexOf(",") >= 0) {
			theString = theString.substring(0, theString.indexOf(",") - 1);
		}

		return theString;
	}

	private static InputStream request4() throws IOException {
		URL url = null;
		try {
			url =  new URL(
						"https://us-california-1-3.vchs.vmware.com/api/org");
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
//		conn.addRequestProperty("Authorization", "Bearer " + login);
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
			url =  new URL(
						//"https://us-virginia-1-4.vchs.vmware.com/api/sc/instances");
					"https://us-california-1-3.vchs.vmware.com/api/sc/instances");
			
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//System.out.println("Bearer " + login2);

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
			url =  new URL(
//						"https://us-virginia-1-4.vchs.vmware.com/api/compute/api/org/ed19f623-6b78-41ef-8e5b-50b461e13149");
						"https://us-california-1-3.vchs.vmware.com/api/compute/api/org/c42b525f-720e-4f90-aba9-256d5e60a57b");
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
	private static InputStream request6() throws IOException {
		URL url = null;
		try {
			url =  new URL(
//						"https://us-virginia-1-4.vchs.vmware.com/api/compute/api/catalog/c6e2e992-b077-4795-92aa-d1facb3a1e95");
					"https://us-california-1-3.vchs.vmware.com/api/compute/api/catalog/64736b28-e508-482b-8438-a0ba33654c97");
		
		
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
	private static InputStream request7() throws IOException {
		URL url = null;
		try {
			url =  new URL(
//					"https://us-virginia-1-4.vchs.vmware.com/api/compute/api/vdc/c6e2e992-b077-4795-92aa-d1facb3a1e95");
						"https://us-california-1-3.vchs.vmware.com/api/compute/api/vdc/5c52bf05-28b7-45d3-9dc5-55780db11a42");
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
			url =  new URL(
//						"https://us-virginia-1-4.vchs.vmware.com/api/compute/api/vApp/vapp-8ac36b2e-9df4-4718-a9a7-59ff7d239250");
					"https://us-california-1-3.vchs.vmware.com/api/compute/api/vApp/vapp-4989d4e0-5c49-40b4-8104-6e12230b589b");
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
	private static InputStream request9() throws IOException {
		URL url = null;
		try {
			url =  new URL(
						"https://us-california-1-3.vchs.vmware.com/api/compute/api/vApp/vapp-4989d4e0-5c49-40b4-8104-6e12230b589b/power/action/powerOn");
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
				conn.setRequestMethod("POST");
				System.out.println("Request URL ... " + url);

				// normally, 3xx is redirect
				int status = conn.getResponseCode();

				System.out.println("Response Code ... " + status);
				return conn.getInputStream();
	}
}
