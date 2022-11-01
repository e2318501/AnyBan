package com.github.tsuoihito.anyban.model

import com.fasterxml.jackson.annotation.JsonProperty

data class BannedIp(
    @JsonProperty val ip: String,
    @JsonProperty val created: String,
    @JsonProperty val source: String,
    @JsonProperty val expires: String,
    @JsonProperty val reason: String
)
