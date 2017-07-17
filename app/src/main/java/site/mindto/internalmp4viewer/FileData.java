package site.mindto.internalmp4viewer;


import java.io.File;

public class FileData {
    private static final String TAG = "MainActivity";
    private String FileName;
    private String FileExtension;
    private String FilePath;
    private long FileSize;
    private String BasePathOfFile;

    public FileData(String FullFileDescriptor) {
        File filePointer;
        filePointer = new File(FullFileDescriptor);
        String[] splitDirs = FullFileDescriptor.split("/");
        String path = "";
        for (int i=1;i<splitDirs.length-1;i++) {
            path += "/" + splitDirs[i];
        }
        this.FilePath = path;
        // Now get the last index and place that into a string
        String filename = splitDirs[splitDirs.length - 1];
        String[] fileSplit = filename.split("\\.(?=[^\\.]+$)");
        this.FileName = fileSplit[0];
        this.FileExtension = fileSplit[1];
        this.FileSize = filePointer.length();
    }

    public String getFileName() {
        return FileName;
    }

    public String getFileExtension() {
        return FileExtension;
    }

    public String getFilePath() {
        return FilePath;
    }
    public double getFileSize() {
        return FileSize;
    }
}
