package com.gt.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 字符串处理工具
 * 
 * @author herun
 * @createTime 2015年2月9日-下午5:19:53
 * @see 无依赖
 */
@SuppressWarnings({ "unused" })
public class ByteHandleImpl {

	/**
	 * 从字节数组到十六进制字符串转换
	 * 
	 * @author herun
	 * @createTime 2015-2-9-上午9:25:04
	 * @param b字节数组
	 * @return 十六进制字符串
	 */
	public static String bytesToHexString(byte[] b) {
		byte[] hex = "0123456789ABCDEF".getBytes();
		byte[] buff = new byte[2 * b.length];
		for (int i = 0; i < b.length; i++) {
			buff[2 * i] = hex[(b[i] >> 4) & 0x0f];
			buff[2 * i + 1] = hex[b[i] & 0x0f];
		}
		return new String(buff);
	}

	/**
	 * 从十六进制字符串到字节数组转换
	 * 
	 * @author herun
	 * @createTime 2015-2-9-上午9:26:09
	 * @param hexstr十六进制字符
	 * @return 字节数组
	 */
	public static byte[] hexStringToByte(String hexstr) {
		byte[] b = new byte[hexstr.length() / 2];
		int j = 0;
		for (int i = 0; i < b.length; i++) {
			char c0 = hexstr.charAt(j++);
			char c1 = hexstr.charAt(j++);
			b[i] = (byte) ((parse(c0) << 4) | parse(c1));
		}
		return b;
	}

