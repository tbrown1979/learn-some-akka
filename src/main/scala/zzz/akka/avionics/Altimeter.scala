package zzz.akka.avionics

import akka.actor.{Props, Actor, ActorSystem, ActorLogging}

import scala.concurrent.duration._

object Altimeter {
  //Sent to Altimeter to inform it about rate-of-climb changes
  case class RateChange(amount: Float)
  case class AltitudeUpdate(altitude: Double)
}

class Altimeter extends Actor with ActorLogging 
                              with EventSource {
  import Altimeter._
  //we need an "ExecutionContext" for the scheduler. This Actor's dispatcher can serve that purpose.
  //The scheduler's work will be dispatched on this Actor's own dispatcher
  implicit val ec = context.dispatcher

  val ceiling = 43000

  val maxRateOfClimb = 5000

  var rateOfClimb = 0f

  var altitude = 0d

  //need to know how much time has passed
  var lastTick = System.currentTimeMillis

  val ticker = context.system.scheduler.schedule(100.millis, 100.millis, self, Tick)

  case object Tick

  def altimeterReceive: Receive = {
    case RateChange(amount) => 
      rateOfClimb = amount.min(1.0f).max(-1.0f) * maxRateOfClimb
      log.info(s"Altimeter changed rate of climb to $rateOfClimb.")

    case Tick =>
      val tick = System.currentTimeMillis
      altitude = altitude + ((tick - lastTick) / 60000.0) * rateOfClimb
      lastTick = tick
      sendEvent(AltitudeUpdate(altitude))
  }

  def receive = eventSourceReceive orElse altimeterReceive

  override def postStop(): Unit = ticker.cancel


}