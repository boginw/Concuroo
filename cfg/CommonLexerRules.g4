lexer grammar CommonLexerRules;

/* Lexer rules */
NUMBER         : [0-9]+;
DECIMAL        : [0-9]+.[0-9]+;
CHAR           : [a-zA-Z];
STRING         : CHAR+;
ARITH_OPERATOR : '+'|'-'|'*'|'%'|'/'|'=';
COMPAR_OPERATOR: '<'|'>'|'<='|'>='|'=='|'!=';
BOOL_OPERATOR  : '!'|'&&'|'||';
POINT_OPERATOR : '&'|'*';
COMPND_OPERATOR: '++'|'--';
SINGLE_COMMENT : '//';
TERMINATOR     : ';';
TRUE           : 'true';
FALSE          : 'false';
WHITESPACE     : SPACE | NEWLINE | TAB;
SPACE          : ' ' -> skip ;
NEWLINE        : '\n' -> skip ;
TAB            : '\t' -> skip ;