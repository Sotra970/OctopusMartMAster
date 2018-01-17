package com.example.ahmed.octopusmart.Service;


import android.util.Log;


import com.example.ahmed.octopusmart.App.Appcontroler;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.ahmed.octopusmart.App.Config.BASE_URL;
import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;

public class Injector
{
    private static final String CACHE_CONTROL = "Cache-Control";
    public  static  int Retry_count = 3 ;
    public  static  int Retry_Time_Offset = 10000 ;

    public static Retrofit provideRetrofit (String baseUrl)
    {
        return new Retrofit.Builder()
                .baseUrl( baseUrl )
                .client( provideOkHttpClient() )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
    }


    public static Retrofit provideCasheRetrofit (String baseUrl)
    {
        return new Retrofit.Builder()
                .baseUrl( baseUrl )
                .client( provideOkHttpCacheClient() )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
    }

    private static OkHttpClient provideOkHttpCacheClient ()
    {
        return new OkHttpClient.Builder()
                .addInterceptor( provideHttpLoggingInterceptor() )
                .addInterceptor( provideOfflineCacheInterceptor() )
                .addNetworkInterceptor( provideCacheInterceptor() )
                .cache( provideCache() )
                .build();
    }


    private static OkHttpClient provideOkHttpClient ()
    {
        return new OkHttpClient.Builder()
                .addInterceptor( provideHttpLoggingInterceptor() )
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder().addHeader("User-Agent", "android").build();
                        return chain.proceed(request);
                    }
                })
                .connectTimeout(Retry_Time_Offset, TimeUnit.MILLISECONDS)
                .writeTimeout(Retry_Time_Offset, TimeUnit.MILLISECONDS)
                .readTimeout(Retry_Time_Offset, TimeUnit.MILLISECONDS)
                .build();
    }

    private static Cache provideCache ()
    {
        Cache cache = null;
        try
        {
            cache = new Cache( new File( Appcontroler.getInstance().getCacheDir(), "http-cache" ),
                               10 * 1024 * 1024 ); // 10 MB
        }
        catch (Exception e)
        {
            Log.e("http", e+ " Could not create Cache!" );
        }
        return cache;
    }

    private static HttpLoggingInterceptor provideHttpLoggingInterceptor ()
    {
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger()
                {
                    @Override
                    public void log (String message)
                    {
                      Log.e("http", message );
                    }
                } );
        httpLoggingInterceptor.setLevel(  BODY  );
        return httpLoggingInterceptor;
    }

    public static Interceptor provideCacheInterceptor ()
    {
        return new Interceptor()
        {
            @Override
            public Response intercept (Chain chain) throws IOException
            {
                Response response = chain.proceed( chain.request() );

                // re-write response header to force use of cache
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge( 2, TimeUnit.MINUTES )
                        .build();

                return response.newBuilder()
                        .header( CACHE_CONTROL, cacheControl.toString() )
                        .build();
            }
        };
    }

    public static Interceptor provideOfflineCacheInterceptor ()
    {
        return new Interceptor()
        {
            @Override
            public Response intercept (Chain chain) throws IOException
            {
                Request request = chain.request();

                if ( !Appcontroler.hasNetwork() )
                {
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale( 7, TimeUnit.DAYS )
                            .build();

                    request = request.newBuilder()
                            .cacheControl( cacheControl )
                            .build();
                }

                return chain.proceed( request );
            }
        };
    }

    public static  ApiInterface Api ()
    {
        return provideRetrofit( BASE_URL ).create( ApiInterface.class );
    }

    public static  UploadFileService UploadApi ()
    {
        return provideRetrofit( BASE_URL ).create( UploadFileService.class );
    }

    public static  ApiInterface CacheApi ()
    {
        return provideCasheRetrofit( BASE_URL ).create( ApiInterface.class );
    }

}
