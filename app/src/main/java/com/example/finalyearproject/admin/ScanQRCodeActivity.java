package com.example.finalyearproject.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.finalyearproject.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanQRCodeActivity extends AppCompatActivity {


        TextView tvQRCodeResultText;
        AppCompatButton btnScanQRCOde;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrcode);

        tvQRCodeResultText = findViewById (R.id.tvScanQRCodeText);
        btnScanQRCOde = findViewById (R.id.acbtnQRCodeScanQR);

        btnScanQRCOde.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator (ScanQRCodeActivity.this);//QRCode scanning
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestcode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestcode, resultCode, data);

        if (result != null){
            if(result.getContents() == null){
                Toast.makeText (this, "Scanning has been cancelled", Toast.LENGTH_SHORT).show ();
            }
            else{
                Toast.makeText (this, result.getContents(), Toast.LENGTH_SHORT).show ();
                tvQRCodeResultText.setText(result.getContents ());
            }
        }
        else
        {
            super.onActivityResult (requestcode, resultCode, data);

        }
    }


}
