package com.sarkartech.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.help5g.uddoktapaysdk.UddoktaPay;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // Constants for payment
    private static final String API_KEY = "982d381360a69d419689740d9f2e26ce36fb7a50";
    private static final String CHECKOUT_URL = "https://sandbox.uddoktapay.com/api/checkout-v2";
    private static final String VERIFY_PAYMENT_URL = "https://sandbox.uddoktapay.com/api/verify-payment";
    private static final String REDIRECT_URL = "https://uddoktapay.com";
    private static final String CANCEL_URL = "https://uddoktapay.com";

    private RelativeLayout relativeLayout;
    private TextInputLayout textInputName;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputAmount;
    private AppCompatButton buttonSubmit;
    private WebView webViewPay;

    // Instance variables to store payment information
    private String storedFullName;
    private String storedEmail;
    private String storedAmount;
    private String storedInvoiceId;
    private String storedPaymentMethod;
    private String storedSenderNumber;
    private String storedTransactionId;
    private String storedDate;
    private String storedFee;
    private String storedChargedAmount;

    private String storedMetaKey1;
    private String storedMetaValue1;

    private String storedMetaKey2;
    private String storedMetaValue2;

    private String storedMetaKey3;
    private String storedMetaValue3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        relativeLayout = findViewById(R.id.relativeLayout);
        textInputName = findViewById(R.id.textInputName);
        textInputEmail = findViewById(R.id.textInputEmail);
        textInputAmount = findViewById(R.id.textInputAmount);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        webViewPay = findViewById(R.id.webViewPay);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get user input
                String name = textInputName.getEditText().getText().toString();
                String email = textInputEmail.getEditText().getText().toString();
                String amount = textInputAmount.getEditText().getText().toString();

                // Check if any of the fields are empty
                if (name.isEmpty() || email.isEmpty() || amount.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Set your metadata values in the map
                    relativeLayout.setVisibility(View.GONE);
                    webViewPay.setVisibility(View.VISIBLE);
                    Map<String, String> metadataMap = new HashMap<>();
                    metadataMap.put("CustomMetaData1", "Meta Value 1");
                    metadataMap.put("CustomMetaData2", "Meta Value 2");
                    metadataMap.put("CustomMetaData3", "Meta Value 3");

                    UddoktaPay.PaymentCallback paymentCallback = new UddoktaPay.PaymentCallback() {
                        @Override
                        public void onPaymentStatus(String status, String fullName, String email, String amount, String invoiceId,
                                                    String paymentMethod, String senderNumber, String transactionId,
                                                    String date, Map<String, String> metadataValues, String fee, String chargeAmount) {
                            // Callback method triggered when the payment status is received from the payment gateway.
                            // It provides information about the payment transaction.
                            storedFullName = fullName;
                            storedEmail = email;
                            storedAmount = amount;
                            storedInvoiceId = invoiceId;
                            storedPaymentMethod = paymentMethod;
                            storedSenderNumber = senderNumber;
                            storedTransactionId = transactionId;
                            storedDate = date;
                            storedFee = fee;
                            storedChargedAmount = chargeAmount;

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // Clear previous metadata values to avoid duplication
                                    storedMetaKey1 = null;
                                    storedMetaValue1 = null;
                                    storedMetaKey2 = null;
                                    storedMetaValue2 = null;
                                    storedMetaKey3 = null;
                                    storedMetaValue3 = null;

                                    // Iterate through the metadata map and store the key-value pairs
                                    for (Map.Entry<String, String> entry : metadataValues.entrySet()) {
                                        String metadataKey = entry.getKey();
                                        String metadataValue = entry.getValue();

                                        if ("CustomMetaData1".equals(metadataKey)) {
                                            storedMetaKey1 = metadataKey;
                                            storedMetaValue1 = metadataValue;
                                        } else if ("CustomMetaData2".equals(metadataKey)) {
                                            storedMetaKey2 = metadataKey;
                                            storedMetaValue2 = metadataValue;
                                        } else if ("CustomMetaData3".equals(metadataKey)) {
                                            storedMetaKey3 = metadataKey;
                                            storedMetaValue3 = metadataValue;
                                        }
                                    }

                                    // Update UI based on payment status
                                    if ("COMPLETED".equals(status)) {
                                        // Handle payment completed case
                                        Intent detailsIntent = new Intent(MainActivity.this, DetailsActivity.class);
                                        detailsIntent.putExtra("fullName", storedFullName);
                                        detailsIntent.putExtra("email", storedEmail);
                                        detailsIntent.putExtra("amount", storedAmount);
                                        detailsIntent.putExtra("invoiceId", storedInvoiceId);
                                        detailsIntent.putExtra("paymentMethod", storedPaymentMethod);
                                        detailsIntent.putExtra("senderNumber", storedSenderNumber);
                                        detailsIntent.putExtra("transactionId", storedTransactionId);
                                        detailsIntent.putExtra("date", storedDate);
                                        detailsIntent.putExtra("fee", storedFee);
                                        detailsIntent.putExtra("chargeAmount", storedChargedAmount);
                                        startActivity(detailsIntent);
                                    } else if ("PENDING".equals(status)) {
                                        // Handle payment pending case
                                        relativeLayout.setVisibility(View.VISIBLE);
                                        webViewPay.setVisibility(View.GONE);
                                    } else if ("ERROR".equals(status)) {
                                        // Handle payment error case
                                        relativeLayout.setVisibility(View.VISIBLE);
                                        webViewPay.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                    };

                    UddoktaPay uddoktapay = new UddoktaPay(webViewPay, paymentCallback);
                    uddoktapay.loadPaymentForm(API_KEY, name, email, amount, CHECKOUT_URL, VERIFY_PAYMENT_URL, REDIRECT_URL, CANCEL_URL, metadataMap);
                }
            }
        });
    }
}
