package DAO;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.common.hash.Hashing;

import DTO.MembersDTO;
import OracleConnection.OracleUtility;

/*
	1. [String]  join - 회원가입 ▶ 사용 안함
	2. [boolean] canLogin - ID,PW 일치여부(로그인 가능여부)
	3. [boolean] isDuplicate - ID 중복 확인
	4. [boolean] isManager - 관리자 여부 확인
	5. [String] getName - ID에 해당하는 이름 가져오기
	6. [String] getType - ID에 해당하는 등급 가져오기
	7. [boolean] isDuplicate - 전화번호 중복 확인
	8. [MembersDTO] memberInfo - 회원 정보 가져오기
*/
public class MembersDAO {
	
	//싱글톤
	private static MembersDAO dao = new MembersDAO();
	private MembersDAO() {}
	public static MembersDAO getMembersDAO() {
		return dao;
	}
	
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//
	
	// 1. join - 회원가입
//	public String join (MembersDTO dto) throws SQLException {
//		String pw = Hashing.sha256()
//					.hashString(dto.getPW(), StandardCharsets.UTF_8)
//					.toString();
//		String name = dto.getM_name();
//		
//		Connection conn = OracleUtility.getConnection();
//		String sql = "INSERT INTO MEMBERS VALUES (? , ? , ? , ? , ? , 일반회원)";
//		PreparedStatement ps = conn.prepareStatement(sql);
//		
//		ps.setString(1, dto.getID());
//		ps.setString(2, pw);
//		ps.setString(3, name);
//		ps.setString(4, dto.getPhone());
//		ps.setString(5, dto.getAddress());
//		
//		ps.execute();
//		
//		ps.close();
//		conn.close();
//		
//		return name;
//	}
	
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//
	
	// 2. canLogin - ID,PW 일치여부(로그인 가능여부)
	public boolean canLogin(String ID, String PW) throws SQLException {
		String pw = Hashing.sha256()
				.hashString(PW, StandardCharsets.UTF_8)
				.toString();
		
		Connection conn = OracleUtility.getConnection();
		String sql = "SELECT CASE WHEN EXISTS ("
				+ "	SELECT 1"
				+ "	FROM MEMBERS"
				+ "	WHERE ID = ? AND PW = ?)"
				+ " THEN 1 ELSE 0 END AS exists_flag"
				+ " FROM dual";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setString(1, ID);
		ps.setString(2, pw);
		
		ResultSet rs = ps.executeQuery();
		boolean result = false;
		int rst = 0;
		
		if(rs.next()) rst = rs.getInt("exists_flag");
		
		if(rst == 1) result = true;
		if(rst == 0) result = false;
		
		rs.close();
		ps.close();
		conn.close();
		
		return result;
	}
	
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//
	
	// 3. isDuplicate - ID 중복 확인
	public boolean isDuplicate(String ID) throws SQLException {
		Connection conn = OracleUtility.getConnection();
		String sql = "SELECT count(ID)"
				+ " FROM MEMBERS"
				+ " WHERE id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setString(1, ID);
		
		ResultSet rs = ps.executeQuery();
		boolean result = false;
		int rst = 0;
		
		if(rs.next()) rst = rs.getInt(1);
		
		if(rst == 1) result = true;
		if(rst == 0) result = false;
		
		rs.close();
		ps.close();
		conn.close();
		
		return result;
	}
	
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//
	
	// 4. isManager - 관리자 여부 확인
	public boolean isManager (String ID, String PW) throws SQLException {
		String pw = Hashing.sha256()
				.hashString(PW, StandardCharsets.UTF_8)
				.toString();
		
		Connection conn = OracleUtility.getConnection();
		String sql = "SELECT CASE WHEN EXISTS ("
				+ "	SELECT ID"
				+ "	FROM MEMBERS"
				+ "	WHERE ID = ? AND PW = ? AND m_type = '관리자')"
				+ " THEN 1 ELSE 0 END AS exists_flag"
				+ " FROM dual";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setString(1, ID);
		ps.setString(2, pw);
		
		ResultSet rs = ps.executeQuery();
		boolean result = false;
		int rst = 0;
		
		if(rs.next()) rst = rs.getInt("exists_flag");
		
		if(rst == 1) result = true;
		if(rst == 0) result = false;
		
		rs.close();
		ps.close();
		conn.close();
		
		return result;
	}
	
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//
	
	// 5. getName - ID에 해당하는 이름 가져오기
	public String getName(String ID) throws SQLException {
		Connection conn = OracleUtility.getConnection();
		String sql = "SELECT m_name FROM MEMBERS WHERE ID = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setString(1, ID);
		
		ResultSet rs = ps.executeQuery();
		String result = null;
		
		if(rs.next()) result = rs.getString(1);
		
		rs.close();
		ps.close();
		conn.close();
		
		return result;
	}
	
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//
	
	// 6. getType - ID에 해당하는 등급 가져오기
	public String getType(String ID) throws SQLException {
		Connection conn = OracleUtility.getConnection();
		String sql = "SELECT m_type FROM MEMBERS WHERE ID = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setString(1, ID);
		
		ResultSet rs = ps.executeQuery();
		String result = null;
		
		if(rs.next()) result = rs.getString(1);
		
		rs.close();
		ps.close();
		conn.close();
		
		return result;
	}
	
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//
	
	//7. [boolean] isDuplicate - 전화번호 중복 확인
	public boolean isDuplicate(String phone, String insertNull) throws SQLException {
		Connection conn = OracleUtility.getConnection();
		String sql = "SELECT count(ID)"
				+ " FROM MEMBERS"
				+ " WHERE phone = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setString(1, phone);
		
		ResultSet rs = ps.executeQuery();
		boolean result = false;
		int rst = 0;
		
		if(rs.next()) rst = rs.getInt(1);
		
		if(rst == 1) result = true;
		if(rst == 0) result = false;
		
		rs.close();
		ps.close();
		conn.close();
		
		return result;
	}

//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//
		
	//8. memberInfo - 회원 정보 가져오기
	public MembersDTO memberInfo(String ID) throws SQLException {
		Connection conn = OracleUtility.getConnection();
		String sql = "SELECT m_name,phone,address FROM MEMBERS WHERE id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setString(1, ID);
		
		ResultSet rs = ps.executeQuery();
		MembersDTO result = null;
		
		if(rs.next()) result = new MembersDTO(ID,"비밀번호 접근 불가",rs.getString(1),rs.getString(2),rs.getString(3),"회원등급 접근 불가");
		
		rs.close();
		ps.close();
		conn.close();
		
		return result;
	}
	

}//class end
