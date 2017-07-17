package site.mindto.internalmp4viewer;

import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by Gabe on 7/10/2017.
 */

public class Files {
    // Actual file name
    private String fileName;
    // Size of file (if a file, not directory)
    private long fileSize;
    // Type of file
    private String fileType;
    // Flag if it is a directory
    private boolean isDirectory;
    // Path of file (with filename)
    private String fullFilepath;
    // File path (without filename);
    private String filePath;

    public Files() {
        Log.d(TAG, "Files object created");
    }

    /// Retrieve the filename
    public String getFileName() {
        return fileName;
    }

    /// Sets the file path
    public String getFilePath() {
        return filePath;
    }

    /// Sets the file path
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /// Sets the filename (not with path)
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /// Gets the filesize, if it's a file. Directory is 0 size.
    public long getFileSize() {
        return fileSize;
    }

    /// Sets the filesize. If it's a directory, it's 0 size.
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    /// Get the extension if it is a file
    public String getFileType() {
        return fileType;
    }

    /// Sets the extension if it is a file
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /// Retrieve if it's a directory or not
    public boolean isDirectory() {
        return isDirectory;
    }

    /// Set if it's a directory or not
    public void setDirectory(boolean directory) {
        isDirectory = directory;
    }
/// Get the full filepath (with filename if applicable)
    public String getFullFilepath() {
        return fullFilepath;
    }

/// Set the full filepath (with filename if applicable)
    public void setFullFilepath(String fullFilepath) {
        this.fullFilepath = fullFilepath;
    }
}
