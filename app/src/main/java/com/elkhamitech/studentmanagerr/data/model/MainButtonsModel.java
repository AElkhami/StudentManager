package com.elkhamitech.studentmanagerr.data.model;

/**
 * Created by ElkhamiTech on 2/4/2018.
 */

public class MainButtonsModel {

    private int backgroundColor;
    private int imgIcon;
    private String buttonText;
    private int textColor;

    public MainButtonsModel() {

    }

    public int getbackgroundColor() {
        return backgroundColor;
    }

    public void setbackgroundColor(int background) {
        this.backgroundColor = background;
    }

    public int getImgIcon() {
        return imgIcon;
    }

    public void setImgIcon(int imgIcon) {
        this.imgIcon = imgIcon;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }
}
