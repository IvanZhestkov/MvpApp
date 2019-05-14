package com.itis.android.mvpapp.data.network.request

import com.itis.android.mvpapp.data.network.pojo.studapi.request.UploadTaskRequest
import io.reactivex.*
import okhttp3.MultipartBody
import okhttp3.RequestBody


class ApiRequestDecorator(val apiRequest: ApiRequest) : ApiRequest {

    companion object {
        private fun processApiThrowable(t: Throwable): Throwable {
            return Exception()
        }
    }

    private class ApiRequestErrorSingleTransformer<T> : SingleTransformer<T, T> {
        override fun apply(upstream: Single<T>): SingleSource<T> {
            return upstream.onErrorResumeNext { t: Throwable -> Single.error(processApiThrowable(t)) }
        }
    }

    private class ApiRequestErrorObservableTransformer<T> : ObservableTransformer<T, T> {
        override fun apply(upstream: Observable<T>): ObservableSource<T> {
            return upstream.onErrorResumeNext { t: Throwable -> Observable.error(processApiThrowable(t)) }
        }
    }

    private class ApiRequestErrorCompletableTransformer : CompletableTransformer {
        override fun apply(upstream: Completable): CompletableSource {
            return upstream.onErrorResumeNext { t: Throwable -> Completable.error(processApiThrowable(t)) }
        }
    }

    override fun uploadTaskCompletable(file: MultipartBody.Part, courseId: RequestBody, taskId: RequestBody, idToken: RequestBody): Completable {
        return apiRequest
                .uploadTaskCompletable(file, courseId, taskId, idToken)
                .compose(ApiRequestErrorCompletableTransformer())
    }
}