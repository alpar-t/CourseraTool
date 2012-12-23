package com.github.atorok.CourseraTool

import com.github.atorok.CourseraTool.client.SiteBrowser
import com.github.atorok.CourseraTool.client.HttpException
import com.codahale.jerkson.Json
import com.github.atorok.CourseraTool.remoteapi.Course

object Main {

  val SAVE_DIR = "~/videos/coursera"

  /**
   * @author atorok
   *
   */
  def main(args: Array[String]): Unit = {
    //TODO: Getting too loing ... refactor it
    System setProperty ("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog")
    System setProperty ("org.apache.commons.logging.simplelog.showdatetime", "true");
    System setProperty ("org.apache.commons.logging.simplelog.log.httpclient.wire.header", "debug")
    System setProperty ("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "debug")
    try {

      println("Wellcome to coursera tool ")
      val browser = SiteBrowser("https://www.coursera.org")
      val sid = readLine

      browser.addCookie("sessionid" -> sid)

      println(" Listing courses ...")
      val courseListStr = browser get "/maestro/api/topic/list_my?user_id=306857" // TODO: figure the user_id out

      val jsonstr: String = courseListStr.getHtml \ "body" text;

      val fullList = Json.parse[List[Course]](jsonstr)

      println("Your have  " + fullList.size + " Courses")
      println("Filtering for active ones. ")

      val detailsCheck = fullList map { elem => elem.courses.size }
      if (detailsCheck.sum != fullList.size) {
        println("WARNNG: results might be off... Course has more subcourses ?")
      }

      val activeCourses = fullList filter { elem => elem.courses(0).active }
      println(activeCourses.size + " courses are active.")

      // Note the parallel collections
      activeCourses.par foreach { each =>
        println("Listing Videos for " + each.name + "...")
        val classBrowser = SiteBrowser(each.courses(0).homeLink)
        classBrowser addCookie ("sessionid" -> sid)
        val homePage = classBrowser get "/auth/auth_redirector?type=login&subtype=normal"
        val videoLinks = homePage.getHtml \\ "a" filter { _.text.toLowerCase contains "video lectures" }
        if (videoLinks.size != 1) {
          println("Warning: Expected to find exactly 1 link (skipping): " + videoLinks + " " + classBrowser.jar)
          return
        }
        val theLink = (videoLinks \ "@href").text
        println("Going to list videos from " + theLink)
        
        // This is where the message processing should kick in to
      }
      
      

    }
    catch {
      case why: HttpException => println(" HTTP Error: " + why)
    }

    println("Done!")

  }

}