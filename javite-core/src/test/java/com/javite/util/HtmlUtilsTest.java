package com.javite.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class HtmlUtilsTest {

    @Test
    void testGenerateScriptTag() {
        String src = "http://localhost:5173/src/main.js";
        String expectedTag = "<script type=\"module\" src=\"http://localhost:5173/src/main.js\"></script>";
        assertThat(HtmlUtils.generateScriptTag(src)).isEqualTo(expectedTag);
    }

    @Test
    void testGenerateLinkTag() {
        String href = "/static/css/style.css";
        String expectedTag = "<link rel=\"stylesheet\" href=\"/static/css/style.css\" />";
        assertThat(HtmlUtils.generateLinkTag(href)).isEqualTo(expectedTag);
    }

}
