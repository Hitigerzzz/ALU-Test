
public class Test {
    public static void main(String[] args) {
		ALU alu=new ALU();
//		System.out.println(alu.integerDivision("1000", "1111",4));//   -6/3
//		System.out.println("-6/3"+" "+alu.integerDivision("1010", "0011",4));//   -6/3
//		System.out.println("6/3"+" "+alu.integerDivision("0110", "0011",4));//    6/3 ok
//		System.out.println("-6/-3"+" "+alu.integerDivision("1010", "1101",4)); //  -6/-3 ok
//		System.out.println("6/-3"+" "+alu.integerDivision("0110", "1101", 4));//  6/-3 ok
//		System.out.println("-6/-2"+" "+alu.integerDivision("1010", "1110", 4));
//		System.out.println("6/2"+" "+alu.integerDivision("0110", "0010", 4));
//		System.out.println("6/6"+" "+alu.integerDivision("0110", "0001  ", 4));
//		System.out.println(alu.floatRepresentation("0.0146484375", 4,4));
//		//原码加法
//		System.out.println(alu.signedAddition("1101", "1101", 4));//000110
//		System.out.println(alu.signedAddition("0100", "0010", 4));//000110
//		System.out.println(alu.signedAddition("0111", "0001", , 4));//010011
//		System.out.println(alu.signedAddition("0100", "1101", 4));//010001
//		System.out.println(alu.signedAddition("0111", "1100", 4));//000011
//		System.out.println(alu.signedAddition("1100", "1101", 4));//111001
//		System.out.println(alu.signedAddition("0001", "1010", 4));
//		System.out.println(alu.floatDivision(
//						"0"	+ "00000001"
//								+ "11000000000000000000000",
//						"0"
//						+ "10000000"
//						+ "00000000000000000000000", 
//						8, 23));
//		System.out.println("00"
//				+ "00000000"
//				+ "11100000000000000000000");
		//除法
//		System.out.println(alu.floatDivision(
//				"0"	+ "00000001"
//						+ "00000000000000000000000",
//				"0"
//				+ "10000000"
//				+ "11000000000000000000000", 
//				8, 23));
//		System.out.println("00"
//				+ "00000000"
//				+ "01001001001001001001001");
//		System.out.println(alu.floatTrueValue("00000000000000000000000000000010",8, 23));
		System.out.println(alu.integerDivision("0111", "0011", 8));
		System.out.println(alu.integerDivision("0111", "1101", 8));
		System.out.println(alu.integerDivision("1001", "0011", 8));
		System.out.println(alu.integerDivision("1001", "1101", 8));
		System.out.println(alu.integerDivision("0110", "0010", 8));
		System.out.println(alu.integerDivision("1010", "1110", 8));
		System.out.println(alu.integerDivision("0110", "1110", 8));
		System.out.println(alu.integerDivision("1010", "0010", 8));
	}
        
}
