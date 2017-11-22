package exercise.com.architecturecomponentspixabay.data;


import android.support.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import exercise.com.architecturecomponentspixabay.BuildConfig;
import exercise.com.architecturecomponentspixabay.PixabayApplication;
import okhttp3.Cache;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PixabayApi {
    private static OkHttpClient mOkHttpClient;
    private static Retrofit mRetrofitInstance;
    private static PixabayRestService mPixabayRestService;

    private static OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectionSpecs(Collections.singletonList(connectionSpec()));
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(@NonNull Chain chain) throws IOException {
                    Request original = chain.request();
                    HttpUrl originalHttpUrl = original.url();
                    HttpUrl.Builder urlBuilder = originalHttpUrl.newBuilder();
                    urlBuilder.addQueryParameter("editors_choice", "true");
                    urlBuilder.addQueryParameter("order", "popular");
                    urlBuilder.addQueryParameter("image_type", "photo");
                    urlBuilder.addQueryParameter("pretty", "false");
                    urlBuilder.addQueryParameter("per_page", "20");
                    urlBuilder.addEncodedQueryParameter("key", BuildConfig.KEY);
                    Request.Builder requestBuilder = original.newBuilder().url(urlBuilder.build());
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
            builder.addInterceptor(loggingInterceptor());
            mOkHttpClient = builder.build();
        }
        return mOkHttpClient;
    }

    private static HttpLoggingInterceptor loggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        return logging;
    }

    private static Retrofit getRetrofitInstance() {
        if (mRetrofitInstance == null) {
            mRetrofitInstance = new Retrofit.Builder()
                    .client(getOkHttpClient())
                    .baseUrl(BuildConfig.END_POINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return mRetrofitInstance;
    }

    private static ConnectionSpec connectionSpec() {
        return new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
                )
                .build();
    }

    public static PixabayRestService provideImageRestServices() {
        if (mPixabayRestService == null) {
            mPixabayRestService = getRetrofitInstance().create(PixabayRestService.class);
        }
        return mPixabayRestService;
    }
}
