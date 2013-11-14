package zzz.akka.avionics

import akka.actor.{Props, Actor, ActorSystem, ActorLogging}

import scala.concurrent.duration._

object Altimeter {
  //Sent to Altimeter to inform it about rate-of-climb changes
  case class RateChange(amount: Float)
}

class Altimeter extends Actor with ActorLogging {
  import Altimeter._
  //we need an "ExecutionContext" for the scheduler. This Actor's dispatcher can serve that purpose.
  //The scheduler's work will be dispatched on this Actor's own dispatcher
  implicit val ec = context.dispatcher

  val ceiling = 43000

  val maxRateOfClimb = 5000

  var rateOfClimb = Of

  var altitude = Od

  //need to know how much time has passed
  var lastTick = System.currentTimeMillis

  val ticker = context.system.scheduler.schedule(100.millis, 100.millis, self, Tick)

  case object Tick

  def receive = {
    
  }


}