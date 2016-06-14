
/**
 * ģ��ALU���������͸���������������
 * @author [�뽫�˴��޸�Ϊ��151250163_��־�ɡ�]
 *
 */

public class ALU {

	/**
	 * ����ʮ���������Ķ����Ʋ����ʾ��<br/>
	 * ����integerRepresentation("9", 8)
	 * @param number ʮ������������Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param length �����Ʋ����ʾ�ĳ���
	 * @return number�Ķ����Ʋ����ʾ������Ϊlength
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
			//ÿλȡ��
			for (int i = 0; i < nums.length; i++) {
				nums[i]=not(nums[i]);
			}
			return oneAdder(String.valueOf(nums)).substring(1);
		}
		return String.valueOf(nums);
	}
	
	/**
	 * ����ʮ���Ƹ������Ķ����Ʊ�ʾ��
	 * ��Ҫ���� 0������񻯡����������+Inf���͡�-Inf������ NaN�����أ������� IEEE 754��
	 * �������Ϊ��0 ���롣<br/>
	 * ����floatRepresentation("11.375", 8, 11)
	 * @param number ʮ���Ƹ�����������С���㡣��Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ������Ϊ������С��������������� 5����
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return number�Ķ����Ʊ�ʾ������Ϊ 1+eLength+sLength���������ң�����Ϊ���š�ָ���������ʾ����β������λ���أ�
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
		//�������ֱ�ʾ�ɶ�����ʱ�ĳ���
		int intPartLength=0;
		//С�����ֱ�ʾ�ɶ�����ʱ��һ��1��λ�ã�
		int doubleFirstOne=0;
		//����λ
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
			//ָ����Ϊ1
			for (int i = 0; i <eCharArray.length; i++) {
				eCharArray[i]='1';
			}
			//β����Ϊ0
			for (int i = 0; i < tempFloatArray.length; i++) {
				tempFloatArray[i]='0';
			}
			return String.valueOf(head)+String.valueOf(eCharArray)+String.valueOf(tempFloatArray);
		}
		//����������ִ��ڵ���1�����ƣ�ָ������
		if(intPart>=1){
		     while((int)Math.pow(2, intPartLength)<=intPart){
			      intPartLength++;
			      }
		 	 //���ָ��
			 exponent=intPartLength-1+(int)Math.pow(2, eLength-1)-1;
			 
		//ָ������
		for (int i = eLength-1; i > -1; i--) {
			eCharArray[i]=(char)(exponent%2+48);
			exponent=exponent/2;
		}
	    //С������
		//��������Ķ����Ʊ�ʾ
		char[] tempCharArray=new char[intPartLength];
		for (int i = intPartLength-1; i > -1; i--) {
			tempCharArray[i]=(char)(intPart%2+48);
			intPart=intPart/2;
		}
		//���С���Ķ����Ʊ�ʾ
		
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
    	//���С�����ƫ������ָ��
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
    	//ָ������
 		for (int i = eLength-1; i > -1; i--) {
 					eCharArray[i]=(char)(exponent%2+48);
 					exponent=exponent/2;
 				}
    	
    	//���С���Ķ����Ʊ�ʾ
 		//�����
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
	 * ����ʮ���Ƹ�������IEEE 754��ʾ��Ҫ�����{@link #floatRepresentation(String, int, int) floatRepresentation}ʵ�֡�<br/>
	 * ����ieee754("11.375", 32)
	 * @param number ʮ���Ƹ�����������С���㡣��Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param length �����Ʊ�ʾ�ĳ��ȣ�Ϊ32��64
	 * @return number��IEEE 754��ʾ������Ϊlength���������ң�����Ϊ���š�ָ���������ʾ����β������λ���أ�
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
	 * ��������Ʋ����ʾ����������ֵ��<br/>
	 * ����integerTrueValue("00001001")
	 * @param operand �����Ʋ����ʾ�Ĳ�����
	 * @return operand����ֵ����Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
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
	 * ���������ԭ���ʾ�ĸ���������ֵ��<br/>
	 * ����floatTrueValue("0 10000010 01101100000", 8, 11)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return operand����ֵ����Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ����������ֱ��ʾΪ��+Inf���͡�-Inf���� NaN��ʾΪ��NaN��
	 */
	public String floatTrueValue (String operand, int eLength, int sLength) {
		
		long intPart=0;
		double floatPart=0.0;
		
		//���ָ����С���Լ�ƫ��λ��
		int exponent=0;
		int rightShift=0;
		int leftShift=0;
		boolean isRightShift=false;
		boolean isLeftShift=false;
		boolean isBig=false;
		char[] exponentTempCharArray=operand.substring(1, eLength+1).toCharArray();
		//���С����������
		char[] floatTempCharArray=operand.substring(eLength+1).toCharArray();
		
		char[] floatCharArray;
		char[] intCharArray;
		//ָ��λΪ1�ĸ���
		int oneCount=0;
		
		for (int i = eLength-1; i >-1; i--) {
	    	if(exponentTempCharArray[i]=='1'){
	    		exponent=exponent+(int)Math.pow(2,  eLength-1-i);
	    		oneCount++;
			}
		}
		
		//ָ��λ��Ϊ1
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
		if(exponent>=a){//С��������ƫ��
			rightShift=exponent-a;
			isRightShift=true;
		}else {//С��������ƫ��
			leftShift=a-exponent;
			isLeftShift=true;
		}
		
		//С��������ƫ��
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
			//�������ֵ
			for (int i = intCharArray.length-1; i >-1; i--) {
		    	if(intCharArray[i]=='1'){
		    		intPart=intPart+(int)Math.pow(2, intCharArray.length-1-i);
				}
			}
			if(isBig){
				intPart=(long) (intPart*Math.pow(2,rightShift-sLength));
				
			}
			//���С��ֵ
			for (int i = floatCharArray.length-1; i >-1; i--) {
		    	if(floatCharArray[i]=='1'){
		    		floatPart=floatPart+Math.pow(2, -i-1);
				}
			}
		}
		
