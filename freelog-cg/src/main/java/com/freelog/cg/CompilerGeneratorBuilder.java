package com.freelog.cg;


import java.util.Map;
import java.lang.reflect.Field;


public class CompilerGeneratorBuilder {

    public String templateDir = "grammar_templates";
    public String outputDir = "output";
    public String color = "resource";
    public String grammarDir = "generated_grammars";
    public String targetLang = "JavaScript";

    public CompilerGenerator build() 
    {
        CompilerGenerator cg = new CompilerGenerator(this.color, this.grammarDir, this.outputDir, this.targetLang);
        return cg;
    }

    public CompilerGeneratorBuilder setColor(String color) {
        this.color = color;
        return this;
    }

    public CompilerGeneratorBuilder setTargetLang(String targetLang) {
        this.targetLang = targetLang;
        return this;
    }

    public CompilerGeneratorBuilder setOutputDir(String outputDir) {
        this.outputDir = outputDir;
        return this;
    }

    public CompilerGeneratorBuilder setGrammarDir(String grammarDir) {
        this.grammarDir = grammarDir;
        return this;
    }

    public CompilerGeneratorBuilder setFieldsFromOptions(Map<String, String> cli_options) {
        Class<? extends CompilerGeneratorBuilder> thisClass = this.getClass();;
        for (Map.Entry<String, String> entry : cli_options.entrySet()) {
            try {
                Option option = Option.optionDefs.get(entry.getKey());
                Field f = thisClass.getDeclaredField(option.fieldName);
                f.set(this, entry.getValue());
            }
            catch (Exception e) {
                System.err.println("can't access field");
            }
        }
        return this;
    }
}