package dz.calc.rpn;

class Token {
    public enum TokenType{
        Operand,
        Operator,
        Brace,
        PlaceHolder
    }

    Token(TokenType type, String value){
        this.type = type;
        this.value = value;
    }

    TokenType type;
    String value;

    @Override
    public String toString(){
        return String.format("%1$9s:%2$2s", type, value);
    }

}
