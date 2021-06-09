import java.io.InputStream

class Scanner(input: InputStream, var row: Int=1, var column: Int=1) {
    private var charCounter=0

    private var lastToken: Token = Token()
    private val maxState: Int = 9
    private val startState: Int = 0
    private val noEdge: Int = -1
    private var automata = Array(maxState + 1) { Array(256) { noEdge } }
    private var finite = IntArray(maxState + 1) { 0 }
    var inputString=input.bufferedReader().use {it.readText()}

    private val tLexError: Int = -1
    private val tIgnore: Int = 0
    private val tInteger: Int = 1
    private val tOperator: Int = 2
    private val tSeparator: Int = 3
    private val tVariable: Int = 4
    private val tLogicOperator:Int=5
    private val tProperty:Int=6
    private val tIndex:Int=7
    private val tMethod:Int=8


    @OptIn(ExperimentalStdlibApi::class)
    fun initAutomata() {
        val abeceda: CharArray = charArrayOf(
            'a',
            'b',
            'c',
            'd',
            'e',
            'f',
            'g',
            'h',
            'i',
            'j',
            'k',
            'l',
            'm',
            'n',
            'o',
            'p',
            'q',
            'r',
            's',
            't',
            'u',
            'v',
            'w',
            'x',
            'y',
            'z'
        )
        val ABECEDA: CharArray = charArrayOf(
            'A',
            'B',
            'C',
            'D',
            'E',
            'F',
            'G',
            'H',
            'I',
            'J',
            'K',
            'L',
            'M',
            'N',
            'O',
            'P',
            'Q',
            'R',
            'S',
            'T',
            'U',
            'V',
            'W',
            'X',
            'Y',
            'Z'
        )
        for (i in 48..57) {
            automata[0][i] = 1
            automata[1][i] = 1
            automata[5][i] = 5
        }
        //operatorji
        automata[0]['+'.code]=2
        automata[0]['-'.code]=2
        automata[0]['='.code]=2
        //logicni operatorji
        automata[2]['='.code]=6
        automata[0]['>'.code]=6
        //separatorji
        automata[0]['('.code]=3
        automata[0][')'.code]=3
        automata[7]['('.code]=9
        automata[9][')'.code]=9
        automata[0]['{'.code]=3
        automata[0]['}'.code]=3
        automata[0][','.code]=3
        automata[0][';'.code]=3
        //whitespace
        automata[0]['\n'.code]=4
        automata[0][' '.code]=4
        automata[0]['\t'.code]=4
        automata[0][13]=4       //carriage return, pojavi se pri stringu z new line
        automata[4]['\n'.code]=4
        automata[4][' '.code]=4
        automata[4]['\t'.code]=4
        automata[4][13]=4

        for(i in 0 until 26){
            automata[0][ABECEDA[i].code]=5
            automata[0][abeceda[i].code]=5
            automata[5][ABECEDA[i].code]=5
            automata[5][abeceda[i].code]=5
            automata[7][ABECEDA[i].code]=7
            automata[7][abeceda[i].code]=7
            automata[8][ABECEDA[i].code]=8
            automata[8][abeceda[i].code]=8
            automata[9][ABECEDA[i].code]=9
            automata[9][abeceda[i].code]=9
        }
        automata[0]['.'.code]=7
        automata[0]['['.code]=8
        automata[8][']'.code]=7
        automata[0][']'.code]=7

        finite[0]=tLexError
        finite[1]=tInteger
        finite[2]=tOperator
        finite[3]=tSeparator
        finite[4]=tIgnore
        finite[5]=tVariable
        finite[6]=tLogicOperator
        finite[7]=tProperty
        finite[8]=tIndex
        finite[9]=tMethod
    }

    fun getNextState(aState: Int, aChar: Int):Int{
        if(aChar==-1)
            return noEdge
        return automata[aState][aChar]
    }

    fun isFiniteState(aState: Int):Boolean{
        return finite[aState]!=tLexError
    }

    fun getFiniteState(aState: Int):Int{
        return finite[aState]
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun peek():Int{
        return inputString[charCounter].code
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun read():Int{
        var temp:Int=inputString[charCounter].code
        charCounter++
        column++
        if(temp=='\n'.code){
            row++
            column=1
        }
        return temp
    }

    fun eof():Boolean{
        return charCounter==inputString.length
    }

    fun nextToken(): Token{
        lastToken=nextTokenImp()
        return lastToken
    }

    fun currentToken(): Token{
        return lastToken
    }

    fun nextTokenImp(): Token{
        var currentState:Int=startState
        var lexem=""
        var startColumn:Int=column
        var startRow:Int=row
        do{
            var tempState=getNextState(currentState, peek())
            if(tempState!=noEdge){
                currentState=tempState
                lexem+=read().toChar()
                if(charCounter==inputString.length){
                    return Token(lexem,startColumn,startRow,getFiniteState(currentState),eof())
                }
            }
            else{
                if(isFiniteState(currentState)){
                    var token: Token=Token(lexem,startColumn,startRow,getFiniteState(currentState),eof())
                    return if(token.token==tIgnore){
                        nextToken()
                    } else{
                        token
                    }
                }
                else{
                    return Token("",startColumn,startRow,tLexError,eof())
                }
            }
        }while(true)
    }
}