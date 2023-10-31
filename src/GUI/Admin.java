package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import DAO.MembersDAO;
import DAO.SalesHistoryDAO;
import DTO.SalesHistoryDTO;

		
public class Admin {
	
		public static void main(String[] args) {
			new Admin();
		}
	
	
		SalesHistoryDAO sDAO = SalesHistoryDAO.getSalesHistoryDAO();
		MembersDAO mDAO = MembersDAO.getMembersDAO();
		
		DefaultTableModel dm;
		JTable jt;
		String[] cols = {"아이디","매출액","날짜","고객 등급"};	
		
		JFrame frame = new JFrame();
		
		
		public Admin() {
			
		frame.setTitle("버거퀸 매출 내역");
		frame.setBounds(100, 100, 800, 800);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
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
				
/*================================= [관리자] 레이블 =================================*/
		JLabel managerLabel = new JLabel("[관리자]");
		managerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		managerLabel.setFont(new Font("HY헤드라인M", Font.BOLD, 20));
		managerLabel.setBounds(276, 140, 195, 35);
		
		imageLabel.add(managerLabel);
			
/*================================= 매출 내역 관리 콤보 박스 =================================*/
		JPanel buyPanel = new JPanel();
		buyPanel.setBorder(new TitledBorder(new LineBorder(Color.gray,3),"매출 내역"));
		buyPanel.setBounds(87, 300, 600, 400);
		buyPanel.setLayout(null);
		
		imageLabel.add(buyPanel);
			
/*============================= "단어 검색 : " 텍스트 필드 =================================*/
		JTextField searchTF = new JTextField();
		searchTF.setFont(new Font("굴림", Font.PLAIN, 16));
		searchTF.setBounds(45, 27, 226, 36);
		searchTF.setColumns(10);
		searchTF.setEnabled(false);
		buyPanel.add(searchTF);

			
/*============================= 매출 현황 콤보 박스 =================================*/
		String[] temp = {"전체 매출 조회","아이디별 매출 조회","등급별 매출 조회"};
		JComboBox<String> CBox = new JComboBox<>(temp);
		CBox.setFont(new Font("굴림", Font.PLAIN, 13));
		CBox.setBounds(283, 26, 124, 36);
		CBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(CBox.getSelectedIndex() == 0) searchTF.setEnabled(false);
				else searchTF.setEnabled(true);
			}
		});
		buyPanel.add(CBox);
			
			
/*============================== 매출 현황 조회 ===================================*/
		dm = new DefaultTableModel(null, cols);
		jt = new JTable(dm);
		
		List<SalesHistoryDTO> list;
		try {
			list = sDAO.selectAll();
			
			for(SalesHistoryDTO sDTO : list) {
				String[] rowData = new String[4];
				
		        rowData[0] = sDTO.getID();
		        rowData[1] = String.valueOf(sDTO.getTotal_price());
		        rowData[2] = sDTO.getBuy_date().toString();
		        rowData[3] = mDAO.getType(sDTO.getID());
		        
		    	dm.addRow(rowData);
		      }
		} catch (SQLException e) {
			System.out.println("SQL 예외 : "+e.getMessage());
		}
		 
		
		
		
		 JScrollPane scrollPane = new JScrollPane(jt);
			scrollPane.setBounds(32, 74, 542, 300);
			buyPanel.add(scrollPane);		
				
			
			
/*============================= 매출 조회 버튼 =================================*/
		JButton searchBtn = new JButton("검색");
		searchBtn.setBounds(472, 26, 97, 36);
		buyPanel.add(searchBtn);
		
		
		searchBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dm = new DefaultTableModel(null, cols);
				jt.setModel(dm);
				
				for (int k = dm.getRowCount()-1; k>=0; k--)
					dm.removeRow(k);
				
				String find = searchTF.getText();
				if(CBox.getSelectedIndex() == 0) {
					List<SalesHistoryDTO> list;
					try {
						list = sDAO.selectAll();
						
						for(SalesHistoryDTO sDTO : list) {
							String[] rowData = new String[4];
							
					        rowData[0] = sDTO.getID();
					        rowData[1] = String.valueOf(sDTO.getTotal_price());
					        rowData[2] = sDTO.getBuy_date().toString();
					        rowData[3] = mDAO.getType(sDTO.getID());
					        
					    	dm.addRow(rowData);
					      }
					} catch (SQLException e1) {
						System.out.println("SQL 예외 : "+e1.getMessage());
					}
				} else if (CBox.getSelectedIndex() == 1){
					List<SalesHistoryDTO> list;
					try {
					list = sDAO.selectAll();
					for(int i = 0; i<list.size(); i++) {
						if(list.get(i).getID().contains(find)&&!find.equals("")) {
						String[] rowData = new String[4];
						
						rowData[0] = list.get(i).getID();
				        rowData[1] = String.valueOf(list.get(i).getTotal_price());
				        rowData[2] = list.get(i).getBuy_date().toString();
				        rowData[3] = mDAO.getType(list.get(i).getID());
				        
				    	dm.addRow(rowData);
						}
					}
					} catch (SQLException e2) {
						System.out.println("SQL 예외 : "+e2.getMessage());
					}
				} else {
					List<SalesHistoryDTO> list;
					try {
					list = sDAO.selectAll();
					for(int i = 0; i<list.size(); i++) {
						if(mDAO.getType(list.get(i).getID()).contains(find)) {
						String[] rowData = new String[4];
						
						rowData[0] = list.get(i).getID();
				        rowData[1] = String.valueOf(list.get(i).getTotal_price());
				        rowData[2] = list.get(i).getBuy_date().toString();
				        rowData[3] = mDAO.getType(list.get(i).getID());
				        
				    	dm.addRow(rowData);
						}
					}
					} catch (SQLException e2) {
						System.out.println("SQL 예외 : "+e2.getMessage());
					}
				}
			}
		});			
		
		frame.setVisible(true);
		
	}//LogInManager() end
}//class end