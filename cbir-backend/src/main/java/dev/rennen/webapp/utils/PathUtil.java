package dev.rennen.webapp.utils;

public class PathUtil {

    public static String pathConverter (String path) {
        return path.replace("/", "\\");
    }
}
