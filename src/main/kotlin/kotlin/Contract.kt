import kotlin.contracts.contract

class Contract (){
    var contractName:String=""
    var contractVariables= ArrayList<Variable>()
    var contractStatements=ArrayList<IfStatement>()
    var contractReturn:Boolean=false
    override fun toString(): String {
        var returnString=contractName+contractVariables
        for(statement in contractStatements){
            returnString+=statement.toString()
        }
        returnString+=contractReturn
        return returnString
    }
    fun print(){
        println("CONTRACT MODEL: ")
        println("-----------------------------------------------------")
        println("Contract name: $contractName")
        println("Contract variables: ")
        for(variable in contractVariables){
            println("- $variable")
        }
        for(statement in contractStatements){
            statement.print()
        }
        println("Contract return value: $contractReturn")
        println("-----------------------------------------------------")
    }
}