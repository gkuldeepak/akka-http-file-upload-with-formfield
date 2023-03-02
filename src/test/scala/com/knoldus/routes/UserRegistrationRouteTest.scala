package com.knoldus.routes

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, Multipart}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.knoldus.models.Models.{UserInfoRequest, UserInfoResponse}
import com.knoldus.services.UserRegistrationService
import org.mockito.Matchers.any
import org.mockito.Mockito.when
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar.mock
import com.knoldus.models.protocols._

import java.time.LocalDateTime
import java.util.UUID
import scala.concurrent.Future

class UserRegistrationRouteTest extends AnyFlatSpec with Matchers with ScalatestRouteTest{

  val userService = mock[UserRegistrationService]
  val routes = new UserRegistrationRoute

  val nameMultipartFormData = Multipart.FormData.BodyPart.Strict("name", "kg")
  val emailMultipartFormData = Multipart.FormData.BodyPart.Strict("email", "kg@xyz.abc")
  val phoneMultipartFormData = Multipart.FormData.BodyPart.Strict("phone", "1234567890")
  val fileMultipartFormData = Multipart.FormData.BodyPart.Strict("file",
    HttpEntity(ContentTypes.`text/plain(UTF-8)`, ""),
    Map("fileName" -> "file.jpg"))
  val multipartFormData = Multipart.FormData(nameMultipartFormData, emailMultipartFormData, phoneMultipartFormData, fileMultipartFormData)

  "POST route" should "execute the request" in {
    when(userService.register(any[UserInfoRequest])) thenReturn Future.successful(UserInfoResponse(UUID.randomUUID().toString, "kg", "kg@xyz.abc", LocalDateTime.now().toString))

    Post("/upload/form-field", multipartFormData) ~> routes.route ~> check {
      responseAs[UserInfoResponse].userName should not be empty
      responseAs[UserInfoResponse].date should not be empty
      responseAs[UserInfoResponse].email should not be empty
      responseAs[UserInfoResponse].uuid should not be empty
    }
  }

}
