package com.tech.tsang.wistrom.api;

import java.util.HashMap;
import android.content.Context;
import android.util.Log;

import com.tech.tsang.wistrom.WiStormApi;
import com.tech.tsang.wistrom.http.BaseVolley;
import com.tech.tsang.wistrom.http.OnFailure;
import com.tech.tsang.wistrom.http.OnSuccess;


/**
 * DeviceApi 2.0 版本
 * @author wu
 * @date 2016-08-12
 */
public class WDeviceApi extends WiStormApi {

	private String Method_Device_Get    = "wicare._iotDevice.get";//获取单个设备信息
	private String Method_Device_Create = "wicare._iotDevice.create";////创建设备
	private String Method_Device_Updata = "wicare._iotDevice.update";//更新设备数据;
	private String Method_Device_Delete = "wicare._iotDevice.delete";//删除设备
	private String Method_Device_List   = "wicare._iotDevice.list";//设备列表

	private String Method_Gpsdata_Create = "wicare._iotGpsData.create";//创建历史定位记录
	private String Method_Gpsdata_Get = "wicare._iotGpsData.list";//获取历史定位记录

	private BaseVolley volley;
	
	
	public WDeviceApi(Context context){
		super(context);
		init();
	}
	
	public void init(){
		if(volley == null){
			volley = new BaseVolley();
		}
	}

	/**
	 * 获取单个设备的信息
	 * @param params 参数字段
	 * @param fields 返回信息字段
	 * @param onSuccess 连接成功回调
	 * @param onFailure 连接失败回调
	 */
	public void get(HashMap<String, String> params, String fields, OnSuccess onSuccess, OnFailure onFailure){
		String url = super.getUrl(Method_Device_Get, fields, params);
		Log.i("TEST_WISTORM", url);
		volley.request(url, onSuccess,onFailure);
	}
	
	/**
	 * 创建设备（或注册设备）
	 * @param params
	 * @param fields
	 * @param onSuccess
	 * @param onFailure
	 */
	public void create(HashMap<String, String> params,String fields,OnSuccess onSuccess,OnFailure onFailure){
		String url = super.getUrl(Method_Device_Create, fields, params);
		Log.i("TEST_WISTORM", url);
		volley.request(url, onSuccess,onFailure);
	}
	
	/**
	 * 创建GPS 定位记录
	 * @param params
	 * @param fields
	 * @param onSuccess
	 * @param onFailure
	 */
	public void gpsCreate(HashMap<String, String> params,String fields,OnSuccess onSuccess,OnFailure onFailure){
		String url = super.getUrl(Method_Gpsdata_Create, fields, params);
		Log.i("TEST_WISTORM", url);
		volley.request(url, onSuccess,onFailure);
	}
	
	
	/**
	 * 获取GPS历史定位记录
	 *
	 * @param params
	 * @param fields
	 * @param onSuccess
	 * @param onFailure
	 */
	public void getGpsList(HashMap<String, String> params,String fields,OnSuccess onSuccess,OnFailure onFailure){
		String url = super.getUrl(Method_Gpsdata_Get, fields, params);
		Log.i("TEST_WISTORM", url);
		volley.request(url, onSuccess,onFailure);
	}


	/**
	 * 更新设备信息
	 *
	 * @param params
	 * @param fields
	 * @param onSuccess
	 * @param onFailure
	 */
	public void updata(HashMap<String, String> params,String fields,OnSuccess onSuccess,OnFailure onFailure){
		String url = super.getUrl(Method_Device_Updata, fields, params);
		Log.i("TEST_WISTORM", url);
		volley.request(url, onSuccess,onFailure);
	}


	/**
	 * 获取设备列表
	 * @param params
	 * @param fields
	 * @param onSuccess
	 * @param onFailure
	 */
	public void list(HashMap<String, String> params,String fields,OnSuccess onSuccess,OnFailure onFailure){
		String url = super.getUrl(Method_Device_List, fields, params);
		Log.i("TEST_WISTORM", url);
		volley.request(url, onSuccess,onFailure);
	}


	/**
	 * 删除设备
	 * @param params
	 * @param fields
	 * @param onSuccess
	 * @param onFailure
	 */
	public void delete(HashMap<String, String> params,String fields,OnSuccess onSuccess,OnFailure onFailure){
		String url = super.getUrl(Method_Device_Delete, fields, params);
		Log.i("TEST_WISTORM", url);
		volley.request(url, onSuccess,onFailure);
	}

}
