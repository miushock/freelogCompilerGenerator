package com.freelog.cg;

import java.util.stream.Stream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URI;
import java.io.*;
import java.nio.file.*;
import java.util.Collections;

class ResourceAccess {
    public static Stream<Path> jarFolderWalkStream (String folder_name) throws IOException, URISyntaxException{    
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        URL url = cl.getResource(folder_name);
        Path path = Paths.get(url.toURI());
        return Files.walk(path, 1);
    }

    public static InputStream getJarStreamReader(String fileName) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        System.out.println(fileName);
        InputStream input = cl.getResourceAsStream(fileName);
        return input == null ? ResourceAccess.class.getResourceAsStream(fileName) : input;
    }

    public static void walkResource (String scope, SimpleFileVisitor<Path> visitor) throws URISyntaxException, IOException {
        System.out.println(scope);
        URI uri = ResourceAccess.class.getResource(scope).toURI();
        System.out.println("Starting from: " + uri);
        try (FileSystem fileSystem = (uri.getScheme().equals("jar") ? FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap()) : null)) {
            Path myPath = Paths.get(uri);
            Files.walkFileTree(myPath, visitor);
        }
    }
}