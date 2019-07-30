package com.paf.dogbreedapp.app.ui.dogbreed

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.paf.dogbreedapp.R
import com.paf.dogbreedapp.app.DataLoadingState
import com.paf.dogbreedapp.app.DataLoadingState.*
import com.paf.dogbreedapp.app.di.component.DaggerNetworkComponent
import com.paf.dogbreedapp.app.di.module.NetworkModule
import com.paf.dogbreedapp.app.viewmodel.dogbreed.DogBreedListViewModel
import com.paf.dogbreedapp.app.viewmodel.dogbreed.DogBreedListViewModelFactory
import com.paf.dogbreedapp.core.model.DogBreed
import com.paf.dogbreedapp.core.model.datasource.DogBreedDataSource
import kotlinx.android.synthetic.main.list_layout.*
import javax.inject.Inject

class DogBreedListFragment: Fragment(), SwipeRefreshLayout.OnRefreshListener, DogBreedAdapter.Listener {

    interface Listener {
        fun onItemClicked(dogBreed: DogBreed)
    }

    private lateinit var dogBreedAdapter: DogBreedAdapter
    private var delegate: Listener? = null
    private val injector = DaggerNetworkComponent
        .builder()
        .networkModule(NetworkModule)
        .build()

    @Inject
    lateinit var dogBreedDataSource: DogBreedDataSource

    private val listViewModel by lazy {
        ViewModelProviders.of(this,
            DogBreedListViewModelFactory(dogBreedDataSource))
            .get(DogBreedListViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            delegate = context as Listener?
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement DogBreedListFragment.Listener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.list_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.hideBackArrow()
        context?.getString(R.string.dog_breed_list_title)?.let { toolbar.setToolbarTitle(it) }

        inject()
        dogBreedAdapter = DogBreedAdapter()
        dogBreedAdapter.delegate = this
        swipeRefreshLayout.setOnRefreshListener(this)

        listViewModel.fetchDogBreeds()
        observeViewModel()

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.adapter = dogBreedAdapter
        recyclerView.layoutManager = layoutManager
    }

    private fun observeViewModel(){

        listViewModel.dogBreedListLiveData.observe(this, Observer {
                dogBreedAdapter.setItems(it)
            })

        listViewModel.dataStateLiveData.observe(this, Observer {
                onDataLoadingStateChanged(it)
            })

        listViewModel.messageLiveData.observe(this, Observer {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            })
    }

    private fun onDataLoadingStateChanged(dataLoadingState: DataLoadingState){
        when(dataLoadingState){
            LOADING -> swipeRefreshLayout.isRefreshing = true
            LOADED -> swipeRefreshLayout.isRefreshing = false
            FAILED -> swipeRefreshLayout.isRefreshing = false
            DEFAULT -> swipeRefreshLayout.isRefreshing = false
        }
    }


    override fun onClickItemInList(dogBreed: DogBreed) {
        delegate?.onItemClicked(dogBreed)
    }

    override fun onRefresh() {
        listViewModel.fetchDogBreeds()
    }

    private fun inject(){
        injector.inject(this)
    }

}