package com.freelog.cg;

import org.antlr.v4.Tool;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;


public class CompilerGenerator {
    public String templateDir;
    public String color;
    public String grammarFile;
    public String targetLang;
    public String outputDir;

    public Tool tool;
    public String[] toolArgs;

    private Map<String, Map<String, String>> all_injections = TargetDependentInjection.injections;

    public CompilerGenerator() {}
    public CompilerGenerator(String templateDir, String color, String grammarFile, String outputDir, String targetLang) {
        this.templateDir = templateDir;
        this.color = color;
        this.targetLang = targetLang;
        this.outputDir = outputDir;
        this.grammarFile = grammarFile;
        this.toolArgs = new String[]{
                "-visitor",
                "-Dlanguage=" + targetLang,
                "-o", outputDir,
                grammarFile
        };
    }

    public void renderGrammar() {
        STGroup stg = new STGroupDir(this.templateDir);
        String startingRule = "policy_grammar";
        ST st = stg.getInstanceOf(startingRule);
        st.add("color", this.color);

        Map<String, String> injections = all_injections.get(this.targetLang);
        for (Map.Entry<String, String> entry : injections.entrySet()) {
            st.add(entry.getKey(), entry.getValue());
        }

        String grammar = st.render();
        try {
            writeInOutputDir("generated_grammar/resource_policy.g4", grammar);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseGrammar() {
        this.tool = new Tool(this.toolArgs);
        this.tool.processGrammarsOnCommandLine();
    }


    private void writeInOutputDir(String filename, String content) throws IOException {
        File file = new File(filename);
        System.out.println(file);
        Files.createDirectories(Paths.get(file.getParent()));
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            writer.write(content);
        } catch (FileAlreadyExistsException e) {
            e.printStackTrace();
        }
    }    
}
