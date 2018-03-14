package com.jxduhai.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class PicUploadResult implements Serializable{

	private Integer error;// 0上传成功，1代表上传失败

	private String width;// 图片的宽
	private String height;// 图片的高
	private String url;// 图片的上传地址

}