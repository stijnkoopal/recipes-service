package models

import play.api.libs.json.Json

case class ApplicationInfo(version: String)

object ApplicationInfo {
  implicit val formatter = Json.format[ApplicationInfo]
}

