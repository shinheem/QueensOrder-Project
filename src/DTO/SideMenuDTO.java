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
public class SideMenuDTO {
	private String s_name;
	private int s_price;
	
	
	@Override
	public String toString() {
		return String.format("사이드 메뉴 이름 : %s, 가격 : %d",
				s_name, s_price);
	}
	
}//class end
