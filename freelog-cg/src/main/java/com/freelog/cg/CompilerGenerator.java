package com.freelog.cg;

import org.antlr.v4.Tool;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.*;
import java.util.*;

public class CompilerGenerator {
    public String color;
    public String grammarDir;
    public String targetLang;
    public String outputDir;

    public final Map<String, Map<String, String>> all_injections = TargetDependentInjection.injections;

    public final String templateResource = "grammar_templates";
    public final String grammarResource = "grammar_files";

    public CompilerGenerator() {}
    public CompilerGenerator(String color, String grammarDir, String outputDir, String targetLang) {
        this.color = color;
        this.targetLang = targetLang;
        this.outputDir = outputDir;
        this.grammarDir = grammarDir;
    }

    /*  two stages:
     *      1. generate grammar from templates and resource
     *          1.1 render templates
     *          1.2 simply copy over those grammar that are not templated
     *      2. compile grammar to generate compiler for specific target
     */
    public void generate() {
        renderGrammarFromTemplate();
        copyGrammar();
        parseGrammar();
    }

    public void renderGrammarFromTemplate() {
        STGroup stg = new STGroupDir("grammar_templates");
        String startingRule = "policy_grammar";
        ST st = stg.getInstanceOf(startingRule);
        st.add("color", this.color);

        Map<String, String> injections = all_injections.get(this.targetLang);
        for (Map.Entry<String, String> entry : injections.entrySet()) {
            st.add(entry.getKey(), entry.getValue());
        }

        String grammar = st.render();

        Path outputPath = Paths.get(this.grammarDir, "resource_policy.g4");
        System.out.println(outputPath);
        writeFile(outputPath, grammar);
    }

    private void copyGrammar(){
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**.g4");
        try {
            ResourceAccess.walkResource(this.grammarResource, new SimpleFileVisitor<Path>() { 
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    System.out.println("visited");
                    if (matcher.matches(file)){
                        System.out.println("gdir:"+CompilerGenerator.this.grammarDir);
                        Path dest = Paths.get(CompilerGenerator.this.grammarDir + "/" + file.getFileName());
                        copyFile(file, dest);
                    }
                    return FileVisitResult.CONTINUE;
                }
            }); 
        }
        catch (Exception e) {
            System.err.println("copy grammar:\n" + e);
        }
    }

    public void parseGrammar() {
        Path grammarPath = Paths.get(this.grammarDir, "resource_policy.g4");
        String [] toolArgs = new String[]{
            grammarPath.toString(),
            "-visitor",
            "-Dlanguage=" + this.targetLang,
            "-o", this.outputDir,
        };
        Tool tool = new Tool(toolArgs);
        tool.processGrammarsOnCommandLine();
    }


    private void writeFile(Path path, String content){
        try {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
            Files.writeString(path, content, StandardCharsets.UTF_8);
        } 
        catch (FileAlreadyExistsException e){
            System.err.println(e);
        }
        catch (IOException e){
            System.err.println(e);
        }

    }

    private void copyFile(Path source, Path dest){

        try {
            Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e){
            System.err.println(e);
        }
    }
}