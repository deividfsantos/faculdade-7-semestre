%%


%standalone
%ignorecase
%unicode

nl     = \n|\r|\r\n

letter = [a-zA-Z_]
digit  = [0-9]
id     = ({letter}|{digit})+ 

%%

({letter}:)?(\\)?({id}\\)*{id}("."{id})?$    {  System.out.println("caminho ok:\t " + yytext());   }

{nl}  {}
.+    {  System.out.println("caminho NOK:\t " + yytext());  }


