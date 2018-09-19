package rich.on.pay.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.hawk.Hawk;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rich.on.pay.App;
import rich.on.pay.BuildConfig;
import rich.on.pay.model.DeviceToken;
import rich.on.pay.model.User;
import rich.on.pay.utils.DateDeserializer;

import static rich.on.pay.App.FIREBASE_TOKEN;
import static rich.on.pay.utils.LocaleUtils.ENGLISH;
import static rich.on.pay.utils.LocaleUtils.INDO;

public class API {
    private static final String TOKEN = "TOKEN";
    private static final String RESET_PASS = "RESET_PASS";
    private static final String OTP_SUCESS = "OTP_SUCESS";
    private static final String USER = "USER";
    private static final String CASHIER_MODEL = "CASHIER_MODEL";
    private static APIService SERVICE;
    private static boolean sessionError = false;
    private static Converter<ResponseBody, BadRequest> ERROR_CONVERTER;
    private static boolean ignoreToken = false;

    public static APIService service() {
        ignoreToken = false;
        if (SERVICE == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            String token = Hawk.get(TOKEN, "");
                            String bearer = "Bearer ";
                            if (token.isEmpty()) {
                                bearer = "";
                            }

                            final DeviceToken deviceTokenModel = Hawk.get(FIREBASE_TOKEN, null);
                            String deviceToken = "";
                            String osVersion = "";
                            String deviceModel = "";
                            try {
                                if (deviceTokenModel != null) {
                                    if (deviceTokenModel.getToken() != null) {
                                        deviceToken = deviceTokenModel.getToken();
                                    }
                                    osVersion = deviceTokenModel.getVersion();
                                    deviceModel = deviceTokenModel.getModel();
                                }
                            } catch (Exception exception) {
                                Log.e("deviceTokenModel", "" + exception);
                            }

                            Request original = chain.request();

                            try {
                                if (ignoreToken) {
                                    Request request = original.newBuilder()
                                            .addHeader("Content-Type", "application/json")
                                            .addHeader("Accept", "application/json")
                                            .addHeader("X-localization", Hawk.get(App.APP_LANGUAGE, "in"))
                                            .method(original.method(), original.body())
                                            .build();
                                    return chain.proceed(request);
                                } else {
                                    Request request = original.newBuilder()
                                            .addHeader("Content-Type", "application/json")
                                            .addHeader("Accept", "application/json")
                                            .addHeader("User-Timezone", "GMT+7")
                                            .addHeader("X-localization", Hawk.get(App.APP_LANGUAGE, "in"))
                                            .addHeader("Device-Type", "Android " + osVersion + " " + deviceModel)
                                            .addHeader("Authorization", bearer + token)
                                            .addHeader("Device-Token", deviceToken)
                                            .method(original.method(), original.body())
                                            .build();
                                    return chain.proceed(request);
                                }
                            } catch (Exception exception) {
                                Request request = original.newBuilder()
                                        .addHeader("Content-Type", "application/json")
                                        .addHeader("Accept", "application/json")
                                        .addHeader("User-Timezone", "GMT+7")
                                        .addHeader("X-localization", Hawk.get(App.APP_LANGUAGE, "in"))
                                        .addHeader("Device-Type", "Android " + osVersion + " " + deviceModel)
                                        .addHeader("Authorization", bearer + token)
                                        .addHeader("Device-Token", deviceToken)
                                        .method(original.method(), original.body())
                                        .build();
                                return chain.proceed(request);
                            }
                        }
                    })
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .registerTypeAdapter(Date.class, new DateDeserializer())
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(BuildConfig.ROOT_URL + "/api/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            ERROR_CONVERTER = retrofit.responseBodyConverter(BadRequest.class, new Annotation[0]);
            SERVICE = retrofit.create(APIService.class);
        }
        return SERVICE;
    }

    public static APIService serviceWithoutToken() {
        ignoreToken = true;
        if (SERVICE == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
//                    .addInterceptor(logging)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();
                            Request request = original.newBuilder()
                                    .addHeader("Content-Type", "application/json")
                                    .addHeader("Accept", "application/json")
                                    .addHeader("X-localization", Hawk.get(App.APP_LANGUAGE, "in"))
                                    .method(original.method(), original.body())
                                    .build();
                            return chain.proceed(request);
                        }
                    })
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .registerTypeAdapter(Date.class, new DateDeserializer())
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(BuildConfig.ROOT_URL + "/api/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            ERROR_CONVERTER = retrofit.responseBodyConverter(BadRequest.class, new Annotation[0]);
            SERVICE = retrofit.create(APIService.class);
        }
        return SERVICE;
    }

    static Converter<ResponseBody, BadRequest> getErrorConverter() {
        return ERROR_CONVERTER;
    }

    public static void setToken(String token) {
        Hawk.put(TOKEN, token);
    }

    public static String getToken() {
        return Hawk.get(TOKEN, "");
    }

    public static User currentUser() {
        return Hawk.get(USER);
    }

    public static void setUser(User profile) {
        Hawk.put(USER, profile);
    }

    public static void setOtpSucess(String status) {
        Hawk.put(OTP_SUCESS, status);
    }

    public static void logOut() {
        String defaultLocale = "";
        if (Locale.getDefault().getLanguage().matches("en")) {
            defaultLocale = ENGLISH;
        } else {
            defaultLocale = INDO;
        }
        DeviceToken deviceToken = new DeviceToken();
        deviceToken = Hawk.get(FIREBASE_TOKEN, deviceToken);
        String language = Hawk.get(App.APP_LANGUAGE, defaultLocale);
        boolean didSkipIntro = App.didntSkipIntro();

        App.isAuthorized = false;
        Hawk.deleteAll();

        App.setdidntSkipIntro(didSkipIntro);
        Hawk.put(App.DEVICE_SESSION, String.valueOf(new Date().getTime() + UUID.randomUUID().toString()));
        Hawk.put(App.APP_LANGUAGE, language);
        Hawk.put(FIREBASE_TOKEN, deviceToken);
    }

    public static boolean isLoggedIn() {
        return Hawk.get(TOKEN) != null;
    }

    public static boolean isOTPSucess() {
        return Hawk.get(OTP_SUCESS) != null;
    }

    public static boolean isSessionError() {
        return sessionError;
    }

    public static void setSessionError(boolean sessionError) {
        API.sessionError = sessionError;
    }
}