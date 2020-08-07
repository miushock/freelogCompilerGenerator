parser grammar entities.g4;

subject_service :SUBJECT_SERVICE_ID;
event_service : EVENT_SERVICE_ID;

subject : subject_service '.' user_orgnization_name '.' SUBJECT_ID;
event : event_service '.' (event_path '.')? event_name;
event_path : (ID '.')+ ID;
event_name : ID;

policy : subject_service '.' user_orgnization_name '.' POLICY_ID;

account: ACCOUNT_NUMBER;

user_orgnization_name: UOID;

