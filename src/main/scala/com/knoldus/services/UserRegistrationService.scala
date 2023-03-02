package com.knoldus.services

import com.knoldus.models.Models.{UserInfoRequest, UserInfoResponse}

import java.time.LocalDateTime
import java.util.UUID
import scala.concurrent.Future

class UserRegistrationService {

  def register(userInfoRequest: UserInfoRequest): Future[UserInfoResponse] = {
    println(Console.GREEN +
      s"""
        |username -> ${userInfoRequest.userName}
        |email -> ${userInfoRequest.emailId}
        |phone number -> ${userInfoRequest.phone}
        |file -> ${userInfoRequest.file}
        |file info -> ${userInfoRequest.fileInfo}
        |""".stripMargin)
    Future.successful(UserInfoResponse(UUID.randomUUID().toString, userInfoRequest.userName, userInfoRequest.emailId, LocalDateTime.now().toString))
  }
}
