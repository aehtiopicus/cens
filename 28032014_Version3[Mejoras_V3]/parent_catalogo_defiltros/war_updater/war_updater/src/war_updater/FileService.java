/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war_updater;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Javier
 */
public class FileService {

    public void deleteDirectory(String dirPath) throws Exception {
        // TODO Auto-generated method stub
        //declaring the path to delete
        Path path = Paths.get(dirPath);
        System.out.println("Deleting recursivey : " + path);
        //browsing the file directory and delete recursively using java nio

        Files.walkFileTree(path, new FileVisitor<Path>() {

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc)
                    throws IOException {
                // TODO Auto-generated method stub

                System.out.println("deleting directory :" + dir);
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir,
                    BasicFileAttributes attrs) throws IOException {
                // TODO Auto-generated method stub
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file,
                    BasicFileAttributes attrs) throws IOException {
                // TODO Auto-generated method stub
                System.out.println("Deleting file: " + file);
                 file.toFile().setWritable(true);
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc)
                    throws IOException {
                // TODO Auto-generated method stub
                System.out.println(exc.toString());
                return FileVisitResult.CONTINUE;
            }
        });
    }

    void cleanAll(String pathTo) throws Exception {
        File[] file = new File(pathTo).listFiles();
        List<File> oldWar = new ArrayList<File>();
        for (File f : file) {
            if (f.isDirectory() && !f.getName().contains("imagenes")) {
                deleteDirectory(f.getAbsolutePath());
            } else if (f.isFile() && f.getName().endsWith("zip")) {
                f.deleteOnExit();
            } else if (f.isFile() && f.getName().endsWith("old")) {
                oldWar.add(f);
            }

        }
        if (oldWar.size() > 1) {
           
            Collections.sort(oldWar, new Comparator<File>() {
                @Override
                public int compare(File f1, File f2) {
                    long dateF1 = extractWarDate(f1);
                    long dateF2 = extractWarDate(f2);
                    long result = (dateF1 - dateF2);
                    if (result == 0) {
                        return 0;
                    } else if (result > 0) {
                        return -1;
                    } else {
                        return 1;
                    }

                }
            });
            oldWar.remove(0);
            for (File f : oldWar) {
                f.deleteOnExit();
            }
        }
    }

    private long extractWarDate(File file) {
        return Long.parseLong(file.getName().replace("web_catalogo_de_filtros.war.", "").replace(".old", ""));
    }
}
