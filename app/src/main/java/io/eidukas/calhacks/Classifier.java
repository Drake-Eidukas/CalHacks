package io.eidukas.calhacks;

import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyImagesOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassification;

import java.io.File;


public class Classifier {
    private String apiKey;
    private String classifierId;


    public String getClassifierId() {
        return classifierId;
    }

    public void setClassifierId(String classifierId) {
        this.classifierId = classifierId;
    }

    public Classifier(String apikey, String classifierId){
        setApiKey(apiKey);
    }

    public void setApiKey(String key){
        this.apiKey = key;
    }
    public String getApiKey(){
        return this.apiKey;
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

    public String itemNameFromClassifier(VisualClassification classification){
        return classification.toString();
    }
}
