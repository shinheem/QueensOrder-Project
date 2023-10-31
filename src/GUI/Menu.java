package GUI;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import DAO.BeverageDAO;
import DAO.CartDAO;
import DAO.HamburgerDAO;
import DAO.SideMenuDAO;
import DTO.CartDTO;


public class Menu {
	
	public static String hname;
	public static String hoption = "단품";
	public static JFrame frame;
	
	Login login;
	
	
	@SuppressWarnings("static-access")
	String ID = login.CustomerID;
	
	CartDTO cart = null;
	
	public static void main(String[] args) {
		new Menu();
	}
	
	public String h_name;
	public String h_option = "단품";
	
	BeverageDAO bDAO = BeverageDAO.getBeverageDAO();
	CartDAO cDAO = CartDAO.getCartDAO();
	SideMenuDAO sDAO = SideMenuDAO.getSideMenuDAO();
	HamburgerDAO hDAO = HamburgerDAO.getHamburgerDAO();
	
	
	//수정시작
	public Menu() {
		
		//Frame
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 10, 800, 800);
		frame.setResizable(false);
		frame.setTitle("BURGER QUEEN");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
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
		
		//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//
		
		
		
		ImageIcon imageIcon = new ImageIcon("./image/123.png");
		JLabel imageLabel = new JLabel(imageIcon);
		imageLabel.setSize(800,800);
		frame.getContentPane().add(imageLabel);
		
		JLabel logoLB = new JLabel("BURGER QUEEN");
		logoLB.setBounds(190,60,380,35);
		logoLB.setHorizontalAlignment(SwingConstants.CENTER);
		logoLB.setFont(new Font("Britannic Bold",Font.BOLD,50));
		
		imageLabel.add(logoLB);
		
		
		//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//
		
		
		//버거,사이드,음료 탭 패널
		JTabbedPane tapPanel = 
				new JTabbedPane(JTabbedPane.TOP);
		tapPanel.setFont(new Font("-윤고딕350", Font.BOLD, 13));
		tapPanel.setBounds(125, 145, 520, 520);
		imageLabel.add(tapPanel);
		
		
		//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//
		
		
		//버거 패널
		JPanel burgerPanel = new JPanel();
		tapPanel.addTab("버거", null, burgerPanel, null);
		burgerPanel.setLayout(null);
		burgerPanel.setBackground(new Color(250,250,240));
		
		
		//버거 스크롤 패널
		JScrollPane burgerScroll = new JScrollPane();
		burgerScroll.setBounds(30, 45, 460, 400);
		burgerScroll.setBackground(new Color(250,250,240));
		burgerPanel.add(burgerScroll);
		
		JPanel backgroundPanel = new JPanel();
		burgerScroll.setViewportView(backgroundPanel);
		backgroundPanel.setLayout(new GridLayout(5, 2, 10, 10));
		
		String[] imgPath1 = new String[10];
		String filePath1 = ".\\BurgerImg\\";
		for (int i = 0; i < imgPath1.length; i++) {
			String fileName = filePath1 + String.valueOf(i + 1) + ".png";
			imgPath1[i] = fileName;
		}		
				
