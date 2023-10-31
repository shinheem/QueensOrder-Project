package DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.SalesHistoryDTO;
import OracleConnection.OracleUtility;

/*
	1. [void] update - 매출 현황에 판매 내역 추가
	2. [List<SalesHistoryDTO>] selectAll - 전체 내용 출력
	3. [List<SalesHistoryDTO>] selectID - ID별 판매내역 출력
*/
public class SalesHistoryDAO {
	
	//싱글톤
	private static SalesHistoryDAO dao = new SalesHistoryDAO();
	private SalesHistoryDAO() {}
	public static SalesHistoryDAO getSalesHistoryDAO() {
		return dao;
	}
	
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//
	
	// 1. update - 매출 현황에 판매 내역 추가
	public void update(String ID, int total) throws SQLException {
		Connection conn = OracleUtility.getConnection();
		String sql = "{ call set_m_type(? , ?) }";
		CallableStatement cs = conn.prepareCall(sql);
		
		cs.setString(1, ID);
		cs.setInt(2, total);
		
		cs.execute();
		
		cs.close();
		conn.close();
	}
	
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//
	
	// 2. selectAll - 전체 내용 출력
	public List<SalesHistoryDTO> selectAll() throws SQLException {
		Connection conn = OracleUtility.getConnection();
		String sql = "SELECT * FROM SALESHISTORY";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		List<SalesHistoryDTO> list = new ArrayList<SalesHistoryDTO>();
		
		while(rs.next()) list.add(new SalesHistoryDTO(rs.getString(1),rs.getInt(2),rs.getDate(3)));
		
		rs.close();
		ps.close();
		conn.close();
		
		return list;
	}
	
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//
	
	// 3. selectID - ID별 판매내역 출력
	public List<SalesHistoryDTO> selectDate(String ID) throws SQLException {
		Connection conn = OracleUtility.getConnection();
		String sql = "SELECT * FROM SALESHISTORY WHERE ID = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setString(1, ID);
		
		ResultSet rs = ps.executeQuery();
		List<SalesHistoryDTO> list = new ArrayList<SalesHistoryDTO>();
		
		while(rs.next()) list.add(new SalesHistoryDTO(rs.getString(1),rs.getInt(2),rs.getDate(3)));
		
		rs.close();
		ps.close();
		conn.close();
		
		return list;
	}
	
	
}//class end
