package com.remember.remember.ui.terms

import androidx.lifecycle.ViewModel
import com.remember.repository.TermsRepository

class TermViewModel(private val termsRepository: TermsRepository) : ViewModel() {

    fun terms() = termsRepository.terms()

    fun acceptTerms() = termsRepository.acceptTerms()

}
