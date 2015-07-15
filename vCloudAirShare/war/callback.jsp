<%@ page language="java" import="org.scribe.builder.ServiceBuilder"
	import="org.scribe.builder.api.TwitterApi"
	import="org.scribe.model.OAuthRequest"
	import="org.scribe.model.Response" import="org.scribe.model.Token"
	import="org.scribe.model.Verb" import="org.scribe.model.Verifier"
	import="org.scribe.oauth.OAuthService" errorPage="/error.jsp"
	pageEncoding="UTF-8"%>
<%
	response.setHeader("Cache-Control", "max-age=0");
	response.setHeader("Content-type", "text/javascript; charset=UTF-8");
%>
<%
	String tmp = "callback";
	String oauth_token = "";
	String oauth_verifier = "oauth_verifier";
	if (null != request.getParameter("callback")) {
		tmp = request.getParameter("callback");
	}
	if (null != request.getParameter("oauth_token")) {
		oauth_token = request.getParameter("oauth_token");
	}
	if (null != request.getParameter("oauth_verifier")) {
		oauth_verifier = request.getParameter("oauth_verifier");
	}

	OAuthService service = new ServiceBuilder()
			.provider(TwitterApi.class)
			.apiKey("T2Dn3GOxnDWBE4ywC6RanwBkd")
			.apiSecret(
					"wN7yrWYB4Oilx2mK0AqSeX0xNzQYnGwvVYNW2i1piFo2FcfCyt")
			.build();

	Token requestToken = new Token(
			oauth_token,
			"wN7yrWYB4Oilx2mK0AqSeX0xNzQYnGwvVYNW2i1piFo2FcfCyt");

	Verifier v = new Verifier(oauth_verifier);
	Token accessToken = service.getAccessToken(requestToken, v); //
	OAuthRequest orequest = new OAuthRequest(Verb.GET,
			"https://api.twitter.com/1.1/account/verify_credentials.json");
	service.signRequest(accessToken, orequest); // the access token from step 4
	Response oresponse = orequest.send();
	System.out.println(oresponse.getBody());

%><%=tmp%>(<%=oresponse.getBody()%>);
