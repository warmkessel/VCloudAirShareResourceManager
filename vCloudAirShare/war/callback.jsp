<%@ page language="java" import="org.scribe.builder.ServiceBuilder" import="org.scribe.builder.api.TwitterApi"
	import="org.scribe.model.OAuthRequest"
	import="org.scribe.model.Response"
	import="org.scribe.model.Token"
	import="org.scribe.model.Verb"
	import="org.scribe.model.Verifier"
	import="org.scribe.oauth.OAuthService"
	errorPage="/error.jsp"
	pageEncoding="UTF-8"%><%	
	response.setHeader("Cache-Control","max-age=0");
	response.setHeader("Content-type", "text/javascript; charset=UTF-8");	
%><%


OAuthService service = new ServiceBuilder()
.provider(TwitterApi.class)
.apiKey("T2Dn3GOxnDWBE4ywC6RanwBkd")
.apiSecret("wN7yrWYB4Oilx2mK0AqSeX0xNzQYnGwvVYNW2i1piFo2FcfCyt")
.callback("http://127.0.0.1:8888/TwitterTest.html") 
.debug()
.build();


		
		
Token requestToken = new Token(request.getParameter("oauth"),"wN7yrWYB4Oilx2mK0AqSeX0xNzQYnGwvVYNW2i1piFo2FcfCyt" );

Verifier v = new Verifier(request.getParameter("verifier"));
Token accessToken = service.getAccessToken(requestToken, v); //
OAuthRequest request2 = new OAuthRequest(Verb.GET, "https://api.twitter.com/1.1/account/verify_credentials.json");
service.signRequest(accessToken, request2); // the access token from step 4
Response response2 = request2.send();
System.out.println(response2.getBody());

String tmp = "callback";
if(null != request.getParameter("callback")){
	tmp = request.getParameter("callback");
	}
%><%=tmp%>(response2.getBody());