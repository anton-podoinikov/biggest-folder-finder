package org.example;

import java.io.File;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        String folderPath = "/media/anton/Диск/Музыка/";
        File file = new File(folderPath);
        long start = System.currentTimeMillis();

        FolderSizeCalculator calculator = new FolderSizeCalculator(file);
        ForkJoinPool pool = new ForkJoinPool();
        long size = pool.invoke(calculator);
        System.out.println(getHumanReadableSize(size));

        long duration = System.currentTimeMillis() - start;
//        System.out.println(duration + " ms");

        long value = getSizeFromHumanReadable("1 tb");

        System.out.println(value);
    }

    public static long getFolderSize(File folder) {
        if (folder.isFile()) {
            return folder.length();
        }
        long sum = 0;
        File[] files = folder.listFiles();
        for (File file : files) {
            sum += getFolderSize(file);
        }
        return sum;
    }

    public static String getHumanReadableSize(long size) {
        long numberB = size;
        long numberKb = numberB / 1000;
        long numberMb = numberKb / 1000;
        long numberGb = numberMb / 1000;
        long numberTb = numberGb / 1000;

        if (size < 8192) {
            return numberB + " b";
        } else if (size > 8192 && size < 1e+6) {
            return numberKb + " kb";
        } else if (size > 1e+6 && size < 1e+9) {
            return numberMb + " mb";
        } else if (size > 1e+9 && size < 1e+12) {
            return numberGb + " gb";
        } else {
            return numberTb + " tb";
        }
    }

    public static long getSizeFromHumanReadable(String size) {
        String[] value = size.split("\s");
        Integer number = Integer.valueOf(value[0]);

        if (value[1].equals("kb")) {
            return number * 1024L;
        } else if (value[1].equals("mb")) {
            return number * 1000 * 1024L;
        } else if (value[1].equals("gb")) {
            return number * 1000 * 1024 * 1024L;
        } else {
            return number * 1000 * 1024 * 1024 * 1024L;
        }
    }
}