package com.freelog.cg;

import java.util.Map;
import java.lang.reflect.Field;


public class CompilerGeneratorBuilder {

    private String templateDir;
    private String outputDir;
    private String color;
    private String grammarFile;
    private String targetLang;

    public CompilerGenerator build() 
    {
        CompilerGenerator cg = new CompilerGenerator(this.templateDir, this.color, this.grammarFile, this.outputDir, this.targetLang);
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

    public CompilerGeneratorBuilder setTemplateDir(String templateDir) {
        this.templateDir = templateDir;
        return this;
    }

    public CompilerGeneratorBuilder setGrammarFile(String grammarFile) {
        this.grammarFile = grammarFile;
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