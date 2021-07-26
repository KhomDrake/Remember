package com.remember.extension

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText

fun EditText.onTextChanged(func: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) = Unit

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            func(p0.toString())
        }
    })
}

fun AppCompatEditText.onTextChanged(func: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) = Unit

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            func(p0.toString())
        }
    })
}

fun AppCompatEditText.formatDate() {
    addTextChangedListener(object : TextWatcher{

        var sb : StringBuilder = StringBuilder("")

        var _ignore = false

        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            if(_ignore){
                _ignore = false
                return
            }

            sb.clear()
            sb.append(if(s!!.length > 10){ s.subSequence(0,10) }else{ s })

            if(sb.lastIndex == 2){
                if(sb[2] != '/'){
                    sb.insert(2,"/")
                }
            } else if(sb.lastIndex == 5){
                if(sb[5] != '/'){
                    sb.insert(5,"/")
                }
            }

            _ignore = true
            setText(sb.toString())
            setSelection(sb.length)
        }
    })
}
