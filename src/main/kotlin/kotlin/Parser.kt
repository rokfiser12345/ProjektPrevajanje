class Parser(var scanner:Scanner){
    val tLexError: Int = -1
    val tIgnore: Int = 0
    val tInteger: Int = 1
    val tOperator: Int = 2
    val tSeparator: Int = 3
    val tVariable: Int = 4
    val tLogicOperator:Int=5
    val tProperty:Int=6
    val tIndex:Int=7
    val tMethod:Int=8

    val contract= Contract()
    var variable=Variable()
    var ifStatement=IfStatement()
    var statement=Statement()

    var insideIndex=false
    var ifDepth=0
    var insideStatement=false

    fun C(): Boolean{
        if(scanner.currentToken().lexem=="CONTRACT") {
            println("Lexem: "+scanner.currentToken().lexem)
            scanner.nextToken()
            if (scanner.currentToken().token == tVariable) {
                //ime pogodbe
                println("Lexem: "+scanner.currentToken().lexem)
                contract.contractName=scanner.currentToken().lexem
                scanner.nextToken()
                if (scanner.currentToken().lexem == "(") {
                    println("Lexem: "+scanner.currentToken().lexem)
                    scanner.nextToken()
                    if (scanner.currentToken().token == tVariable) {            //-------------> 1. Type+variable+vejica
                        println("Lexem: "+scanner.currentToken().lexem)
                        variable=Variable()
                        variable.type=scanner.currentToken().lexem
                        scanner.nextToken()
                        if (scanner.currentToken().token == tVariable) {        //-------------> 1. Type+variable+vejica
                            println("Lexem: "+scanner.currentToken().lexem)
                            variable.name=scanner.currentToken().lexem
                            contract.contractVariables.add(variable)
                            scanner.nextToken()
                            if (scanner.currentToken().lexem == ",") {          //-------------> 1. Type+variable+vejica
                                println("Lexem: "+scanner.currentToken().lexem)
                                scanner.nextToken()
                                if (scanner.currentToken().token == tVariable) {        //----->2. Type+variable+vejica
                                    println("Lexem: "+scanner.currentToken().lexem)
                                    variable=Variable()
                                    variable.type=scanner.currentToken().lexem
                                    scanner.nextToken()
                                    if (scanner.currentToken().token == tVariable) {    //----->2. Type+variable+vejica
                                        println("Lexem: "+scanner.currentToken().lexem)
                                        variable.name=scanner.currentToken().lexem
                                        contract.contractVariables.add(variable)
                                        scanner.nextToken()
                                        if (scanner.currentToken().lexem == ",") {      //----->2. Type+variable+vejica
                                            println("Lexem: "+scanner.currentToken().lexem)
                                            scanner.nextToken()
                                            if (scanner.currentToken().token == tVariable) {        //----->3. Type+variable
                                                println("Lexem: "+scanner.currentToken().lexem)
                                                variable=Variable()
                                                variable.type=scanner.currentToken().lexem
                                                scanner.nextToken()
                                                if (scanner.currentToken().token == tVariable) {    //----->3. Type+variable
                                                    println("Lexem: "+scanner.currentToken().lexem)
                                                    variable.name=scanner.currentToken().lexem
                                                    contract.contractVariables.add(variable)
                                                    scanner.nextToken()
                                                    if (scanner.currentToken().lexem == ")") {
                                                        variable=Variable()
                                                        println("Lexem: "+scanner.currentToken().lexem)
                                                        scanner.nextToken()
                                                        if (scanner.currentToken().lexem == "{") {
                                                            println("Lexem: "+scanner.currentToken().lexem)
                                                            scanner.nextToken()
                                                            if (B() && scanner.currentToken().lexem == "return") {
                                                                println("Lexem: "+scanner.currentToken().lexem)
                                                                scanner.nextToken()
                                                                if (scanner.currentToken().lexem == "false") {
                                                                    println("Lexem: "+scanner.currentToken().lexem)
                                                                    contract.contractReturn=false
                                                                    scanner.nextToken()
                                                                    if (scanner.currentToken().lexem == ";") {
                                                                        scanner.nextToken()
                                                                        if (scanner.currentToken().lexem == "}") {
                                                                            println("Lexem: "+scanner.currentToken().lexem)
                                                                            return true
                                                                        } else return false
                                                                    } else return false
                                                                } else return false
                                                            } else return false
                                                        } else return false
                                                    } else return false
                                                } else return false
                                            }else return false
                                        } else {
                                            if (scanner.currentToken().lexem == ")") {
                                                println("Lexem: "+scanner.currentToken().lexem)
                                                variable=Variable()
                                                scanner.nextToken()
                                                if (scanner.currentToken().lexem == "{") {
                                                    println("Lexem: "+scanner.currentToken().lexem)
                                                    scanner.nextToken()
                                                    if (B() && scanner.currentToken().lexem == "return") {
                                                        println("Lexem: "+scanner.currentToken().lexem)
                                                        scanner.nextToken()
                                                        if (scanner.currentToken().lexem == "false") {
                                                            println("Lexem: "+scanner.currentToken().lexem)
                                                            contract.contractReturn=false
                                                            scanner.nextToken()
                                                            if (scanner.currentToken().lexem == ";") {
                                                                println("Lexem: "+scanner.currentToken().lexem)
                                                                scanner.nextToken()
                                                                if (scanner.currentToken().lexem == "}") {
                                                                    println("Lexem: "+scanner.currentToken().lexem)
                                                                    return true
                                                                }
                                                            }else return false
                                                        }else return false
                                                    }else return false
                                                }else return false
                                            }else return false
                                        }
                                    }else return false
                                }else return false
                            }else return false
                        }else return false
                    }else return false
                }else return false
            }else return false
        } else {
            return false
        }
    return false
    }

    fun B():Boolean{
        if(I()){
            return true
        }
        else if(scanner.currentToken().lexem=="return"){
            println("Lexem: "+scanner.currentToken().lexem)
            scanner.nextToken()
            return if(scanner.currentToken().lexem=="true"){
                println("Lexem: "+scanner.currentToken().lexem)
                statement.returnValue=true
                contract.contractStatements[ifDepth-1].codeBlock.add(statement)
                scanner.nextToken()
                if(scanner.currentToken().lexem==";") {
                    println("Lexem: "+scanner.currentToken().lexem)
                    scanner.nextToken()
                    return true
                } else false
            } else false
        }
        else if(scanner.currentToken().token==tVariable){
            println("Lexem: "+scanner.currentToken().lexem)
            variable.name=scanner.currentToken().lexem
            scanner.nextToken()
            return if(P()&&V()&&scanner.currentToken().lexem==";"){
                scanner.nextToken()
                if(statement.leftSideVariable.name!=""){
                    statement.rightSideVariable=variable
                }
                else{
                    statement.leftSideVariable=variable
                }
                contract.contractStatements[ifDepth-1].codeBlock.add(statement)
                statement=Statement()
                variable=Variable()
                B()
            } else false
        } else return false
    }

    fun I():Boolean{
        if(scanner.currentToken().lexem=="IF"){
            println("Lexem: "+scanner.currentToken().lexem)
            scanner.nextToken()
            if(scanner.currentToken().lexem=="("){
                println("Lexem: "+scanner.currentToken().lexem)
                ifDepth++
                scanner.nextToken()
                return if(scanner.currentToken().token==tVariable){
                    println("Lexem: "+scanner.currentToken().lexem)
                    variable.name=scanner.currentToken().lexem
                    //ifStatement.leftSideVariable.name=scanner.currentToken().lexem
                    //contract.contractStatements.add(ifStatement)
                    scanner.nextToken()
                    if(P()&&L()&&scanner.currentToken().lexem==")"){
                        println("Lexem: "+scanner.currentToken().lexem)
                        if(ifStatement.leftSideVariable.name!=""){
                            if(ifStatement.rightSideValue==""&&ifStatement.rightSideVariable.name=="") {
                                ifStatement.rightSideVariable=variable
                            }
                        }
                        else{
                            ifStatement.leftSideVariable=variable
                        }
                        contract.contractStatements.add(ifStatement)
                        ifStatement=IfStatement()
                        variable=Variable()
                        scanner.nextToken()
                        if(scanner.currentToken().lexem=="{"){
                            println("Lexem: "+scanner.currentToken().lexem)
                            scanner.nextToken()
                            if(B()&&scanner.currentToken().lexem=="}"){
                                println("Lexem: "+scanner.currentToken().lexem)
                                scanner.nextToken()
                                ifDepth--
                                true
                            } else false
                        } else false
                    } else false
                } else false
            } else return false
        } else return false
    }

    fun P():Boolean{
        if(scanner.currentToken().token==tProperty){
            println("Lexem xd: "+scanner.currentToken().lexem)
            if(scanner.currentToken().lexem=="]"){
                insideIndex=false
            }
            val property=Property()
            property.name=scanner.currentToken().lexem
            if(insideIndex){
                var temp=variable.properties.lastIndex
                variable.properties[variable.properties.lastIndex].indeks+=scanner.currentToken().lexem
            }else{
                variable.properties.add(property)
            }
            scanner.nextToken()
            return P()
        }
        else if(scanner.currentToken().token==tMethod){
            println("Lexem123: "+scanner.currentToken().lexem)
            val property=Property()
            property.name=scanner.currentToken().lexem
            if(insideIndex){
                variable.properties[variable.properties.lastIndex].indeks+=scanner.currentToken().lexem
            }else{
                variable.properties.add(property)
            }
            scanner.nextToken()
            return P()
        }
        else if(scanner.currentToken().token==tIndex){
            println("Lexem: "+scanner.currentToken().lexem)
            variable.properties[variable.properties.lastIndex].indeks+=scanner.currentToken().lexem
            insideIndex=true
            scanner.nextToken()
            if(P()&&scanner.currentToken().lexem=="]"){
                println("Lexem1: "+scanner.currentToken().lexem)
                variable.properties[variable.properties.lastIndex].indeks+=scanner.currentToken().lexem
                insideIndex=false
                return P()
            }
        }
        return true
    }

    fun L():Boolean{
        return if(scanner.currentToken().lexem==">"){
            println("Lexem: "+scanner.currentToken().lexem)
            ifStatement.operator=scanner.currentToken().lexem
            ifStatement.leftSideVariable=variable
            //contract.contractStatements[contract.contractStatements.lastIndex].logicOperator=scanner.currentToken().lexem
            scanner.nextToken()
            E()&&P()
        } else if(scanner.currentToken().lexem=="=="){
            println("Lexem: "+scanner.currentToken().lexem)
            ifStatement.operator=scanner.currentToken().lexem
            ifStatement.leftSideVariable=variable
            //contract.contractStatements[contract.contractStatements.lastIndex].logicOperator=scanner.currentToken().lexem
            scanner.nextToken()
            E()&&P()
        } else false
    }

    fun V():Boolean{
        return if(scanner.currentToken().lexem=="-="){
            println("Lexem: "+scanner.currentToken().lexem)
            statement.operator=scanner.currentToken().lexem
            statement.leftSideVariable=variable
            scanner.nextToken()
            E()&&P()
        } else if(scanner.currentToken().lexem=="+="){
            println("Lexem: "+scanner.currentToken().lexem)
            statement.operator=scanner.currentToken().lexem
            statement.leftSideVariable=variable
            scanner.nextToken()
            E()&&P()
        } else if(scanner.currentToken().lexem=="="){
            println("Lexem: "+scanner.currentToken().lexem)
            statement.operator=scanner.currentToken().lexem
            statement.leftSideVariable=variable
            scanner.nextToken()
            E()&&P()
        } else true
    }

    fun F(): Boolean {
        var value=""
        if (scanner.currentToken().lexem == "(") {
            scanner.nextToken()
            return if (E() && scanner.currentToken().lexem === ")") {
                scanner.nextToken()
                true
            } else false
        } else if (scanner.currentToken().token == tInteger) {
            println("Lexem: "+scanner.currentToken().lexem)
            variable=Variable()
            ifStatement.rightSideValue=scanner.currentToken().lexem
            statement.rightSideValue=scanner.currentToken().lexem
            scanner.nextToken()
            return true
        } else if(scanner.currentToken().lexem=="true"){
            println("Lexem: "+scanner.currentToken().lexem)
            ifStatement.rightSideValue=scanner.currentToken().lexem
            statement.rightSideValue=scanner.currentToken().lexem
            scanner.nextToken()
            return true
        } else if(scanner.currentToken().lexem=="false"){
            println("Lexem: "+scanner.currentToken().lexem)
            variable=Variable()
            ifStatement.rightSideValue=scanner.currentToken().lexem
            statement.rightSideValue=scanner.currentToken().lexem
            scanner.nextToken()
            return true
        } else if (scanner.currentToken().token == tVariable) {
            variable=Variable()
            variable.name=scanner.currentToken().lexem
            scanner.nextToken()
            return true
        } else if (scanner.currentToken().lexem == "-") {
            println("Lexem: "+scanner.currentToken().lexem)
            value+=scanner.currentToken().lexem
            scanner.nextToken()
            if (scanner.currentToken().token == tInteger) {
                println("Lexem: "+scanner.currentToken().lexem)
                value+=scanner.currentToken().lexem
                ifStatement.rightSideValue=value
                statement.rightSideValue=value
                scanner.nextToken()
                return true
            }
        }
        return false
    }

    fun TT(): Boolean {
        if (scanner.currentToken().lexem === "*") {
            scanner.nextToken()
            return F() && TT()
        } else if (scanner.currentToken().lexem === "/") {
            scanner.nextToken()
            return F() && TT()
        } else if (scanner.currentToken().lexem === "^") {
            scanner.nextToken()
            return F() && TT()
        } else if (scanner.currentToken().lexem === "%") {
            scanner.nextToken()
            return F() && TT()
        }
        return true
    }

    fun T(): Boolean {
        return F() && TT()
    }

    fun EE(): Boolean {
        if (scanner.currentToken().lexem === "+") {
            println("Lexem: "+scanner.currentToken().lexem)
            scanner.nextToken()
            return T() && EE()
        } else if (scanner.currentToken().lexem === "-") {
            println("Lexem: "+scanner.currentToken().lexem)
            scanner.nextToken()
            return T() && EE()
        }
        return true
    }

    fun E(): Boolean {
        return T() && EE()
    }

    fun parse():Boolean{
        return C()&&scanner.inputString[scanner.inputString.length-1]==scanner.currentToken().lexem[0]
    }
}