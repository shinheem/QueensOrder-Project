package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import DAO.BeverageDAO;
import DAO.CartDAO;
import DAO.SideMenuDAO;
import DTO.CartDTO;

public class Cart {


	public static void main(String[] args) {
		new Cart();
	}
	
	CartDAO cDAO = CartDAO.getCartDAO();
	SideMenuDAO sDAO = SideMenuDAO.getSideMenuDAO();
	BeverageDAO bDAO = BeverageDAO.getBeverageDAO();
	
	Menu menu;
	
	DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
	
	public JFrame frame;
	
	@SuppressWarnings("static-access")
	public Cart() {
		
		frame = new JFrame();
		frame.getContentPane().setBounds(new Rectangle(0, 0, 800, 1000));
		frame.setSize(800, 1000);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(0, 0, 785, 700);
		frame.getContentPane().add(scrollPane);

		JPanel scrollContent = new JPanel();
		scrollContent.setLayout(new BoxLayout(scrollContent, BoxLayout.Y_AXIS)); // Y축 방향으로 아이템들이 쌓이도록 설정
		scrollPane.setViewportView(scrollContent);
		
		
		
		
		
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//

		
		
		List<CartDTO> list = new ArrayList<>();
		try {
			list = cDAO.selectAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(list.size()==0) {
			JPanel emptyPanel = new JPanel();
			emptyPanel.setBounds(0,0,785,700);
			emptyPanel.setBackground(Color.WHITE);
			emptyPanel.setLayout(null);
			
			scrollContent.add(emptyPanel);
			
			
			JLabel empty = new JLabel("장바구니가 비어있습니다.");
			empty.setFont(new Font("맑은 고딕",Font.BOLD,50));
			empty.setHorizontalAlignment(SwingConstants.CENTER);
			empty.setBounds(0,200,785,300);
			emptyPanel.add(empty);
		}
		
		
		
		
		
		
		
		JPanel[] panel = new JPanel[list.size()];		//패널
		JLabel[] cNo = new JLabel[list.size()];			//c_no 입력 라벨
		JLabel[] image = new JLabel[list.size()];		//이미지 라벨
		JLabel[] number = new JLabel[list.size()];		//구매번호 라벨
		JLabel[] name = new JLabel[list.size()];		//이름
		JLabel[] option = new JLabel[list.size()];		//옵션
		JTextPane[] additional = new JTextPane[list.size()];	//추가옵션
		JLabel[] price1 = new JLabel[list.size()];		//금액 (고정글자)
		JLabel[] price2 = new JLabel[list.size()];		//금액 입력 (￦ 붙이기)
		JButton[] cancel = new JButton[list.size()];	//취소버튼
		JLabel[] end = new JLabel[list.size()];			//메뉴 마감선
		
		
		for (int i = 0; i < list.size(); i++) {
			
			 int temp = 0;
			 String burgerName = list.get(i).getH_name();

			 HashMap<String, Integer> burgerMap = new HashMap<>();
			 burgerMap.put("더블불고기버거", 1);
			 burgerMap.put("딥치즈버거", 2);
			 burgerMap.put("불고기버거", 3);
			 burgerMap.put("새우버거", 4);
			 burgerMap.put("아보카도버거", 5);
			 burgerMap.put("어니언치즈버거", 6);
			 burgerMap.put("치즈버거", 7);
			 burgerMap.put("치킨버거", 8);
			 burgerMap.put("트리플패티버거", 9);
			 burgerMap.put("할라피뇨치킨버거", 10);

			 temp = burgerMap.getOrDefault(burgerName, 0);
			    
			 String filePath = ".\\BurgerImg\\" + String.valueOf(temp) + ".png";
		
		//------------------------------------------------------------------------//	
			
		//패널
			panel[i] = new JPanel();
			panel[i].setBackground(Color.WHITE);
			panel[i].setLayout(null);
			panel[i].setPreferredSize(new Dimension(700, 650));
			panel[i].setBackground(new Color(250,250,240));
			
			scrollContent.add(panel[i]);
			
			
		//c_no 입력칸
			cNo[i] = new JLabel(Integer.toString(list.get(i).getC_No()));
			cNo[i].setFont(new Font("Dialog", Font.BOLD, 25));
			cNo[i].setForeground(Color.WHITE);
			cNo[i].setBounds(0,0,50,50);
			cNo[i].setHorizontalAlignment(SwingConstants.CENTER);
			cNo[i].setOpaque(false);
			
			panel[i].add(cNo[i]);
			
			
		//햄버거 사진
			ImageIcon icon = new ImageIcon(filePath);
			Image img = icon.getImage();
			img = img.getScaledInstance(180, 180, Image.SCALE_SMOOTH);
			icon = new ImageIcon(img);

			image[i] = new JLabel(icon);
			image[i].setBounds(55,120,290,330);
			image[i].setBorder(BorderFactory.createEtchedBorder());
			image[i].setBackground(Color.WHITE);
			
			panel[i].add(image[i]);
			
			
		//구매 번호
			number[i] = new JLabel(Integer.toString(i+1));
			number[i].setFont(new Font("한컴 고딕", Font.BOLD, 25));
			number[i].setBorder(BorderFactory.createEtchedBorder());
			number[i].setBounds(55,50,50,50);
			number[i].setHorizontalAlignment(SwingConstants.CENTER);
			
			panel[i].add(number[i]);
			
			
		//햄버거 이름
			name[i] = new JLabel(list.get(i).getH_name());
			name[i].setBorder(BorderFactory.createEtchedBorder());
			name[i].setBounds(105,50,240,50);
			name[i].setHorizontalAlignment(SwingConstants.CENTER);
			name[i].setFont(new Font("한컴 고딕", Font.BOLD, 25));
			
			panel[i].add(name[i]);
			
			
		//햄버거 옵션
			option[i] = new JLabel(list.get(i).getH_option());
			option[i].setBounds(395,50,290,50);
			option[i].setBorder(BorderFactory.createEtchedBorder());
			option[i].setHorizontalAlignment(SwingConstants.CENTER);
			option[i].setFont(new Font("한컴 고딕", Font.BOLD, 25));
			
			panel[i].add(option[i]);
			
			
		//추가 옵션
			additional[i] = new JTextPane();	
			additional[i].setBounds(395,120,290,330);
			additional[i].setBackground(new Color(250,250,240));
			additional[i].setEditable(false);
			additional[i].setLayout(null);
			additional[i].setBorder(BorderFactory.createEtchedBorder());
			
			StyledDocument doc = additional[i].getStyledDocument();
			Style style = additional[i].addStyle("Style1", null);
			StyleConstants.setFontFamily(style, "한컴 고딕");
			StyleConstants.setFontSize(style, 20);
			StyleConstants.setBold(style, true);
			
			SimpleAttributeSet left = new SimpleAttributeSet();
			StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
			doc.setParagraphAttributes(0, doc.getLength(), left, false);
			
			try {									
				String side = list.get(i).getS_name();
				
				String beverage = list.get(i).getB_name();
				
				doc.insertString(doc.getLength(), 
						String.format("[사이드 메뉴]\n\n%s\n\n\n\n[음료]\n\n%s",side,beverage)
						, style);
			} catch (BadLocationException e) {
				JOptionPane.showMessageDialog(null, "오류1.\nError : " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
			}
			
			panel[i].add(additional[i]);
			
			
		//금액 (안내 글자)
			price1[i] = new JLabel("금액 :");
			price1[i].setBorder(BorderFactory.createEtchedBorder());
			price1[i].setBounds(55,480,290,50);
			price1[i].setFont(new Font("한컴 고딕", Font.BOLD, 20));
			
			panel[i].add(price1[i]);
			
			
		//금액 (해당 주문건 금액)
			price2[i] = new JLabel("￦  " + decimalFormat.format(list.get(i).getC_price()));
			price2[i].setHorizontalAlignment(SwingConstants.RIGHT);
			price2[i].setBounds(150,480,180,50);
			price2[i].setFont(new Font("한컴 고딕", Font.BOLD, 20));
			
			panel[i].add(price2[i]);
			
			
		//취소 버튼
			int c_no = list.get(i).getC_No();
			
			cancel[i] = new JButton("취소");
			cancel[i].setBounds(395,480,290,50);
			cancel[i].addActionListener(e->{
				try {
					cDAO.delete(c_no);
				} catch (SQLException e1) {
					System.out.println("SQL 예외 : "+e1.getMessage());
				}
				
				JOptionPane.showMessageDialog(null, "취소되었습니다.");
				frame.dispose();
				new Cart();
			});
			
			panel[i].add(cancel[i]);
			
			
		//메뉴 마감선
			end[i] = new JLabel("━".repeat(100));
			end[i].setBounds(50,550,675,15);
			
			panel[i].add(end[i]);
			
		}//for end
		
	

		
		
		
		
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//		
		
		
		// 총 금액
		JLabel total = new JLabel("총 금액 :");
		total.setBorder(BorderFactory.createEtchedBorder());
		total.setFont(new Font("맑은고딕",Font.BOLD,25));
		total.setBounds(280, 730, 250, 70);
		frame.getContentPane().add(total);
		
		// 총 금액 입력 칸
		try {
		JLabel totalWon = new JLabel("￦  "+decimalFormat.format(cDAO.total()));
		totalWon.setHorizontalAlignment(SwingConstants.RIGHT);
		totalWon.setFont(new Font("맑은고딕",Font.BOLD,25));
		totalWon.setBounds(400, 730, 120, 70);
		frame.getContentPane().add(totalWon);
		} catch(SQLException e) {
			System.out.println("SQL 예외 : "+e.getMessage());
		}
		// 전체 취소
		JButton allCancel = new JButton("전체 취소");
		allCancel.setBounds(55, 830, 300, 70);
		allCancel.addActionListener(e->{
			try {
				cDAO.truncate();
			} catch (SQLException e1) {
				System.out.println("SQL 예외 : "+e1.getMessage());
			}
			JOptionPane.showMessageDialog(null, "전체 취소되었습니다.");
			frame.dispose();
			new Cart();
			menu.frame.setEnabled(true);
		});
		
		frame.getContentPane().add(allCancel);
		
		// 구매하기
		JButton buy = new JButton("구매하기");
		buy.setBounds(420, 830, 300, 70);
		buy.addActionListener(e->{
			getpayment(frame);
		});
		
		frame.getContentPane().add(buy);
		
		frame.setResizable(false);
		frame.setVisible(true);
		
	}
	
	
	private void getpayment(JFrame frame) {
		frame.dispose();
		new Payment();
	}
	
}
