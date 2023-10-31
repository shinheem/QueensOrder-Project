package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import DAO.CartDAO;
import DAO.MembersDAO;

public class Login {
	
	public static String CustomerID;	//안될경우 static
	
	JFrame frame = new JFrame();
	
	static ImageIcon icon = new ImageIcon("./image/7777.PNG");
	static ImageIcon icon2 = new ImageIcon("./image/999.PNG");
	static ImageIcon icon3 = new ImageIcon("./image/444.PNG");
	
	static JButton signInBT = new JButton(icon);
	static JButton joinBT = new JButton(icon2);
	static JButton findBT = new JButton(icon3);
	
	CartDAO cDAO = CartDAO.getCartDAO();
	
	public Login() {
	StringBuffer ID = new StringBuffer();	//ID 저장
	MembersDAO mDAO = MembersDAO.getMembersDAO();
	
/*================================= 버거퀸 로그인창 =================================*/
//	JFrame frame = new JFrame();
	frame.setTitle("버거퀸 로그인");
	frame.setBounds(100, 100, 800, 800);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.getContentPane().setLayout(null);
	frame.setLocationRelativeTo(null);
	frame.setResizable(false);
	frame.addWindowListener(new WindowAdapter() {
		@Override
        public void windowClosing(WindowEvent e) {
            try {
                cDAO.truncate();
            } catch (SQLException ex) {
                System.out.println("SQL 예외: " + ex.getMessage());
            }
        }
	});
	
	ImageIcon imageIcon = new ImageIcon("./image/123.png");
	JLabel imageLabel = new JLabel(imageIcon);
	imageLabel.setSize(800,800);
	frame.getContentPane().add(imageLabel);
	
/*================================= 로그인창 맨 위에 버거퀸 로고 =================================*/
	JLabel logoLB = new JLabel("BURGER QUEEN");
	logoLB.setBounds(190, 120, 380, 50);
	logoLB.setHorizontalAlignment(SwingConstants.CENTER);
	logoLB.setFont(new Font("Britannic Bold", Font.BOLD, 45));
	imageLabel.add(logoLB);
	
/*================================= 로그인 패널 =================================*/
	JPanel panel = new JPanel();
	panel.setBorder(new TitledBorder(new LineBorder(Color.gray,3),"회원 로그인"));
	panel.setLayout(null);
	panel.setBounds(85, 300, 600, 400);
	panel.setBackground(new Color(255,0,0,0));
	imageLabel.add(panel);
	
/*================================= ID : 레이블 =================================*/
	JLabel idLB = new JLabel("ID : ");
	idLB.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 20));
	idLB.setBounds(65, 80, 45, 65);
	panel.add(idLB);
	
/*================================= PW : 레이블 =================================*/
	JLabel pwLB = new JLabel("PW : ");
	pwLB.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 20));
	pwLB.setBounds(65, 190, 60, 45);
	panel.add(pwLB);
	
/*================================= 아이디 텍스트 필드 =================================*/
	JTextField idTF = new JTextField();
	idTF.setFont(new Font("굴림", Font.BOLD, 20));
	idTF.setColumns(10);
	idTF.setBounds(140, 80, 385, 65);
	panel.add(idTF);
	
/*================================= 비밀번호 텍스트 필드 =================================*/
	JPasswordField pwPF = new JPasswordField();
	pwPF.setFont(new Font("굴림", Font.BOLD, 20));
	pwPF.setEchoChar('*');
	pwPF.setBounds(140, 185, 385, 65);
	panel.add(pwPF);
	
/*================================= 버튼 이벤트 =================================*/	
	
	//ID,PW 찾기 버튼
	findBT.setBounds(430, 355, 160, 35);
	findBT.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			setDisable();
				
			FindInfo findInfo = new FindInfo();
			findInfo.frame.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					setEnable();
				}
			});
		}
	});
	panel.add(findBT);
	
	//로그인 버튼 이벤트
	signInBT.setBounds(220, 280, 150, 45);																
	signInBT.addActionListener(e ->{
		ID.setLength(0);
		ID.append(idTF.getText());
		String id = ID.toString();
		String pw = new String(pwPF.getPassword());
		
		boolean login = false;
		boolean manager = false;
		
		try {
		//로그인 가능 여부 및 관리자 여부 확인
		if(mDAO.canLogin(id, pw)) login = true;
		if(mDAO.isManager(id, pw)) manager = true;

		//login + manager = true 일 경우 관리자 화면 실행
		if(login && manager) {
			setDisable();
			
			Admin admin = new Admin();
			admin.frame.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					setEnable();
				}
			});
		} else if(login && manager == false) {
			String name = mDAO.getName(id);
			JOptionPane.showMessageDialog(null, "★"+name+"★ 님 환영합니다!","Welcome"
					,JOptionPane.INFORMATION_MESSAGE);
			CustomerID = id;
			switchMenu(frame);
		} else {
			JOptionPane.showMessageDialog(null, "⚠  없는 계정이거나 비밀번호가 틀립니다  ⚠","Warning"
					,JOptionPane.ERROR_MESSAGE);
		}
		} catch (SQLException e1) {
			System.out.println("SQL 예외 : "+e1.getMessage());
		}
	});
	panel.add(signInBT);
	
	//회원가입 버튼 이벤트
	joinBT.setBounds(10, 355, 95, 35);
	joinBT.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			setDisable();
			
			Join join = new Join();
			join.frame.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					setEnable();
				}
			});
		}
	});
	
	panel.add(joinBT);
	
	frame.setVisible(true);
	}//생성자 end
	
	
	 public static JButton getFindBT() {
	     return findBT;
	 }

	 public static JButton getJoinBT() {
	     return joinBT;
	 }

	 public static JButton getSignInBT() {
	     return signInBT;
	 }
	
	
	private void switchMenu(JFrame frame) {
		frame.dispose();
		new Menu();
	}
	
	
	public static void main(String[] args) {
		new Login();
	}
	
	
	public void setDisable() {
		joinBT.setEnabled(false);
		findBT.setEnabled(false);
		signInBT.setEnabled(false);
	}
	
	
	public void setEnable() {
		joinBT.setEnabled(true);
		findBT.setEnabled(true);
		signInBT.setEnabled(true);
	}
	
}//class end