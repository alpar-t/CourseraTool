package com.github.atorok.CourseraTool.client

import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.impl.client.DefaultRedirectStrategy
import org.apache.http.client.methods.HttpGet
import org.apache.http.cookie.Cookie
import org.apache.http.client.HttpClient
import org.apache.http.impl.client.BasicCookieStore
import org.apache.http.impl.client.SystemDefaultHttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.NameValuePair
import org.apache.http.message.BasicNameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.protocol.HTTP
import java.util.ArrayList
import java.net.CookieStore
import org.apache.http.client.CookieStore
import org.apache.http.impl.cookie.BasicClientCookie



/**
 * 
 */
class SimpleHttpRequest(httpClient:DefaultHttpClient)  extends NoOpSetup {
 
  val client = httpClient
  
  setup(httpClient)
  
  def get(url:String) : HtmlResponse = {    
      new HtmlResponse(httpClient execute new HttpGet(url))
  }
  
  def post(url:String, params:Map[String, String]) : HtmlResponse = {    
    val request =  new HttpPost(url)
    val nvps:List[NameValuePair]    = params.foldRight (List[NameValuePair]())  {(elem, carry) => 
      new BasicNameValuePair(elem._1, elem._2) :: carry        
    }
    // Wonnder why I need an ArrayList, and couldn't pass the scala List
    // because it needs a mutable list. It would sure be nice to have a quick way to get a mutable copy and pass it in
    // would scala convert it automatically from a mutable list ?
    val nvpsConvert = nvps.foldLeft (new ArrayList[NameValuePair]()) { (carry, elem) =>
      carry add elem
      carry
    }
    request setEntity(new UrlEncodedFormEntity(nvpsConvert , HTTP.UTF_8))
    new HtmlResponse(httpClient execute request)    
  }
  
}


/**
 * 
 * 
 */
class ConvenientHttpRequest(
     httpClient : DefaultHttpClient
 ) 
 extends SimpleHttpRequest(httpClient)  
 with AutoRedirect 
{
  
  def this()  {
    this(new SystemDefaultHttpClient)
  } 
   
}

object CheckedHttpRequest {
  
  def apply(client: SimpleHttpRequest)
           (operation: SimpleHttpRequest => HtmlResponse):HtmlResponse = {
       val response = operation(client)
       if (response.statusCode != 200) {
          throw new HtmlStatusException(response)
       }
       response
  }
}


class SiteBrowser(baseUrl:String)  {
 
  val jar = new BasicCookieStore
  
  def get(uri: String) = {
     val req = new ConvenientHttpRequest
     req.client setCookieStore  jar 
     CheckedHttpRequest (req) { req =>  req.get(baseUrl + uri) }
  } 
  
  def post(uri: String, params: Map[String, String]) = {
    val req = new ConvenientHttpRequest
    req.client setCookieStore  jar
    CheckedHttpRequest(req) { req => req.post(baseUrl + uri, params) }
  }
  
  def addCookie(param:(String,String)) {
    val cookie = new BasicClientCookie(param._1, param._2)
    jar addCookie(cookie)
  }
  
}

  
  
