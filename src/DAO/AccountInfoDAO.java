package DAO;

import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.common.hash.Hashing;

import DTO.MembersDTO;
import OracleConnection.OracleUtility;

/*
	1. [String] join - 회원가입
	2. [boolean] isExistID - 해당 회원 아이디 존재 여부
	3. [String] findID - 아이디 찾기
	4. [boolean] isExistPW - 해당 회원 비밀번호 존재 여부
	5. [String] findPW - 비밀번호 찾기
*/
public class AccountInfoDAO {
	
	//싱글톤
	private static AccountInfoDAO dao = new AccountInfoDAO();
	private AccountInfoDAO() {}
	public static AccountInfoDAO getAccountInfoDAO() {
		return dao;
	}
	
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//
	
	// 1. join - 회원가입
	public String join(MembersDTO dto) throws SQLException {
		String hashPW = Hashing.sha256()
						.hashString(dto.getPW(), StandardCharsets.UTF_8)
						.toString();
		String name = dto.getM_name();
		
		Connection conn = OracleUtility.getConnection();
		String sql = "{ call back_up(? , ? , ? , ? , ? , ?) }";
		CallableStatement cs = conn.prepareCall(sql);
		
		cs.setString(1, dto.getID());
		cs.setString(2, dto.getPW());
		cs.setString(3, hashPW);
		cs.setString(4, dto.getM_name());
		cs.setString(5, dto.getPhone());
		cs.setString(6, dto.getAddress());
		
		cs.execute();
		
		cs.close();
		conn.close();
		
		return name;
	}
	
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//
	
	// 2. isExistID - 해당 회원 아이디 존재 여부
	public boolean isExistID(String name, String phone) throws SQLException {
		Connection conn = OracleUtility.getConnection();
		String sql = "SELECT count(ID) FROM ACCOUNTINFO WHERE m_name = ? AND phone = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setString(1, name);
		ps.setString(2, phone);
		
		ResultSet rs = ps.executeQuery();
		int rst = 0;
		boolean result = false;
		
		if(rs.next()) rst = rs.getInt(1);
		
		if(rst == 0) result = false;
		if(rst == 1) result = true;
		return result;
	}
	
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//
	
	// 3. findID - 아이디 찾기
	public String findID(String name, String phone) throws SQLException {
		Connection conn = OracleUtility.getConnection();
		String sql = "SELECT ID FROM ACCOUNTINFO WHERE m_name = ? AND phone = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setString(1, name);
		ps.setString(2, phone);
		
		ResultSet rs = ps.executeQuery();
		String result = null;
		
		if(rs.next()) result = rs.getString(1);
		
		
		ps.close();
		conn.close();
		
		return result;
	}
	
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//
	
	// 4. isExistPW - 해당 회원 비밀번호 존재 여부
	public boolean isExistPW(String name, String phone, String ID) throws SQLException {
		Connection conn = OracleUtility.getConnection();
		String sql = "SELECT count(ID) FROM ACCOUNTINFO WHERE m_name = ? AND phone = ? AND ID = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setString(1, name);
		ps.setString(2, phone);
		ps.setString(3, ID);
		
		ResultSet rs = ps.executeQuery();
		int rst = 0;
		boolean result = false;
		
		if(rs.next()) rst = rs.getInt(1);
		
		if(rst == 0) result = false;
		if(rst == 1) result = true;
		return result;
	}
	
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//
	
	// 5. findPW - 비밀번호 찾기
	public String findPW(String name, String phone, String ID) throws SQLException {
		Connection conn = OracleUtility.getConnection();
		String sql = "SELECT PW FROM ACCOUNTINFO WHERE m_name = ? AND phone = ? AND ID = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setString(1, name);
		ps.setString(2, phone);
		ps.setString(3, ID);
		
		ResultSet rs = ps.executeQuery();
		String result = null;
		
		if(rs.next()) result = rs.getString(1);
		
		rs.close();
		ps.close();
		conn.close();
		
		return result;
	}
	
	
}//class end
