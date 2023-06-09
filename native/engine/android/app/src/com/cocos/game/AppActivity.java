/****************************************************************************
Copyright (c) 2015-2016 Chukong Technologies Inc.
Copyright (c) 2017-2018 Xiamen Yaji Software Co., Ltd.

http://www.cocos2d-x.org

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
****************************************************************************/
package com.cocos.game;

import android.os.Bundle;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;

import com.cocos.lib.CocosHelper;
import com.cocos.lib.CocosJavascriptJavaBridge;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.cocos.service.SDKWrapper;
import com.cocos.lib.CocosActivity;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;

public class AppActivity extends CocosActivity {
    private static final int RC_SIGN_IN = 1000;
    CallbackManager callbackManager;
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    private static AppActivity appActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appActivity = this;
        // DO OTHER INITIALIZATION BELOW
        SDKWrapper.shared().init(this);

        //FOR FACEBOOK LOGIN
        callbackManager = CallbackManager.Factory.create();
        Log.d("AppActivity", "onCreate: Created");
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        changeScene();
                    }

                    @Override
                    public void onCancel() {
                        Log.d("AppActivity", "onSuccess: Login Cancelled");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        errorCaught();
                    }
                });

        //FOR GOOGLE LOGIN
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
    @Override
    protected void onResume() {
        super.onResume();
        SDKWrapper.shared().onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SDKWrapper.shared().onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Workaround in https://stackoverflow.com/questions/16283079/re-launch-of-activity-on-home-button-but-only-the-first-time/16447508
        if (!isTaskRoot()) {
            return;
        }
        SDKWrapper.shared().onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("AppActivity", "onActivityResult() returned: " + resultCode);
        if(requestCode == RC_SIGN_IN){
        Log.d("AppActivity", "onActivityResult: For Google Login Callback");
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        handleSignInResult(task);
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            Log.d("AppActivity", "handleSignInResult: Try block 1");
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.d("AppActivity", "handleSignInResult: Try block 2");
            // Signed in successfully, show authenticated UI
            afterGoogleLogin();
            Log.d("AppActivity", "handleSignInResult: Login Successful");
        } catch (ApiException e) {
            errorCaught();
            Log.w("AppActivity", "signInResult:failed code=" + e.getStatusCode());
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        SDKWrapper.shared().onNewIntent(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SDKWrapper.shared().onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SDKWrapper.shared().onStop();
    }

    @Override
    public void onBackPressed() {
        SDKWrapper.shared().onBackPressed();
        super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        SDKWrapper.shared().onConfigurationChanged(newConfig);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        SDKWrapper.shared().onRestoreInstanceState(savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        SDKWrapper.shared().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        SDKWrapper.shared().onStart();
        super.onStart();
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    }

    @Override
    public void onLowMemory() {
        SDKWrapper.shared().onLowMemory();
        super.onLowMemory();
    }
    
    public static void fbLoginCallFromCocos(){
        Log.d("AppActivity", "fbLoginCallFromCocos: Call Received for Facebook Login From Cocos");
        LoginManager.getInstance().logInWithReadPermissions(appActivity, Arrays.asList("public_profile"));
    }

    public static void googleLoginFromCocos(){
        Log.d("AppActivity", "googleLoginFromCocos: Call Received for Google Login From Cocos");
        appActivity.googleSignIn();
    }

    public static void twitterLoginFromCocos(){
        Log.d("AppActivity", "twitterLoginFromCocos: Call Received for Twitter Login From Cocos");

    }
    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    void afterGoogleLogin(){
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(appActivity);
        Log.d("AppActivity", "afterGoogleLogin: Call cocos function");
        changeScene();
    }
    void changeScene(){
        CocosHelper.runOnGameThread(new Runnable() {
            @Override
            public void run() {
                Log.d("AppActivity", "onSuccess: Call received and sending back to cocos");
                CocosJavascriptJavaBridge.evalString("cc.find('Canvas').getComponent('Main').changeScene()");
                Log.d("AppActivity", "onSuccess: Login Successful");
            }
        });
    }
    void errorCaught(){
        CocosHelper.runOnGameThread(new Runnable() {
            @Override
            public void run() {
                Log.d("AppActivity", "onSuccess: Call received and sending back to cocos");
                CocosJavascriptJavaBridge.evalString("cc.find('Canvas').getComponent('Main').errorWhileLogging()");
                Log.d("AppActivity", "onSuccess: Login Error! Login Not Done");
            }
        });
    }


}
