grammar Mu;

parse
 : block EOF
 ;

block
 : stat*
 ;

stat
 : assignment
 | if_stat
 | while_stat
 | print
 | change_self_stat
 | for_stat
 | OTHER {System.err.println("unknown char: " + $OTHER.text);}
 ;

assignment
 : ID ASSIGN expr SCOL
 ;


if_stat
 : IF condition_block (ELSE IF condition_block)* (ELSE stat_block)?
 ;

 for_stat
 : FOR OPAR for_init_stat for_condition_stat for_recurrent_stat CPAR
  stat_block
 ;

for_init_stat
: assignment
|
;

for_condition_stat
: expr SCOL
|
;

for_recurrent_stat
: expr
|
;

change_self_stat
 :expr SCOL
 ;

condition_block // (condidtion) { statment; }
 : expr stat_block
 ;

stat_block
 : OBRACE block CBRACE // { block }
 | stat
 ;

while_stat
 : WHILE expr stat_block
 ;

print
 : PRINT expr SCOL  // 打印算子, log(a+b)
 ;

expr: expr POW<assoc=right> expr        #powExpr
 | MINUS expr                           #unaryMinusExpr // -234
 | NOT expr                             #notExpr
 | expr op=(MULT | DIV | MOD) expr      #multiplicationExpr
 | expr op=(PLUS | MINUS) expr          #additiveExpr
 //| logical_expr                         #logicalExpr
 | expr op=(LTEQ | GTEQ | LT | GT) expr #relationalExpr
 | expr op=(EQ | NEQ) expr              #equalityExpr
 //| relation_expr                        #relationAllExpr
 | expr AND expr                        #andExpr
 | expr OR expr                         #orExpr
 | atom                                 #atomExpr
 | change_self_expr                     #changeSelfExpr
 ;

//logical_expr
// : expr op=(LTEQ | GTEQ | LT | GT) expr #relationalExpr
// | expr op=(EQ | NEQ) expr              #equalityExpr
// ;

/*relation_expr
 : expr AND expr                        #andExpr
 | expr OR expr                         #orExpr
 ;*/

atom
 : OPAR expr CPAR #parExpr // ( expr )
 | (INT | FLOAT)  #numberAtom // (3)
 | (TRUE | FALSE) #booleanAtom // (true)
 | ID             #idAtom // a
 | STRING         #stringAtom // "abc"
 | NIL            #nilAtom // nil
 ;

change_self_expr
    : op=(PLUS_PLUS|MINUS_MINUS) ID # changeThenGet
    | ID op =(PLUS_PLUS|MINUS_MINUS) # getThenChange
    ;

OR : '||';
AND : '&&';
EQ : '==';
NEQ : '!=';
GT : '>';
LT : '<';
GTEQ : '>=';
LTEQ : '<=';
PLUS : '+';
PLUS_PLUS: '++';
MINUS : '-';
MINUS_MINUS : '--';
MULT : '*';
DIV : '/';
MOD : '%';
POW : '^';
NOT : '!';

SCOL : ';';
ASSIGN : '=';
OPAR : '(';
CPAR : ')';
OBRACE : '{';
CBRACE : '}';

TRUE : 'true';
FALSE : 'false';
NIL : 'nil';
IF : 'if';
ELSE : 'else';
WHILE : 'while';
PRINT : 'print';
FOR: 'for';

ID
 : [a-zA-Z_] [a-zA-Z_0-9]*
 ;

INT
 : [0-9]+
 ;

FLOAT
 : [0-9]+ '.' [0-9]*
 | '.' [0-9]+
 ;

STRING
 : '"' (~["\r\n] | '""')* '"'
 ;
COMMENT
 : '#' ~[\r\n]* -> skip
 ;
SPACE
 : [ \t\r\n] -> skip
 ;
OTHER
 : .
 ;
