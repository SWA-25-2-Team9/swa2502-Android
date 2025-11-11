package com.example.swa2502.data.repository

import com.example.swa2502.data.datasource.ManageDataSource
import com.example.swa2502.domain.repository.ManageRepository

class ManageRepositoryImpl(
    private val remote: ManageDataSource
): ManageRepository{

}