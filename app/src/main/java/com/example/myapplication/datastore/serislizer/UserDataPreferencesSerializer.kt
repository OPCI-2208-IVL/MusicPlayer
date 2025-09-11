package com.example.myapplication.datastore.serislizer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.myapplication.datastore.UserDataPreferences
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class UserDataPreferencesSerializer @Inject constructor(
): Serializer<UserDataPreferences>{
    override val defaultValue: UserDataPreferences = UserDataPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserDataPreferences =
        try {
            UserDataPreferences.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Can't read proto", e)
        }

    override suspend fun writeTo(t: UserDataPreferences, output: OutputStream) =
        t.writeTo(output)
}