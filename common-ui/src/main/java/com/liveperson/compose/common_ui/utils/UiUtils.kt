package com.liveperson.compose.common_ui.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

inline fun <reified T : Fragment> FragmentManager.findFragment(tag: String): T? {
    return findFragmentByTag(tag) as? T
}

inline fun <T> Flow<T>.observe(
    owner: LifecycleOwner,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: (T) -> Unit
): Job {
    return owner.lifecycleScope.launch {
        owner.repeatOnLifecycle(state) {
            collectLatest { block(it) }
        }
    }
}