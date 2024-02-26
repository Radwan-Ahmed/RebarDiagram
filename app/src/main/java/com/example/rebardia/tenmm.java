package com.example.rebardia;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class tenmm extends AppCompatActivity {
    private LinearLayout stirrupContainer;
    private Button buttonAddStirrup, buttonCalculate;
    private TextView textViewResult;
    private int stirrupCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenmm);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black_ash)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        stirrupContainer = findViewById(R.id.stirrupContainer);
        buttonAddStirrup = findViewById(R.id.buttonAddStirrup);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        textViewResult = findViewById(R.id.textViewResult);


        buttonAddStirrup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStirrupInput();
            }
        });

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateWeight();
            }
        });
        Button buttonErase = findViewById(R.id.buttonErase);
        buttonErase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eraseAllValues();
            }
        });

    }

    private List<EditText> editTextsList = new ArrayList<>();

    private void eraseAllValues() {
        // Clear all EditText values
        for (EditText editText : editTextsList) {
            editText.setText("");
        }
        stirrupContainer.removeAllViews();
        // Reset total weight result
        textViewResult.setText("Let's Start Calculating Total Weight and Order Quantity:");
        // Reset the editTextsList
        editTextsList.clear();
        // Reset stirrupCount
        stirrupCount = 0;
    }

    private void addStirrupInput() {
        stirrupCount++;

        TextView textViewStirrupNumber = new TextView(this);
        textViewStirrupNumber.setText("Stirrup " + stirrupCount);
        stirrupContainer.addView(textViewStirrupNumber);


        EditText editTextLength = new EditText(this);
        editTextLength.setHint("Length (in)");
        editTextLength.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editTextLength.setId(View.generateViewId()); // Set a unique ID
        editTextLength.setTextColor(Color.BLACK);
        stirrupContainer.addView(editTextLength);
        editTextsList.add(editTextLength); // Add to the list

        EditText editTextWidth = new EditText(this);
        editTextWidth.setHint("Width (in)");
        editTextWidth.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editTextWidth.setId(View.generateViewId()); // Set a unique ID
        editTextLength.setTextColor(Color.BLACK);
        stirrupContainer.addView(editTextWidth);
        editTextsList.add(editTextWidth); // Add to the list

        EditText editTextNumPieces = new EditText(this);
        editTextNumPieces.setHint("Number of Pieces");
        editTextNumPieces.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        editTextNumPieces.setId(View.generateViewId()); // Set a unique ID
        editTextLength.setTextColor(Color.BLACK);
        stirrupContainer.addView(editTextNumPieces);
        editTextsList.add(editTextNumPieces); // Add to the list

    }


    private void calculateWeight() {
        final double[] totalWeight = {0.00};
        final double[] roundedTotalWeight= {0.00};

        boolean allFieldsFilled = true;

        for (int i = 0; i < editTextsList.size(); i += 3) {
            try {
                EditText editTextLength = editTextsList.get(i);
                EditText editTextWidth = editTextsList.get(i + 1);
                EditText editTextNumPieces = editTextsList.get(i + 2);

                // Check if any of the fields is empty
                if (editTextLength.getText().toString().isEmpty() ||
                        editTextWidth.getText().toString().isEmpty() ||
                        editTextNumPieces.getText().toString().isEmpty()) {
                    allFieldsFilled = false;
                    break;  // Exit the loop early if any field is empty
                }

                double length = Double.parseDouble(editTextLength.getText().toString());
                double width = Double.parseDouble(editTextWidth.getText().toString());
                double numPieces = Double.parseDouble(editTextNumPieces.getText().toString());

                double final_cutLength = Math.ceil(2 * (length + width) * 25.4 + 2 * 105 - 7.4 * 10 + 1);
                double perMeterWeight = (10 * 10) / 162.2;
                double weight = numPieces * final_cutLength / 1000 * perMeterWeight;


                totalWeight[0] += weight;
                roundedTotalWeight[0] = Math.ceil(totalWeight[0] / 10) * 10;
            } catch (NumberFormatException | ClassCastException | NullPointerException e) {
                e.printStackTrace();
                allFieldsFilled = false;
            }
        }

        if (allFieldsFilled) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (textViewResult != null && textViewResult.getParent() != null) {

                            String formattedTotalWeight = String.format("%.2f", totalWeight[0]);

                            // Calculate the rounded result
                            double roundedResult = Math.ceil(totalWeight[0] / 10) * 10;
                            String formattedRoundedResult = String.format("%.2f", roundedResult);

                            // Combine both total and rounded results into a single string
                            String displayText = "Total Weight of all stirrups: " + formattedTotalWeight + " kg\n" +
                                    "The order quantity will be: " + formattedRoundedResult + " kg";

                            // Apply SpannableString to make only the digits bold and bigger
                            SpannableString spannableString = new SpannableString(displayText);

                            int startTotal = displayText.indexOf(formattedTotalWeight);
                            int endTotal = startTotal + formattedTotalWeight.length();
                            int startRounded = displayText.indexOf(formattedRoundedResult);
                            int endRounded = startRounded + formattedRoundedResult.length();

                            for (int i = 0; i < displayText.length(); i++) {
                                char c = displayText.charAt(i);
                                if (Character.isDigit(c)) {
                                    if (i >= startTotal && i < endTotal) {
                                        spannableString.setSpan(new StyleSpan(Typeface.BOLD), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                        spannableString.setSpan(new AbsoluteSizeSpan(20, true), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                        spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    } else if (i >= startRounded && i < endRounded) {
                                        spannableString.setSpan(new StyleSpan(Typeface.BOLD), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                        spannableString.setSpan(new AbsoluteSizeSpan(20, true), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                        spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    }else{
                                        spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    }
                                }else{
                                    spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                }
                            }
                            textViewResult.setText(spannableString);
                        } else {
                            Log.e("StirrupApp", "textViewResult is null or not part of the layout");
                        }
                    } catch (Exception e) {
                        Log.e("StirrupApp", "Error updating UI: " + e.getMessage());
                    }
                }
            });
        } else {
            // Notify the user that all fields must be filled
            showToast("Please fill in all fields");
        }
    }

    private void showToast(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

}