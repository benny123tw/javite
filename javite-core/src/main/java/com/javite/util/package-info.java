/**
 * This package contains utility classes for the JaVite library.
 *
 * <p>The utility classes in this package provide various helper methods to support the integration
 * of Vite with Java web applications. These utilities simplify tasks such as reading Vite manifest files
 * and generating HTML tags for Vite assets.</p>
 *
 * <p>The following classes are included in this package:</p>
 * <ul>
 *     <li>{@link com.javite.util.ManifestUtils} - Utility methods for reading and processing Vite manifest files.</li>
 *     <li>{@link com.javite.util.HtmlUtils} - Utility methods for generating HTML script and link tags for Vite assets.</li>
 * </ul>
 *
 * <p>Usage example:</p>
 * <pre class="code"><code class="java">
 * // Reading the Vite manifest
 * JsonNode manifest = ManifestUtils.readManifest(manifestFilePath);
 *
 * // Generating a script tag
 * String scriptTag = HtmlUtils.generateScriptTag("http://localhost:5173/src/main.js");
 * </code></pre>
 *
 * @see com.javite.util.ManifestUtils
 * @see com.javite.util.HtmlUtils
 */
package com.javite.util;
