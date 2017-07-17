package site.mindto.internalmp4viewer;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    // Controls
    ListView fileList;
    TextView fileName, bottomView;
    VideoView videoPlayer;
    ArrayAdapter<Files> adapter;
    Snackbar snackBar;
    public Context context;

    // Variables
    private String icons;
    private File loadedDirectory;
    private String startingDirectory = Environment.getExternalStorageDirectory().getAbsolutePath();
    private ArrayList<Files> videoFileList = new ArrayList<>();
    private boolean appHasFilePermission;
    // Initialize the directory array
//    directoryArray = new ArrayAdapter<Files>;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize the controls
        fileList = (ListView) findViewById(R.id.fileList);
        fileName = (TextView) findViewById(R.id.tvFilename);
        bottomView = (TextView) findViewById(R.id.tvFilename);
        videoPlayer = (VideoView) findViewById(R.id.videoPlayer);
        context = getApplicationContext();
        // Set the icons credit thing
        icons = "http://www.softicons.com/toolbar-icons/childish-icons-by-double-j-design";
        // Check for permissions
        appHasFilePermission = false;
        PermissionsChecker();
        if (appHasFilePermission) {
            getDirectory(startingDirectory);
        } else {
            // Display the close dialog
            Dialog closeDlg = new Dialog(this);
            closeDlg.setContentView(R.layout.reloadwindow);
            closeDlg.setTitle("Reload application");
            Button closeBtn = (Button) closeDlg.findViewById(R.id.reloadBtn);
            closeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(getIntent());
                }
            });
            closeDlg.show();
        }
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Item clicked: " + videoFileList.get(position).getFileName());
                if (videoFileList.get(position).isDirectory()) {
                    getDirectory(videoFileList.get(position).getFullFilepath());
                    bottomView.setText(videoFileList.get(position).getFileName());
                } else {
                    Toast.makeText(context, "This isn't utilized yet", Toast.LENGTH_LONG).show();
                    bottomView.setText(videoFileList.get(position).getFullFilepath());
                }
                /* RECREATE THIS ENTIRE SECTION!!!
                // Determine if index if a directory
                String filename = videoFileList.get(position).getFileName().trim();
                String filepath = videoFileList.get(position).getFullFilepath().trim();
                if (videoFileList.get(position).isDirectory()) {
                    // Is directory
                    // Next directory
                    String nextDirectory = filepath;
                    Log.d(TAG, "Going to " + nextDirectory);
                    String compFullFP, compStartDir;
                    compFullFP = filepath.trim();
                    compStartDir = startingDirectory.trim();
                    if (compFullFP.equals(compStartDir)) {
                        Toast.makeText(context, "You cannot go any further. You are already at the top.", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d(TAG, "Going to previous directory.");
                        getDirectory(nextDirectory);
                    }
                    Toast.makeText(context, "Comparing \'" + compFullFP + "\' with \'" + compStartDir + "\'", Toast.LENGTH_LONG).show();
                } else {
                    // Is a file

                }
                */
            }
        };
        fileList.setOnItemClickListener(listener);
    }

    public void itemListener(String command, String file) {
        switch (command) {
            case "chdir":
                break;
            case "open":
                break;
        }
    }

    private void PopulateFileListing() {
        adapter = new FileAdapter(this, 0, videoFileList);
        //adapter = new FileAdapter(this. )
        fileList.setAdapter(adapter);
    }


    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    private void getDirectory(String directory) {
        // TODO Fix empty directories so that they load at least.
        // Clear the array
        //videoFileList = new ArrayList<>();
        videoFileList.clear();
        // Create the variables needed
        Files fileData = new Files();
        // Compare the directory with starting directory
        if (directory.toLowerCase().equals(startingDirectory.toLowerCase()) == false) {
            Log.d(TAG, "This is not the root directory");
            // Create a "go to parent" link
            fileData.setFileName("..");
            // Strip the directory down one
            String[] temp = directory.split("/");
            String prevDir = "";
            // Check to see if it's empty directory
//            if(temp.length != null) {
            for (int i = 1; i < temp.length - 1; i++) {
                prevDir += "/" + temp[i];
            }
            fileData.setFullFilepath(prevDir);
            fileData.setFileSize(0l);
            fileData.setFileType("directory");
            fileData.setDirectory(true);
            Files parentItem = fileData;
            videoFileList.add(fileData);
//            }
        }
        // Build the file list
        try {
            Log.d(TAG, "Getting directories and files");
            File[] directoryList = new File(directory).listFiles();
            bottomView.setText("Checking  directory");
            Log.d(TAG, "Elements: " + directoryList.length);
            // Check for an empty directory
            try {
                for (File file : directoryList) {
                    videoFileList.add(getFileInfo(file));
                }
            } catch (Exception e) {
                // Create an error box
                Toast.makeText(context, "There was an error in the command structure.\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            PopulateFileListing();
        } catch (Exception e) {
           CrashReport(e.getMessage());
        }
/*

        // Load the directory specified
        File[] dirList = new File(directory).listFiles();
        // Create a Files object and add into that
        fileName = "";
        filePath = "";
        fileSize = 0;
        fileType = "";
        boolean isDirectory;
        isDirectory = false;
        // Add a "up a directory if it's not the base
        if (directory.toLowerCase() != startingDirectory.toLowerCase()) {
            // Add parent linkage
            Files transmit = new Files("..", Double.longBitsToDouble(0), "parent", directory, true);
            videoFileList.add(transmit);
        }
        for (File f : dirList) {
            filePath = f.getAbsolutePath();
            fileName = filePath;

            if (f.isDirectory()) {
//                Log.d(TAG, f.getAbsolutePath() + " is a directory");
//                fileName += "/";
                fileSize = 0;
                fileType = "";
                // Form the information into proper transmit
                isDirectory = true;
            } else {
//                Log.d(TAG, f.getAbsolutePath() + "is a file");
                fileSize = f.getTotalSpace();
                String tDir = f.getAbsolutePath();
                fileName = fileName.substring(tDir.length());
                fileType = "Unknown at the moment";
                isDirectory = false;
            }
            Files fileInfo = new Files(fileName, Double.longBitsToDouble(fileSize), fileType, filePath, isDirectory);
            //fileList.add(fileName + "/", Double.longBitsToDouble(fileSize), fileType, isDirectory);
            videoFileList.add(fileInfo);
            Log.d(TAG, "Adding " + fileName + " to the list.");
        }
        PopulateFileListing();
        */
    }

    private void CrashReport(String message) {
        Dialog crashDlg = new Dialog(this);
        crashDlg.setContentView(R.layout.reloadwindow);
        TextView crashMsg = (TextView) crashDlg.findViewById(R.id.textView);
        Button reloadBtn = (Button) crashDlg.findViewById(R.id.reloadBtn);
        crashMsg.setText(message);
        reloadBtn.setText("Reload Application");
        reloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });

    }
    private void PermissionsChecker() {
        // Check to see if file permissions are allowed.

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result < 0) {
            appHasFilePermission = false;
        } else {
            appHasFilePermission = true;
        }
    }

    private Files getFileInfo(File filebase) {
        Files fileInfo = new Files();
        // Break down the file into it's respective information
        if (filebase.isDirectory()) {
            // Directory
            fileInfo.setDirectory(true);
            fileInfo.setFileSize(0l);
            fileInfo.setFullFilepath(filebase.getAbsolutePath().toString());
            String [] splitInfo = fileInfo.getFullFilepath().split("/");
            String pathBuilder = "";
            for(int i=0;i<splitInfo.length;i++) {
                pathBuilder += "/" + splitInfo[i];
            }
            fileInfo.setFilePath(pathBuilder);
            fileInfo.setFileName(splitInfo[splitInfo.length-1]);
            fileInfo.setFileType("directory");
        } else if (filebase.isFile()) {
            // File
            // Set the full filename and path
            fileInfo.setDirectory(false);
            fileInfo.setFileSize(filebase.length());
            fileInfo.setFullFilepath(filebase.getAbsoluteFile().toString());
            String[] splitInfo = fileInfo.getFullFilepath().split("/");
            String pathBuilder = "";
            for (int i = 1; i < splitInfo.length-1; i++) {
                pathBuilder += "/" + splitInfo[i];
            }
            fileInfo.setFilePath(pathBuilder);
            String fileName;
            fileName = splitInfo[splitInfo.length-1];
            fileInfo.setFileName(fileName);
            String[] extBuilder = fileName.split("\\.(?=[^\\.]+$)");
            fileInfo.setFileType(extBuilder[extBuilder.length-1]);
        }
        // Set the filename
        // Break apart the filename for the extension

        // Send everything back
        return fileInfo;
    }
}

