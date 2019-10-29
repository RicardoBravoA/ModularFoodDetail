package com.rba.modular.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rba.modular.detaild.domain.usecase.DetailUseCase
import com.rba.modular.model.model.DetailModel
import com.rba.modular.model.model.ErrorModel
import com.rba.modular.util.ScopedViewModel
import com.rba.modular.util.domain.ResultType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailViewModel(private val detailUseCase: DetailUseCase) : ScopedViewModel() {

    private val mutableModel = MutableLiveData<UiViewModel>()
    val model: LiveData<UiViewModel>
        get() {
            if (mutableModel.value == null) refresh()
            return mutableModel
        }

    init {
        initScope()
    }

    fun refresh() {
        mutableModel.value = UiViewModel.Refresh
    }

    fun getData(id: String) {
        GlobalScope.launch(Dispatchers.Main) {
            when (val result = detailUseCase.getDetail(id)) {
                is ResultType.Success -> mutableModel.value = UiViewModel.ShowData(result.value)
                is ResultType.Error -> mutableModel.value = UiViewModel.ShowError(result.value)
            }
        }

    }

    sealed class UiViewModel {
        class ShowData(val detailModel: DetailModel) : UiViewModel()
        class ShowError(val errorModel: ErrorModel) : UiViewModel()
        object Refresh : UiViewModel()
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

}