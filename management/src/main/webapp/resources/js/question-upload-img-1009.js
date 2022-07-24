 $(function(){
	 var uppic = $("#uploadify").Huploadify({
	//	var uppic = $("#uploadify").uploadifive({
				//   http://www.360doc.com/content/16/0927/23/466494_594249476.shtml
		 
		 
				'debug'	 : false,
				'buttonText'	: '点击上传图片看',
				'width': 120,//上传按钮的宽度（单位：像素）
			    'height': 30,//上传按钮的高度（单位：像素）
			    
				'buttonCursor'	: 'pointer',
		 //		'fileTypeExts'  : '*.jpg;*.png;*jpeg',

				'fileType'         : '.jpg,.jpeg,.gif,.png',
				
		 		'queueID' : 'queue',
		 		 'uploadLimit': 1,//指定允许上传的最大文件数量

			//	'uploader':	'uu111.php',

				'uploader'	 : document.getElementsByTagName('base')[0].href + 'secure/upload-uploadify-img/',
				'multi'	 : false,
				'auto'	 : true,
				'height'	 : '26',
				'width'	 : '60',
				'requeueErrors'	: false,
				'fileSizeLimit'	: '200', // expects input in kb
				
				   'onFallback': function () { 
				        alert('浏览器不支持HTML5,无法上传！');
				    },
			    
				'cancelImage'	: document.getElementsByTagName('base')[0].href + 'resources/js/Huploadify/cancel.png',
				
				overrideEvents:['onSelectError','onDialogClose'],
				onUploadProgress: function() {
					alert("onUploadProgress....");

//					$('#loader').show();
				},
				onUpload : function(file){
					document.getElementsById("submitPic").disabled=true;
				},
				
				onUploadComplete: function(file) {
                    console.log(data);


					alert("onUploadComplete....");
					
					document.getElementsById("submitPic").disabled=false;
					
//					$('#div-file-list').html('<a id=\'file-name\'>' + file.name + '</a>');
//					$('#loader').fadeOut(100);
					$('#maincontent').load(location.href+' #maincontent > *');
					$(".fade").modal('hide');
				},
				onUploadSuccess : function(file, data, response) {
			            alert('The file ' + file.name + ' was successfully uploaded with a response of ' + response + ':' + data);
			        	var fileurl = data;
			        	var destination  = $(".img-destination input").val();
			        	if(destination == -1){
//			        		var textareaval = $(".question-content textarea").val();
//			        		$(".question-content textarea").val( textareaval + "<img class=\"question-content-img\" src=\"" +  fileurl + "\">");
			        		
			        		var displayImg = $(".question-content").find(".diaplay-img");
			        		if(displayImg.length  == 0){
			        			$(".question-content textarea").after("<a href=\"..\\" + fileurl + "\" class=\"diaplay-img display-content-img\" target=\"_blank\" data-url=\"" + fileurl + "\">预览图片</a>");
			        		}else{
			        			displayImg.attr("href", fileurl);
			        		}
			        		
			        	}else{
			        		var thisopt =  $($(".add-opt-item")[destination]);
			        		var displayImg = thisopt.find(".diaplay-img");
			        		
			        		if(displayImg.length  == 0){
			        			thisopt.find("input.form-question-opt-item").after("<a href=\"..\\" + fileurl + "\" class=\"diaplay-img display-opt-img\" target=\"_blank\" data-url=\"" + fileurl + "\">预览图片</a>");
			        		}else{
			        			displayImg.attr("href", fileurl);
			        		}
			        		
//			        		var optval = thisopt.val();
//			        		thisopt.val(optval + "<img class=\"question-opt-img\" src=\"" +  fileurl + "\">");
			        	}
			        	
			        	/*$("#add-question-img-dialog").dialog( "close" );*/
				},
				onSelectError: function(file,errorCode,errorMsg) {
					if(errorCode==-110){
						util.error("只能上传100KB以下的图片。");
						return false;
					}
				},
				onUploadError: function(file,errorCode,errorMsg, errorString) {
					util.error(errorMsg);
				}
			});
	 
	 
	 
			$("#question-add-form").on("click",".add-img",function() {
				alert("#question-add-form.......");

				
				$(".fade").modal({backdrop:true,keyboard:true});
				
//				$("#add-question-img-dialog").dialog("open");
				$("#file-name").empty();
				
				if($(this).hasClass("add-content-img")){
					$(".img-destination label").text("试题内容");
					$(".img-destination input").val(-1);
				}else if($(this).hasClass("add-opt-img")){
					$(".img-destination label").text("试题选项 ");
					var this_index = $(".add-opt-img").index($(this));
					$(".img-destination label").append(String.fromCharCode(65 + this_index));
					$(".img-destination input").val(this_index);
				}
			});
	 
	 
			
			 
				$("#bk-conent-question-content").delegate(".diaplay-img","click",function() {
					alert("#-bk-conent-question-content......");

					window.open(location.protocol + "//" + location.host + "/" + $(this).attr("href"));
					e.preventDefault();
				});
		 
	 
 });  
		