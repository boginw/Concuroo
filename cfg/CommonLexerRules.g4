lexer grammar CommonLexer;

/* Lexer rules */
NUMBER     : [0-9]+ ;
OPERATOR   : '+'|'-'|'*'|'%';
WHITESPACE : SPACE | NEWLINE | TAB;
SPACE      : ' ' -> skip ;
NEWLINE    : '\n' -> skip ;
TAB        : '\t' -> skip ;