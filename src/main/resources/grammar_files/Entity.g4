grammar Entity;
import LexToken;

subject_service :SUBJECT_SERVICE_NAME;
event_service : EVENT_SERVICE_NAME;

subject : subject_service '.' user_orgnization_name '.' SUBJECT_ID;
event : event_service '.' (event_path '.')? event_name;
event_path : (ID '.')+ ID;
event_name : ID;

policy : subject_service '.' user_orgnization_name '.' ID;

account: ACCOUNT_NUMBER;

user_orgnization_name: UOID;

