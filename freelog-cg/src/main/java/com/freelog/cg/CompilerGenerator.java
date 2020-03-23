package com.freelog.cg;

import org.antlr.v4.Tool;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
//import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 *
 */
public class CompilerGenerator {
    private String templateDir;
    private String color;
//    private String grammarFile;
    private String targetLang;
//    private String targetDir;

    private Tool tool;
    private String[] toolArgs;

    private Map<String, Map<String, String>> all_injections = TargetDependentInjection.injections;

    public static enum OptionArgType {NONE, STRING} // NONE implies boolean
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
            Map.entry("-c", new Option("color", OptionArgType.STRING, "specify coloring definition of the compiler to be generated")),
            Map.entry("-o", new Option("-outputDir", OptionArgType.STRING, "specify location to generate compilers or grammars")),
            Map.entry("-t", new Option("-target", OptionArgType.STRING, "specify target language of the generated compilers"))
    );

    /**
     * 构造函数
     * @param templateDir 模板目录
     * @param color 染色
     * @param grammarFile g4文件入口
     * @param outputDir g4文件生成到目录
     * @param targetLang 目标语言
     * @param targetDir 目标目录
     */

    public CompilerGenerator() {}
    public CompilerGenerator(String templateDir, String color, String grammarFile, String outputDir, String targetLang, String targetDir) {
        this.templateDir = templateDir;
        this.color = color;
        this.targetLang = targetLang;
//        this.targetDir = targetDir;
//        this.grammarFile = grammarFile;
        this.toolArgs = new String[]{
                "-visitor",
                "-Dlanguage=" + targetLang,
                "-Xexact-output-dir",
                "-package",
                "pkg",
                "-o", targetDir,
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

    /**
     *
     */
    public void parseGrammar() {
        this.tool = new Tool(this.toolArgs);
        this.tool.processGrammarsOnCommandLine();
    }

//    public String generateCompiler(String target) {
//
//        return "";
//    }

    /**
     * @param filename 要生成的的文件名
     * @param content 要写入的内容
     * @throws IOException 抛错
     */
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

    private static String getArg(String[] args, String key) {
        for (String arg : args) {
            String prefix = "--" + key + "=";
            if (arg.startsWith(prefix)) {
                return arg.replace(prefix, "");
            }
        }
        switch (key) {
            case "color":
                return "Resource";
            case "targetLang":
                return "JavaScript";
            case "targetDir":
                return "gen";
            case "g4Files":
            case "g4Dir":
            default:
                return null;
        }
    }

    
}
