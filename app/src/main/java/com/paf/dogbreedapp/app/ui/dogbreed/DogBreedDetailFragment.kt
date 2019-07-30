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
import com.paf.dogbreedapp.app.di.component.DaggerNetworkComponent
import com.paf.dogbreedapp.app.di.module.NetworkModule
import com.paf.dogbreedapp.app.ui.MainActivity
import com.paf.dogbreedapp.app.ui.OnClickBackToolbarListener
import com.paf.dogbreedapp.app.viewmodel.dogbreed.DogBreedDetailViewModel
import com.paf.dogbreedapp.app.viewmodel.dogbreed.DogBreedDetailViewModelFactory
import com.paf.dogbreedapp.core.model.DogBreed
import com.paf.dogbreedapp.core.model.getName
import com.paf.dogbreedapp.core.model.datasource.DogBreedDataSource
import kotlinx.android.synthetic.main.list_layout.*
import javax.inject.Inject

class DogBreedDetailFragment: Fragment(), SwipeRefreshLayout.OnRefreshListener, OnClickBackToolbarListener {

    interface Listener {
        fun onBackClicked()
    }

    private lateinit var imageAdapter: DogBreedImageAdapter
    private var delegate: Listener? = null
    private val injector = DaggerNetworkComponent
        .builder()
        .networkModule(NetworkModule)
        .build()

    @Inject
    lateinit var dogBreedDataSource: DogBreedDataSource
    var dogBreed: DogBreed? = null

    private val listViewModel by lazy {
        ViewModelProviders.of(this,
            DogBreedDetailViewModelFactory(dogBreedDataSource)
        ).get(DogBreedDetailViewModel::class.java)
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

        inject()

        dogBreed = arguments?.getParcelable(MainActivity.DOG_BREED_KEY)

        toolbar.setBackListener(this)
        dogBreed?.getName()?.let { toolbar.setToolbarTitle(it) }

        imageAdapter = DogBreedImageAdapter()
        swipeRefreshLayout.setOnRefreshListener(this)

        dogBreed?.let { listViewModel.fetchDogBreeds(it) }
        observeViewModel()

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.adapter = imageAdapter
        recyclerView.layoutManager = layoutManager
    }

    private fun observeViewModel(){

        listViewModel.dogBreedLiveData.observe(this, Observer {
            it.imageList?.let { list -> imageAdapter.setItems(list) }
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
            DataLoadingState.LOADING -> swipeRefreshLayout.isRefreshing = true
            DataLoadingState.LOADED -> swipeRefreshLayout.isRefreshing = false
            DataLoadingState.FAILED -> swipeRefreshLayout.isRefreshing = false
            DataLoadingState.DEFAULT -> swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun onClickBackMenuListener() {
        delegate?.onBackClicked()
    }

    override fun onRefresh() {
        dogBreed?.let { listViewModel.fetchDogBreeds(it) }
    }

    private fun inject(){
        injector.inject(this)
    }
}