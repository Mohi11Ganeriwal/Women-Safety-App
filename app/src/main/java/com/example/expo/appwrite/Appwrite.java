package com.example.expo.appwrite;

import android.content.Context;

import io.appwrite.Client;

public class Appwrite {
    private static Appwrite single_instance = null;
    private Context globalContext;
    public Auth auth;
    private Client client;

    private Appwrite(Context mContext)
    {
        globalContext = mContext;
        client = new Client(mContext, "https://app.zephyrus.in/v1");
        client.setProject("678291ea000620ca604f");
        auth = new Auth(client);
    }

    public static synchronized Appwrite getInstance(Context context)
    {
        if (single_instance == null)
            single_instance = new Appwrite(context);

        return single_instance;
    }
}