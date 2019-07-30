package com.paf.dogbreedapp.app.viewmodel.dogbreed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.paf.dogbreedapp.core.model.datasource.DogBreedDataSource

class DogBreedListViewModelFactory(private val dogBreedDataSource: DogBreedDataSource): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = DogBreedListViewModel(dogBreedDataSource) as T
}