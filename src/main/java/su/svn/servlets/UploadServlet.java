/*
 * This file was last modified at 2020.07.11 21:08 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UploadServlet.java
 * $Id$
 */

package su.svn.servlets;

import javax.servlet.annotation.WebServlet;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static su.svn.shared.Constants.*;

@WebServlet(
        name = "UploadServlet",
        urlPatterns = {"/admin/upload"}
)
public class UploadServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Pattern zipFile = Pattern.compile(".*\\.zip$");

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LOGGER.info("request: {}, response: {}", request, response);

        if (ServletFileUpload.isMultipartContent(request)) {

            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(MEMORY_THRESHOLD);
            String javaIoTmpDir = System.getProperty("java.io.tmpdir");
            LOGGER.info("java.io.tmpdir: {}", javaIoTmpDir);
            factory.setRepository(new File(javaIoTmpDir));

            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setFileSizeMax(MAX_FILE_SIZE);
            upload.setSizeMax(MAX_REQUEST_SIZE);
            String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
            LOGGER.info("uploadPath: {}", uploadPath);
            File uploadDir = new File(uploadPath);
            if ( ! uploadDir.exists()) {
                uploadDir.mkdir();
            }

            try {
                List<FileItem> formItems = upload.parseRequest(request);

                if (formItems != null && formItems.size() > 0) {
                    String unzipPath = getServletContext().getRealPath("") + File.separator + UNZIP_DIRECTORY;
                    for (FileItem item : formItems) {
                        if ( ! item.isFormField()) {
                            String fileName = new File(item.getName()).getName();
                            String filePath = uploadPath + File.separator + fileName;
                            File storeFile = new File(filePath);
                            item.write(storeFile);
                            request.setAttribute("message", "File " + fileName + " has uploaded successfully!");
                            Matcher matcher = zipFile.matcher(fileName);
                            if (matcher.find()) {
                                LOGGER.info("unzipFile({}, {})", filePath, unzipPath);
                                unzipFile(filePath, unzipPath);
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                LOGGER.info("There was an error: {}", ex.getMessage());
                request.setAttribute("message", "There was an error: " + ex.getMessage());
            }
            getServletContext().getRequestDispatcher("/welcome.xhtml").forward(request, response);
        }
    }

    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }

    private void unzipFile(String fileName, String destinationDirectory) {
        try {
            ZipFile zipFile = new ZipFile(fileName);
            zipFile.extractAll(destinationDirectory);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }
}

