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

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ys.BytesUtil;
import com.ys.GetBytesUtils;
import com.ys.ui.R;

import java.io.IOException;

/**
 * 出货
 */
public class SelectGoodsActivity extends SerialPortActivity {

	SendingThread mSendingThread;
	byte[] mBuffer;

	private Button btnSelect;
	private Button btnOut;

	private EditText backNo;

	private EditText dataSend;
	private EditText dataRecive;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_goods);

		backNo = (EditText)findViewById(R.id.editText);
		btnSelect = (Button)findViewById(R.id.btn_select);
		btnOut = (Button)findViewById(R.id.btn_out);
		dataRecive = (EditText)findViewById(R.id.text_recive);
		dataSend = (EditText)findViewById(R.id.text_send);
		//billOff = (Button)findViewById(R.id.btn_billoff);
		//standBy = (Button)findViewById(R.id.btn_standby);

		//billOff.setOnClickListener(listener1);
		//standBy.setOnClickListener(listener2);
		btnSelect.setOnClickListener(listener3);
		btnOut.setOnClickListener(listener4);

		//  打开串口
	}



		@Override
		protected void onDataReceived(final byte[] buffer, final int size) {
			// ignore incoming data
			//dataRecive.setText(dataRecive.getText()+ "\n" + BytesUtil.bytesToHexString(buffer));
			//dataRecive.append( BytesUtil.bytesToHexString(buffer));
			runOnUiThread(new Runnable() {
				public void run() {
					//if (mReception != null) {
					// 处理返回参数
					dataRecive.append("[HEX]:"+new String(buffer, 0, size)+"\n");
					//}
				}
			});
		}

	View.OnClickListener listener1 = new View.OnClickListener() {
		public void onClick(View v) {

			mBuffer = null;
			mBuffer = GetBytesUtils.billInhibit(0);
			String str = BytesUtil.bytesToHexString(mBuffer);
			if (str != null) {
				dataSend.append(str);
			}
			if (mSerialPort != null) {
				mSendingThread = new SendingThread();
				mSendingThread.start();
			}
		}
	};
	View.OnClickListener listener2 = new View.OnClickListener() {
		public void onClick(View v) {

			mBuffer = null;
			mBuffer = GetBytesUtils.standby();
			String str = BytesUtil.bytesToHexString(mBuffer);
			if (str != null) {
				dataSend.append(str);
			}
			if (mSerialPort != null) {
				mSendingThread = new SendingThread();
				mSendingThread.start();
			}
		}
	};
      View.OnClickListener listener3 = new View.OnClickListener() {
		public void onClick(View v) {

			CharSequence charSequence =  backNo.getText();
			byte no = BytesUtil.toByteArray(Integer.valueOf(charSequence.toString()));
            mBuffer = null;


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
			while (!isInterrupted()) {
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
	}
}
