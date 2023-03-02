package com.knoldus.models

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.directives.FileInfo
import com.knoldus.models.Models.UserInfoResponse
import spray.json.DefaultJsonProtocol

import java.io.File

object Models {

  final case class UserInfoRequest(userName: String, emailId: String, phone: String, fileInfo: FileInfo, file: File)

  final case class UserInfoResponse(uuid: String, userName: String, email: String, date: String)

}

object protocols extends SprayJsonSupport with DefaultJsonProtocol{

  implicit val UserInfoResponseFormat = jsonFormat4(UserInfoResponse)
}
