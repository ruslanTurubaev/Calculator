package com.example.simplecalculator.di;

import com.example.simplecalculator.data.engine.Calculator;
import com.example.simplecalculator.data.provider.CalculatorProvider;
import com.example.simplecalculator.domain.provider.ProviderInterface;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DataModules {

    @Provides
    @Singleton
    public ProviderInterface provideProviderInterface(Calculator calculator){
        return new CalculatorProvider(calculator);
    }

    @Provides
    @Singleton
    public Calculator provideCalculator(){
        return new Calculator();
    }
}
