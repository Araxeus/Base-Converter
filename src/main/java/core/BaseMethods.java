package core;
public interface BaseMethods {
	
	public static String toUpperCase(String number)
	{
		StringBuilder output = new StringBuilder();
		for(int i=0;i<number.length();i++)
			if(isLowerCase(number.charAt(i),36))
				output.append((char)(number.charAt(i)-32)); //IF char is lowercase -> make it uppercase
			else
				output.append(number.charAt(i));
		return output.toString();
	}
	
	private static boolean isLowerCase(char ch,int limit) {
		return ch>96 && ch<123-(36-limit); //ascii lower case range
	}
	private static boolean isNumber(char ch,int limit){
		if(limit>10)
			limit=10;
		return ch>47 && ch<(58-(10-limit)); //ascii digit range , limit is used for base calculation
	}
	
	private static boolean isUpperCase(char ch,int limit) {
		return ch>64 && ch<91-(36-limit); //ascii upper case range , limit is used for base calculation
	}
	
	private static boolean isValidChar(char ch,int index,int limit){
		return isNumber(ch,limit) || isUpperCase(ch,limit) || isLowerCase(ch,limit) || (index==0 && ch=='-') || ch=='.';
	}
	
	public static boolean isValidString(String input,int base){
		if(input.isBlank() || input.indexOf('.') != input.lastIndexOf('.'))
			return false;
		for(int i=0;i<input.length();i++)
			if(!isValidChar(input.charAt(i),i,base))
				return false;
		return true;
	}
	
	public static String getBaseXtoY(int baseX,int baseY, String num)
	{
		return getBase10toX(getBaseXto10(num,baseX),baseY);
	}
	
	public static double getBaseXto10(String num,int base) //2<=base<=36 , double num
	{
		if (base<2 || base>36)
			return -1;
		boolean negativeNum = false;
		double fraction = 0;
		if (num.charAt(0)=='-')
			negativeNum=true;
		if(num.contains("."))
		{
			fraction=getFractionBaseXto10(num.substring(num.indexOf('.')+1, num.length()),base);
			num=num.substring(0,num.indexOf('.'));			
		}
		long output=0;
		int currentDigit;
		char currentChar;
		for (int pos=0;pos<num.length();pos++)
		{
			currentChar=num.charAt((num.length()-1)-pos);
			if(isUpperCase(currentChar,36))
				currentDigit=currentChar-55;
			else if(isNumber(currentChar,10))
				currentDigit=currentChar-48;
			else if(currentChar=='-')
				break;
			else
				return -999;
			output+=currentDigit*Math.pow(base, pos);
		}
		fraction+=output;
		return negativeNum? fraction*-1 : fraction;		
	}
	
	private static double getFractionBaseXto10(String num,int base) //2<=base<=36 , 0<num<1 PRIVATE
	{
		int currentDigit;
		double output = 0;
		char currentChar;
		for (int pos=0;pos<num.length();pos++)
		{
			currentChar=num.charAt((num.length()-1)-pos);
			if(isUpperCase(currentChar,36))
				currentDigit=currentChar-55;
			else if(isNumber(currentChar,10))
				currentDigit=currentChar-48;
			else
				return 999;
			output+=currentDigit*Math.pow(base, -(num.length()-pos));
		}
		return output;
	}
	
	public static String getBase10toX(double num,int base) //2<=base<=36 , double num
	{
		if (base<2 || base>36)
			return "-1";	
		String fraction = "", negative = "";
		StringBuilder output = new StringBuilder();
		if (num<0)
		{
			num*=-1;
			negative="-";
		}
		if(num%1!=0)
		{
			fraction=getFractionBase10toX(num%1,base);
			num-=num%1;		
		}
		for (int wholeNum=(int)num; wholeNum>0; wholeNum/=base)
		{
			int currentNum=wholeNum%base;
			if(currentNum>9)
				output.append((char)(currentNum+55));
			else
				output.append(currentNum);
		}
		if(output.isEmpty())
			output.append("0");
		return negative + reverseString(output.toString()) + fraction;		
	}
	
	private static String getFractionBase10toX(double num,int base) // 0>num<1 2<=base<=36 PRIVATE
	{
		StringBuilder output = new StringBuilder(".");
		double remainder;
		int currentNum, digits = 0;
		do {
			remainder=(num*base)%1;
			if(1-remainder<=0.02)
				remainder=0;
			currentNum=(int)Math.ceil((num*base)-remainder);
			if (currentNum>9)
				output.append((char)(currentNum+55));
			else
				output.append(currentNum);
			num=remainder;
			digits++;
		}
		while(num!=0 && digits<8);
		return output.toString();		
	}
	
	private static String reverseString(String input)
	{
		StringBuilder output = new StringBuilder();
		for(int j=input.length()-1; j>=0; j--) // create new String and add 'charAt' to it starting from the end of this.sentence
			output.append(input.charAt(j));
		return output.toString();
	}	
}
