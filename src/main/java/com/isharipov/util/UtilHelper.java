package com.isharipov.util;

import com.isharipov.Result;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @since 21.08.2016
 */
public class UtilHelper {
    public static void compress(String fileName) {
        byte[] buffer = new byte[1024];

        try (FileOutputStream fos = new FileOutputStream(fileName + ".zip");
             ZipOutputStream zos = new ZipOutputStream(fos);
             FileInputStream in = new FileInputStream(fileName)) {
            ZipEntry ze = new ZipEntry(fileName);
            zos.putNextEntry(ze);
            int len;
            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File decompress(String fileName) {
        byte[] buffer = new byte[1024];
        File newFile = null;
        try (ZipInputStream zis =
                     new ZipInputStream(new FileInputStream(fileName));) {

            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                String newFileName = ze.getName();
                newFile = new File(System.getProperty("user.dir") + File.separator + newFileName);
                FileOutputStream fos = new FileOutputStream(newFile);

                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                ze = zis.getNextEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFile;
    }

    public static Double getResult(File file) {
        return deserialize(file).getResult().get(file.getName());
    }

    public static File serialize(Result result, String fileName) {
        try (FileOutputStream fout = new FileOutputStream(fileName);
             ObjectOutputStream oos = new ObjectOutputStream(fout)) {
            oos.writeObject(result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new File(fileName);
    }

    private static Result deserialize(File file) {
        Result result;
        try (FileInputStream fin = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fin)) {
            result = (Result) ois.readObject();
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
