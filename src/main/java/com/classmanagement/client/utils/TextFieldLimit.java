package com.classmanagement.client.utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * ClassManagement
 *
 * @author Richard-J
 * @description
 * @date 2019.05
 */

public class TextFieldLimit extends PlainDocument {

    private int limit;
    //限制的长度

    public TextFieldLimit(int limit) {
        super();
        //调用父类构造
        this.limit = limit;
    }

    @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null) {
            return;
        }

        //下面的判断条件改为自己想要限制的条件即可，这里为限制输入的长度
        if ((getLength() + str.length()) <= limit) {
            super.insertString(offset, str, attr);
            //调用父类方法
        }
    }

}