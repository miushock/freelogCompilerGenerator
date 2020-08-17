policy_grammar(serviceName, keep_id) ::= <<
grammar <serviceName>Policy;

import <serviceName>, Event, Expression;

policy : requirement declaration_section? state_definition_section EOF;

requirement : REQUIRE (contract_with_subject);

contract_with_subject : 'contract' 'with' subject;

declaration_section: declaration_statements+ ;

declaration_statements
  : expression_declaration
  | contract_account_declaration
  ;

state_definition_section
  :  state_definition+
  ;

state_definition
  : service_state? state_name ':' state_transition+
  ;

state_transition
  : 'proceed' 'to' state_name 'on' event
  | TERMINATE
  ;

state_name : ID ;

currency_unit : FEATHER | BARB ;

resource_id : RESOURCE_ID_TOKEN { <keep_id> };

>>