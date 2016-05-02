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

import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidParameterException;

 import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.widget.Toast;

import com.ys.ui.R;
import com.ys.ui.base.App;

import android_serialport_api.SerialPort;

public abstract class SerialPortActivity extends Activity 
{

	public App mApplication;
	public SerialPort mSerialPort=null;
	public OutputStream mOutputStream;

	private void DisplayError(int resourceId) 
	{
		AlertDialog.Builder b = new AlertDialog.Builder(this);
		b.setTitle("Error");
		b.setMessage(resourceId);
		b.setPositiveButton("OK", new OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				//SerialPortActivity.this.finish();
			}
		});
		b.show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
	//	InitCom();
	}
	public void InitCom()
	{
		mApplication = (App) getApplication();
		try 
		{
			mSerialPort = mApplication.getSerialPort();
			mOutputStream = mSerialPort.getOutputStream();
		} catch (SecurityException e) 
		{
			DisplayError(R.string.error_security);
		} catch (IOException e) 
		{
			DisplayError(R.string.error_unknown);
		} catch (InvalidParameterException e) 
		{
			DisplayError(R.string.error_configuration);
		}	
	}
	public void SendData(byte[] bytedata)
	{
		if (mSerialPort != null) 
		{
			try 
			{
				if (mOutputStream != null) 
				{
					mOutputStream.write(bytedata);
				} else 
				{
				}
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		else
		{
			Toast.makeText(SerialPortActivity.this,"���ȴ򿪴���", Toast.LENGTH_SHORT).show();
		}
	}
	public void CloseCom()
	{
		mApplication.closeSerialPort();
		mSerialPort = null;
	}
	
	protected abstract void onDataReceived(final byte[] buffer, final int size);

	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
		//CloseCom();
	}
}
