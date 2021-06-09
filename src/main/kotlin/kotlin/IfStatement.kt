class IfStatement(): Statement() {
    var codeBlock=ArrayList<Statement>()
    var returnStatement= false
    fun print() {
        println("If statement condition: ")
        if(rightSideValue!=""&&rightSideVariable.name!=""){
            println("- $leftSideVariable$operator$rightSideVariable")
        }
        else{
            println("- $leftSideVariable$operator$rightSideVariable$rightSideValue")
        }
        if(codeBlock.size!=0) {
            println("If statement codeBlock statements: ")
            for (statement in codeBlock) {
                println("- $statement")
            }
        }
        if(returnStatement){
            println("Return value: ")
            println(returnStatement)
        }
    }
}