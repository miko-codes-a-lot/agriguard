package com.example.agriguard.modules.shared.ext

import io.realm.kotlin.types.RealmInstant
import kotlinx.datetime.Instant


fun Instant.toRealmInstant(): RealmInstant {
    return RealmInstant.from(epochSeconds, nanosecondsOfSecond)
}

fun RealmInstant.toKotlinInstant(): Instant {
    return Instant.fromEpochSeconds(epochSeconds, nanosecondsOfSecond.toLong())
}

fun RealmInstant.plusSeconds(secondsToAdd: Long): RealmInstant {
    val newEpochSeconds = this.epochSeconds + secondsToAdd
    val newNanoSeconds = this.nanosecondsOfSecond

    return RealmInstant.from(newEpochSeconds, newNanoSeconds)
}