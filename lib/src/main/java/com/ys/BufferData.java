package com.ys;

/**
 * Created by wangtao on 2016/3/15.
 */
public class BufferData {

    private byte[] _list;
    private int _startMatch;
    private int _endMatch;
    public int length() {
        return _list.length;
    }

    public int getStartMatchIndex() {
        return _startMatch;
    }
    public int getEndMatchIndex() {
        return _endMatch;
    }

    public int getMatchedByteCount () {
        return _endMatch - _startMatch + 1;
    }

    public byte getByteByIndex(int index) {
        return _list[index];
    }

    public int getLastIndex () {
        return _list.length - 1;
    }

    public byte[] getBytes() {
        return _list;
    }
    public void clear() {
        _list = new byte[0];
    }

    public BufferData() {
        _list = new byte[0];
        _startMatch =0;
        _endMatch =0;
    }

    public void add(byte[] bytes) {
        _list = bytes;
    }

    public boolean match(byte startbyte, byte endbyte) {

        // 有的报文  可能 只有02  或者没有02
        boolean flag = false;
        for (int i =0; i< _list.length; i++) {
            if (_list[i] == startbyte) {
                flag = true; _startMatch = i;
            }
            if (flag && _list[i] == endbyte) {
                _endMatch = i + 1; return true;
            }
        }
        _startMatch = _endMatch = 0; return false;
    }
    public byte[] getMatchedBytes() {
        byte[] temp = new byte[getMatchedByteCount() - 1];
        //_list.CopyTo(_startMatch, temp, 0, getMatchedByteCount() - 1);
        for (int i = 0;i< getMatchedByteCount()-1; i++) {
            temp[i] = _list[_startMatch + i];
        }
        return temp;
    }

    public void removeMatched()
    {
        try
        {
            // index = _startMatch  count=_endMatch

            //_list.RemoveRange(_startMatch, _endMatch);
            _startMatch = 0; _endMatch = 0;
        }
        catch (Exception ex)
        {
        }
    }
}
