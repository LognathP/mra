$('#dataTables-example').DataTable();
$(document).ready(function () {

	loadUserType();
});

function loadUserType()
{
	var editButtonHtml = "<a class=\"btn btn-primary btn-sm\" id=\"edit\" href=\"edituser.html\"><i class=\"fa fa-edit \"></i> Edit</a>";
	var deleteButtonHtml = "<a class=\"btn btn-danger btn-sm\" id=\"delete\" ><i class=\"fa fa-trash-o \"></i> Delete</a>";
	$.ajax({
		type: "POST",
		url: baseUrl + listUserTypeApi,
		data: { data: "" },
		cache: false,
		success: function (result) {
			data = JSON.parse(JSON.stringify(result.data));
			if (result.status == '200 OK') {
				$.each(data, function (index, data) {
					var rowIndex = $('#dataTables-example').dataTable().fnAddData([
						data.user_type_name,
						data.user_type_desc,
						data.user_type_cover_area,
						data.user_type_cover_limit,
						editButtonHtml + "  " + deleteButtonHtml]
					);
					var row = $('#dataTables-example').dataTable().fnGetNodes(rowIndex);
					$(row).attr('id', data.user_type_id);
				});
				
				
			}
			else {
				$.toast({
				    text: result.message,
				    hideAfter: 2000,
				    icon: 'error',
				    loader: false,
				    showHideTransition: 'fade',
				    stack: false,
				    position: 'top-left'
  	
				});
	
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

$(document).on("click", "#edit", function () {
	var uid = $(this).closest('tr').attr('id');
	sessionStorage.setItem('userTypeId', uid);
});

$(document).on("click", "#delete", function () {
	var uid = $(this).closest('tr').attr('id');
	deleteUserTypeApiCall(uid);
});

function deleteUserTypeApiCall(id) {
	var req = { 'user_type_id': id };
	req = JSON.stringify(req);

	$.ajax({
		type: "POST",
		headers: {
			// 'Authorization': token,
			'content-type': 'application/json'
		},
		url: baseUrl + deleteUserTypeApi,
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
				loadUserType();
				
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



