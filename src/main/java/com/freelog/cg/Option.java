package com.freelog.cg;

import java.util.Map;

public class Option {
    public enum OptionArgType {BOOL, STRING}

    String fieldName;
    OptionArgType argType;
    String description;

    public Option(final String fieldName, final String description) {
        this(fieldName, OptionArgType.BOOL, description);
    }

    public Option(final String name, final OptionArgType argType, final String description) {
        this.fieldName = name;
        this.argType = argType;
        this.description = description;
    }

    public static Map<String, Option> optionDefs = Map.ofEntries(
        Map.entry("-sn", new Option("serviceName", OptionArgType.STRING, "specify coloring definition of the compiler to be generated")),
        Map.entry("-o", new Option("outputDir", OptionArgType.STRING, "specify location to generate compilers or grammars")),
        Map.entry("-t", new Option("targetLang", OptionArgType.STRING, "specify target language of the generated compilers")),
        Map.entry("-help", new Option("help", OptionArgType.STRING, "print help messages"))
);
}
