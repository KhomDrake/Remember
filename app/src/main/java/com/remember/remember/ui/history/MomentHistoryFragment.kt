package com.remember.remember.ui.history

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import br.com.arch.toolkit.delegate.viewProvider
import com.remember.common.widget.loadUrl
import com.remember.extension.navControllerProvider
import com.remember.remember.R

class MomentHistoryFragment : Fragment(R.layout.moment_fragment_moment_detail) {

    private val navController: NavController by navControllerProvider()
    private val args: MomentHistoryFragmentArgs by navArgs()

    private val toolbar: Toolbar by viewProvider(R.id.toolbar)
    private val momentImage: AppCompatImageView by viewProvider(R.id.moment_image)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        momentImage.loadUrl(args.url)

        toolbar.setNavigationOnClickListener {
            navController.navigateUp()
        }
    }
}
