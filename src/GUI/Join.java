package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.PlainDocument;

import DAO.AccountInfoDAO;
import DAO.MembersDAO;
import DTO.MembersDTO;

public class Join {

	MembersDAO mDAO = MembersDAO.getMembersDAO();
	MembersDTO mDTO;
	AccountInfoDAO aDAO = AccountInfoDAO.getAccountInfoDAO();
	JFrame frame = new JFrame();
	
	public static void main(String[] args) {
		new Join();
	}
	
	public Join() {
		frame.setBounds(100, 10, 800, 800);
		frame.setTitle("회원가입");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		
		ImageIcon imageIcon = new ImageIcon("./image/123.png");
		JLabel imageLabel = new JLabel(imageIcon);
		imageLabel.setSize(800,800);
		frame.getContentPane().add(imageLabel);
		
		//회원가입창 제목 패널
		JPanel mainpanel = new JPanel();
		mainpanel.setBounds(145, 45, 500, 100);
		imageLabel.add(mainpanel);
		mainpanel.setBackground(new Color(0,0,0,0));
		mainpanel.setLayout(null);
		
		JLabel LogoLB = new JLabel("BURGER QUEEN");
		LogoLB.setBounds(190, 100, 380, 35);
		LogoLB.setHorizontalAlignment(SwingConstants.CENTER);
		LogoLB.setFont(new Font("Britannic Bold", Font.BOLD, 36));
		imageLabel.add(LogoLB);
		
		
		
		//회원정보 입력 패널
		JPanel joinpanel = new JPanel();
		joinpanel.setBounds(120, 175, 555, 500);
		joinpanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY,3),"회원가입"));
		joinpanel.setLayout(null);
		
		imageLabel.add(joinpanel);
		joinpanel.setBackground(new Color(255,0,0,0));
		
		String[] labelNames = {"이름", "아이디", "비밀번호", "휴대폰", "주소"};
		int labelY = 70; // 레이블의 y 위치 값

		for (int i = 0; i < labelNames.length; i++) {
		    JLabel label = new JLabel(labelNames[i]);
		    label.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		    label.setBounds(100, labelY, 67, 15);
		    joinpanel.add(label);

		    labelY += 65; // 다음 레이블의 y 위치값을 조정
		    if(i==3) {
		    	labelY = 270;
		    	label.setBounds(100,labelY,67,15);
		    }
		    if(i==4) {
		    	labelY = 340;
		    	label.setBounds(100,labelY,67,15);
		    }
		}
		
		
		//이름 텍스트필드
		JTextField nameTF = new JTextField();
		nameTF.setBounds(190, 70, 140, 20);
		joinpanel.add(nameTF);
		nameTF.setColumns(10);
		
		
		//아이디 텍스트필드
		JTextField idTF = new JTextField();
		idTF.setBounds(190, 135, 140, 20);
		joinpanel.add(idTF);
		idTF.setColumns(10);
		
		
		//비밀번호 텍스트필드
		JPasswordField pwPF = new JPasswordField();
		pwPF.setBounds(190, 200, 270, 20);
		pwPF.setDocument(new PlainDocument());
		((AbstractDocument) pwPF.getDocument()).setDocumentFilter(new PasswordFilter());
		
		joinpanel.add(pwPF);
		
		
		// 휴대폰 번호 텍스트 필드 배열 선언
        JTextField[] phoneTF = new JTextField[3];

        // 휴대폰 번호 입력 텍스트 필드 생성
        int[] phoneColumns = {3, 4, 4};
        int x = 190, y = 270, width = 65, height = 20;
        for (int i = 0; i < phoneTF.length; i++) {
            phoneTF[i] = new JTextField();
            phoneTF[i].setBounds(x, y, width, height);
            phoneTF[i].setDocument(new PlainDocument());
            ((AbstractDocument) phoneTF[i].getDocument()).setDocumentFilter(new NumberOnlyFilter(phoneColumns[i]));

            phoneTF[i].setHorizontalAlignment(JTextField.CENTER);

            joinpanel.add(phoneTF[i]);
            x += width + 10;
        }
		
		
		//주소 텍스트필드
		JTextField addressTF = new JTextField();
		addressTF.setBounds(190, 340, 270, 20);
		joinpanel.add(addressTF);
		addressTF.setColumns(10);
		
		
		//아이디 중복확인 버튼
		JButton duplicateBT = new JButton("중복확인");
		duplicateBT.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		duplicateBT.setBounds(360, 135, 100, 20);
		duplicateBT.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = idTF.getText();
				boolean temp = false;
				try {
					temp = mDAO.isDuplicate(id);
				} catch (SQLException e1) {
					System.out.println("SQL 예외 : "+e1.getMessage());
				}
			
			if(temp) JOptionPane.showMessageDialog(null, "⚠  이미 사용중인 계정입니다  ⚠","Warning"
					,JOptionPane.ERROR_MESSAGE);
			else {
				JOptionPane.showMessageDialog(null, "사용 가능한 아이디입니다!",""
						,JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		joinpanel.add(duplicateBT);
		
		
		
		//회원가입 버튼
		JButton joinBT = new JButton("회원가입");
		joinBT.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		joinBT.setBounds(190, 450, 210, 35);
		joinBT.setEnabled(false);
		
		
		joinBT.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				String name = nameTF.getText();
				String id = idTF.getText();
				String pw = new String(pwPF.getPassword());
				String phone = phoneTF[0].getText()+"-"+phoneTF[1].getText()+"-"+phoneTF[2].getText();
				String address = addressTF.getText();
				boolean temp1 = false;
				boolean temp2 = false;
				boolean temp3 = false;
				
				if(name.equals("")) JOptionPane.showMessageDialog(null, "⚠  '이름'은 필수 항목입니다  ⚠", "Warning", JOptionPane.ERROR_MESSAGE);
				else if(id.equals("")) JOptionPane.showMessageDialog(null, "⚠  'ID'는 필수 항목입니다  ⚠", "Warning", JOptionPane.ERROR_MESSAGE);
				else if(pw.equals("")) JOptionPane.showMessageDialog(null, "⚠  '비밀번호'는 필수 항목입니다  ⚠", "Warning", JOptionPane.ERROR_MESSAGE);
				else if(phone.length() != 13) JOptionPane.showMessageDialog(null, "⚠  '전화번호 11자리'는 필수 항목입니다  ⚠", "Warning", JOptionPane.ERROR_MESSAGE);
				else if(address.equals("")) JOptionPane.showMessageDialog(null, "⚠  '주소'는 필수 항목입니다  ⚠", "Warning", JOptionPane.ERROR_MESSAGE);
				else temp1 = true;
				
				if(temp1) {
				try {
					temp2 = mDAO.isDuplicate(id);
					temp3 = mDAO.isDuplicate(phone, null);
				} catch (Exception e2) {
					System.out.println("SQL 예외 : "+e2.getMessage());
				}
				
				if(temp2) JOptionPane.showMessageDialog(null, "⚠  이미 사용중인 계정입니다  ⚠","Warning"
						,JOptionPane.ERROR_MESSAGE);
				else if(temp3) JOptionPane.showMessageDialog(null, "⚠  이미 사용중인 전화번호입니다  ⚠","Warning"
						,JOptionPane.ERROR_MESSAGE);
				else {
					JOptionPane.showMessageDialog(null, "★"+name+"★ 님 환영합니다!","Welcome"
							,JOptionPane.INFORMATION_MESSAGE);
					mDTO = new MembersDTO(id,pw,name,phone,address,null);
					try {
						aDAO.join(mDTO);
					} catch (SQLException e3) {
						System.out.println("SQL 예외 : "+e3.getMessage());
					}
					temp1 = false;
					frame.dispose();
					setEnable();
				}
				}
			}
		});
		
		
		joinpanel.add(joinBT);			
		
		//개인정보 동의 체크박스
		JCheckBox agreeCB = new JCheckBox("   개인정보 수집 및 이용 동의");
		agreeCB.setFont(new Font("굴림", Font.BOLD, 13));
		agreeCB.setBounds(190, 410, 210, 20);
		agreeCB.setBackground(Color.WHITE);
		agreeCB.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // 체크박스 상태에 따라 회원가입 버튼 활성화 여부 결정
		        joinBT.setEnabled(agreeCB.isSelected());
		    }
		});
		joinpanel.add(agreeCB);
		
		
	frame.setVisible(true);
	}//생성자 end
	
	
	
	public void setEnable() {
		Login.findBT.setEnabled(true);
		Login.joinBT.setEnabled(true);
		Login.signInBT.setEnabled(true);
	}
	
    
}//class end