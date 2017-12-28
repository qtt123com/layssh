package com.gt.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;

/**
 * 
 * @功能说明：MD5数据摘要算法。
 * @作者： herun
 * @创建日期：2014-3-9
 * @版本号：V1.0
 */
public class MD5 {

	/**
	 * 返回MD5数据摘要字符串。
	 *
	 * @param s
	 *            字符串。
	 * @return MD5数据摘要字符串。
	 */
	public String getMD5Str(String s) {
		md5Init();
		try {
			md5Update(s.getBytes("utf-8"), s.length());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		md5Final();
		digestHexStr = "";
		for (int i = 0; i < 16; i++)
			digestHexStr += byteHEX(digest[i]);

		return digestHexStr;
	}

	public MD5() {
		state = new long[4];
		count = new long[2];
		buffer = new byte[64];
		digest = new byte[16];
		md5Init();
	}

	private void md5Init() {
		count[0] = 0L;
		count[1] = 0L;
		state[0] = 1732584193L;
		state[1] = 4023233417L;
		state[2] = 2562383102L;
		state[3] = 271733878L;
	}

	private long F(long l, long l1, long l2) {
		return l & l1 | ~l & l2;
	}

	private long G(long l, long l1, long l2) {
		return l & l2 | l1 & ~l2;
	}

	private long H(long l, long l1, long l2) {
		return l ^ l1 ^ l2;
	}

	private long I(long l, long l1, long l2) {
		return l1 ^ (l | ~l2);
	}

	private long FF(long l, long l1, long l2, long l3, long l4, long l5, long l6) {
		l += F(l1, l2, l3) + l4 + l6;
		l = (int) l << (int) l5 | (int) l >>> (int) (32L - l5);
		l += l1;
		return l;
	}

	private long GG(long l, long l1, long l2, long l3, long l4, long l5, long l6) {
		l += G(l1, l2, l3) + l4 + l6;
		l = (int) l << (int) l5 | (int) l >>> (int) (32L - l5);
		l += l1;
		return l;
	}

	private long HH(long l, long l1, long l2, long l3, long l4, long l5, long l6) {
		l += H(l1, l2, l3) + l4 + l6;
		l = (int) l << (int) l5 | (int) l >>> (int) (32L - l5);
		l += l1;
		return l;
	}

	private long II(long l, long l1, long l2, long l3, long l4, long l5, long l6) {
		l += I(l1, l2, l3) + l4 + l6;
		l = (int) l << (int) l5 | (int) l >>> (int) (32L - l5);
		l += l1;
		return l;
	}

	private void md5Update(byte abyte0[], int i) {
		byte abyte1[] = new byte[64];
		int k = (int) (count[0] >>> 3) & 63;
		if ((count[0] += i << 3) < (long) (i << 3))
			count[1]++;
		count[1] += i >>> 29;
		int l = 64 - k;
		int j;
		if (i >= l) {
			md5Memcpy(buffer, abyte0, k, 0, l);
			md5Transform(buffer);
			for (j = l; j + 63 < i; j += 64) {
				md5Memcpy(abyte1, abyte0, 0, j, 64);
				md5Transform(abyte1);
			}

			k = 0;
		} else {
			j = 0;
		}
		md5Memcpy(buffer, abyte0, k, j, i - j);
	}

	private void md5Final() {
		byte abyte0[] = new byte[8];
		Encode(abyte0, count, 8);
		int i = (int) (count[0] >>> 3) & 63;
		int j = i >= 56 ? 120 - i : 56 - i;
		md5Update(PADDING, j);
		md5Update(abyte0, 8);
		Encode(digest, state, 16);
	}

	private void md5Memcpy(byte abyte0[], byte abyte1[], int i, int j, int k) {
		for (int l = 0; l < k; l++)
			abyte0[i + l] = abyte1[j + l];

	}

	private void md5Transform(byte abyte0[]) {
		long l = state[0];
		long l1 = state[1];
		long l2 = state[2];
		long l3 = state[3];
		long al[] = new long[16];
		Decode(al, abyte0, 64);
		l = FF(l, l1, l2, l3, al[0], 7L, 3614090360L);
		l3 = FF(l3, l, l1, l2, al[1], 12L, 3905402710L);
		l2 = FF(l2, l3, l, l1, al[2], 17L, 606105819L);
		l1 = FF(l1, l2, l3, l, al[3], 22L, 3250441966L);
		l = FF(l, l1, l2, l3, al[4], 7L, 4118548399L);
		l3 = FF(l3, l, l1, l2, al[5], 12L, 1200080426L);
		l2 = FF(l2, l3, l, l1, al[6], 17L, 2821735955L);
		l1 = FF(l1, l2, l3, l, al[7], 22L, 4249261313L);
		l = FF(l, l1, l2, l3, al[8], 7L, 1770035416L);
		l3 = FF(l3, l, l1, l2, al[9], 12L, 2336552879L);
		l2 = FF(l2, l3, l, l1, al[10], 17L, 4294925233L);
		l1 = FF(l1, l2, l3, l, al[11], 22L, 2304563134L);
		l = FF(l, l1, l2, l3, al[12], 7L, 1804603682L);
		l3 = FF(l3, l, l1, l2, al[13], 12L, 4254626195L);
		l2 = FF(l2, l3, l, l1, al[14], 17L, 2792965006L);
		l1 = FF(l1, l2, l3, l, al[15], 22L, 1236535329L);
		l = GG(l, l1, l2, l3, al[1], 5L, 4129170786L);
		l3 = GG(l3, l, l1, l2, al[6], 9L, 3225465664L);
		l2 = GG(l2, l3, l, l1, al[11], 14L, 643717713L);
		l1 = GG(l1, l2, l3, l, al[0], 20L, 3921069994L);
		l = GG(l, l1, l2, l3, al[5], 5L, 3593408605L);
		l3 = GG(l3, l, l1, l2, al[10], 9L, 38016083L);
		l2 = GG(l2, l3, l, l1, al[15], 14L, 3634488961L);
		l1 = GG(l1, l2, l3, l, al[4], 20L, 3889429448L);
		l = GG(l, l1, l2, l3, al[9], 5L, 568446438L);
		l3 = GG(l3, l, l1, l2, al[14], 9L, 3275163606L);
		l2 = GG(l2, l3, l, l1, al[3], 14L, 4107603335L);
		l1 = GG(l1, l2, l3, l, al[8], 20L, 1163531501L);
		l = GG(l, l1, l2, l3, al[13], 5L, 2850285829L);
		l3 = GG(l3, l, l1, l2, al[2], 9L, 4243563512L);
		l2 = GG(l2, l3, l, l1, al[7], 14L, 1735328473L);
		l1 = GG(l1, l2, l3, l, al[12], 20L, 2368359562L);
		l = HH(l, l1, l2, l3, al[5], 4L, 4294588738L);
		l3 = HH(l3, l, l1, l2, al[8], 11L, 2272392833L);
		l2 = HH(l2, l3, l, l1, al[11], 16L, 1839030562L);
		l1 = HH(l1, l2, l3, l, al[14], 23L, 4259657740L);
		l = HH(l, l1, l2, l3, al[1], 4L, 2763975236L);
		l3 = HH(l3, l, l1, l2, al[4], 11L, 1272893353L);
		l2 = HH(l2, l3, l, l1, al[7], 16L, 4139469664L);
		l1 = HH(l1, l2, l3, l, al[10], 23L, 3200236656L);
		l = HH(l, l1, l2, l3, al[13], 4L, 681279174L);
		l3 = HH(l3, l, l1, l2, al[0], 11L, 3936430074L);
		l2 = HH(l2, l3, l, l1, al[3], 16L, 3572445317L);
		l1 = HH(l1, l2, l3, l, al[6], 23L, 76029189L);
		l = HH(l, l1, l2, l3, al[9], 4L, 3654602809L);
		l3 = HH(l3, l, l1, l2, al[12], 11L, 3873151461L);
		l2 = HH(l2, l3, l, l1, al[15], 16L, 530742520L);
		l1 = HH(l1, l2, l3, l, al[2], 23L, 3299628645L);
		l = II(l, l1, l2, l3, al[0], 6L, 4096336452L);
		l3 = II(l3, l, l1, l2, al[7], 10L, 1126891415L);
		l2 = II(l2, l3, l, l1, al[14], 15L, 2878612391L);
		l1 = II(l1, l2, l3, l, al[5], 21L, 4237533241L);
		l = II(l, l1, l2, l3, al[12], 6L, 1700485571L);
		l3 = II(l3, l, l1, l2, al[3], 10L, 2399980690L);
		l2 = II(l2, l3, l, l1, al[10], 15L, 4293915773L);
		l1 = II(l1, l2, l3, l, al[1], 21L, 2240044497L);
		l = II(l, l1, l2, l3, al[8], 6L, 1873313359L);
		l3 = II(l3, l, l1, l2, al[15], 10L, 4264355552L);
		l2 = II(l2, l3, l, l1, al[6], 15L, 2734768916L);
		l1 = II(l1, l2, l3, l, al[13], 21L, 1309151649L);
		l = II(l, l1, l2, l3, al[4], 6L, 4149444226L);
		l3 = II(l3, l, l1, l2, al[11], 10L, 3174756917L);
		l2 = II(l2, l3, l, l1, al[2], 15L, 718787259L);
		l1 = II(l1, l2, l3, l, al[9], 21L, 3951481745L);
		state[0] += l;
		state[1] += l1;
		state[2] += l2;
		state[3] += l3;
	}

	private void Encode(byte abyte0[], long al[], int i) {
		int j = 0;
		for (int k = 0; k < i; k += 4) {
			abyte0[k] = (byte) (int) (al[j] & 255L);
			abyte0[k + 1] = (byte) (int) (al[j] >>> 8 & 255L);
			abyte0[k + 2] = (byte) (int) (al[j] >>> 16 & 255L);
			abyte0[k + 3] = (byte) (int) (al[j] >>> 24 & 255L);
			j++;
		}

	}

	private void Decode(long al[], byte abyte0[], int i) {
		int j = 0;
		for (int k = 0; k < i; k += 4) {
			al[j] = b2iu(abyte0[k]) | b2iu(abyte0[k + 1]) << 8
					| b2iu(abyte0[k + 2]) << 16 | b2iu(abyte0[k + 3]) << 24;
			j++;
		}

	}

	public static long b2iu(byte byte0) {
		return byte0 >= 0 ? byte0 : byte0 & 255;
	}

	public static String byteHEX(byte byte0) {
		char ac[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char ac1[] = new char[2];
		ac1[0] = ac[byte0 >>> 4 & 15];
		ac1[1] = ac[byte0 & 15];
		String s = new String(ac1);
		return s;
	}

	public static void main(String args[]) {
		MD5 md5 = new MD5();
		if (Array.getLength(args) == 0) {
			System.out.println("MD5 Test suite:");
			System.out.println("MD5(\"0\"):" + md5.getMD5Str("0"));
		} else {
			System.out
					.println("MD5(" + args[0] + ")=" + md5.getMD5Str(args[0]));
		}
	}

	static final int S11 = 7;

	static final int S12 = 12;

	static final int S13 = 17;

	static final int S14 = 22;

	static final int S21 = 5;

	static final int S22 = 9;

	static final int S23 = 14;

	static final int S24 = 20;

	static final int S31 = 4;

	static final int S32 = 11;

	static final int S33 = 16;

	static final int S34 = 23;

	static final int S41 = 6;

	static final int S42 = 10;

	static final int S43 = 15;

	static final int S44 = 21;

	static final byte PADDING[] = { -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0 };

	private long state[];

	private long count[];

	private byte buffer[];

	public String digestHexStr;

	private byte digest[];

}
