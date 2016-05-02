package com.ys.ui.serial.print.activity;

public class StrToHex 
{
	public static int String2Hex(byte[] SendStr, byte[] SendHex)  //ת��Ϊ16���ƣ�����ת������
	{
		int hexdata,lowhexdata;
		int hexdatalen=0;
		int len=SendStr.length;
		for(int i=0;i<len;)
		{
			byte lstr=0;
			byte hstr=SendStr[i];
			if((hstr==' ')||(hstr=='\r')||(hstr=='\n'))
			{                       
				i++;
				continue;
			}

			i++;

			if(i!=len)
			{
	           lstr=SendStr[i];
			}
			
			if((i==len)||(lstr==' ')||(lstr=='\r')||(lstr=='\n'))
			{
				lstr=hstr;
				hstr='0';
			}

			hexdata=ConvertHexChar(hstr);
			lowhexdata=ConvertHexChar(lstr);
			if((hexdata>=16)||(lowhexdata>=16))
			{
				return 0;
			}
			else 
				hexdata=hexdata*16+lowhexdata;
			i++;
			SendHex[hexdatalen]=(byte)hexdata;
			hexdatalen++;
		}
		return hexdatalen;
	}


	private static int ConvertHexChar(byte ch)  
	{
		if((ch>='0')&&(ch<='9'))
			return (ch-0x30);
		else if((ch>='A')&&(ch<='F'))
			return (ch-'A'+10);
		else if((ch>='a')&&(ch<='f'))
			return (ch-'a'+10);
		else 
			return 256;
	}

}
