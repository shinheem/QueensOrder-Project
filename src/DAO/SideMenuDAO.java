package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.SideMenuDTO;
import OracleConnection.OracleUtility;

/*
	1. [List<BeverageDTO>] selectAll() - 전체 데이터 출력
	2-1-1. [List<String>]  selectSingleName1() - 단품 이름 전체 출력 (없음 포함 - 옵션 화면용)
	2-1-2. [List<String>]  selectSingleName2() - 단품 이름 전체 출력 (없음 미포함 - 메뉴 화면용)
	2-2. [List<String>]  selectSetName() - 세트 이름 전체 출력
	3-1. [List<Integer>] selectSinglePrice() - 단품 가격 전체 출력
	3-2. [List<Integer>] selectSetPrice() - 세트 가격 전체 출력
	4. [int] selectOnePrice() - 한가지 메뉴 가격 출력
*/
public class SideMenuDAO {
	
	//싱글톤
	private static SideMenuDAO dao = new SideMenuDAO();
	private SideMenuDAO() {}
	public static SideMenuDAO getSideMenuDAO() {
		return dao;
	}
	
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//
	
	// 1. selectAll() - 전체 데이터 출력
	public List<SideMenuDTO> selectAll() throws SQLException{
		Connection conn = OracleUtility.getConnection();
		String sql = "SELECT * FROM SIDEMENU";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		List<SideMenuDTO> list = new ArrayList<>();
		
		while(rs.next()) list.add(new SideMenuDTO(rs.getString(1),rs.getInt(2)));
		
		rs.close();
		ps.close();
		conn.close();
		
		return list;
	}
	
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//
	
	// 2-1-1. selectSingleName() - 단품 이름 전체 출력 (없음 포함 - 옵션 화면용)
	public List<String> selectSingleName1() throws SQLException{
		Connection conn = OracleUtility.getConnection();
		String sql = "SELECT s_name FROM SIDEMENU WHERE s_name NOT LIKE ('SET%')";
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
	
	// 2-1-2. selectSingleName() - 단품 이름 전체 출력 (없음 미포함 - 메뉴 화면용)
	public List<String> selectSingleName2() throws SQLException{
		Connection conn = OracleUtility.getConnection();
		String sql = "SELECT s_name FROM SIDEMENU WHERE s_name NOT LIKE ('SET%') AND s_name != '없음'";
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
	
	// 2-2. selectSetName() - 세트 이름 전체 출력
	public List<String> selectSetName() throws SQLException{
		Connection conn = OracleUtility.getConnection();
		String sql = "SELECT s_name FROM SIDEMENU WHERE s_name LIKE ('SET%')";
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
	
	// 3-1. selectSinglePrice() - 단품 가격 전체
	public List<Integer> selectSinglePrice() throws SQLException{
		Connection conn = OracleUtility.getConnection();
		String sql = "SELECT s_price FROM SIDEMENU WHERE s_name NOT LIKE ('SET%')";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		List<Integer> list = new ArrayList<>();
		list.add(0);
		while(rs.next()) list.add(rs.getInt(1));
		
		rs.close();
		ps.close();
		conn.close();
		
		return list;
	}
	
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//
	
	// 3-2. selectSetPrice() - 세트 가격 전체 출력
	public List<Integer> selectSetPrice() throws SQLException{
		Connection conn = OracleUtility.getConnection();
		String sql = "SELECT s_price FROM SIDEMENU WHERE s_name LIKE ('SET%')";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		List<Integer> list = new ArrayList<>();
		list.add(0);
		while(rs.next()) list.add(rs.getInt(1));
		
		rs.close();
		ps.close();
		conn.close();
		
		return list;
	}

//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//
	
	// 4. selectOnePrice() - 한가지 메뉴 가격 출력
	public int selectOnePrice(String s_name) throws SQLException {
		Connection conn = OracleUtility.getConnection();
		String sql = "SELECT s_price FROM SIDEMENU WHERE s_name = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setString(1, s_name);
		
		ResultSet rs = ps.executeQuery();
		int result = 0;
		
		if(rs.next()) result = rs.getInt(1);
		
		rs.close();
		ps.close();
		conn.close();
		
		return result;
	}
	

	

	
	
}//class end
