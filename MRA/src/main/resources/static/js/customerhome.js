$(document).ready(function () {

loadUserType();
listProducts();


});

function loadUserType()
{
		$.ajax({
		type: "POST",
		url: baseUrl + listUserTypeApi,
		data: { data: "" },
		cache: false,
		success: function (result) {
			data = JSON.parse(JSON.stringify(result.data));
			if (result.status == '200 OK') {
				$('#user_type').empty();
				$('#user_type').append("<option value=\"\" disabled selected>User Type</option>");
				$.each(data, function () {
					$('#user_type').append("<option value=" + this.user_type_id + ">" + this.user_type_name + "</option>");
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


function listProducts()
{
	$.ajax({
		type: "POST",
		url: baseUrl + listproductApi,
		data: { data: "" },
		cache: false,
		success: function (result) {
			data = JSON.parse(JSON.stringify(result.data));
			if (result.status == '200 OK') {
				$('#prod_list').empty();
				$('#prod_list').append("<option value=\"\" disabled selected>Product</option>");
				$.each(data, function () {
					$('#prod_list').append("<option value=" + this.product_id + ">" + this.product_name + "</option>");
				});
			
	}
			else if (data.status == '403 Forbidden'){
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

$(document).on('change','#user_type',function(){
	//alert($(this).val());
	fetchUserTypeApiCall($(this).val());
});

function fetchUserTypeApiCall(id) {
	//var token = sessionStorage.getItem("Authorization");
	var req = { 'user_type_id': id };
	req = JSON.stringify(req);

	$.ajax({
		type: "POST",
		headers: {
			// 'Authorization': token,
			'content-type': 'application/json'
		},
		url: baseUrl + getUserTypeApi,
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
				var response = JSON.parse(JSON.stringify(data.data));
				$("#cover_area").val(response.user_type_cover_area);
				$("#lat").val(response.area_lat);
				$("#lng").val(response.area_lng);
				$("#area_limit").val(response.user_type_cover_limit);
				console.log($("#lat").val());
				console.log($("#lng").val());
				console.log($("#area_limit").val());
				console.log($("#cover_area").val());
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



$(document).on("click", ".btn-info", function (event) {
	event.preventDefault();

});

