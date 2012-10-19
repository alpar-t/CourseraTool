package com.github.atorok.CourseraTool.client

import org.apache.http.HttpResponse

class HtmlResponse (response:HttpResponse) {
	val statusCode = response.getStatusLine.getStatusCode
	val reason = response.getStatusLine.getReasonPhrase
	
    /**
     * Get the response HTML
     */
    def getHtml() {
      
    }
    
}