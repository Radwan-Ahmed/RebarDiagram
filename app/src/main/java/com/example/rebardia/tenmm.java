package com.example.rebardia;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
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

public class tenmm extends AppCompatActivity {
    private LinearLayout stirrupContainer;
    private Button buttonAddStirrup, buttonCalculate;
    private TextView textViewResult;
    private int stirrupCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenmm);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black_ash)));
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
        textViewResult.setText("Total Weight of all stirrups: 0.00 kg");
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
        stirrupContainer.addView(editTextLength);
        editTextsList.add(editTextLength); // Add to the list

        EditText editTextWidth = new EditText(this);
        editTextWidth.setHint("Width (in)");
        editTextWidth.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editTextWidth.setId(View.generateViewId()); // Set a unique ID
        stirrupContainer.addView(editTextWidth);
        editTextsList.add(editTextWidth); // Add to the list

        EditText editTextNumPieces = new EditText(this);
        editTextNumPieces.setHint("Number of Pieces");
        editTextNumPieces.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        editTextNumPieces.setId(View.generateViewId()); // Set a unique ID
        stirrupContainer.addView(editTextNumPieces);
        editTextsList.add(editTextNumPieces); // Add to the list

    }


    private void calculateWeight() {
        final double[] totalWeight = {0.00};
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
                            String formattedWeight = String.format("%.2f", totalWeight[0]);
                            textViewResult.setText("Total Weight of all stirrups: " + String.valueOf(formattedWeight) + " kg");
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