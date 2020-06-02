package com.freelog.cg;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;


/**
 * Unit test for simple App.
 */
public class ResourceAccessTest 
{
    @Test
    public void grammarWalkerTest() {
        try {
            Iterator<Path> iterator = ResourceAccess.jarFolderWalkStream("grammar_files").iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

    @Test
    public void rawWalkTest() {
        try {
            ResourceAccess.walkResource("grammar_files", new SimpleFileVisitor<Path>() { 
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    System.out.println(file);
                    return FileVisitResult.CONTINUE;
                }
            });
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }
}