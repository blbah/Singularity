package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;

public class Controller {

    public static void processFiles(String fromPath, String toPath)
            throws IOException, InterruptedException {
        File from = new File(fromPath);
        File to = new File(toPath);
        if (from.getName().contains(".ans")) {
            ZipUtil.unzip(from, to);
            decompress(new File((to.getAbsolutePath() + "/" + from.getName())
                    .replace("\\", "/")
                    .replace(".ans", ".temp")));
        }
        else {
            File res_to = new File((to.getAbsolutePath() + "/"
                    + from.getName() + ".temp")
                    .replace("\\", "/"));
            res_to.mkdir();
            compress(from, res_to);
            File result = new File((res_to.getAbsolutePath())
                    .replace(".temp", ".ans")
                    .replace("\\", "/"));
            result.createNewFile();
            ZipUtil.zip(res_to, result);
            deleteDir(res_to);
        }
    }

    private static void deleteDir(File directory) throws IOException {
        Files.walk(directory.toPath())
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    private static void compress(File from, File to) throws IOException, InterruptedException {
        String parent = from.getAbsolutePath();
        ArrayList<ThreadCompress> threads = new ArrayList<>();
        if (!from.isDirectory()) {
            File res_file = new File(from.getAbsolutePath()
                    .replace(parent, to.getAbsolutePath() + "/")
                    .replace("\\", "/"));
            res_file.createNewFile();
            ThreadCompress thread = new ThreadCompress(from, res_file);
            thread.start();
            thread.join();
            if (thread.getResult() != null) {
                throw thread.getResult();
            }
        }
        else {
            for (File file : from.listFiles()) {
                if (file.isDirectory()) {
                    File res_folder = new File(to.getAbsolutePath()
                            + "/" + file.getName());
                    res_folder.mkdir();
                    compress(file, res_folder);
                } else {
                    File res_file = new File(file.getAbsolutePath()
                            .replace(parent, to.getAbsolutePath() + "/")
                            .replace("\\", "/"));
                    res_file.createNewFile();
                    threads.add(new ThreadCompress(file, res_file));
                }
            }
            for (ThreadCompress thread : threads) {
                thread.start();
                thread.join();
            }
            for (ThreadCompress thread : threads) {
                if (thread.getResult() != null) {
                    throw thread.getResult();
                }
            }
        }
    }

    private static void decompress(File folder)
            throws IOException, InterruptedException {
        ArrayList<ThreadDecompress> threads = new ArrayList<>();
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                decompress(file);
            }
            else {
                File res_file = new File(file.getAbsolutePath() + ".lol");
                res_file.createNewFile();
                threads.add(new ThreadDecompress(file, res_file));
            }
        }
        for (ThreadDecompress thread : threads) {
            thread.start();
            thread.join();
        }
        for (ThreadDecompress thread : threads) {
            thread.clean();
            if (thread.getResult() != null) {
                throw thread.getResult();
            }
        }
        folder.renameTo(new File(folder.getAbsolutePath()
                .replace(".temp", "")));
    }
}
