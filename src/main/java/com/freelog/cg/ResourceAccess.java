package com.freelog.cg;

import java.util.stream.Stream;
import java.net.URISyntaxException;
import java.net.URI;
import java.io.*;
import java.nio.file.*;
import java.util.Collections;

class ResourceAccess {
    public static Stream<Path> jarFolderWalkStream (String folder_name) throws IOException, URISyntaxException{    
        URI uri = ResourceAccess.class.getClassLoader().getResource(folder_name).toURI();
        Path path = Paths.get(uri);
        return Files.walk(path, 1);
    }

    public static InputStream getJarStreamReader(String fileName) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        System.out.println(fileName);
        InputStream input = cl.getResourceAsStream(fileName);
        return input == null ? ResourceAccess.class.getResourceAsStream(fileName) : input;
    }

    public static void walkResource (String scope, SimpleFileVisitor<Path> visitor) throws URISyntaxException, IOException {
        URI uri = ResourceAccess.class.getClassLoader().getResource(scope).toURI();
        try (FileSystem fileSystem = (uri.getScheme().equals("jar") ? FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap()) : null)) {
            System.out.println("in iter");
            Path grammarDir = (fileSystem == null)? Paths.get(uri) : fileSystem.getPath(scope);
            Files.walkFileTree(grammarDir, visitor);
        }
    }
}