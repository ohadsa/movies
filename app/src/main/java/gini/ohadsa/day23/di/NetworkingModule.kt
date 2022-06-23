package gini.ohadsa.day23.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gini.ohadsa.day23.BuildConfig
import gini.ohadsa.day23.networking.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//hilt is dagger(java) for android

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {
    private const val API_KEY_NAME = "api_key"
    private const val BASE_URL = BuildConfig.TMDB_BASE_URL
    private const val API_KEY_VALUE = BuildConfig.TMDB_API_KEY


    @Provides
    fun provideGsonFactory(): Converter.Factory = GsonConverterFactory.create()

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // full log please
    }

    //provides an object for injection
    // בנייה של אובייקטים שלא אנחנו אלה שכתבו להן בנאי
    @Provides
    fun provideAuthorizationInterceptor() = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()

            if (API_KEY_VALUE.isBlank()) return chain.proceed(originalRequest)
            val url =
                originalRequest.url.newBuilder().addQueryParameter(API_KEY_NAME, API_KEY_VALUE)
                    .build()
            val newRequest = originalRequest.newBuilder().url(url).build()
            return chain.proceed(newRequest)
        }
    }

    @Provides
    fun provideOKHTTPClient(
        authInterceptor: Interceptor,
        loggingInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor).build()

    @Provides
    fun provideRetrofit(
        httpClient: OkHttpClient,
        gsonConverterFactory: Converter.Factory
    ): Retrofit = Retrofit.Builder()
        .client(httpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(gsonConverterFactory)
        .build()

    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieApi = retrofit.create(MovieApi::class.java)

    @Provides
    fun provideTVService(retrofit: Retrofit): TVShowApi = retrofit.create(TVShowApi::class.java)

    @Provides
    fun providePersonService(retrofit: Retrofit): PersonApi = retrofit.create(PersonApi::class.java)
}
//    @Provides
//    fun provideRemoteApi(movieApi: MovieApi , tvShowApi: TVShowApi , personApi: PersonApi): RemoteApi =
//        RemoteApiImpl(movieApi , tvShowApi , personApi)
//}