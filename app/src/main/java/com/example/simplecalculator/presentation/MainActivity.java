package com.example.simplecalculator.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.simplecalculator.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    public MainViewModel mainViewModel;

    EditText insertPanel;
    TextView resultPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insertPanel = findViewById(R.id.insert_panel);
        resultPanel = findViewById(R.id.result_panel);

        insertPanel.setShowSoftInputOnFocus(false);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getInsertPanelLiveData().observe(this, s -> insertPanel.setText(s));
        mainViewModel.getResultPanelLiveData().observe(this, s -> resultPanel.setText(s));
        mainViewModel.getCursorPositionLiveData().observe(this, integer -> insertPanel.setSelection(integer));
    }

    public void onClickButton(View view) {
        CustomButton customButton = (CustomButton) view;
        int id = view.getId();
        switch (id){
            case (R.id.button_clear):
                customButton.runAnimation();
                AnimatedView animatedView = findViewById(R.id.animatedView);
                animatedView.runAnimation();
                animatedView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mainViewModel.erase();
                    }
                }, animatedView.getAnimationDuration());
                break;
            case (R.id.button_equal):
                customButton.runAnimation();
                mainViewModel.calculate();
                break;
            case (R.id.button_cursor_back):
                customButton.runAnimation();
                mainViewModel.delete(insertPanel.getSelectionStart());
                break;
            default:
                customButton.runAnimation();
                mainViewModel.add(insertPanel.getSelectionStart(), customButton.getText().charAt(0));
        }

    }
}