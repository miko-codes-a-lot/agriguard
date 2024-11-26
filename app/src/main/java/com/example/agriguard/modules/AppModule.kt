package com.example.agriguard.modules

import com.example.agriguard.modules.main.user.model.entity.User
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import com.example.agriguard.BuildConfig
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import kotlinx.coroutines.runBlocking
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRealmDatabase(): Realm {
        return runBlocking {
            val app = App.create(BuildConfig.REALM_APP_ID)
            val credentials = Credentials.apiKey(BuildConfig.REALM_API_KEY)
            val user = app.login(credentials)

            val setOfEntities = setOf(
                User::class
            )

            val config = SyncConfiguration
                .Builder(
                    user,
                    setOfEntities
                )
                .initialSubscriptions { realm ->
                    add(
                        realm.query<User>("_id <> $0", null),
                        name = "Users"
                    )
                }
                .build()

            Realm.open(config)
        }
    }
}
