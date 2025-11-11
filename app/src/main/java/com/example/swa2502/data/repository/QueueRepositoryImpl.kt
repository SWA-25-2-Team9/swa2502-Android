package com.example.swa2502.data.repository

import com.example.swa2502.data.datasource.QueueDataSource
import com.example.swa2502.domain.repository.QueueRepository

class QueueRepositoryImpl(
    private val remote: QueueDataSource
): QueueRepository {

}