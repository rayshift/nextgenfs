package io.rayshift.translatefgo;

import android.os.Build;
import android.os.FileUtils;
import android.os.RemoteException;
import android.system.Os;
import android.util.Log;
import android.content.Context;

import androidx.annotation.Keep;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class NGFSService extends INGFSService.Stub {

    public NGFSService() {
        Log.i("TranslateFGO", "Starting NGFS service.");
    }

    @Keep
    public NGFSService(Context context) {
        Log.i("TranslateFGO", "Starting NGFS service with context=" + context.toString());
    }

    @Override
    public void destroy() {
        System.exit(0);
    }

    @Override
    public void exit() {
        destroy();
    }

    @Override
    public boolean WriteFileContents(String filename, byte[] contents, int offset, int length, NGFSError error) throws RemoteException {
        File file = new File(filename);
        RandomAccessFile fis = null;
        try {
            fis = new RandomAccessFile(file, "rw");
            fis.seek(offset);
            fis.write(contents, 0, length);
            error.IsSuccess = true;
            fis.close();
            return true;
        }
        catch (Exception ex) {
            error.IsSuccess = false;
            error.Error = ex.toString();
            return false;
        }
    }

    @Override
    public boolean RemoveFileIfExists(String filename, NGFSError error) throws RemoteException {
        File file = new File(filename);
        try {
            error.IsSuccess = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Files.deleteIfExists(file.toPath());
                return true;
            }
            else {
                error.IsSuccess = false;
                error.Error = "Android version too low.";
                return false;
            }
        }
        catch (Exception ex) {
            error.IsSuccess = false;
            error.Error = ex.toString();
            return false;
        }
    }

    @Override
    public boolean GetFileExists(String filename, NGFSError error) throws RemoteException {
        File file = new File(filename);
        error.IsSuccess = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Files.exists(file.toPath());
        }
        else {
            error.IsSuccess = false;
            error.Error = "Android version too low.";
            return false;
        }
    }

    @Override
    public long GetFileModTime(String filename, NGFSError error) throws RemoteException {
        File file = new File(filename);
        error.IsSuccess = true;
        return file.lastModified();
    }

    public static int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new ArithmeticException(l + " cannot be cast to int without changing its value.");
        }
        return (int) l;
    }

    @Override
    public int GetExistingFileSize(String filename, NGFSError error) throws RemoteException {
        try {
            File file = new File(filename);
            long length = file.length();
            error.IsSuccess = true;
            return safeLongToInt(length);
        }
        catch (Exception ex) {
            error.IsSuccess = false;
            error.Error = ex.toString();
            return -1;
        }
    }

    @Override
    public byte[] ReadExistingFile(String filename, int offset, int length, NGFSError error) throws RemoteException {
        File file = new File(filename);
        RandomAccessFile fis = null;
        try {
            fis = new RandomAccessFile(file, "r");
            fis.seek(offset);
            byte[] byteStore = new byte[length];
            fis.read(byteStore, 0, length);
            error.IsSuccess = true;
            fis.close();
            return byteStore;
        }
        catch (Exception ex) {
            error.IsSuccess = false;
            error.Error = ex.toString();
            return new byte[0];
        }
    }

    @Override
    public boolean CopyFile(String source, String destination, NGFSError error) throws RemoteException {
        try {
            File input = new File(source);
            File output = new File(destination);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Files.copy(input.toPath(), output.toPath(), StandardCopyOption.REPLACE_EXISTING);

                error.IsSuccess = true;
                return true;
            }
            else {
                error.IsSuccess = false;
                error.Error = "Android version too low.";
                return false;
            }
        }
        catch (Exception ex) {
            error.IsSuccess = false;
            error.Error = ex.toString();
            return false;
        }
    }

    @Override
    public String[] ListDirectoryContents(String filename, NGFSError error) throws RemoteException {
        List<String> li = new ArrayList<String>();
        File[] files = new File(filename).listFiles();

        if (files != null) {
            for (File el : files) {
                if (el.isDirectory()) {
                    li.add("D|" + el.getPath());
                } else {
                    li.add("F|" + el.getPath());
                }
            }
        }

        String[] stockArr = new String[li.size()];
        error.IsSuccess = true;
        return li.toArray(stockArr);
    }


}
