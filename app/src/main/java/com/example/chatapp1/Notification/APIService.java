package com.example.chatapp1.Notification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAA9CriHvY:APA91bFn-aAlzjw_zJ46KD1rlEGfXckfYCTca-0Zn5UXzyxzmoWghqFaALNjnCtRFO-AXgN-vEIawJKAquxiNpQavJMLDr6nPtWEBFp7tLYUNusj9PZHYNndDAYyQ6sKqWpbJ-bYvIzi"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender sender);
}
