
/**
 * 模拟ALU进行整数和浮点数的四则运算
 * @author [请将此处修改为“151250163_吴志成”]
 *
 */

public class ALU {

	/**
	 * 生成十进制整数的二进制补码表示。<br/>
	 * 例：integerRepresentation("9", 8)
	 * @param number 十进制整数。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param length 二进制补码表示的长度
	 * @return number的二进制补码表示，长度为length
	 */
	public String integerRepresentation (String number, int length) {
		long  num=Long.parseLong(number);
		char[] nums=new char[length];
		for (int i = 0; i <nums.length; i++) {
			nums[i]='0';
		}
        if(num<0){
        	num=-num;
        }
		for (int i = length-1; i > -1; i--) {
			nums[i]=(char)(num%2+48);
			num=num/2;
		}
		if(number.charAt(0)=='-'){
			//每位取反
			for (int i = 0; i < nums.length; i++) {
				nums[i]=not(nums[i]);
			}
			return oneAdder(String.valueOf(nums)).substring(1);
		}
		return String.valueOf(nums);
	}
	
	/**
	 * 生成十进制浮点数的二进制表示。
	 * 需要考虑 0、反规格化、正负无穷（“+Inf”和“-Inf”）、 NaN等因素，具体借鉴 IEEE 754。
	 * 舍入策略为向0 舍入。<br/>
	 * 例：floatRepresentation("11.375", 8, 11)
	 * @param number 十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位（可能为不包含小数点的整数，例如 5）。
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return number的二进制表示，长度为 1+eLength+sLength。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
	 */
	public String floatRepresentation (String number, int eLength, int sLength) {
		double num=Double.valueOf(number);
		int intPart=(int)num;
		double doublePart=num-(double)intPart;
		double tempdoublePart=doublePart;
		char head;
		boolean check=true;
		int exponent=0;
		int looptimes=(int)Math.pow(2, eLength-1)-2;
		char[] eCharArray=new char[eLength];
		char[] tempFloatArray=new char[sLength];
		//整数部分表示成二进制时的长度
		int intPartLength=0;
		//小数部分表示成二进制时第一个1的位置；
		int doubleFirstOne=0;
		//符号位
		if(number.charAt(0)=='-'){
			head='1';
			intPart=-intPart;
			doublePart=-doublePart;
			num=-num;
		}else{
			head='0';
		}
		//TODO +inf -inf
		if(num==Double.MAX_VALUE){
			//指数都为1
			for (int i = 0; i <eCharArray.length; i++) {
				eCharArray[i]='1';
			}
			//尾数都为0
			for (int i = 0; i < tempFloatArray.length; i++) {
				tempFloatArray[i]='0';
			}
			return String.valueOf(head)+String.valueOf(eCharArray)+String.valueOf(tempFloatArray);
		}
		//如果整数部分大于等于1，左移，指数增加
		if(intPart>=1){
		     while((int)Math.pow(2, intPartLength)<=intPart){
			      intPartLength++;
			      }
		 	 //算出指数
			 exponent=intPartLength-1+(int)Math.pow(2, eLength-1)-1;
			 
		//指数部分
		for (int i = eLength-1; i > -1; i--) {
			eCharArray[i]=(char)(exponent%2+48);
			exponent=exponent/2;
		}
	    //小数部分
		//获得整数的二进制表示
		char[] tempCharArray=new char[intPartLength];
		for (int i = intPartLength-1; i > -1; i--) {
			tempCharArray[i]=(char)(intPart%2+48);
			intPart=intPart/2;
		}
		//获得小数的二进制表示
		
		for (int i = 0; i < tempFloatArray.length; i++) {
			if(i<=intPartLength-2){
				tempFloatArray[i]=tempCharArray[i+1];
			}else{
				if(doublePart*2>=1){
					tempFloatArray[i]='1';
					doublePart=doublePart*2-1;
				}else{
					tempFloatArray[i]='0';
					doublePart=doublePart*2;
				}
			}
		}
		return String.valueOf(head)+String.valueOf(eCharArray)+String.valueOf(tempFloatArray);
     }else{
    	//获得小数点的偏移量和指数
    	for (int i = 0; i < looptimes; i++) {
				if(doublePart*2>=1){
					if(check){
						doubleFirstOne=i;
						exponent=(-doubleFirstOne)-1+(int)Math.pow(2, eLength-1)-1;
						check=false;
						doublePart=doublePart*2-1.0;
						break;
					}
					doublePart=doublePart*2-1.0;
				}else{
					doublePart=doublePart*2;
				}
		    }
    	//指数部分
 		for (int i = eLength-1; i > -1; i--) {
 					eCharArray[i]=(char)(exponent%2+48);
 					exponent=exponent/2;
 				}
    	
    	//获得小数的二进制表示
 		//反规格
    	if(doubleFirstOne==0&&check){
    		doublePart=tempdoublePart*Math.pow(2, looptimes);
    		for (int i = 0; i < tempFloatArray.length; i++) {
    			
 				if(doublePart*2>=1){
 					tempFloatArray[i]='1';
 					doublePart=doublePart*2-1.0;
 				}else{
 					tempFloatArray[i]='0';
 					doublePart=doublePart*2;
 				}
 		    }
    		return String.valueOf(head)+String.valueOf(eCharArray)+String.valueOf(tempFloatArray);
    	}
    	
 		for (int i = 0; i < tempFloatArray.length; i++) {
 				if(doublePart*2>=1){
 					tempFloatArray[i]='1';
 					doublePart=doublePart*2-1.0;
 				}else{
 					tempFloatArray[i]='0';
 					doublePart=doublePart*2;
 				}
 		    }
 		return String.valueOf(head)+String.valueOf(eCharArray)+String.valueOf(tempFloatArray);
       }
		
	}
	