	/**
	 * 把字节数组转换为对象
	 * 
	 * @author herun
	 * @createTime 2015-2-9-上午9:27:27
	 * @param bytes字节数组
	 * @return 对象
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static final Object bytesToObject(byte[] bytes) throws IOException, ClassNotFoundException {
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		ObjectInputStream oi = new ObjectInputStream(in);
		Object o = oi.readObject();
		oi.close();
		return o;
	}

	/**
	 * 把可序列化对象转换成字节数组
	 * 
	 * @author herun
	 * @createTime 2015-2-9-上午9:28:02
	 * @param s可序列化对象
	 * @return 字节数组
	 * @throws IOException
	 */
	public static final byte[] objectToBytes(Serializable s) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream ot = new ObjectOutputStream(out);
		ot.writeObject(s);
		ot.flush();
		ot.close();
		return out.toByteArray();
	}

	/**
	 * BCD码转为10进制串(阿拉伯数据)
	 * 
	 * @author herun
	 * @createTime 2015-2-9-上午9:28:37
	 * @param bcdsBCD码
	 * @return 10进制串
	 */
	public static String bcd2str(byte[] bcds) {
		char[] ascii = "0123456789abcdef".toCharArray();
		byte[] temp = new byte[bcds.length * 2];
		for (int i = 0; i < bcds.length; i++) {
			temp[i * 2] = (byte) ((bcds[i] >> 4) & 0x0f);
			temp[i * 2 + 1] = (byte) (bcds[i] & 0x0f);
		}
		StringBuffer res = new StringBuffer();

		for (int i = 0; i < temp.length; i++) {
			res.append(ascii[temp[i]]);
		}
		return res.toString().toUpperCase();
	}

	/**
	 * 10进制串转为BCD码
	 * 
	 * @author herun
	 * @createTime 2015-2-9-上午9:29:00
	 * @param asc
	 *            10进制串
	 * @return BCD码
	 */
	public static byte[] str2Bcd(String asc) {
		int len = asc.length();
		int mod = len % 2;

		if (mod != 0) {
			asc = "0" + asc;
			len = asc.length();
		}

		byte abt[] = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}

		byte bbt[] = new byte[len];
		abt = asc.getBytes();
		int j, k;

		for (int p = 0; p < asc.length() / 2; p++) {
			if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
				j = abt[2 * p] - '0';
			} else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
				j = abt[2 * p] - 'a' + 0x0a;
			} else {
				j = abt[2 * p] - 'A' + 0x0a;
			}

			if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
				k = abt[2 * p + 1] - '0';
			} else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
				k = abt[2 * p + 1] - 'a' + 0x0a;
			} else {
				k = abt[2 * p + 1] - 'A' + 0x0a;
			}
			int a = (j << 4) + k;
			byte b = (byte) a;
			bbt[p] = b;
		}
		return bbt;
	}

	/**
	 * BCD码转ASC码
	 * 
	 * @author herun
	 * @createTime 2015-2-9-上午9:29:46
	 * @param bytesBCD串
	 * @return ASC码
	 */
	public static String bcdToAsc(byte[] bytes) {
		StringBuffer temp = new StringBuffer(bytes.length * 2);

		for (int i = 0; i < bytes.length; i++) {
			int h = ((bytes[i] & 0xf0) >>> 4);
			int l = (bytes[i] & 0x0f);
		}
		return temp.toString();
	}

	/**
	 * 整数到字节数组转换
	 * 
	 * @author herun
	 * @createTime 2015-2-9-上午9:30:44
	 * @param n整数
	 * @return 字节数组
	 */
	public static byte[] int2bytes(int n) {
		byte[] ab = new byte[4];
		ab[0] = (byte) (0xff & n);
		ab[1] = (byte) ((0xff00 & n) >> 8);
		ab[2] = (byte) ((0xff0000 & n) >> 16);
		ab[3] = (byte) ((0xff000000 & n) >> 24);
		return ab;
	}

	/**
	 * 字节转换到字符
	 * 
	 * @author herun
	 * @createTime 2015-2-9-上午9:34:20
	 * @param b字节数组
	 * @return 字符
	 */
	public static char byte2char(byte b) {
		return (char) b;
	}

	/**
	 * 字符转整数
	 * 
	 * @author herun
	 * @createTime 2015-2-9-上午9:36:55
	 * @param c字符
	 * @return 整数
	 */
	public static int parse(char c) {
		if (c >= 'a')
			return (c - 'a' + 10) & 0x0f;
		if (c >= 'A')
			return (c - 'A' + 10) & 0x0f;
		return (c - '0') & 0x0f;
	}

	/**
	 * 字节数组到整数的转换
	 * 
	 * @author herun
	 * @createTime 2015-2-9-上午9:38:09
	 * @param b字节数组
	 * @return 整数
	 */
	public static int bytes2int(byte b[]) {
		int s = 0;
		s = ((((b[0] & 0xff) << 8 | (b[1] & 0xff)) << 8) | (b[2] & 0xff)) << 8 | (b[3] & 0xff);
		return s;
	}

	/**
	 * 计算报文长度,位不足左补0,且返回内容=报文长度+data
	 * 
	 * @author herun
	 * @createTime 2015-2-9-上午9:38:36
	 * @param data报文内容
	 * @param length报文字节长度
	 * @return 报文
	 */
	public static byte[] addLength(byte[] data, int length) {
		byte[] bytes = new byte[data.length + length];// 目标数组
		String lens = Long.toHexString(data.length);
		byte[] redata = str2Bcd(addZeroLeft(lens + "", length * 2));// 报文长度
		System.arraycopy(redata, 0, bytes, 0, redata.length);// 拷贝报文长度
		System.arraycopy(data, 0, bytes, length, data.length);// 拷贝原报文
		return bytes;
	}

	/**
	 * 字符串左补零操作
	 * 
	 * @author herun
	 * @createTime 2015-2-9-上午9:40:01
	 * @param str待补零的字符串
	 * @param len补零之后的字符串长度
	 * @return 补零后的字符串内容
	 */
	public static String addZeroLeft(String str, int len) {
		StringBuffer s = new StringBuffer();
		s.append(str);
		while (s.length() < len) {
			s.insert(0, "0");
		}
		return s.toString();
	}

	/**
	 * 计算报文长度,位不足左补0,返回16进制的字节数组
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:20:23
	 * @param data报文内容
	 * @param length报文长度
	 * @return 加上长度之后的字节数组
	 */
	public static byte[] countAndLeftZerro(byte[] data, int length) {
		String lens = Long.toHexString(data.length);
		byte[] redata = str2Bcd(addZeroLeft(lens + "", length * 2));
		return redata;
	}

	/**
	 * 右边补0x00
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:20:48
	 * @param bytes原数组
	 * @param length报文字节长度
	 * @return 补位之后的字节数组
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] rightAddZerro(byte[] bytes, int length) throws UnsupportedEncodingException {
		byte[] b = new byte[length];
		int b_length = bytes.length;

		if (b_length == length) {
			return bytes;
		}

		if (length < b_length) {
			return bytes;
		}

		// src - 源数组。
		// srcPos - 源数组中的起始位置。
		// dest - 目标数组。
		// destPos - 目标数据中的起始位置。
		// length - 要复制的数组元素的数量。
		System.arraycopy(bytes, 0, b, 0, b_length);

		int destPos = b_length;
		for (int i = 0; i < (length - b_length); i++) {
			byte[] b_b = new byte[] { 0x00 };
			System.arraycopy(b_b, 0, b, destPos, 1);
			destPos++;
		}
		return b;
	}

	/**
	 * 不足8的倍数补位
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:21:30
	 * @param hexbyte原字节数组
	 * @return 补位自后的字节数组
	 */
	public static byte[] to8RightByte(byte[] hexbyte) {
		int mod = hexbyte.length % 8;
		byte[] reByte = hexbyte;
		if (mod != 0) {
			byte[] hex0 = new byte[8 - mod];
			for (int i = 0; i < hex0.length; i++) {
				hex0[i] = (byte) 0X00;
			}
			reByte = new byte[hexbyte.length + hex0.length];
			System.arraycopy(hexbyte, 0, reByte, 0, hexbyte.length);
			System.arraycopy(hex0, 0, reByte, hexbyte.length, hex0.length);
		}
		return reByte;
	}

	/**
	 * 左边补0x30
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:22:38
	 * @param bytes原字节数组
	 * @param length补位之后的长度
	 * @return 补位之后的字节数组
	 */
	public static byte[] leftAddZerro(byte[] bytes, int length) {
		byte[] b = new byte[length];
		int b_length = bytes.length;

		if (b_length == length) {
			return bytes;
		}

		if (length < b_length) {
			return bytes;
		}

		int destPos = 0;
		for (int i = 0; i < (length - bytes.length); i++) {
			byte[] b_b = new byte[] { 0x30 };
			// src - 源数组。
			// srcPos - 源数组中的起始位置。
			// dest - 目标数组。
			// destPos - 目标数据中的起始位置。
			// length - 要复制的数组元素的数量。
			System.arraycopy(b_b, 0, b, destPos, 1);
			destPos++;
		}

		System.arraycopy(bytes, 0, b, destPos, bytes.length);
		return b;
	}

	/**
	 * 合并byte数组
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:24:14
	 * @param data1数组1
	 * @param data2数组2
	 * @return 合并之后的字节数组
	 */
	public static byte[] copyarray(byte[] data1, byte[] data2) {
		byte[] data3 = new byte[data1.length + data2.length];
		System.arraycopy(data1, 0, data3, 0, data1.length);
		System.arraycopy(data2, 0, data3, data1.length, data2.length);

		return data3;
	}

	/**
	 * 打印字节数组
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:24:48
	 * @param bArray需要打印的字节数组
	 * @return 打印字符串
	 */
	public static final String bytesToHexStringForPrint(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		sb.append("\r\n");
		String sTemp;
		int j = 0; // 此处定义的j用于控制每行输出的数据
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase()).append(" ");
			j++;
			if (j % 16 == 0) {
				sb.append("\r\n");
			}
		}
		return sb.toString();
	}

	/**
	 * 把两个字节的字节数组转化为整型数据，高位补零，例如：<br/>
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:25:47
	 * @param lenData需要进行转换的字节数组
	 * @return字节数组所表示整型值的大小
	 */
	public static int bytesToInt(byte lenData[]) {
		if (lenData.length != 2) {
			System.out.println("所要转换的数组长度不符合要求，转换失败");
			return 0;
		}
		byte fill[] = new byte[] { 0, 0 };
		byte real[] = new byte[4];
		System.arraycopy(fill, 0, real, 0, 2);
		System.arraycopy(lenData, 0, real, 2, 2);
		int len = byteToInt(real);
		return len;

	}

	/**
	 * 将一个整型数据转换为字节数组
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:26:18
	 * @param intValue需要转换的整型数据
	 * @return 该整型所对应的字节数组
	 */
	public static byte[] intToByte(int intValue) {
		byte[] result = new byte[4];
		result[0] = (byte) ((intValue & 0xFF000000) >> 24);
		result[1] = (byte) ((intValue & 0x00FF0000) >> 16);
		result[2] = (byte) ((intValue & 0x0000FF00) >> 8);
		result[3] = (byte) ((intValue & 0x000000ff));

		return result;
	}

	/**
	 * 将byte数组转化为int类型的数据
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:26:58
	 * @param byteVal需要转化的字节数组
	 * @return 字节数组所表示的整型数据
	 */
	public static int byteToInt(byte[] byteVal) {
		int result = 0;
		for (int i = 0; i < byteVal.length; i++) {
			int tmpVal = (byteVal[i] << (8 * (3 - i)));
			switch (i) {
			case 0:
				tmpVal = tmpVal & 0xFF000000;
				break;
			case 1:
				tmpVal = tmpVal & 0x00FF0000;
				break;
			case 2:
				tmpVal = tmpVal & 0x0000FF00;
				break;
			case 3:
				tmpVal = tmpVal & 0x000000FF;
				break;
			}
			result = result | tmpVal;
		}
		return result;
	}

	/**
	 * 把char类型的数据转换为byte[]字节
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:27:22
	 * @param ch类型数据
	 * @return 该char类型数据对应的字节内容
	 */
	public static byte[] charToByte(char ch) {
		int temp = (int) ch;
		byte[] b = new byte[2];
		for (int i = b.length - 1; i > -1; i--) {
			b[i] = new Integer(temp & 0xFF).byteValue(); // 将最高位保存在最低位
			temp = temp >> 8; // 向右移位
		}
		return b;
	}

	/**
	 * 把byte字节数组转化为char类型数据
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:27:46
	 * @param b需要转换的字节数组
	 * @return 返回字节数组所表示的char类型数据
	 */
	public static char byteToChar(byte[] b) {
		int s = 0;
		if (b[0] > 0)
			s += b[0];
		else
			s += 256 + b[0];
		s *= 256;
		if (b[1] > 0)
			s += b[1];
		else
			s += 256 + b[1];
		char ch = (char) s;
		return ch;
	}

	/**
	 * 把double类型的数据转换为字节数组
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:28:07
	 * @param d需要转换的double类型的数据内容
	 * @return 该double类型数据对应的字节数组
	 */
	public static byte[] doubleToByte(double d) {
		byte[] b = new byte[8];
		long l = Double.doubleToLongBits(d);
		for (int i = 0; i < b.length; i++) {
			b[i] = new Long(l).byteValue();
			l = l >> 8;
		}
		return b;
	}

	/**
	 * 把字节数组转换为double类型的数据
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:28:28
	 * @param b要进行转换的字节数组
	 * @return 返回该字节数组对应的double类型数据
	 */
	public static double byteToDouble(byte[] b) {
		long l;

		l = b[0];
		l &= 0xFF;
		l |= ((long) b[1] << 8);
		l &= 0xFFFF;
		l |= ((long) b[2] << 16);
		l &= 0xFFFFFF;
		l |= ((long) b[3] << 24);
		l &= 0xFFFFFFFF;
		l |= ((long) b[4] << 32);
		l &= 0xFFFFFFFFFFl;
		l |= ((long) b[5] << 40);
		l &= 0xFFFFFFFFFFFFl;
		l |= ((long) b[6] << 48);
		l &= 0xFFFFFFFFFFFFFFl;
		l |= ((long) b[7] << 56);

		return Double.longBitsToDouble(l);
	}

	/**
	 * 将byte字节数组转换为double类型的数据
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:28:54
	 * @param data需要转的字节数组
	 * @return 转换后的double类型的数据
	 */
	public static double fromByteToDouble(byte[] data) {
		String str = "";
		char[] chardata = new char[10];
		for (int i = 0; i < data.length; i++) {
			chardata[i] = (char) data[i];
			str += chardata[i];
		}
		double num = Double.parseDouble(str);
		return num;
	}

	/**
	 * 将字节数组转换为对应的二进制串
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:29:16
	 * @param data需要转换的字节数组
	 * @return 该字节数组对应的二进制字符串
	 */
	public static String byteToBinaryStr(byte[] data) {
		StringBuffer result = new StringBuffer();
		BitSet bs = new BitSet(data.length * 8);
		int pos = 0;
		for (int i = 0; i < data.length; i++) {
			int bit = 128;
			for (int b = 0; b < 8; b++) {
				bs.set(pos++, (data[i] & bit) != 0); // pos先参与set赋值后加1
				bit >>= 1;
			}
		}
		for (int i = 0; i < data.length * 8; i++) {
			if (bs.get(i)) {
				result.append("1");
			} else {
				result.append("0");
			}
		}
		return result.toString();
	}

	/**
	 * 把二进制字符串转换为对应的字节数组
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:29:43
	 * @param str需要转换的0
	 *            、1二进制字符串
	 * @return 二进制所表示的字节数组
	 */
	public static byte[] binaryStrToBytes(String str) {
		if (str.length() % 8 != 0) {
			throw new RuntimeException("所要转换的二进制字符串长度不是8的整数倍，转换失败");
		}
		char str_data[] = str.toCharArray();
		BitSet bs = new BitSet(str_data.length);
		for (int i = 0; i < str_data.length; i++) {
			switch (str_data[i]) {
			case '0':
				bs.set(i, false);
				break;
			case '1':
				bs.set(i, true);
				break;
			default:
				System.out.println("二进制字符串含有0、1之外的其他字符");
				throw new RuntimeException("所要转换的二进制字符串含有0、1之外的字符，无法进行转换");
			}
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte data[] = new byte[str.length() / 8];
		// Write bitmap into stream
		int pos = 128; // // 用来做位运算： -- 1000 0000（初值最高位为1，然后右移一位，等等）
		int b = 0; // 用来做位运算：初值二进制位全0
		for (int i = 0; i < str.length(); i++) {
			if (bs.get(i)) {
				b |= pos;
			}
			pos >>= 1;
			if (pos == 0) { // 到一个字节时（8位），就写入
				bos.write(b);
				pos = 128;
				b = 0;
			}
		}
		data = bos.toByteArray();
		return data;
	}

	/**
	 * 将字节数组中的内容转化为字符串，例如有字节数组byte []data = new byte[]{12,34,56}
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:30:06
	 * @param data所要转换的字节数组
	 * @return 转换后的字符串内容
	 */
	public static String byteArrayToStr(byte[] data) {
		String str = "";
		for (int i = 0; i < data.length; i++) {
			str += data[i];
		}
		return str;
	}

	/**
	 * 功能描述：将传递进来的Map集合转换成Json格式的字符串，例如map =
	 * {put("aa",10),put("bb",20)},转换后为：{"aa":"10","bb":"20"}
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:30:33
	 * @param map需要转换的map集合
	 * @return 返回转换成功后的字符串，map为null的话返回null
	 */
	public static String mapToJsonString(Map<String, String> map) {
		if (map == null) {
			return null;
		}
		String jsonStr = null;
		int mapSize = 1;
		StringBuffer tempStr = new StringBuffer();
		tempStr.append("{");
		@SuppressWarnings("rawtypes")
		Set mapKey = map.keySet();
		for (Object keyStr : mapKey) {
			tempStr.append("\""); // 附加一个双引号
			tempStr.append(keyStr.toString()); // 附加key值
			tempStr.append("\""); // 附件一个双引号
			tempStr.append(":"); // 附件冒号
			tempStr.append("\""); // 附件一个双引号
			tempStr.append(map.get(keyStr).toString()); // 获取Value值
			tempStr.append("\""); // 附件一个双引号
			if (mapSize == map.size()) {
			} else {
				tempStr.append(","); // 增加中间点的逗号
			}
			mapSize++; // mapSize自增1
		}
		tempStr.append("}");
		return tempStr.toString();
	}

	/**
	 * 功能描述：将json字符串转换为Map集合，例如jsonStr
	 * ={"aa":"20","bb":"30"},可以转换为Map(){put("aa","20"),put("bb","30")};
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:31:13
	 * @param jsonStr需要转换的json字符串转
	 * @return Map集合
	 */
	@SuppressWarnings("rawtypes")
	public static Map jsonStrToMap(String jsonStr) {
		Map<String, String> map = new HashMap<String, String>();
		String str = jsonStr;
		System.out.println("jsonStr = " + jsonStr + ";jsonStr.length = " + jsonStr.length());
		String[] tempArr = str.substring(1, str.length() - 1).split(","); // 截取开始的{和末尾的}
		for (int i = 0; i < tempArr.length; i++) {// 将"aa":"20"格式的字符串的前半部分和后半部分放入map集合中
			System.out.println("key = " + tempArr[i].split(":")[0].toString() + ";first.length = " + tempArr[i].split(":")[0].toString().length());
			System.out.println("value = " + tempArr[i].split(":")[1].toString() + ";second.length = " + tempArr[i].split(":")[1].toString().length());
			String key = tempArr[i].split(":")[0].toString().substring(1, tempArr[i].split(":")[0].toString().length() - 1);
			String content = tempArr[i].split(":")[1].toString().substring(1, tempArr[i].split(":")[1].toString().length() - 1);
			map.put(key, content);
		}
		return map;
	}

	/**
	 * 在一个字符串的末尾补零，直至该字符串的长度是8的整数倍
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:34:25
	 * @param str待补位的字符串
	 * @return 返回补位后的字符串
	 */
	public static String addZeroLast(String str) {
		StringBuffer sbf = null;
		if (str.length() % 16 != 0) {
			sbf = new StringBuffer(str);
			while (sbf.length() % 16 != 0) {
				sbf.append("0");
			}
			return sbf.toString(); // 将补位后的值返回
		} else {
			return str;
		}
	}

	/**
	 * 在一个byte[]数组的末尾补零，直至该byte[]数组的长度是8的整数倍
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:34:41
	 * @param data原字节数组
	 * @return 补位之后的字节数组
	 */
	public static byte[] addZeroRight(byte[] data) {
		byte[] addObject = new byte[] { 0 };
		if (data.length % 16 != 0) {
			while (data.length % 16 != 0) {
				data = copyarray(data, addObject);
			}
			return data;
		} else {
			return data;
		}

	}

	/**
	 * 在一个字符串的左边补零，直至该字符串的长度是8的整数倍
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:35:30
	 * @param 补位前的str字符串
	 * @return 补位之后的字符串
	 */
	public static String addZeroLeft(String str) {
		StringBuffer sbf = null;
		StringBuffer sbf2 = null;
		if (str.length() % 16 != 0) {
			sbf = new StringBuffer(str);
			while (sbf.length() % 16 != 0) {
				sbf2 = new StringBuffer();
				sbf2.append("0");
				sbf2.append(sbf);
				sbf = sbf2;
			}
			return sbf.toString(); // 将补位后的值返回
		} else {
			return str;
		}
	}

	/**
	 * 两个等长的数组做异或,以第一个数组长度为准
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:36:11
	 * @param array1数组1
	 * @param array2数组1
	 * @return int数组
	 */
	public static int[] diffOr(int[] array1, int[] array2) {
		int len = array1.length;
		int[] dest = new int[len];
		for (int i = 0; i < len; i++) {
			dest[i] = array1[i] ^ array2[i];
		}
		return dest;
	}

	/**
	 * 将单个字节的字节数组转化为对应的整型数据
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:37:04
	 * @param data字节数组
	 * @return int数
	 */
	public static int bytesToIntWhereLengthEqual(byte[] data) {
		String strHex = bytesToHexString(data);
		int value = Integer.parseInt(strHex, 16); // 将16进制字符串转化为整型数据
		return value;
	}

	/**
	 * 将整型数据转化为2字节的16进制bytes
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:37:30
	 * @param len长度
	 * @return 字节数组
	 */
	public static byte[] intToHexBytes(int len) {
		byte[] buf = new byte[2];
		int pos = 0;
		buf[pos] = (byte) ((len & 0xff00) >> 8);
		pos++;
		buf[pos] = (byte) (len & 0xff);
		return buf;
	}

	/**
	 * 字符串右补零操作
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:38:31
	 * @param str待补零的字符串
	 * @param len补零之后的字符串长度
	 * @return 补零后的字符串内容
	 */
	public static String addZeroRight(String str, int len) {
		StringBuffer s = new StringBuffer();
		s.append(str);
		while (s.length() < len) {
			s.append("0");
		}
		return s.toString();
	}

	/**
	 * 字符串左补空格操作
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:38:59
	 * @param str待补空格的字符串
	 * @param len补空格之后的字符串长度
	 * @return 补空格之后的字符串内容
	 * @throws UnsupportedEncodingException
	 */
	public static String addSpaceLeft(String str, int len) throws UnsupportedEncodingException {
		StringBuffer s = new StringBuffer();
		s.append(str);
		while (s.toString().getBytes("GBK").length < len) {
			s.insert(0, " ");
		}
		return s.toString();
	}

	/**
	 * 字符串右补空格操作
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:39:21
	 * @param str待补空格的字符串
	 * @param len补空格后字符串的长度
	 * @return 补空格后的字符串
	 * @throws UnsupportedEncodingException
	 */
	public static String addSpaceRight(String str, int len) throws UnsupportedEncodingException {
		StringBuffer s = new StringBuffer();
		s.append(str);
		while (s.toString().getBytes("GBK").length < len) {
			s.append(" ");
		}
		return s.toString();
	}

	/**
	 * ASCII字符串转16进制字符串
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:39:41
	 * @param content字符串
	 * @return 字符串
	 */
	public static String StringToAsciiString(String content) {
		String result = "";
		int max = content.length();
		for (int i = 0; i < max; i++) {
			char c = content.charAt(i);
			String b = Integer.toHexString(c);
			result = result + b;
		}
		return result;
	}

	/**
	 * ASCII字符串转16进制字符串,长度不足右补00
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:40:12
	 * @param content内容
	 * @param len长度
	 * @return 补位之后的字符串
	 */
	public static String StringToAsciiString(String content, int len) {
		String result = "";
		int max = content.length();
		for (int i = 0; i < max; i++) {
			char c = content.charAt(i);
			String b = Integer.toHexString(c);
			result = result + b;
		}
		int mu = len - content.length();
		for (int i = 0; i < mu; i++) {
			result += "00";
		}
		return result;
	}

	/**
	 * 十六进制转字符串
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:40:51
	 * @param hexString
	 *            十六进制字符串
	 * @param encodeType编码类型4
	 *            ：Unicode,2：普通编码
	 * @return 字符串
	 */
	public static String hexStringToString(String hexString, int encodeType) {
		String result = "";
		int max = hexString.length() / encodeType;
		for (int i = 0; i < max; i++) {
			char c = (char) hexStringToAlgorism(hexString.substring(i * encodeType, (i + 1) * encodeType));
			result += c;
		}
		return result;
	}

	/**
	 * 十六进制字符串装十进制
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:41:21
	 * @param hex十六进制字符串
	 * @return 十进制数值
	 */
	public static int hexStringToAlgorism(String hex) {
		hex = hex.toUpperCase();
		int max = hex.length();
		int result = 0;
		for (int i = max; i > 0; i--) {
			char c = hex.charAt(i - 1);
			int algorism = 0;
			if (c >= '0' && c <= '9') {
				algorism = c - '0';
			} else {
				algorism = c - 55;
			}
			result += Math.pow(16, max - i) * algorism;
		}
		return result;
	}

	/**
	 * 十六转二进制
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:41:38
	 * @param hex十六进制字符串
	 * @return 二进制字符串
	 */
	public static String hexStringToBinary(String hex) {
		hex = hex.toUpperCase();
		String result = "";
		int max = hex.length();
		for (int i = 0; i < max; i++) {
			char c = hex.charAt(i);
			switch (c) {
			case '0':
				result += "0000";
				break;
			case '1':
				result += "0001";
				break;
			case '2':
				result += "0010";
				break;
			case '3':
				result += "0011";
				break;
			case '4':
				result += "0100";
				break;
			case '5':
				result += "0101";
				break;
			case '6':
				result += "0110";
				break;
			case '7':
				result += "0111";
				break;
			case '8':
				result += "1000";
				break;
			case '9':
				result += "1001";
				break;
			case 'A':
				result += "1010";
				break;
			case 'B':
				result += "1011";
				break;
			case 'C':
				result += "1100";
				break;
			case 'D':
				result += "1101";
				break;
			case 'E':
				result += "1110";
				break;
			case 'F':
				result += "1111";
				break;
			}
		}
		return result;
	}

	/**
	 * ASCII码字符串转数字字符串
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:41:56
	 * @param ASCII字符串
	 * @return 字符串
	 */
	public static String AsciiStringToString(String content) {
		String result = "";
		int length = content.length() / 2;
		for (int i = 0; i < length; i++) {
			String c = content.substring(i * 2, i * 2 + 2);
			int a = hexStringToAlgorism(c);
			char b = (char) a;
			String d = String.valueOf(b);
			result += d;
		}
		return result;
	}

	/**
	 * 将十进制转换为指定长度的十六进制字符串,位不足左边补0
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:43:39
	 * @param algorism十进制数字
	 * @param len转换后的十六进制字符串长度
	 * @return 转换后的十六进制字符串
	 */
	public static String algorismToHEXString(int algorism, int len) {
		String result = "";
		result = Integer.toHexString(algorism).toUpperCase();

		String zerro = "";
		// 左边补0
		for (int i = result.length(); i < (len * 2); i++) {
			zerro += "0";
		}

		return zerro + result;
	}

	/**
	 * 字节数组转为普通字符串（ASCII对应的字符）
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:43:04
	 * @param bytearray字节数组
	 * @return 字符串
	 */
	public static String bytetoString(byte[] bytearray) {
		String result = "";
		char temp;

		int length = bytearray.length;
		for (int i = 0; i < length; i++) {
			temp = (char) bytearray[i];
			result += temp;
		}
		return result;
	}

	/**
	 * 二进制字符串转十进制
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:42:49
	 * @param binary二进制字符串
	 * @return 十进制数值
	 */
	public static int binaryToAlgorism(String binary) {
		int max = binary.length();
		int result = 0;
		for (int i = max; i > 0; i--) {
			char c = binary.charAt(i - 1);
			int algorism = c - '0';
			result += Math.pow(2, max - i) * algorism;
		}
		return result;
	}

	/**
	 * 十进制转换为十六进制字符串
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:42:32
	 * @param algorism十进制的数字
	 * @return 对应的十六进制字符串
	 */
	public static String algorismToHEXString(int algorism) {
		String result = "";
		result = Integer.toHexString(algorism);

		if (result.length() % 2 == 1) {
			result = "0" + result;

		}
		result = result.toUpperCase();

		return result;
	}

	/**
	 * HEX字符串前补0，主要用于长度位数不足。
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:33:40
	 * @param str需要补充长度的十六进制字符串
	 * @param maxLength补充后十六进制字符串的长度
	 * @return 补充结果
	 */
	static public String patchHexString(String str, int maxLength) {
		String temp = "";
		for (int i = 0; i < maxLength - str.length(); i++) {
			temp = "0" + temp;
		}
		str = (temp + str).substring(0, maxLength);
		return str;
	}

	/**
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:33:13
	 * @param s要转换的字符串
	 * @param defaultInt默认返回的数字
	 * @param radix要转换的字符串是什么进制的
	 *            ,如16 8 10.
	 * @return 转换后的数字
	 */
	public static int parseToInt(String s, int defaultInt, int radix) {
		int i = 0;
		try {
			i = Integer.parseInt(s, radix);
		} catch (NumberFormatException ex) {
			i = defaultInt;
		}
		return i;
	}

	/**
	 * 
	 * 将一个十进制形式的数字字符串转换为int
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:32:29
	 * @param s要转换的字符串
	 * @param defaultInt默认返回的数字
	 * @return 转换后的数字
	 */
	public static int parseToInt(String s, int defaultInt) {
		int i = 0;
		try {
			i = Integer.parseInt(s);
		} catch (NumberFormatException ex) {
			i = defaultInt;
		}
		return i;
	}

	/**
	 * 十六进制串转化为byte数组
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:32:09
	 * @param hex十六进制串
	 * @return byte数组
	 * @throws IllegalArgumentException
	 */
	public static final byte[] hex2byte(String hex) throws IllegalArgumentException {
		if (hex.length() % 2 != 0) {
			throw new IllegalArgumentException();
		}
		char[] arr = hex.toCharArray();
		byte[] b = new byte[hex.length() / 2];
		for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
			String swap = "" + arr[i++] + arr[i];
			int byteint = Integer.parseInt(swap, 16) & 0xFF;
			b[j] = new Integer(byteint).byteValue();
		}
		return b;
	}

	/**
	 * 字节数组转换为十六进制字符串
	 * 
	 * @author herun
	 * @createTime 2015年2月9日-下午5:31:48
	 * @param b需要转换的字节数组
	 * @return 十六进制字符串
	 */
	public static final String byte2hex(byte b[]) {
		if (b == null) {
			throw new IllegalArgumentException("Argument b ( byte array ) is null! ");
		}
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0xff);
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}
}
