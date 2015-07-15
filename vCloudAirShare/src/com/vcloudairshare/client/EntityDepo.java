package com.vcloudairshare.client;

import java.util.List;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.vcloudairshare.client.jso.FeedJSO;
import com.vcloudairshare.shared.enumeration.Status;
import com.vcloudairshare.shared.proxy.UserDTO;
import com.vcloudairshare.shared.proxy.VirtualMachineDTO;

public class EntityDepo {
	public static String ACCOUNTID = "AccountId";

	public static String OAUTHTOKEN = "oauth_token=";
	public static String OAUTHVERIFIER = "oauth_verifier=";

	private String oAuthToken = "";
	private String oAuthVerifier = "";

	private UserDTO user = null;
	private FeedJSO feed = null;

	private List<VirtualMachineDTO> vm = null;
	private VirtualMachineDTO test = null;

	public List<VirtualMachineDTO> getVm() {
		return vm;
	}

	public void setVm(List<VirtualMachineDTO> vm) {
		this.vm = vm;
	}

	public EntityDepo() {

	}

	public boolean isAccountReady() {
		return (getFeed() != null || getAccountId() != null);
//		return (getFeed() != null);
//		return true;
	}

	public String getAccountId() {
		return Cookies.getCookie(ACCOUNTID);
	}

	public void setAccountId(String theID) {
		Cookies.setCookie(ACCOUNTID, theID);
	}

	public boolean isUserLoggedInReady() {
		return (getoAuthToken() != null && getoAuthVerifier() != null
				&& getoAuthToken().length() > 0 && getoAuthVerifier().length() > 0);
	}

	public boolean isUserLoggedIn() {
		return (getUser() != null && getUser().getStatus() == Status.APPROVED
				.getId());
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO id) {
		user = id;
	}

	public VirtualMachineDTO getTest() {
		return test;
	}

	public void setTest(VirtualMachineDTO test) {
		this.test = test;
	}

	public FeedJSO getFeed() {
		return feed;
	}

	public void setFeed(FeedJSO feed) {
		this.feed = feed;
	}

	public String getoAuthToken() {
		return oAuthToken;
	}

	public void setoAuthToken(String oAuthToken) {
		this.oAuthToken = oAuthToken;
	}

	public String getoAuthVerifier() {
		return oAuthVerifier;
	}

	public void setoAuthVerifier(String oAuthVerifier) {
		this.oAuthVerifier = oAuthVerifier;
	}

	public void processsURL() {
		// String tmp3 = Cookies.getCookie(OAUTHTOKEN);
		// if (null != tmp3 && tmp3.length() > 0) {
		// oAuthToken = tmp3;
		// }
		// String tmp4 = Cookies.getCookie(OAUTHVERIFIER);
		// if (null != tmp4 && tmp4.length() > 0) {
		// oAuthVerifier = tmp4;
		// }

		String tmp = extractParam(OAUTHTOKEN);
		if (null != tmp && tmp.length() > 0) {
			oAuthToken = tmp;
			// Cookies.setCookie(OAUTHTOKEN, oAuthToken);
		}
		String tmp2 = extractParam(OAUTHVERIFIER);
		if (null != tmp2 && tmp2.length() > 0) {
			oAuthVerifier = tmp2;
			// Cookies.setCookie(OAUTHVERIFIER, oAuthVerifier);
		}
	}

	private String extractParam(String theParam) {
		String tkn = Window.Location.getQueryString();

		int start = tkn.indexOf(theParam);
		if (start >= 0) {
			tkn = tkn.substring(start + theParam.length(), tkn.length());
			start = tkn.indexOf("&");
			if (start >= 0) {
				tkn = tkn.substring(0, start);
			}
			return tkn;
		}
		return "";
	}
}
