package io.github.benny123tw.servlet.tags;

import io.github.benny123tw.servlet.TestApplication;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.PageContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockJspWriter;
import org.springframework.mock.web.MockPageContext;
import org.springframework.web.context.WebApplicationContext;

import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = TestApplication.class)
public class ViteImportTest {

    private static final Logger log = LoggerFactory.getLogger(ViteImportTest.class);
    @Autowired
    private WebApplicationContext webApplicationContext;

    private PageContext pageContext;
    private StringWriter stringWriter;
    private JspWriter jspWriter;

    @BeforeEach
    public void setup() {
        log.info("Setting up test");
        stringWriter = new StringWriter();
        jspWriter = new MockJspWriter(stringWriter);

        pageContext = new MockPageContext() {
            @Override
            public JspWriter getOut() {
                return jspWriter;
            }
        };
        pageContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext, PageContext.APPLICATION_SCOPE);
    }

    @Test
    public void testDevViteImportTag() throws Exception {
        ViteImport viteImport = new ViteImport();
        viteImport.setEntry("main.js");
        viteImport.setJspContext(pageContext);

        viteImport.doTag();

        String output = stringWriter.toString();
        assertTrue(output.contains("<script type=\"module\" src=\"http://localhost:5173/@vite/client\" defer></script>"));
        assertTrue(output.contains("<script type=\"module\" src=\"http://localhost:5173/main.js\" defer></script>"));
    }

    @Test
    public void testProdViteImportTag() throws Exception {
        ViteImport viteImport = new ViteImport();
        viteImport.setDebug(false);
        viteImport.setEntry("src/main.ts");
        viteImport.setLocalServerUrl("http://localhost:5173");
        viteImport.setManifestPath("/.vite/manifest.json");
        viteImport.setResourcePath("/public");
        viteImport.setJspContext(pageContext);

        viteImport.doTag();

        String output = stringWriter.toString();
        assertTrue(output.contains("<script type=\"module\" src=\"/public/assets/main-C7y8ECKo.js\" defer>"));
        assertTrue(output.contains("</script><link rel=\"stylesheet\" href=\"/public/assets/main-DnsH5c2P.css\"/>"));
    }
}
