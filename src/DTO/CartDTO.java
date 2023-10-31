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
public class CartDTO {
	private int c_No;
	private String ID;
	private String h_name;
	private String h_option;
	private String s_name;
	private String b_name;
	private int c_price;
	
	
	@Override
	public String toString() {
		return String.format("카트 번호 : %d, ID : %s, 햄버거 이름 : %s, 햄버거 옵션 : %s, 사이드 메뉴 이름 : %s, 음료 이름 : %s, 금액 : %d",
				c_No, ID, h_name, h_option, s_name, b_name, c_price);
	}
	
}//class end
