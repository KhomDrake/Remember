package com.remember.common.bottomsheet

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import br.com.arch.toolkit.delegate.viewProvider
import com.remember.common.R
import com.remember.common.widget.EventEditText
import com.remember.common.widget.LoaderButton

typealias OnTextChanged = (String) -> Unit
typealias OnClickPositiveButton = (button: LoaderButton, customBottomSheet: CustomBottomSheetDialog) -> Unit
typealias OnClickNegativeButton = (button: LoaderButton, customBottomSheet: CustomBottomSheetDialog) -> Unit

class CustomBottomSheetDialog : RoundedBottomSheetDialogFragment(R.layout.common_custom_bottom_sheet_dialog) {

    private val title: AppCompatTextView by viewProvider(R.id.title)
    private val message: AppCompatTextView by viewProvider(R.id.message)
    private val input: EventEditText by viewProvider(R.id.input)
    private val negativeButton: LoaderButton by viewProvider(R.id.negative_button)
    private val positiveButton: LoaderButton by viewProvider(R.id.positive_button)
    private val singlePositiveButton: LoaderButton by viewProvider(R.id.single_positive_button)

    val inputValue: String
        get() = input.text

    private var titleText: String? = null
    @StringRes
    private var titleTextRes: Int = 0
    private var messageText: String? = null
    @StringRes
    private var messageTextRes: Int = 0
    private var inputHint: String? = null
    @StringRes
    private var inputHintRes: Int = 0
    private var defaultValueInput: String? = null

    private var onTextChangedInput: OnTextChanged? = null
    private var positiveButtonText: String? = null
    @StringRes
    private var positiveButtonTextRes: Int = 0
    private var negativeButtonText: String? = null
    @StringRes
    private var negativeButtonTextRes: Int = 0

    private var singlePositiveButtonFlag: Boolean = false
    private var onClickPositiveButton: OnClickPositiveButton? = null
    private var onClickNegativeButton: OnClickNegativeButton? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title.isVisible = titleText != null || titleTextRes != 0
        message.isVisible = messageText != null || messageTextRes != 0

        input.isVisible = inputHint != null || inputHintRes != 0
        positiveButton.isVisible = positiveButtonText != null || positiveButtonTextRes != 0
        negativeButton.isVisible = negativeButtonText != null || negativeButtonTextRes != 0

        title.text = if(titleTextRes != 0) getString(titleTextRes) else titleText
        message.text = if(messageTextRes != 0) getString(messageTextRes) else messageText

        input.setHint(if(inputHintRes != 0) getString(inputHintRes) else inputHint)
        positiveButton.setText(if(positiveButtonTextRes != 0) getString(positiveButtonTextRes) else positiveButtonText)
        singlePositiveButton.setText(if(positiveButtonTextRes != 0) getString(positiveButtonTextRes) else positiveButtonText)
        negativeButton.setText(if(negativeButtonTextRes != 0) getString(negativeButtonTextRes) else negativeButtonText)

        input.onTextChanged { onTextChangedInput?.invoke(it) }
        input.setText(defaultValueInput)

        singlePositiveButton.isVisible = singlePositiveButtonFlag
        positiveButton.isVisible = !singlePositiveButtonFlag
        negativeButton.isVisible = !singlePositiveButtonFlag

        if(singlePositiveButtonFlag) {
            singlePositiveButton.setOnClickListener {
                onClickPositiveButton?.invoke(singlePositiveButton, this)
            }
        }

        positiveButton.setOnClickListener {
            onClickPositiveButton?.invoke(positiveButton, this)
        }

        negativeButton.setOnClickListener {
            onClickNegativeButton?.invoke(negativeButton, this)
        }
    }

    class Builder {
        private var titleText: String? = null
        @StringRes
        private var titleTextRes: Int = 0
        private var messageText: String? = null
        @StringRes
        private var messageTextRes: Int = 0
        private var inputHint: String? = null
        @StringRes
        private var inputHintRes: Int = 0
        private var defaultValueInput: String? = null

        private var onTextChangedInput: OnTextChanged? = null
        private var positiveButtonText: String? = null
        @StringRes
        private var positiveButtonTextRes: Int = 0
        private var negativeButtonText: String? = null
        @StringRes
        private var negativeButtonTextRes: Int = 0

        private var singlePositiveButton = false
        private var onClickPositiveButton: OnClickPositiveButton? = null
        private var onClickNegativeButton: OnClickNegativeButton? = null

        fun setTitle(value: String) {
            titleText = value
        }

        fun singlePositiveButton() {
            singlePositiveButton = true
        }

        fun setTitle(@StringRes value: Int) {
            titleTextRes = value
        }

        fun setMessage(value: String) {
            messageText = value
        }

        fun setMessage(@StringRes value: Int) {
            messageTextRes = value
        }

        fun setInputHint(value: String) {
            inputHint = value
        }

        fun setInputHint(@StringRes value: Int) {
            inputHintRes = value
        }

        fun setDefaultValueInput(value: String) {
            defaultValueInput = value
        }

        fun onTextChangedInput(onTextChanged: OnTextChanged) {
            onTextChangedInput = onTextChanged
        }

        fun setPositiveButtonText(value: String) {
            positiveButtonText = value
        }

        fun setPositiveButtonText(@StringRes value: Int) {
            positiveButtonTextRes = value
        }

        fun setNegativeButtonText(value: String) {
            negativeButtonText = value
        }

        fun setNegativeButtonText(@StringRes value: Int) {
            negativeButtonTextRes = value
        }

        fun onClickPositiveButton(onClick: OnClickPositiveButton) {
            onClickPositiveButton = onClick
        }

        fun onClickNegativeButton(onClick: OnClickNegativeButton) {
            onClickNegativeButton = onClick
        }

        fun show(manager: FragmentManager, tag: String? = CustomBottomSheetDialog::class.java.name) : CustomBottomSheetDialog {
            val bottomSheet = CustomBottomSheetDialog()
            bottomSheet.titleText = titleText
            bottomSheet.titleTextRes = titleTextRes
            bottomSheet.messageText = messageText
            bottomSheet.messageTextRes = messageTextRes
            bottomSheet.inputHint = inputHint
            bottomSheet.inputHintRes = inputHintRes
            bottomSheet.onTextChangedInput = onTextChangedInput
            bottomSheet.positiveButtonText = positiveButtonText
            bottomSheet.positiveButtonTextRes = positiveButtonTextRes
            bottomSheet.negativeButtonText = negativeButtonText
            bottomSheet.negativeButtonTextRes = negativeButtonTextRes
            bottomSheet.onClickPositiveButton = onClickPositiveButton
            bottomSheet.onClickNegativeButton = onClickNegativeButton
            bottomSheet.defaultValueInput = defaultValueInput
            bottomSheet.singlePositiveButtonFlag = singlePositiveButton
            bottomSheet.show(manager, tag)
            return bottomSheet
        }
    }
}
