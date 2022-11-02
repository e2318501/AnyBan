package com.github.tsuoihito.anyban.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class BannedPlayer(
    @JsonProperty val uuid: UUID,
    @JsonProperty val name: String,
    @JsonProperty val created: String,
    @JsonProperty val source: String,
    @JsonProperty val expires: String,
    @JsonProperty val reason: String,
)
