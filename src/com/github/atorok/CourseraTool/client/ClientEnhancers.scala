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
  
  def setup(httpClient: DefaultHttpClient) = {}
    
}

/**
 * Trait to configure Auto-Redirect
 * 
 * @author Alpar
 */
trait AutoRedirect extends NoOpSetup {

  override def setup(httpClient: DefaultHttpClient) = {
    httpClient setRedirectStrategy new DefaultRedirectStrategy
  }
  
}


  

 