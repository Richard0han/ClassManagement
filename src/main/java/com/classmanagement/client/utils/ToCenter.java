package com.classmanagement.client.utils;

import java.awt.*;

/**
 * ClassManager
 *
 * @author Richard-J
 * @description 把窗体放在中央
 * @date 2019.03
 */

public class ToCenter {
    public static void toCenter(Component comp) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle rec = ge.getDefaultScreenDevice().getDefaultConfiguration().getBounds();
        comp.setLocation(((int) (rec.getWidth() - comp.getWidth()) / 2),
                ((int) (rec.getHeight() - comp.getHeight())) / 2);
    }
}
