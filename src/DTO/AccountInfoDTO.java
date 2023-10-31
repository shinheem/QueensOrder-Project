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
public class AccountInfoDTO {
	private String ID;
	private String PW;
	private String m_name;
	private String phone;
	
	
	@Override
	public String toString() {
		return String.format("ID : %s, PW : %s, 회원 이름 : %s, 전화번호 : %s",
				ID, PW, m_name, phone);
	}
}
