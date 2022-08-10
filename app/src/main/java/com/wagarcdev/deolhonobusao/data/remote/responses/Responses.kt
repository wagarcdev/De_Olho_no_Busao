package com.wagarcdev.deolhonobusao.data.remote.responses


import androidx.annotation.Keep

@Keep
data class Responses(
    var auth: Auth,
    var event: List<Event>,
    var info: Info,
    var item: List<Item>,
    var protocolProfileBehavior: ProtocolProfileBehavior,
    var variable: List<Variable>
) {
    @Keep
    data class Auth(
        var apikey: List<Apikey>,
        var type: String
    ) {
        @Keep
        data class Apikey(
            var key: String,
            var type: String,
            var value: String
        )
    }

    @Keep
    data class Event(
        var listen: String,
        var script: Script
    ) {
        @Keep
        data class Script(
            var exec: List<String>,
            var id: String,
            var type: String
        )
    }

    @Keep
    data class Info(
        var _postman_id: String,
        var name: String,
        var schema: String
    )

    @Keep
    data class Item(
        var description: String?,
        var event: List<Event>?,
        var item: List<Item>?,
        var name: String,
        var protocolProfileBehavior: ProtocolProfileBehavior?,
        var request: Request?,
        var response: List<Any>?
    ) {
        @Keep
        data class Event(
            var listen: String,
            var script: Script
        ) {
            @Keep
            data class Script(
                var exec: List<String>,
                var id: String,
                var type: String
            )
        }

        @Keep
        data class Item(
            var name: String,
            var request: Request,
            var response: List<Any>
        ) {
            @Keep
            data class Request(
                var description: String,
                var header: List<Any>,
                var method: String,
                var url: Url
            ) {
                @Keep
                data class Url(
                    var host: List<String>,
                    var path: List<String>,
                    var query: List<Query>?,
                    var raw: String
                ) {
                    @Keep
                    data class Query(
                        var description: String,
                        var disabled: Boolean?,
                        var key: String,
                        var value: String?
                    )
                }
            }
        }

        @Keep
        class ProtocolProfileBehavior

        @Keep
        data class Request(
            var description: String,
            var header: List<Any>,
            var method: String,
            var url: Url
        ) {
            @Keep
            data class Url(
                var host: List<String>,
                var path: List<String>,
                var raw: String
            )
        }
    }

    @Keep
    class ProtocolProfileBehavior

    @Keep
    data class Variable(
        var id: String,
        var key: String,
        var type: String,
        var value: String
    )
}