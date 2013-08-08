package org.scala.course

object Application extends App{
  
  println( "Hello World!" )
  println("concat arguments = " + foo(args))
  
  def foo(x : Array[String]) = x.foldLeft("")((a,b) => a + b)
}

object ScalaUtils {
  def foo(x : Array[String]) = x.foldLeft("")((a,b) => a + b)
}
