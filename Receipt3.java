package april26; //2022.4.26 kopo21 �Ȱ���, ������ ��� 3

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Receipt3 {
	static DecimalFormat df;
	
	static final String[] itemName = {"���ѱ� ������� 4����", "�̷α� �����K �������νø���", "�������θ��� ý������ ���ø��", "���� ���۸����� �������Ǽ�", "��Ÿ�ƽ� ���ο��μ�", "���彺Ʃ��� ���̼�����", "���ڶ� �ƿ��ũ��", "���� �ö�����̰��� �� �� ��Ǿ", "�ƽ� ��������� Ŀ�ǹͽ�", "������ ����ѿз�", "������ �÷η� EDT", "�޹� �ܹ� �Ѹ�ä�� �����", "���� Ŭ���� 1��Ʈ", "���� Ȧ��", "�Ե�Ǫ�� ������ (�õ�), 35��", "�ϰմ��� ����ư ���ݸ� (�õ�)", "����Ų��� ���̽�ũ�� �� ���ں��� 5��", "���׷� �ٳ���������, 240ml, 8��", "���� ������ ��¥���� ����ġ�� 1kg", "�ж� ����ī���� ġ��, 500g, 1��", "Dole �̱��� �ƺ�ī��, 1kg, 1��", "Dole ����Ƽ�� �ٳ���, 6��", "��� ��Ǿ ��ǻ�� 200ml 3��", "��� ��Ǿ ���߷� ���� ĵ�� 500g", "���� ���ø� �ʸ��� �ٵ����", "Ŭ����ǻ�� ������ ��ȭ�� ����ũ ����", "��Ŭ ���� ��Ƽ����¡ ������ ����� ����", "�������� ��� ���ũ�� 50ml", "�������� ��ũ�׶��� ��������ũ�� 10��", "�̴Ͻ����� ȭ����� ��� Ŭ��¡ ��"};
	static final int[] price = {2930, 5040, 4260, 102750, 22990, 9730, 13920, 30050, 23790, 24930, 24340, 2980, 25490, 8500, 16800, 9100, 10800, 8940, 11190, 10180, 20900, 13900, 18180, 17990, 8900, 15840, 26990, 31000, 62000, 7800};
	static final int[] num = {1, 2, 2, 1, 2, 2, 4, 1, 1, 1, 1, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 2};
	static final boolean[] taxFree = {false, false, false, false, false, false, false, true, false, true, true, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, true, true, false};
	
	static int SUM_TAXFREE = 0; // �鼼��ǰ ���� ��
	static int SUM_TAX = 0; // ������ǰ ���� ��
	static final float taxRate = 0.1f; // ����
	static final int POINTS = 5473; // ���� ���� ����Ʈ
	static final int DOTTED_LINE = 5; // ��ǰ��¿��� ���м��� ����� ����Ұ���
	static final int CUTLENGTH = 15; // ���ڿ� ���ϴ¸�ŭ �ڸ� ����Ʈ ����
	
	/////////////// ���� �ݾ� ���
	private static int calNetPrice(int sum) {
		int netPrice = (int)(sum / (1 + taxRate));	 // int casting���� �����ݾ� �Ҽ����� ��������	
		return netPrice;
	}
	
	/////////////// ���� �ð� ��ȯ
	private static String getNow(String textFormat) {
		LocalDateTime now = LocalDateTime.now(); // LocalDateTime ����
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(textFormat); // DateTimeFormatter ����
		String timeNow = now.format(formatter); // ���ϴ� �������� ����ð��� ���
		return timeNow;
	}
	
	/////////////// ���ϴ� ����Ʈ��ŭ ���ڿ� ���߱� (���⸦ �߰��ϰų� �ڸ��ų�)
	private static String cutString(String text) {
		String textConverted = ""; // ��ȯ�� �����
		int byteIndex = 0;
		int compareLength = CUTLENGTH - text.getBytes().length; // ���ϴ� ���̿� ���ڿ� ������ ����
		
		if (compareLength >= 0) { // �ؽ�Ʈ�� ���ϴ� ���̺��� ª�ų� �������
			textConverted = text;
			for (int i = 0; i < compareLength; i++) {
				textConverted += " "; // ª����ŭ ���⸦ �ؼ� ���� �����ش�.
			}
		} else { // �ؽ�Ʈ�� ���ϴ±��̺��� ����
			for (int i = 0; i < text.length(); i++) {
				byteIndex += String.valueOf(text.charAt(i)).getBytes().length; // ���� ������ ����Ʈ�� ���鼭	
				
				if (byteIndex == CUTLENGTH) { // ���ϴ� ���̿� �ٴٸ���
					textConverted += text.charAt(i); // ������ ���ڸ� �߰��ϰ� ����
					break;
				} else if (byteIndex > CUTLENGTH) { // ������ ���ڰ� �ѱ�(2����Ʈ) ���� 1��ŭ �ʰ��ϴ°��
					textConverted += " "; // ������ ���ڴ�� ���⸸ �߰��� ����
					break;
				}
				
				textConverted += text.charAt(i); // return�� ������� �ѱ��ھ� �߰�
			}
		}
		
		return textConverted;
	}
	
	/////////////// ù��° ��Ʈ ���
	private static void printPart1() {
		System.out.println("             �̸�Ʈ ������ (031)888-1234");
		System.out.println("    emart    206-86-50913 ����");
		System.out.println("             ���� ������ ������� 552");
		System.out.println("");
		System.out.println("������ �������� ��ȯ/ȯ�� �Ұ�");
		System.out.println("�����ǰ�� ����, 30�� �̳�(�ż� 7��)");
		System.out.println("���Ϻ� �귣����� ����(���� ����������)");
		System.out.println("��ȯ/ȯ�� ���������� ����(����ī�� ����)");
		System.out.println("");
		System.out.printf("[�� ��]%-18s%15s\n", getNow("yyyy-MM-dd HH:mm"), "POS:0011-9861"); //����ð� ���
		System.out.println("----------------------------------------");
		System.out.println("  �� ǰ ��           �� ��  ����   �� ��");
		System.out.println("----------------------------------------");
	}
	
	/////////////// ǰ�� ���� ���
	private static void printItem(String itemName, int price, int num, boolean taxFree) {
		if (taxFree == true) { // taxfree�� *ǥ��
			System.out.print("* ");
		} else {
			System.out.print("  ");
		}
		System.out.print(cutString(itemName)); // ǰ�� ���ϴ� ���̷� ���缭 ���
		System.out.printf("%10s%3s%10s\n", df.format(price), df.format(num), df.format(price * num)); // �ܰ� ���� �ݾ� ��� (�ݾ� 1000���ڸ�����, ���� 100���ڸ�����)
	}
	
	/////////////// �ι�° ��Ʈ ���
	private static void printPart2() {
		System.out.println();
		System.out.printf("%22s%13s\n", "�� ǰ�� ����", df.format(itemName.length)); // ǰ�� ����
		System.out.printf("%23s%13s\n", "(*)�� ��  �� ǰ", df.format(SUM_TAXFREE)); // �鼼ǰ ���� ��
		System.out.printf("%23s%13s\n", "�� ��  �� ǰ", df.format(calNetPrice(SUM_TAX))); // ����ǰ ���� �� (����)
		System.out.printf("%24s%13s\n", "��   ��   ��", df.format(SUM_TAX - calNetPrice(SUM_TAX))); // ����
		System.out.printf("%25s%13s\n", "��        ��", df.format(SUM_TAX + SUM_TAXFREE)); // ����
		System.out.printf("%-21s%13s\n", "�� �� �� �� �� ��", df.format(SUM_TAX + SUM_TAXFREE)); // ����
		System.out.println("----------------------------------------");
		System.out.printf("%-17s%21s\n", "0012 KEB �ϳ�", "541707**0484/35860658");
		System.out.printf("%-15s%18s\n", "ī�����(IC)", "�Ͻú� / " + df.format(SUM_TAX + SUM_TAXFREE)); // ����
		System.out.println("----------------------------------------");
		System.out.println("          [�ż�������Ʈ ����]");
		System.out.println("ȫ*�� ������ ����Ʈ ��Ȳ�Դϴ�.");
		System.out.printf("%-12s%11s%10s\n", "��ȸ�߻�����Ʈ", "9350**9995", df.format((SUM_TAX + SUM_TAXFREE) / 1000)); // ��ȸ�߻� ����Ʈ = (�����ݾ� / 1000)
		System.out.printf("%-12s%11s%10s\n", "����(����)����Ʈ", df.format((SUM_TAX + SUM_TAXFREE) / 1000  + POINTS) + "(", df.format(POINTS) + ")"); // ��������Ʈ�� ��������Ʈ ���
		System.out.println("*�ż�������Ʈ ��ȿ�Ⱓ�� 2���Դϴ�.");
		System.out.println("----------------------------------------");
		System.out.println("   ���űݾױ��� ���������ð� �ڵ��ο�");
		System.out.printf("%-21s%14s\n", "������ȣ :", "34��****");
		System.out.printf("%-17s%14s\n", "�����ð� :", getNow("yyyy-MM-dd HH:mm:ss")); // ����ð� �ʴ�������
		System.out.println("----------------------------------------");
		System.out.printf("%-23s%14s\n", "ĳ��:084599 ��00", "1150"); 
		System.out.println("   ��������������������������������������������������������������������");
		System.out.println("   ��������������������������������������������������������������������");
		System.out.printf("      %s/00119861/00164980/31", getNow("yyyyMMdd")); // ���ڵ�� ���ڿ� ���ó�¥ ����
	}
	
	/////////////// ���� �޼ҵ�
	public static void main(String[] args) {
		df = new DecimalFormat("###,###,###,###,###"); // 3�ڸ����� �޸���� ����
		
		printPart1();
		
		for (int itemIndex = 0; itemIndex < itemName.length; itemIndex++) { // ǰ�� ���
			printItem(itemName[itemIndex], price[itemIndex], num[itemIndex], taxFree[itemIndex]); // ǰ�� ���� ��� (��ǰ��, �ܰ�, ����, �ݾ�)
			
			if (taxFree[itemIndex] == true) { // �� ���� ��� (�鼼��ǰ, ������ǰ ���ε���)
				SUM_TAXFREE += price[itemIndex] * num[itemIndex];
			} else {
				SUM_TAX += price[itemIndex] * num[itemIndex];
			}
			if (itemIndex % DOTTED_LINE == DOTTED_LINE - 1) { // ���м� ���
				System.out.println("----------------------------------------");
			}
		}
		
		printPart2();
	}

}