	/**
	 * 生成十进制浮点数的IEEE 754表示，要求调用{@link #floatRepresentation(String, int, int) floatRepresentation}实现。<br/>
	 * 例：ieee754("11.375", 32)
	 * @param number 十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param length 二进制表示的长度，为32或64
	 * @return number的IEEE 754表示，长度为length。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
	 */
	public String ieee754 (String number, int length) {
		if(length==32){
			return floatRepresentation(number,8,23);
		}
		if(length==64){
			return floatRepresentation(number,11,52);
		}
		return null;
	}
	
	/**
	 * 计算二进制补码表示的整数的真值。<br/>
	 * 例：integerTrueValue("00001001")
	 * @param operand 二进制补码表示的操作数
	 * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 */
	public String integerTrueValue (String operand) {
	    long result=0;
	    int a=0;
	    for (int i = operand.length()-1; i >0; i--) {
	    	a=Integer.valueOf(operand.charAt(i)-48);
	    	if(a==1){
			result=result+(long)Math.pow(2, operand.length()-1-i);
			}
		}
	    if(Integer.valueOf(operand.charAt(0))==49){
	    	result=result-(long)Math.pow(2, operand.length()-1 );
	    	 if(operand.length()>=64){
	 	    	return String.valueOf(result-1);
	 	    }
	    }
	   
		return String.valueOf(result);
	}
	
	/**
	 * 计算二进制原码表示的浮点数的真值。<br/>
	 * 例：floatTrueValue("0 10000010 01101100000", 8, 11)
	 * @param operand 二进制表示的操作数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位。正负无穷分别表示为“+Inf”和“-Inf”， NaN表示为“NaN”
	 */
	public String floatTrueValue (String operand, int eLength, int sLength) {
		
		long intPart=0;
		double floatPart=0.0;
		
		//获得指数大小，以及偏移位数
		int exponent=0;
		int rightShift=0;
		int leftShift=0;
		boolean isRightShift=false;
		boolean isLeftShift=false;
		boolean isBig=false;
		char[] exponentTempCharArray=operand.substring(1, eLength+1).toCharArray();
		//获得小数部分数组
		char[] floatTempCharArray=operand.substring(eLength+1).toCharArray();
		
		char[] floatCharArray;
		char[] intCharArray;
		//指数位为1的个数
		int oneCount=0;
		
		for (int i = eLength-1; i >-1; i--) {
	    	if(exponentTempCharArray[i]=='1'){
	    		exponent=exponent+(int)Math.pow(2,  eLength-1-i);
	    		oneCount++;
			}
		}
		
		//指数位都为1
		if(oneCount==eLength){
			if(operand.substring(eLength+1).contains("1")){
				return "NaN";
			}else {
				if(operand.charAt(0)=='1'){
					return "-Inf";
				}else {
					return "+Inf";
				}
			}
		}
		
		int a=(int)Math.pow(2,  eLength-1)-1;
		if(exponent>=a){//小数点向右偏移
			rightShift=exponent-a;
			isRightShift=true;
		}else {//小数点向左偏移
			leftShift=a-exponent;
			isLeftShift=true;
		}
		
		//小数点向右偏移
		if(isRightShift){
			if(rightShift<=sLength){
			floatCharArray=new char[sLength-rightShift];
			intCharArray=new char[rightShift+1];
			}else{
				floatCharArray=new char[0];
				intCharArray=new char[sLength+1];
				isBig=true;
			}
			for (int i = 0; i < intCharArray.length; i++) {
				if(i==0){
					intCharArray[i]='1';
				}else {
					intCharArray[i]=floatTempCharArray[i-1];
				}
			}
			
			for (int i = 0; i < floatCharArray.length; i++) {
				floatCharArray[i]=floatTempCharArray[i+rightShift];
			}
			//获得整数值
			for (int i = intCharArray.length-1; i >-1; i--) {
		    	if(intCharArray[i]=='1'){
		    		intPart=intPart+(int)Math.pow(2, intCharArray.length-1-i);
				}
			}
			if(isBig){
				intPart=(long) (intPart*Math.pow(2,rightShift-sLength));
				
			}
			//获得小数值
			for (int i = floatCharArray.length-1; i >-1; i--) {
		    	if(floatCharArray[i]=='1'){
		    		floatPart=floatPart+Math.pow(2, -i-1);
				}
			}
		}
		
	   //小数点向左偏移
	   if(isLeftShift){
		    //指数位都为0
		    if(oneCount==0){
		    	//反规格化数
		    	if(operand.substring(eLength+1).contains("1")){
		    		for (int i = floatTempCharArray.length-1; i >-1; i--) {
		    			if(floatTempCharArray[i]=='1'){
				    		floatPart=floatPart+Math.pow(2, -i-1);
						}
					}
		    		//除以Math.pow(2, (int)Math.pow(2, eLength-1)-2);
		    		floatPart=floatPart/Math.pow(2, Math.pow(2, eLength-1)-2);
		    		//判断正负号
		    		 if(operand.charAt(0)=='1'){
		    			  //小数为0.0返回整数 不带小数点
		    			  return "-"+String.valueOf(floatPart);
		    		 		}
		    		 else {
		    			 return String.valueOf(floatPart);
							}
		    	}else {//返回0
					return "0";
				}
		    	
		    	 
		    }
			floatCharArray=new char[sLength+leftShift];
			
			for (int i = 0; i <floatCharArray.length; i++) {
				if(i<leftShift-1){
					floatCharArray[i]='0';
				}
				if(i==leftShift-1){
					floatCharArray[i]='1';
				}
				if(i>leftShift-1){
					floatCharArray[i]=floatTempCharArray[i-leftShift];
				}
			}
			
			//获得小数值
			for (int i = floatCharArray.length-1; i >-1; i--) {
		    	if(floatCharArray[i]=='1'){
		    		floatPart=floatPart+Math.pow(2, -i-1);
				}
			}
		}
	 
	  
	   
	 //判断正负号
	 if(operand.charAt(0)=='1'){
		 //小数为0.0返回整数 不带小数点
		 if(floatPart==0){
			return "-"+String.valueOf(intPart);
		 }
		 return "-"+String.valueOf(intPart)+String.valueOf(floatPart).substring(1);
	 		}
	 
	 if(floatPart==0){
			return String.valueOf(intPart);
		 }
		return String.valueOf(intPart)+String.valueOf(floatPart).substring(1);
	}
	
