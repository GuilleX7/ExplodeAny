package io.github.guillex7.explodeany.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import net.freeutils.httpserver.HTTPServer;
import net.freeutils.httpserver.HTTPServer.ContextHandler;
import net.freeutils.httpserver.HTTPServer.Headers;
import net.freeutils.httpserver.HTTPServer.Request;
import net.freeutils.httpserver.HTTPServer.Response;

/**
 * The {@code JarResourceContextHandler} services a context by mapping it
 * to a specific directory in a jar file. In order to serve the directory
 * (recursively), the context path must end with a wildcard path parameter named
 * "*",
 * e.g. "/path/{*}" (with slash) or "/path{*}" (with or without slash).
 */
public class JarResourceContextHandler implements ContextHandler {
    /**
     * The map of jar entries by their names. Names are relative to the base path,
     * which is the directory in the jar file that this context handler serves.
     */
    private final Map<String, JarEntry> jarEntriesByName = new HashMap<>();
    /**
     * The base path in the jar file that this context handler serves.
     */
    private String basePath;
    /**
     * The class that the jar file is loaded from.
     */
    private Class<?> classInJar;

    /**
     * Creates a new {@code JarResourceContextHandler} that serves the given base
     * path in the jar file that {@code JarResourceContextHandler} is running from.
     * Note that if {@code JarResourceContextHandler} is not running from a jar
     * file, an exception will be thrown.
     * 
     * @param basePath the base path to serve
     * @throws IOException
     */
    public JarResourceContextHandler(String basePath) throws IOException {
        this(basePath, JarResourceContextHandler.class);
    }

    /**
     * Creates a new {@code JarResourceContextHandler} that serves the given base
     * path in the jar file that {@code classInJar} is running from.
     * Any class that is running from a jar file can be used as the given class.
     * Note that if the class is not running from a jar file, an exception
     * will be thrown.
     * 
     * @param basePath   the base path to serve
     * @param classInJar the class to get the jar file from
     * @throws IOException
     */
    public JarResourceContextHandler(String basePath, Class<?> classInJar) throws IOException {
        this.basePath = getSanitizedBasePath(basePath);
        this.classInJar = classInJar;

        final URL jarUrl = new URL("jar:file:" + this.getJarFilePath(this.classInJar) + "!/");
        final JarURLConnection jarFileConnection = (JarURLConnection) jarUrl.openConnection();

        final JarFile jarFile = jarFileConnection.getJarFile();
        final Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            final JarEntry entry = entries.nextElement();
            if (!entry.isDirectory() && entry.getName().startsWith(this.basePath)) {
                this.jarEntriesByName.put(entry.getName().substring(this.basePath.length()), entry);
            }
        }
    }

    /**
     * Returns the sanitized base path from a raw base path.
     * Since inner paths are addressed in the format of "path/to/resource",
     * the base path must start with a slash and end with a slash, "path/to/".
     * 
     * @param rawBasePath
     * @return the sanitized base path in the format "path/to/"
     */
    private String getSanitizedBasePath(String rawBasePath) {
        String sanitizedBasePath = rawBasePath;

        // Paths in jar files never start with a slash
        if (sanitizedBasePath.startsWith("/")) {
            sanitizedBasePath = sanitizedBasePath.substring(1);
        }

        // If length is 0, the base path is the root directory, so no need to add a trailing slash
        // Otherwise, add the trailing slash if it is not already there
        if (sanitizedBasePath.length() > 0 && !sanitizedBasePath.endsWith("/")) {
            sanitizedBasePath += "/";
        }

        return sanitizedBasePath;
    }

    /**
     * Returns the path to the jar file that the given class is running from.
     * 
     * @return the path to the jar file
     */
    private String getJarFilePath(Class<?> baseClass) {
        final URL classResource = baseClass.getResource(baseClass.getSimpleName() + ".class");
        if (classResource == null) {
            throw new RuntimeException("Class resource is null");
        }

        final String url = classResource.toString();
        if (url.startsWith("jar:file:")) {
            final String path = url.replaceAll("^jar:(file:.*[.]jar)!/.*", "$1");
            try {
                return Paths.get(new URL(path).toURI()).toString();
            } catch (Exception e) {
                throw new RuntimeException("Invalid jar file URL");
            }
        }

        throw new RuntimeException("Not running from a jar file");
    }

    @Override
    public int serve(Request request, Response response) throws IOException {
        final String requestPath = request.getPath();
        final String requestPathParam = request.getParams().get("*");
        final String requestResourcePath = requestPathParam != null ? requestPathParam : requestPath;
        return this.serveResource(requestResourcePath, request, response);
    }

    /**
     * Serves the resource at the given path.
     * If the resource is not found, or is not located under the base path,
     * a 404 status code is returned.
     * Note that folders are never served as a directory listing, but as a 404.
     * 
     * @param requestResourcePath the path of the resource to serve
     * @param request             the request
     * @param response            the response into which the content is written
     * @return the status code of the response
     * @throws IOException
     */
    private int serveResource(String requestResourcePath, Request request, Response response) throws IOException {
        final JarEntry jarEntry = this.jarEntriesByName.get(requestResourcePath);
        if (jarEntry == null) {
            return 404;
        }

        URL resourceUrl = this.classInJar.getResource("/" + jarEntry.getName());
        if (resourceUrl == null) {
            return 404;
        }

        this.serveResourceContent(jarEntry, resourceUrl, request, response);
        return 0;
    }

    /**
     * Serves the contents of a resource, with its corresponding content type,
     * last modification time, etc. Conditional and partial retrievals are
     * handled according to the RFC.
     * 
     * @param jarEntry    the jar entry of the resource
     * @param resourceUrl the URL of the resource
     * @param request     the request
     * @param response    the response into which the content is written
     * @throws IOException
     */
    private void serveResourceContent(JarEntry jarEntry, URL resourceUrl, Request request, Response response)
            throws IOException {
        final String fileName = jarEntry.getName();
        final long fileLength = jarEntry.getSize();
        final long fileLastModified = jarEntry.getLastModifiedTime().toMillis();
        final long fileLastModifiedInSeconds = fileLastModified - fileLastModified % 1000;
        final String fileETag = "W/\"" + fileLastModified + "\"";

        long[] range = request.getRange(fileLength);
        int status = HTTPServer.getConditionalStatus(request, fileLastModifiedInSeconds, fileETag, range != null);
        if (status == 206) {
            status = range[0] >= fileLength ? 416 : 200;
        } else {
            range = null;
        }

        Headers responseHeaders = response.getHeaders();
        switch (status) {
            case 304:
                responseHeaders.add("ETag", fileETag);
                responseHeaders.add("Vary", "Accept-Encoding");
                responseHeaders.add("Last-Modified", HTTPServer.formatDate(fileLastModified));
                response.sendHeaders(304);
                break;
            case 412:
                response.sendError(412);
                break;
            case 416:
                responseHeaders.add("Content-Range", "bytes */" + fileLength);
                response.sendError(416);
                break;
            case 200:
                response.sendHeaders(200, fileLength, fileLastModified, fileETag,
                        HTTPServer.getContentType(fileName, "application/octet-stream"), range);

                try (InputStream in = resourceUrl.openStream()) {
                    response.sendBody(in, fileLength, range);
                }
                break;
            default:
                response.sendError(500);
                break;
        }
    }
}