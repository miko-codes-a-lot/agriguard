package com.example.agriguard.modules

import com.example.agriguard.BuildConfig
import com.example.agriguard.modules.main.user.model.entity.Address
import com.example.agriguard.modules.main.user.model.entity.Indemnity
import com.example.agriguard.modules.main.rice.model.entity.RiceInsurance
import com.example.agriguard.modules.main.user.model.entity.User
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
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
                User::class,
                Address::class,
                Indemnity::class,
                RiceInsurance::class
            )

            val config = SyncConfiguration
                .Builder(
                    user,
                    setOfEntities
                )
                .initialSubscriptions { realm ->
                    add(
                        realm.query<User>("_id <> $0", null),
                        name = "User"
                    )
                    add(
                        realm.query<Address>("_id <> $0", null),
                        name = "Addresses"
                    )
                    add(
                        realm.query<Indemnity>("_id <> $0", null),
                        name = "Indemnity"
                    )
                    add(
                        realm.query<RiceInsurance>("_id <> $0", null),
                        name = "RiceInsurance"
                    )
                }
                .build()

            Realm.open(config)
        }
    }
}
