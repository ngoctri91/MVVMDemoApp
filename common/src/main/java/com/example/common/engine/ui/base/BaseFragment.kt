package com.example.common.engine.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment : Fragment(), CoroutineScope {
    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()

        viewLifecycleOwnerLiveData.observe(this, Observer { lifecycleOwner ->
            bindUI(lifecycleOwner)
            launch {
                if (lifecycleOwner != null) {
                    bindUIAsync(lifecycleOwner)
                }
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    abstract fun initUI()
    abstract fun bindUI(lifecycleOwner: LifecycleOwner)
    abstract suspend fun bindUIAsync(lifecycleOwner: LifecycleOwner)
}