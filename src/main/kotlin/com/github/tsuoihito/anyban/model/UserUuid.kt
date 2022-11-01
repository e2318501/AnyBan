package com.github.tsuoihito.anyban.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class UserUuid(
    @JsonProperty val name: String,
    @JsonProperty val uuid: UUID,
    @JsonProperty val expiresOn: String
)
