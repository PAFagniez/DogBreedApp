package com.paf.dogbreedapp.app.di.component

import com.paf.dogbreedapp.app.di.module.NetworkModule
import com.paf.dogbreedapp.app.ui.dogbreed.DogBreedDetailFragment
import com.paf.dogbreedapp.app.ui.dogbreed.DogBreedListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface NetworkComponent {

   fun inject(fragment: DogBreedListFragment)
   fun inject(fragment: DogBreedDetailFragment)

   @Component.Builder
   interface Builder {
      fun build(): NetworkComponent
      fun networkModule(networkModule: NetworkModule): Builder
   }
}