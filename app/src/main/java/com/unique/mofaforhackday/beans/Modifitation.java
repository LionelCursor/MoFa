package com.unique.mofaforhackday.beans;

/**
 * Created by ldx on 2014/9/13.
 */
public class Modifitation {

    public Modifitation(){    }

    public TYPE getType() {
        return type;
    }

    public ColorAdjuster getColorAdjusterBean() {
        return ColorAdjusterBean;
    }

    public int getBlurProgress() {
        return BlurProgress;
    }

    public enum TYPE {
        ADJUST,
        BLUR,
        WORD,
    }

    private ColorAdjuster ColorAdjusterBean;

    private int BlurProgress;

    private String word;

    private TYPE type;

    public void clear(){
        ColorAdjusterBean = null;
        BlurProgress = 0;
        word = null;
    }

    public void set(TYPE modifyType, int progress){
        this.type = modifyType;
        this.BlurProgress = progress;
    }

    public void set(TYPE modifyType, ColorAdjuster Bean){
        type = modifyType;
        this.ColorAdjusterBean = Bean;
    }

    //how to go back word
    public void set(TYPE motifyType){

    }

    @Override
    public String toString() {
        return ""+type+" ; "+getBlurProgress();
    }
}
