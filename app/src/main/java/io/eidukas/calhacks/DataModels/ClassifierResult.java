package io.eidukas.calhacks.DataModels;

import android.util.Log;

import com.google.gson.annotations.SerializedName;


public class ClassifierResult {

    private Classifiers[] images;
    public Classifiers[] getImages(){
        return images;
    }

    public String getName(){
        Log.d("TAG", "getName: "+images[0].getClassifiers()[0].getClasses()[0].toString());
        return images[0].getClassifiers()[0].getClasses()[0].toString();

    }

    public class Class {
        @SerializedName("class")
        private String classname;
        private double score;
        public String getClassname(){
            return classname;
        }
        public double getScore(){
            return score;
        }
    }
    public class  ClassGroup{
        private Class[] classes;
        public Class[] getClasses(){
            return classes;
        }
    }

    public class Classifiers{
        @SerializedName("classifiers")
        private ClassGroup[] classifiers;
        public ClassGroup[] getClassifiers(){
            return classifiers;
        }
    }
}
