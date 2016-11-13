package io.eidukas.calhacks.DataModels;

import com.google.gson.annotations.SerializedName;


public class ClassifierResult {

    private Images images;
    public Images getImages(){
        return images;
    }

    public String getName(){
        return images.getImages()[0].getClasses()[0].getClassname();
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
    public class Images{
        private ClassGroup[] images;
        @SerializedName("custom_classes")
        private int customClasses;
        public ClassGroup[] getImages(){
            return images;
        }
        public int getCustomClasses(){
            return customClasses;
        }
    }
}
