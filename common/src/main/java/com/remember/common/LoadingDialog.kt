package com.remember.common

import android.app.Dialog
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import br.com.arch.toolkit.livedata.response.ResponseLiveData

class LoadingDialog<T>(
    private val response: ResponseLiveData<T>,
    val onData: (T) -> Unit,
    val onError: (Throwable) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return requireActivity().let { activity ->
            isCancelable = false
            val builder = AlertDialog.Builder(activity)

            response.observe(activity) {
                data {
                    onData.invoke(it)
                    dismiss()
                }
                error { it ->
                    onError.invoke(it)
                    dismiss()
                }
            }

            val progressBar = ProgressBar(activity).apply {
                isIndeterminate = true
            }
            builder.setView(progressBar)

            builder.create()
        }
    }
}
