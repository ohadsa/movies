package gini.ohadsa.day23.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gini.ohadsa.day23.networking.RemoteApi
import gini.ohadsa.day23.networking.RemoteApiImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{

    @Binds
    @Singleton
    abstract fun bindRemoteApi(remoteApiImpl: RemoteApiImpl): RemoteApi
}

