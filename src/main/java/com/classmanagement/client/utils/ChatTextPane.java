package com.classmanagement.client.utils;

import javax.swing.*;
import javax.swing.text.*;

/**
 * ClassManagement
 *
 * @author Richard-J
 * @description  该类是真正实现超长单词都能自动换行的 JTextPane 的子类对
 *  超长单词都能有效，但从 Java 7 开始读超长单词就不能自动
 *  换行，导致 JTextPane 的实际宽度变大，使得滚动条出现。
 *  下面的方法是对这个 bug 的较好修复。
 * @date 2019.04
 */

public class ChatTextPane extends JTextPane {
    private class WarpEditorKit extends StyledEditorKit {

        private ViewFactory defaultFactory = new WarpColumnFactory();

        @Override
        public ViewFactory getViewFactory() {
            return defaultFactory;
        }
    }

    private class WarpColumnFactory implements ViewFactory {

        @Override
        public View create(Element elem) {
            String kind = elem.getName();
            if (kind != null) {
                if (kind.equals(AbstractDocument.ContentElementName)) {
                    return new WarpLabelView(elem);
                } else if (kind.equals(AbstractDocument.ParagraphElementName)) {
                    return new ParagraphView(elem);
                } else if (kind.equals(AbstractDocument.SectionElementName)) {
                    return new BoxView(elem, View.Y_AXIS);
                } else if (kind.equals(StyleConstants.ComponentElementName)) {
                    return new ComponentView(elem);
                } else if (kind.equals(StyleConstants.IconElementName)) {
                    return new IconView(elem);
                }
            }

            // default to text display
            return new LabelView(elem);
        }
    }

    private class WarpLabelView extends LabelView {

        public WarpLabelView(Element elem) {
            super(elem);
        }

        @Override
        public float getMinimumSpan(int axis) {
            switch (axis) {
                case View.X_AXIS:
                    return 0;
                case View.Y_AXIS:
                    return super.getMinimumSpan(axis);
                default:
                    throw new IllegalArgumentException("Invalid axis: " + axis);
            }
        }
    }

    public ChatTextPane() {
        super();
        this.setEditorKit(new WarpEditorKit());
    }
}
