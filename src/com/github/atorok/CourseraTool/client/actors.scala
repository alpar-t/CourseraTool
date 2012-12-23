/**
 *
 */
package com.github.atorok.CourseraTool.client

import com.github.atorok.CourseraTool.remoteapi.Course
import scala.actors.Actor
import com.github.atorok.CourseraTool.remoteapi.Course

class VideoDownloader extends Actor {
  
  def act() {
    loop {
      react {
        case (course: Course) =>  {
           println("Going to process " + course)
        }       		
      }
    }
  }
  
}
