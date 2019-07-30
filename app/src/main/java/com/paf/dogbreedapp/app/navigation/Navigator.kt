package com.paf.dogbreedapp.app.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.paf.dogbreedapp.R
import com.paf.dogbreedapp.app.ui.MainActivity
import com.paf.dogbreedapp.app.ui.dogbreed.DogBreedDetailFragment
import com.paf.dogbreedapp.app.ui.dogbreed.DogBreedListFragment
import com.paf.dogbreedapp.core.model.DogBreed

interface DogBreedListListNavigator {

    fun FragmentActivity.navigateToDogBreedList() {
        switchToFragment(DogBreedListFragment())
    }
}

interface DogBreedDetailsNavigator {
    fun FragmentActivity.navigateToDogBreedDetails(dogBreed: DogBreed) {
        val fragment = prepareDogBreedDetailFragment(dogBreed)
        switchToFragment(fragment)
    }

    private fun prepareDogBreedDetailFragment(dogBreed: DogBreed) : DogBreedDetailFragment {
        val fragment = DogBreedDetailFragment()
        val args = Bundle()
        args.putParcelable(MainActivity.DOG_BREED_KEY, dogBreed)

        fragment.arguments = args
        return fragment
    }
}

fun FragmentActivity.switchToFragment(fragment: Fragment, args: Bundle? = null) {
    args?.let { fragment.arguments = it }
    this.supportFragmentManager.beginTransaction()
        .replace(R.id.fragmentContainer, fragment)
        .addToBackStack(null)
        .commit()
}

