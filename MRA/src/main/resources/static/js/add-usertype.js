$(document).on("click", ".btn-info", function (event) {
	event.preventDefault();
	if (($('#ut_name').val() != '') && ($('#ut_desc').val() != '') && ($('#ut_cover_area').val() != '') && ($('#ut_area_limit').val() != '')) {

		var User = {
			"user_type_name": $('#ut_name').val(),
			"user_type_desc": $('#ut_desc').val(),
			"user_type_cover_area": $('#ut_cover_area').val(),
			"user_type_cover_limit": parseInt($('#ut_area_limit').val()),
			"area_lat": $('#lat').val(),
			"area_lng": $('#lng').val()
		};
		addUserTypeApiCall(User);
	}
	else {
		if ($('#ut_name').val() == '') {
			setError('ut_name');
		}
		else {
			setClear('ut_name');
		}
		if ($('#ut_desc').val() == '') {
			setError('ut_desc');
		}
		else {
			setClear('ut_desc');
		}
		if ($('#ut_cover_area').val() == '') {
			setError('ut_cover_area');
		}
		else {
			setClear('ut_cover_area');
		}
		if ($('#ut_area_limit').val() == '') {
			setError('ut_area_limit');
		}
		else {
			setClear('ut_area_limit');
		}
	}

});

function setError(id) {
	$('#' + id).css("border-color", "#ff383b");
	$('#' + id).css("box-shadow", "0 1px 1px rgba(0, 0, 0, 0.075) inset, 0 0 8px rgba(232, 88, 88, 0.6)");
	$('#' + id).css("outline", "0 none");
}
function setClear(id) {
	$('#' + id).css("border-color", "#66afe9");
	$('#' + id).css("box-shadow", "0 1px 1px rgba(0, 0, 0, 0.075) inset, 0 0 8px rgba(102, 175, 233, 0.6)");
	$('#' + id).css("outline", "0 none");
}


function addUserTypeApiCall(User) {
	//var token = sessionStorage.getItem("Authorization");
	var req = JSON.stringify(User);
	$.ajax({
		type: "POST",
		headers: {
			// 'Authorization': token,
			'content-type': 'application/json'
		},
		url: baseUrl + addUserTypeApi,
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
			if (data.status == '200 OK') {
				$.toast({
				    text: data.message,
				    hideAfter: 2000,
				    icon: 'success',
				    loader: false,
				    showHideTransition: 'fade',
				    stack: false,
				    position: 'top-left'
  	
				});
				$('.btn-default').click();
				
			}
			else {
				$.toast({
				    text: data.message,
				    hideAfter: 2000,
				    icon: 'error',
				    loader: false,
				    showHideTransition: 'fade',
				    stack: false,
				    position: 'top-left'
  	
				});
				$('.btn-default').click();

			}

		},
		error: function (e) {
			console.log("ERROR : ", e);
			$.toast({
			    text: 'Error in Server Connection',
			    hideAfter: 2000,
			    icon: 'error',
			    loader: false,
			    showHideTransition: 'fade',
			    stack: false,
			    position: 'top-left'
	
			});
		}
	});

}
