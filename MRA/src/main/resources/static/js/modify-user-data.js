var baseUrl = "http://dev.paymee.ai";
var token = sessionStorage.getItem("token");

$(document).on("click", ".delete-btn", function () {

	var uid = $(this).closest('tr').attr('id');
	console.log(uid);
	if (uid != undefined) {
		deleteEmployeeApiCall(uid);
		//		location.reload(true);
	}
	$(this).closest('tr').remove();

});

$(document).on("click", ".save-btn", function () {

	var uid = $(this).closest('tr').attr('id');
	console.log(uid);

	if (uid == undefined) {

		var addEmp = {
			"name": $(this).closest('tr').find("input")[1].value,
			"email": $(this).closest('tr').find("input")[2].value,
			"staff_id": parseInt($(this).closest('tr').find("input")[0].value),
			"designation": $(this).closest('tr').find("input")[3].value,
			"manager_id": parseInt($(this).closest('tr').find("input")[4].value),
			"manager_name": $(this).closest('tr').find("input")[5].value,
			"manager_email": $(this).closest('tr').find("input")[6].value
		};
		addEmployeeApiCall(addEmp);
		//location.reload(true);

	}
	else {

		var editEmp = {
			"emp_unique_id": parseInt(uid),
			"name": $(this).closest('tr').find("input")[1].value == '' ? $($(this).closest('tr').find("label")[1]).html() : $(this).closest('tr').find("input")[1].value,
			"email": $(this).closest('tr').find("input")[2].value == '' ? $($(this).closest('tr').find("label")[2]).html() : $(this).closest('tr').find("input")[2].value,
			"staff_id": parseInt($(this).closest('tr').find("input")[0].value == '' ? $($(this).closest('tr').find("label")[0]).html() : $(this).closest('tr').find("input")[0].value),
			"designation": $(this).closest('tr').find("input")[3].value == '' ? $($(this).closest('tr').find("label")[3]).html() : $(this).closest('tr').find("input")[3].value,
			"manager_id": parseInt($(this).closest('tr').find("input")[4].value == '' ? $($(this).closest('tr').find("label")[4]).html() : $(this).closest('tr').find("input")[4].value),
			"manager_name": $(this).closest('tr').find("input")[5].value == '' ? $($(this).closest('tr').find("label")[5]).html() : $(this).closest('tr').find("input")[5].value,
			"manager_email": $(this).closest('tr').find("input")[6].value == '' ? $($(this).closest('tr').find("label")[6]).html() : $(this).closest('tr').find("input")[6].value
		};
		editEmployeeApiCall(editEmp);
		//location.reload(true);

	}


});




function addEmployeeApiCall(addEmp) {

	// Get form
	//var getUrl = window.location;
	//var baseUrl = getUrl.protocol + "//" + getUrl.host + "/" + getUrl.pathname.split('/')[1];
	//var baseUrl = "https://eefdf38aa303.ngrok.io";
	var api = "/followup-ai/add-employee";
	//var token = sessionStorage.getItem("Authorization");
	var req = JSON.stringify(addEmp);
	$.ajax({
		type: "POST",
		headers: {
			'Authorization': token,
			'content-type': 'application/json'
		},
		// headers: {
		// 	'Authorization': token,
		// 	'content-type': 'application/json'
		// },
		url: baseUrl + api,
		dataType: "json",
		data: req,
		processData: false,
		contentType: false,
		crossDomain: true,
		async: true,
		cache: false,
		timeout: 60000,
		success: function (data) {

			console.log("SUCCESS : ", data);
			if (data.statuscode == '200') { alert(data.message); }
			else if (data.statuscode == '400') { alert(data.message); }
			else if (data.statuscode == '401') { alert(data.message); }
			else if (data.statuscode == '500') { alert(data.message); }
			else { alert(data.message); }
			location.reload(true);
		},
		error: function (e) {

			console.log("ERROR : ", e);
			alert("Error occured on calling API");

		}
	});

}



function editEmployeeApiCall(editEmp) {

	// Get form
	//var getUrl = window.location;
	//var baseUrl = getUrl.protocol + "//" + getUrl.host + "/" + getUrl.pathname.split('/')[1];
	//	var baseUrl = "https://eefdf38aa303.ngrok.io";
	var api = "/followup-ai/edit-employee";
	//var token = sessionStorage.getItem("Authorization");
	var req = JSON.stringify(editEmp);
	$.ajax({
		type: "POST",
		headers: {
			'Authorization': token,
			'content-type': 'application/json'
		},
		// headers: {
		// 	'Authorization': token,
		// 	'content-type': 'application/json'
		// },
		url: baseUrl + api,
		dataType: "json",
		data: req,
		processData: false,
		contentType: false,
		async: true,
		crossDomain: true,
		cache: false,
		timeout: 60000,
		success: function (data) {

			console.log("SUCCESS : ", data);
			if (data.statuscode == '200') { alert(data.message); }
			else if (data.statuscode == '400') { alert(data.message); }
			else if (data.statuscode == '401') { alert(data.message); }
			else if (data.statuscode == '500') { alert(data.message); }
			else { alert(data.message); }
			location.reload(true);
		},
		error: function (e) {

			console.log("ERROR : ", e);
			alert("Error occured on calling API");

		}
	});

}

function deleteEmployeeApiCall(empId) {

	// Get form
	//var getUrl = window.location;
	//var baseUrl = getUrl.protocol + "//" + getUrl.host + "/" + getUrl.pathname.split('/')[1];
	//var baseUrl = "https://eefdf38aa303.ngrok.io";
	var api = "/followup-ai/delete-employee";
	//var token = sessionStorage.getItem("Authorization");
	var req = { 'emp_unique_id': empId };
	req = JSON.stringify(req)

	$.ajax({
		type: "POST",
		headers: {
			'Authorization': token,
			'content-type': 'application/json'
		},
		// headers: {
		// 	'Authorization': token,
		// 	'content-type': 'application/json'
		// },
		url: baseUrl + api,
		dataType: "json",
		data: req,
		processData: false,
		contentType: false,
		async: true,
		crossDomain: true,
		cache: false,
		timeout: 60000,
		success: function (data) {

			console.log("SUCCESS : ", data);
			if (data.statuscode == '200') { alert(data.message); }
			else if (data.statuscode == '400') { alert(data.message); }
			else if (data.statuscode == '401') { alert(data.message); }
			else if (data.statuscode == '500') { alert(data.message); }
			else { alert(data.message); }
			location.reload(true);
		},
		error: function (e) {

			console.log("ERROR : ", e);
			alert("Error occured on calling API");

		}
	});

}


