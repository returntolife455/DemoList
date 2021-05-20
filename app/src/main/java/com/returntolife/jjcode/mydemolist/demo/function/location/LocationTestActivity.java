package com.returntolife.jjcode.mydemolist.demo.function.location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.returntolife.jjcode.mydemolist.AppPermissionActivity;
import com.returntolife.jjcode.mydemolist.R;
import com.returntolife.jjcode.mydemolist.main.activity.MainActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tools.jj.tools.utils.LogUtil;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @Author : hejiajun
 * @Time : 2021/5/20
 * @Email : hejiajun@lizhi.fm
 * @Desc :
 */
public class LocationTestActivity extends Activity implements LocationListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_test);


        new RxPermissions(this).request(
                Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            location();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        finish();
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }

    private void location() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        List<String> list = locationManager.getProviders(true);
        if (list != null) {
            for (String x : list) {
                LogUtil.d("name:" + x);
            }
        }

        LocationProvider lpGps = locationManager.getProvider(LocationManager.GPS_PROVIDER);
        LocationProvider lpNet = locationManager.getProvider(LocationManager.NETWORK_PROVIDER);
        LocationProvider lpPsv = locationManager.getProvider(LocationManager.PASSIVE_PROVIDER);


        Criteria criteria = new Criteria();
        // Criteria是一组筛选条件
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //设置定位精准度
        criteria.setAltitudeRequired(false);
        //是否要求海拔
        criteria.setBearingRequired(true);
        //是否要求方向
        criteria.setCostAllowed(true);
        //是否要求收费
        criteria.setSpeedRequired(true);
        //是否要求速度
        criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
        //设置电池耗电要求
        criteria.setBearingAccuracy(Criteria.ACCURACY_HIGH);
        //设置方向精确度
        criteria.setSpeedAccuracy(Criteria.ACCURACY_HIGH);
        //设置速度精确度
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        //设置水平方向精确度
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);
        //设置垂直方向精确度

        //返回满足条件的当前设备可用的provider，第二个参数为false时返回当前设备所有provider中最符合条件的那个provider，但是不一定可用
        String mProvider = locationManager.getBestProvider(criteria, true);
        if (mProvider != null) {
            LogUtil.d("mProvider:" + mProvider);
        }

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 10, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
          LogUtil.d("onLocationChanged" + location.toString());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
          LogUtil.d("onStatusChanged" + status);
    }

    @Override
    public void onProviderEnabled(String provider) {
          LogUtil.d("onProviderEnabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
          LogUtil.d("onProviderDisabled");
    }
}
