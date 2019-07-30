package com.paf.dogbreedapp.app.viewmodel.dogbreed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paf.dogbreedapp.app.DataLoadingState
import com.paf.dogbreedapp.app.DataLoadingState.*
import com.paf.dogbreedapp.core.model.DogBreed
import com.paf.dogbreedapp.core.model.datasource.DogBreedDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DogBreedDetailViewModel @Inject constructor(val dogBreedDataSource: DogBreedDataSource): ViewModel() {

    val dogBreedLiveData = MutableLiveData<DogBreed>()
    val dataStateLiveData = MutableLiveData<DataLoadingState>()
    val messageLiveData = MutableLiveData<String>()
    private lateinit var subscription: Disposable

    fun fetchDogBreeds(dogBreed: DogBreed){
        subscription = dogBreedDataSource.getImagesForBreed(dogBreed)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onLoadList() }
            .subscribe(
                { result -> onFetchListSuccess(result) },
                { result -> onFetchListError() }
            )
    }

    private fun onLoadList() {
        dataStateLiveData.value = LOADING
    }

    private fun onFetchListSuccess(dogBreed: DogBreed){
        dataStateLiveData.value = LOADED
        dogBreedLiveData.value = dogBreed
    }

    private fun onFetchListError(){
        dataStateLiveData.value = FAILED
        messageLiveData.value = "Error fetching images"
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}