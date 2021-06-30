

function loadProducts()
{
	$('#dataTables-example5').DataTable().clear();
	var editButtonHtml = "<a class=\"btn btn-primary btn-sm\" id=\"btnEditProd\" href=\"editProduct.html\" ><i class=\"fa fa-edit \"></i> Edit</a>";
	var deleteButtonHtml = "<a class=\"btn btn-danger btn-sm\" id=\"btnDeleteProd\" ><i class=\"fa fa-trash-o \"></i> Delete</a>";
	$.ajax({
		type: "GET",
		url: baseUrl + listproductApi,
		data: { data: "" },
		cache: false,
		success: function (result) {
			data = JSON.parse(JSON.stringify(result.data));
			console.log(result);
			if (result.status == 'OK') {
				$.each(result.data, function (index, data) {
					var rowIndex = $('#dataTables-example5').dataTable().fnAddData([
						data.product_name,
						data.price,
						data.selling_price,
						data.units,
						editButtonHtml + "  " + deleteButtonHtml]
					);
					var row = $('#dataTables-example5').dataTable().fnGetNodes(rowIndex);
					$(row).attr('id', data.id);
				});
			
				
				
			}
			else {
				$.toast({
				    text: "Unable to Load Product Table",
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


$(document).on("click", "#btnEditProd", function () {
	var pid = $(this).closest('tr').attr('id');
	
});


$(document).on("click", "#btnDeleteProd", function () {
	var pid = $(this).closest('tr').attr('id');
	deleteProductbyId(pid);
});

function deleteProductbyId(pid) {
	$.ajax({
		type: "DELETE",
		headers: {
			'content-type': 'application/json'
		},
		url: baseUrl + deleteproductApi+"/"+pid,
		crossDomain: true,
		async: true,
		timeout: 60000,
		success: function (data) {
			console.log("SUCCESS : ", data);
			if (data.status == 'OK') {
				$.toast({
				    text: "Product Deleted Successfully",
				    hideAfter: 2000,
				    icon: 'success',
				    loader: false,
				    showHideTransition: 'fade',
				    stack: false,
				    position: 'top-left'
  	
				});
				loadProducts();
				
			}
			else {
				$.toast({
				    text: "Unable to Delete Product",
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