	/**
	 * 按位取反操作。<br/>
	 * 例：negation("00001001")
	 * @param operand 二进制表示的操作数
	 * @return operand按位取反的结果
	 */
	public String negation (String operand) {
		char[] temp=new char[operand.length()];
		for (int i = 0; i <operand.length(); i++) {
			if(operand.charAt(i)=='0'){
				temp[i]='1';
			}
			if(operand.charAt(i)=='1'){
				temp[i]='0';
			}
		}
		return String.valueOf(temp);
	}
	
	/**
	 * 左移操作。<br/>
	 * 例：leftShift("00001001", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 左移的位数
	 * @return operand左移n位的结果
	 */
	public String leftShift (String operand, int n) {
		char[] temp=new char[operand.length()];
		for (int i= 0; i < operand.length(); i++) {
			if(i+n+1<=operand.length()){
			temp[i]=operand.charAt(i+n);
			}else{
				temp[i]='0';
			}
		}
		return String.valueOf(temp);
	}
	
	/**
	 * 逻辑右移操作。<br/>
	 * 例：logRightShift("11110110", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 右移的位数
	 * @return operand逻辑右移n位的结果
	 */
	public String logRightShift (String operand, int n) {
		char[] temp=new char[operand.length()];
		for (int i = 0; i < operand.length(); i++) {
			if(i<n){
				temp[i]='0';
			}else{
				temp[i]=operand.charAt(i-n);
			}
		}
		return String.valueOf(temp);
	}
	
	/**
	 * 算术右移操作。<br/>
	 * 例：logRightShift("11110110", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 右移的位数
	 * @return operand算术右移n位的结果
	 */
	public String ariRightShift (String operand, int n) {
		char[] temp=new char[operand.length()];
		for (int i = 0; i < operand.length(); i++) {
			if(i<n){
				if(operand.charAt(0)=='0'){
					temp[i]='0';
					}
				if(operand.charAt(0)=='1'){
					temp[i]='1';
					}
			}else{
				temp[i]=operand.charAt(i-n);
			}
		}
		return String.valueOf(temp);
	}
	
	/**
	 * 全加器，对两位以及进位进行加法运算。<br/>
	 * 例：fullAdder('1', '1', '0')
	 * @param x 被加数的某一位，取0或1
	 * @param y 加数的某一位，取0或1
	 * @param c 低位对当前位的进位，取0或1
	 * @return 相加的结果，用长度为2的字符串表示，第1位表示进位，第2位表示和
	 */
	public String fullAdder (char x, char y, char c) {
		String result;
		int sum = (int)x-48+(int)y-48+(int)c-48;
		switch(sum){
		case 0:result = "00";return result;
		case 1:result = "01";return result;
		case 2:result = "10";return result;
		case 3:result = "11";return result;
		default: return null;
		}
	}
	
	/**
	 * 4位先行进位加法器。要求采用{@link #fullAdder(char, char, char) fullAdder}来实现<br/>
	 * 例：claAdder("1001", "0001", '1')
	 * @param operand1 4位二进制表示的被加数
	 * @param operand2 4位二进制表示的加数
	 * @param c 低位对当前位的进位，取0或1
	 * @return 长度为5的字符串表示的计算结果，其中第1位是最高位进位，后4位是相加结果，其中进位不可以由循环获得
	 */
	public String claAdder (String operand1, String operand2, char c) {
		char[] sum=new char[operand1.length()];
		for (int i = operand1.length()-1; i > -1 ; i--) {
			sum[i]=fullAdder(operand1.charAt(i), operand2.charAt(i), c).charAt(1);
			c=fullAdder(operand1.charAt(i), operand2.charAt(i), c).charAt(0);
		}
		return c+String.valueOf(sum);
	}
	
