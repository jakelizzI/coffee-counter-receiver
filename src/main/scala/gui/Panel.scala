package gui

import java.net.URL

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafxml.core.{FXMLView, NoDependencyResolver}

object Panel extends JFXApp {

  val resorce: URL = getClass.getResource("Panel.fxml")
  val root = FXMLView(resorce, NoDependencyResolver)

  stage = new JFXApp.PrimaryStage {
    title = "coffee-counter"
    scene = new Scene(root)
  }
}
