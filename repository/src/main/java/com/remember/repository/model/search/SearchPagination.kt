package com.remember.repository.model.search

import com.remember.network.models.search.SearchPaginationResponse

class SearchPagination(
    val next: String?,
    val previous: String?,
    val count: Int,
    val results: List<UserSearch>
)

fun SearchPaginationResponse.toSearchPagination() = SearchPagination(
    next,
    previous,
    count,
    results.map { UserSearch(it) }
)