package april26; //2022.4.26 kopo21 안계현, 영수증 찍기 3

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Receipt3 {
	static DecimalFormat df;
	
	static final String[] itemName = {"오뚜기 진비빔면 4개입", "켈로그 스페셜K 오리지널시리얼", "오곡으로만든 첵스초코 마시멜로", "레고 슈퍼마리오 루이지맨션", "비타맥스 올인원로션", "비비드스튜디오 아이섀도우", "선텔라 아우라선크림", "겐조 플라워바이겐조 오 드 퍼퓸", "맥심 슈프림골드 커피믹스", "지미추 오드뚜왈렛", "지미추 플로럴 EDT", "햇반 솥반 뿌리채소 영양밥", "스팸 클래식 1세트", "블랜드 홀빈", "롯데푸드 빠삐코 (냉동), 35개", "하겐다즈 파이튼 초콜릿 (냉동)", "베스킨라빈스 아이스크림 콘 초코봉봉 5입", "빙그레 바나나맛우유, 240ml, 8개", "곰곰 슈레드 모짜렐라 피자치즈 1kg", "밀라 마스카포네 치즈, 500g, 1개", "Dole 미국산 아보카도, 1kg, 1팩", "Dole 스위티오 바나나, 6팩", "쿤달 퍼퓸 디퓨저 200ml 3개", "쿤달 퍼퓸 내추럴 소이 캔들 500g", "도브 딥플리 너리싱 바디워시", "클린앤퓨어 오가닉 목화솜 마스크 대형", "링클 스팟 안티에이징 에센스 리페어 세럼", "르오에스 비건 톤업크림 50ml", "르오에스 핑크테라피 수딩마스크팩 10매", "이니스프리 화산송이 모공 클렌징 폼"};
	static final int[] price = {2930, 5040, 4260, 102750, 22990, 9730, 13920, 30050, 23790, 24930, 24340, 2980, 25490, 8500, 16800, 9100, 10800, 8940, 11190, 10180, 20900, 13900, 18180, 17990, 8900, 15840, 26990, 31000, 62000, 7800};
	static final int[] num = {1, 2, 2, 1, 2, 2, 4, 1, 1, 1, 1, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 2};
	static final boolean[] taxFree = {false, false, false, false, false, false, false, true, false, true, true, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, true, true, false};
	
	static int SUM_TAXFREE = 0; // 면세물품 가격 합
	static int SUM_TAX = 0; // 과세물품 가격 합
	static final float taxRate = 0.1f; // 세율
	static final int POINTS = 5473; // 현재 가용 포인트
	static final int DOTTED_LINE = 5; // 상품출력에서 구분선을 몇개마다 출력할건지
	static final int CUTLENGTH = 15; // 문자열 원하는만큼 자를 바이트 길이
	
	/////////////// 세전 금액 계산
	private static int calNetPrice(int sum) {
		int netPrice = (int)(sum / (1 + taxRate));	 // int casting으로 세전금액 소수점을 버림해줌	
		return netPrice;
	}
	
	/////////////// 현재 시간 반환
	private static String getNow(String textFormat) {
		LocalDateTime now = LocalDateTime.now(); // LocalDateTime 선언
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(textFormat); // DateTimeFormatter 선언
		String timeNow = now.format(formatter); // 원하는 포맷으로 현재시간을 출력
		return timeNow;
	}
	
	/////////////// 원하는 바이트만큼 문자열 맞추기 (띄어쓰기를 추가하거나 자르거나)
	private static String cutString(String text) {
		String textConverted = ""; // 반환할 결과값
		int byteIndex = 0;
		int compareLength = CUTLENGTH - text.getBytes().length; // 원하는 길이와 문자열 길이의 차이
		
		if (compareLength >= 0) { // 텍스트가 원하는 길이보다 짧거나 같을경우
			textConverted = text;
			for (int i = 0; i < compareLength; i++) {
				textConverted += " "; // 짧은만큼 띄어쓰기를 해서 줄을 맞춰준다.
			}
		} else { // 텍스트가 원하는길이보다 길경우
			for (int i = 0; i < text.length(); i++) {
				byteIndex += String.valueOf(text.charAt(i)).getBytes().length; // 각각 글자의 바이트를 세면서	
				
				if (byteIndex == CUTLENGTH) { // 원하는 길이에 다다르면
					textConverted += text.charAt(i); // 마지막 글자를 추가하고 종료
					break;
				} else if (byteIndex > CUTLENGTH) { // 마지막 글자가 한글(2바이트) 여서 1만큼 초과하는경우
					textConverted += " "; // 마지막 글자대신 띄어쓰기만 추가후 종료
					break;
				}
				
				textConverted += text.charAt(i); // return할 결과값에 한글자씩 추가
			}
		}
		
		return textConverted;
	}
	
	/////////////// 첫번째 파트 출력
	private static void printPart1() {
		System.out.println("             이마트 죽전점 (031)888-1234");
		System.out.println("    emart    206-86-50913 강희석");
		System.out.println("             용인 수지구 포은대로 552");
		System.out.println("");
		System.out.println("영수증 미지참시 교환/환불 불가");
		System.out.println("정상상품에 한함, 30일 이내(신선 7일)");
		System.out.println("※일부 브랜드매장 제외(매장 고지물참조)");
		System.out.println("교환/환불 구매점에서 가능(결제카드 지참)");
		System.out.println("");
		System.out.printf("[구 매]%-18s%15s\n", getNow("yyyy-MM-dd HH:mm"), "POS:0011-9861"); //현재시간 출력
		System.out.println("----------------------------------------");
		System.out.println("  상 품 명           단 가  수량   금 액");
		System.out.println("----------------------------------------");
	}
	
	/////////////// 품명 한줄 출력
	private static void printItem(String itemName, int price, int num, boolean taxFree) {
		if (taxFree == true) { // taxfree면 *표시
			System.out.print("* ");
		} else {
			System.out.print("  ");
		}
		System.out.print(cutString(itemName)); // 품명 원하는 길이로 맞춰서 출력
		System.out.printf("%10s%3s%10s\n", df.format(price), df.format(num), df.format(price * num)); // 단가 수량 금액 출력 (금액 1000만자리까지, 수량 100의자리까지)
	}
	
	/////////////// 두번째 파트 출력
	private static void printPart2() {
		System.out.println();
		System.out.printf("%22s%13s\n", "총 품목 수량", df.format(itemName.length)); // 품목 수량
		System.out.printf("%23s%13s\n", "(*)면 세  물 품", df.format(SUM_TAXFREE)); // 면세품 가격 합
		System.out.printf("%23s%13s\n", "과 세  물 품", df.format(calNetPrice(SUM_TAX))); // 과세품 가격 합 (세전)
		System.out.printf("%24s%13s\n", "부   가   세", df.format(SUM_TAX - calNetPrice(SUM_TAX))); // 세금
		System.out.printf("%25s%13s\n", "합        계", df.format(SUM_TAX + SUM_TAXFREE)); // 총합
		System.out.printf("%-21s%13s\n", "결 제 대 상 금 액", df.format(SUM_TAX + SUM_TAXFREE)); // 총합
		System.out.println("----------------------------------------");
		System.out.printf("%-17s%21s\n", "0012 KEB 하나", "541707**0484/35860658");
		System.out.printf("%-15s%18s\n", "카드결제(IC)", "일시불 / " + df.format(SUM_TAX + SUM_TAXFREE)); // 총합
		System.out.println("----------------------------------------");
		System.out.println("          [신세계포인트 적립]");
		System.out.println("홍*두 고객님의 포인트 현황입니다.");
		System.out.printf("%-12s%11s%10s\n", "금회발생포인트", "9350**9995", df.format((SUM_TAX + SUM_TAXFREE) / 1000)); // 금회발생 포인트 = (결제금액 / 1000)
		System.out.printf("%-12s%11s%10s\n", "누계(가용)포인트", df.format((SUM_TAX + SUM_TAXFREE) / 1000  + POINTS) + "(", df.format(POINTS) + ")"); // 가용포인트와 누계포인트 출력
		System.out.println("*신세계포인트 유효기간은 2년입니다.");
		System.out.println("----------------------------------------");
		System.out.println("   구매금액기준 무료주차시간 자동부여");
		System.out.printf("%-21s%14s\n", "차량번호 :", "34저****");
		System.out.printf("%-17s%14s\n", "입차시간 :", getNow("yyyy-MM-dd HH:mm:ss")); // 현재시간 초단위포함
		System.out.println("----------------------------------------");
		System.out.printf("%-23s%14s\n", "캐셔:084599 양00", "1150"); 
		System.out.println("   ││││││││││││││││││││││││││││││││││");
		System.out.println("   ││││││││││││││││││││││││││││││││││");
		System.out.printf("      %s/00119861/00164980/31", getNow("yyyyMMdd")); // 바코드밑 숫자에 오늘날짜 포함
	}
	
	/////////////// 메인 메소드
	public static void main(String[] args) {
		df = new DecimalFormat("###,###,###,###,###"); // 3자리마다 콤마찍기 선언
		
		printPart1();
		
		for (int itemIndex = 0; itemIndex < itemName.length; itemIndex++) { // 품목 출력
			printItem(itemName[itemIndex], price[itemIndex], num[itemIndex], taxFree[itemIndex]); // 품목 한줄 출력 (상품명, 단가, 수량, 금액)
			
			if (taxFree[itemIndex] == true) { // 총 가격 계산 (면세물품, 과세물품 따로따로)
				SUM_TAXFREE += price[itemIndex] * num[itemIndex];
			} else {
				SUM_TAX += price[itemIndex] * num[itemIndex];
			}
			if (itemIndex % DOTTED_LINE == DOTTED_LINE - 1) { // 구분선 출력
				System.out.println("----------------------------------------");
			}
		}
		
		printPart2();
	}

}
