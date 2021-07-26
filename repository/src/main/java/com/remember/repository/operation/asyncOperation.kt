package com.remember.repository.operation

import br.com.arch.toolkit.livedata.response.MutableResponseLiveData
import br.com.arch.toolkit.livedata.response.ResponseLiveData
import com.remember.repository.cache.CacheStrategy
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

private val supervisorJob = SupervisorJob()
private val scope = CoroutineScope(Dispatchers.IO + supervisorJob)

fun <T> asyncLiveData(
    liveData: MutableResponseLiveData<T> = MutableResponseLiveData(),
    func: suspend () -> T
): ResponseLiveData<T> {
    return asyncLiveData(
        liveData = liveData,
        func = func,
        cacheStrategy = CacheStrategy.NONE
    )
}

fun <T> asyncLiveData(
    key: String = "",
    cache: HashMap<String, T> = hashMapOf(),
    cacheStrategy: CacheStrategy = CacheStrategy.NONE,
    liveData: MutableResponseLiveData<T> = MutableResponseLiveData(),
    func: suspend () -> T
): ResponseLiveData<T> {
    scope.launch {
        try {
            liveData.postLoading()
            when (cacheStrategy) {
                CacheStrategy.NONE -> {
                    liveData.postData(func.invoke())
                }
                CacheStrategy.CACHE -> {
                    if(cache.containsKey(key)) {
                        cache[key]?.let {
                            liveData.postData(it)
                        }
                    } else {
                        val data = func.invoke()
                        cache[key] = data
                        liveData.postData(data)
                    }
                }
                CacheStrategy.UPDATE -> {
                    cache[key]?.let {
                        liveData.postData(it)
                    }
                    val data = func.invoke()
                    cache[key] = data
                    liveData.postData(data)
                }
            }
        } catch (error: Exception) {
            liveData.postError(error)
        }
    }
    return liveData
}
