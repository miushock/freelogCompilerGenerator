package com.freelog.cg;

import org.antlr.v4.*;
import org.stringtemplate.v4.*;

import java.util.*;
import static java.util.Map.*;

import java.io.IOException;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;

/**
 * 
 *
 */
public class CompilerGenerator 
{
    private String templateDir;
    private String outputDir;
    private String startingRule = "policy_grammar";
    private String color;
    private String grammarFile;
    private String targetLang;

    private Tool tool;
    private String [] toolArgs;

    private Map<String, Map<String, String>> all_injections = TargetDependentInjection.injections;

    public static enum OptionArgType { NONE, STRING } // NONE implies boolean
	public static class Option {
		String name;
		OptionArgType argType;
		String description;

		public Option(final String name, final String description) {
			this(name, OptionArgType.NONE, description);
		}

		public Option(final String name, final OptionArgType argType, final String description) {
			this.name = name;
			this.argType = argType;
			this.description = description;
		}
    }
    public static Map<String, Option> optionDefs = Map.ofEntries(
        entry("color",          new Option("-c", OptionArgType.STRING, "specify coloring definition of the compiler to be generated")),
        entry("outputDir",      new Option("-o", OptionArgType.STRING, "specify location to generate compilers or grammars")),
        entry("target",         new Option("-t", OptionArgType.STRING, "specify target language of the generated compilers"))
    );

    public static void main(String[] args )
    {
        CompilerGenerator cg = new CompilerGenerator("./grammar_templates", "User", "./target/test-classes/generated_grammar/resource_policy.g4", "./target/test-classes", "JavaScript");
        cg.renderGrammar("Resource");
        cg.parseGrammar();
    }

    public CompilerGenerator() {

    }

    public CompilerGenerator(String templateDir, String color) {
        this(templateDir, color, "grammar", ".");
    }

    public CompilerGenerator(String templateDir, String color, String grammarFile, String outputDir) {
        this(templateDir, color, grammarFile, outputDir, "JavaScript");
    }

    public CompilerGenerator(String templateDir, String color, String grammarFile, String outputDir, String targetLang) {
        this.templateDir = templateDir;
        this.outputDir = outputDir;
        this.color = color;
        this.targetLang = targetLang;
        this.grammarFile = grammarFile;
        this.toolArgs = new String[] {this.grammarFile, "-o", outputDir+"/targets/"+targetLang, "-Dlanguage="+targetLang};
    }

    public String renderGrammar(String color) {
        STGroup stg = new STGroupDir(this.templateDir);
        ST st = stg.getInstanceOf(startingRule);
        st.add("color", this.color);
        Map<String, String> injections = all_injections.get(this.targetLang);
        System.out.println(injections);
        for (Map.Entry<String, String> entry: injections.entrySet()){
            st.add(entry.getKey(), entry.getValue());
        }
        String grammar = st.render();
        try {
            writeInOutputDir("generated_grammar/resource_policy.g4", grammar);
        } catch (IOException e) {
            System.out.println(e);
        }
        return grammar;
    }

    public boolean parseGrammar() {
        this.tool = new Tool(this.toolArgs);
        this.tool.processGrammarsOnCommandLine();
        return true;
    }

    public String generateCompiler(String target) {

        return "";
    }

    private void writeInOutputDir(String filename, String content) throws IOException{
        Path path = Paths.get(this.outputDir, filename).toAbsolutePath();
        
        System.out.println(path);
        try {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
            Path file = Paths.get(outputDir+"/"+filename);
            Files.writeString(file, content, StandardCharsets.UTF_8);
        } catch (FileAlreadyExistsException e){}
    }
}
