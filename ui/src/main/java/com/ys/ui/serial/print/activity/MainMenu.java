/*
 * Copyright 2009 Cedric Priscal
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package com.ys.ui.serial.print.activity;

import java.io.UnsupportedEncodingException;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ys.ui.R;

public class MainMenu extends SerialPortActivity
{
	private ToggleButton mBtnBtSwitch;
	private Button  mSetBtn;
	private Button    mAddBtn;
	private Button    mSubBtn;
	private Button    mDoubleHW;
	private Button mCutBtn ;
	private Button mCode39Btn;
	private Button mCutBtn5;
	private Button mCutBtn10;
	private EditText  mPriNumEdit;
	private int iPriNum=1;
	private Button    mClearBtn;
	private EditText  mSendEditText;
	private Button    mPrintBtn;
	public static boolean bChinese =true;
	private CheckBox mCheckBoxHex;
	private boolean bHex=false;
	public void Init()
	{
		mBtnBtSwitch = (ToggleButton) this.findViewById(R.id.tbtnSwitch);
		mBtnBtSwitch.setOnClickListener(new BtnListener());
		mSetBtn = (Button)findViewById(R.id.SetButton);
		mSetBtn.setOnClickListener(new BtnListener());
		mAddBtn = (Button)findViewById(R.id.AddBtn);
		mAddBtn.setOnClickListener(new BtnListener());
		mSubBtn = (Button)findViewById(R.id.SubBtn);
		mSubBtn.setOnClickListener(new BtnListener());
		mPriNumEdit=(EditText) findViewById(R.id.PRiNumEdit);
		mClearBtn=(Button)findViewById(R.id.BtnClear);
		mClearBtn.setOnClickListener(new BtnListener());
		mSendEditText = (EditText) findViewById(R.id.mEditSend);
		mSendEditText.setText("欢迎使用嵌入式打印机,0123456789abcdefghiklmnopqrstuvwxyz\n");
		mPrintBtn = (Button) findViewById(R.id.printButton);
		mPrintBtn.setOnClickListener(new BtnListener());
		mCheckBoxHex=(CheckBox)findViewById(R.id.CheckBoxHex);
		mCheckBoxHex.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				// TODO Auto-generated method stub
				bHex=isChecked;
			}
		});
		mDoubleHW=(Button) findViewById(R.id.DoubleHW);
		mDoubleHW.setOnClickListener(new BtnListener());
		mCutBtn = (Button) findViewById(R.id.CutBtn);
		mCutBtn.setOnClickListener(new BtnListener());
		mCode39Btn= (Button) findViewById(R.id.Code39);
		mCode39Btn.setOnClickListener(new BtnListener());
		mCutBtn5=(Button) findViewById(R.id.CutBtn5);
		mCutBtn5.setOnClickListener(new BtnListener());
		mCutBtn10=(Button) findViewById(R.id.CutBtn10);
		mCutBtn10.setOnClickListener(new BtnListener());

	}
	class BtnListener implements OnClickListener
	{
		public void onClick(View v)
		{
			if(v==mSetBtn)	//Wifi设置按钮
			{
				startActivity(new Intent(MainMenu.this, SerialPortPreferences.class));
			}
			if(v==mBtnBtSwitch)//
			{

				if(mSerialPort==null)
				{
					InitCom(); if(mSerialPort==null){mBtnBtSwitch.setChecked(false);mBtnBtSwitch.setText("打开串口");}else{mBtnBtSwitch.setChecked(true);mBtnBtSwitch.setText("关闭串口");}
				}
				else
				{
					CloseCom();
					if(mSerialPort==null){mBtnBtSwitch.setChecked(false);mBtnBtSwitch.setText("打开串口");}else{mBtnBtSwitch.setChecked(true);mBtnBtSwitch.setText("关闭串口");}
				}
			}
			if(v==mAddBtn)	//++按钮
			{
				iPriNum++;
				mPriNumEdit.setText(Integer.toString(iPriNum));
			}

			/***********************************减按钮**************************************/
			if(v==mSubBtn)	//--按钮
			{
				if(iPriNum>=2)
				{
					iPriNum--;
					mPriNumEdit.setText(Integer.toString(iPriNum));
				}
			}
			if(v==mClearBtn)
			{
				mSendEditText.setText("");
			}
			if(v==mDoubleHW)
			{
				byte SendBuf[]={0x1b,0x40,0x0a,0x1d,0x21,0x11,0x0a,(byte) 0xc2,(byte) 0xa1,(byte) 0xb1,(byte)0xa6,(byte)0xc8,(byte)0xc8,(byte)0xc3,(byte)0xf4,(byte)0xb4,(byte)0xf2,(byte)0xd3,(byte)0xa1,(byte)0xbb,(byte)0xfa,0x5f,(byte)0xb1,(byte)0xb6,(byte)0xb8,(byte)0xdf,(byte)0xb1,(byte)0xb6,(byte)0xbf,(byte) 0xed,(byte)0xd7,(byte)0xd6,(byte)0xcc,(byte)0xe5,0x0a,0x0a,0x0a};
				SendData(SendBuf);
			}
			if(v==mCutBtn)
			{
				byte SendBuf[]={0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x1b,0x69};
				SendData(SendBuf);
			}
			if(v==mCutBtn5)
			{
				byte SendBuf[]={0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x1b,0x69};
				SendData(SendBuf);
				SendData(SendBuf);
				SendData(SendBuf);
				SendData(SendBuf);
				SendData(SendBuf);
			}
			if(v==mCutBtn10)
			{
				byte SendBuf[]={0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x1b,0x69};
				SendData(SendBuf);
				SendData(SendBuf);
				SendData(SendBuf);
				SendData(SendBuf);
				SendData(SendBuf);
				SendData(SendBuf);
				SendData(SendBuf);
				SendData(SendBuf);
				SendData(SendBuf);
				SendData(SendBuf);
			}
			if(v==mCode39Btn)
			{
				byte SendBuf[]={0X0D, 0X0A, 0X1D, 0X48, 0X02, 0X1D, 0X68, (byte) 0X80, 0X1D, 0X77, 0X02, 0X1D, 0X6B, 0X45, 0X09, 0X30, 0X31, 0X32, 0X33, 0X34, 0X35, 0X36, 0X37, 0X38, 0X0D, 0X0A};
				SendData(SendBuf);
			}
			if(v==mPrintBtn)
			{
				iPriNum=Integer.parseInt(mPriNumEdit.getText().toString());
				String mSendData = mSendEditText.getText().toString();
				if(mSendData.length()==0)
				{
					if(bChinese)
						Toast.makeText(MainMenu.this, "发送数据不能为空！", Toast.LENGTH_SHORT).show();
					else
						Toast.makeText(MainMenu.this, "The input data is null", Toast.LENGTH_SHORT).show();
					return;
				}
				byte SendBuf[]={0x1b,0x40};
				SendData(SendBuf);
				if(iPriNum>1)//打印次数处理
				{
					String temp;
					temp=mSendData;
					for(int i=1;i<iPriNum;i++)
					{
						mSendData=mSendData+temp;
					}
				}
				try
				{
					byte[] send = null;
					send = mSendData.getBytes("GBK");
					if (!bHex) //如果未选中十六进制
					{
						SendData(send);
					}
					else //十六进制处理
					{
						int length = (send.length + 1) / 2;
						byte hexbytes[] = new byte[length];
						for (int i = 0; i < length; i++)
						{
							hexbytes[i] = 0;
						}
						if (StrToHex.String2Hex(send, hexbytes) == 0)
						{
							if(bChinese)
								Toast.makeText(MainMenu.this,"十六进制输入有误", Toast.LENGTH_SHORT).show();
							else
								Toast.makeText(MainMenu.this,"Hexadecimal character input Error", Toast.LENGTH_SHORT).show();
							return;
						}
						SendData(hexbytes);
					}
				}
				catch (UnsupportedEncodingException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}



	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main2);
		Init();
		if(mSerialPort==null){mBtnBtSwitch.setChecked(false);mBtnBtSwitch.setText("打开串口");}else{mBtnBtSwitch.setChecked(true);mBtnBtSwitch.setText("关闭串口");}

	}// end onCreate

	@Override
	protected void onDataReceived(byte[] buffer, int size)
	{
		// TODO Auto-generated method stub

	}
	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
		CloseCom();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) // && event.getRepeatCount() ==0
		{
			String str1,str2,str3,str4;
			AlertDialog.Builder builder = new Builder(this);
			if(bChinese)
			{
				str1="确定要退出吗?";
				str2="提示";
				str3="确认";
				str4="取消";
			}
			else
			{
				str1="Are you sure you want to exit?";
				str2="Exit?";
				str3="Yes";
				str4="No";
			}
			builder.setMessage(str1);

			builder.setTitle(str2);
			builder.setPositiveButton(str3,
					new android.content.DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int which)
						{
							dialog.dismiss();
							finish();
						}
					});
			builder.setNegativeButton(str4,new android.content.DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which)
				{
					dialog.dismiss();
				}
			});
			builder.create().show();
		}
		return true;
	}
}
