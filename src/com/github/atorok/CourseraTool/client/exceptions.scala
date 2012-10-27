package com.github.atorok.CourseraTool.client

class HttpException() extends Exception ;

class HtmlStatusException(response: HtmlResponse) extends HttpException {
  
  override def toString() = {
    val code = response.statusCode
    val msg  = response.reason
    val html = response.getHtml
    "HTTP Status Error (" + code  +") " + msg + "\n" + html
  } 
  
  
}


class HttpContentException(reason:String) extends HttpException;