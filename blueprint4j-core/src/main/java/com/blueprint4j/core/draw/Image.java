package com.blueprint4j.core.draw;

public class Image {

    public static final String RECTANGLE_SHAPE = "rectangle";
    private String imageName;
    private String shape;
    private ImageType imageType;

    public Image (){
        applyDefaults();
    }
    public Image(ImageType imageType){
        this.imageType = imageType;
        if (ImageType.CUSTOM.equals(imageType)){
            throw new RuntimeException("If you want to use a custom image, please use the constructor that set's the imageName.");
        }

        applyDefaults();
    }

    public Image(String imageName){
        this.imageType =  ImageType.CUSTOM;
        this.imageName = imageName;
        this.shape="none";
    }

    public Image(Image image) {
        this.imageName = image.getImageName();
        this.shape = image.getShape();
        this.imageType = image.getImageType();
    }

    private void applyDefaults() {
        /* apply defaults */
        imageType = ImageType.RECTANGLE;
        shape = RECTANGLE_SHAPE;
        imageName=null;
    }


    public String getImageName() {
        return imageName;
    }

    public String getShape() {
        return shape;
    }

    public ImageType getImageType() {
        return imageType;
    }
}
