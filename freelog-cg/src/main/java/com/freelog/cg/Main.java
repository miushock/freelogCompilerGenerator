package com.freelog.cg;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

class Main {
    public static void main(String[] args) {
        CompilerGeneratorBuilder cg_builder = new CompilerGeneratorBuilder();
        CompilerGenerator cg = cg_builder
                                .setColor("user")
                                .setTemplateDir("./grammar_templates")
                                .build();
        
        cg.renderGrammar();
        cg.parseGrammar();
    }

    public static Map<String, String> parseArgs (String[] args) {
        Map<String, String> argMap = new HashMap<String, String>();
        List<String> argList = Arrays.asList(args);
        ListIterator<String> iterator = argList.listIterator();

        while(iterator.hasNext()) {
            String option = iterator.next();
            if (argMap.size() == 0 && option == "-help") {
                return Map.ofEntries(Map.entry("-help", ""));                
            }
            if (option.charAt(0)!='-' || option == "-help") {
                return Map.ofEntries(Map.entry("-help", "invalid option: "+option));
            }
            if (!iterator.hasNext()) {
                return Map.ofEntries(Map.entry("-help", "missing operand of option:"+ option));
            }
            String operand = iterator.next();
            if (operand.charAt(0)=='-') {
                return Map.ofEntries(Map.entry("-help", "invalid operand:"+ operand));
            }
            argMap.put(option, operand);
        }
        return argMap;
    }
}