/*
 * Copyright 2011 Cedric Priscal
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

package com.ys.ui.sample;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ys.BytesUtil;
import com.ys.GetBytesUtils;
import com.ys.ui.R;
import com.ys.ui.base.App;
import com.ys.ui.serial.salemachine.SerialMachineActivity;
import com.ys.ui.utils.ToastUtils;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 出货
 */
public class SelectGoodsActivity extends SerialMachineActivity {

	SendingThread mSendingThread;
	byte[] mBuffer;

	private Button btnSelect;
	private Button btnOut;

	private EditText backNo;

	private EditText dataSend;
	private EditText dataRecive;


	@Override
	protected void onCreate(Bundle savedInstanceState) {


		try {
			super.onCreate(savedInstanceState);

 			setContentView(R.layout.select_goods);

			backNo = (EditText)findViewById(R.id.editText);
			btnSelect = (Button)findViewById(R.id.btn_select);
			btnOut = (Button)findViewById(R.id.btn_out);
			dataRecive = (EditText)findViewById(R.id.text_recive);
			dataSend = (EditText)findViewById(R.id.text_send);
			btnSelect.setOnClickListener(listener3);
			btnOut.setOnClickListener(listener4);


		} catch (Exception e) {
			 ToastUtils.showError(e.getMessage(), this);
			return;
		}
		//  打开串口
	}


//

		@Override
		protected  void onDataReceived(final byte[] buffer) {


			runOnUiThread(new Runnable() {
				public void run() {

					ToastUtils.showError(new String(buffer), App.getContext());
					//byte[] buf = new byte[packlen];
				    //System.arraycopy(buffer, index, buf, 0, packlen);
					// 处理返回参数
					dataRecive.append("[rev-HEX]:" + new String(buffer, 0, buffer.length)+"\n");

					//}
				}
			});

		}


      View.OnClickListener listener3 = new View.OnClickListener() {
		public void onClick(View v) {

			CharSequence charSequence =  backNo.getText();
			byte no = Integer.valueOf(charSequence.toString(), 16).byteValue();

            mBuffer = null;

			//byte no = Byte.parseByte(charSequence.toString());

			//byte[] no = charSequence.toString().getBytes();
			mBuffer = GetBytesUtils.goodsSelect(no);
//			String str = BytesUtil.bytesToHexString(mBuffer);
			dataSend.append("send[HEX]:");
			for (byte b : mBuffer) {
				dataSend.append(BytesUtil.byteToHexString(b));
			}
			dataSend.append("\n");
			if (mSerialPort != null) {
				mSendingThread = new SendingThread();
				mSendingThread.start();
			}
 		}
	};
	View.OnClickListener listener4 = new View.OnClickListener() {
		public void onClick(View v) {

			mBuffer = null;
			mBuffer = GetBytesUtils.goodsOuter();
			String str = BytesUtil.bytesToHexString(mBuffer);
			dataSend.append("send[HEX]:");
			if (str != null) {
				dataSend.append(str);
			}
			dataSend.append("\n");

			if (mSerialPort != null) {
				mSendingThread = new SendingThread();
				mSendingThread.start();
			}
 		}
	};

	private class SendingThread extends Thread {
		@Override
		public void run() {
//			while (!isInterrupted()) {
				try {
					if (mOutputStream != null) {
						mOutputStream.write(mBuffer, 0, mBuffer.length);
					} else {
						return;
					}
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
//			}
		}
	}
//
//	private void startTimer() {
////
////		long startTime = System.currentTimeMillis();
////
////		while (true) {
////
//////			if (System.currentTimeMillis() - startTime > 20000) {
//////				break;
//////			} else {
//////				dataSend.append("[timer]:"+ timer +"timer1="+timer1 + "timer2="+timer2 +"timer3="+timer3 + "timer43="+conTimer + " \n");
//////				try {
//////					Thread.sleep(500);
//////				} catch (InterruptedException e) {
//////					e.printStackTrace();
//////				}
//////			}
////
////		}
//		try {
//			Thread.sleep(15000);
//			int i =  list.size()<10000 ? list.size() : 10000;
//
//			for (int j=0; j<list.size(); j++) {
//				if (j > 0 && equals(list.get(j), list.get(j-1)) ) {
//					continue;
//				}
//				dataSend.append("[rev-HEX]:" + BytesUtil.bytesToHexString(list.get(j)) +"\n");
//				if(timer++>1000)
//				{
//
//					dataSend.append("[rev-HEX]:" + BytesUtil.bytesToHexString(list.get(list.size()-1)) +"\n");
//					dataSend.append("[rev-HEX]:" + BytesUtil.bytesToHexString(list.get(list.size()-2)) +"\n");
//					break;
//				}
//			}
//			dataSend.append("[rev-HEX]:" + list.size() + "timer:" + timer +"\n");
//
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}

//	private boolean equals(byte[] list1, byte[] list2) {
//
//		int size = list1.length;
//		int size2 = list2.length;
//		if (size != size2) {
//			return false;
//		}
//		for (int i = 0; i< size ; i++) {
//			if (list1[i] != list2[i]) {
//				return false;
//			}
//		}
//		return true;
//	}


}
