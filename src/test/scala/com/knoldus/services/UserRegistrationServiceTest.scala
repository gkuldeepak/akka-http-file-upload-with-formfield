package com.knoldus.services

import akka.http.scaladsl.model.{ContentType, MediaTypes}
import akka.http.scaladsl.server.directives.FileInfo
import com.knoldus.models.Models
import com.knoldus.models.Models.UserInfoRequest
import org.scalatest.flatspec.AnyFlatSpecLike
import org.scalatest.matchers.should.Matchers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import java.io.File

class UserRegistrationServiceTest extends AnyFlatSpecLike with Matchers{

  val userRegistrationService = new UserRegistrationService

  val fileInfo = FileInfo.apply("file", "file.jpg", ContentType.apply(MediaTypes.`image/jpeg`))
  val file = File.createTempFile("file", ".jpg")
  val request = UserInfoRequest("username", "email", "phoneNumber", fileInfo, file)

  "User Registration Service" should "register an user" in {
    val result: Future[Models.UserInfoResponse] = userRegistrationService.register(request)
    result.map {
      response =>
        response.uuid should not be empty
        response.userName should not be empty
        response.email should not be empty
        response.date should not be empty
    }
  }

}
