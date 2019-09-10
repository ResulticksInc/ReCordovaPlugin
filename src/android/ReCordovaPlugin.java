package resu.io;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import io.mob.resu.reandroidsdk.AppConstants;
import io.mob.resu.reandroidsdk.AppLifecyclePresenter;
import io.mob.resu.reandroidsdk.MRegisterUser;
import io.mob.resu.reandroidsdk.ReAndroidSDK;
import io.mob.resu.reandroidsdk.error.Log;


/**
 * This class echoes a string called from JavaScript.
 */
public class ReCordovaPlugin extends CordovaPlugin {
    private static final String TAG = "ReCordovaPlugin";

    public static CordovaWebView gWebView;
    public static JSONObject jsonObject;
    String OldScreenName = null;
    String newScreenName = null;
    CallbackContext NotificationCallbacks;
    ArrayList<JSONObject> notificationByObject;
    private Calendar oldCalendar = Calendar.getInstance();
    private Calendar sCalendar = Calendar.getInstance();

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals("notificationPayload")) {
                if (NotificationCallbacks != null)
                    try {
                        NotificationCallbacks.success(new JSONObject(intent.getExtras().getString("notification")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }


    };

    public ReCordovaPlugin() {

    }

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        gWebView = webView;
        AppConstants.isHyBird = true;
        android.util.Log.d(TAG, "==> ReCordovaPlugin initialize");
        LocalBroadcastManager.getInstance(cordova.getActivity()).registerReceiver(mMessageReceiver, new IntentFilter("notification"));
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        switch (action) {

            case "userRegister":
                this.userRegister(args, callbackContext);
                break;

            case "customEvent":
                this.customEvent(args, callbackContext);
                break;

            case "screenNavigation":
                this.screenNavigation(args, callbackContext);
                break;

            case "locationUpdate":
                this.locationUpdate(args, callbackContext);
                break;

            case "getNotification":

                this.getNotification(args, callbackContext);
                break;

            case "deleteNotification":
                this.deleteNotification(args, callbackContext);
                break;

            case "notificationPayLoadReceiver":
                this.notificationPayLoadReceiver(args, callbackContext);
                break;

            default:
                break;

        }
        return false;
    }

    private void notificationPayLoadReceiver(JSONArray args, CallbackContext callbackContext) {
        NotificationCallbacks = callbackContext;
    }

    private void deleteNotification(JSONArray message, CallbackContext callbackContext) {

        if (message != null && message.length() > 0) {
            try {
                JSONObject jsonObject = message.getJSONObject(0);
                ReAndroidSDK.getInstance(cordova.getActivity()).deleteNotificationByObject(jsonObject);
                Log.e("Notification : ", "Delete sucessfully");
            } catch (Exception e) {
                Log.e("Delete Notification Exception: ", String.valueOf(e.getMessage()));
            }
        } else {
            Log.e("Delete Notification Exception : ", "Expected one non-empty string argument.");
        }
    }

    private void getNotification(JSONArray args, CallbackContext callbackContext) {

        try {
            notificationByObject = ReAndroidSDK.getInstance(cordova.getActivity()).getNotificationByObject();
            JSONArray jsonArray = new JSONArray(notificationByObject);
            callbackContext.success(jsonArray.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void locationUpdate(JSONArray message, CallbackContext callbackContext) {

        cordova.getThreadPool().execute(() -> {

            if (message != null && message.length() > 0) {
                try {
                    JSONObject jsonObject = message.getJSONObject(0);

                    double latitude = jsonObject.optDouble("latitude");
                    double longitude = jsonObject.optDouble("longitude");
                    if (latitude != 0 && longitude != 0)
                        ReAndroidSDK.getInstance(cordova.getActivity()).onLocationUpdate(latitude, longitude);

                } catch (Exception e) {
                    Log.e("User events Exception: ", String.valueOf(e.getMessage()));
                }
            } else {
                Log.e("User events Exception: ", "Expected one non-empty string argument.");
            }

        });

    }


    private void screenNavigation(JSONArray message, CallbackContext callbackContext) {

        cordova.getThreadPool().execute(() -> {
            if (message != null && message.length() > 0) {
                try {
                    JSONObject jsonObject = message.getJSONObject(0);
                    screenTracking(jsonObject.optString("screenName"));
                    OldScreenName = newScreenName;
                    newScreenName = jsonObject.optString("screenName");
                } catch (Exception e) {
                    Log.e("userNavigation Exception: ", String.valueOf(e.getMessage()));
                }

            } else {
                Log.e("userNavigation Exception: ", "Expected one non-empty string argument.");
            }
        });

    }

    private void userRegister(JSONArray message, CallbackContext callbackContext) {

        cordova.getThreadPool().execute(() -> {
            if (message != null && message.length() > 0) {
                try {
                    JSONObject jsonObject = message.getJSONObject(0);
                    MRegisterUser registerUser = new MRegisterUser();
                    registerUser.setUserUniqueId(jsonObject.optString("uniqueId"));
                    registerUser.setName(jsonObject.optString("name"));
                    registerUser.setEmail(jsonObject.optString("email"));
                    registerUser.setPhone(jsonObject.optString("phone"));
                    registerUser.setAge(jsonObject.optString("age"));
                    registerUser.setGender(jsonObject.optString("gender"));
                    registerUser.setDeviceToken(jsonObject.optString("token"));
                    registerUser.setProfileUrl(jsonObject.optString("profileUrl"));
                    ReAndroidSDK.getInstance(cordova.getActivity()).onDeviceUserRegister(registerUser);
                } catch (Exception e) {
                    Log.e("register Exception: ", String.valueOf(e.getMessage()));
                }

            } else {

                Log.e("register Exception: ", "Expected one non-empty string argument.");
            }
        });
    }

    private void customEvent(JSONArray message, CallbackContext callbackContext) {
        cordova.getThreadPool().execute(() -> {
            if (message != null && message.length() > 0) {
                try {
                    JSONObject jsonObject = message.getJSONObject(0);
                    String eventName = jsonObject.optString("eventName");
                    JSONObject eventData = jsonObject.optJSONObject("data");

                    if (TextUtils.isEmpty(eventName)) {
                        Log.e("Event name can't be empty!", "");
                        return;
                    }
                    if (TextUtils.isEmpty(eventData.toString()))
                        ReAndroidSDK.getInstance(cordova.getActivity()).onTrackEvent(eventName);
                    else
                        ReAndroidSDK.getInstance(cordova.getActivity()).onTrackEvent(eventData, eventName);

                } catch (Exception e) {
                    Log.e("User events Exception: ", String.valueOf(e.getMessage()));
                }
            } else {
                Log.e("User events Exception: ", "Expected one non-empty string argument.");
            }

        });
    }

    private void screenTracking(String screenName) {

        try {
            if (sCalendar == null)
                sCalendar = Calendar.getInstance();

            oldCalendar = sCalendar;
            sCalendar = Calendar.getInstance();

            if (OldScreenName != null) {
                AppLifecyclePresenter.getInstance().onSessionStop(cordova.getActivity(), oldCalendar, sCalendar, OldScreenName, null, null);
                AppLifecyclePresenter.getInstance().onSessionStartFragment(cordova.getActivity(), OldScreenName, null);
            }
            if (newScreenName == null)
                newScreenName = screenName;

        } catch (Exception e) {
            Log.e("screenTracking Exception: ", "" + e.getMessage());

        }
    }


    @Override
    public void onPause(boolean multitasking) {
        super.onPause(multitasking);
        screenTracking(newScreenName);
    }

    @Override
    public void onResume(boolean multitasking) {
        super.onResume(multitasking);
        oldCalendar = Calendar.getInstance();
        sCalendar = Calendar.getInstance();
    }
}
