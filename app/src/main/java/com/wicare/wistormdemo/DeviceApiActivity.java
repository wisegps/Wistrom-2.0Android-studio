package com.wicare.wistormdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;

import com.android.volley.VolleyError;
import com.wicare.wistorm.WiStormApi;
import com.wicare.wistorm.api.WDeviceApi;
import com.wicare.wistorm.http.BaseVolley;
import com.wicare.wistorm.http.OnFailure;
import com.wicare.wistorm.http.OnSuccess;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/8/22.
 */
public class DeviceApiActivity extends AppCompatActivity {

    final String TAG = "DEVICE_API";
    private WDeviceApi deviceApi;

    private String token = "6acff450a0a68d56902a22df0170c78efefdf92366473fc56bb5144d6a300b9a3ece243bfdc488b6bc39ad81ec02b0dbf9ecab5d9fdd09977d6139b68ad56734";
    private String did  = "1234567890";//867878020872884
    private String uid  = "763993890020790300";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        ButterKnife.bind(this);
        initWistorm();
    }


    /**
     * 初始化Wistorm
     */
    private void initWistorm(){
        BaseVolley.init(this);
        deviceApi = new WDeviceApi(this);
    }

    /**
     * @param context
     */
    public static void startAciton(Context context){
        Intent intent = new Intent(context,DeviceApiActivity.class);
        context.startActivity(intent);
    }


    @OnClick({R.id.btn_device_get, R.id.btn_device_create, R.id.btn_device_updata, R.id.btn_device_list, R.id.btn_device_delete, R.id.btn_gps_create, R.id.btn_get_gps})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_device_get:
                getDevice();
                break;
            case R.id.btn_device_create:
                deviceCreate();
                break;
            case R.id.btn_device_updata:
                Toast.makeText(DeviceApiActivity.this,"ddddd",Toast.LENGTH_LONG).show();
                deviceUpdata();
                break;
            case R.id.btn_device_list:
                getDeviceList();
                break;
            case R.id.btn_device_delete:
                deleteDevice();
                break;
            case R.id.btn_gps_create:
                gpsDataCreate();
                break;
            case R.id.btn_get_gps:
                getGpsDataList();
                break;
        }
    }

    /**
     * 获取设备信息 （可以通过设备id 或者通过设备的序列号获取）
     */
    private void  getDevice(){
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", token);
        params.put("did", did);//设备id
        String fields = "did,activeGpsData";//返回字段
        deviceApi.get(params, fields, new OnSuccess() {
            @Override
            protected void onSuccess(String response) {
                Log.d(TAG,"设备信息 ：" + response);
                /** 设备信息 ：{"status_code":0,"data":null}   data为null的时候表示这个设备没有创建*/
            }
        }, new OnFailure() {
            @Override
            protected void onFailure(VolleyError error) {
                Log.d(TAG,error.toString());
            }
        });
    }
    /**
     * 创建设备（只创建一次）
     */
    private void deviceCreate(){
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", token);
        params.put("did", did);
        params.put("uid", uid);
        deviceApi.create(params, "", new OnSuccess() {

            @Override
            protected void onSuccess(String response) {
                // TODO Auto-generated method stub
                Log.d(TAG, "创建设备 ：" + response);
                /** 创建设备 ：{"status_code":0,"objectId":768021292686381000}*/
            }
        }, new OnFailure() {

            @Override
            protected void onFailure(VolleyError error) {
                // TODO Auto-generated method stub

            }
        });
    }
    /**
     * 更新设备表信息 （在设备id did前面加上下划线 _did 根据此条件修改对应设备的信息）
     */
    private void deviceUpdata(){
        Log.i(TAG, "更新设备返回信息  : ");
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", token);
        params.put("uid", uid);
        params.put("_did", did);
        params.put("activeGpsData", getActiveGpsData().toString());
        String fields = "";
        deviceApi.updata(params, fields, new OnSuccess() {

            @Override
            protected void onSuccess(String response) {
                // TODO Auto-generated method stub
                Log.d(TAG, "更新设备返回信息  : " + response);
//                更新设备返回信息  : {"status_code":0,"n":1,"nModified":1}
            }
        }, new OnFailure() {

            @Override
            protected void onFailure(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e(TAG, "更新设备返回信息  : " + error.toString());
            }
        });
    }
    private Object getActiveGpsData(){
        JSONObject jObject=new JSONObject();
        try {
            jObject.put("lon", "58");
            jObject.put("lat", "58");
            jObject.put("gpsTime", WiStormApi.getCurrentTime());
            jObject.put("did", did);
            jObject.put("gpsFlag", "0");
            jObject.put("speed", "0");
            jObject.put("direct", "0");
            jObject.put("signal", "0");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jObject;
    }
    /**
     * 获取设备列表
     */
    private void getDeviceList(){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("access_token", token);
        params.put("uid", uid);
        params.put("sorts", "did");
//		params.put("page", "");
        params.put("min_id", "0");
        params.put("max_id", "0");
        params.put("limit", "-1");
        String fields = "did,activeGpsData";//返回字段
        deviceApi.list(params, fields, new OnSuccess() {
            @Override
            protected void onSuccess(String response) {
                Log.e(TAG, "设备列表返回信息  : " + response);
//                设备列表返回信息  : {"status_code":0,"total":8,"data":[{"did":"111111000000111111","activeGpsData":{"lat":"58","gpsFlag":"0","signal":"0","did":"111111000000111111","speed":"0","gpsTime":"2016-08-16 10:23:32","lon":"58","direct":"0"}},{"did":"111111777777","activeGpsData":{"signal":"-1","direct":"0","speed":"0.0","gpsFlag":"1","did":"111111777777","gpsTime":"2016-08-17 15:38:34","lat":"22.581469","lon":"113.917938"}},{"did":"1234567890","activeGpsData":{"lat":"58","gpsFlag":"0","signal":"0","did":"1234567890","speed":"0","gpsTime":"2016-08-24 14:07:19","lon":"58","direct":"0"}},{"did":"3a1164faf8ec22a"},{"did":"459432807298410","activeGpsData":{"lat":"22.583276","gpsFlag":"1","signal":"14","did":"459432807298410","speed":"0.0","gpsTime":"2016-08-23 18:22:35","lon":"113.918393","direct":"0"}},{"did":"56621858142","activeGpsData":{"did":"56621858142","gpsTime":"2016-07-11T17:58:26.000Z","battery":0,"air":0,"temp":0,"fuel":0,"signal":0,"mileage":0,"alerts":[],"status":[8196],"direct":340,"speed":0,"lat":26.60252,"lon":106.72764,"gpsFlag":2}},{"did":"867878020872884","activeGpsData":{"signal":"-1","direct":"0","speed":"0.0","gpsFlag":"1","did":"867878020872884","gpsTime":"2016-08-17 16:24:05","lat":"22.581469","lon":"113.917938"}},{"did":"868331011992179"}]}
            }
        }, new OnFailure() {
            @Override
            protected void onFailure(VolleyError error) {
                Log.e(TAG, error.toString());
            }
        });
    }
    /**
     * 删除设备 （删除对应的 did）
     */
    private void deleteDevice(){
        HashMap<String,String> params = new HashMap<>();
        params.put("access_token",token);
        params.put("did", "111111000000111111");

        deviceApi.delete(params, "", new OnSuccess() {
            @Override
            protected void onSuccess(String response) {
                Log.d(TAG, "删除设备返回信息  : " + response);
//                删除设备返回信息  : {"status_code":0}
            }
        }, new OnFailure() {
            @Override
            protected void onFailure(VolleyError error) {
                Log.e(TAG, error.toString());
            }
        });
    }

    private void gpsDataCreate(){
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", token);
        params.put("did", did);
        params.put("lat", "22.5");//纬度
        params.put("lon", "113.3");//经度
        params.put("createdAt", WiStormApi.getCurrentTime());//当前时间
        params.put("gps_time", WiStormApi.getCurrentTime());//定位时间
        deviceApi.gpsCreate(params, "", new OnSuccess() {

            @Override
            protected void onSuccess(String response) {
                // TODO Auto-generated method stub
                Log.d(TAG,"创建定位记录返回信息 ：" + response);
//				{"status_code":0,"objectId":764020970095775700}
            }
        }, new OnFailure() {

            @Override
            protected void onFailure(VolleyError error) {
                // TODO Auto-generated method stub
                Log.d(TAG,error.toString());
            }
        });
    }

    /**
     * 获取GPS历史定位记录
     */
    private void getGpsDataList(){
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", token);
        params.put("did", did);
        params.put("createdAt", "2016-06-30 23:59:59@2016-08-30 01:59:59");
        params.put("page", "");
        params.put("min_id", "0");
        params.put("max_id", "0");
        params.put("limit", "-1");
        String fields = "createdAt,lon,lat";
        deviceApi.getGpsList(params, fields, new OnSuccess() {

            @Override
            protected void onSuccess(String response) {
                // TODO Auto-generated method stub
                Log.d(TAG, response);
//				{"status_code":0,"id":1}
            }
        }, new OnFailure() {

            @Override
            protected void onFailure(VolleyError error) {
                // TODO Auto-generated method stub

            }
        });
    }
}
