/* Starts With Selector: [attribute^="value"]
   Ends With Selector: [attribute$="value"] */

var formDefault = window.formDefault || {};
formDefault = {
   onReady: function () {
      //alert("i'm ready!")
   },
   
   prepCookie:function() {
      alert("cookie: " + $("input[name$='chkRememberMe']").is(":checked"));
      //alert("cookie: " + document.getElementById('form_main:chkRememberMe').checked);
      //formCook.setCookie();
   },

   callRest:function(n) {
        //var urlServer = $("input[id$='hidServerUrl']").val();
        var urlServer = "http://localhost:8080/ROOT/restservice/rest/";

        var strFncSuccess = "wsRelEmp.showAjaxSuccess";
        //var webMethod = urlServer + "_layouts/SAQ/ReleveEmploi/wsRelEmp.asmx/InfoUser";
        var webMethod = urlServer + n;
        this.valToRest = n;
        var parameters = null;

        wsRelEmp.setAjaxCall(webMethod, parameters, strFncSuccess);

   },
   valToRest: {}
};


var wsRelEmp = window.wsRelEmp || {};
wsRelEmp = {
	setAjaxCall: function (webMethod, parameters, successFnc) {
        //alert("webMethod: " + webMethod + "\nparameters: " + parameters + "\nsuccessFnc: " + successFnc);
		try {
			$.ajax({
				type: "GET"
				, url: webMethod
				, data: JSON.stringify(parameters)
				, contentType: "application/json; charset=utf-8"
				, dataType: "json"
				, success: eval(successFnc)
				, error: wsRelEmp.showAjaxError
			});
		}
		catch (err) {
			alert("error: " + err.message);
		}
	},

	showAjaxSuccess: function (xData, status, jqXHR) {
		PF("growlPF").renderMessage({
			"summary":"Value pass: " + formDefault.valToRest,
            "detail":"Value return: " + xData,
            "severity":"info"
    	});
        //alert("xData: " + xData);
	},

	showAjaxSucAdm: function (xData, status, jqXHR) {
		var dataStatus, dataMsg;
		dataStatus = xData.d[0].Status;
		dataMsg = xData.d[0].Msg;

		$("#spMsg").text(dataMsg).fadeIn(800).delay(3600).fadeOut(1200);
	},

	showAjaxError: function (jqXHR, textStatus, errorThrown) {
		var sErrMsg = errorThrown;
        //var jqXHRerr = JSON.parse(jqXHR.responseText);
        var jqXHRerr = jqXHR.responseText;
		for (var keyVal in jqXHRerr) {
			if (jqXHRerr.hasOwnProperty(keyVal)) {
				if (jqXHRerr[keyVal] != "") {
					sErrMsg += " - " + jqXHRerr[keyVal];
				}
			}
        }
        alert(sErrMsg);
		//formUtils.showError(sErrMsg);
	}
}


var expXLS = window.expXLS || {};
expXLS = {
	loadTbl : function(dtTbl) {
		dtTbl.DataTable({
			searching: false,
		    ordering:  true
		});
	},
	embedVid : function() {
		alert("embed");
	},
	colorVid : function() {
		alert("color");
	}
};


var pfDtTbl = window.pfDtTbl || {};
pfDtTbl = {
	dtTimer : {},
	dtTblPF : function() {
		this.dtTimer = setInterval(pfDtTbl.showPage, 3000);
	},
	
	showPage: function() {
		var tblTotPage = PF("dtTbl").getPaginator().cfg.pageCount;
		// getPaginator().cfg.page start at 0;
		var tblCurPage = PF("dtTbl").getPaginator().cfg.page;
		
		if (tblCurPage < tblTotPage-1) {
			tblCurPage = tblCurPage + 1;
		}
		else {
			tblCurPage = 0;
		}
		PF("growlPF").renderMessage({
			"summary":"Total pages: " + tblTotPage,
            "detail":"current page: " + (tblCurPage+1),
            "severity":"info"
    	});
		
		PF("dtTbl").getPaginator().setPage(tblCurPage);
	},
	
	stopTimer: function() {
		clearInterval(this.dtTimer);
	}
};


var formCook = window.formCook || {};
formCook = {
   setCookie: function (name, value, days) {
      var expires = "";
      if (days) {
         var date = new Date();
         date.setTime(date.getTime() + (days*24*60*60*1000));
         expires = "; expires=" + date.toUTCString();
      }
      document.cookie = name + "=" + value + expires + "; path=/";
   },
   
   getCookie: function (name) {
      var nameEQ = name + "=";
      var ca = document.cookie.split(';');
      for(var i=0;i < ca.length;i++) {
         var c = ca[i];
         while (c.charAt(0)==' ') c = c.substring(1,c.length);
         if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
      }
      return null; 
   },
  
   eraseCookie: function(name){
      // 0=delete; -1=never expire
      setCookie(name, "", 0);
   },
  
   checkCookie: function () {
      var user = getCookie("username");
      if (user != "") {
         alert("Welcome again " + user);
      } 
      else {
         user = prompt("Please enter your name:", "");
         if (user != "" && user != null) {
            setCookie("username", user, 365);
         }
      }
   }
};


