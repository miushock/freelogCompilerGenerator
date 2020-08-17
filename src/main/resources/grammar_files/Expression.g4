grammar Expression;
import LexToken, EnvironmentVariable;

expression_test : expression + EOF;

expression_call_or_literal
  : expression_call
  | expression
  ;

expression_assignment: expression_handle '(' (ID (',' ID)*)* ')' '=' expression;

expression_call : expression_handle '(' (expression_call_argument (',' expression_call_argument)*)* ')' ;

expression_call_argument
  : INT
  | environment_variable
  ;

expression_handle : ID ;

expression
   : multiplyingExpression ((PLUS | MINUS) multiplyingExpression)*
   ;

multiplyingExpression
   : powExpression ((TIMES | DIV) powExpression)*
   ;

powExpression
   : signedAtom (POW signedAtom)*
   ;

signedAtom
   : PLUS signedAtom
   | MINUS signedAtom
   | built_in_function
   | atom
   ;

built_in_function
  : funcname LPAREN expression (COMMA expression)* RPAREN
  ;

funcname
  : SUM
  ;


atom
  : scientific
  | constant
  | LPAREN expression RPAREN
  | INT
  | variable
  ;

scientific
  : SCIENTIFIC_NUMBER
  ;

constant
  : PI
  | EULER
  ;

variable : ID ;