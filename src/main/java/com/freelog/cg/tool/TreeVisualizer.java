package com.freelog.cg.tool;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class TreeVisualizer {
    

    
    public static void main (String args[]) throws IllegalAccessException, InvocationTargetException, IOException,
            InstantiationException, IllegalArgumentException, NoSuchMethodException, SecurityException,
            ClassNotFoundException {
        CharStream charStream = CharStreams.fromPath(Paths.get(args[1]));
        Class <? extends Parser> parserClass = getParserClass(args[0]);
        Parser parser = getParserFromParserClass(parserClass);
        Lexer lexer = getLexerOfService(args[0]);
        Tree tree = parseForTree(parser, parserClass, lexer, charStream);
        TreeVisualizer.viewAST(Arrays.asList(parser.getRuleNames()), tree);
        
    }

    public TreeVisualizer(String contractServiceName) {

    }

    public static void viewAST(List<String> ruleNames, Tree tree) {
        JFrame frame = new JFrame("Antlr AST");
        JPanel panel = new JPanel();
        TreeViewer viewer = new TreeViewer(ruleNames,tree);
        viewer.setScale(1.5); 
        panel.add(viewer);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static Parser getParserFromParserClass (Class<? extends Parser> parserClass)
    throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException 
    {
        return parserClass.getConstructor(TokenStream.class).newInstance((TokenStream)null);
    }

    public static Parser getParserOfService (String serviceName) {
        String grammarName = serviceName+"Policy";
        Parser parser = null;
        Class<? extends Parser> parserClass = null;
        try {
            parserClass = TreeVisualizer.class.getClassLoader().loadClass(grammarName+"Parser").asSubclass(Parser.class);
            Constructor<? extends Parser> parserCtor = parserClass.getConstructor(TokenStream.class);
			parser = parserCtor.newInstance((TokenStream)null);
        }
        catch(Exception e) {
            System.err.println("cant load parser: " + e);
        }

        return parser;
    }

    public static Lexer getLexerOfService(String serviceName) {
        String grammarName = serviceName+"Policy";
        Lexer lexer = null;
        Class<? extends Lexer> lexerClass = null;
        try {
            lexerClass = TreeVisualizer.class.getClassLoader().loadClass(grammarName+"Lexer").asSubclass(Lexer.class);
            Constructor<? extends Lexer> lexerCtor = lexerClass.getConstructor(CharStream.class);
			lexer = lexerCtor.newInstance((TokenStream)null);
        }
        catch(Exception e) {
            System.err.println("cant load parser: " + e);
        }

        return lexer;
    }

    public static Class<? extends Parser> getParserClass (String serviceName) throws ClassNotFoundException {
        return  TreeVisualizer.class.getClassLoader().loadClass(serviceName+"PolicyParser").asSubclass(Parser.class);
    }

    public static ParserRuleContext parseForTree (Parser parser, Class<? extends Parser> parserClass, Lexer lexer, CharStream cstream)  throws IOException, IllegalAccessException, InvocationTargetException {
        ParserRuleContext tree = null;

        lexer.setInputStream(cstream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        tokenStream.fill();

        parser.setTokenStream(tokenStream);

        try {
            Method startRule = parserClass.getMethod("policy");
            System.out.println(startRule);
            tree = (ParserRuleContext)startRule.invoke(parser, (Object[])null);
        }
        catch (NoSuchMethodException e) {
            System.err.print("starting rule name(policy) not found");
        }

        return tree;
    }
}