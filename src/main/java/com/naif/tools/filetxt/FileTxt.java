package com.naif.tools.filetxt;

import java.io.*;
import java.util.*;

/**
 * Utility class to write content to a file, generally used
 * in a file generator code, in a for/while loop.
 *
 * @author Nelson Ivan Fernandez Suarez
 */ // Coded on : 5/Jul/2014
public class FileTxt {

    private boolean lHayErr  = false;
    private String  dir      = "";
    private String  fileName = "";
    private String  texto    = "";
    
    public FileTxt() {}

    public FileTxt(String dir, String fileName, String texto) {
        if (dir == null || dir.isEmpty()) {
            lHayErr = true;
            throw new IllegalArgumentException("El directorio esta vacio.");
        }
        if (fileName == null || fileName.isEmpty()) {
            lHayErr = true;
            throw new IllegalArgumentException("El nombre del archivo esta vacio.");
        }
        if (texto == null || texto.isEmpty()) {
            lHayErr = true;
            throw new IllegalArgumentException("El texto esta vacio.");
        }

        this.dir      = dir;
        this.fileName = fileName;
        this.texto    = texto;
        
        save();
    }

    private void save() {
        try {

            File directory = new File(mkDirs(dir));
            File file      = new File(directory, fileName);

            // Escribir la info del source(texto) al archivo
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(texto);
            bw.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    } // end : writeGeneratedFile Method

    private String mkDirs(String paquete) {

        File myDir;
        String[] carpetas = paquete.split("\\.");

        // Si el nombre del paquete no tiene puntos
        if(carpetas.length == 1) {
            myDir = new File(carpetas[0]);
            myDir.mkdir();
            return carpetas[0];
        }

        String carpetasPaquetes = carpetas[0];

        int i = 1;
        // Se crean las carpetas de los paquetes
        for(String carpeta : carpetas) {

            if(i == 1) {
                myDir = new File(carpeta);
                myDir.mkdir();
                i = 0; continue;
            }

            carpetasPaquetes += "/" + carpeta;
            myDir = new File(carpetasPaquetes);
            myDir.mkdir();
        }

        return carpetasPaquetes;
    }

    public ArrayList<String> showFilesList(String filePath) {

        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("Ubicacion(Path) del archivo es nula/vacia.");
        }

        File   dir      = new File(filePath);
        File[] ficheros = dir.listFiles();

        ArrayList<String> filesList = new ArrayList<String>();

        for (File file : ficheros) {
            if(file.isFile()) {
                filesList.add(file.getName());
            }
        }

        return filesList;
    }

    public ArrayList<String> showFilesList(String filePath, String ext) {

        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("Ubicacion(Path) del archivo es nula/vacia.");
        }
        if (ext == null || ext.isEmpty()) {
            throw new IllegalArgumentException("Extension para el filtro de los archivos es nula/vacia.");
        }

        ArrayList<String> filesList = new ArrayList<String>();
        ArrayList<String> filesInFolder = showFilesList(filePath);

        String[] nameAndExt;

        for (String fileName : filesInFolder) {
            nameAndExt = fileName.split("\\.");

            if (nameAndExt[1].equalsIgnoreCase(ext)) {
                filesList.add(fileName);
            }
        }

        return filesList;
    }

    public ArrayList<String> getClassFiles(String filePath, String ext) {

        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("Ubicacion(Path) del archivo es nula/vacia.");
        }
        if (ext == null || ext.isEmpty()) {
            throw new IllegalArgumentException("Extension para el filtro de los archivos es nula/vacia.");
        }

        ArrayList<String> filesList = new ArrayList<String>();
        ArrayList<String> filesInFolder = showFilesList(filePath);

        String[] nameAndExt;

        for (String fileName : filesInFolder) {
            nameAndExt = fileName.split("\\.");
            
            if (nameAndExt.length != 2) {
                continue;
            }

            if (nameAndExt[1].equalsIgnoreCase(ext)) {
                filesList.add(nameAndExt[0]);
            }
        }

        return filesList;
    }

    public boolean isLHayErr() {
        return lHayErr;
    }

} // END : FileTxt : CLASS