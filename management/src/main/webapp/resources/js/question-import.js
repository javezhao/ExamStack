$(function () {
  var up = $('#uploadify').Huploadify({
    'debug': false,
    'buttonText': '点击上传附件',
    'uploader': document.getElementsByTagName('base')[0].href + 'secure/upload-uploadify/',
    'queueID': 'fileQueue',
    'multi': false,
    'auto': true,
    'fileType' : '.xlsx',
    'fileTypeExts'  : '*.xlsx;*.xls',
    
    'requeueErrors': false,
    'fileSizeLimit': '20480', // expects input in kb
    'cancelImage': document.getElementsByTagName('base')[0].href + 'resources/js/uploadify/cancel.png',
    removeCompleted: true,
    overrideEvents: ['onSelectError', 'onDialogClose'],
    onUploadComplete: function (file) {
      document.getElementById('submit').disabled = false//当开始上传文件，要防止上传未完成而表单被提交

      //util.error("onUploadComplete  ...");

      // alert('上传完成')
      //	document.write("onUploadComplete....<br/>");

    },

    onUploadSuccess: function (file, data, response) {
      $('#div-file-list').html('<a class=\'file-name\'>'
        + file.name
        + '</a><input type=\'hidden\' value=\''
        + file.name + '\' />')

      // alert('上传成功')
      //	document.write("onUploadSuccess....<br/>");

    },
    onSelectError: function (file, errorCode, errorMsg) {
      if (errorCode == -110) {
        util.notify('只能上传20M以下的文件。')
        return false
      }
    },
    onUploadError: function (file, errorCode, errorMsg, errorString) {
      util.error(errorMsg)
    },

    onCancel: function (file) {
      alert(file.name + ' 已取消上传~!')
    },

    onFallback: function () {
      alert('该浏览器无法使用!')
      //	document.write("onUploadComplete....<br/>");

    },

    onUpload: function (file) {
      document.getElementById('submit2').disabled = true//当开始上传文件，要防止上传未完成而表单被提交
      //	document.write("onUpload....<br/>");
      //	util.error("onUpload  ...");
      alert('onUpload ....')

    }

  })

  $('#submit').click(function () {
    console.log(1111)
  })
  $('#from-question-import').submit(function () {

    // document.write('11111....<br/>')
    // console.log($('#select_btn_1').files[0])
    var filePath = $('#div-file-list').find('input').val()

    //	alert($("#from-question-import").attr("action"));
//			return false

    $.ajax({
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      type: 'POST',
      url: $('#from-question-import').attr('action') + '/' + $('.upload-question-group select').val(),

      data: filePath,
      success: function (message, tst, jqXHR) {
        console.log(jqXHR)
        // alert('AJAX 请求完成')

        if (!util.checkSessionOut(jqXHR))
          return false
        if (message.result == 'success') {
          util.success('导入成功', function () {
            $('#submit-div .form-message').text(message.messageInfo)
          //  document.location.href = document.getElementsByTagName('base')[0].href + '/admin/question/question-list/filter-0-0-0-0-0-1.html'
          })
        } else {
          util.error('操作失败请稍后尝试:' + message.result)
          $('#submit-div .form-message').text(message.messageInfo)
          $('#btn-add-submit').removeAttr('disabled')
        }
      },
      error: function (jqXHR, textStatus) {
        // console.log(jqXHR);
        //util.error(jqXHR);
        util.error('操作失败请稍后再尝试...')
        $('#btn-add-submit').removeAttr('disabled')
      }
    })
    return false
  })
})
