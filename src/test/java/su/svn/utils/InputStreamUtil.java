/*
 * This file was last modified at 2020.03.05 12:39 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * InputStreamUtil.java
 * $Id$
 */

package su.svn.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public class InputStreamUtil {

    public static String read(InputStream is) {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                contentBuilder.append(currentLine).append("\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    public static void readAndExecuteLine(InputStream is, Consumer<String> consumer) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                if (currentLine.length() > 0)
                    consumer.accept(currentLine);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
