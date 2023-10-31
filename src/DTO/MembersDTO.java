package DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembersDTO {
	private String ID;
	private String PW;
	private String m_name;
	private String phone;
	private String address;
	private String m_type;
	
	
	@Override
	public String toString() {
		return String.format("ID : %s, PW : %s, 이름 : %s, 전화번호 : %s, 주소 : %s, 회원 등급 : %s",
				ID, PW, m_name, phone, address, m_type);
	}
	
}//class end
