import java.io.File
import java.io.InputStream

fun main(args: Array<String>) {
    println("Hello world")
    val input: InputStream= File("test.txt").inputStream()
    var s=Scanner(input)
    s.initAutomata()
    s.nextToken()

    var parser=Parser(s)
    if(parser.parse()){
        println("Pogodba veljavna\n")
    }
    else{
        println("Napake v pogodbi")
    }
    parser.contract.print()
}