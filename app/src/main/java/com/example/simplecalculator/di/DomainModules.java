package com.example.simplecalculator.di;

import com.example.simplecalculator.domain.provider.ProviderInterface;
import com.example.simplecalculator.domain.usecase.AddDividersUseCase;
import com.example.simplecalculator.domain.usecase.CalculateUseCase;
import com.example.simplecalculator.domain.usecase.CheckInputUseCase;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;

@Module
@InstallIn(ViewModelComponent.class)
public class DomainModules {

    @Provides
    public AddDividersUseCase provideAddDividersUseCase(){
        return new AddDividersUseCase();
    }

    @Provides
    public CheckInputUseCase provideCheckInputUseCase(){
        return new CheckInputUseCase();
    }

    @Provides
    public CalculateUseCase provideCalculateUseCase(ProviderInterface provider){
        return new CalculateUseCase(provider);
    }
}
