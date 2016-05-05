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

package com.ys.ui.serial.salemachine;

import android.app.Activity;
import android.os.Bundle;

import com.ys.ui.base.App;
import com.ys.ui.utils.ToastUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import android_serialport_api.SerialPort;

public abstract class SerialMachineActivity extends Activity {


	protected App mApplication;
	protected SerialPort mSerialPort;
	protected OutputStream mOutputStream;
	private InputStream mInputStream;
	private ReadThread mReadThread;
	protected int baudrate  = 19200;

	protected String path = "/dev/ttyES1";

	boolean begin = false;
	boolean end = false;
	/**
	 * 读取终端设备数据
	 * @author Administrator
	 */
	private class ReadThread extends Thread {

		@Override
		public void run() {
			super.run();

			// 定义一个包的最大长度
			int maxLength = 32;
			//byte[] buffer = new byte[maxLength];
			// 每次收到实际长度
			int available = 0;
			// 当前已经收到包的总长度
			int currentLength = 0;
			// 协议头长度4个字节（开始符1，类型1，长度2）
			int headerLength = 4;

			while (!isInterrupted()) {
				byte[] buffer = new byte[maxLength];
				try {
					available = mInputStream.available();
					if (available > 0) {
						// 防止超出数组最大长度导致溢出
						if (available > maxLength - currentLength) {
							available = maxLength - currentLength;
						}
						mInputStream.read(buffer, currentLength, available);
						currentLength += available;

						if (buffer[0]==0x02) {
							begin = true;
						}

                        List<Byte> temp=new ArrayList<>();
						for (int i=0; i<buffer.length;i++) {
							if (buffer[i] == 0x02) {
								begin = true;
								end = false;
							}

							if(begin&&!end)
							{

								temp.add(buffer[i]);
							}

							if(buffer[i]==0x03)
							{

								// 需要兼容 02  和下面的包分开的情况

                                if (!begin && end) {

									byte[] temp2=new byte[temp.size()+1];
									temp2[0] = 0x02;
									for (int k = 1; k< temp.size()+1 ; k++) {
										temp2[k] = temp.get(k);
									}
									onDataReceived(buffer);

								} else {
									byte[] temp2=new byte[temp.size()];
									for (int k = 0; k< temp.size() ; k++) {
										temp2[k] = temp.get(k);
									}

 									onDataReceived(buffer);
								}

								temp.clear();
								end=true;
								begin = false;

							}




						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

//				int cursor = 0;

				// 如果当前收到包大于头的长度，则解析当前包
//				while (currentLength >= headerLength) {
//					timer++;
//					// 取到头部第一个字节
//					if (buffer[cursor] != 0x02) {
//						--currentLength;
//						++cursor;
//						conTimer++;
//						continue;
//					}
//
//					int contentLenght = parseLen(buffer, cursor, headerLength);
//					// 如果内容包的长度大于最大内容长度或者小于等于0，则说明这个包有问题，丢弃
//					if (contentLenght <= 0 || contentLenght > maxLength - 5) {
//						currentLength = 0;
//						timer3++;
//						break;
//					}
//					// 如果当前获取到长度小于整个包的长度，则跳出循环等待继续接收数据
//					int factPackLen = contentLenght + 5;
//					if (currentLength < contentLenght + 5) {
//						timer2++;
//						break;
//					}
//
//					// 一个完整包即产生
////					 proceOnePacket(buffer,i,factPackLen);
//					timer1++;
//					onDataReceived(buffer, cursor, factPackLen);
//					currentLength -= factPackLen;
//					cursor += factPackLen;
//				}
//				// 残留字节移到缓冲区首
//				if (currentLength > 0 && cursor > 0) {
//					System.arraycopy(buffer, cursor, buffer, 0, currentLength);
//				}
			}
		}
	}

	/**
	 * 获取协议内容长度
	 * @param
	 * @return
	 */
	public int parseLen(byte buffer[], int index, int headerLength) {

		if (buffer.length - index < headerLength) { return 0; }
		byte a = buffer[index + 2];
		byte b = buffer[index + 3];
		int rlt = 0;
		if (((a >> 7) & 0x1) == 0x1) {
			rlt = (((a & 0x7f) << 8) | b);
		} else {
			char[] tmp = new char[2];
			tmp[0] = (char) a;
			tmp[1] = (char) b;
			String s = new String(tmp, 0, 2);
			rlt = Integer.parseInt(s, 16);
		}

		return rlt;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mApplication = (App)getApplication();
//
		try {
			path = mApplication.getSale_path();

			baudrate = mApplication.getSale_baudrate();

 			mSerialPort = mApplication.getSerialPort(path, baudrate);

			mOutputStream = mSerialPort.getOutputStream();
			mInputStream = mSerialPort.getInputStream();
			/* Create a receiving thread */
			mReadThread = new ReadThread();
			mReadThread.start();
		} catch (SecurityException e) {
			//DisplayError(R.string.error_security);
            ToastUtils.showError("SecurityException:" + e.getMessage(), this);
		} catch (InvalidParameterException e) {
			ToastUtils.showError("InvalidParameterException:" + e.getMessage(), this);
		} catch (Exception e) {
			ToastUtils.showError("Exception:" + e.getMessage(), this);

		}
	}

	//protected abstract void onDataReceived(final byte[] buffer, final int size);
	protected abstract void onDataReceived(final byte[] buffer);
//	protected abstract void onDataReceived(final byte[] buffer, final int index, final int packlen) {
////		runOnUiThread(new Runnable() {
////			public void run() {
////				//if (mReception != null) {
////				// 处理返回参数
////				byte[] buf = new byte[packlen];
////				System.arraycopy(buffer, index, buf, 0, packlen);
////				onDataReceived(buffer, packlen);                //}
////			}
////		});
//
//		//ProtocolAnalyze.getInstance(myHandler).analyze(buf);
//	}
	@Override
	protected void onDestroy() {
		if (mReadThread != null)
			mReadThread.interrupt();
		mApplication.closeSerialPort();
		mSerialPort = null;
		super.onDestroy();
	}


}
