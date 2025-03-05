package com.example.expo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expo.appwrite.Appwrite;

import io.appwrite.Client;
import io.appwrite.ID;
import io.appwrite.coroutines.CoroutineCallback;
import io.appwrite.services.Storage;
import io.appwrite.exceptions.AppwriteException;
import io.appwrite.models.InputFile;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class file_paths extends AppCompatActivity {
    private boolean isRecording = false;
    private Client client;
    private Storage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Appwrite Client
        client = Appwrite.getInstance(this).client;

        storage = new Storage(client);

        // Initialize Emergency Button
        Button emergencyButton = findViewById(R.id.emergency_button);
        emergencyButton.setOnClickListener(v -> toggleRecording());
    }

    // Start/Stop Recording Service
    private void toggleRecording() {
        if (!isRecording) {
            startService(new Intent(this, RecorderService.class)); // Start recording
            isRecording = true;
        } else {
            stopService(new Intent(this, RecorderService.class)); // Stop recording
            isRecording = false;

            // Upload to Appwrite after stopping recording
            String recordedFilePath = getExternalFilesDir(null).getAbsolutePath() + "/recorded_audio.3gp";
            uploadToAppwrite(recordedFilePath);
        }
    }

    // Upload Recorded File to Appwrite
    private void uploadToAppwrite(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            Toast.makeText(this, "Recorded file not found!", Toast.LENGTH_SHORT).show();
            return;
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            storage.createFile(
                    "67c83241003487ab1680",
                    ID.Companion.unique(16),
                    InputFile.Companion.fromFile(file),
                    new CoroutineCallback<>((result, error) -> {
                        if (error != null) {
                            error.printStackTrace();
                            return;
                        }

                        Log.d("Appwrite", result.toString());
                    })
            );

            runOnUiThread(() -> Toast.makeText(file_paths.this, "File uploaded successfully!", Toast.LENGTH_SHORT).show());
        });
    }
}
