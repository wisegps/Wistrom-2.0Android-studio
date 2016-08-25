package com.wicare.wistormdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.android.volley.VolleyError;
import com.wicare.wistorm.api.WCommApi;
import com.wicare.wistorm.api.WUserApi;
import com.wicare.wistorm.http.BaseVolley;
import com.wicare.wistorm.http.OnFailure;
import com.wicare.wistorm.http.OnSuccess;
import com.wicare.wistormdemo.app.AppApplication;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/22.
 */
public class UserApiActivity extends AppCompatActivity {

    final String TAG = "USER_API";

    private WUserApi userApi;
    private WCommApi commApi;

    private AppApplication appApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        initWistorm();
        appApplication = (AppApplication)getApplication();
    }

    /**
     * 初始化Wistorm
     */
    private void initWistorm(){
        BaseVolley.init(this);
        userApi = new WUserApi(this);
        commApi = new WCommApi(this);
    }

    /**
     * @param context
     */
    public static void startAciton(Context context){
        Intent intent = new Intent(context,UserApiActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.btn_token, R.id.btn_register, R.id.btn_isexist, R.id.btn_login,
            R.id.btn_sms,R.id.btn_valid, R.id.btn_reset})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_token:
                getToken();
                break;
            case R.id.btn_sms:
                sendSms();
                break;
            case R.id.btn_register:
                register();
                break;
            case R.id.btn_isexist:
                accountIsExist();
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_valid:
                validCode();
                break;
            case R.id.btn_reset:
                passwordReset();
                break;
        }
    }

    /**
     * 获取令牌
     */
    private void getToken(){
        userApi.getToken("18607556697", "123456","2", new OnSuccess() {

            @Override
            protected void onSuccess(String response) {
                // TODO Auto-generated method stub
                Log.d(TAG, "获取token : " + response);
                try {
                    JSONObject object = new JSONObject(response);
                    if("0".equals(object.getString("status_code"))){
                        appApplication.token = object.getString("access_token");
                        appApplication.uid = object.getString("uid");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                返回信息：{"status_code":0,"user_type":10,"session_token":"6419ea45fd911dfd957ad6b36e36b751","access_token":"6acff450a0a68d56902a22df0170c78efefdf92366473fc56bb5144d6a300b9a3ece243bfdc488b6bc39ad81ec02b0dbf9ecab5d9fdd09977d6139b68ad56734","refresh_token":"54f735185c2fa5acd2bc0061894450b2381639d9de0285b7d5d9033954f2815baf8c51a4bdf18f3941b9a8408debeecb774bd0afbed4a05d336c77bafc0e1d48","expire_in":"2016-08-24T06:37:49.730Z","uid":763993890020790300}
            }
        } , new OnFailure() {

            @Override
            protected void onFailure(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e(TAG, error.toString());
            }
        });
    }
    /**
     * 发送短信
     */
    private void sendSms(){
        commApi.sendSMS("13537687553", 1, new OnSuccess() {
            @Override
            protected void onSuccess(String response) {
                Log.d(TAG, "发送验证码 : " + response);
                //返回信息： {"status_code":0}
            }
        }, new OnFailure() {
            @Override
            protected void onFailure(VolleyError error) {
                Log.e(TAG, error.toString());
            }
        });
    }
    /**
     * 判断账号是否存在
     */
    private void accountIsExist(){
        userApi.isExist("13537687553", new OnSuccess() {
            @Override
            protected void onSuccess(String response) {
                Log.d(TAG, "账号是否存在 : " + response);
//                账号是否存在 : {"exist":true}
            }
        }, new OnFailure() {
            @Override
            protected void onFailure(VolleyError error) {
                Log.d(TAG, error.toString());
            }
        });
    }
    /**
     * 校验验证码是否正确
     */
    private void validCode(){
        commApi.validCode("13537687553", "7874", new OnSuccess() {
            @Override
            protected void onSuccess(String response) {
                Log.d(TAG, "验证码是否正确 : " + response);
//                返回信息：{"valid":true}
            }
        }, new OnFailure() {
            @Override
            protected void onFailure(VolleyError error) {
                Log.d(TAG, error.toString());
            }
        });
    }
    /**
     * 注册的时候 先去获取短信验证码，（再去校验验证码是否正确）然后得到的验证码去登陆
     */
    private void register(){
        userApi.register("13537687553", "111111", "7874", new OnSuccess() {
            @Override
            protected void onSuccess(String response) {
                Log.d(TAG, "注册账号返回信息  : " + response);
//                注册账号返回信息  : {"status_code":0} 已经注册的话返回：{"status_code":8,"uid":767977603083669500}
            }
        }, new OnFailure() {
            @Override
            protected void onFailure(VolleyError error) {
                Log.d(TAG, error.toString());
            }
        });
    }
    /**
     * 登录
     */
    private void login(){
        userApi.login("13537687553", "123456", new OnSuccess() {
            @Override
            protected void onSuccess(String response) {
                Log.d(TAG, "登录信息 : " + response);
//                登录信息 : {"status_code":0,"session_token":"3658f50344cf97b4f0b641c53c8cf86e1e0af09bc921d1803f1c7d4c2c9bf0ad","access_token":"6acff450a0a68d56902a22df0170c78efefdf92366473fc56bb5144d6a300b9a16b1f3ae50aae99d4357dcc45dc27b6800758ae2fe964460666ef49e464235ea","refresh_token":"54f735185c2fa5acd2bc0061894450b2a4be28ac583cd59f4343aaebc2eb2fbf6660d735fcd0d44618e5d59963f320189e6f8388dff6526b79e3838e8e9b3bbc40450a028cbb60668929e72c6d57f735","expire_in":"2016-08-24T07:00:03.044Z","uid":767977603083669500}
            }
        }, new OnFailure() {
            @Override
            protected void onFailure(VolleyError error) {
                Log.d(TAG, error.toString());
            }
        });
    }
    /**
     * 重置密码时候 先去获取短信验证码，（再去校验验证码是否正确）然后得到的验证码去重置密码
     */
    private void passwordReset(){
        userApi.passwordReset("13537687553", "123456", "1", "9339", new OnSuccess() {
            @Override
            protected void onSuccess(String response) {
                Log.d(TAG, "重置密码 : " + response);
//                重置密码 : {"status_code":0}
            }
        }, new OnFailure() {
            @Override
            protected void onFailure(VolleyError error) {
                Log.d(TAG, error.toString());
            }
        });
    }
}