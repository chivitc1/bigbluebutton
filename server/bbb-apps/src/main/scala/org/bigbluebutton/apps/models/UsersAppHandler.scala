package org.bigbluebutton.apps.models

import akka.actor.ActorRef
import org.bigbluebutton.apps.utils.RandomStringGenerator
import org.bigbluebutton.apps.protocol.UserRegistered
import akka.event.LoggingAdapter
import org.bigbluebutton.apps.MeetingActor
import org.bigbluebutton.apps.models.UsersApp.RegisteredUser
import org.bigbluebutton.apps.protocol.UserMessages._

/**
 * Users app for meeting
 */
trait UsersAppHandler {  
  this : MeetingActor =>
  
  val pubsub: ActorRef
  val log: LoggingAdapter
  val usersApp = new UsersApp
  
  def handleRegisterUser(msg: RegisterUserRequest) = {
    val user = usersApp.register(msg.payload)
    
  }
  
  def handleJoinUser(msg: JoinUserRequest) = {
    val token = msg.token
    usersApp.join(token) match {
      case Some(juser) => {
        val jur1Payload = JoinUserReplyPayload( true, "Successfully joined user.", Some(juser))
        val jur1 = JoinUserReply(msg.header, jur1Payload)
      }
      case None => sender ! "Failed to join user"
    }
  }
}