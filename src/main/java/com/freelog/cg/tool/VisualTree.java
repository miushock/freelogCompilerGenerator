package com.freelog.cg.tool;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.antlr.runtime.tree.ParseTree;
import org.antlr.v4.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class VisualTree {
    //public void renderTree(){
    //    //show AST in GUI
    //    JFrame frame = new JFrame("Antlr AST");
    //    JPanel panel = new JPanel();
    //    panel.setPreferredSize(new Dimension(500,500));
    //    frame.add(panel);
    //    frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    //    frame.pack();
    //    frame.setVisible(true);
    //}
    public static void main (String args[]) {
        VisualTree vt = new VisualTree();
        vt.printAST(args[0], args[1]);
    }

    public void printAST(String color, CharStream cstream) {

  
    }

    public ParserRuleContext getTree (String color, CharStream cstream)  throws IOException, IllegalAccessException, InvocationTargetException {
        String grammarName = color+"Policy";
        ClassLoader cl = VisualTree.class.getClassLoader();
        Parser parser = null;
        Lexer lexer = null;
        ParserRuleContext tree = null;

		Class<? extends Lexer> lexerClass = null;
        try {
            lexerClass = cl.loadClass(grammarName+"Lexer").asSubclass(Lexer.class);
            Constructor<? extends Lexer> lexerCtor = lexerClass.getConstructor(CharStream.class);
            lexer = lexerCtor.newInstance(cstream);
        }
        catch (Exception e) {
            System.err.println("cant load lexer: " + e);
        }
        
        Class<? extends Parser> parserClass = null;
        try {
            parserClass = cl.loadClass(grammarName+"Parser").asSubclass(Parser.class);
            Constructor<? extends Parser> parserCtor = parserClass.getConstructor(TokenStream.class);
			parser = parserCtor.newInstance((TokenStream)null);
        }
        catch(Exception e) {
            System.err.println("cant load parser: " + e);
        }

        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        tokenStream.fill();
        
        try {
            Method startRule = parserClass.getMethod("policy");
            tree = (ParserRuleContext)startRule.invoke(parser, (Object[])null);
        }

        catch (NoSuchMethodException e) {
            System.err.print("starting rule name(policy) not found");
        }

        return tree;
    }
}