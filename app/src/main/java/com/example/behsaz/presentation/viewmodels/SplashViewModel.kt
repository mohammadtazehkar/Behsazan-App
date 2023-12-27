package com.example.behsaz.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.behsaz.domain.usecase.CheckUserExistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(private val checkUserExistUseCase: CheckUserExistUseCase) : ViewModel()  {

    private val _hasUserData = mutableStateOf(false)
    val hasUserData : State<Boolean> = _hasUserData

    init {
        checkData()
    }

    private fun checkData() = viewModelScope.launch {
        _hasUserData.value = checkUserExistUseCase.execute()
    }
}
//class SplashViewModelFactory(private val checkUserExistUseCase: CheckUserExistUseCase) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(SplashViewModel::class.java)){
//            return SplashViewModel(checkUserExistUseCase) as T
//        }
//        throw IllegalArgumentException("Unknown View Model Class")
//    }
//}