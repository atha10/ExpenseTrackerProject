package com.example.expensetrackerproject;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.protobuf.StringValue;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class PopUpWindow {
    RadioButton transradioButton;
    EditText inputValue;
    RadioGroup radioGroup;


    //PopupWindow display method

    public void showPopupWindow(final View view) {


        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.popup_window, null);
        radioGroup = popupView.findViewById(R.id.radioGroup);
        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        final Spinner dropdown = popupView.findViewById(R.id.spinner1);
        String[] items = new String[]{"FOOD", "CLOTH", "SALARY", "INSURANCE", "GROCERIES", "EDUCATION", "UTILITIES BILLS", "OTHER"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        //Initialize the elements of our window, install the handler

        final CircularProgressButton buttonEdit = popupView.findViewById(R.id.submitButton);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonEdit.startAnimation();
                int selectedId = radioGroup.getCheckedRadioButtonId();
                transradioButton = (RadioButton) popupView.findViewById(selectedId);

                if (selectedId == -1) {
                    Toast.makeText(view.getContext(), "Nothing selected", Toast.LENGTH_SHORT).show();
                    buttonEdit.revertAnimation();
                } else {
                    inputValue = popupView.findViewById(R.id.valueInput);
                    String value = inputValue.getText().toString();
                    if (TextUtils.isEmpty(value)) {
                        Toast.makeText(view.getContext(), "Please Enter Value", Toast.LENGTH_SHORT).show();
                        buttonEdit.revertAnimation();
                    } else {
                        String ob = dropdown.getSelectedItem().toString();
                        String type = transradioButton.getText().toString();
                        transaction trans = new transaction();
                        trans.addTransaction(v, value, type, ob);
                        buttonEdit.revertAnimation();
                        Toast.makeText(view.getContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                        popupWindow.dismiss();
                    }
                }

            }
        });


        //Handler for clicking on the inactive zone of the window

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Close the window when clicked
                popupWindow.dismiss();
                return true;
            }
        });
    }

}
