class Token (var lexem: String="", var column: Int=0, var row: Int=0, var token: Int=0, var eof: Boolean=false){
    override fun toString():String{
        return "'$lexem' $token ($row,$column)$eof"
    }
}