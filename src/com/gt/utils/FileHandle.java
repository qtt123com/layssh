package com.gt.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;

/**
 * 文件操作工具
 * 
 * @author herun
 * @createTime 2015年2月10日-上午10:05:41
 * @see 依赖：commons-io-2.0.1.jar、servlet-api.jar
 */
public class FileHandle {

	/**
	 * 文件是否存在
	 * 
	 * @author herun
	 * @createTime 2015年2月10日-上午10:06:02
	 * @param path文件路径
	 * @return 是否存在
	 */
	public static boolean isExists(String path) {
		return new File(path).exists();
	}

	/**
	 * 计算文件大小
	 * 
	 * @author herun
	 * @createTime 2015年2月10日-上午10:07:21
	 * @param path文件路径
	 * @return 文件大小
	 */
	public static long getLength(String path) {
		return new File(path).length();
	}

	/**
	 * 删除文件
	 * 
	 * @author herun
	 * @createTime 2015年2月10日-上午10:07:57
	 * @param filePath文件路径
	 * @return 操作结果
	 */
	public static boolean deleteFile(String filePath) {
		File curFile = new File(filePath);
		int i = 0;
		for (; i < 20 && !curFile.delete(); i++) {
			try {
				System.gc();
				Thread.sleep(10);
			} catch (InterruptedException e) {

			}
		}
		return i < 20;
	}

	/**
	 * 当目录为空时删除目录
	 * 
	 * @author herun
	 * @createTime 2015年2月10日-上午10:08:27
	 * @param dir文件路径
	 * @return
	 */
	public static boolean deleteEmptyDirectory(String dir) {
		return new File(dir).delete();
	}

	/**
	 * 删除目录
	 * 
	 * @author herun
	 * @createTime 2015年2月10日-上午10:09:46
	 * @param dir目录路径
	 * @return 操作结果
	 */
	public static boolean deleteDirectory(String dir) {
		return deleteDirectory(new File(dir), true);
	}

