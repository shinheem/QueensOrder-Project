package GUI;


import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import DAO.BeverageDAO;
import DAO.CartDAO;
import DAO.SideMenuDAO;

public class SetOption {

	public static void main(String[] args) {
		new SetOption();
	}
	
	DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
	
	SideMenuDAO sDAO = SideMenuDAO.getSideMenuDAO();
	BeverageDAO bDAO = BeverageDAO.getBeverageDAO();
	CartDAO cDAO = CartDAO.getCartDAO();
	Login login;
	Menu menu;
	
	public SetOption() {
		JFrame frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 800, 800);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			 @Override
           public void windowClosing(WindowEvent e) {
               JOptionPane.showMessageDialog(null, "⚠ 세트 옵션 선택은 필수입니다 ⚠","warning"
            		   ,JOptionPane.ERROR_MESSAGE);
           }
		});
		
		
		JLabel logoLB = new JLabel("BURGER QUEEN");
		logoLB.setBounds(190, 50, 380, 50);
		logoLB.setHorizontalAlignment(SwingConstants.CENTER);
		logoLB.setFont(new Font("Britannic Bold", Font.BOLD, 45));
		frame.add(logoLB);
	
		
		JScrollPane scroll = new JScrollPane();
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(122, 148, 516, 468);
		scroll.setBackground(new Color(250,250,240));
		frame.getContentPane().add(scroll);
		
		JPanel scrollContent = new JPanel();
		scrollContent.setLayout(new GridLayout(16,2,60,5));
		scrollContent.setBackground(new Color(250,250,240));
		scroll.setViewportView(scrollContent);
		
		frame.getContentPane().add(scroll);
 		
		
				
		
		Font font1 = new Font("한컴 고딕", Font.BOLD, 15);
		Font font2 = new Font("한컴 고딕", Font.BOLD, 18);

//라벨 생성		
		JLabel sideChoise = new JLabel("▶▶ 사이드 선택(한가지 필수) ◀◀");
		sideChoise.setFont(font2);		
		scroll.add(sideChoise);
		
		JLabel space1 = new JLabel("");
		scroll.add(space1);
		
		JLabel space2 = new JLabel("");
		scroll.add(space2);
		
		
		
	
	
		JLabel drinkCh = new JLabel("▶▶ 음료선택(한가지 필수) ◀◀");
		drinkCh.setFont(font2);		
		scroll.add(drinkCh);
	
		scrollContent.add(sideChoise);		
		scrollContent.add(space1);		
		
		
		ButtonGroup sideGr = new ButtonGroup();
		JCheckBox[] sidemenu = new JCheckBox[5];

		//사이드메뉴 이름 가져오기
		String[] s_name = new String[5];
		for (int i = 0; i < s_name.length; i++) {
			try {
				s_name[i] = sDAO.selectSetName().get(i);
			} catch (SQLException e) {
				System.out.println("SQL 예외 : "+e.getMessage());
			}
		}
		
		
		//사이드메뉴 가격 가져오기
		String[] s_price = new String[5];
		for (int i = 0; i < s_price.length; i++) {
			try {
				s_price[i] = "+  "+decimalFormat.format(sDAO.selectOnePrice(s_name[i]))+" ￦";
			} catch (SQLException e) {
				System.out.println("SQL 예외 : "+e.getMessage());
			}
		}
				
		JLabel[] side = new JLabel[5];		
		for(int i=0; i<side.length; i++) {
			side[i] = new JLabel(String.valueOf(s_price[i]));
			side[i].setFont(font1);
			sidemenu[i] =  new JCheckBox(s_name[i]);
			sidemenu[i].setFont(font1);	
			sidemenu[i].setBackground(new Color(250,250,240));
			
			scroll.add(side[i]); 
			scroll.add(sidemenu[i]);		
			scrollContent.add(sidemenu[i]);
			scrollContent.add(side[i]);
			sideGr.add(sidemenu[i]);	
		}
		
		scrollContent.add(drinkCh);	
		scrollContent.add(space2);	
		
		ButtonGroup drink = new ButtonGroup();
		JCheckBox[] drinkmenu = new JCheckBox[4];	
		
		//음료 이름 가져오기
		String[] beverage = new String[4];
		for (int i = 0; i < beverage.length; i++) {
			try {
				beverage[i] = bDAO.selectSetName().get(i);
			} catch (SQLException e) {
				System.out.println("SQL 예외 : "+e.getMessage());
			}
		}
		
		
		
		Integer[] b_price = new Integer[4];
		for (int i = 0; i < b_price.length; i++) {
			try {
				b_price[i] = bDAO.selectOnePrice(beverage[i]);
			} catch (SQLException e) {
				System.out.println("SQL 예외 : "+e.getMessage());
			}
		}

		JLabel[] beverageLB = new JLabel[4];
		for(int i=0;i<beverageLB.length;i++) {
			beverageLB[i] = new JLabel("+  "+b_price[i]+" ￦");
			beverageLB[i].setFont(font1);
			drinkmenu[i] = new JCheckBox(beverage[i]);
			drinkmenu[i].setFont(font1);
			drinkmenu[i].setBackground(new Color(250,250,240));
			
			scroll.add(beverageLB[i]);
			scroll.add(drinkmenu[i]);		
			
			drink.add(drinkmenu[i]);
			scrollContent.add(drinkmenu[i]);
			scrollContent.add(beverageLB[i]);
		}

		
