<%@ page language="java" import="org.scribe.builder.ServiceBuilder" import="org.scribe.builder.api.TwitterApi"
import="org.scribe.builder.api.TwitterApi"
import="org.scribe.model.Token"
import="org.scribe.oauth.OAuthService"
pageEncoding="UTF-8"%>
	<%	
	response.setHeader("Cache-Control","max-age=0");
	response.setHeader("Content-type", "text/html; charset=UTF-8");	




		OAuthService service = new ServiceBuilder()
        .provider(TwitterApi.class)
        .apiKey("T2Dn3GOxnDWBE4ywC6RanwBkd")
        .apiSecret("wN7yrWYB4Oilx2mK0AqSeX0xNzQYnGwvVYNW2i1piFo2FcfCyt")
        .callback("http://localhost:8080/index.html") 
//        .debug()
//        .callback("http://23.92.254.209/index.html") 

        .build();
		
		Token requestToken = service.getRequestToken();
		
		String authUrl = service.getAuthorizationUrl(requestToken);
		System.out.println("authUrl " + authUrl);
		//response.sendRedirect(authUrl);
		/* session.setAttribute("ser", service);
		session.setAttribute("tok", requestToken);
 */
		response.setStatus(307);
		response.setHeader( "Location", authUrl );
		response.setHeader( "Connection", "close" );
		%>