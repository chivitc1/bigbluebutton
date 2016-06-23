package org.bigbluebutton.core2x.bus.handlers

import java.util

trait HandlerTestFixtures {
  val piliIntMeetingId = "pili-pinas-2016"

  val du30IntUserId = "du30"
  val du30ExtUserId = "DU30"
  val du30UserName = "Rody Duterte"

  val du30AuthToken = "Du30LetMeWin!"
  val du30Avatar = "http://www.gravatar.com/sdsdas"
  val du30LogoutUrl = "http://www.amoutofhere.com"
  val du30Welcome = "Hello World!"

  val du30Roles: util.ArrayList[String] = new util.ArrayList[String]()
  du30Roles.add("moderator")
  du30Roles.add("guest")
  val du30DialNums: util.ArrayList[String] = new util.ArrayList[String]()
  val du30ConfigXml: String = "DU30_CONFIG_PLACEHOLDER"
  val du30ExternalData: String = "DU30_EXT_DATA_PLACEHOLDER"

}