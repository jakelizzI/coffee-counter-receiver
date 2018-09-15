package gui

import javafx.application.Platform
import reader.CCReceiver
import scalafx.scene.control.{Button, Label, MenuItem}
import scalafxml.core.macros.sfxml

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

@sfxml
class PanelController (
  private val menuCount: MenuItem
  , private val menuRegister: MenuItem
  , private val menuPreferences: MenuItem
  , private val menuQuit: MenuItem
  , private val buttonCount: Button
  , private val labelBottomPane: Label) {

  var isWaiting = true

  val receiver: CCReceiver = new CCReceiver

  // ボタンを押すと集計中の文字を出す。
  buttonCount.onMouseClicked = e => {
    if(isWaiting){
      buttonCount.disable = true
      labelBottomPane.text = "集計中...\n(felicaにセキュリティカードをかざして下さい。)"
      isWaiting = false
      val f:Future[String] = Future {
        this.read()
      }

      f.onComplete {
        case Success(idm) => this.changeMesage("success!\n" + idm)
        case Failure(_) => this.changeMesage("error")
      }

    }else{
      buttonCount.text = "count"
      labelBottomPane.text = "coffee counter"
      isWaiting = true
    }
  }


  def read(): String ={
    receiver.read()
  }

  def toArgs(args: Any*) = args.map(_.asInstanceOf[AnyRef]).toArray

  def changeMesage(mes: String): Unit ={
    Platform.runLater(() => {
      labelBottomPane.text = mes
      buttonCount.text = "reset"
      buttonCount.disable = false
    })
  }
}
