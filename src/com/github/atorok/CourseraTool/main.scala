package com.github.atorok.CourseraTool

import com.github.atorok.CourseraTool.client.SiteBrowser
import com.github.atorok.CourseraTool.client.HttpException
import com.codahale.jerkson.Json
import com.github.atorok.CourseraTool.remoteapi.Course

object Main {

  def main(args: Array[String]): Unit = {

    try {
      println ( "Wellcome to coursera tool " )
      val browser = new SiteBrowser("https://www.coursera.org")
      val sid = "04b02aafa4a3446c348a5ae0938339be" // TODO: read from user

      browser.addCookie("sessionid" -> sid)

      println ( " Listing courses ..." )
      val courseListStr = browser get "/maestro/api/topic/list_my?user_id=306857" // TODO: figure the user_id out
      
      val jsonstr:String = courseListStr.getHtml \ "body" text ; 

      val courses = Json.parse[List[Course]](jsonstr)
      
      println ( "Your courses are " + courses )
      

      // Need more reverse eng to get this to work due to CSRF
      //println("Logging in...")
      //val logIn = browser.post (
      //     "/maestro/api/user/login",
      //     Map(
      //         "email_address" -> "...",
      //         "password"      -> "..."
      //     )         
      //)
      //println(logIn.getHtml \\ "body")   

      //println("Listing courses ..")
      // do Json request

      // Iterate trough courses and grab videos

    } catch {
      case why: HttpException => println("Error: " + why)
      case why: Throwable => println("Error:" + why)
    }

    println("Done!")

  }

}