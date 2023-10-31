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
public class BeverageDTO {
	private String b_name;
	private int b_price;
	
	
	@Override
	public String toString() {
		return String.format("음료 이름 : %s, 가격 : %d",
				b_name, b_price);
	}
	
}//class end
