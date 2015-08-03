package com.banjo.net.bc14ls;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.DefaultCookieSpec;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class FunctionUtils  
{  

	public static String startUrl = "";
	public static String topDomain = "";
	public static ArrayList<String> getUrls(String url){
		return getHrefIn(getContentFrom(url));
		//vUrls.print();
	}
	/**  
     * get the html source of given url
     *   
     * @param url  
     * @return  
     */
    public static String getContentFrom(String url)  
    {  
    	
    	URL u = null;
    	URI uri = null;
		try {
			u = new URL(url);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			uri = new URI(u.getProtocol(), u.getHost(), u.getPath(), u.getQuery(), null);
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		CloseableHttpClient client = getHttpClient();  
        HttpGet getHttp = new HttpGet(uri);  
        String content = null;  
    
        CloseableHttpResponse response = null;  
        try
        {  
            /*get the loader of content*/
            response = client.execute(getHttp);  
            HttpEntity entity = response.getEntity();
            
//            if(!vUrls.visited.contains(url)) vUrls.visited.add(url);

            if (entity != null)  
            {  
                /* trans to string*/
                content = EntityUtils.toString(entity);  
 
            }  
            if(content == null) System.out.println("NULL CONTENT");
    
        } catch (ClientProtocolException e)  
        {  
            e.printStackTrace();  
        } catch (IOException e)  
        {  
            e.printStackTrace();  
        } finally  
        {  
            if(client!=null)
				try {
					client.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            if(response!=null)
				try {
					response.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }  
             
        return content;  
    }  
    public  static CloseableHttpClient getHttpClient(){
    	
    	CookieSpecProvider easySpecProvider = new CookieSpecProvider() {

    	    public CookieSpec create(HttpContext context) {

    	        return new DefaultCookieSpec() {
    	            @Override
    	            public void validate(Cookie cookie, CookieOrigin origin)
    	                    throws MalformedCookieException {
    	                // Oh, I am easy
    	            }
    	        };
    	    }

    	};
    	Registry<CookieSpecProvider> reg = RegistryBuilder.<CookieSpecProvider>create()
    	        .register(CookieSpecs.DEFAULT,
    	            new CookieSpecProvider() {
						@Override
						public CookieSpec create(HttpContext arg0) {
							// TODO Auto-generated method stub
							return null;
						}
					})
    	        .register(CookieSpecs.DEFAULT,
    	            new CookieSpecProvider() {
						
						@Override
						public CookieSpec create(HttpContext arg0) {
							// TODO Auto-generated method stub
							return null;
						}
					})
    	        .register("mySpec", easySpecProvider)
    	        .build();

    	RequestConfig requestConfig = RequestConfig.custom()
    	        .setCookieSpec("mySpec")
    	        .build();

    	CloseableHttpClient httpclient = HttpClients.custom()
    	        .setDefaultCookieSpecRegistry(reg)
    	        .setDefaultRequestConfig(requestConfig)
    	        .build();
    	return httpclient;
    	
    	
    	/*
    	CloseableHttpClient httpclient = HttpClients.createDefault();   
    	 // DefaultHttpClient httpClient = new DefaultHttpClient();
    	  // customer cookie policy, ignore cookie check 
     	 CookieSpecFactory csf = new CookieSpecFactory() {
     	     public CookieSpec newInstance(HttpParams params) {
     	         return new BrowserCompatSpec() {   
     	             @Override
     	             public void validate(Cookie cookie, CookieOrigin origin)
     	             throws MalformedCookieException {
     	                 // Oh, I am easy
     	             }
     	         };
     	     }
     	 };
    	  httpClient.getCookieSpecs().register("easy", csf);
    	  httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, "easy");
    	        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 2000);
    	        return httpClient;
    	        
    	        
    	        System.out.println("----setContext");
    	        context = HttpClientContext.create();
    	        Registry<CookieSpecProvider> registry = RegistryBuilder
    	            .<CookieSpecProvider> create()
    	            .register(CookieSpecs.BEST_MATCH, new BestMatchSpecFactory())
    	            .register(CookieSpecs.BROWSER_COMPATIBILITY,
    	                new BrowserCompatSpecFactory()).build();
    	        context.setCookieSpecRegistry(registry);
    	        context.setCookieStore(cookieStore);
    	        */
    	 }
    	
    /**  
     * get the urls in that html source
     */
    public static ArrayList<String> getHrefIn(String content){  
    	ArrayList<String> urls = new ArrayList<String>();
       // System.out.println("begin");  
        String[] contents = content.split("<a href=\""); 
        for (int i = 1; i < contents.length; i++){
	        	int endHref = contents[i].indexOf("\"");  
	        	if(endHref!=-1){
		            String aHref = getHrefOfInOut(contents[i].substring(  
		                    0, endHref));  
		            if (aHref != null)  
		            {  
		                String href = getHrefOfInOut(aHref);
		                if(!urls.contains(href)) urls.add(href);
		    
//		                if (!vUrls.visited.contains(href)){  
//		                    vUrls.visited.add(href);  
//		                }  
		            } 
	        	}
        }  
        return urls;
    }  
    
    /**  
     * trans to the formal format
     * as we know ,there are two kinds of urls , one for the inner of the website, that url use the relative path link /xx/..
     * and two for the absolute path link http://xxx.xx/xx/..
     *   
     * @param href  
     * @return  
     */
    public static String getHrefOfInOut(String href)  
    {  
        String resultHref = null;  
     
        /* absolute path */
        if (href.startsWith("http://"))  {  
        	if(href.indexOf(topDomain)!=-1)
        		resultHref = href;
        	else resultHref = startUrl;
        } 
        else
        {  
            /* the relative path inner our website preclude hrefs like a href="#" */
            if (href.startsWith("/")){ 
	             resultHref = startUrl + href;  
            }  
        }  
        return resultHref;  
    }  
     
}