package com.remember.profile.ui.picture.preview

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import br.com.arch.toolkit.delegate.viewProvider
import com.remember.common.extension.showError
import com.remember.common.extension.showSuccess
import com.remember.common.widget.LoaderButton
import com.remember.extension.navControllerProvider
import com.remember.profile.R
import de.hdodenhof.circleimageview.CircleImageView
import org.koin.androidx.viewmodel.ext.android.viewModel

class PreviewProfilePictureFragment : Fragment(R.layout.profile_fragment_preview_profile_picture) {

    private val toolbar: AppCompatImageView by viewProvider(R.id.toolbar)
    private val navController: NavController by navControllerProvider()
    private val update: LoaderButton by viewProvider(R.id.update)
    private val preview: CircleImageView by viewProvider(R.id.preview)
    private val root: ViewGroup by viewProvider(R.id.root)
    private val args: PreviewProfilePictureFragmentArgs by navArgs()
    private val viewModel: ChangeProfilePictureViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val screen = requireActivity().window.decorView.width
        val size = screen / 2
        preview.layoutParams?.run {
            width = size
            height = size
        }
        preview.requestLayout()
        val uri = Uri.fromFile(args.file)
        preview.scaleType = ImageView.ScaleType.CENTER_CROP
        preview.setImageURI(uri)

        toolbar.setOnClickListener {
            navController.navigateUp()
        }

        update.setOnClickListener {
            viewModel.changeProfilePicture(args.profileId, args.file).observe(viewLifecycleOwner) {
                success {
                    showSuccess(R.string.profile_profile_changed_success, root)
                    navController.navigate(
                        PreviewProfilePictureFragmentDirections.actionPreviewProfilePictureFragmentToProfileFragment2()
                    )
                }
                showLoading {
                    update.onLoading(true)
                }
                error { _ ->
                    update.onLoading(false)
                    showError(R.string.profile_profile_changed_failure, root)
                }
            }
        }
    }
}
