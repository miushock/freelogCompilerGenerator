package com.freelog.cg;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Map;


/**
 * Unit test for simple App.
 */
public class CommandLineOptionParsingTest 
{
    @Test
    public void parse_empty () {
        String [] args = new String[]{};
        Map<String, String> argMap = Main.parseArgs(args);
        assertEquals("{}", argMap.toString());
    }

    @Test
    public void parse_help () {
        String [] args = new String[]{"-help"};
        Map<String, String> argMap = Main.parseArgs(args);
        assertEquals("{-help=}", argMap.toString());
    }

    @Test
    public void parse_multiple () {
        String [] args = new String[]{"-c", "User", "-t", "JavaScript", "-o", "./output"};
        Map<String, String> argMap = Main.parseArgs(args);
        assertEquals("{-o=./output, -c=User, -t=JavaScript}", argMap.toString());
    }

    @Test
    public void parse_wrong_args () {
        String [] args = new String[]{"User", "./output"};
        Map<String, String> argMap = Main.parseArgs(args);
        assertEquals("{-help=invalid option: User}", argMap.toString());
    }

    @Test
    public void setCGWithArgs () {
        String [] args = new String[]{"-c", "User", "-t", "JavaScript", "-o", "./output"};
        Map<String, String> argMap = Main.parseArgs(args);
        CompilerGeneratorBuilder builder = new CompilerGeneratorBuilder();
        CompilerGenerator cg = builder.setFieldsFromOptions(argMap).build();
        assertEquals("User", cg.color);
        assertEquals("JavaScript", cg.targetLang);
        assertEquals("./output", cg.outputDir);
    }

}