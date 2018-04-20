grammar Concuroo;

// Expressions
primaryExpression
    :   Identifier
    |   ConstantLiteral
    |   StringLiteral+
    |   '(' expression ')'
    ;

postfixExpression
    :   primaryExpression
    |   postfixExpression '[' expression ']'
    |   postfixExpression '(' argumentExpressionList? ')'
    |   postfixExpression '++'
    |   postfixExpression '--'
    ;

argumentExpressionList
    :   assignmentExpression
    |   argumentExpressionList ',' assignmentExpression
    ;

unaryExpression
    : postfixExpression
    | unaryOperator castExpression
    | CompoundOperator unaryExpression
    | 'sizeof' unaryExpression
    | 'sizeof' '(' typeSpecifier ')'
    ;

unaryOperator
  : '&'
  | '*'
  | '+'
  | '-'
  | '!'
  ;

castExpression
    : '(' typeSpecifier ')' castExpression
    | unaryExpression
    ;

multiplicativeExpression
    :   castExpression
    |   multiplicativeExpression '*' castExpression
    |   multiplicativeExpression '/' castExpression
    |   multiplicativeExpression '%' castExpression
    ;

additiveExpression
    : multiplicativeExpression
    | additiveExpression '+' multiplicativeExpression
    | additiveExpression '-' multiplicativeExpression
    ;

relationalExpression
    :   additiveExpression
    |   relationalExpression '<' additiveExpression
    |   relationalExpression '>' additiveExpression
    |   relationalExpression '<=' additiveExpression
    |   relationalExpression '>=' additiveExpression
    ;

equalityExpression
    :   relationalExpression
    |   equalityExpression '==' relationalExpression
    |   equalityExpression '!=' relationalExpression
    ;

logicalAndExpression
    :   equalityExpression
    |   logicalAndExpression '&&' equalityExpression
    ;

logicalOrExpression
  : logicalAndExpression
  | logicalOrExpression '||' logicalAndExpression
  ;

assignmentExpression
    :   logicalOrExpression
    |   unaryExpression '=' assignmentExpression
    |   Number // for
    ;

expression
    :   assignmentExpression
    |   expression ',' assignmentExpression
    ;

// Declarations

declarationStatement
  : declarationSpecifiers initDeclarator ';'
  ;

functionDefinition
  : declarationSpecifiers? declarator declarationList? compoundStatement
  ;

declarationList
  : declarationStatement
  | declarationList declarationStatement
  ;

initDeclarator
  : declarator
  | declarator '=' initializer
  ;

declarator
  : pointer? directDeclarator
  ;

 directDeclarator
  : Identifier
  | '(' declarator ')'
  | directDeclarator '[' assignmentExpression? ']'
  | directDeclarator '(' parameterTypeList ')'
  | directDeclarator '(' identifierList? ')'
  ;

parameterTypeList
  : parameterList
  | parameterList ',' '...'
  ;

parameterList
  : parameterDeclaration
  | parameterList ',' parameterDeclaration
  ;

parameterDeclaration
  : declarationSpecifiers declarator
  ;

identifierList
  : Identifier
  | identifierList ',' Identifier
  ;

declarationSpecifiers
  : declarationSpecifier+
  ;

declarationSpecifier
  : typeSpecifier
  | typeQualifier
  | typeModifier
  ;

initializer
  : assignmentExpression
  | '{' initializerList '}'
  | '{' initializerList ',' '}'
  ;

initializerList
  : initializer
  | initializerList ',' initializer
  ;

// Statement
statement
  : compoundStatement
  | expressionStatement
  | selectionStatement
  | iterationStatement
  | jumpStatement
  | declarationStatement
  ;

compoundStatement
  : '{' statementList? '}'
  ;

statementList
  : statement
  | statementList statement
  ;

expressionStatement
  : expression? ';'
  ;

selectionStatement
  : ifStatement
  ;

ifStatement
  : 'if' '(' expression ')' statement
  | ifStatement 'else' statement
  ;

iterationStatement
  : 'while' '(' expression ')' statement
  ;

jumpStatement
  : 'continue' ';'
  | 'break' ';'
  | 'return' expression? ';'
  ;

// MISC
typeSpecifier
  : 'char'
  | 'bool'
  | 'double'
  | 'int'
  | 'void'
  ;

pointer
  : '*' typeQualifierList?
  ;

typeModifier
  : 'long'
  ;

typeQualifierList
  : typeQualifier
  | typeQualifierList typeQualifier
  ;

typeQualifier
  : 'const'
  ;





// START STUFF

start
  : translationUnit? EOF
  ;

translationUnit
  :   externalDeclaration
  |   translationUnit externalDeclaration
  ;

externalDeclaration
    :   functionDefinition
    |   declarationStatement
    |   ';' // stray ;
    ;




/* Literals */
StringLiteral : '"' SCharSeq? '"';



ConstantLiteral
  : BoolLiteral
  | CharLiteral
  | IntLiteral
  | DoubleLiteral
  ;

ComparisonOperator
  : '!='
  | '>'
  | '<'
  | '>='
  | '<='
  | '=='
  ;

CompoundOperator
  : '++'
  | '--'
  ;

Identifier
  : (Letter | '_') (AlphaNum | '_')*
  ;

/* Lexer rules */
NewLine        : ('\r' '\n' | '\n') -> skip;
Whitespace     : [ \t]+ -> skip;
LineComment    : '//' ~[\r\n]* -> skip;
BlockComment   : '/*' .*? '*/' -> skip;

Number: Digit+;

fragment
Letter         : [a-zA-Z];

fragment
Word           : Letter+;

fragment
AlphaNum       : Digit | Letter;

fragment
AlphaNumSeq    : AlphaNum+;

fragment
EscapeSeq      : '\\' ['"?abfnrtv\\];

fragment
SChar          : ~["\\\r\n]
               | EscapeSeq
               | '\\\n'
               | '\\\r\n'
               ;

fragment
SCharSeq       : SChar+;

fragment
Digit: [0-9];



fragment
IntLiteral : Number;

fragment
DoubleLiteral
  : Number '.' Number
  | Number '.'
  | '.' Number
  ;

fragment
BoolLiteral : 'true' | 'false';

fragment
CharLiteral : '\'' SChar? '\'';