	/**
	 * 加一器，实现操作数加1的运算。
	 * 需要采用与门、或门、异或门等模拟，
	 * 不可以直接调用{@link #fullAdder(char, char, char) fullAdder}、
	 * {@link #claAdder(String, String, char) claAdder}、
	 * {@link #adder(String, String, char, int) adder}、
	 * {@link #integerAddition(String, String, int) integerAddition}方法。<br/>
	 * 例：oneAdder("00001001")
	 * @param operand 二进制补码表示的操作数
	 * @return operand加1的结果，长度为operand的长度加1，其中第1位指示是否溢出（溢出为1，否则为0），其余位为相加结果
	 */
	public String oneAdder (String operand) {
		char[] x=operand.toCharArray();
		char[] carry=new char[x.length+1];
		carry[x.length]='0';
		char[] sum=new char[x.length];
		//初始化y 00000001
		char[] y=new char[x.length];
		for (int i = 0; i < y.length-1; i++) {
			y[i]='0';
		}
		y[y.length-1]='1';
		for (int i = x.length-1; i >-1; i--) {
			sum[i]=xor(xor(x[i], y[i]), carry[i+1]);
			carry[i]=or(or(and(x[i],carry[i+1] ), and(y[i], carry[i+1])), and(x[i], y[i]));
		}
		
		if(xor(carry[0], carry[1])=='1'){
			carry[0]='1';
		}else{
			carry[0]='0';
		}
		return String.valueOf(carry[0])+String.valueOf(sum);
	}
	public char not(char c) {
		switch (c) {
		case '0': return '1';
		case '1': return '0';
		default: return '0';
		}
	}
	public char or(char c,char d) {
		if(c=='0'){
			if(d=='0'){return '0';}
			if(d=='1'){return '1';}
		}else{
			if(d=='0'){return '1';}
			if(d=='1'){return '1';}
		}
		return '0';
	}
	public char xor(char c,char d) {
		if(c=='0'){
			if(d=='0'){return '0';}
			if(d=='1'){return '1';}
		}else{
			if(d=='0'){return '1';}
			if(d=='1'){return '0';}
		}
		return '0';
	}
	public char and(char c,char d) {
		if(c=='0'){
			if(d=='0'){return '0';}
			if(d=='1'){return '0';}
		}else{
			if(d=='0'){return '0';}
			if(d=='1'){return '1';}
		}
		return '0';
	}
	/**
	 * 加法器，要求调用{@link #claAdder(String, String, char)}方法实现。<br/>
	 * 例：adder("0100", "0011", ‘0’, 8)
	 * @param operand1 二进制补码表示的被加数
	 * @param operand2 二进制补码表示的加数
	 * @param c 最低位进位
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
	 */
	public String adder (String operand1, String operand2, char c, int length) {
		//补符号位
		char[] head1=new char[length-operand1.length()];
		char[] head2=new char[length-operand2.length()];
		if(operand1.charAt(0)=='0'){
			for (int i = 0; i < head1.length; i++) {
				head1[i]='0';
			}
		}else{
			for (int i = 0; i < head1.length; i++) {
				head1[i]='1';
			}
		}
		if(operand2.charAt(0)=='0'){
			for (int i = 0; i < head2.length; i++) {
				head2[i]='0';
			}
		}else{
			for (int i = 0; i < head2.length; i++) {
				head2[i]='1';
			}
		}
		String str1=String.valueOf(head1)+operand1;
		String str2=String.valueOf(head2)+operand2;
		String res=claAdder(str1,str2,c);
		char[] temp=res.toCharArray();
		
		//溢出位
		if(or(and(and(str1.charAt(0),str2.charAt(0)), not(res.charAt(1))), and(and(not(str1.charAt(0)),not(str2.charAt(0))), res.charAt(1)))=='1'){
			//溢出
			temp[0]='1';
		}else{
			temp[0]='0';
		}
		return String.valueOf(temp);
	}
	
	/**
	 * 整数加法，要求调用{@link #adder(String, String, char, int) adder}方法实现。<br/>
	 * 例：integerAddition("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被加数
	 * @param operand2 二进制补码表示的加数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
	 */
	public String integerAddition (String operand1, String operand2, int length) {
		return adder(operand1, operand2, '0', length);
	}
	
	/**
	 * 整数减法，可调用{@link #adder(String, String, char, int) adder}方法实现。<br/>
	 * 例：integerSubtraction("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被减数
	 * @param operand2 二进制补码表示的减数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相减结果
	 */
	public String integerSubtraction (String operand1, String operand2, int length) {
		//对减数取反加一
		String str=oneAdder(negation(operand2)).substring(1);
		return adder(operand1,str, '0', length);
	}
	
	/**
	 * 整数乘法，使用Booth算法实现，可调用{@link #adder(String, String, char, int) adder}等方法。<br/>
	 * 例：integerMultiplication("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被乘数
	 * @param operand2 二进制补码表示的乘数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的相乘结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相乘结果
	 */
	public String integerMultiplication (String operand1, String operand2, int length) {
		//乘数
		char[] tempy=operand2.toCharArray();
		char[] y=new char[operand2.length()+1];
		String tempRes1;
		String tempRes2;
		y[operand2.length()]='0';//add y0
		for (int i = 0; i < y.length-1; i++) {
			 y[i]=tempy[i];
		}
		//将被乘数取反加一,-x
		String notoperand1=oneAdder(negation(operand1)).substring(1);
		//初始化结果
		char[] temp=new char[length];
		for (int i = 0; i <temp.length; i++) {
			temp[i]='0';
		}
		String res=String.valueOf(temp);
		//溢出位
		char out='0';
		//开始
		for (int i = y.length-1; i>0; i--) {
			if(y[i]-y[i-1]==1){//+x
				tempRes1=adder(res.substring(0, operand1.length()), operand1, '0', operand1.length());
				tempRes2=tempRes1.substring(1)+res.substring(operand1.length());
				res=ariRightShift(tempRes2, 1);
				
				out=tempRes1.charAt(0);
			}
			if(y[i]-y[i-1]==0){//0
				res=ariRightShift(res, 1);
			}
			if(y[i]-y[i-1]==(-1)){//-x
				tempRes1=adder(res.substring(0, operand1.length()), notoperand1, '0', operand1.length());
				tempRes2=tempRes1.substring(1)+res.substring(operand1.length());
				res=ariRightShift(tempRes2, 1);
				
				out=tempRes1.charAt(0);
			}
			
		}
		return out+res;
	}
	
