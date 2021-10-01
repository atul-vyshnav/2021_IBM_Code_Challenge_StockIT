package org.unimodules.core.utilities;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileUtilities {
    public static File ensureDirExists(File file) throws IOException {
        if (file.isDirectory() || file.mkdirs()) {
            return file;
        }
        throw new IOException("Couldn't create directory '" + file + "'");
    }

    public static String generateOutputPath(File file, String str, String str2) throws IOException {
        File file2 = new File(file + File.separator + str);
        ensureDirExists(file2);
        String uuid = UUID.randomUUID().toString();
        StringBuilder sb = new StringBuilder();
        sb.append(file2);
        sb.append(File.separator);
        sb.append(uuid);
        if (!str2.startsWith(".")) {
            str2 = "." + str2;
        }
        sb.append(str2);
        return sb.toString();
    }
}
