package com.knoldus.routes

import akka.http.scaladsl.model.{ContentType, ContentTypes, HttpEntity, HttpResponse, MediaTypes, StatusCodes}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.FileInfo
import com.knoldus.models.Models.UserInfoRequest
import com.knoldus.services.UserRegistrationService
import com.knoldus.models.protocols._

import java.io.File

class UserRegistrationRoute {

  val userRegistrationService = new UserRegistrationService

  val route: Route = {
    pathPrefix("upload") {
      path("form-field") {
        (post & withoutSizeLimit) {
          extractRequestContext { erc =>
            implicit val materializer = erc.materializer
            formField("name", "email", "phone") { (name, email, phone) =>
              storeUploadedFile("file", destination) {
                case (fileInfo: FileInfo, file: File) =>
                  onSuccess(userRegistrationService.register(UserInfoRequest(name, email, phone, fileInfo, file))) { response =>
                    complete(response)
                  }
                case _ =>
                  complete(HttpResponse(StatusCodes.NoContent, entity = HttpEntity(ContentTypes.`application/json`, "No Content Provided")))
              }
            }
          }
        }
      }
    }
  }

  private def destination(fileInfo: FileInfo): File = {
    File.createTempFile(fileInfo.fileName, getExtension(fileInfo.getContentType))
  }

  private def getExtension(contentType: ContentType): String = {
    contentType.mediaType match {
      case MediaTypes.`application/json` => ".pdf"
      case MediaTypes.`image/png` => ".png"
      case MediaTypes.`image/jpeg` => ".jpg"
      case _ => ".tmp"
    }
  }

}
