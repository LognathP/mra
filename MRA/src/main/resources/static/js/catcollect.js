//$('#dataTables-example').DataTable();
var modalHtml = $("#myModal").html();
$(document).ready(function () {

	loadProduct();
});


$(document).on("click", "#prod", function () {
	$("#myModal").html(modalHtml);
});



function loadProduct()
{
	$('#dataTables-example').DataTable().clear();
	var editButtonHtml = "<a class=\"btn btn-primary btn-sm\" id=\"btnEdit\" data-toggle=\"modal\" data-target=\"#myModalEdit\"  ><i class=\"fa fa-edit \"></i> Edit</a>";
	var deleteButtonHtml = "<a class=\"btn btn-danger btn-sm\" id=\"btnDelete\" ><i class=\"fa fa-trash-o \"></i> Delete</a>";
	$.ajax({
		type: "POST",
		url: baseUrl + listproductApi,
		data: { data: "" },
		cache: false,
		success: function (result) {
			data = JSON.parse(JSON.stringify(result.data));
			if (result.status == '200 OK') {
				$.each(data, function (index, data) {
					var rowIndex = $('#dataTables-example').dataTable().fnAddData([
						data.product_name,
						data.product_desc,
						editButtonHtml + "  " + deleteButtonHtml]
					);
					var row = $('#dataTables-example').dataTable().fnGetNodes(rowIndex);
					$(row).attr('id', data.product_id);
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

$(document).on("click", "#btnAdd", function (event) {
	event.preventDefault();
	if (($('#prod_name').val() != '') && ($('#prod_desc').val() != '')){

		var Product = {
			"product_name": $('#prod_name').val(),
			"product_desc": $('#prod_desc').val()		};
		//console.log(Product);
		saveProductApiCall(Product,0);
	}
	else {
		if ($('#prod_name').val() == '') {
			setError('prod_name');
		}
		else {
			setClear('prod_name');
		}
		if ($('#prod_desc').val() == '') {
			setError('prod_desc');
		}
		else {
			setClear('prod_desc');
		}
//		if ($('#prod_unit').val() == null) {
//			setError('prod_unit');
//		}
//		else {
//			setClear('prod_unit');
//		}
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


function saveProductApiCall(Product,opFlag) {
	//var token = sessionStorage.getItem("Authorization");
	var req = JSON.stringify(Product);
	if(opFlag == 0)
		{
		var api = addproductApi;
	}else
		{
		var api = editproductApi;

		}
	$.ajax({
		type: "POST",
		headers: {
			// 'Authorization': token,
			'content-type': 'application/json'
		},
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
				$('.close').click();
				loadProduct();
				
				
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
				$('.close').click();

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
			$('.btn-default').click();
			$('.close').click();
		}
	});

}

$(document).on("click", "#btnEdit", function () {
	var pid = $(this).closest('tr').attr('id');
	var row = $(this).closest('tr');
	$('#pid').val(pid);
	$('#prod_name_edit').val(row.find("td:eq(0)").text());
	$('#prod_desc_edit').val(row.find("td:eq(1)").text());
	//$('#prod_unit_edit').val(row.find("td:eq(2)").text()=='Kg'?1:2);
});


$(document).on("click", "#btnUpd", function (event) {
	event.preventDefault();
	if (($('#prod_name_edit').val() != '') && ($('#prod_desc_edit').val() != '')){

		var Product = {
			"product_id": parseInt($('#pid').val()),
			"product_name": $('#prod_name_edit').val(),
			"product_desc": $('#prod_desc_edit').val()
			//"unit_metrics": $('#prod_unit_edit').val()
		};
		saveProductApiCall(Product,1);
	}
	else {
		if ($('#prod_name_edit').val() == '') {
			setError('prod_name_edit');
		}
		else {
			setClear('prod_name_edit');
		}
		if ($('#prod_desc_edit').val() == '') {
			setError('prod_desc_edit');
		}
		else {
			setClear('prod_desc_edit');
		}
		
	}

});

$(document).on("click", "#btnDelete", function () {
	var pid = $(this).closest('tr').attr('id');
	deleteProductApiCall(pid);
});

function deleteProductApiCall(pid) {
	var req = { 'product_id': pid };
	req = JSON.stringify(req);

	$.ajax({
		type: "POST",
		headers: {
			// 'Authorization': token,
			'content-type': 'application/json'
		},
		url: baseUrl + deleteproductApi,
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
				loadProduct();
				
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



