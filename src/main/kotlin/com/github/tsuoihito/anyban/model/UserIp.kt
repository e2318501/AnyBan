package com.github.tsuoihito.anyban.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UserIp(
    @JsonProperty val name: String,
    @JsonProperty val ips: List<String>
)
