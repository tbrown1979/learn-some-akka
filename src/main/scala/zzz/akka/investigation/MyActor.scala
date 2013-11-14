package zzz.akka.investigation

import akka.actor.{Props, Actor, ActorSystem}

case class Gamma(g: String)
case class Beta(b: String, g: Gamma)
case class Alpha(b1: Beta, b2: Beta)

class MyActor extends Actor {
  def receive = {
    case "Hello" => println("Hi")
    case 42 => println("I don't know the question.Go ask the Earth Mark II.")
    case s: String => println(s"You sent me a string: $s")
    case Alpha(Beta(b1, Gamma(g1)), Beta(b2, Gamma(g2))) => println(s"beta1: $b1, beta2: $b2, gamma1: $g1, gamma2: $g2")  
    case _ => println("Huh?")
  }
}

// val system = ActorSystem("MyActors")

// val actorProps = Props[MyActor]

// val actor = system.actorOf(actorProps)
