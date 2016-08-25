package com.wicare.wistormdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.android.volley.VolleyError;
import com.wicare.wistorm.api.WVehicleApi;
import com.wicare.wistorm.http.BaseVolley;
import com.wicare.wistorm.http.OnFailure;
import com.wicare.wistorm.http.OnSuccess;
import com.wicare.wistormdemo.app.AppApplication;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/24.
 */
public class VehicleActivity extends AppCompatActivity {

    final String TAG = "VEHICLE_API";

    private WVehicleApi vehicleApi;
    private AppApplication appApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);
        ButterKnife.bind(this);
        initWistorm();
        appApplication = (AppApplication)getApplication();
    }


    private void initWistorm(){
        BaseVolley.init(this);
        vehicleApi = new WVehicleApi(this);
    }
    /**
     * @param context
     */
    public static void startAction(Context context) {
        Intent intent = new Intent(context, VehicleActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.btn_vehicle_get, R.id.btn_vehicle_create, R.id.btn_vehicle_updata, R.id.btn_vehicle_list, R.id.btn_vehicle_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_vehicle_get:
                getVehicle();
                break;
            case R.id.btn_vehicle_create:
                vehicleCreate();
                break;
            case R.id.btn_vehicle_updata:
                updataVehicle();
                break;
            case R.id.btn_vehicle_list:
                getVehicleList();
                break;
            case R.id.btn_vehicle_delete:
                vehicleDelete();
                break;
        }
    }

    /**
     * 创建车辆
     */
    private void vehicleCreate(){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("access_token", appApplication.token);
        params.put("uid", appApplication.uid);
        params.put("name", "粤B:888888");//车牌号
//        params.put("did", "0");//
        params.put("brand", "1");//车品牌ID
        params.put("model", "11");//车系id
        params.put("type", "21");//车款型ID
        params.put("desc", "高方程式赛车阿斯拉达");//车辆描述
        vehicleApi.create(params, new OnSuccess() {
            @Override
            protected void onSuccess(String response) {
                Log.d(TAG,"创建车辆返回信息 ：" + response);
//                创建车辆返回信息 ：{"status_code":0,"objectId":768391204697149400}
            }
        }, new OnFailure() {
            @Override
            protected void onFailure(VolleyError error) {

            }
        });


    }

    /**
     * 获取车辆信息
     */
    private void getVehicle(){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("objectId", "768391204697149400");
        params.put("access_token", appApplication.token);
        String fields = "name,brand,desc";
        vehicleApi.get(params,fields, new OnSuccess() {

            @Override
            protected void onSuccess(String response) {
                Log.d(TAG,"获取车辆返回信息 ：" + response);
//                获取车辆信息 ：{"status_code":0,"data":{"brand":"1","desc":"高方程式赛车阿斯拉达","name":"粤B:888888","objectId":768391204697149400}}
            }
        }, new OnFailure() {

            @Override
            protected void onFailure(VolleyError error) {
            }
        });
    }

    /**
     * 更新车辆信息（更新车辆信息条件 以objectId加下划线 _objectId 表现修改这个车辆的信息 ）
     */
    private void updataVehicle(){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("_objectId", "768398028796203000");
        params.put("access_token", appApplication.token);
        params.put("desc", "高方程式赛车阿斯拉达,2016-08-25 11:38修改");//车辆描述
        vehicleApi.update(params, new OnSuccess() {
            @Override
            protected void onSuccess(String response) {
                Log.d(TAG,"更新车辆返回信息 ：" + response);
//                更新车辆返回信息 ：{"status_code":0,"n":1,"nModified":1}
            }
        }, new OnFailure() {
            @Override
            protected void onFailure(VolleyError error) {

            }
        });
    }

    /**
     * 获取车辆信息列表
     */
    private void getVehicleList(){
        HashMap<String,String> params = new HashMap<>();
        params.put("access_token", appApplication.token);
        params.put("uid",appApplication.uid);
        params.put("sort", "objectId");
//        params.put("page", "");
        params.put("min_id", "0");
        params.put("max_id", "0");
        params.put("limit", "-1");
        String fields = "name,brand,desc,objectId";
        vehicleApi.list(params, fields, new OnSuccess() {
            @Override
            protected void onSuccess(String response) {
                Log.d(TAG,"获取车辆列表返回信息 ：" + response);
            }
        }, new OnFailure() {
            @Override
            protected void onFailure(VolleyError error) {

            }
        });
    }

    /**
     * 删除 objectId 的车辆
     */
    private void vehicleDelete(){
        HashMap<String,String> params = new HashMap<>();
        params.put("access_token", appApplication.token);
        params.put("objectId","768398029811224600");
        vehicleApi.delete(params, new OnSuccess() {
            @Override
            protected void onSuccess(String response) {
                Log.d(TAG,"删除车辆返回信息 ：" + response);
//                删除车辆返回信息 ：{"status_code":0} status_code 为 0 表示删除车辆成功
            }
        }, new OnFailure() {
            @Override
            protected void onFailure(VolleyError error) {

            }
        });
    }

}
