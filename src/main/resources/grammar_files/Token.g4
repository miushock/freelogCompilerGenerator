lexer grammar Token.g4

SELF : 'self';
TERMINATE : 'terminate';

SUBJECT_ID : '*' (HEX_DIGIT)+;
UOID : '@' ID;
ACCOUNT_NUMBER : '#' (ALPHABET|DIGIT)+;
SUBJECT_SERVICE_NAME : '^' ID;
EVENT_SERVICE_NAME: '~' ID;

ANY_CONTRACT : 'AnyContract';
WITH: 'with';
OF: 'of';

USER_ID : PHONE_NUMBER_CN_MOBILE | EMAIL;

FEATHER : 'feather' ;
BARB : 'barb' ;

INT:  DIGIT+;
MONEY_AMOUNT : '$' DIGIT+ ('.' DIGIT DIGIT?)?;

TIME : TWO_DIGITS ':' TWO_DIGITS (':' TWO_DIGITS)?;
DATE : FOUR_DIGITS '-' TWO_DIGITS '-' TWO_DIGITS;

PERIOD : ('cycle'|'cycles') | ('week'|'weeks') | ('month'|'months') | ('year'|'years')

PHONE_NUMBER_CN_MOBILE : ELEVEN_DIGITS;
EMAIL : [a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+);

TWO_DIGITS : DIGIT DIGIT ;
THREE_DIGITS : TWO_DIGITS DIGIT;
FOUR_DIGITS : TWO_DIGITS TWO_DIGITS;
NIGHT_DIGITS : FOUR_DIGITS FOUR_DIGITS DIGIT;
ELEVEN_DIGITS : FOUR_DIGITS FOUR_DIGITS THREE_DIGITS;

ID
  : ALPHABET (ALPHABET | INT | '_' | '-')*
  ;

WS  : [ \t\r\n]+ -> skip;

fragment DIGIT : [0-9];
fragment ALPHABET : [a-zA-Z];
fragment HEX_DIGIT : [0-9a-fA-F];


fragment A : ('A'|'a');
fragment B : ('B'|'b');
fragment C : ('C'|'c');
fragment D : ('D'|'d');
fragment E : ('E'|'e');
fragment F : ('F'|'f');
fragment G : ('G'|'g');
fragment H : ('H'|'h');
fragment I : ('I'|'i');
fragment J : ('J'|'j');
fragment K : ('K'|'k');
fragment L : ('L'|'l');
fragment M : ('M'|'m');
fragment N : ('N'|'n');
fragment O : ('O'|'o');
fragment P : ('P'|'p');
fragment Q : ('Q'|'q');
fragment R : ('R'|'r');
fragment S : ('S'|'s');
fragment T : ('T'|'t');
fragment U : ('U'|'u');
fragment V : ('V'|'v');
fragment W : ('W'|'w');
fragment X : ('X'|'x');
fragment Y : ('Y'|'y');
fragment Z : ('Z'|'z');