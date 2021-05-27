grammar ArithmeticEvaluator;

start: expression | EOF ;

expression: '-' expression                          # UNARYGROUP    |
    expression '!'                                  # FACTGRP       |
    expression op1 expression                       # OPERAND1GRP   |
    expression op2 expression                       # OPERAND2GRP   |
    sqrt '(' expression ')'                         # SQRTGRP       |
    root '(' expression ',' expression ')'          # ROOTGRP       |
    log  '(' expression ',' expression ')'          # LOGGRP        |
    ln   '('  expression ')'                        # LNGRP         |
    '(' expression ')'                              # PARENGRP      |
    NUM                                             # NUMBERGRP
    ;

sqrt   : 'sqrt' ;
root   : 'root' ;
log    : 'log'  ;
ln     : 'ln'   ;
op2    :  '+' | '-' ;
op1    :  '^' | '*' | '/' | '%' ;

//---------------------------------------------------------------
NUM    :  '0'..'9'+ | '0'..'9'+'.''0'..'9'* | '.''0'..'9'+ | 'pi' | 'e';
WS     : [ \r\n\t] + -> skip ;