	/**
	 * 整数的不恢复余数除法，可调用{@link #adder(String, String, char, int) adder}等方法实现。<br/>
	 * 例：integerDivision("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被除数
	 * @param operand2 二进制补码表示的除数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为2*length+1的字符串表示的相除结果，其中第1位指示是否溢出（溢出为1，否则为0），其后length位为商，最后length位为余数
	 */
	public String integerDivision (String operand1, String operand2, int length) {
		String tempRes1;
		String tempRes2;
		//补符号位
		char[] head1=new char[2*length-operand1.length()];
		if(operand1.charAt(0)=='0'){
			for (int i = 0; i < head1.length; i++) {
					head1[i]='0';
			}
		}else{
			for (int i = 0; i < head1.length; i++) {
					head1[i]='1';
			}
		}
		//補充符號位的被除數
		String newoperand1=String.valueOf(head1)+operand1;
		//将余数与除数进行操作，余数为newoperand1前length位
		for (int i = 0; i < operand1.length(); i++) {
			if(xor(newoperand1.charAt(0), operand2.charAt(0))=='1'){//不同相加
				tempRes1=adder(newoperand1.substring(0,operand1.length()), operand2,'0', operand1.length());
				tempRes2=tempRes1.substring(1)+newoperand1.substring(operand1.length());
				//左移,最右为0
				newoperand1=leftShift(tempRes2, 1);
				//若结果与除数符号相同，商寄存器后补1 否则补0。在此调用加一器
				if(xor(tempRes1.charAt(1), operand2.charAt(0))=='0'){
					newoperand1=oneAdder(newoperand1).substring(1);
				}
			}else{//相同相减
				tempRes1=integerSubtraction(newoperand1.substring(0,operand1.length()), operand2,operand1.length());
				tempRes2=tempRes1.substring(1)+newoperand1.substring(operand1.length());
				//左移,最右为0
				newoperand1=leftShift(tempRes2, 1);
				//若结果与除数符号相同，商寄存器后补1 否则补0。在此调用加一器
				if(xor(tempRes1.charAt(1), operand2.charAt(0))=='0'){
					newoperand1=oneAdder(newoperand1).substring(1);
				}
			}
		}
		//最后阶段
		char out='0';//溢出位
		String remainder="";//余数
		String quotient;//商
		//整除的情况
		if(!newoperand1.substring(0, operand1.length()).contains("1")){
			quotient=newoperand1.substring(operand1.length());
			while(remainder.length()<operand1.length()){
				 remainder= remainder+"0";
			}
			
			//-6/3  quotient=quotient-1
			if(operand1.charAt(0)=='1'&&operand2.charAt(0)=='0'){
				String neOne="1";
				quotient=adder(quotient, neOne, '0', quotient.length()).substring(1);
				return out+quotient+remainder;
			}
			
			quotient=leftShift(quotient, 1);
		
			//商   if quotient is negative (the dividend has the different sign with divisor), quotient adds 1 
			if(quotient.charAt(0)=='1'){
				quotient=oneAdder(quotient).substring(1);
				//6/-3
				if(operand1.charAt(0)=='0'&&operand2.charAt(0)=='1'){
					quotient=oneAdder(quotient).substring(1);
				}
				
			}
			// -6/-3 
			if(operand1.charAt(0)=='1'&&operand2.charAt(0)=='1'){
				quotient=oneAdder(quotient).substring(1);
				quotient=oneAdder(quotient).substring(1);
			}
			
			return out+quotient+remainder;
		}
		
		//余数
		if(xor(newoperand1.charAt(0), operand2.charAt(0))=='1'){//不同相加
			tempRes1=adder(newoperand1.substring(0,operand1.length()), operand2,'0', operand1.length());
			remainder=tempRes1.substring(1);
			
			quotient=newoperand1.substring(operand1.length());
			
			//整除
			if(!remainder.contains("1")){
				//-6/2  quotient=quotient-1
				if(operand1.charAt(0)=='1'&&operand2.charAt(0)=='0'){
					String neOne="1";
					quotient=adder(quotient, neOne, '0', quotient.length()).substring(1);
					return out+quotient+remainder;
				}
			}
			//整除 -8/2
			String tempE=integerAddition(remainder, operand2, remainder.length()).substring(1);
			if(! tempE.contains("1")){
				quotient=leftShift(quotient, 1);
				return out+quotient+tempE;
			}
			quotient=leftShift(quotient, 1);
			//若结果与除数符号相同，商寄存器后补1 否则补0。在此调用加一器
			if(xor(tempRes1.charAt(1), operand2.charAt(0))=='0'){
				quotient=oneAdder(quotient).substring(1);
			}
			//余数与被除数符号不相同 做以下操作
            //若余数与除数符号相同，则余数减除数，否则做加法
            if(xor(tempRes1.charAt(1),operand1.charAt(0))=='1'){
            	if(xor(tempRes1.charAt(1), operand2.charAt(0))=='0'){
            		tempRes2=integerSubtraction(tempRes1.substring(1), operand2, operand1.length());
            		out=tempRes2.charAt(0);
            	}else{
            		tempRes2=integerAddition(tempRes1.substring(1), operand2, operand1.length());
            		out=tempRes2.charAt(0);
            	}
            	remainder=tempRes2.substring(1);
            }
		}else{//相同相减
			tempRes1=integerSubtraction(newoperand1.substring(0,operand1.length()), operand2,operand1.length());
			remainder=tempRes1.substring(1);
			quotient=newoperand1.substring(operand1.length());
			
			//整除
			if(!remainder.contains("1")){
				//-6/-2  
				if(operand1.charAt(0)=='1'&&operand2.charAt(0)=='1'){
					quotient=leftShift(quotient, 1);
					quotient=oneAdder(quotient).substring(1);
					return out+quotient+remainder;
				}
			}
			//整除 -8/-2
			String tempE=integerSubtraction(remainder, operand2, remainder.length()).substring(1);
			if(!tempE.contains("1")){
				quotient=leftShift(quotient, 1);
				quotient=oneAdder(quotient).substring(1);
				quotient=oneAdder(quotient).substring(1);
				return out+quotient+tempE;
			}
			quotient=leftShift(quotient, 1);
			//若结果与除数符号相同，商寄存器后补1 否则补0。在此调用加一器
			if(xor(tempRes1.charAt(1), operand2.charAt(0))=='0'){
				quotient=oneAdder(quotient).substring(1);
			}
			//余数与被除数符号不相同 做以下操作
            //若余数与除数符号相同，则余数减除数，否则做加法
            if(xor(tempRes1.charAt(1),operand1.charAt(0))=='1'){
            	if(xor(tempRes1.charAt(1), operand2.charAt(0))=='0'){
            		tempRes2=integerSubtraction(tempRes1.substring(1), operand2, operand1.length());
            		out=tempRes2.charAt(0);
            	}else{
            		tempRes2=integerAddition(tempRes1.substring(1), operand2, operand1.length());
            		out=tempRes2.charAt(0);
            	}
            	remainder=tempRes2.substring(1);
            }
		}
		//商   if quotient is negative (the dividend has the different sign with divisor), quotient adds 1 
		if(quotient.charAt(0)=='1'){
			quotient=oneAdder(quotient).substring(1);
		}
		//TODO
		return out+" "+quotient+" "+remainder;
	}
	
