package com.javite.spring.tags;

import com.fasterxml.jackson.databind.JsonNode;
import com.javite.config.ViteProperties;
import com.javite.util.HtmlUtils;
import com.javite.util.ManifestUtils;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Custom JSP tag to import Vite-generated assets.
 */
public class ViteImport extends SimpleTagSupport {

    private static final Logger log = LoggerFactory.getLogger(ViteImport.class);

    @Nullable
    private String entry;

    @Nullable
    private String manifestPath;

    @Nullable
    private String localServerUrl;

    @Nullable
    private Boolean isDebug;

    @Nullable
    private String resourcePath;

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public void setManifestPath(String manifestPath) {
        this.manifestPath = manifestPath;
    }

    public void setLocalServerUrl(String localServerUrl) {
        this.localServerUrl = localServerUrl;
    }

    public void setDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public void doTag() throws JspException, IOException {
        ServletContext servletContext = ((PageContext) getJspContext()).getServletContext();
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        JspWriter out = getJspContext().getOut();

        if (ctx == null) {
            log.error("No WebApplicationContext found");
            throw new JspException("No WebApplicationContext found");
        }

        ViteProperties viteProperties = ctx.getBean(ViteProperties.class);

        isDebug = isDebug != null ? isDebug : viteProperties.isDebug();
        String effectiveManifestPath = manifestPath != null ? manifestPath : viteProperties.getManifestPath();
        String effectiveLocalServerUrl = localServerUrl != null ? localServerUrl : viteProperties.getLocalServerUrl();
        String effectiveResourcePath = resourcePath != null ? resourcePath : viteProperties.getResourcePath();

        if (isDebug) {
            handleDevEnvironment(out, effectiveLocalServerUrl);
        } else {
            handleProdEnvironment(out, servletContext, effectiveManifestPath, effectiveResourcePath);
        }
    }

    private void handleProdEnvironment(JspWriter out, ServletContext servletContext, String manifestFilePath, String resourcePath) throws IOException {
        JsonNode manifest = ManifestUtils.readManifest(servletContext.getRealPath(manifestFilePath));
        JsonNode entryNode = manifest.get(entry);

        if (entryNode != null) {
            printJsImport(out, servletContext, entryNode, resourcePath);
            printCssImports(out, servletContext, entryNode, resourcePath);
        } else {
            log.error("Entry not found in manifest: {}", entry);
            throw new IOException("Entry not found in manifest: " + entry);
        }
    }

    private void handleDevEnvironment(JspWriter out, String localServerUrl) throws IOException {
        String viteClientScriptTag = HtmlUtils.generateScriptTag(localServerUrl + "/@vite/client");
        String entryScriptTag = HtmlUtils.generateScriptTag(this.entry != null ? this.entry : "src/main.js");

        out.print(viteClientScriptTag);
        out.print(entryScriptTag);
    }

    private void printJsImport(JspWriter out, ServletContext servletContext, JsonNode entryNode, String resourcePath) throws IOException {
        String jsFile = entryNode.get("file").asText();
        String contextPath = servletContext.getContextPath();
        String entryScriptTag = HtmlUtils.generateScriptTag(contextPath + resourcePath + "/" + jsFile);

        out.print(entryScriptTag);
    }

    private void printCssImports(JspWriter out, ServletContext servletContext, JsonNode entryNode, String resourcePath) throws IOException {
        JsonNode cssFiles = entryNode.get("css");
        if (cssFiles != null) {
            String contextPath = servletContext.getContextPath();
            for (JsonNode cssFile : cssFiles) {
                String cssLinkTag = HtmlUtils.generateLinkTag(contextPath + resourcePath + "/" + cssFile.asText());
                out.print(cssLinkTag);
            }
        }
    }

}
