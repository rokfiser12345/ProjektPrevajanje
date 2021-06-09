open class Statement {
    var leftSideVariable=Variable()
    var operator=""
    var rightSideValue=""
    var rightSideVariable=Variable()
    var returnValue=false
    override fun toString(): String {
        if(returnValue){
            return "return $returnValue"
        }
        return if(rightSideValue!=""&&rightSideVariable.name!=""){
            rightSideValue=""
            leftSideVariable.toString()+operator+rightSideVariable.toString()
        } else{
            leftSideVariable.toString()+operator+rightSideValue+rightSideVariable.toString()
        }
    }
}