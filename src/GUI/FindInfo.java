package GUI;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.AbstractDocument;
import javax.swing.text.PlainDocument;

import DAO.AccountInfoDAO;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class FindInfo {
	
	AccountInfoDAO aDAO = AccountInfoDAO.getAccountInfoDAO();
	JFrame frame = new JFrame();
	
	public static void main(String[] args) {
		new FindInfo();
	}
	
	public FindInfo() {
		
		//Frame
		frame.setBounds(100, 100, 800, 800);
		frame.setTitle("아이디/비밀번호 찾기");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		
		ImageIcon imageIcon = new ImageIcon("./image/123.png");
		JLabel imageLabel = new JLabel(imageIcon);
		imageLabel.setSize(800,800);
		frame.getContentPane().add(imageLabel);

		JLabel buyLabel = new JLabel("BURGER QUEEN");
		buyLabel.setBounds(190, 95, 380, 35);
		buyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		buyLabel.setFont(new Font("Britannic Bold", Font.BOLD, 36));
		
		imageLabel.add(buyLabel);
		
		
		
		/////////////////////////////////////////////////////////////////
		
		
		//아이디/비밀번호 찾기 선택 탭 패널
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		tabbedPane.setBounds(140, 200, 525, 390);
		frame.getContentPane().add(tabbedPane);
		
		imageLabel.add(tabbedPane);
		
		//아이디 찾기 패널
		JPanel panel1 = new JPanel();
		tabbedPane.addTab("아이디 찾기", null, panel1, null);
		panel1.setLayout(null);
		
		panel1.setOpaque(true);
		panel1.setBackground(new Color(250,250,240));
		
		//비밀번호 찾기 패널
		JPanel panel2 = new JPanel();
		panel2.setLayout(null);
		tabbedPane.addTab("비밀번호 찾기", null, panel2, null);
		
		panel2.setOpaque(true);
		panel2.setBackground(new Color(250,250,240));
		
		String[][] labels = { {"이름", "휴대폰"}, {"이름", "휴대폰", "아이디"} };

		JPanel[] panels = {panel1, panel2};
		int[] y = {110, 80};
	
		for (int i = 0; i < labels.length; i++) {
		    int labelX = 100;
		    for (int j = 0; j < labels[i].length; j++) {
		        JLabel label = new JLabel(labels[i][j]);
		        label.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		        label.setBounds(labelX, y[i], 70, 20);
		        label.setHorizontalAlignment(SwingConstants.RIGHT);
		        panels[i].add(label);
		        
		        if(i == 0) y[i] += 60;
		        if(i == 1) y[i] += 55;
		    }
		}
		
		
		//아이디 찾기 이름 텍스트필드
		JTextField nameTF1 = new JTextField();
		nameTF1.setBounds(200, 110, 200, 25);
		panel1.add(nameTF1);
		nameTF1.setColumns(10);
		
		// 휴대폰 번호 텍스트 필드 배열 선언
        JTextField[] phoneTF1 = new JTextField[3];
		
		// 휴대폰 번호 입력 텍스트 필드 생성
        int[] phoneColumns1 = {3, 4, 4};
        int x1 = 200, y1 = 170, width1 = 60, height1 = 25;
        for (int i = 0; i < phoneTF1.length; i++) {
            phoneTF1[i] = new JTextField();
            phoneTF1[i].setBounds(x1, y1, width1, height1);
            phoneTF1[i].setDocument(new PlainDocument());
            ((AbstractDocument) phoneTF1[i].getDocument()).setDocumentFilter(new NumberOnlyFilter(phoneColumns1[i]));

            phoneTF1[i].setHorizontalAlignment(JTextField.CENTER);

            panel1.add(phoneTF1[i]);
            x1 += width1 + 10;
        }
		
		//아이디 찾기 버튼
		JButton findBT1 = new JButton("아이디 찾기");
		findBT1.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		findBT1.setBounds(200, 250, 130, 30);
		findBT1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameTF1.getText();
				String phone = phoneTF1[0].getText()+"-"+phoneTF1[1].getText()+"-"+phoneTF1[2].getText();
				
				if(phone.length() != 13) JOptionPane.showMessageDialog(null, "⚠  전화번호는 11자리를 입력해주세요  ⚠", "Warning", JOptionPane.ERROR_MESSAGE);
				
				try {
					if(aDAO.isExistID(name, phone)) {
						String id = aDAO.findID(name, phone);
						JOptionPane.showMessageDialog(null, "[ "+name+" ] 님의 ID "+"▶ "+id,"Welcome"
								,JOptionPane.INFORMATION_MESSAGE);
					}
						
					else JOptionPane.showMessageDialog(null, "⚠  입력하신 정보와 일치하는 계정이 없습니다  ⚠","Warning"
							,JOptionPane.ERROR_MESSAGE);
				} catch (SQLException e1) {
					System.out.println("SQL 예외 : "+e1.getMessage());
				}
			}
		});
		
		panel1.add(findBT1);
		
