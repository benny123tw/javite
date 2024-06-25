package com.javite.spring.tags;

import com.fasterxml.jackson.databind.JsonNode;
import com.javite.config.ViteProperties;
import com.javite.util.HtmlUtils;
import com.javite.util.ManifestUtils;
import jakarta.servlet.ServletContext;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;

/**
 * Custom JSP tag to import Vite-generated assets.
 * <p>
 * This tag handles the inclusion of Vite-generated JavaScript and CSS assets in JSP pages, dynamically determining the correct assets based on the environment
 * (development or production).
 *
 * @author Benny Yen
 * @since 0.1.0
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

    /**
     * Sets the entry point for the Vite assets.
     *
     * @param entry the entry point for the Vite assets
     */
    public void setEntry(String entry) {
        this.entry = entry;
    }

    /**
     * Sets the path to the Vite manifest file.
     *
     * @param manifestPath the path to the Vite manifest file
     */
    public void setManifestPath(String manifestPath) {
        this.manifestPath = manifestPath;
    }

    /**
     * Sets the URL for the local Vite development server.
     *
     * @param localServerUrl the URL for the local Vite development server
     */
    public void setLocalServerUrl(String localServerUrl) {
        this.localServerUrl = localServerUrl;
    }

    /**
     * Sets the debug mode. If true, assets will be loaded from the local Vite development server.
     *
     * @param isDebug true for debug mode, false otherwise
     */
    public void setDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }

    /**
     * Sets the resource path for the Vite assets.
     *
     * @param resourcePath the resource path for the Vite assets
     */
    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    /**
     * Processes the tag by determining the environment and including the appropriate assets.
     *
     * @throws JspException if an error occurs during JSP processing
     * @throws IOException  if an I/O error occurs
     */
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
