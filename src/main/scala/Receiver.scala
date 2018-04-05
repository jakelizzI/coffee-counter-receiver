import java.nio.{ByteBuffer, IntBuffer}

import com.sun.jna.{NativeLibrary, Pointer}

object Receiver {

  def main(args: Array[String]): Unit ={

    val libpafe = NativeLibrary.getInstance("/usr/local/lib/libpafe.so")

    val pasoriOpen = libpafe.getFunction("pasori_open")

    val pasori = pasoriOpen.invokePointer(null)

    val pasoriInit = libpafe.getFunction("pasori_init")

    pasoriInit.invokeInt(Array(pasori))

    val felicaPolling = libpafe.getFunction("felica_polling")

    val FELICA_POLLING_ANY = 0xffffff

    val felica = felicaPolling.invokePointer(toArgs(pasori, FELICA_POLLING_ANY, 0, 0))

    val felicaReadSingle = libpafe.getFunction("felica_read_single")

    val data = ByteBuffer.allocate(16)

    val FELICA_SERVICE_CODE_SUICA = 0x090f
    val FELICA_SERVICE_CODE_ANY = 8048.toOctalString
    val NANACO = Integer.parseInt("558b", 16)

    val result = felicaReadSingle.invokeInt(toArgs(felica, FELICA_SERVICE_CODE_SUICA, 0, 0, data))

    println("FELICA_SERVICE_CODE_SUICA : " + FELICA_SERVICE_CODE_SUICA)
    println("結果：" + result)
//    println("16進数：" + 0x558b)
//    println("10進数：" + 0x558b.toOctalString)
//    println("----------------")
//    println("16進数：" + 0x090f)
//    println("10進数：" + 0x090f.toOctalString)

    for(i <- Array(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15)){
      println(data.get(i))
    }
  }

  def toArgs(args: Any*) = args.map(_.asInstanceOf[AnyRef]).toArray
}
