package com.inkenka.miniimageloader.demo.MultiItemList;

/**
 *
 */
public class ListItemData {
    public static final int TYPE_TEXT = 0;
    public static final int TYPE_ONE_PIC = 1;
    public static final int TYPE_THREE_PIC = 2;

    public static final int TYPE_COUNT = 3;

    private String name;
    private int type;
    private String[] images;

    public ListItemData(int type, String name, String[] images) {
        this.type = type;
        this.name = name;
        this.images = images;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String[] getImages(){
        return images;
    }

}
