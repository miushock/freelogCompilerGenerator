package com.freelog.cg;

import java.io.IOException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {

        String[] needCopyFiles = new String[]{
                "grammar_templates/policy_grammar.st",
                "grammar_templates/coloring/exhibit_coloring.g4.st",
                "grammar_templates/coloring/resource_coloring.g4.st",
                "grammar_templates/coloring/user_group_coloring.g4.st",
        };
        for (String file : needCopyFiles) {
            CopyFile.copyFile(file, file);
        }

        System.out.println("Copy '.st' files done!");

        CompilerGenerator cg = new CompilerGenerator(
                "./grammar_templates",
                "generated_grammar/resource_policy.g4",
                "",
                getArg(args,"targetLang"),
                getArg(args, "targetDir"));
        cg.renderGrammar(getArg(args, "color"));
        cg.parseGrammar();
        System.out.println("End!");

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
