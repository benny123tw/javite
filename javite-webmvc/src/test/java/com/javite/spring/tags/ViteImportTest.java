package com.javite.spring.tags;

import static org.mockito.Mockito.contains;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.javite.config.ViteProperties;
import jakarta.servlet.ServletContext;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.PageContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ViteImportTest {

    @Mock
    private PageContext pageContext;

    @Mock
    private JspWriter jspWriter;

    @Mock
    private ServletContext servletContext;

    @Mock
    private WebApplicationContext webApplicationContext;

    @Mock
    private ViteProperties viteProperties;

    @InjectMocks
    private ViteImport viteImport;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(pageContext.getServletContext()).thenReturn(servletContext);
        when(pageContext.getOut()).thenReturn(jspWriter);
        when(WebApplicationContextUtils.getWebApplicationContext(servletContext)).thenReturn(webApplicationContext);
        when(webApplicationContext.getBean(ViteProperties.class)).thenReturn(viteProperties);
    }

    @Test
    void testDoTagDebugMode() throws Exception {
        when(viteProperties.isDebug()).thenReturn(true);
        when(viteProperties.getLocalServerUrl()).thenReturn("http://localhost:5173");

        viteImport.setEntry("main.js");
        viteImport.setDebug(true);
        viteImport.doTag();

        verify(jspWriter).print("<script type=\"module\" src=\"http://localhost:5173/@vite/client\"></script>");
        verify(jspWriter).print("<script type=\"module\" src=\"http://localhost:5173/main.js\"></script>");
    }

    @Test
    void testDoTagProductionMode() throws Exception {
        when(viteProperties.isDebug()).thenReturn(false);
        when(viteProperties.getManifestPath()).thenReturn("/WEB-INF/dist/.vite/manifest.json");
        when(viteProperties.getResourcePath()).thenReturn("/resources");
        when(servletContext.getRealPath("/WEB-INF/dist/.vite/manifest.json")).thenReturn("src/test/resources/.vite/manifest.json");

        viteImport.setEntry("src/main.ts");
        viteImport.setDebug(false);
        viteImport.doTag();

        verify(jspWriter, times(1)).print(contains("assets/main-C7y8ECKo.js"));
        verify(jspWriter, times(1)).print(contains("assets/main-DnsH5c2P.css"));
    }
}
