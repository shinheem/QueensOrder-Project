package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

//결제 완료 창 (홈으로, 종료)
public class AfterPayment {
	
	public static void main(String[] args) {
		new AfterPayment();
	}
	
	private int remainingSec;
	private JLabel endSec;
	 
	public AfterPayment() {
		
		JFrame jf = new JFrame();
		jf.setSize(800,800);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.getContentPane().setLayout(null);
		jf.setTitle("Queen's Order");
		jf.setResizable(false);
		
		ImageIcon imageIcon = new ImageIcon("./image/end.jpg");
	    JLabel imageLabel = new JLabel(imageIcon);
	    imageLabel.setSize(800,800);
	    jf.getContentPane().add(imageLabel);
		
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//		
//● 메인 패널 생성
		JPanel jp = new JPanel();
		jp.setSize(600,600);
		jp.setLocation(100,200);
		jp.setLayout(null);
		jp.setBackground(Color.WHITE);
		jf.getContentPane().add(jp);
		
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//		
//● 메인 패널 문구 생성
		
	//주문 완료 안내
		JLabel end = new JLabel("이용해주셔서 감사합니다.");
		end.setFont(new Font("한컴 고딕", Font.BOLD, 35));
		end.setLocation(0,0);
		end.setHorizontalAlignment(SwingConstants.CENTER);
		end.setSize(780,80);
		imageLabel.add(end);
		
		endSec = new JLabel();
        endSec.setFont(new Font("한컴 고딕", Font.BOLD, 20));
        endSec.setHorizontalAlignment(SwingConstants.CENTER);
        endSec.setBounds(90, 610, 600, 100);
        imageLabel.add(endSec);

		remainingSec = 5;
	    startTimer();
		

		jf.setVisible(true);
		
	}//생성자 end
	
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//	
	
	private void startTimer() {
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (remainingSec > 0) {
                    endSec.setText(remainingSec + "초 뒤 자동으로 종료됩니다...");
                    remainingSec--;
                } else {
                    ((Timer) e.getSource()).stop();
                    System.exit(0);
                }
            }
        });
        timer.start();
    }

	
}//class end
