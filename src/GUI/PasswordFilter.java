package GUI;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;


public class PasswordFilter extends DocumentFilter {

    // 허용되는 문자 집합 정의
    private static final String ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789\\p{Punct}\b";

    // 필터링 메서드
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException {
        StringBuilder sb = new StringBuilder();
        for (char c : string.toCharArray()) {
            if (ALLOWED_CHARACTERS.indexOf(c) != -1) {
                sb.append(c);
            }
        }
        super.insertString(fb, offset, sb.toString(), attr);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {
        if (text != null) {
            StringBuilder sb = new StringBuilder();
            for (char c : text.toCharArray()) {
                if (ALLOWED_CHARACTERS.indexOf(c) != -1) {
                    sb.append(c);
                }
            }
            text = sb.toString();
        }
        super.replace(fb, offset, length, text, attrs);
    }
}//class end
