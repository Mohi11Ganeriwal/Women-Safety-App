package com.example.expo.util;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.telephony.PhoneNumberUtils;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.HashMap;
import java.util.Map;

public class ContactFetcher {

    private static final int CONTACTS_PERMISSION_REQUEST_CODE = 123;
    private final Context context;

    public ContactFetcher(Context context) {
        this.context = context;
    }

    public void getContactNamesAsync(String[] numbers, FetchContactNameCallback callback) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (context instanceof android.app.Activity) {
                ActivityCompat.requestPermissions((android.app.Activity) context, new String[]{Manifest.permission.READ_CONTACTS}, CONTACTS_PERMISSION_REQUEST_CODE);
            }
            callback.onContactNameFetched(new HashMap<>());
            return;
        }

        new Thread(() -> {
            Map<String, String> contactNames = getContactNames(numbers);

            new Handler(Looper.getMainLooper()).post(() -> callback.onContactNameFetched(contactNames));
        }).start();
    }

    private Map<String, String> getContactNames(String[] numbers) {
        Map<String, String> contactNames = new HashMap<>();
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};

        try (Cursor cursor = contentResolver.query(uri, projection, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String contactNumber = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    String contactName = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                    contactNumber = normalizeNumber(contactNumber);

                    for (String number : numbers) {
                        if (PhoneNumberUtils.compare(normalizeNumber(number), contactNumber)) {
                            contactNames.put(number, contactName);
                            break;
                        }
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("ContactFetcher", "Error fetching contacts", e);
        }

        return contactNames;
    }

    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults, ContactFetcher contactFetcher, String[] numbers, FetchContactNameCallback callback) {
        if (requestCode == CONTACTS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                contactFetcher.getContactNamesAsync(numbers, callback);
            } else {
                callback.onContactNameFetched(new HashMap<>());
            }
        }
    }

    public interface FetchContactNameCallback {
        void onContactNameFetched(Map<String, String> contactNames);
    }

    private String normalizeNumber(String number) {
        if (number == null) return "";
        return number.replaceAll("[^0-9]", "")
                .replaceFirst("^91", "")
                .replaceFirst("^\\+91", "");
    }
}

