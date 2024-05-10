package com.liveperson.compose.common_ui.wrapper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.liveperson.compose.common_ui.R
import com.liveperson.compose.common_ui.utils.findFragment
import com.liveperson.compose.common_ui.utils.observe
import com.liveperson.infra.messaging_ui.fragment.ConversationFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class LivepersonWrapperFragment : Fragment() {

    private val _viewModel: LivePersonWrapperViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lp_conversation_wrapper, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewModel.initState.observe(viewLifecycleOwner) {
            handleInitialization(it)
        }
    }

    private fun handleInitialization(data: LPShowConversationData) {
        val lpAuthenticationParams = data.lpAuthenticationParams
        val conversationParams = data.conversationViewParams
        val brandId = data.brandId
        var fragment: ConversationFragment? = childFragmentManager.findFragment(ConversationFragment.TAG)
        if (fragment == null) {
            fragment = ConversationFragment.newInstance(
                brandId,
                lpAuthenticationParams,
                conversationParams,
                false
            )
        }
        if (fragment != null && !fragment.isInLayout) {
            childFragmentManager.commit {
                replace(R.id.fragmentContainer, fragment, ConversationFragment.TAG)
            }
        }
    }
}