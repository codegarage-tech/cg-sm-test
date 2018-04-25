package yalantis.com.sidemenu.enumeration;

import yalantis.com.sidemenu.R;

public enum MenuType {

    HOME("Home", R.drawable.icn_1), BACKUP("Backup", R.drawable.icn_2), RATE("Rate", R.drawable.icn_3), ABOUT("About", R.drawable.icn_5), SETTINGS("Settings", R.drawable.icn_4);

    private final String mValue;
    private final int mImageResourceId;

    MenuType(String value, int imageResourceId) {
        mValue = value;
        mImageResourceId = imageResourceId;
    }

    public String getValue() {
        return mValue;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public static MenuType fromValue(String value) {
        for (MenuType menuType : MenuType.values()) {
            if (menuType.getValue() == value) {
                return menuType;
            }
        }
        return null;
    }
}
