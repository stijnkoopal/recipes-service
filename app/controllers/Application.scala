package controllers

import models.ApplicationInfo
import play.api.mvc._
import play.api.libs.json._

class Application extends Controller {

  implicit val applicationInfoWrites = new Writes[ApplicationInfo] {
    def writes(applicationInfo: ApplicationInfo) = Json.obj(
      "version" -> applicationInfo.version
    )
  }

  def info = Action {
    Ok(Json.toJson(ApplicationInfo("1.0.0")))
  }

}