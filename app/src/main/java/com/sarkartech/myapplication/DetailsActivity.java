package com.sarkartech.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_details);

        // Retrieve all the stored data from the intent extras
        String fullName = getIntent().getStringExtra("fullName");
        String email = getIntent().getStringExtra("email");
        String amount = getIntent().getStringExtra("amount");
        String invoiceId = getIntent().getStringExtra("invoiceId");
        String paymentMethod = getIntent().getStringExtra("paymentMethod");
        String senderNumber = getIntent().getStringExtra("senderNumber");
        String transactionId = getIntent().getStringExtra("transactionId");
        String date = getIntent().getStringExtra("date");
        String fee = getIntent().getStringExtra("fee");
        String chargeAmount = getIntent().getStringExtra("chargeAmount");

        // Find the TextViews in your layout to display the details
        TextView fullNameTextView = findViewById(R.id.fullNameTextView);
        TextView emailTextView = findViewById(R.id.emailTextView);
        TextView amountTextView = findViewById(R.id.amountTextView);
        TextView invoiceIdTextView = findViewById(R.id.invoiceIdTextView);
        TextView paymentMethodTextView = findViewById(R.id.paymentMethodTextView);
        TextView senderNumberTextView = findViewById(R.id.senderNumberTextView);
        TextView transactionIdTextView = findViewById(R.id.transactionIdTextView);
        TextView dateTextView = findViewById(R.id.dateTextView);
        TextView feeTextView = findViewById(R.id.feeTextView);
        TextView chargeAmountTextView = findViewById(R.id.chargeAmountTextView);

        // Add more TextViews for other payment details

        // Set the text of the TextViews with the retrieved details
        fullNameTextView.setText(fullName);
        emailTextView.setText(email);
        amountTextView.setText(amount);
        invoiceIdTextView.setText(invoiceId);
        paymentMethodTextView.setText(paymentMethod);
        senderNumberTextView.setText(senderNumber);
        transactionIdTextView.setText(transactionId);
        dateTextView.setText(date);
        feeTextView.setText(fee);
        chargeAmountTextView.setText(chargeAmount);


    }
}