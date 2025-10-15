package com.example.a1_niaj_hossain;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editHours, editRate;
    TextView txtPay, txtOvertime, txtTotal, txtTax;
    Button btnCalculate;

    // Shared list of payment records
    public static ArrayList<String> paymentHistory = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect views from XML
        editHours = findViewById(R.id.editHours);
        editRate = findViewById(R.id.editRate);
        txtPay = findViewById(R.id.txtPay);
        txtOvertime = findViewById(R.id.txtOvertime);
        txtTotal = findViewById(R.id.txtTotal);
        txtTax = findViewById(R.id.txtTax);
        btnCalculate = findViewById(R.id.btnCalculate);

        // Calculate button action
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculatePay();
            }
        });
    }

    private void calculatePay() {
        String hoursStr = editHours.getText().toString().trim();
        String rateStr = editRate.getText().toString().trim();

        // Validation
        if (hoursStr.isEmpty() || rateStr.isEmpty()) {
            Toast.makeText(this, "Please enter both fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        double hours = Double.parseDouble(hoursStr);
        double rate = Double.parseDouble(rateStr);
        double regularPay, overtimePay = 0, totalPay, tax;

        // Pay calculation
        if (hours <= 40) {
            regularPay = hours * rate;
        } else {
            overtimePay = (hours - 40) * rate * 1.5;
            regularPay = 40 * rate;
        }

        totalPay = regularPay + overtimePay;
        tax = totalPay * 0.18;

        // Display results
        txtPay.setText("Pay: $" + String.format("%.2f", regularPay));
        txtOvertime.setText("Overtime Pay: $" + String.format("%.2f", overtimePay));
        txtTotal.setText("Total Pay: $" + String.format("%.2f", totalPay));
        txtTax.setText("Tax: $" + String.format("%.2f", tax));

        // Add to history
        String record = "Hours: " + hours + " | Rate: $" + rate + " | Total: $" + totalPay;
        paymentHistory.add(record);

        Toast.makeText(this, "Payment calculated successfully!", Toast.LENGTH_SHORT).show();
    }

    // Add a menu to go to DetailActivity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_details) {
            startActivity(new Intent(this, DetailActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
