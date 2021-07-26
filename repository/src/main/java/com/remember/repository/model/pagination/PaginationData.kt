package com.remember.repository.model.pagination

sealed class PaginationStatus(val itemId: String)
abstract class DataPagination(id: String) : PaginationStatus(id)
object LoadingPagination: PaginationStatus("Loading")
object NoMorePagination: PaginationStatus("NoMore")
object ErrorPagination: PaginationStatus("Error")