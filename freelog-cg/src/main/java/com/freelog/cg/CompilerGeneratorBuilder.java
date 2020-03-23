package com.freelog.cg;


public class CompilerGeneratorBuilder {

    private String templateDir;
    private String outputDir;
    private String color;
    private String grammarFile;
    private String targetLang;
    private String targetDir;

    public CompilerGenerator build() 
    {
        CompilerGenerator cg = new CompilerGenerator(this.templateDir, this.color, this.grammarFile, this.outputDir, this.targetLang, this.targetDir);
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

    public CompilerGeneratorBuilder setTargetDir(String targetDir) {
        this.targetDir = targetDir;
        return this;
    }

    public CompilerGeneratorBuilder setGrammarFile(String grammarFile) {
        this.grammarFile = grammarFile;
        return this;
    }
}