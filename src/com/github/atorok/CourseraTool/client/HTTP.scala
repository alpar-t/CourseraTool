package com.github.atorok.CourseraTool.client

import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.impl.client.DefaultRedirectStrategy
import org.apache.http.client.methods.HttpGet
import org.apache.http.cookie.Cookie
import org.apache.http.client.HttpClient
import org.apache.http.impl.client.BasicCookieStore
import org.apache.http.impl.client.SystemDefaultHttpClient



/**
 * 
 */
class SimpleHttpRequest(httpClient:DefaultHttpClient)  extends NoOpSetup {
 
  setup
  
  def get(url:String) : HtmlResponse = {    
      new HtmlResponse(httpClient execute new HttpGet(url))
  }
  
}



class ConvenientHttpRequest(httpClient : DefaultHttpClient) extends SimpleHttpRequest(httpClient)  {
  
  def this() {
    this(new SystemDefaultHttpClient)
  }
   
}

object ConvenientHttpRequest  {
  
  def apply()  {
    new ConvenientHttpRequest   
                       with AutoRedirect 
                       with AutoCookie
  }
  
 
}

object CheckedHttpRequest {
  
  def apply(client: SimpleHttpRequest)
           (operation: SimpleHttpRequest => HtmlResponse):HtmlResponse = {
       val response = operation(client)
       if (response.statusCode != 200) {
         
       }
       response
  }
}


class SiteBrowser(baseUrl:String) {
 
  def get(uri: String) = CheckedHttpRequest (new ConvenientHttpRequest) { client =>
    client.get(baseUrl + uri)
  } 
  
}

  
  
