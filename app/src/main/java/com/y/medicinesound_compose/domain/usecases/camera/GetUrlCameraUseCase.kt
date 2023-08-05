package com.y.medicinesound_compose.domain.usecases.camera

import android.net.Uri
import com.y.medicinesound_compose.domain.repositories.CustomCameraRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUrlCameraUseCase @Inject constructor(
    private val customCameraRepo: CustomCameraRepo
) {
    // invoke 사용시 호출 부에서 해당 메소드 이름을 호출하지 않고 class 이름 만으로 호출이 가능하다.
    // usecase는 repository 내부의 함수를 호출하는 역할만 하므로 invoke를 사용한다.
    operator fun invoke() : Flow<Uri?> = customCameraRepo.getSavedImageUri()
}