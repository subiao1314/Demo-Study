jQuery.fn.extend({
    uploadPreview: function (opts) {
        var _self = this,
            _this = $(this);
        opts = jQuery.extend({
            Img: "ImgPr",
            Width: 100,
            Height: 100,
            ImgType: ["gif", "jpeg", "jpg", "bmp", "png"],
            Callback: function () {}
        }, opts || {});
        _self.getObjectURL = function (file) {
            var url = null;
            if (window.createObjectURL != undefined) {
                url = window.createObjectURL(file)
            } else if (window.URL != undefined) {
                url = window.URL.createObjectURL(file)
            } else if (window.webkitURL != undefined) {
                url = window.webkitURL.createObjectURL(file)
            }
            return url
        };
        _this.change(function () {
            if (this.value) {
                if (!RegExp("\.(" + opts.ImgType.join("|") + ")$", "i").test(this.value.toLowerCase())) {
                    console.log("选择文件错误,图片类型必须是" + opts.ImgType.join("，") + "中的一种");
                    //this.value = "";
                    $("#" + opts.Img).attr('src', '');
                    return false
                }
                if ($.browser.msie) {
                    try {
                        $("#" + opts.Img).attr('src', _self.getObjectURL(this.files[0]))
                    } catch (e) {
                        var src = "";
                        var obj = $("#" + opts.Img);
                        var div = obj.parent("div")[0];
                        _self.select();
                        if (top != self) {
                            window.parent.document.body.focus()
                        } else {
                            _self.blur()
                        }
                        src = document.selection.createRange().text;
                        document.selection.empty();
                        obj.hide();
                        obj.parent("div").css({
                            'filter': 'progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)',
                            'width': opts.Width + 'px',
                            'height': opts.Height + 'px'
                        });
                        div.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = src
                    }
                } else {
                    $("#" + opts.Img).attr('src', _self.getObjectURL(this.files[0]))
                }
                opts.Callback()
            }else{
            	//$("#" + opts.Img).attr('src', '');
            	opts.Callback()
            }
        })
    }
});


var isIE = /msie/i.test(navigator.userAgent) && !window.opera; 
function fileChange(target) {
	var fileSize = 0; 
	var filetypes =[".jpg",".png"];//".rar",".txt",".zip",".doc",".ppt",".xls",".pdf",".docx",".xlsx" 
	var filepath = target.val();
	
	
	var filemaxsize = 1024*2;//2M
	if(filepath){
		var isnext = false; 
		var fileend = filepath.substring(filepath.lastIndexOf("."),filepath.length);
		if(filetypes && filetypes.length>0){
			for(var i =0; i<filetypes.length;i++){
				if(filetypes[i]==fileend){
					isnext = true; 
					break;
				}
			} 
		} 
		if(!isnext){ 
			return "请选择JPG或PNG类型文件"; 
		} 
	}else{
		return ""; 
	}
	
	if (isIE && !target.prop('files')) {
		var filePath = target.val(); 
		var fileSystem = new ActiveXObject("Scripting.FileSystemObject"); 
			if(!fileSystem.FileExists(filePath)){
			return "附件不存在，请重新输入！"; 
		} 
		var file = fileSystem.GetFile (filePath); 
		fileSize = file.Size; 
	} else {
		fileSize = target.prop('files')[0].size; 
	}

	var size = fileSize / 1024; 
	if(size>filemaxsize){
		return "附件大小不能大于"+filemaxsize/1024+"M！"; 
	}
	
	if(size<=0){
		return "附件大小不能为0M！"; 
	}
}

function autoAddEllipsis(pStr, pLen) {
    // 原字符串长度
    var _strLen = pStr.length;
    var _tmpCode;
    var _cutString;
    // 默认情况下，返回的字符串是原字符串的一部分
    var _cutFlag = "1";
    var _lenCount = 0;
    var _ret = false;
    if (_strLen <= pLen / 2) {
        _cutString = pStr;
        _ret = true;
    }
    if (!_ret) {
        for (var i = 0; i < _strLen; i++) {
            if (isFull(pStr.charAt(i))) {
                _lenCount += 2;
            } else {
                _lenCount += 1;
            }

            if (_lenCount > pLen) {
                _cutString = pStr.substring(0, i);
                _ret = true;
                break;
            } else if (_lenCount == pLen) {
                _cutString = pStr.substring(0, i + 1);
                _ret = true;
                break;
            }
        }
    }
    if (!_ret) {
        _cutString = pStr;
        _ret = true;
    }
    if (_cutString.length == _strLen) {
        _cutFlag = "0";
    }
    return {"cutstring": _cutString, "cutflag": _cutFlag};
}

function isFull(pChar) {
    for (var i = 0; i < pChar.strLen; i++) {
        if ((pChar.charCodeAt(i) > 128)) {
            return true;
        } else {
            return false;
        }
    }
}