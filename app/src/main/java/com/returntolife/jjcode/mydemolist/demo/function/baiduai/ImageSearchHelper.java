package com.returntolife.jjcode.mydemolist.demo.function.baiduai;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.baidu.aip.imageclassify.AipImageClassify;
import com.baidu.aip.util.Base64Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.returntolife.jjcode.mydemolist.Api;
import com.returntolife.jjcode.mydemolist.AppApplication;
import com.returntolife.jjcode.mydemolist.R;
import com.returntolife.jjcode.mydemolist.demo.function.baiduai.bean.AnimalBean;
import com.returntolife.jjcode.mydemolist.demo.function.baiduai.bean.CarResultBean;
import com.returntolife.jjcode.mydemolist.demo.function.baiduai.bean.ImageSearchBean;
import com.returntolife.jjcode.mydemolist.demo.function.baiduai.bean.IngredientBean;
import com.returntolife.jjcode.mydemolist.demo.function.baiduai.bean.TokenResultBean;
import com.tools.jj.tools.http.Http;
import com.tools.jj.tools.utils.LogUtil;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
/**
 * Created by HeJiaJun on 2019/8/1.
 * Email:455hejiajun@gmail
 * des:
 */
public class ImageSearchHelper {

    //设置APPID/AK/SK
    private static final String APP_ID = "16931161";
    private static final String API_KEY = "YqsdmBRyvxDfA05HDyN9zUnC";
    private static final String SECRET_KEY = "C2MurrL9NUdKyNcYLkyckOdUeWGZTtY8";
    private static final String GRANT_TYPE="client_credentials";

//    private static class SingleHolder {
//        private static final ImageSearchHelper INSTANCE = new ImageSearchHelper();
//    }

    public ImageSearchHelper() {
        client= new AipImageClassify(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
//        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");
        gson=new Gson();
    }

//    public static final ImageSearchHelper getInstance() {
//        return ImageSearchHelper.SingleHolder.INSTANCE;
//    }

    private AipImageClassify client;
    private Gson gson;


    public String imageSearch(int imageRes,int type){
        Bitmap bitmap=BitmapFactory.decodeResource(AppApplication.pAppContext.getResources(),imageRes);
        String result="";
        switch (type){
            case ImageSearchBean.SEARCH_TYPE_ANIMAL:
                result=searchAnimal(bitmap);
                break;
            case ImageSearchBean.SEARCH_TYPE_CAR:
                result=searchCar(bitmap);
                break;
            case ImageSearchBean.SEARCH_TYPE_INGREDIENT:
                result=searchIngredient(bitmap);
                break;
            default:
                break;
        }
        return result;
    }

    private byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    private String searchAnimal(Bitmap bitmap){
        JSONObject  res = client.animalDetect(Bitmap2Bytes(bitmap), new HashMap<String, String>());
        AnimalBean animalBean=gson.fromJson(res.toString(),AnimalBean.class);

        if(animalBean!=null&&animalBean.getResult()!=null&&animalBean.getResult().size()>0){
            StringBuilder stringBuilder=new StringBuilder();
            for (AnimalBean.ResultBean resultBean : animalBean.getResult()) {
                stringBuilder.append(resultBean.getName()).append("\n");
            }
            return stringBuilder.toString();
        }else {
            return "";
        }
    }

    private String searchCar(Bitmap bitmap){
        JSONObject  res = client.carDetect(Bitmap2Bytes(bitmap), new HashMap<String, String>());
        CarResultBean carResultBean=gson.fromJson(res.toString(),CarResultBean.class);

        if(carResultBean!=null&&carResultBean.getResult()!=null&&carResultBean.getResult().size()>0){
            StringBuilder stringBuilder=new StringBuilder();
            for (CarResultBean.ResultBean resultBean : carResultBean.getResult()) {
                stringBuilder.append(resultBean.getName()).append("\n");
            }
            return stringBuilder.toString();
        }else {
            return "";
        }
    }

    private String searchIngredient(Bitmap bitmap){
        JSONObject  res = client.ingredient(Bitmap2Bytes(bitmap), new HashMap<String, String>());
        IngredientBean ingredientBean=gson.fromJson(res.toString(),IngredientBean.class);

        if(ingredientBean!=null&&ingredientBean.getResult()!=null&&ingredientBean.getResult().size()>0){
            StringBuilder stringBuilder=new StringBuilder();
            for (IngredientBean.ResultBean resultBean : ingredientBean.getResult()) {
                stringBuilder.append(resultBean.getName()).append("\n");
            }
            return stringBuilder.toString();
        }else {
            return "";
        }
    }

}
