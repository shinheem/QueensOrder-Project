package GUI;

import javax.swing.text.*;

public class NumberOnlyFilter extends DocumentFilter {
    private int maxLength; // 최대 입력 길이

    public NumberOnlyFilter(int maxLength) {
        this.maxLength = maxLength;
    }

    // 숫자만 허용하도록 필터링하는 메서드
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException {
        StringBuilder sb = new StringBuilder();
        for (char c : string.toCharArray()) {
            if (Character.isDigit(c)) {
                sb.append(c);
            }
        }

        // 최대 입력 길이를 초과하지 않도록 검사
        if (fb.getDocument().getLength() + sb.length() <= maxLength) {
            super.insertString(fb, offset, sb.toString(), attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {
        if (text != null) {
            StringBuilder sb = new StringBuilder();
            for (char c : text.toCharArray()) {
                if (Character.isDigit(c)) {
                    sb.append(c);
                }
            }
            text = sb.toString();
        }

        // 최대 입력 길이를 초과하지 않도록 검사
        if (fb.getDocument().getLength() - length + text.length() <= maxLength) {
            super.replace(fb, offset, length, text, attrs);
        }
    }
}
