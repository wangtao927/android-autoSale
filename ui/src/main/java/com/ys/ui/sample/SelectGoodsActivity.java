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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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

	}


//

	protected void onDataReceived(final byte[] buff )  {


		runOnUiThread(new Runnable() {
			public void run() {
				// 是正确的返回结果
				dataRecive.append("[rev-HEX]:" + BytesUtil.bytesToHexString(buff) +"\n");

				switch (buff[1]) {

					case 0x0E: // ------------------------------------------------------------------------------- 提货
						if (buff[2] == 0xF2) buff[2] = 0x02;
						if (buff[2] == 0xF3) buff[2] = 0x03;
						switch (buff[3]) {
							case 0x00:
								//queue.add(new RobotEventArg(2, 2, Integer.valueOf(buff[2]).toString()));
								break; // 提取中
							case 0x55:
								//queue.add(new RobotEventArg(2, 3, Integer.valueOf(buff[2]).toString()));
								// 选货结束  发送出货命令

								mBuffer = GetBytesUtils.goodsOuter();

								if (mSendingThread != null) {
									mSendingThread.interrupt();
								}
								mSendingThread = new SendingThread();
								mSendingThread.start();
//								startTimer();
								break; // 提取完毕
							case (byte) 0xFF:


 								//queue.add(new RobotEventArg(2, 4, Integer.valueOf(buff[2]).toString()));
								break; // 提取失败
							case (byte) 0xEE:
 								//queue.add(new RobotEventArg(2, 4, "提取错误"));
								break; // 提取错误
						}
						break;
					case 0x08: // 商品出货
						switch (buff[2]) {
							case 0x00:
								// 出货中    等待
								// queue.add(new RobotEventArg(2, 5, "排放中"));
								break;
							case 0x55:
								// 出货完成    结束出货流程
								//queue.add(new RobotEventArg(2, 6, "排放完毕"));
								// 出货成功  结束
  								ToastUtils.showShortMessage("交易成功");

								//mSendingThread.interrupt();
								break;
							case (byte) 0xFF:
								// 出货失败，结束出货流程
								// 出货失败：
 								// queue.add(new RobotEventArg(2, 7, "排放失败"));
								break;
							case (byte) 0xEE:
								// 出货错误

								// queue.add(new RobotEventArg(2, 7, "排放错误"));
								break;
							default:
								break;
						}
						break;


				}
			}


		});
	}


      View.OnClickListener listener3 = new View.OnClickListener() {
		public void onClick(View v) {

			CharSequence charSequence =  backNo.getText();
			byte no = Integer.valueOf(charSequence.toString(), 16).byteValue();

            mBuffer = null;

			mBuffer = GetBytesUtils.goodsSelect(no);
			dataSend.append("send[HEX]:");
			for (byte b : mBuffer) {
				dataSend.append(BytesUtil.byteToHexString(b));
			}
			dataSend.append("\n");
			if (mSerialPort != null) {
				mSendingThread = new SendingThread();
				mSendingThread.start();
			}
			//startTimer();

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
			//startTimer();

		}
	};

	private class SendingThread extends Thread {
		@Override
		public void run() {
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
//			try {
//				Thread.sleep(10000);
//			} catch (InterruptedException e1) {
//				e1.printStackTrace();
//			}
//
//			int size = list.size() > 1000 ? 1000: list.size();
//
//			for (int j=0; j<size; j++) {
//
//					dataSend.append("[rev-HEX]:" + BytesUtil.bytesToHexString(list.get(j)) +"\n");
//				}
//
//			 for(int j =0 ;j< list1.size();j++) {
//				 dataRecive.append("[rev-HEX]:" +  BytesUtil.bytesToHexString(list1.get(j)) +"\n");
//
//			 }
//			//dataRecive.append("[rev-HEX]:" + list1.size() +"\n");
//			dataSend.append("[rev-HEX]:" + list.size() + "  timer=" + timer + "\n");
//
//			list.clear();
//			list1.clear();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	private boolean equals(byte[] list1, byte[] list2) {

		int size = list1.length;
		int size2 = list2.length;
		if (size != size2) {
			return false;
		}
		for (int i = 0; i< size ; i++) {
			if (list1[i] != list2[i]) {
				return false;
			}
		}
		return true;
	}


}
