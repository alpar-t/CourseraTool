/**
 *
 */
package com.github.atorok.CourseraTool.remoteapi

import com.codahale.jerkson.JsonSnakeCase

/**
 * @author atorok
 */
@JsonSnakeCase
case class CourseDetails (
    val homeLink:String,
    val active:Boolean
) {
    
    override def toString:String = {
       homeLink
    }
}


/**
 * @author atorok
 *
 */
@JsonSnakeCase
case class Course (
     val id:Int, 
     val name:String, 
     val shortDescription:String,
     val socialLink: String,
     val courses:List[CourseDetails]
 ) {

  override def toString:String = {
    "Course (" + id + ") '" + name + "' @ " + courses
  } 
  
}