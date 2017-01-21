package controllers

import javax.inject._

import models.Recipe
import models.Ingredient
import play.api.libs.json._
import play.api.mvc._
import play.modules.reactivemongo._
import reactivemongo.api.ReadPreference
import reactivemongo.bson.BSONObjectID
import reactivemongo.play.json._
import reactivemongo.play.json.collection._

import scala.concurrent.{ExecutionContext, Future}

//TODO: create repository/service to abstract some mongo stuff
@Singleton
class Recipes @Inject()(val reactiveMongoApi: ReactiveMongoApi)(implicit exec: ExecutionContext) extends Controller
  with MongoController with ReactiveMongoComponents {

  private def recipesFuture = database.map(_.collection[JSONCollection]("recipes"))

  def list = Action.async {
    val result = recipesFuture.flatMap {
      _.find(Json.obj())
        .cursor[Recipe](ReadPreference.primary)
        .collect[List]()
    }
    result.map { recipes =>
      Ok(Json.toJson(recipes))
    }
  }

  def get(id: String) = Action.async {
    val result = recipesFuture.flatMap {
      _.find(Json.obj("_id" -> BSONObjectID(id)))
          .one[Recipe](ReadPreference.primary)
    }
    result.map {
      case Some(recipe) => Ok(Json.toJson(recipe))
      case None => NotFound("recipe not found")
    }
  }
//
//  def create = Action { request =>
//    request.body.asJson match {
//      case Some(x) => x.validate[Recipe] match {
//        case s: JsSuccess[Recipe] => Ok(Json.toJson(s.get))
//        case e: JsError => BadRequest(e.toString)
//      }
//      case None => BadRequest("Entity not found")
//    }
//  }
//
//  def update(id: Long) = Action { request =>
//    Ok("test")
//  }
}