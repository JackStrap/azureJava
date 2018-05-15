/// <reference path="..\..\..\..\..\..\..\Saq.References\jquery-vsdoc.js" />

///Overwrite Microsoft SharePoint(core.js) function PreSaveItem()
function PreSaveItem() {
	$('.requiredMissing').remove();

	checkForm('.saqReq');
	
	if ($.trim($("span[id$='lblEmpID']").text()) == "") {
		$("#lblErrMsg").append("<span class='requiredMissing'>Vous devez choisir un employé valide.</span>");
	}
	
	if ($('.requiredMissing').length != 0) {
		return false;
	}
	else {
		return true;
	}
}


var formDefault = window.formDefault || {};
formDefault = {
	onReady: function () {
		var $hidRadVal = $("input[id$='hidRadVal']");
		var $hidRadCode = $("input[id$='hidRadCode']");
		var $txtAutre = $("textarea[id$='relEmpAutre']");
		var $rbAll = $("form input:radio");

		if ($hidRadCode.val() == "radAutre") {
			$("#rowOther").show();
			$txtAutre.addClass("saqReq");
		}

		$rbAll.on("click", function (event) {
			$hidRadVal.val($(this).data("text"));
			$hidRadCode.val($(this).val());

			if ($(this).val() == "radAutre") {
				$("#rowOther").show();
				$txtAutre.addClass("saqReq");
			}
			else {
				$("#rowOther").hide();
				$txtAutre.removeClass("saqReq");
			}
		});
	},

	saqDialog: function (ppeID) {
		$('.requiredMissing').remove();

		if (ppeID == null || ppeID == "" || typeof (ppeID) == "undefined") {
			formUtils.showError("Erreur: Le champs est mal configurer.");
			return false;
		}
		var ppEditor = $("[id$='" + ppeID + "_upLevelDiv']");

		if (ppEditor.length > 0) {
			var ppeDataResolved = $("#divEntityData", ppEditor).attr("isresolved");
			if (ppeDataResolved.toLowerCase() == "true") {
				var ppeDataKey = $("#divEntityData", ppEditor).attr("key");

				ppeDataKey = ppeDataKey.substring(ppeDataKey.indexOf("\\") + 1);

				var urlServer = $("input[id$='hidServerUrl']").val();

				var strFncSuccess = "wsRelEmp.showAjaxSuccess";
				var webMethod = urlServer + "_layouts/SAQ/ReleveEmploi/wsRelEmp.asmx/InfoUser";
				var parameters = { usrName: ppeDataKey };

				wsRelEmp.setAjaxCall(webMethod, parameters, strFncSuccess);
			}
			else {
				$("span[id$='lblEmpFirstName']").text("");
				$("span[id$='lblEmpLastName']").text("");
				$("span[id$='lblEmpID']").text("");
				$("span[id$='lblEmpService']").text("");
				$("input[id$='hidFullName']").val("");
				$("input[id$='hidEmail']").val("");
				$("input[id$='hidFirstName']").val("");
				$("input[id$='hidLastName']").val("");
				$("input[id$='hidMatricule']").val("");
				$("input[id$='hidService']").val("");
			}
		}
	}
};


