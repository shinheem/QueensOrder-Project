package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

import DAO.CartDAO;
import DAO.MembersDAO;
import DAO.SalesHistoryDAO;
import DTO.CartDTO;
import DTO.MembersDTO;


//TODO 꼭 확인하기 ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★



//결제창 - 반복문으로 코드 간소화
public class Payment {
	
	Login login;
	
	@SuppressWarnings("static-access")
	String ID = login.CustomerID;
	
	CartDAO cDAO = CartDAO.getCartDAO();
	MembersDAO mDAO = MembersDAO.getMembersDAO();
	SalesHistoryDAO sDAO = SalesHistoryDAO.getSalesHistoryDAO();
	
	DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
	
	public JFrame frame;
	
	public static void main(String[] args) {
		new Payment();
	}
	
	
	public Payment() {
		
		int y = 0;
		
		
//● 메인 프레임 생성
	//메인 프레임 생성
		frame = new JFrame();
		frame.setSize(800,1000);
		frame.getContentPane().setBackground(new Color(250,250,240));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Queen's Order");
		frame.setResizable(false);
		
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//		
//● 패널 생성
		
	//고객 정보 패널 생성
		JPanel custJP = new JPanel();
		custJP.setSize(700,300);
		custJP.setLocation(50,50);
		custJP.setLayout(null);
		custJP.setBackground(new Color(250,250,240));
		
	//주문내역 패널 생성
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(50,370,700,400);
		scrollPane.setBackground(new Color(250,250,240));
		frame.getContentPane().add(scrollPane);

		JPanel scrollContent = new JPanel();
		scrollContent.setLayout(new BoxLayout(scrollContent, BoxLayout.Y_AXIS)); // Y축 방향으로 아이템들이 쌓이도록 설정
		scrollPane.setViewportView(scrollContent);
		
	//결제 패널 생성
		JPanel payJP = new JPanel();
		payJP.setSize(700,150);
		payJP.setLocation(50,790);
		payJP.setLayout(null);
		payJP.setBackground(new Color(250,250,240));	//TODO
		
	//패널 ▶ 프레임 합체
		frame.getContentPane().add(custJP);
		frame.getContentPane().add(scrollPane);
		frame.getContentPane().add(payJP);
		
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//		
//● 고객 정보 라벨 생성
		
	//고객 정보 - Title
		JLabel custInfo = new JLabel("[고객 정보]");
		custInfo.setFont(new Font("한컴 고딕", Font.BOLD, 20));
		custInfo.setSize(700,30);
		custInfo.setLocation(0,10);
		custInfo.setHorizontalAlignment(SwingConstants.CENTER);
		custJP.add(custInfo);
	
	//고객 정보 - 이름, 전화번호, 주소
		JLabel[] custLB = new JLabel[3];
		String[] cust_LB = {"이름", "전화번호", "주소"};
		y = 50;
		
		for (int i = 0; i < cust_LB.length; i++) {
			custLB[i] = new JLabel(cust_LB[i]);
			custLB[i].setFont(new Font("한컴 고딕", Font.BOLD, 20));
			custLB[i].setBounds(0,y+i*60,100,50);
			custLB[i].setHorizontalAlignment(SwingConstants.LEFT);
			
			custJP.add(custLB[i]);
		}
		
//----------------------------------------------------------//
//● 출력 정보 라벨 생성
		MembersDTO mDTO = null;
		try {
			mDTO = mDAO.memberInfo(ID);
		} catch (SQLException e1) {
			System.out.println("SQL 예외 : "+e1.getMessage());
		}
		
		String mName = mDTO.getM_name();
		String phone = mDTO.getPhone();
		String address = mDTO.getAddress();
	//이름, 전화번호 출력 라벨
		JLabel[] userLB = new JLabel[2];
		String[] user_LB = {mName,phone};
		y = 50;
		
		for (int i = 0; i < user_LB.length; i++) {
			userLB[i] = new JLabel("▶  "+user_LB[i]);
			userLB[i].setFont(new Font("한컴 고딕", Font.BOLD, 20));
			userLB[i].setBounds(110,y+i*60,190,50);
			userLB[i].setHorizontalAlignment(SwingConstants.LEFT);
			userLB[i].setOpaque(true);
			userLB[i].setBackground(new Color(250,250,240));
			
			custJP.add(userLB[i]);
		}
		
	//주소 출력 라벨 (자동 줄바꿈)
		JTextPane userAddress = new JTextPane();
		userAddress.setSize(590,110);
		userAddress.setLocation(110, 175);
		userAddress.setBackground(new Color(250,250,240));
		userAddress.setEditable(false);
		
		StyledDocument doc = userAddress.getStyledDocument();
		Style style = userAddress.addStyle("Style1", null);
		StyleConstants.setFontFamily(style, "한컴 고딕");
		StyleConstants.setFontSize(style, 20);
		StyleConstants.setBold(style, true);
		
		SimpleAttributeSet left = new SimpleAttributeSet();
		StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
		doc.setParagraphAttributes(0, doc.getLength(), left, false);
		
		try {
			doc.insertString(doc.getLength(), "▶  "+address, style);
		} catch (BadLocationException e) {
			JOptionPane.showMessageDialog(null, "고객 주소를 불러오던 중 오류가 발생했습니다.\nError : "+e.getMessage(),"오류", JOptionPane.ERROR_MESSAGE);
		}
		
		custJP.add(userAddress);
		
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//		
//TODO 장바구니창 불러오기		
		
		List<CartDTO> list = new ArrayList<>();
		try {
			list = cDAO.selectAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(list.size()==0) {
			JPanel emptyPanel = new JPanel();
			emptyPanel.setBounds(0,0,700,400);
			emptyPanel.setBackground(Color.WHITE);
			emptyPanel.setLayout(null);
			
			scrollContent.add(emptyPanel);
			
			
			JLabel empty = new JLabel("장바구니가 비어있습니다.");
			empty.setFont(new Font("맑은 고딕",Font.BOLD,50));
			empty.setHorizontalAlignment(SwingConstants.CENTER);
			empty.setBounds(0,100,700,200);
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
			additional[i].setBackground(Color.WHITE);
			additional[i].setEditable(false);
			additional[i].setLayout(null);
			additional[i].setBorder(BorderFactory.createEtchedBorder());
			
			StyledDocument doc2 = additional[i].getStyledDocument();
			Style style2 = additional[i].addStyle("Style1", null);
			StyleConstants.setFontFamily(style2, "한컴 고딕");
			StyleConstants.setFontSize(style2, 20);
			
			SimpleAttributeSet left2 = new SimpleAttributeSet();
			StyleConstants.setAlignment(left2, StyleConstants.ALIGN_LEFT);
			doc2.setParagraphAttributes(0, doc2.getLength(), left2, false);
			
			try {									
				String side = list.get(i).getS_name();
				
				String beverage = list.get(i).getB_name();
				
				doc2.insertString(doc2.getLength(), 
						String.format("[사이드 메뉴]\n\n%s\n\n\n\n[음료]\n\n%s",side,beverage)
						, style2);
			} catch (BadLocationException e) {
				JOptionPane.showMessageDialog(null, "오류1.\nError : " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
			}
			
			panel[i].add(additional[i]);
			
			
		//금액 (안내 글자)
			price1[i] = new JLabel("금액 :");
			price1[i].setBorder(BorderFactory.createEtchedBorder());
			price1[i].setBounds(55,480,290,50);
			price1[i].setFont(new Font("한컴 고딕", Font.PLAIN, 20));
			
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
		
//TODO 장바구니창 여기까지가 끝	
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//		
//● 결제 수단 콤보박스 및 버튼 생성
	
	//결제 수단 - Title
		JLabel payInfo = new JLabel("[결제 수단]");
		payInfo.setFont(new Font("한컴 고딕", Font.BOLD, 20));
		payInfo.setSize(700,30);
		payInfo.setLocation(0,10);
		payInfo.setHorizontalAlignment(SwingConstants.CENTER);
		payJP.add(payInfo);
		
	//결제 수단 콤보박스
		JComboBox<String> payType = new JComboBox<>();
		payType.setSize(430,30);
		payType.setLocation(30,50);
		
		
		payType.addItem("결제 수단을 선택하세요  (Please select a payment method)");
		payType.addItem("------------------------------------------------------------------------------");
		payType.addItem("어플로  카드  결제  (Card Payment through App)");
		payType.addItem("만나서  카드  결제  (Card Payment in Person)");
		payType.addItem("만나서  현금  결제  (Cash Payment in Person)");
		payType.setSelectedIndex(0);
		
		payType.addActionListener(e -> {
			int select = payType.getSelectedIndex();
			switch(select) {
				case 0,1 -> JOptionPane.showMessageDialog(null, "올바른 결제 수단을 선택해주세요.","결제 수단 선택",JOptionPane.ERROR_MESSAGE);
//				case 2,3,4 -> 
			}
		});
		
		payJP.add(payType);
		
	//결제 버튼
		JButton payBT = new JButton("결제");
		payBT.setFont(new Font("한컴 고딕", Font.BOLD, 20));
		payBT.setSize(200,90);
		payBT.setLocation(490,50);
		payBT.addActionListener(e -> {
			int select = payType.getSelectedIndex();
			if(select == 0 || select == 1)
				JOptionPane.showMessageDialog(null, "올바른 결제 수단을 선택해주세요.","결제 수단 선택",JOptionPane.ERROR_MESSAGE);
			else {String option2 = payType.getItemAt(select);
	        JOptionPane.showMessageDialog(null, option2 + "를 선택하셨습니다.", "선택한 결제 수단", JOptionPane.INFORMATION_MESSAGE);
			
			 Random random = new Random();
	         int deliveryTime = random.nextInt(21) * 5 + 20; // 5의 배수이며 최소 20분, 최대 120분
	         
	         LocalTime currentTime = LocalTime.now();
	         LocalTime estimatedTime = currentTime.plusMinutes(deliveryTime);
	         
	         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH시 mm분");
	         String formattedTime = estimatedTime.format(formatter);
	         
	        int total=0;
			try {
				total = cDAO.total();
			} catch (SQLException e1) {
				System.out.println("SQL 예외 : "+e1.getMessage());
			}
			
			
	         try {
				sDAO.update(ID, total);
			} catch (SQLException e1) {
				System.out.println("SQL 예외 : "+e1.getMessage());
			}
	         
	         
	         try {
				cDAO.truncate();
			} catch (SQLException e1) {
				System.out.println("SQL 예외 : "+e1.getMessage());
			}
	         
	         JOptionPane.showMessageDialog(null, "결제 및 주문이 완료되었습니다. 감사합니다.\n※ 예상 소요 시간: "+deliveryTime+"분"+"\n※ 도착 예정 시간: " + formattedTime , "결제 및 주문 완료", JOptionPane.INFORMATION_MESSAGE);
			
	         completePayment(frame);
			}
		});
		payJP.add(payBT);
		
		
		
		
	//메인 프레임 활성화
		frame.setVisible(true);
		
	}//생성자 end
	
	private void completePayment(JFrame frame) {
		frame.dispose();
		new AfterPayment();
	}
	
	
}//class end
