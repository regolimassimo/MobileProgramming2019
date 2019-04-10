package com.massimoregoli.mp2019.myactivities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.massimoregoli.mp2019.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FilesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files);
        internalFiles();
        externalFile();
    }

    private void internalFiles() {
        saveInternalFile();
        loadInternalFile();
    }

    private void loadInternalFile() {

    }

    private void saveInternalFile() {
        String filename = "myfile.txt";
        String fileContents = "Hello world!";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void externalFile() {
        ActivityCompat.requestPermissions
                (this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        100);

        String sdPath = Environment
                .getExternalStorageDirectory()
                .getAbsolutePath() ;

        Toast.makeText(this, sdPath, Toast.LENGTH_SHORT).show();
        saveFile(sdPath);
        loadFile(sdPath);

    }

    private void saveFile(String mySdPath) {
        try {
            File myFile = new File(mySdPath + "/mysdfile.txt");
            OutputStreamWriter myOutWriter = new OutputStreamWriter(
                    new FileOutputStream(myFile));
            myOutWriter.append("HELLO WORLD!");
            myOutWriter.close();
            Toast.makeText(getBaseContext(),
                    "Done writing SD ’mysdfile.txt’",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFile(String mySdPath) {
        try {
            BufferedReader myReader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(new File(
                            mySdPath + "/mysdfile.txt"))));
            String aDataRow = "";
            String aBuffer = "";
            while ((aDataRow = myReader.readLine()) != null) {
                aBuffer += aDataRow + "\n";
            }
            myReader.close();
            Toast.makeText(getApplicationContext(),
                    "Done reading SD mysdfile.txt:" + aBuffer,
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(this, "Permission Accepted!", Toast
//                            .LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denied!", Toast
                            .LENGTH_SHORT).show();
                }
        }
    }
}
