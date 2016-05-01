package com.ys;

/**
 * Created by wangtao on 2016/3/15.
 */
public class RobotEvent {

    private static RobotEvent robotEvent = new RobotEvent();

    public static RobotEvent getInstance () {
        return robotEvent;
    }

    public void handle(Object sender, RobotEventArg args)
    {
//        if (this.InvokeRequired)
//        {
//            this.BeginInvoke(new EventHandler<RobotEventArgs>(RobotEvents), new object[] { sender, args }); return;
//        }

        try
        {
            switch (args.getiCode())
            {
                case 0: // ------------------------------------------------------------------------------------- BOARD
                    String msg0 = (String)args.getParamObject();
                    if (!msg0.equals("OK")) {
                        //lblMsg.Text = msg0;
                    }
                    break;
                case 1: // ------------------------------------------------------------------------------- COIN / BILL
//                    switch (args.GetMsgCode())
//                    {
//                        case 1: // COIN 입수[5각]
//                            this.CASH_InMoney[1] += double.Parse(args.GetObject()) * 0.5;
//                            this.CASH_InMoney[0] = this.CASH_InMoney[1] + this.CASH_InMoney[2];
//                            this.lblMsg.Text = "입금=> " + this.CASH_InMoney[0].ToString(); break;
//                        case 2: // COIN 입수[1원]
//                            this.CASH_InMoney[1] += double.Parse(args.GetObject());
//                            this.CASH_InMoney[0] = this.CASH_InMoney[1] + this.CASH_InMoney[2];
//                            this.lblMsg.Text = "입금=> " + this.CASH_InMoney[0].ToString(); break;
//                        case 3: // BILL 입수
//                            this.CASH_InMoney[2] += double.Parse(args.GetObject());
//                            this.CASH_InMoney[0] = this.CASH_InMoney[1] + this.CASH_InMoney[2];
//                            this.lblMsg.Text = "입금=> " + this.CASH_InMoney[0].ToString(); break;
//                        case 4: // BILL 입수실패
//                            this.lblMsg.Text = "BILL 입수실패"; break;
//                        case 5: // 동전 반환중
//                            this.lblMsg.Text = (string)args.GetObject(); break;
//                        case 6: // 거스름 배출 확인(배출금확인)
//                            this.CASH_InMoney = new double[] { 0.0, 0.0, 0.0 };
//                            this.lblMsg.Text = (string)args.GetObject(); break;
//                        case 7: // 거스름 반환중 에러
//                            this.lblMsg.Text = (string)args.GetObject(); break;
//                        case 8: // 거스름 가능매수 확인
//                            this.CASH_OutEnabled = args.GetMoney()[0] * 0.5 + (double)args.GetMoney()[1];
//                            this.lblMsg.Text = "OutEnabled Money=>" + this.CASH_OutEnabled.ToString(); break;
//                        case 9: // ERROR MSG
//                            string msg1 = (string)args.GetObject();
//                            if (!msg1.Equals("OK")) { lblMsg.Text = msg1; }
//                            break;
//                    }
                    break;
                case 2: // -------------------------------------------------------------------------------------- RACK
//                    switch (args.GetMsgCode())
//                    {
//                        case 1: // 장비세부상태
//                            string msg3 = (string)args.GetObject();
//                            if (!msg3.Equals("OK")) { lblMsg.Text = msg3; }
//                            break;
//                        case 2: this.lblMsg.Text = args.GetObject() + " 추출   중...,"; this.lblMsg.Text = (string)args.GetObject() + " 추출   중...,"; break;
//                        case 3: this.lblMsg.Text = args.GetObject() + " 추출 완료...,"; this.lblMsg.Text = (string)args.GetObject() + " 추출 완료...,"; break;
//                        case 4: this.lblMsg.Text = args.GetObject() + " 추출 실패...,"; this.lblMsg.Text = (string)args.GetObject() + " 추출 실패...,"; break;
//                        case 5: this.lblMsg.Text = args.GetObject() + " 추출 에러...,"; this.lblMsg.Text = (string)args.GetObject() + " 추출 에러...,"; break;
//                        case 6: this.lblMsg.Text = args.GetObject() + " 배출   중...,"; this.lblMsg.Text = (string)args.GetObject() + " 배출   중...,"; break;
//                        case 7: this.lblMsg.Text = args.GetObject() + " 배출 완료...,"; this.lblMsg.Text = (string)args.GetObject() + " 배출 완료...,"; break;
//                        case 8: this.lblMsg.Text = args.GetObject() + " 배출 실패...,"; this.lblMsg.Text = (string)args.GetObject() + " 배출 실패...,"; break;
//                        case 9: this.lblMsg.Text = args.GetObject() + " 배출 에러...,"; this.lblMsg.Text = (string)args.GetObject() + " 배출 에러...,"; break;
//                        case 10: // ERROR MSG
//                            string msg9 = (string)args.GetObject();
//                            if (!msg9.Equals("OK")) { lblMsg.Text = msg9; }
//                            break;
//                    }
                    break;
                case 3: // -------------------------------------------------------------------------------------- 상태에러
                    // this.lblMsg.Text = args.GetObject(); break;
                case 4: // -------------------------------------------------------------------------------------- 품절상태
                    // this.lblMsg.Text = "脱销: " + args.GetObject(); break;
            }
        }
        catch (Exception ex)
        {

        }
    }

}
