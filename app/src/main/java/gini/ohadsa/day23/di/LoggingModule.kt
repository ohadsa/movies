package gini.ohadsa.day23.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import gini.ohadsa.day23.utils.LoggerDatasource
import gini.ohadsa.day23.utils.LoggerDatasourceImpl1
import javax.inject.Qualifier

@Qualifier
annotation class Logger1

@Qualifier
annotation class Logger2

@InstallIn(ActivityComponent::class)
@Module
abstract class ActivityLoggingModule {

    @Logger1
    @Binds
    abstract fun bindLogger1(logger: LoggerDatasourceImpl1): LoggerDatasource

}

@InstallIn(FragmentComponent::class)
@Module
abstract class FragmentLoggingModule {


    @Logger2
    @Binds
    abstract fun bindLogger2(logger: LoggerDatasourceImpl1): LoggerDatasource

}