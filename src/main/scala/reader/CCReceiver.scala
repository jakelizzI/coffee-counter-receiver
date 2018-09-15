package reader

import java.nio.ByteBuffer

import com.sun.jna.{NativeLibrary, Pointer}
import javax.xml.bind.DatatypeConverter

class CCReceiver {

  private val FELICA_SERVICE_CODE_SUICA = 0x090f
  private val FELICA_SERVICE_CODE_ANY = 8048.toOctalString
  private val NANACO = Integer.parseInt("558b", 16)
  private val FELICA_POLLING_ANY = 0xffffff

  private val libpafe: NativeLibrary = NativeLibrary.getInstance("/usr/local/lib/libpafe.so")

  private val pasoriClose = libpafe.getFunction("pasori_close")
  private val pasoriInit = libpafe.getFunction("pasori_init")
  private val felicaPolling = libpafe.getFunction("felica_polling")
  private val felicaReadSingle = libpafe.getFunction("felica_read_single")
  private val felicaGetIdm = libpafe.getFunction("felica_get_idm")
  private val pasoriOpen = libpafe.getFunction("pasori_open")

  private val pasori: Pointer = pasoriOpen.invokePointer(null)

  def read(): String ={

    println("libpafe : " + libpafe)

    println("pasori : " + pasori)
    println("init : " + pasoriInit.invokeInt(Array(pasori)))

    val idm = ByteBuffer.allocate(16)

    var sleepCounter = 0

    // loop
    while(true) {

      // 10秒以上タッチがなかったら強制終了
      if(sleepCounter > 20){
        return "-1"
      }

      // 4363
      val felica = felicaPolling.invokePointer(toArgs(pasori, FELICA_POLLING_ANY, 0, 0))

      val result = felicaGetIdm.invokeInt(Array(felica, idm))

      println("結果 : " + result)

      if (result == 0) {
        val idmString: String = DatatypeConverter.printHexBinary(idm.array())
        print("data : " + idmString)
        return idmString
      }
      Thread.sleep(500)
      sleepCounter = sleepCounter + 1
    }

    pasoriClose.invokeVoid(Array(pasori))

    // エラーの際は-1を返す
    "-1"
  }

  def toArgs(args: Any*) = args.map(_.asInstanceOf[AnyRef]).toArray
}