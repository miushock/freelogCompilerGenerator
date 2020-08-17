grammar Event;
import LexToken, Event;

event : EVENT_SERVICE_NAME ('.' event_path)? '.' event_name;

event_path : ID ('.' ID)* ;

event_name : ID ;