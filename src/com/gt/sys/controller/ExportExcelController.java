package com.gt.sys.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.utils.FileHandle;
import com.gt.utils.ParseProperties;
import com.gt.utils.PbUtils;

/**
 * @功能说明：excel导出工具
 * @作者： herun
 * @创建日期：2015-11-20
 * @版本号：V1.0
 */
@Controller
@RequestMapping("/exportExcel")
public class ExportExcelController extends BaseController {

	private String basePath = ParseProperties.getProperties().getProperty("excelBasePath");

	/**
	 * 创建excel
	 * 
	 * @param wareDayCount
	 * @return
	 * @throws WriteException
	 * @throws RowsExceededException
	 * @throws IOException
	 */
	@RequestMapping("/createExport")
	@ResponseBody
	public Json exportExcel(String jsonstr, String widths, String titles, String fields) throws RowsExceededException, WriteException, IOException {
		String width[] = widths.split(",");
		String title[] = titles.split(",");
		String field[] = fields.split(",");
		Gson gson = new Gson();
		DatagridForLayUI datagrid = gson.fromJson(jsonstr, DatagridForLayUI.class);
		String excelPath = "/jxlTem/" + PbUtils.getCurrentTime() + ".xls";
		String filePath = basePath + excelPath;

		// 路径不存在则创建
		File saveFile = new File(filePath);
		if (saveFile.exists()) {
			saveFile.delete();
		} else {
			saveFile.getParentFile().mkdirs();
		}

		// 打开文件
		WritableWorkbook book = Workbook.createWorkbook(new File(filePath));

		// 生成名为“第一页”的工作表，参数0表示这是第一页
		WritableSheet sheet = book.createSheet("第一页 ", 0);

		for (int i = 0; i < width.length; i++) {
			// 设置第i行列的宽度
			sheet.setColumnView(i, Integer.parseInt(width[i])/5);
		}

		// 设置高度
		sheet.setRowView(0, 500);

		// 设置字体;
		WritableFont font1 = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.RED);

		WritableCellFormat cellFormat = new WritableCellFormat(font1);
		// 设置背景颜色;
		cellFormat.setBackground(Colour.GREEN);
		// 设置边框;
		cellFormat.setBorder(Border.ALL, BorderLineStyle.DASH_DOT);
		// 设置自动换行;
		cellFormat.setWrap(true);
		// 设置文字居中对齐方式;
		cellFormat.setAlignment(Alignment.CENTRE);
		// 设置垂直居中;
		cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

		// 设置标题,设置第一行,第i列的值
		for (int i = 0; i < title.length; i++) {
			// 设置第i行列的宽度
			Label label = new Label(i, 0, title[i].toString(), cellFormat);
			sheet.addCell(label);// 添加到表格
		}

		// 在Label对象的构造子中指名单元格位置是第i列第y行(0,i)
		List<Map<String, Object>> list = datagrid.getData();
		for (int i = 0; i < field.length; i++) {
			int y = 1;
			if (list != null && list.size() > 0) {
				for (Map<String, Object> map : list) {
					Label label = new Label(i, y, map.get(field[i]) == null ? "" : map.get(field[i]).toString());
					sheet.addCell(label);// 添加到表格
					y++;
				}
			}
		}

		// 写入数据并关闭文件
		book.write();
		book.close();

		Json json = new Json();
		json.setSuccess(true);
		json.setMsg("成功");
		json.setObj(excelPath);
		return json;
	}

	/**
	 * 附件是否存在
	 */
	public Json isExistsFile(String excelPath) {
		Json j = new Json();
		j.setSuccess(true);
		String filePath = basePath + excelPath;
		try {
			// 检查文件是否存在
			if (!FileHandle.isExists(filePath)) {
				j.setSuccess(false);
				j.setMsg("对不起，附件不存在");
			}
		} catch (Exception e) {
			j.setSuccess(false);
			j.setObj("系统异常" + e.getMessage());
		}
		return j;
	}

	/**
	 * 文件下载
	 * 
	 * @param excelPath
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/download")
	public String download(String excelPath, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filePath = basePath + excelPath;
		if (!FileHandle.isExists(filePath)) {
			request.setAttribute("msg", "对不起,附件不存在");
			request.getRequestDispatcher("/errors/noFile.jsp").forward(request, response);
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=" + excelPath.substring(excelPath.lastIndexOf("/") + 1));
		try {
			InputStream inputStream = new FileInputStream(new File(filePath));
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}

			// 这里主要关闭。
			os.close();
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 返回值要注意，要不然就出现下面这句错误！
		return null;
	}

}
