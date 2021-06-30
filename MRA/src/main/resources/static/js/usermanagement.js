$(document).ready(function () {

	//$("#progress-box").show();
	listCustomersApiCall($('#user').val());


});

function listCustomersApiCall(user) {
	$("#progress-box").show();
	// Get form
	//var form = $('#fileUploadForm')[0];
	//var getUrl = window.location;
	//var baseUrl = "https://6ac03d7474d2.ngrok.io";
	var api = "/followup-ai/list-customers";
	//var token = sessionStorage.getItem("Authorization");

	$.ajax({
		type: "POST",
		headers: { 'Authorization': token },
		url: baseUrl + api, processData: false,
		contentType: false,
		crossDomain: true,
		cache: false,
		timeout: 60000,
		success: function (data) {
			console.log(data);
			if (data.statuscode == '200') {
				$("#progress-box").hide();
				var response = JSON.parse(JSON.stringify(data.data));
				if (user == 'IA') {
					var viewTd = "<td><label><a class=\"btn btn-gradient-dark edit-btn\" href=\"ia_customer_status\" ";
				}
				else if (user == 'CM') {
					var viewTd = "<td><label><a class=\"btn btn-gradient-dark edit-btn\" href=\"cm_customer_status\" ";
				}
				else if (user == 'IT') {
					var viewTd = "<td><label><a class=\"btn btn-gradient-dark edit-btn\" href=\"it_customer_status\" ";
				}
				else if (user == 'SP') {
					var viewTd = "<td><label><a class=\"btn btn-gradient-dark edit-btn\" href=\"status\" ";
				}
				var odd_even = true;
				var tr = "";
				var notProcessed = "Not Processed";
				var hyphen = "-";
				$.each(response, function (key, value) {
					tr = tr + "<tr role=\"row\" class=\"" + (odd_even ? "odd" : "even") + "\">";
					tr = tr + "<td class=\"sorting_1\" >" + value.customer_id + "</td>";
					tr = tr + "<td class=\"sorting_1\" ><label>" + value.customer_name + "</label>" + "</td>";
					tr = tr + "<td><label class=\"badge badge-danger\" >" + (value.status == null ? notProcessed : value.status) + "</label>" + "</td>";
					if (user == 'IT') {
						tr = tr + "<td><label>" + (value.feedback == null ? hyphen : value.feedback) + "</label>" + "</td>";
					}
					tr = tr + viewTd + "id=\"" + value.id + "\"" + "> View Details</a></label></td>";
					tr = tr + "</tr>";
					odd_even = !odd_even;
				});
				$("#progress-box").hide();
				$("#order-listing_list_cm").append(tr);//append body 
				$(function () {
					$("#order-listing_list_cm").dataTable();
				})
			}
			else if (data.statuscode == '400') { alert(data.message); }
			else if (data.statuscode == '401') { alert(data.message); }
			else if (data.statuscode == '500') { alert(data.message); }
			else { alert(data.message); }

		},
		error: function (e) {

			console.log("ERROR : ", e);
			alert("Error occured on calling API");
		}
	});

}