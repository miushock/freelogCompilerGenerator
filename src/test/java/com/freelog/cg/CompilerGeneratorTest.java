package com.freelog.cg;

import org.junit.Test;
//import static org.junit.Assert.*;


/**
 * Unit test for simple App.
 */
public class CompilerGeneratorTest 
{

    @Test
    public void generateJavaScriptTarget(){
        CompilerGeneratorBuilder builder = new CompilerGeneratorBuilder();
        CompilerGenerator cg = builder.setServiceName("userGroup").setTargetLang("JavaScript").setOutputDir("./target/js").setGrammarDir("./target/generated_grammar").build();
        cg.generate();
    }

    @Test
    public void generateJavaTarget(){
        CompilerGeneratorBuilder builder = new CompilerGeneratorBuilder();
        CompilerGenerator cg = builder.setServiceName("userGroup").setTargetLang("Java").setOutputDir("./target/java").setGrammarDir("./target/generated_grammar").build();
        cg.generate();
    }
}
