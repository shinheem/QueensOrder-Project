package DTO;

import java.sql.Date;

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
public class SalesHistoryDTO {
	private String ID;
	private int total_price;
	private Date buy_date;

	
	@Override
	public String toString() {
		return String.format("ID : %s, 총 결제 금액 : %d, 구매일자 : %s",
				ID, total_price, buy_date);
	}
	
}//class end
