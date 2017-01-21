package models

case class Recipe(id: Option[Long] = None, name: String, ingredients: Option[Seq[Ingredient]] = None)
case class Ingredient(id: Option[Long], name: String)