package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.HamburgerDTO;
import OracleConnection.OracleUtility;

/*
	1. [List<BeverageDTO>] selectAll() - 전체 데이터 출력
	2. [List<String>]  selectAllName() - 전체 이름 출력
	3. [List<Integer>] selectAllPrice() - 전체 가격 출력
	4. [int] selectOnePrice() - 한가지 메뉴 가격 출력
*/
public class HamburgerDAO {
	
	//싱글톤
	private static HamburgerDAO dao = new HamburgerDAO();
	private HamburgerDAO() {}
	public static HamburgerDAO getHamburgerDAO() {
		return dao;
	}
	
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//
	
	// 1. selectAll() - 전체 데이터 출력
	public List<HamburgerDTO> selectAll() throws SQLException{
		Connection conn = OracleUtility.getConnection();
		String sql = "SELECT * FROM HAMBURGER ORDER BY h_name";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		List<HamburgerDTO> list = new ArrayList<>();
		
		while(rs.next()) list.add(new HamburgerDTO(rs.getString(1),rs.getString(2),rs.getInt(3)));
		
		rs.close();
		ps.close();
		conn.close();
		
		return list;
	}
	
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//	
	
	// 2. selectAllName() - 전체 이름 출력
	public List<String> selectAllName() throws SQLException{
		Connection conn = OracleUtility.getConnection();
		String sql = "SELECT DISTINCT h_name FROM HAMBURGER ORDER BY h_name";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		List<String> list = new ArrayList<>();
		
		while(rs.next()) list.add(rs.getString(1));
		
		rs.close();
		ps.close();
		conn.close();
		
		return list;
	}
	
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//	
	
	// 3. selectAllPrice() - 전체 가격 출력
	public List<Integer> selectAllPrice() throws SQLException{
		Connection conn = OracleUtility.getConnection();
		String sql = "SELECT h_price FROM HAMBURGER ORDER BY h_name";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		List<Integer> list = new ArrayList<>();
		
		while(rs.next()) list.add(rs.getInt(1));
		
		rs.close();
		ps.close();
		conn.close();
		
		return list;
	}
	
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//	
	
	// 4. selectOnePrice() - 한가지 메뉴 가격 출력
	public int selectOnePrice(String h_name, String h_option) throws SQLException {
		Connection conn = OracleUtility.getConnection();
		String sql = "SELECT h_price FROM HAMBURGER WHERE h_name = ? AND h_option = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setString(1, h_name);
		ps.setString(2, h_option);
		
		ResultSet rs = ps.executeQuery();
		int result = 0;
		
		if(rs.next()) result = rs.getInt(1);
		
		rs.close();
		ps.close();
		conn.close();
		
		return result;
	}
	
	
}//class end
