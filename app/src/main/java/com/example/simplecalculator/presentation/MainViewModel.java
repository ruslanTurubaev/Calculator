package com.example.simplecalculator.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.simplecalculator.domain.models.ArithmeticalExpressionUI;
import com.example.simplecalculator.domain.models.CheckInputSettings;
import com.example.simplecalculator.domain.usecase.AddDividersUseCase;
import com.example.simplecalculator.domain.usecase.CalculateUseCase;
import com.example.simplecalculator.domain.usecase.CheckInputUseCase;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {
    private AddDividersUseCase addDividersUseCase;
    private CheckInputUseCase checkInputUseCase;
    private CalculateUseCase calculateUseCase;

    private MutableLiveData<String> insertPanelLiveData = new MutableLiveData<>();
    private MutableLiveData<String> resultPanelLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> cursorPositionLiveData = new MutableLiveData<>();

    public LiveData<String> getInsertPanelLiveData() {
        if(insertPanelLiveData == null){
            insertPanelLiveData = new MutableLiveData<>();
            insertPanelLiveData.setValue("");
        }
        return insertPanelLiveData;
    }

    public LiveData<String> getResultPanelLiveData() {
        if(resultPanelLiveData == null){
            resultPanelLiveData = new MutableLiveData<>();
            resultPanelLiveData.setValue("");
        }
        return resultPanelLiveData;
    }

    public LiveData<Integer> getCursorPositionLiveData() {
        if(cursorPositionLiveData == null){
            cursorPositionLiveData = new MutableLiveData<>();
            cursorPositionLiveData.setValue(0);
        }
        return cursorPositionLiveData;
    }

    @Inject
    public MainViewModel(AddDividersUseCase addDividersUseCase, CheckInputUseCase checkInputUseCase, CalculateUseCase calculateUseCase) {
        this.addDividersUseCase = addDividersUseCase;
        this.checkInputUseCase = checkInputUseCase;
        this.calculateUseCase = calculateUseCase;

        insertPanelLiveData.setValue("");
        resultPanelLiveData.setValue("");
        cursorPositionLiveData.setValue(0);
    }

    public void erase (){
        insertPanelLiveData.setValue("");
        resultPanelLiveData.setValue("");
    }

    public void calculate(){
        if(Objects.requireNonNull(this.getInsertPanelLiveData().getValue()).length() > 0) {
            ArithmeticalExpressionUI arithmeticalExpressionUI = new ArithmeticalExpressionUI(insertPanelLiveData.getValue());

            String arithmeticalExpression = calculateUseCase.execute(arithmeticalExpressionUI).getArithmeticExpression();

            arithmeticalExpressionUI.setArithmeticExpression(arithmeticalExpression);
            arithmeticalExpressionUI = addDividersUseCase.execute(arithmeticalExpressionUI);

            resultPanelLiveData.setValue(arithmeticalExpressionUI.getArithmeticExpression());
        }
    }

    public void add(int cursorPosition, char addedCharacter){
        StringBuilder currentArithmeticalExpression = new StringBuilder(Objects.requireNonNull(this.getInsertPanelLiveData().getValue()));
        CheckInputSettings checkInputSettings = new CheckInputSettings(currentArithmeticalExpression.toString(), cursorPosition, addedCharacter);

        if(checkInputUseCase.execute(checkInputSettings)){
            int initialLength = currentArithmeticalExpression.length();

            currentArithmeticalExpression.insert(cursorPosition, addedCharacter);

            setLiveData(currentArithmeticalExpression.toString(), cursorPosition, initialLength);
        }
    }

    public void delete(int cursorPosition){
        if(Objects.requireNonNull(this.getInsertPanelLiveData().getValue()).length() != 0 || cursorPosition != 0) {
            StringBuilder currentArithmeticalExpression = new StringBuilder(this.getInsertPanelLiveData().getValue());
            int initialLength = currentArithmeticalExpression.length();

            currentArithmeticalExpression.delete(cursorPosition - 1, cursorPosition);

            setLiveData(currentArithmeticalExpression.toString(), cursorPosition, initialLength);
        }
    }

    private void setLiveData(String currentArithmeticalExpression, int cursorPosition, int initialLength){
        ArithmeticalExpressionUI arithmeticalExpressionUI = new ArithmeticalExpressionUI(currentArithmeticalExpression);
        arithmeticalExpressionUI = addDividersUseCase.execute(arithmeticalExpressionUI);

        int finalLength = arithmeticalExpressionUI.getArithmeticExpression().length();

        int cursorDelta = finalLength - initialLength;
        cursorPosition = cursorPosition + cursorDelta;

        insertPanelLiveData.setValue(arithmeticalExpressionUI.getArithmeticExpression());
        cursorPositionLiveData.setValue(cursorPosition);
    }
}
