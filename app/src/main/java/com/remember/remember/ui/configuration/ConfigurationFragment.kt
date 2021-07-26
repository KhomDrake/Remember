package com.remember.remember.ui.configuration

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import br.com.arch.toolkit.delegate.viewProvider
import com.remember.common.actions.toLogo
import com.remember.remember.BuildConfig
import com.remember.remember.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConfigurationFragment : Fragment(R.layout.app_fragment_home_configuration) {

    private val leaveAccount: AppCompatButton by viewProvider(R.id.leave_account_button)
    private val version: AppCompatTextView by viewProvider(R.id.version)
    private val configurationViewModel: ConfigurationViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        leaveAccount.setOnClickListener {
            configurationViewModel.logout().observeData(viewLifecycleOwner) {
                startActivity(requireContext().toLogo())
                requireActivity().finish()
            }
        }
        version.text = getString(R.string.app_version_app, BuildConfig.VERSION_NAME)
    }
}
