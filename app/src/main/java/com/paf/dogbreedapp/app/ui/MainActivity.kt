package com.paf.dogbreedapp.app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.paf.dogbreedapp.R
import com.paf.dogbreedapp.app.navigation.DogBreedDetailsNavigator
import com.paf.dogbreedapp.app.navigation.DogBreedListListNavigator
import com.paf.dogbreedapp.app.ui.dogbreed.DogBreedDetailFragment
import com.paf.dogbreedapp.app.ui.dogbreed.DogBreedListFragment
import com.paf.dogbreedapp.app.ui.dogbreed.DogBreedListFragment_MembersInjector
import com.paf.dogbreedapp.core.model.DogBreed

class MainActivity : AppCompatActivity(), DogBreedListListNavigator, DogBreedDetailsNavigator,
    DogBreedListFragment.Listener, DogBreedDetailFragment.Listener {

    companion object {
        const val DOG_BREED_KEY = "DOG_BREED_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigateToDogBreedList()
    }

    override fun onItemClicked(dogBreed: DogBreed) {
        navigateToDogBreedDetails(dogBreed)
    }

    override fun onBackClicked() {
        onBackPressed()
    }
}
