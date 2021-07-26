package com.remember.repository.model.terms

import com.remember.network.models.terms.TermItemResponse

class Term(
    val title: String,
    val items: List<TermItem>
)

class TermItem(
    val title: String?,
    val subTitle: String?,
    val description: String?,
    val type: String,
    val items: List<TermItem>
) {

    constructor(termItemResponse: TermItemResponse) : this(
        termItemResponse.title,
        termItemResponse.subTitle,
        termItemResponse.description,
        termItemResponse.type,
        termItemResponse.items.map { TermItem(it) }
    )
}