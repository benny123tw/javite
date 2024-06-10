package io.github.benny123tw.servlet.tags;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.benny123tw.servlet.config.ViteProperties;
import jakarta.servlet.ServletContext;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;
import java.io.File;
import java.io.IOException;
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

    /**
     * Sets the entry point for the Vite asset.
     *
     * @param entry the entry point of the Vite asset
     */
    public void setEntry(String entry) {
        this.entry = entry;
    }

    /**
     * Sets the path to the Vite manifest file.
     *
     * @param manifestPath the manifest file path
     */
    public void setManifestPath(String manifestPath) {
        this.manifestPath = manifestPath;
    }

    /**
     * Sets the local server URL for Vite development.
     *
     * @param localServerUrl the local server URL
     */
    public void setLocalServerUrl(String localServerUrl) {
        this.localServerUrl = localServerUrl;
    }

    /**
     * Sets the debug flag.
     *
     * @param isDebug the debug flag
     */
    public void setDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }

    /**
     * Sets the resource path.
     *
     * @param resourcePath the resource path
     */
    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    /**
     * Processes the custom tag to import Vite assets based on the environment.
     *
     * @throws JspException if a JSP error occurs
     * @throws IOException  if an I/O error occurs
     */
    @Override
    public void doTag() throws JspException, IOException {
        log.info("doTag() called");

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

        log.info("isDebug: {}", isDebug);
        log.debug("effectiveManifestPath: {}, effectiveLocalServerUrl: {}, effectiveResourcePath: {}", effectiveManifestPath, effectiveLocalServerUrl,
                effectiveResourcePath);

        if (isDebug) {
            log.info("Handling development environment");
            handleDevEnvironment(out, effectiveLocalServerUrl);
        } else {
            log.info("Handling production environment");
            handleProdEnvironment(out, servletContext, effectiveManifestPath, effectiveResourcePath);
        }
    }



    /**
     * Handles the production environment by loading assets from the manifest file.
     *
     * @param out              the JSP writer
     * @param servletContext   the servlet context
     * @param manifestFilePath the manifest file path
     * @param resourcePath     the resource path
     * @throws IOException if an I/O error occurs
     */
    private void handleProdEnvironment(JspWriter out, ServletContext servletContext, String manifestFilePath, String resourcePath) throws IOException {
        File manifestFile = new File(servletContext.getRealPath(manifestFilePath));
        log.debug("Looking for manifest file at: {}", manifestFile.getAbsolutePath());
        if (manifestFile.exists()) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode manifest = mapper.readTree(manifestFile);
            JsonNode entryNode = manifest.get(entry);

            if (entryNode != null) {
                printJsImport(out, servletContext, entryNode, resourcePath);
                printCssImports(out, servletContext, entryNode, resourcePath);
            }
        } else {
            log.error("Manifest file not found at: {}", manifestFile.getAbsolutePath());
        }
    }

    /**
     * Handles the development environment by pointing to the local Vite server.
     *
     * @param out            the JSP writer
     * @param localServerUrl the local server URL
     * @throws IOException if an I/O error occurs
     */
    private void handleDevEnvironment(JspWriter out, String localServerUrl) throws IOException {
        log.info("Adding script tags for dev environment");
        out.print("<script type=\"module\" src=\"" + localServerUrl + "/@vite/client\" defer></script>");
        out.print("<script type=\"module\" src=\"" + localServerUrl + "/" + entry + "\" defer></script>");
    }

    /**
     * Prints the JavaScript import statement.
     *
     * @param out            the JSP writer
     * @param servletContext the servlet context
     * @param entryNode      the manifest entry node
     * @param resourcePath   the resource path
     * @throws IOException if an I/O error occurs
     */
    private void printJsImport(JspWriter out, ServletContext servletContext, JsonNode entryNode, String resourcePath) throws IOException {
        String jsFile = entryNode.get("file").asText();
        String contextPath = servletContext.getContextPath();
        log.debug("Printing JS import: {}", jsFile);
        out.print("<script type=\"module\" src=\"" + contextPath + resourcePath + "/" + jsFile + "\" defer></script>");
        log.info("JS import printed");
    }

    /**
     * Prints the CSS import statements.
     *
     * @param out            the JSP writer
     * @param servletContext the servlet context
     * @param entryNode      the manifest entry node
     * @param resourcePath   the resource path
     * @throws IOException if an I/O error occurs
     */
    private void printCssImports(JspWriter out, ServletContext servletContext, JsonNode entryNode, String resourcePath) throws IOException {
        JsonNode cssFiles = entryNode.get("css");
        if (cssFiles != null) {
            String contextPath = servletContext.getContextPath();
            for (JsonNode cssFile : cssFiles) {
                log.debug("Printing CSS import: {}", cssFile.asText());
                out.print("<link rel=\"stylesheet\" href=\"" + contextPath + resourcePath + "/" + cssFile.asText() + "\"/>");
                log.info("CSS import printed");
            }
        }
    }

}
