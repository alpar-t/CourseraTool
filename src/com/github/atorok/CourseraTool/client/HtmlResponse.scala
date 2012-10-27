package com.github.atorok.CourseraTool.client

import org.apache.http.HttpResponse
import org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl
import scala.xml.{Elem, XML}
import scala.xml.factory.XMLLoader


class HtmlResponse (response:HttpResponse) {
	val statusCode = response.getStatusLine.getStatusCode
	val reason = response.getStatusLine.getReasonPhrase
	val entity = response getEntity
	
    /**
     * Get the response HTML
     */
    def getHtml() = {
		if ( entity.getContentType.getValue.equalsIgnoreCase("text/html") ) {
		    throw new HttpContentException("Expected HTML, got: " + entity.getContentType.getValue)
		}
		TagSoupXmlLoader().load(entity.getContent())
    }
    
}


object TagSoupXmlLoader {
 
    private val factory = new SAXFactoryImpl()
 
 
    def apply(): XMLLoader[Elem] = {
        XML.withSAXParser(factory.newSAXParser())
    }
}