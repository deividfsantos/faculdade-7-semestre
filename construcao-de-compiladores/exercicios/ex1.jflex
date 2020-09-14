%%

%public
%standalone
%ignorecase
%unicode
%line

nl        = \n|\r|\r\n
STRING    = [a-zA-Z_]*
NUMBER    = [0-9]+
VALUE     = ({STRING}|{NUMBER}|{OBJECT}|{ARRAY})
ELEMENTS  = ({ELEMENTS}","{VALUE}|{VALUE})
ARRAY     = ("["{ELEMENTS}"]")
MEMBERS   = ({STRING}":"{VALUE})|({STRING}":"{VALUE}","{MEMBERS})
OBJECT    = ("{"{MEMBERS}"}")
JSON      = ({ARRAY}|{OBJECT})

%%
{STRING}      {  System.out.println( "(" + yyline + ") tag: " + yytext()); } 
{OBJECT}      {  System.out.println( "(" + yyline + ") tag: " + yytext()); } 
{MEMBERS}     {  System.out.println( "(" + yyline + ") tag: " + yytext()); } 
{ARRAY}       {  System.out.println( "(" + yyline + ") tag: " + yytext()); } 
{ELEMENTS}    {  System.out.println( "(" + yyline + ") tag: " + yytext()); } 
{VALUE}       {  System.out.println( "(" + yyline + ") tag: " + yytext()); } 

[^]   {}
{nl}  {}
.+    {  System.out.println("caminho NOK:\t " + yytext());  }