	/**
	 * 删除目录,递归函数
	 * 
	 * @author herun
	 * @createTime 2015年2月10日-上午10:10:23
	 * @param dir
	 * @param deleteSelf
	 * @return
	 */
	private static boolean deleteDirectory(File dir, boolean deleteSelf) {
		if (dir.exists()) {
			File[] files = dir.listFiles();
			for (int i = files.length - 1; i >= 0; i--) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i], true);
				} else {
					deleteFile(files[i].getPath());
				}
			}
			if (deleteSelf) {
				dir.delete();
			}
		}
		return true;
	}

	/**
	 * 创建文件目录
	 * 
	 * @author herun
	 * @createTime 2015年2月10日-上午11:05:51
	 * @param filepath路径
	 */
	public static void mkdirs(String filepath) {
		File savdir = new File(filepath);
		// 目录不存在则创建
		if (!savdir.exists()) {
			savdir.mkdirs();
		}
	}

	/**
	 * 拷贝文件
	 * 
	 * @author herun
	 * @createTime 2015年2月10日-上午11:03:02
	 * @param src源文件
	 * @param dest目标文件
	 * @return 是否操作成功
	 */
	public static String copyFile(String src, String dest) {
		File destFile = new File(dest);
		if (destFile.isDirectory()) { // 目录
			if (!dest.endsWith("/")) {
				dest += "/";
			}
			int index = src.lastIndexOf('/');
			if (index == -1) {
				index = src.lastIndexOf('\\');
			}
			dest += src.substring(index + 1);
			destFile = new File(dest);
		}
		File srcFile = new File(src);
		if (destFile.exists()) { // 文件已存在,且不允许覆盖
			if (srcFile.lastModified() == destFile.lastModified()) { // 文件最后修改时间相同,不需要拷贝
				return dest;
			}
		}
		byte[] buffer = new byte[Math.max(1, Math.min((int) srcFile.length(), 81920))];
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(destFile);
			int readLen;
			while ((readLen = in.read(buffer)) > 0) {
				out.write(buffer, 0, readLen);
			}
		} catch (Exception e) {
			return null;
		} finally {
			try {
				in.close();
			} catch (Exception e) {

			}
			try {
				out.close();
			} catch (Exception e) {

			}
		}
		destFile.setLastModified(srcFile.lastModified());
		return dest;
	}

	/**
	 * 拷贝目录
	 * 
	 * @author herun
	 * @createTime 2015年2月10日-上午11:02:24
	 * @param src源目录
	 * @param dest目标目录
	 */
	public static void copyDirectory(String src, String dest) {
		if (!src.endsWith("/")) {
			src += "/";
		}
		if (!dest.endsWith("/")) {
			dest += "/";
		}
		File srcDir = new File(src);
		File[] files = srcDir.listFiles();
		if (files == null) {
			return;
		}
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) { // 目录
				File destDir = new File(dest + files[i].getName());
				if (!destDir.exists()) {
					destDir.mkdir();
				}
				copyDirectory(src + files[i].getName(), dest + files[i].getName());
			} else { // 文件
				copyFile(src + files[i].getName(), dest + files[i].getName());
			}
		}
	}

	/**
	 * 写入文本内容
	 * 
	 * @author herun
	 * @createTime 2015年2月10日-上午11:00:45
	 * @param filePath文件路径
	 * @param content内容
	 * @param encoding编码格式
	 * @return
	 */
	public static boolean saveStringToFile(String filePath, String content, String encoding) {
		FileOutputStream out = null;
		encoding = encoding.toUpperCase();
		try {
			// Unicode：FF FE, Unicode big_endian：EF FF, UTF-8： EF BB BF
			out = new FileOutputStream(filePath);
			if ("UTF-8".equals(encoding)) {
				out.write(0xEF);
				out.write(0xBB);
				out.write(0xBF);
			} else if ("UNICODE".equals(encoding)) {
				out.write(0xFF);
				out.write(0xFE);
			}
			out.write(encoding == null ? content.getBytes() : content.getBytes(encoding));
			return true;
		} catch (Exception ex) {
			deleteFile(filePath); // 保存失败,删除文件
			return false;
		} finally {
			try {
				out.close();
			} catch (IOException ex1) {

			}
		}
	}

	/**
	 * 读取指定目录文件的字节
	 * 
	 * @author herun
	 * @createTime 2015年2月10日-上午10:57:57
	 * @param filepath文件路径
	 * @return 文件字节数组
	 */
	public static byte[] readBytesFromFile(String filepath) {
		FileInputStream inputStream = null;
		try {
			File file = new File(filepath);
			int fileSize = (int) file.length();
			inputStream = new FileInputStream(file);
			byte[] bytes = new byte[fileSize];
			inputStream.read(bytes);
			return bytes;
		} catch (Exception ex) {
			return null;
		} finally {
			try {
				inputStream.close();
			} catch (Exception e) {

			}
		}
	}

	/**
	 * 实现文件下载
	 * 
	 * @author herun
	 * @createTime 2015年2月10日-上午11:08:18
	 * @param response
	 *            -HttpServletResponse对象
	 * @param savePath文件保存路径
	 * @param encode编码格式UTF
	 *            -8、GBK
	 * @throws IOException
	 */
	public static void fileDownLoad(HttpServletResponse response, String savePath, String encode) throws IOException {
		String pathname = savePath.substring(savePath.lastIndexOf("/") + 1);
		response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(pathname, encode));
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(savePath);
			int len = 0;
			byte[] b = new byte[1024];
			out = response.getOutputStream();
			while ((len = in.read(b)) > 0) {
				out.write(b, 0, len);

			}
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}

			}
		}
	}

	/**
	 * 多个文件上传
	 * 
	 * @author herun
	 * @createTime 2015年2月10日-下午1:35:16
	 * @param file当前文件对象
	 * @param fileFileName文件名称和后缀
	 * @param filepath保存在服务哪个路径
	 */
	public static void uploadFiles(File[] file, String[] fileFileName, String filepath) {
		if (file != null) {
			for (int i = 0; i < file.length; i++) {
				File savdir = new File(filepath + "/" + i);
				// 目录不存在则创建
				if (!savdir.exists()) {
					savdir.mkdirs();
				}
				File saveFile = new File(savdir, fileFileName[i]);
				try {
					FileUtils.copyFile(file[i], saveFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 多个文件上传并且返回保存的路径
	 * 
	 * @author herun
	 * @createTime 2015年2月10日-下午1:35:16
	 * @param file当前文件对象
	 * @param fileFileName文件名称和后缀
	 * @param filepath保存在服务哪个路径
	 * @return 保存的路径数组
	 * @throws Exception
	 */
	public static String[] uploadFile(File[] file, String fileName[], String path) throws Exception {
		String savePath[] = new String[file.length];
		for (int i = 0; i < file.length; i++) {
			// 源文件
			FileInputStream in = new FileInputStream(file[i]);

			// 目标文件
			String fPath = path + "/" + i + "/" + fileName[i];
			File saveFile = new File(fPath);
			savePath[i] = fPath;

			// 判断存放路径是否存在，存在就删除，不存在就创建
			if (saveFile.exists()) {
				saveFile.delete();
			} else {
				saveFile.getParentFile().mkdirs();
			}

			// 输出流
			FileOutputStream out = new FileOutputStream(saveFile);

			int c;
			byte buffer[] = new byte[1024];
			while ((c = in.read(buffer)) != -1) {
				for (int y = 0; y < c; y++)
					out.write(buffer[y]);
			}

			in.close();
			out.close();
		}

		return savePath;
	}

	/**
	 * 获取文件的Md5值
	 * 
	 * @author herun
	 * @createTime 2015年2月10日-下午2:03:22
	 * @param file文件对象
	 * @return MD5值
	 * @throws Exception
	 */
	public static String getMd5ByFile(File file) throws Exception {
		String value = null;
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			BigInteger bi = new BigInteger(1, md5.digest());
			value = bi.toString(16);
		} catch (Exception e) {
			throw e;
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	/**
	 * 获取文件的Md5值
	 * 
	 * @author herun
	 * @createTime 2015年2月10日-下午2:03:22
	 * @param filePath文件绝对路径
	 * @return MD5值
	 * @throws Exception
	 */
	public static String getMd5ByFile(String filePath) throws Exception {
		String value = null;
		File file = new File(filePath);
		value = getMd5ByFile(file);
		return value;
	}

}
