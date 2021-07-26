package com.remember.profile.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import br.com.arch.toolkit.delegate.viewProvider
import com.remember.extension.navControllerProvider
import com.remember.extension.setStatusBar
import com.remember.profile.R

class DeleteAccountFragment : Fragment(R.layout.profile_fragment_delete_account) {

    private val navController: NavController by navControllerProvider()
    private val back: AppCompatTextView by viewProvider(R.id.back)
    private val container: ConstraintLayout by viewProvider(R.id.container)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBar(R.color.remember_red)
        container.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

        back.setOnClickListener {
            navController.navigateUp()
        }
    }
}