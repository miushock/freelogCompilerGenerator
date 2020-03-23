package com.freelog.cg;

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
}