	/**
	 * 带符号整数加法，可以调用{@link #adder(String, String, char, int) adder}等方法，
	 * 但不能直接将操作数转换为补码后使用{@link #integerAddition(String, String, int) integerAddition}、
	 * {@link #integerSubtraction(String, String, int) integerSubtraction}来实现。<br/>
	 * 例：signedAddition("1100", "1011", 8)
	 * @param operand1 二进制原码表示的被加数，其中第1位为符号位
	 * @param operand2 二进制原码表示的加数，其中第1位为符号位
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度（不包含符号），当某个操作数的长度小于length时，需要将其长度扩展到length
	 * @return 长度为length+2的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），第2位为符号位，后length位是相加结果
	 */
	public String signedAddition (String operand1, String operand2, int length) {
		String result;
		String tempRes;
		boolean isDown1=false;
		boolean isDown2=false;
		//长度扩展 
		char[] head1=new char[length-operand1.length()+1];
		head1[0]=operand1.charAt(0);
		for (int i = 1; i < head1.length; i++) {
			head1[i]='0';
		}
		//扩展后被加数
		String str1=String.valueOf(head1)+operand1.substring(1);
		//如果小于0，取反加一
		if(head1[0]=='1'){
			str1=oneAdder(negation(str1)).substring(1);
		    isDown1=true;
		}
		char[] head2=new char[length-operand2.length()+1];
		head2[0]=operand2.charAt(0);
        for (int i = 1; i < head2.length; i++) {
			head2[i]='0';
		}
        //扩展后加数
        String str2=String.valueOf(head2)+operand2.substring(1);
    	//如果小于0，取反加一
		if(head2[0]=='1'){ 
			str2=oneAdder(negation(str2)).substring(1);
			isDown2=true;
		}
		
		tempRes=adder(str1, str2,'0', length);
		result=tempRes.substring(1);
		
		//溢出位
		char out;
		if(xor(head1[0], head2[0])=='1'){
			out='0';//不会溢出
		}
		out=tempRes.charAt(0);
		//符号位
		char sign;
		//同号相加，sign不变,
		if(operand1.charAt(0)==operand2.charAt(0)){
			sign=operand1.charAt(0);
			if(out=='1'){
				result=oneAdder(negation(result)).substring(1);
				if(isDown1||isDown2){
					out='0';
				}
			}else{
				if(isDown1||isDown2){
					out='1';
					result=oneAdder(negation(result)).substring(1);
				}
			}
		}else {
			if(out=='1'){
				sign=operand1.charAt(0);
				//修复第一位
				result=not(result.charAt(0))+result.substring(1);
				out='0';
			}else{
				result=oneAdder(negation(result)).substring(1);
				//修复第一位
				result=not(result.charAt(0))+result.substring(1);
				sign=not(operand1.charAt(0));
			}
		}
		return String.valueOf(out)+sign+result;
	}
	
