package deco2800.spooky.managers;

import com.badlogic.gdx.graphics.Texture;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class NewTextureManagerTest {
    /*
    @Test
    public void checkTexture() {
        NewTextureManager test = new NewTextureManager();

        for (String key: test.getLoadTexture().keySet()) {
            System.out.println(key+":"+test.getLoadTexture().get(key));
        }


        for (String key: test.getTextureMap().keySet()) {
            System.out.println(key+":"+test.getTextureMap().get(key));
        }
    }

     */
    @Test
    public void checkMap() {
        int a = 100;
        List<File[]> fileList = new ArrayList<>();
        Map<String, String> loadTexture = new HashMap<>();
        File directory = new File("resources/TestTextureManager");
        File[] loadingFile = directory.listFiles();
        fileList.add(loadingFile);
        List<File[]> thingsToBe = new ArrayList<>();
        while (a > 0) {
            for (File[] folder : fileList) {
                for (File file : folder) {
                    if (file.isDirectory()) {
                        thingsToBe.add(file.listFiles());
                        a--;
                        break;
                    } else {
                        if (loadTexture.get(file.getName()) == null) {
                            String name= file.getName();
                            String[] strip = name.split("\\.");
                            loadTexture.put(strip[0], file.getPath());
                        }
                    }
                }
            }

            fileList.addAll(thingsToBe);
            thingsToBe.clear();
        }
        /*
        for(File[] folder : fileList) {
            for (File file : folder) {
                System.out.println(file.getName());
            }
        }
        */

        for (String key: loadTexture.keySet()) {
            System.out.println(key+":"+loadTexture.get(key));
        }


        /*
        System.out.println(fileList.get(0)[0].getName());
        System.out.println(fileList.get(0)[1].getName());
        System.out.println(fileList.get(1)[0].getName());
        */
    }

    @Test
    public void checkReadFile(){
        File directory = new File("resources/TestTextureManager");
        List<File> fileList = new ArrayList<>();
        //fileList.add(loadingFile);
        retrieveFile(fileList, directory);
        System.out.println("Num of files: " + fileList.size());
        Map<String, String> loadTexture = new HashMap<>();

    }

    public void retrieveFile(List<File> fileList, File loadingFile){
        File[] folder = loadingFile.listFiles();
        for (File file: folder){
            if (file.isDirectory()) {
                System.out.println("A dir: " + file.getAbsolutePath());
                retrieveFile(fileList, file);
            } else {
                System.out.println("A file " + file.getAbsolutePath());
                fileList.add(file);
                //loadTexture.put(file.getName(), file.getPath());
            }
        }
    }
}