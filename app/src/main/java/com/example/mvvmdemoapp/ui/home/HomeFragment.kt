package com.example.mvvmdemoapp.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.local.entities.VideoEntity
import com.example.mvvmdemoapp.databinding.FragmentHomeBinding
import com.example.mvvmdemoapp.ui.base.ScreenFragment
import com.example.mvvmdemoapp.ui.home.adapter.DevByteAdapter
import com.example.repository.base.Resource
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : ScreenFragment() {
    private val homeViewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null
    private var viewModelAdapter: DevByteAdapter? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.prepareData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun initUI() {
        viewModelAdapter = DevByteAdapter(object: DevByteAdapter.VideoClick {
            override fun onClick(video: VideoEntity) {
                val packageManager = context?.packageManager ?: return
                var intent = Intent(Intent.ACTION_VIEW, video.launchUri)
                if(intent.resolveActivity(packageManager) == null) {
                    intent = Intent(Intent.ACTION_VIEW, Uri.parse(video.url))
                }
                startActivity(intent)
            }
        })
        recycler_view?.apply {
            adapter = viewModelAdapter
        }
    }

    override fun bindUI(lifecycleOwner: LifecycleOwner) {
    }

    override suspend fun bindUIAsync(lifecycleOwner: LifecycleOwner) {
        homeViewModel.playlistResultLiveData.await().observe(lifecycleOwner, Observer { resources ->
            if (resources == null) return@Observer
            when (resources.status) {
                Resource.Status.SUCCESS -> {
                    viewModelAdapter?.videos = resources.data.orEmpty()
                    loading_spinner?.visibility = View.GONE
                }
                Resource.Status.ERROR -> {
                    //handle error here
                    Toast.makeText(requireContext(), "get playlist error", Toast.LENGTH_LONG).show()
                    loading_spinner?.visibility = View.GONE
                }
                else -> {
                    //handle loading here
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}