	/**
	 * 浮点数加法，可调用{@link #signedAddition(String, String, int) signedAddition}等方法实现。<br/>
	 * 例：floatAddition("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 二进制表示的被加数
	 * @param operand2 二进制表示的加数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @param gLength 保护位的长度
	 * @return 长度为2+eLength+sLength的字符串表示的相加结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatAddition (String operand1, String operand2, int eLength, int sLength, int gLength) {
		// TODO YOUR CODE HERE.
		int e1;   //operand1的指数
		int e2;   //operand2的指数
		int exponent=0;//结果指数
		char sign='0';//符号位
		char out='0';
		String s1;//operand1的尾数
		String s2;//operand2的尾数
		String tempStr;//尾数相加结果
		String significands;//结果尾数
		//检查0
		//无论哪个数为0，结果为另一个数
		if(floatTrueValue(operand1, eLength, sLength).equals("0")){
			  return "0"+operand2;
		}
		if(floatTrueValue(operand2, eLength, sLength).equals("0")){
			  return "0"+operand1; 
		}
		//对齐有效数,初始化尾数
		e1=Integer.valueOf(integerTrueValue("0"+operand1.substring(1, eLength+1)));
		e2=Integer.valueOf(integerTrueValue("0"+operand2.substring(1, eLength+1)));
		s1=operand1.charAt(0)+"1"+operand1.substring(eLength+1);
		s2=operand2.charAt(0)+"1"+operand2.substring(eLength+1);
		if(e1>=e2){//e2增大,s2右移
			exponent=e1;
			if(s2.length()>(e1-e2)){
				s2=s2.substring(0, s2.length()-(e1-e2));
			}else{//s2为0
				return "0"+operand1;
			}
			
		}else {
			exponent=e2;
			if(s2.length()>(e2-e1)){
				s1=s1.substring(0, s1.length()-(e2-e1));
			}else{//s1为0
				return "0"+operand2;
			}
			
		}
		//加或减有效数
		tempStr=signedAddition(s1, s2, sLength+2);
		significands=tempStr.substring(3);
		
		//规格化结果
		sign=tempStr.charAt(1);
		for (int i = 0; i < significands.length(); i++) {
			if(significands.charAt(i)=='1'){
				significands=significands.substring(i+1);
				while(significands.length()<sLength){
					significands=significands+"0";
				}
				exponent=exponent-i;
				break;
			}
		}
		return String.valueOf(out)+String.valueOf(sign)+integerRepresentation(String.valueOf(exponent), eLength)+significands;
	}
	
	/**
	 * 浮点数减法，可调用{@link #floatAddition(String, String, int, int, int) floatAddition}方法实现。<br/>
	 * 例：floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 二进制表示的被减数
	 * @param operand2 二进制表示的减数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @param gLength 保护位的长度
	 * @return 长度为2+eLength+sLength的字符串表示的相减结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatSubtraction (String operand1, String operand2, int eLength, int sLength, int gLength) {
		//减数符号位取反
		operand2=not(operand2.charAt(0))+operand2.substring(1);
		return floatAddition(operand1, operand2, eLength, sLength, gLength);
	}
	
	/**
	 * 浮点数乘法，可调用{@link #integerMultiplication(String, String, int) integerMultiplication}等方法实现。<br/>
	 * 例：floatMultiplication("0 01111101 11000000", "0 01111110 00000000", 8, 8)
	 * @param operand1 二进制表示的被乘数
	 * @param operand2 二进制表示的乘数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatMultiplication (String operand1, String operand2, int eLength, int sLength) {
		char sign='0';//符号位
		char out='0'; //溢出位
		int exponent=0;//结果指数
		int e1;   //operand1的指数
		int e2;   //operand2的指数
		String significands;//尾数
		String tempStr;//暂时存储尾数相乘结果
		
		//无论哪个数为0，乘积即为0
		if(floatTrueValue(operand1, eLength, sLength).equals("0")||floatTrueValue(operand2, eLength, sLength).equals("0")){
			return "0"+floatRepresentation("0", eLength, sLength);
		}
		//一个数为NaN,结果为无穷
		if(floatTrueValue(operand1, eLength, sLength).equals("NaN")){
			if(operand2.charAt(0)=='0'){
				return "0"+floatRepresentation(Double.MAX_VALUE+"", eLength, sLength);
			}else{
				return "0"+floatRepresentation(-Double.MAX_VALUE+"", eLength, sLength);
			}
		}
		if(floatTrueValue(operand2, eLength, sLength).equals("NaN")){
			if(operand1.charAt(0)=='0'){
				return "0"+floatRepresentation(Double.MAX_VALUE+"", eLength, sLength);
			}else{
				return "0"+floatRepresentation(-Double.MAX_VALUE+"", eLength, sLength);
			}
		}
		
		//异或求出符号位
		sign=xor(operand1.charAt(0), operand2.charAt(0));
		
		//指数化成十进制进行相加 结果再减去偏值
		e1=Integer.valueOf(integerTrueValue("0"+operand1.substring(1, eLength+1)));
		e2=Integer.valueOf(integerTrueValue("0"+operand2.substring(1, eLength+1)));
		exponent=e1+e2-(int)Math.pow(2, eLength-1)+1;
		//指数上溢
		if(exponent>(int)Math.pow(2, eLength)-1){
			out='1';
			return String.valueOf(out)+floatRepresentation(Double.MAX_VALUE+"", eLength, sLength);
		}
		
		//尾数相乘
		String str1="01"+operand1.substring(eLength+1);
		String str2="01"+operand2.substring(eLength+1);
		tempStr=integerMultiplication(str1,str2, (sLength+2)*2);
		significands=tempStr.substring(2);
		//大于2 指数加1
		String tempE=integerRepresentation(String.valueOf((int)Math.pow(2, eLength-1)-1),eLength);
		double a=Double.valueOf(floatTrueValue("0"+tempE+operand1.substring(eLength+1), eLength, sLength));
		double b=Double.valueOf(floatTrueValue("0"+tempE+operand2.substring(eLength+1), eLength, sLength));
		if(a*b>=2.0){
			exponent++;
		}
		int c=0;
		//如果指数小于0，添加c至0 左移小数点 反规格
		if(exponent<0){
			c=0-exponent;
			exponent=0;
		}
		for (int i = 0; i < significands.length(); i++) {
			if(significands.charAt(i)=='1'){//出现第一个1，截取后面的部分
				if( i+1+sLength<=significands.length()){
					//反规格
					if(exponent==0){
						significands=significands.substring(i-c, i-c+sLength);
						break;
					}
					significands=significands.substring(i+1, i+1+sLength);
					break;
				}else {
					significands=significands.substring(i+1,significands.length());
					//右边剩余部分补0
					while (significands.length()<sLength) {
						significands=significands+"0";
					}
				}
				
			}
		}
		return String.valueOf(out)+String.valueOf(sign)+integerRepresentation(String.valueOf(exponent), eLength)+significands;
	}
	
	/**
	 * 浮点数除法，可调用{@link #integerDivision(String, String, int) integerDivision}等方法实现。<br/>
	 * 例：floatDivision("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 二进制表示的被除数
	 * @param operand2 二进制表示的除数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatDivision (String operand1, String operand2, int eLength, int sLength) {
		char sign='0';//符号位
		char out='0'; //溢出位
		int exponent=0;//结果指数
		int e1;   //operand1的指数
		int e2;   //operand2的指数
		String significands;//尾数
		String tempStr;//暂时存储尾数相除结果
		
		//如果被除数为0，结果即为0
		if(floatTrueValue(operand1, eLength, sLength).equals("0")){
			return "0"+floatRepresentation("0", eLength, sLength);
		}
		//异或求出符号位
		sign=xor(operand1.charAt(0), operand2.charAt(0));
				
		//指数化成十进制进行相加减 结果再加上偏值
		e1=Integer.valueOf(integerTrueValue("0"+operand1.substring(1, eLength+1)));
		e2=Integer.valueOf(integerTrueValue("0"+operand2.substring(1, eLength+1)));
		exponent=e1-e2+(int)Math.pow(2, eLength-1)-1;
		//指数上溢
		if(exponent>=(int)Math.pow(2, eLength)-1){
			out='1';
			return String.valueOf(out)+floatRepresentation(Double.MAX_VALUE+"", eLength, sLength);
		}
		
		//尾数相除
		String str1="01"+operand1.substring(eLength+1);
		String str2="01"+operand2.substring(eLength+1);
		significands=significandsDivision(str1,str2,sLength+2);
		
		//大于2 指数加1
		String tempE=integerRepresentation(String.valueOf((int)Math.pow(2, eLength-1)-1),eLength);
		double a=Double.valueOf(floatTrueValue("0"+tempE+operand1.substring(eLength+1), eLength, sLength));
		double b=Double.valueOf(floatTrueValue("0"+tempE+operand2.substring(eLength+1), eLength, sLength));
		int c=0;
		//如果指数小于0，添加c至0 左移小数点 反规格
		if(exponent<0){
			c=0-exponent;
			exponent=0;
		  }			
		//反规格
		if(exponent==0){
			significands=significands.substring(0, sLength);
			significands=logRightShift(significands, c);
		}else{
			significands=significands.substring(1, sLength+1);
		}
		return String.valueOf(out)+String.valueOf(sign)+integerRepresentation(String.valueOf(exponent), eLength)+significands;
	}
	/**
	 * 尾数除法
	 * @param operand1
	 * @param operand2
	 * @param eLength
	 * @param sLength
	 * @return
	 */
	public String significandsDivision(String operand1, String operand2, int sLength) {
		//后面补0
		char[] resC=new char[sLength];
		for (int i = 0; i < resC.length; i++) {
			resC[i]='0';
		}
		String resTempStr=String.valueOf(resC);
		String tempStr="0"+operand1+resTempStr;
		operand2="0"+operand2+resTempStr;
		String s1=null;
		String s2=null;
		String tempRes="";
		for (int i = 0; i < sLength; i++) {
			s1= tempStr;
			s2=integerSubtraction(s1,operand2, sLength*2+1);
			if(s2.charAt(1)=='0'){
				tempRes=tempRes+"1";
				tempStr=leftShift(s2.substring(1),1);
			}else{
				tempRes=tempRes+"0";
				tempStr=leftShift(s1,1);
			}
		}
		return tempRes;
	}
}
