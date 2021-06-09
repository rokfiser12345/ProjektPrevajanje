class Variable (){
    var type:String=""
    var name:String=""
    var properties=ArrayList<Property>()
    override fun toString(): String {
        if(type!=""){
            return "$type $name"
        }
        var returnString=name
        for(property in properties){
            returnString+=property.name
            returnString+=property.indeks
        }
        return returnString
    }
}