package DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.CartDTO;
import OracleConnection.OracleUtility;

/*
	1. [int] addAll - 전체 메뉴 추가
	2. [int] addSideMenu - 사이드 메뉴만 추가
	3. [int] addBeverage - 음료만 추가
	4. [void] delete - 장바구니에서 삭제
	5. [int] total - 장바구니 금액 총 합
	6. [List<CartDTO>] selectAll - 장바구니 내역 출력
	7. [void] truncate - 장바구니 비우기
*/
public class CartDAO {
	
	//싱글톤
	private static CartDAO dao = new CartDAO();
	private CartDAO() {}
	public static CartDAO getCartDAO() {
		return dao;
	}
	
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//
	
	// 1. addAll - 전체 메뉴 추가
	public int addAll(String ID, String h_name, String h_option, String s_name, String b_name) throws SQLException {
		Connection conn = OracleUtility.getConnection();
		String sql = "{ call set_price(? , ? , ? , ? , ?) }";
		CallableStatement cs;
		
		cs = conn.prepareCall(sql);
		
		cs.setString(1, ID);
		cs.setString(2, h_name);
		cs.setString(3, h_option);
		cs.setString(4, s_name);
		cs.setString(5, b_name);
		
		int result = cs.executeUpdate();
		
		cs.close();
		conn.close();
		
		return result;
	}
	
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//
	
	// 2. addSideMenu - 사이드 메뉴만 추가
	public int addSideMenu(String ID, String s_name) throws SQLException {
		Connection conn = OracleUtility.getConnection();
		String sql = "{ call set_price(? , null , null , ? , null) }";
		CallableStatement cs = conn.prepareCall(sql);
		
		cs.setString(1, ID);
		cs.setString(2, s_name);
		
		int result = cs.executeUpdate();
		
		cs.close();
		conn.close();
		
		return result;
	}
	
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//
	
	// 3. addBeverage - 음료만 추가
	public int addBeverage(String ID, String b_name) throws SQLException {
		Connection conn = OracleUtility.getConnection();
		String sql = "{ call set_price(? , null , null , null , ?) }";
		CallableStatement cs = conn.prepareCall(sql);
		
		cs.setString(1, ID);
		cs.setString(2, b_name);
		
		int result = cs.executeUpdate();
		
		cs.close();
		conn.close();
		
		return result;
	}
	
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//
	
	// 4. delete - 장바구니에서 삭제
	public void delete(int c_No) throws SQLException {
		Connection conn = OracleUtility.getConnection();
		String sql = "DELETE cart WHERE c_No = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setInt(1, c_No);
		
		ps.execute();
		
		ps.close();
		conn.close();
	}
	
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//
	
	// 5. total - 장바구니 금액 총 합
	public int total() throws SQLException {
		Connection conn = OracleUtility.getConnection();
		String sql = "SELECT sum(c_price) FROM CART";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		int result = 0;
		
		if(rs.next()) result = rs.getInt(1);
		
		rs.close();
		ps.close();
		conn.close();
		
		return result;
	}
	
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//
	
	// 6. selectAll - 장바구니 내역 출력
	public List<CartDTO> selectAll() throws SQLException {
		Connection conn = OracleUtility.getConnection();
		String sql = "SELECT * FROM CART";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		List<CartDTO> list = new ArrayList<>();
		
		while(rs.next()) list.add(new CartDTO(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getInt(7)));
		
		rs.close();
		ps.close();
		conn.close();
		
		return list;
	}
	
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//
	
	// 7. truncate - 장바구니 비우기
	public void truncate() throws SQLException {
		Connection conn = OracleUtility.getConnection();
		String sql1 = "TRUNCATE TABLE CART";
		PreparedStatement ps = conn.prepareStatement(sql1);
		
		ps.execute();
		
		ps.close();
		
		conn.close();
	}
	
	
}//class end

