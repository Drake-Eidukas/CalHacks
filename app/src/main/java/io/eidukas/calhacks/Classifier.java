package io.eidukas.calhacks;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyImagesOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassification;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


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

    public Classifier(String apikey, String classifierId, Context context){
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

    public VisualClassification getClassification(String filename){
        VisualRecognition service = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20);
        service.setApiKey(apiKey);

        ClassifyImagesOptions options = new ClassifyImagesOptions.Builder()
                .images(new File(filename))
                .classifierIds(getClassifierId())
                .build();
        return service.classify(options).execute();
    }

    public String jsonFromClassifier(VisualClassification classification){
        return classification.toString();
    }

    public String jsonFromBitmap(Bitmap bitmap){
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

            return jsonFromClassifier(getClassification(f.getAbsolutePath()));

        } catch (IOException ioE){
            Log.d(TAG, "jsonFromBitmap: " + ioE.getMessage());
        }
        return "NA";

    }
}