		int countBT1 = 10;
		JLabel[] Img1 = new JLabel[countBT1];
		JPanel[] imgPanel1 = new JPanel[countBT1];
		JLabel[] nameLB1 = new JLabel[countBT1];
		
		
		String[] burgernames = new String[10];
		for (int i = 0; i < burgernames.length; i++) {
			try {
				burgernames[i] = hDAO.selectAllName().get(i);
			} catch (SQLException e1) {
				System.out.println("SQL 예외 : "+e1.getMessage());
			}
			
		}//for end		
		
		
		//버거 버튼 반복문
		 for (int i = 0; i < Img1.length; i++) {
			 //상품 패널 생성
			 imgPanel1[i] = new JPanel();
			 imgPanel1[i].setLayout(new BorderLayout());
			 imgPanel1[i].setSize(200,220);
			 imgPanel1[i].setBackground(new Color(250,250,240));
			 
			 //버거 이미지 라벨 생성
			 Img1[i] = new JLabel();
			 Img1[i].setBackground(new Color(255, 255, 255));
			 
			 //이미지 크기조정
			 ImageIcon icon1 = new ImageIcon(imgPath1[i]);
			 Image img1 = icon1.getImage();
			 img1 = img1.getScaledInstance(180, 180, Image.SCALE_SMOOTH);
			 icon1 = new ImageIcon(img1);
			 Img1[i].setIcon(icon1);			//이미지 크기는 150,130
			 imgPanel1[i].add(Img1[i],BorderLayout.NORTH);
			
			 //버거 이름 레이블
			 nameLB1[i] = new JLabel(burgernames[i]);
			 nameLB1[i].setFont(new Font("맑은고딕", Font.BOLD, 15));			
			 nameLB1[i].setVerticalAlignment(SwingConstants.TOP);
			 nameLB1[i].setHorizontalAlignment(SwingConstants.CENTER);
		     
			 
			 nameLB1[i].setSize(180,20);		//레이블 크기
			 imgPanel1[i].add(nameLB1[i],BorderLayout.CENTER);
		      
		     backgroundPanel.add(imgPanel1[i]);
		     backgroundPanel.setBackground(new Color(250,250,240));
		     
		     //버거 단품,세트 콤보박스
		     AtomicReference<String> result = new AtomicReference<>("단품");
		     String[] temp = {"단품", "세트"};
		     JComboBox<String> jc = new JComboBox<>(temp);
		     jc.setSelectedIndex(0);
		     jc.addActionListener(e -> {
		         if (jc.getSelectedIndex() == 0) {
		             result.set("단품");
		             hoption = "단품";
		         } else {
		             result.set("세트");
		             hoption = "세트";
		         }
		     });
		     
		     imgPanel1[i].add(jc, BorderLayout.EAST);
		
		     
		     //카트 담기 버튼
		     StringBuffer temp2 = new StringBuffer();
		     try {
				temp2.append("￦  "+hDAO.selectOnePrice(burgernames[i], "단품")+" / "+hDAO.selectOnePrice(burgernames[i], "세트")+"   담기");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		     
		     
		     JButton addBT1 = new JButton(temp2.toString());
		     addBT1.setFont(new Font("맑은고딕",Font.BOLD,15));
		     imgPanel1[i].add(addBT1, BorderLayout.SOUTH);
		     
		     AtomicReference<String> burgerNameRef = new AtomicReference<>(burgernames[i]);
		     addBT1.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {
		        	 hname = burgerNameRef.get();
		        	 
		        	 if (result.get().equals("단품")) {
		            	 getsinglepage(frame);
		            	 
		             } else if (result.get().equals("세트")) {
		            	 getsetpage(frame);
		             }
		         }
		     });

		 }//for end
		 
	
		 ///////////////////////////////////////////////////////////////////////////////////
		 
		
		//사이드 패널
		JPanel side_Pn = new JPanel();
		tapPanel.addTab("사이드", null, side_Pn, null);
		side_Pn.setLayout(null);
		side_Pn.setBackground(new Color(250,250,240));
		
		//사이드 스크롤 패널
		JScrollPane side_Spn = new JScrollPane();
		side_Spn.setBounds(30, 45, 460, 400);  
		side_Pn.add(side_Spn);
		side_Spn.setBackground(new Color(250,250,240));
		
		JPanel sideLay_pn = new JPanel();
		side_Spn.setViewportView(sideLay_pn);
		sideLay_pn.setLayout(new GridLayout(0, 2, 10, 5));
		sideLay_pn.setBackground(new Color(250,250,240));		
				
		//사이드 버튼 반복문
		
		//이미지 파일 삽입을 위한 반복문
		String[] sideBtimg = new String[5];
		String filepath2 = ".\\SideImg\\";
		for (int i = 0; i < sideBtimg.length; i++) {
			String fileName2 = filepath2 + String.valueOf(i + 1) + ".png";
			sideBtimg[i] = fileName2;
		}//for end
		
		int numbuttonsides = 5;
		JLabel[] sideimg_Lb = new JLabel[numbuttonsides];
		JPanel[] sidename_Pn = new JPanel[numbuttonsides];
		JLabel[] sidename_Lb = new JLabel[numbuttonsides];
		
		//사이드 이름 가져오기
		String[] sidenames = new String[5];
		for (int i = 0; i < sidenames.length; i++) {
			try {
				sidenames[i] = sDAO.selectSingleName2().get(i);
			} catch (SQLException e2) {
				System.out.println("SQL 예외 : "+e2.getMessage());
			}
			
		}//for end		
		
		
		for ( int i = 0; i < sideimg_Lb.length; i++) {
			
			//상품 패널 생성
			sidename_Pn[i] = new JPanel();
			sidename_Pn[i].setLayout(new BorderLayout());
			sidename_Pn[i].setSize(200, 220);
			sidename_Pn[i].setBackground(new Color(250,250,240));
			
			//버튼 생성
			sideimg_Lb[i] = new JLabel();
			sideimg_Lb[i].setBackground(new Color(255, 255, 255));
			
			//이미지 크기 조정
			ImageIcon icon2 = new ImageIcon(sideBtimg[i]);
			Image img2 = icon2.getImage();
			img2 = img2.getScaledInstance(180, 180, Image.SCALE_SMOOTH);
			icon2 = new ImageIcon(img2);
			sideimg_Lb[i].setIcon(icon2);       //이미지 크기는 150,130
			sidename_Pn[i].add(sideimg_Lb[i], BorderLayout.NORTH);
			
			//사이드 이름 레이블
			sidename_Lb[i] = new JLabel(sidenames[i]);
			sidename_Lb[i].setFont(new Font("-윤고딕340", Font.BOLD, 15));
			sidename_Lb[i].setVerticalAlignment(SwingConstants.TOP);
			sidename_Lb[i].setHorizontalAlignment(SwingConstants.CENTER);
			
			String side = sidename_Lb[i].getText();
			
			sidename_Lb[i].setSize(180,20);			//레이블 크기
			sidename_Pn[i].add(sidename_Lb[i],BorderLayout.CENTER);
			
			sideLay_pn.add(sidename_Pn[i]);
			
			
			//카트 담기 버튼
		    StringBuffer temp2 = new StringBuffer();
		     try {
				temp2.append("￦  "+sDAO.selectOnePrice(side)+"   담기");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		    
		    JButton add2_Bt = new JButton(temp2.toString());
		    add2_Bt.setFont(new Font("-윤고딕340", Font.BOLD, 15));
		    add2_Bt.addActionListener(e -> {
		    	
		    	try {
					cDAO.addSideMenu(ID, side);
					
					JOptionPane.showMessageDialog(null, "["+side+"] 장바구니에 추가되었습니다");
				} catch (SQLException e1) {
					System.out.println("SQL 예외 : "+e1.getMessage());
				}
		    	
		    });
		    sidename_Pn[i].add(add2_Bt, BorderLayout.SOUTH);
		     			
		}//for end
		

		//////////////////////////////////////////////////////////////////////
		
		
		//음료 패널
		JPanel drink_Pn =  new JPanel();
		tapPanel.addTab("음료", null, drink_Pn, null);	
		drink_Pn.setLayout(null);
		drink_Pn.setBackground(new Color(250,250,240));
		
		//음료 스크롤 패널
		JScrollPane drink_Spn = new JScrollPane();
		drink_Spn.setBounds(30, 45, 460, 400);
		drink_Pn.add(drink_Spn);
		drink_Pn.setBackground(new Color(250,250,240));
		
		JPanel drinkLay_Pn = new JPanel();
		drink_Spn.setViewportView(drinkLay_Pn);
		drinkLay_Pn.setLayout(new GridLayout(0, 2, 10, 5));
		drinkLay_Pn.setBackground(new Color(250,250,240));
		
		//주문하기 버튼
		JButton orderBT = new JButton("주문하기");
		imageLabel.add(orderBT);
		orderBT.setBounds(170, 685, 150, 50);
		orderBT.setFont(new Font("-윤고딕330", Font.BOLD, 21));
		
		
		//장바구니 버튼
		JButton cartBT = new JButton("장바구니");
		imageLabel.add(cartBT);
		cartBT.setBounds(440, 685, 150, 50);
		cartBT.setFont(new Font("-윤고딕330", Font.BOLD, 21));
		cartBT.addActionListener(e ->{
			frame.setEnabled(false);
			
			Cart cart = new Cart();
			cart.frame.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					Menu.frame.setEnabled(true);
				}
			});
		});
		orderBT.addActionListener(e -> {
			frame.setEnabled(false);
			
			Payment payment = new Payment();
			payment.frame.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					Menu.frame.setEnabled(true);
				}
			});
		});
		
		
		//음료 버튼 반복문
		
		//이미지 파일 삽입을 위한 반복문
		String[] drinkbuttonimg = new String[4];
		String filepath3 = ".\\BeverageImg\\";
		for(int i = 0; i < drinkbuttonimg.length; i++) {
			String fileName3 = filepath3 + String.valueOf(i + 1) + ".png";
			drinkbuttonimg[i] = fileName3;
		}//for end
		
		int numbuttondrinks = 4;
		JLabel[] b_LB = new JLabel[numbuttondrinks];
		JPanel[] b_namePanel = new JPanel[numbuttondrinks];
		JLabel[] b_nameLB = new JLabel[numbuttondrinks];
		
		
		//음료 이름 가져오기
		String[] b_name = new String[4];
		for (int i = 0; i < b_name.length; i++) {
			try {
				b_name[i] = bDAO.selectSingleName2().get(i);
			} catch (SQLException e3) {
				System.out.println("SQL 예외 : "+e3.getMessage());
			}
			
		}
		
		
		for ( int i = 0; i < b_LB.length; i++) {
			
			//상품 패널 생성
			b_namePanel[i] = new JPanel();
			b_namePanel[i].setLayout(new BorderLayout());
			b_namePanel[i].setSize(200, 220);
			b_namePanel[i].setBackground(new Color(250,250,240));
			
			
			//버튼 생성
			b_LB[i] = new JLabel();
			b_LB[i].setBackground(new Color(255, 255, 255));
			
			//이미지 크기 조정
			ImageIcon icon3 = new ImageIcon(drinkbuttonimg[i]);
			Image img3 = icon3.getImage();
			img3 = img3.getScaledInstance(180, 180, Image.SCALE_SMOOTH);
			icon3 = new ImageIcon(img3);
			b_LB[i].setIcon(icon3);       //이미지 크기는 150,130
			b_namePanel[i].add(b_LB[i], BorderLayout.NORTH);
			
			//음료 이름 레이블
			b_nameLB[i] = new JLabel(b_name[i]);			
			b_nameLB[i].setFont(new Font("-윤고딕340", Font.BOLD, 15));
			b_nameLB[i].setVerticalAlignment(SwingConstants.TOP);
			b_nameLB[i].setHorizontalAlignment(SwingConstants.CENTER);
			
			String beverage = b_nameLB[i].getText();
			
			b_nameLB[i].setSize(180,20);			//레이블 크기
			b_namePanel[i].add(b_nameLB[i],BorderLayout.CENTER);
			
			drinkLay_Pn.add(b_namePanel[i]);
			
			
			 StringBuffer temp2 = new StringBuffer();
		     try {
				temp2.append("￦  "+bDAO.selectOnePrice(beverage)+"   담기");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			//카트 담기 버튼
			JButton add3_Bt = new JButton(temp2.toString());
			add3_Bt.setFont(new Font("-윤고딕340", Font.BOLD, 15));
			add3_Bt.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
					cDAO.addBeverage(ID, beverage);
					JOptionPane.showMessageDialog(null, "["+beverage+"] 장바구니에 추가되었습니다");
				} catch (SQLException e1) {
					System.out.println("SQL 예외 : "+e1.getMessage());
				}
				}
			});
			
		    b_namePanel[i].add(add3_Bt, BorderLayout.SOUTH);
		     		
			
		}//for end
		
		frame.setVisible(true);
		
	}//생성자 end
	
 	private void getsinglepage(JFrame jframe) {
		new SingleOption();
	}
	private void getsetpage(JFrame jframe) {
		new SetOption();
	}
	
	
	
}//class end