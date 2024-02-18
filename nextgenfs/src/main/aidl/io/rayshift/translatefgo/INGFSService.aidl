package io.rayshift.translatefgo;

import io.rayshift.translatefgo.NGFSError;

interface INGFSService {
    void destroy() = 16777114;

    void exit() = 1; // Exit method defined by user

    boolean WriteFileContents(String filename, in byte[] contents, int offset, int length, out NGFSError error) = 4;

    boolean RemoveFileIfExists(String filename, out NGFSError error) = 5;

    boolean GetFileExists(String filename, out NGFSError error) = 6;

    long GetFileModTime(String filename, out NGFSError error) = 7;

    int GetExistingFileSize(String filename, out NGFSError error) = 11;

    byte[] ReadExistingFile(String filename, int offset, int length, out NGFSError error) = 8;

    boolean CopyFile(String source, String destination, out NGFSError error) = 16;

    @nullable
    String[] ListDirectoryContents(String filename, out NGFSError error) = 9;

}