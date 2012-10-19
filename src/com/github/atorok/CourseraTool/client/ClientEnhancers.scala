/**
 *
 */
package com.github.atorok.CourseraTool.client

import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.impl.client.DefaultRedirectStrategy
import org.apache.http.impl.client.BasicCookieStore


/**
 * Simple No-op Trait ( interface )
 * 
 * @author Alpar
 */
trait NoOpSetup {
  val httpClient:DefaultHttpClient
  
  def setup() 
    
}

/**
 * Trait to configure Auto-Redirect
 * 
 * @author Alpar
 */
trait AutoRedirect extends NoOpSetup {

  def setup() {
    httpClient setRedirectStrategy new DefaultRedirectStrategy
  }
  
}

/**
 * Trait to configure Auto-Cookie support
 * 
 *  @author Alpar
 */
trait AutoCookie extends NoOpSetup {
  
  def setup {
     httpClient setCookieStore new BasicCookieStore  
  }
  
}
 