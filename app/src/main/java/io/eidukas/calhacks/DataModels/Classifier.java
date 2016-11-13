package io.eidukas.calhacks.DataModels;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.Gson;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyImagesOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassification;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.eidukas.calhacks.R;


public class Classifier {
    private static final String TAG = "TAG";

    private String apiKey;
    private String classifierId;
    private Context context;


    public String getClassifierId() {
        return classifierId;
    }

    public void setClassifierId(String classifierId) {
        this.classifierId = classifierId;
    }

    public Classifier(String apiKey, String classifierId, Context context){
        setApiKey(apiKey);
        setClassifierId(classifierId);
        setContext(context);
    }

    public void setApiKey(String key){
        this.apiKey = key;
    }
    public String getApiKey(){
        return this.apiKey;
    }

    public Context getContext(){
        return context;
    }

    public void setContext(Context context){
        this.context = context;
    }

    private VisualClassification getClassification(String filename){
        VisualRecognition service = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20);
        service.setApiKey(apiKey);

        Log.d(TAG, "getClassification: " + filename);
        ClassifyImagesOptions options = new ClassifyImagesOptions.Builder()
                .images(new File(filename))
                .classifierIds(getClassifierId())
                .build();
        service.classify(options);
        return service.classify(options).execute();
    }

    private String jsonFromClassifier(VisualClassification classification){
        return classification.toString();
    }

    private String jsonFromBitmap(Bitmap bitmap){
        try {
            //create a file to write bitmap data
            File f = new File(context.getCacheDir(), context.getResources().getString(R.string.temp_file_name));
            f.createNewFile();


    //Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

    //write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();

            System.out.println(f.getAbsolutePath());
            return jsonFromClassifier(getClassification(f.getAbsolutePath()));

        } catch (IOException ioE){
            Log.d(TAG, "jsonFromBitmap: " + ioE.getMessage());
        }
        return "NA";

    }

    public String nameFromBitmap(Bitmap bitmap){
        Gson gson = new Gson();
        String json = jsonFromBitmap(bitmap);
        ClassifierResult result = gson.fromJson(json, ClassifierResult.class);
        ClassifierResult.Class[] classes = result.getImages()[0].getClassifiers()[0].getClasses();
        double min = 0;
        String minS = classes[0].getClassname();
        for (ClassifierResult.Class res : classes){
            if (res.getScore() > min){
                minS = res.getClassname();
                min = res.getScore();
            }
        }
        return minS;
    }
}
