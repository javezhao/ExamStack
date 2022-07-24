// 获取图片预览地址函数
function getObjectURL(file) {  
	var url = null;   
	if (window.createObjectURL!=undefined) {  
		url = window.createObjectURL(file) ;  
	} else if (window.URL!=undefined) { // mozilla(firefox)  
		url = window.URL.createObjectURL(file) ;  
	} else if (window.webkitURL!=undefined) { // webkit or chrome  
		url = window.webkitURL.createObjectURL(file) ;  
	}  
	return url ;  
}
// 选择图片后触发-用于预览图片
function uploader(e){
	// 将图片信息通过getObjectURL函数处理出预览地址
	var s = getObjectURL(e[0]);
	// 获取img元素，label元素，div[上传按钮]元素
	var img=document.getElementById('o_photo_img');
	var label=document.getElementById('o_photo_label');
	var uploader_btn=document.getElementById('uploader_btn');
	// 设置图片展示样式
	img.style.padding='3px';
	img.style.borderStyle='solid';
	img.style.borderColor='#eee';
	img.style.borderWidth='1px';
	// 设置img的src值实现图片预览
	img.src=s;
	// 隐藏选择图片按钮
	label.style.display='none';
	// 显示上传图片按钮及设置样式
	uploader_btn.style.display='block';
	uploader_btn.style.marginTop='5px';
}
// 上传图片函数
function uploader_query(){
	// 避免用户进行二次上传
	if($('input[name="o_photo"]').val()){
		return false;
	}
	// 抓取必要元素
	var file=document.getElementById('o_photo').files;
	var msg=document.getElementById('message');
	// 设置图片上传信息
	var formData = new FormData(); 
	formData.append('file', file[0]);  //添加图片信息的参数
	
	document.write(file);
	
	// 通过ajax无刷新上传至后台服务器处理
	$.ajax({
	//	url:"{:url('Uploader/uploaderimg')}",
		url: document.getElementsByTagName('base')[0].href + 'secure/upload-uploadify-img/' ,
		data:formData,
		processData: false, // 告诉jQuery不要去处理发送的数据
    	contentType: false, // 告诉jQuery不要去设置Content-Type请求头
		dataType:'json',
		type:'post',
		success:function(data){
			if (data.code==1) {
				// 上传成功将正式地址放入容器内，用于后期form提交整个表单数据
				$('input[name="o_photo"]').val(data.data);
				// 设置图片上传结果信息
				msg.innerHTML = data.msg;
				msg.style.display='block';
				// 隐藏上传按钮
				$('#uploader_btn').hide();
			}else{
				msg.innerHTML = data.msg;
				msg.style.display='block';
				msg.style.backgroundColor='#ff01017d';
			}
		}
	});
}