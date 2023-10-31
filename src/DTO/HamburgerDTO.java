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
public class HamburgerDTO {
	private String h_name;
	private String h_option;
	private int h_price;
	
	
	@Override
	public String toString() {
		return String.format("햄버거 이름 : %s, 옵션 : %s, 가격 : %d",
				h_name, h_option, h_price);
	}
	
}//class end
