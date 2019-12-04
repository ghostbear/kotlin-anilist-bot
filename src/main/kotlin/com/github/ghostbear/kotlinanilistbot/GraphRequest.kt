package com.github.ghostbear.kotlinanilistbot

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.jackson.responseObject
import com.github.kittinunf.result.Result
import com.taskworld.kraph.Kraph

interface IGraphRequest {
    val url: String
    val header: HashMap<String, Any>
    fun query(): Kraph
}

abstract class GraphRequest: IGraphRequest {
    override val url: String
        get() = AniList.url

    override val header: HashMap<String, Any>
        get() = AniList.headers
}

inline fun <reified T : Any> IGraphRequest.postRequest(noinline handler: (Request, Response, Result<T, FuelError>) -> Unit) {
    return this.url.httpPost().header(header).body(query().toRequestString()).responseObject<T>(handler::invoke)
}