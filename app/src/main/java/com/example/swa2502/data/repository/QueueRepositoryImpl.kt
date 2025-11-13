package com.example.swa2502.data.repository

import com.example.swa2502.data.datasource.QueueDataSource
import com.example.swa2502.domain.repository.QueueRepository
import javax.inject.Inject

class QueueRepositoryImpl @Inject constructor(
    private val remote: QueueDataSource
): QueueRepository {

}