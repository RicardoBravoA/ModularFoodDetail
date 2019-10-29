package com.rba.modular.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rba.modular.detaild.data.repository.DetailDataRepository
import com.rba.modular.detaild.domain.usecase.DetailUseCase

class DetailViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            val detailUseCase =
                DetailUseCase(DetailDataRepository())
            DetailViewModel(detailUseCase) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}