var wsRelEmp = window.wsRelEmp || {};
wsRelEmp = {
	setAjaxCall: function (webMethod, parameters, successFnc) {
		try {
			$.ajax({
				type: "POST"
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
		var userLN = $("span[id$='lblEmpLastName']");
		var userID = $("span[id$='lblEmpID']");
		var userFN = $("span[id$='lblEmpFirstName']");
		var userSucc = $("span[id$='lblEmpService']");

		var userFull = $("input[id$='hidFullName']");
		var hidEmail = $("input[id$='hidEmail']");
		var hidFN = $("input[id$='hidFirstName']");
		var hidLN = $("input[id$='hidLastName']");
		var hidMat = $("input[id$='hidMatricule']");
		var hidServ = $("input[id$='hidService']");

		userFN.text(xData.d[0].FirstName);
		userLN.text(xData.d[0].LastName);
		userID.text(xData.d[0].Matricule);
		userSucc.text(xData.d[0].NoSucc);

		userFull.val(xData.d[0].FullName);
		hidEmail.val(xData.d[0].eMail);

		hidFN.val(xData.d[0].FirstName);
		hidLN.val(xData.d[0].LastName);
		hidMat.val(xData.d[0].Matricule);
		hidServ.val(xData.d[0].NoSucc);

		if (xData.d[0].FirstName == "") {
			var errEmp = $("#errEmp");
			if (errEmp.length < 1) {
				userID.after("<span id='errEmp' class='requiredMissing'>Le numéro d'employé n'est pas valide.</span>");
			}
		}
		else {
			$("#errEmp").remove();
		}
	},

	showAjaxSucAdm: function (xData, status, jqXHR) {
		var dataStatus, dataMsg;
		dataStatus = xData.d[0].Status;
		dataMsg = xData.d[0].Msg;

		$("#spMsg").text(dataMsg).fadeIn(800).delay(3600).fadeOut(1200);
	},

	showAjaxError: function (jqXHR, textStatus, errorThrown) {
		var sErrMsg = errorThrown;
		var jqXHRerr = JSON.parse(jqXHR.responseText);
		for (var keyVal in jqXHRerr) {
			if (jqXHRerr.hasOwnProperty(keyVal)) {
				if (jqXHRerr[keyVal] != "") {
					sErrMsg += " - " + jqXHRerr[keyVal];
				}
			}
		}
		formUtils.showError(sErrMsg);
	}
}


var formUtils = window.formUtils || {};
formUtils = {
	setDatePicker: function () {
		//default setting for all calendar
		$.datepicker.setDefaults({
			dateFormat: 'yy-mm-dd'
			, firstDay: 0
			, showOn: "both"
			, buttonImageOnly: true
			, buttonImage: "/_layouts/images/calendar.gif"
			, buttonText: "Calendrier"
		});
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
		errHTML += "<tr class='ms-formbody'><td class='ms-authoringcontrols' style='font-weight:bold'>Message d&rsquo;erreur: <span style='color:red'>" + errMsg + "</span></td></tr>"
		errHTML += "<tr><td>&nbsp;</td></tr><tr><td style='text-align:center'><input type='button' id='btnClose' value='Fermer' onclick='formUtils.closeErr();' /></td></tr></table>";

		$("#errDiv").html("");
		$("#formEmp").hide();
		$("#errDiv").html(errHTML).show();
		$("#btnClose").focus();
	},

	writeError: function (objHtml, errMsg) {
		var $this = $('#' + objHtml);

		var errorHtml = "<div class='ui-widget-error'>";
		errorHtml += "<div class='ui-state-error ui-corner-all' style='padding: 0 .7em;'>";
		errorHtml += "<p>";
		errorHtml += "<span class='ui-icon ui-icon-alert' style='float:left; margin-right: .3em;'></span>";
		errorHtml += errMsg;
		errorHtml += "</p>";
		errorHtml += "</div>";
		errorHtml += "</div>";

		$this.html(errorHtml).show();
	},

	closeErr: function () {
		//$("#errDiv").hide();
		document.location.href = document.location.href;
		//$("#formEmp").show();
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
			default:
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

	transComaToDot: function (strToRep) {
		var newStr = strToRep.replace(",", ".");
		return newStr;
	},

	replaceAll: function (str, find, replace) {
		return str.replace(new RegExp(formUtils.escapeRegExp(find), 'g'), replace);
	},

	escapeRegExp: function (str) {
		return str.replace(/([.*+?^=!:${}()|\[\]\/\\])/g, "\\$1");
	},

	jQueryVer: function () {
		alert("jQuery version: " + $.fn.jquery + "\njQueryUI: " + $.ui.version);
	},

	showObject: function (obj) {
		var retVal = "";

		for (var props in obj) {
			if (obj != null && props != null && obj[props] != null) {
				if (obj != "undefined" && props != "undefined" && obj[props] != "undefined") {
					try {
						retVal += props + ": " + obj[props] + "\n";
					}
					catch (err) {
						alert("error: " + err.message);
					}
				}
			}
		}
		return retVal;
	},

	//HOWTO USE: formUtils.strFormat("url: {0} <br> year: {1}", url, year);
	strFormat: function () {
		var retVal = arguments[0]; //always first argument

		// start with second args
		for (var x = 1; x < arguments.length; x++) {
			var regEx = new RegExp("\\{" + (x - 1) + "\\}", "gm"); //gm == Global and Multiline search
			retVal = retVal.replace(regEx, arguments[x]);
		}

		return retVal;
	}
};