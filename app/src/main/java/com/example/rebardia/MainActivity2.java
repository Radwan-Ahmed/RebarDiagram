package com.example.rebardia;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity2 extends AppCompatActivity {
    private LinearLayout stirrupContainer;
    private Button buttonAddStirrup, buttonCalculate;
    private TextView textViewResult;
    private int stirrupCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

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
    }

    private void addStirrupInput() {
        stirrupCount++;

        TextView textViewStirrupNumber = new TextView(this);
        textViewStirrupNumber.setText("Stirrup " + stirrupCount);
        stirrupContainer.addView(textViewStirrupNumber);

        EditText editTextDiameter = new EditText(this);
        editTextDiameter.setHint("Diameter (mm)");
        editTextDiameter.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
        stirrupContainer.addView(editTextDiameter);

        EditText editTextLength = new EditText(this);
        editTextLength.setHint("Length (in)");
        editTextLength.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
        stirrupContainer.addView(editTextLength);

        EditText editTextWidth = new EditText(this);
        editTextWidth.setHint("Width (in)");
        editTextWidth.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
        stirrupContainer.addView(editTextWidth);


        EditText editTextPerHook = new EditText(this);
        editTextPerHook.setHint("Hook(mm)");
        editTextPerHook.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
        stirrupContainer.addView(editTextPerHook);

        EditText editTextNumPieces = new EditText(this);
        editTextNumPieces.setHint("Number of Pieces");
        editTextNumPieces.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        stirrupContainer.addView(editTextNumPieces);

    }


    private void calculateWeight() {
        int childCount = stirrupContainer.getChildCount();
        double totalWeight = 0.0;

        for (int i = 0; i < childCount; i += 9) {
            EditText editTextDiameter = (EditText) stirrupContainer.getChildAt(i + 1);
            EditText editTextLength = (EditText) stirrupContainer.getChildAt(i + 2);
            EditText editTextWidth = (EditText) stirrupContainer.getChildAt(i + 3);
            EditText editTextPerHook = (EditText) stirrupContainer.getChildAt(i + 4);
            EditText editTextNumPieces = (EditText) stirrupContainer.getChildAt(i + 5);


            double diameter = Double.parseDouble(editTextDiameter.getText().toString());
            double length = Double.parseDouble(editTextLength.getText().toString());
            double width = Double.parseDouble(editTextWidth.getText().toString());
            int numPieces = Integer.parseInt(editTextNumPieces.getText().toString());
            double hook = Double.parseDouble(editTextPerHook.getText().toString());




            double final_cutLength = Math.ceil(2*(length+width)*25.4+2*hook-7.4*diameter+1);
            double perMeterWeight = (diameter*diameter)/162.2;
            double weight = numPieces * final_cutLength/1000*perMeterWeight;
            totalWeight += weight;
        }

        textViewResult.setText("Total Weight of all stirrups: " + totalWeight + " kg");
    }
}