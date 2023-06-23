package controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

    private static final int BUFFER_SIZE = 8192;

    private static final int COMPRESSION_LEVEL = 0;

    public static void zip(File folder, File result) throws IOException {
        try (ZipOutputStream stream = new ZipOutputStream(new FileOutputStream(result))) {
            stream.setLevel(COMPRESSION_LEVEL);
            zipDirectory(folder, folder.getName(), stream);
            stream.flush();
        }
    }

    private static void zipDirectory(File folder, String parent,
                                     ZipOutputStream zipStream) throws IOException {
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                zipDirectory(file, parent + "/" + file.getName(), zipStream);
                continue;
            }
            zipStream.putNextEntry(new ZipEntry(parent + "/" + file.getName()));
            try (BufferedInputStream fileStream = new BufferedInputStream(new FileInputStream(file))) {
                byte[] bytesIn = new byte[BUFFER_SIZE];
                int read;
                while ((read = fileStream.read(bytesIn)) != -1) {
                    zipStream.write(bytesIn, 0, read);
                }
            }
            zipStream.closeEntry();
        }
    }

    /*private static void zipFile(File file, ZipOutputStream stream) throws IOException {
        stream.putNextEntry(new ZipEntry(file.getName()));
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read;
        while ((read = bis.read(bytesIn)) != -1) {
            stream.write(bytesIn, 0, read);
        }
        stream.closeEntry();
    }*/

    public static void unzip(File file, File result) throws IOException {
        if(!result.exists()) result.mkdirs();
        byte[] buffer = new byte[BUFFER_SIZE];
        try (FileInputStream inputStream = new FileInputStream(file)) {
            try (ZipInputStream zipStream = new ZipInputStream(inputStream)) {
                ZipEntry entry = zipStream.getNextEntry();
                while (entry != null) {
                    File newFile = new File(result + "/" + entry.getName());
                    new File(newFile.getParent()).mkdirs();
                    try (FileOutputStream outputStream = new FileOutputStream(newFile)) {
                        int len;
                        while ((len = zipStream.read(buffer)) > 0) {
                            outputStream.write(buffer, 0, len);
                        }
                    }
                    zipStream.closeEntry();
                    entry = zipStream.getNextEntry();
                }
                zipStream.closeEntry();
            }
        }
    }
}
