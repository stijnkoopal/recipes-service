package models

import play.api.libs.json.Json
import reactivemongo.bson.BSONObjectID
import reactivemongo.play.json.BSONFormats.BSONObjectIDFormat

object Database {
  type Id = BSONObjectID
}

case class Ingredient(id: Option[Database.Id], name: String)
object Ingredient {
  implicit val formatter = Json.format[Ingredient]
}

case class Recipe(_id: Option[Database.Id] = None, name: String, ingredients: Option[Seq[Ingredient]] = None)
object Recipe {
  implicit val formatter = Json.format[Recipe]
}