package com.ccopart.projet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import androidx.biometric.BiometricPrompt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.net.Inet4Address;
import java.util.concurrent.Executor;




public class Authentication extends AppCompatActivity {

    private TextView authTitle;
    private Button authBtn;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private androidx.biometric.BiometricPrompt.PromptInfo promptInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        authBtn = findViewById(R.id.authBtn);

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(Authentication.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(Authentication.this, "       Authentication failed !", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(Authentication.this, "       Authentication succeed !", Toast.LENGTH_SHORT).show();
                openMainActivity();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                authTitle.setText("Authentication succeed !");
                Toast.makeText(Authentication.this, "Authentication failed !", Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                .setSubtitle("Login using fingerprint authentication")
                .setDeviceCredentialAllowed(true)
                .build();

        authBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);
            }
        });
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    };
}
