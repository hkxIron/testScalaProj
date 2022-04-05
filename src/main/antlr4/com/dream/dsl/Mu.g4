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
 | OTHER {System.err.println("unknown char: " + $OTHER.text);}
 ;

assignment
 : ID ASSIGN expr SCOL
 ;

if_stat
 : IF condition_block (ELSE IF condition_block)* (ELSE stat_block)?
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

expr
 : expr POW<assoc=right> expr           #powExpr
 //: expr POW expr           #powExpr
 | MINUS expr                           #unaryMinusExpr // -234
 | NOT expr                             #notExpr
 | expr op=(MULT | DIV | MOD) expr      #multiplicationExpr
 | expr op=(PLUS | MINUS) expr          #additiveExpr
 | expr op=(LTEQ | GTEQ | LT | GT) expr #relationalExpr
 | expr op=(EQ | NEQ) expr              #equalityExpr
 | expr AND expr                        #andExpr
 | expr OR expr                         #orExpr
 | atom                                 #atomExpr
 ;

atom
 : OPAR expr CPAR #parExpr // ( expr )
 | (INT | FLOAT)  #numberAtom // (3)
 | (TRUE | FALSE) #booleanAtom // (true)
 | ID             #idAtom // a
 | STRING         #stringAtom
 | NIL            #nilAtom
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
MINUS : '-';
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
