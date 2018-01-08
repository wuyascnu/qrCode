package com.liuzhihu.qrcode.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.liuzhihu.qrcode.service.CoderService;
import com.liuzhihu.qrcode.service.ZxingDecoderService;

@Controller
public class ZxingEncoderAction {
	private ZxingDecoderService zxingDecodeService;
	private CoderService coderService;

	@RequestMapping(value = "/zxingdecode", method = RequestMethod.GET)
	public ModelAndView zxingdecode(@RequestParam("realImgPath") String realImgPath, HttpSession session) {
		String uploadPath = "/images";
		String realUploadPath = session.getServletContext().getRealPath(uploadPath);
		String imgPath = realUploadPath + "/" + realImgPath;
		String result = zxingDecodeService.zxingdecode(imgPath);

		ModelAndView ret = new ModelAndView();
		ret.addObject("result", result);
		ret.setViewName("zxingdecode");

		return ret;
	}

	@RequestMapping(value = "/zxingcoder", method = RequestMethod.GET)
	public ModelAndView watermark(HttpSession session) throws Exception {
		String uploadPath = "/images";
		String realUploadPath = session.getServletContext().getRealPath(uploadPath);
		String imageName = "12345" + ".png";
		// 模拟订单详情
		String contents = "订单编号：20160512082345" + "\n" + "订单金额：￥ 2050.00" + "\n" + "支付方式：预存款" + "\n" + "配送方式：京东快递";

		int width = 300;
		int height = 300;
		String zxingImage = coderService.encode(contents, width, height, uploadPath, realUploadPath, imageName);

		ModelAndView ret = new ModelAndView();
		ret.addObject("imageUrl", zxingImage);
		ret.addObject("imageName", imageName);
		return ret;
	}

	@Autowired
	public void setZxingDecodeService(ZxingDecoderService zxingDecodeService) {
		this.zxingDecodeService = zxingDecodeService;
	}

	@Autowired
	public void setCoderService(CoderService coderService) {
		this.coderService = coderService;
	}

}
