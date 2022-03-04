grammar CSV;

file : hdr row+ ;
hdr : row ;

row: field (',' field)* NL ;

field: TEXT # text
    | STRING # string
    |        # empty
    ;

TEXT: ~[,\n\r"]+ ; // text是不带引号的文本
STRING: '"' ('""'|~'"')* '"'; // 带有引号的文本
NL: '\r'?'\n';