	   //С��������ƫ��
	   if(isLeftShift){
		    //ָ��λ��Ϊ0
		    if(oneCount==0){
		    	//�������
		    	if(operand.substring(eLength+1).contains("1")){
		    		for (int i = floatTempCharArray.length-1; i >-1; i--) {
		    			if(floatTempCharArray[i]=='1'){
				    		floatPart=floatPart+Math.pow(2, -i-1);
						}
					}
		    		//����Math.pow(2, (int)Math.pow(2, eLength-1)-2);
		    		floatPart=floatPart/Math.pow(2, Math.pow(2, eLength-1)-2);
		    		//�ж�������
		    		 if(operand.charAt(0)=='1'){
		    			  //С��Ϊ0.0�������� ����С����
		    			  return "-"+String.valueOf(floatPart);
		    		 		}
		    		 else {
		    			 return String.valueOf(floatPart);
							}
		    	}else {//����0
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
			
			//���С��ֵ
			for (int i = floatCharArray.length-1; i >-1; i--) {
		    	if(floatCharArray[i]=='1'){
		    		floatPart=floatPart+Math.pow(2, -i-1);
				}
			}
		}
	 
	  
	   
	 //�ж�������
	 if(operand.charAt(0)=='1'){
		 //С��Ϊ0.0�������� ����С����
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
	 * ��λȡ��������<br/>
	 * ����negation("00001001")
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @return operand��λȡ���Ľ��
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
	 * ���Ʋ�����<br/>
	 * ����leftShift("00001001", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand����nλ�Ľ��
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
	 * �߼����Ʋ�����<br/>
	 * ����logRightShift("11110110", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand�߼�����nλ�Ľ��
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
	 * �������Ʋ�����<br/>
	 * ����logRightShift("11110110", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand��������nλ�Ľ��
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
	 * ȫ����������λ�Լ���λ���мӷ����㡣<br/>
	 * ����fullAdder('1', '1', '0')
	 * @param x ��������ĳһλ��ȡ0��1
	 * @param y ������ĳһλ��ȡ0��1
	 * @param c ��λ�Ե�ǰλ�Ľ�λ��ȡ0��1
	 * @return ��ӵĽ�����ó���Ϊ2���ַ�����ʾ����1λ��ʾ��λ����2λ��ʾ��
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
	 * 4λ���н�λ�ӷ�����Ҫ�����{@link #fullAdder(char, char, char) fullAdder}��ʵ��<br/>
	 * ����claAdder("1001", "0001", '1')
	 * @param operand1 4λ�����Ʊ�ʾ�ı�����
	 * @param operand2 4λ�����Ʊ�ʾ�ļ���
	 * @param c ��λ�Ե�ǰλ�Ľ�λ��ȡ0��1
	 * @return ����Ϊ5���ַ�����ʾ�ļ����������е�1λ�����λ��λ����4λ����ӽ�������н�λ��������ѭ�����
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
	 * ��һ����ʵ�ֲ�������1�����㡣
	 * ��Ҫ�������š����š�����ŵ�ģ�⣬
	 * ������ֱ�ӵ���{@link #fullAdder(char, char, char) fullAdder}��
	 * {@link #claAdder(String, String, char) claAdder}��
	 * {@link #adder(String, String, char, int) adder}��
	 * {@link #integerAddition(String, String, int) integerAddition}������<br/>
	 * ����oneAdder("00001001")
	 * @param operand �����Ʋ����ʾ�Ĳ�����
	 * @return operand��1�Ľ��������Ϊoperand�ĳ��ȼ�1�����е�1λָʾ�Ƿ���������Ϊ1������Ϊ0��������λΪ��ӽ��
	 */
	public String oneAdder (String operand) {
		char[] x=operand.toCharArray();
		char[] carry=new char[x.length+1];
		carry[x.length]='0';
		char[] sum=new char[x.length];
		//��ʼ��y 00000001
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
	 * �ӷ�����Ҫ�����{@link #claAdder(String, String, char)}����ʵ�֡�<br/>
	 * ����adder("0100", "0011", ��0��, 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param c ���λ��λ
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����ӽ��
	 */
	public String adder (String operand1, String operand2, char c, int length) {
		//������λ
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
		
		//���λ
		if(or(and(and(str1.charAt(0),str2.charAt(0)), not(res.charAt(1))), and(and(not(str1.charAt(0)),not(str2.charAt(0))), res.charAt(1)))=='1'){
			//���
			temp[0]='1';
		}else{
			temp[0]='0';
		}
		return String.valueOf(temp);
	}
	
	/**
	 * �����ӷ���Ҫ�����{@link #adder(String, String, char, int) adder}����ʵ�֡�<br/>
	 * ����integerAddition("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����ӽ��
	 */
	public String integerAddition (String operand1, String operand2, int length) {
		return adder(operand1, operand2, '0', length);
	}
	
	/**
	 * �����������ɵ���{@link #adder(String, String, char, int) adder}����ʵ�֡�<br/>
	 * ����integerSubtraction("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ��������
	 */
	public String integerSubtraction (String operand1, String operand2, int length) {
		//�Լ���ȡ����һ
		String str=oneAdder(negation(operand2)).substring(1);
		return adder(operand1,str, '0', length);
	}
	
	/**
	 * �����˷���ʹ��Booth�㷨ʵ�֣��ɵ���{@link #adder(String, String, char, int) adder}�ȷ�����<br/>
	 * ����integerMultiplication("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ĳ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ����˽�������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����˽��
	 */
	public String integerMultiplication (String operand1, String operand2, int length) {
		//����
		char[] tempy=operand2.toCharArray();
		char[] y=new char[operand2.length()+1];
		String tempRes1;
		String tempRes2;
		y[operand2.length()]='0';//add y0
		for (int i = 0; i < y.length-1; i++) {
			 y[i]=tempy[i];
		}
		//��������ȡ����һ,-x
		String notoperand1=oneAdder(negation(operand1)).substring(1);
		//��ʼ�����
		char[] temp=new char[length];
		for (int i = 0; i <temp.length; i++) {
			temp[i]='0';
		}
		String res=String.valueOf(temp);
		//���λ
		char out='0';
		//��ʼ
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
	 * �����Ĳ��ָ������������ɵ���{@link #adder(String, String, char, int) adder}�ȷ���ʵ�֡�<br/>
	 * ����integerDivision("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ĳ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊ2*length+1���ַ�����ʾ�������������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0�������lengthλΪ�̣����lengthλΪ����
	 */
	public String integerDivision (String operand1, String operand2, int length) {
		String tempRes1;
		String tempRes2;
		//������λ
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
		//�a���̖λ�ı�����
		String newoperand1=String.valueOf(head1)+operand1;
		//��������������в���������Ϊnewoperand1ǰlengthλ
		for (int i = 0; i < operand1.length(); i++) {
			if(xor(newoperand1.charAt(0), operand2.charAt(0))=='1'){//��ͬ���
				tempRes1=adder(newoperand1.substring(0,operand1.length()), operand2,'0', operand1.length());
				tempRes2=tempRes1.substring(1)+newoperand1.substring(operand1.length());
				//����,����Ϊ0
				newoperand1=leftShift(tempRes2, 1);
				//����������������ͬ���̼Ĵ�����1 ����0���ڴ˵��ü�һ��
				if(xor(tempRes1.charAt(1), operand2.charAt(0))=='0'){
					newoperand1=oneAdder(newoperand1).substring(1);
				}
			}else{//��ͬ���
				tempRes1=integerSubtraction(newoperand1.substring(0,operand1.length()), operand2,operand1.length());
				tempRes2=tempRes1.substring(1)+newoperand1.substring(operand1.length());
				//����,����Ϊ0
				newoperand1=leftShift(tempRes2, 1);
				//����������������ͬ���̼Ĵ�����1 ����0���ڴ˵��ü�һ��
				if(xor(tempRes1.charAt(1), operand2.charAt(0))=='0'){
					newoperand1=oneAdder(newoperand1).substring(1);
				}
			}
		}
		//���׶�
		char out='0';//���λ
		String remainder="";//����
		String quotient;//��
		//���������
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
		
			//��   if quotient is negative (the dividend has the different sign with divisor), quotient adds 1 
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
		
		//����
		if(xor(newoperand1.charAt(0), operand2.charAt(0))=='1'){//��ͬ���
			tempRes1=adder(newoperand1.substring(0,operand1.length()), operand2,'0', operand1.length());
			remainder=tempRes1.substring(1);
			
			quotient=newoperand1.substring(operand1.length());
			
			//����
			if(!remainder.contains("1")){
				//-6/2  quotient=quotient-1
				if(operand1.charAt(0)=='1'&&operand2.charAt(0)=='0'){
					String neOne="1";
					quotient=adder(quotient, neOne, '0', quotient.length()).substring(1);
					return out+quotient+remainder;
				}
			}
			//���� -8/2
			String tempE=integerAddition(remainder, operand2, remainder.length()).substring(1);
			if(! tempE.contains("1")){
				quotient=leftShift(quotient, 1);
				return out+quotient+tempE;
			}
			quotient=leftShift(quotient, 1);
			//����������������ͬ���̼Ĵ�����1 ����0���ڴ˵��ü�һ��
			if(xor(tempRes1.charAt(1), operand2.charAt(0))=='0'){
				quotient=oneAdder(quotient).substring(1);
			}
			//�����뱻�������Ų���ͬ �����²���
            //�����������������ͬ�����������������������ӷ�
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
		}else{//��ͬ���
			tempRes1=integerSubtraction(newoperand1.substring(0,operand1.length()), operand2,operand1.length());
			remainder=tempRes1.substring(1);
			quotient=newoperand1.substring(operand1.length());
			
			//����
			if(!remainder.contains("1")){
				//-6/-2  
				if(operand1.charAt(0)=='1'&&operand2.charAt(0)=='1'){
					quotient=leftShift(quotient, 1);
					quotient=oneAdder(quotient).substring(1);
					return out+quotient+remainder;
				}
			}
			//���� -8/-2
			String tempE=integerSubtraction(remainder, operand2, remainder.length()).substring(1);
			if(!tempE.contains("1")){
				quotient=leftShift(quotient, 1);
				quotient=oneAdder(quotient).substring(1);
				quotient=oneAdder(quotient).substring(1);
				return out+quotient+tempE;
			}
			quotient=leftShift(quotient, 1);
			//����������������ͬ���̼Ĵ�����1 ����0���ڴ˵��ü�һ��
			if(xor(tempRes1.charAt(1), operand2.charAt(0))=='0'){
				quotient=oneAdder(quotient).substring(1);
			}
			//�����뱻�������Ų���ͬ �����²���
            //�����������������ͬ�����������������������ӷ�
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
		//��   if quotient is negative (the dividend has the different sign with divisor), quotient adds 1 
		if(quotient.charAt(0)=='1'){
			quotient=oneAdder(quotient).substring(1);
		}
		//TODO
		return out+" "+quotient+" "+remainder;
	}
	
	/**
	 * �����������ӷ������Ե���{@link #adder(String, String, char, int) adder}�ȷ�����
	 * ������ֱ�ӽ�������ת��Ϊ�����ʹ��{@link #integerAddition(String, String, int) integerAddition}��
	 * {@link #integerSubtraction(String, String, int) integerSubtraction}��ʵ�֡�<br/>
	 * ����signedAddition("1100", "1011", 8)
	 * @param operand1 ������ԭ���ʾ�ı����������е�1λΪ����λ
	 * @param operand2 ������ԭ���ʾ�ļ��������е�1λΪ����λ
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ����������ţ�����ĳ���������ĳ���С��lengthʱ����Ҫ���䳤����չ��length
	 * @return ����Ϊlength+2���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������2λΪ����λ����lengthλ����ӽ��
	 */
	public String signedAddition (String operand1, String operand2, int length) {
		String result;
		String tempRes;
		boolean isDown1=false;
		boolean isDown2=false;
		//������չ 
		char[] head1=new char[length-operand1.length()+1];
		head1[0]=operand1.charAt(0);
		for (int i = 1; i < head1.length; i++) {
			head1[i]='0';
		}
		//��չ�󱻼���
		String str1=String.valueOf(head1)+operand1.substring(1);
		//���С��0��ȡ����һ
		if(head1[0]=='1'){
			str1=oneAdder(negation(str1)).substring(1);
		    isDown1=true;
		}
		char[] head2=new char[length-operand2.length()+1];
		head2[0]=operand2.charAt(0);
        for (int i = 1; i < head2.length; i++) {
			head2[i]='0';
		}
        //��չ�����
        String str2=String.valueOf(head2)+operand2.substring(1);
    	//���С��0��ȡ����һ
		if(head2[0]=='1'){ 
			str2=oneAdder(negation(str2)).substring(1);
			isDown2=true;
		}
		
		tempRes=adder(str1, str2,'0', length);
		result=tempRes.substring(1);
		
		//���λ
		char out;
		if(xor(head1[0], head2[0])=='1'){
			out='0';//�������
		}
		out=tempRes.charAt(0);
		//����λ
		char sign;
		//ͬ����ӣ�sign����,
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
				//�޸���һλ
				result=not(result.charAt(0))+result.substring(1);
				out='0';
			}else{
				result=oneAdder(negation(result)).substring(1);
				//�޸���һλ
				result=not(result.charAt(0))+result.substring(1);
				sign=not(operand1.charAt(0));
			}
		}
		return String.valueOf(out)+sign+result;
	}
	
	/**
	 * �������ӷ����ɵ���{@link #signedAddition(String, String, int) signedAddition}�ȷ���ʵ�֡�<br/>
	 * ����floatAddition("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ļ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param gLength ����λ�ĳ���
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����ӽ�������е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatAddition (String operand1, String operand2, int eLength, int sLength, int gLength) {
		// TODO YOUR CODE HERE.
		int e1;   //operand1��ָ��
		int e2;   //operand2��ָ��
		int exponent=0;//���ָ��
		char sign='0';//����λ
		char out='0';
		String s1;//operand1��β��
		String s2;//operand2��β��
		String tempStr;//β����ӽ��
		String significands;//���β��
		//���0
		//�����ĸ���Ϊ0�����Ϊ��һ����
		if(floatTrueValue(operand1, eLength, sLength).equals("0")){
			  return "0"+operand2;
		}
		if(floatTrueValue(operand2, eLength, sLength).equals("0")){
			  return "0"+operand1; 
		}
		//������Ч��,��ʼ��β��
		e1=Integer.valueOf(integerTrueValue("0"+operand1.substring(1, eLength+1)));
		e2=Integer.valueOf(integerTrueValue("0"+operand2.substring(1, eLength+1)));
		s1=operand1.charAt(0)+"1"+operand1.substring(eLength+1);
		s2=operand2.charAt(0)+"1"+operand2.substring(eLength+1);
		if(e1>=e2){//e2����,s2����
			exponent=e1;
			if(s2.length()>(e1-e2)){
				s2=s2.substring(0, s2.length()-(e1-e2));
			}else{//s2Ϊ0
				return "0"+operand1;
			}
			
		}else {
			exponent=e2;
			if(s2.length()>(e2-e1)){
				s1=s1.substring(0, s1.length()-(e2-e1));
			}else{//s1Ϊ0
				return "0"+operand2;
			}
			
		}
		//�ӻ����Ч��
		tempStr=signedAddition(s1, s2, sLength+2);
		significands=tempStr.substring(3);
		
		//��񻯽��
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
	 * �������������ɵ���{@link #floatAddition(String, String, int, int, int) floatAddition}����ʵ�֡�<br/>
	 * ����floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ļ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param gLength ����λ�ĳ���
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ�������������е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatSubtraction (String operand1, String operand2, int eLength, int sLength, int gLength) {
		//��������λȡ��
		operand2=not(operand2.charAt(0))+operand2.substring(1);
		return floatAddition(operand1, operand2, eLength, sLength, gLength);
	}
	
	/**
	 * �������˷����ɵ���{@link #integerMultiplication(String, String, int) integerMultiplication}�ȷ���ʵ�֡�<br/>
	 * ����floatMultiplication("0 01111101 11000000", "0 01111110 00000000", 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ĳ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����˽��,���е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatMultiplication (String operand1, String operand2, int eLength, int sLength) {
		char sign='0';//����λ
		char out='0'; //���λ
		int exponent=0;//���ָ��
		int e1;   //operand1��ָ��
		int e2;   //operand2��ָ��
		String significands;//β��
		String tempStr;//��ʱ�洢β����˽��
		
		//�����ĸ���Ϊ0���˻���Ϊ0
		if(floatTrueValue(operand1, eLength, sLength).equals("0")||floatTrueValue(operand2, eLength, sLength).equals("0")){
			return "0"+floatRepresentation("0", eLength, sLength);
		}
		//һ����ΪNaN,���Ϊ����
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
		
		//����������λ
		sign=xor(operand1.charAt(0), operand2.charAt(0));
		
		//ָ������ʮ���ƽ������ ����ټ�ȥƫֵ
		e1=Integer.valueOf(integerTrueValue("0"+operand1.substring(1, eLength+1)));
		e2=Integer.valueOf(integerTrueValue("0"+operand2.substring(1, eLength+1)));
		exponent=e1+e2-(int)Math.pow(2, eLength-1)+1;
		//ָ������
		if(exponent>(int)Math.pow(2, eLength)-1){
			out='1';
			return String.valueOf(out)+floatRepresentation(Double.MAX_VALUE+"", eLength, sLength);
		}
		
		//β�����
		String str1="01"+operand1.substring(eLength+1);
		String str2="01"+operand2.substring(eLength+1);
		tempStr=integerMultiplication(str1,str2, (sLength+2)*2);
		significands=tempStr.substring(2);
		//����2 ָ����1
		String tempE=integerRepresentation(String.valueOf((int)Math.pow(2, eLength-1)-1),eLength);
		double a=Double.valueOf(floatTrueValue("0"+tempE+operand1.substring(eLength+1), eLength, sLength));
		double b=Double.valueOf(floatTrueValue("0"+tempE+operand2.substring(eLength+1), eLength, sLength));
		if(a*b>=2.0){
			exponent++;
		}
		int c=0;
		//���ָ��С��0�����c��0 ����С���� �����
		if(exponent<0){
			c=0-exponent;
			exponent=0;
		}
		for (int i = 0; i < significands.length(); i++) {
			if(significands.charAt(i)=='1'){//���ֵ�һ��1����ȡ����Ĳ���
				if( i+1+sLength<=significands.length()){
					//�����
					if(exponent==0){
						significands=significands.substring(i-c, i-c+sLength);
						break;
					}
					significands=significands.substring(i+1, i+1+sLength);
					break;
				}else {
					significands=significands.substring(i+1,significands.length());
					//�ұ�ʣ�ಿ�ֲ�0
					while (significands.length()<sLength) {
						significands=significands+"0";
					}
				}
				
			}
		}
		return String.valueOf(out)+String.valueOf(sign)+integerRepresentation(String.valueOf(exponent), eLength)+significands;
	}
	
	/**
	 * �������������ɵ���{@link #integerDivision(String, String, int) integerDivision}�ȷ���ʵ�֡�<br/>
	 * ����floatDivision("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ĳ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����˽��,���е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatDivision (String operand1, String operand2, int eLength, int sLength) {
		char sign='0';//����λ
		char out='0'; //���λ
		int exponent=0;//���ָ��
		int e1;   //operand1��ָ��
		int e2;   //operand2��ָ��
		String significands;//β��
		String tempStr;//��ʱ�洢β��������
		
		//���������Ϊ0�������Ϊ0
		if(floatTrueValue(operand1, eLength, sLength).equals("0")){
			return "0"+floatRepresentation("0", eLength, sLength);
		}
		//����������λ
		sign=xor(operand1.charAt(0), operand2.charAt(0));
				
		//ָ������ʮ���ƽ�����Ӽ� ����ټ���ƫֵ
		e1=Integer.valueOf(integerTrueValue("0"+operand1.substring(1, eLength+1)));
		e2=Integer.valueOf(integerTrueValue("0"+operand2.substring(1, eLength+1)));
		exponent=e1-e2+(int)Math.pow(2, eLength-1)-1;
		//ָ������
		if(exponent>=(int)Math.pow(2, eLength)-1){
			out='1';
			return String.valueOf(out)+floatRepresentation(Double.MAX_VALUE+"", eLength, sLength);
		}
		
		//β�����
		String str1="01"+operand1.substring(eLength+1);
		String str2="01"+operand2.substring(eLength+1);
		significands=significandsDivision(str1,str2,sLength+2);
		
		//����2 ָ����1
		String tempE=integerRepresentation(String.valueOf((int)Math.pow(2, eLength-1)-1),eLength);
		double a=Double.valueOf(floatTrueValue("0"+tempE+operand1.substring(eLength+1), eLength, sLength));
		double b=Double.valueOf(floatTrueValue("0"+tempE+operand2.substring(eLength+1), eLength, sLength));
		int c=0;
		//���ָ��С��0�����c��0 ����С���� �����
		if(exponent<0){
			c=0-exponent;
			exponent=0;
		  }			
		//�����
		if(exponent==0){
			significands=significands.substring(0, sLength);
			significands=logRightShift(significands, c);
		}else{
			significands=significands.substring(1, sLength+1);
		}
		return String.valueOf(out)+String.valueOf(sign)+integerRepresentation(String.valueOf(exponent), eLength)+significands;
	}
	/**
	 * β������
	 * @param operand1
	 * @param operand2
	 * @param eLength
	 * @param sLength
	 * @return
	 */
	public String significandsDivision(String operand1, String operand2, int sLength) {
		//���油0
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