var formUtils = window.formUtils || {};
formUtils = {
   BeginWith: function (objField, chkStr) {
      if (objField.attr("id").match("^" + chkStr)) {
         return true;
      }
      return false;
   },

   EndsWith: function (objField, chkStr) {
      if (objField.attr("id").match(chkStr + "$")) {
         return true;
      }
      return false;
   },

   setDataTable: function (tblToSet) {
      return $(tblToSet).dataTable({
         "bJQueryUI": true
         , "bFilter": false
         , "bAutoWidth": true
         , "sPaginationType": "full_numbers"
         , "aaSorting": []
         , "iDisplayLength": 25
         , "aLengthMenu": [[25, 50, 75, 100], [25, 50, 75, 100]]
         , "oLanguage": { "sUrl": "js/dataTables.fr_CA.txt" }
         , "bDestroy": true
      });
   },

   showError: function (errMsg) {
      var errHTML = "<table style='width:860px' cellpadding='0' cellspacing='0' border='0' class='ms-formtable'><tr><td>&nbsp;</td></tr><tr class='ms-formbody'><td class='ms-authoringcontrols' style='font-size:large'>";
      errHTML += "Une erreur c&rsquo;est produite!&nbsp;-&nbsp; Veuillez communiquer cette erreur au 6124.</td></tr><tr><td>&nbsp;</td></tr>";
      errHTML += "<tr class='ms-formbody'><td class='ms-authoringcontrols' style='font-weight:bold'>Message d&rsquo;erreur: <span style='color:red'>" + errMsg + "</span></td></tr></table>";

      $("#objTbl").html("");
      $("#objTbl").html(errHTML);
   },

   openModal: function (sUrl, sFeatures) {
      var sSetting = "dialogWidth:980px; edge:Raised; center:Yes; resizable:Yes; status:No;"; // dialogHeight:500px;
      var oArgs = new Object();
      oArgs.Link = "colorbox"; //argument pass to ModalDialog

      if (sFeatures != "") {
         sSetting = sFeatures;
      }
      window.returnValue = window.showModalDialog(sUrl, oArgs, sSetting);

      //check the return value here
      if (returnValue) {
         arrRetVal = returnValue.split(";");
         // do something with return value
      }
   },

   openNewWin: function (sUrl, sTitle, sFeatures) {
      var sSetting = "width=1000px,height=500px,menubar=0,toolbar=0,directories=0,location=0,status=0,scrollbars=1,resizable=1";
      if (sFeatures != "") {
         sSetting = sFeatures;
      }
      window.open(sUrl, sTitle, sSetting);
   },

   closeWin: function () {
      //window.parent.close();
      //this.window.close();
      window.close();
   },

   copyToClip: function (objID) {
      var objLnk = $("a[id$='" + objID + "']");

      if (window.clipboardData) {
         window.clipboardData.setData("Text", objLnk.text())
      }
      //return objLnk;
   },
   
   goToUrl: function (sUrl) {
      //alert(sUrl);
      switch (sUrl.toLowerCase()) {
         case "referrer":
            sUrl = document.referrer;
            if (sUrl == "") {
               /*********************************
               * sUrl = "" for current directory
               * sUrl = "/" for root
               *********************************/
               sUrl = "/";
            }
            break;
         case "home":
            sUrl = "/";
            break;
         case "current":
            sUrl = document.location.href;
            break;
         case "source":
            sUrl = formUtils.getParamName(document.location.href, "Source");
            if (sUrl == "") {
               sUrl = document.location.href;
            }
            break;
         default :
            break;
      }
      document.location.href = sUrl;
   },

   showHideMsg: function (spMsg, imgExpCol) {
      if (document.getElementById(spMsg).style.display == 'none') {
         document.getElementById(spMsg).style.display = 'block';
         document.getElementById(imgExpCol).src = "/_layouts/images/collapse.gif";
      }
      else {
         document.getElementById(spMsg).style.display = 'none';
         document.getElementById(imgExpCol).src = "/_layouts/images/expand.gif";
      }
   },

   //HOWTO USE: var src = formUtils.getQueryString($(this).attr("href"), "Source");
   getQueryString: function (qsUrl, name) {
      function parseParams() {
         var params = {},
            e,
            a = /\+/g,  // Regex for replacing addition symbol with a space
            r = /([^&=]+)=?([^&]*)/g,
            d = function (s) { return decodeURIComponent(s.replace(a, " ")); },
            q = qsUrl.split("?")[1];
         while (e = r.exec(q)) {
            params[d(e[1])] = d(e[2]);
         }
         return params;
      }

      this.queryStringParams = parseParams();

      return this.queryStringParams[name];
   },

   //HOWTO USE: var src = formUtils.getParamName($(this).attr("href"), "Source");
   getParamName: function (qsUrl, name) {
      name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
      var regexS = "[\\?&]" + name + "=([^&#]*)";
      var regex = new RegExp(regexS);
      var results = regex.exec(qsUrl);
      if (results == null) {
         return "";
      }
      else {
         return decodeURIComponent(results[1].replace(/\+/g, " "));
      }
   },

   jQueryVer: function () {
      /*try {
         var jVer = $.fn.jquery;
         var jUI = $.ui.version;
         var bootVer = $.fn.tooltip.Constructor.VERSION;
      }
      catch (e){
         console.log(e);
      }*/
      alert("jQuery version: " + $.fn.jquery + "\nBootstrap: " + $.fn.tooltip.Constructor.VERSION);
      //alert("jQuery version: " + $.fn.jquery + "\njQueryUI: " + $.ui.version + "\nBootstrap: " + $.fn.tooltip.Constructor.VERSION);
   }
};

