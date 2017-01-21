package controllers

import models.{Ingredient, Recipe}
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.mvc._

class Recipes extends Controller {

  implicit val ingredientReads: Reads[Ingredient] = (
    (JsPath \ "id").readNullable[Long] and
      (JsPath \ "name").read[String]
    )(Ingredient.apply _)

  implicit val ingredientWrites = new Writes[Ingredient] {
    def writes(ingredient: Ingredient) = Json.obj(
      "id" -> ingredient.id,
      "name" -> ingredient.name
    )
  }

  implicit val recipeReads: Reads[Recipe] = (
    (JsPath \ "id").readNullable[Long] and
      (JsPath \ "name").read[String] and
      (JsPath \ "ingredients").readNullable[Seq[Ingredient]]
    )(Recipe.apply _)

  implicit val recipeWrites = new Writes[Recipe] {
    def writes(recipe: Recipe) = Json.obj(
      "id" -> recipe.id,
      "name" -> recipe.name,
      "ingredients" -> recipe.ingredients
    )
  }

  def list = Action {
    val names = Array("bla", "bla3")
    Ok(Json.toJson(names.map(Recipe(Some(100), _, None))))
  }

  def get(id: Long) = Action {
    Ok(Json.toJson(Recipe(Some(id), "bla")))
  }

  def create = Action { request =>
    request.body.asJson match {
      case Some(x) => x.validate[Recipe] match {
        case s: JsSuccess[Recipe] => Ok(Json.toJson(s.get))
        case e: JsError => BadRequest(e.toString)
      }
      case None => BadRequest("Entity not found")
    }
  }

  def update(id: Long) = Action { request =>
    Ok("test")
  }
}