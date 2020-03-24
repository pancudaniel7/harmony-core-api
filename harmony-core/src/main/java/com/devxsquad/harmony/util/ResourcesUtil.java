package com.devxsquad.harmony.util;

import java.io.File;
import java.net.URL;

import static java.lang.String.format;
import static java.util.Objects.isNull;

public class ResourcesUtil {
    public static File getFileFromResources(String fileName) {
        ClassLoader classLoader = ResourcesUtil.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (isNull(resource)) {
            throw new IllegalArgumentException(format("File %s is not found!", fileName));
        }
        return new File(resource.getFile());
    }
}
