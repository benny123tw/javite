package com.javite.util;

/**
 * Utility methods for generating HTML tags for Vite assets.
 *
 * @author Benny Yen
 * @since 0.1.0
 */
public class HtmlUtils {

    /**
     * Generates an HTML script tag for the specified JavaScript file.
     *
     * @param src the source URL of the JavaScript file
     * @return the HTML script tag
     */
    public static String generateScriptTag(String src) {
        return "<script type=\"module\" src=\"" + src + "\"></script>";
    }

    /**
     * Generates an HTML link tag for the specified CSS file.
     *
     * @param href the URL of the CSS file
     * @return the HTML link tag
     */
    public static String generateLinkTag(String href) {
        return "<link rel=\"stylesheet\" href=\"" + href + "\" />";
    }
}
