package com.example.todo.di.network

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.todo.data.local.TodoItemDao
import com.example.todo.data.local.TodoItemDatabase
import com.example.todo.data.network.NetworkConnection
import com.example.todo.di.activity.MainActivityScope
import com.example.todo.di.app.AppScope
import dagger.Module
import dagger.Provides
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
interface NetworkModule {

    companion object{
        @Provides
        @AppScope
        fun provideHttpClient(): HttpClient {
            return HttpClient(Android){
                install(ContentNegotiation){
                    json(
                        Json {
                            prettyPrint = true
                            isLenient = true
                            ignoreUnknownKeys = true
                        }
                    )
                }

                install(Auth){
                    bearer {
                        loadTokens {
                            BearerTokens("Isilme", "Isilme")
                        }
                    }
                }

                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            Log.d("KTOR_REQUEST", message)
                        }
                    }
                    level = LogLevel.ALL
                }
            }
        }

        @AppScope
        @Provides
        fun provideDataBase(context: Context): TodoItemDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                TodoItemDatabase::class.java,
                "todoItems.db"
            )
                .build()
        }

        @Provides
        fun provideToDoDao(db: TodoItemDatabase): TodoItemDao {
            return db.dao
        }

        @Provides
        @AppScope
        fun provideNetworkConnection(context: Context) : NetworkConnection {
            return NetworkConnection(context)
        }
    }
}