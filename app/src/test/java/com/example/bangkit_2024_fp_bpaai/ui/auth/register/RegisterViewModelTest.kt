package com.example.bangkit_2024_fp_bpaai.ui.auth.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.bangkit_2024_fp_bpaai.data.DataDummy
import com.example.bangkit_2024_fp_bpaai.data.StoryRepository
import com.example.bangkit_2024_fp_bpaai.data.remote.response.RegisterResponse
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import com.example.bangkit_2024_fp_bpaai.data.remote.Result
import com.example.bangkit_2024_fp_bpaai.getOrAwaitValue
import org.junit.Assert
import org.mockito.Mockito
import org.mockito.Mockito.`when`

@RunWith(MockitoJUnitRunner::class)
class RegisterViewModelTest {
    @get: Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var registerViewModel: RegisterViewModel
    private val dummyRegister = DataDummy.generateDummyRegisterResponse()

    @Before
    fun setUp() {
        registerViewModel = RegisterViewModel(storyRepository)
    }

    @Test
    fun `when Register Should  Not Null and Return Success`() = runTest {
        val expectedUser = MutableLiveData<Result<RegisterResponse>>()
        expectedUser.value = Result.Success(dummyRegister)
        `when`(registerViewModel.register(NAME, EMAIL, PASSWORD)).thenReturn(expectedUser)

        val actualUser = registerViewModel.register(NAME, EMAIL, PASSWORD).getOrAwaitValue()

        Mockito.verify(storyRepository).register(NAME, EMAIL, PASSWORD)
        Assert.assertNotNull(actualUser)
        Assert.assertTrue(actualUser is Result.Success)
    }

    companion object {
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val PASSWORD = "password"
    }
}