////////////////////////////////////////////////
		
		//비밀번호 찾기 이름 텍스트필드
		JTextField nameTF2 = new JTextField();
		nameTF2.setColumns(10);
		nameTF2.setBounds(200, 80, 200, 25);
		panel2.add(nameTF2);
		
		// 휴대폰 번호 텍스트 필드 배열 선언
        JTextField[] phoneTF2 = new JTextField[3];
		
		// 휴대폰 번호 입력 텍스트 필드 생성
        int[] phoneColumns2 = {3, 4, 4};
        int x2 = 200, y2 = 135, width2 = 60, height2 = 25;
        for (int i = 0; i < phoneTF2.length; i++) {
            phoneTF2[i] = new JTextField();
            phoneTF2[i].setBounds(x2, y2, width2, height2);
            phoneTF2[i].setDocument(new PlainDocument());
            ((AbstractDocument) phoneTF2[i].getDocument()).setDocumentFilter(new NumberOnlyFilter(phoneColumns2[i]));

            phoneTF2[i].setHorizontalAlignment(JTextField.CENTER);

            panel2.add(phoneTF2[i]);
            x2 += width2 + 10;
        }	
		
		
		//비밀번호 찾기 아이디 텍스트필드
		JTextField idTF2 = new JTextField();
		idTF2.setColumns(10);
		idTF2.setBounds(200, 190, 200, 25);
		panel2.add(idTF2);
		
		
		//비밀번호 찾기 버튼
		JButton findBT2 = new JButton("비밀번호 찾기");
		findBT2.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		findBT2.setBounds(200, 250, 130, 30);
		
		findBT2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameTF2.getText();
				String phone = phoneTF2[0].getText()+"-"+phoneTF2[1].getText()+"-"+phoneTF2[2].getText();
				String id = idTF2.getText();
				
				if(phone.length() != 13) JOptionPane.showMessageDialog(null, "⚠  전화번호는 11자리를 입력해주세요  ⚠", "Warning", JOptionPane.ERROR_MESSAGE);
				
				try {
					if(aDAO.isExistPW(name, phone, id)) {
						String pw = aDAO.findPW(name, phone, id);
						JOptionPane.showMessageDialog(null, "[ "+name+" ] 님의 비밀번호 "+"▶ "+pw,"Welcome"
								,JOptionPane.INFORMATION_MESSAGE);
					}
						
					else JOptionPane.showMessageDialog(null, "⚠  입력하신 정보와 일치하는 계정이 없습니다  ⚠","Warning"
							,JOptionPane.ERROR_MESSAGE);
				} catch (SQLException e2) {
					System.out.println("SQL 예외 : "+e2.getMessage());
				}
			}
		});
		
		panel2.add(findBT2);		
			
		frame.setVisible(true);
	}//생성자 end

	
}//class end
