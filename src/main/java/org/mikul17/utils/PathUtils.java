package org.mikul17.utils;

import java.nio.file.Path;

public class PathUtils {

    public static Path getFilePath(Class<?> clazz, String fileName) {
        String packagePath = clazz.getPackageName().replace('.', '/');
        return Path.of("src/main/java/" + packagePath + "/" + fileName);
    }
}
