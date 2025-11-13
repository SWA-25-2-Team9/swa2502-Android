package com.example.swa2502.data.repository

import com.example.swa2502.data.datasource.ManageDataSource
import com.example.swa2502.domain.repository.ManageRepository
import javax.inject.Inject

class ManageRepositoryImpl @Inject constructor(
    private val remote: ManageDataSource
): ManageRepository{

}