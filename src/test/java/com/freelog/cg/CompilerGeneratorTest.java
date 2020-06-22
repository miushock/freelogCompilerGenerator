package com.freelog.cg;

import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Unit test for simple App.
 */
public class CompilerGeneratorTest 
{

    @Test
    public void generateJavaScriptTarget(){
        CompilerGeneratorBuilder builder = new CompilerGeneratorBuilder();
        CompilerGenerator cg = builder.setColor("userGroup").setTargetLang("JavaScript").setOutputDir("./target/output").setGrammarDir("./target/generated_grammar").build();
        cg.generate();
    }

}