//패널만들기
	
	
		JPanel underPanel = new JPanel();
		underPanel.setBounds(121, 442, 326, 70);
		underPanel.setBackground(new Color(250,250,240));
		frame.getContentPane().add(underPanel);
		underPanel.setLayout(null);
		
		
//장바구니 버튼		
		JButton cartButton = new JButton("장바구니 담기");
		cartButton.setBounds(121, 650, 516, 32);
		cartButton.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
					 boolean sideChosen = sidemenu[0].isSelected() || sidemenu[1].isSelected() || sidemenu[2].isSelected() || 
							 sidemenu[3].isSelected() || sidemenu[4].isSelected();
				     boolean drinkChosen = drinkmenu[0].isSelected() || drinkmenu[1].isSelected() ||
				    		 drinkmenu[2].isSelected() || drinkmenu[3].isSelected();
				

				     if (!sideChosen || !drinkChosen) {
				            JOptionPane.showMessageDialog(null, "⚠ 사이드와 음료를 1개씩 선택해야 합니다 ⚠", "Warning", JOptionPane.ERROR_MESSAGE);
				     } else {
				    	 StringBuilder selectedSide = new StringBuilder();
				            for (JCheckBox sideMenuCheckbox : sidemenu) {
				                if (sideMenuCheckbox.isSelected()) {
				                    selectedSide.append(sideMenuCheckbox.getText());
				                }
				            }
				            String side = selectedSide.toString().trim();
				            String beverage = "";
				            for (JCheckBox drinkMenuCheckbox : drinkmenu) {
				                if (drinkMenuCheckbox.isSelected()) {
				                    beverage = drinkMenuCheckbox.getText();
				                    break;
				                }
				            }
				    	
				            String ID = login.CustomerID;
				            String hname = menu.hname;
				            String hoption = menu.hoption;
				            
				          try {
							cDAO.addAll(ID, hname, hoption, side, beverage);
						} catch (SQLException e1) {
							System.out.println("SQL 예외 : "+e1.getMessage());
						}
				            
				    	JOptionPane.showMessageDialog(null, "장바구니에 추가되었습니다");
				    	
				     	frame.dispose();
				        	}
				}
			});
	
		underPanel.add(cartButton);
		
		//프레임 사이즈 조절 X
		frame.setResizable(false);
		frame.setVisible(true);
		
		
	}

	
}