$(document).ready(function () {

	loadCategory();
});

function clearCategory()
{
$('#catid').val('');
$('#category_name').val('');
}

function loadCategory()
{
	$('#dataTables-example1').DataTable().clear();
	var editButtonHtml = "<a class=\"btn btn-primary btn-sm\" id=\"btnEditCat\" data-toggle=\"modal\" data-target=\"#myModal1\"  ><i class=\"fa fa-edit \"></i> Edit</a>";
	var deleteButtonHtml = "<a class=\"btn btn-danger btn-sm\" id=\"btnDeleteCat\" ><i class=\"fa fa-trash-o \"></i> Delete</a>";
	$.ajax({
		type: "GET",
		url: baseUrl + getAllCategories,
		data: { data: "" },
		cache: false,
		success: function (result) {
			data = JSON.parse(JSON.stringify(result.data));
			console.log(result);
			if (result.status == 'OK') {
				$.each(result.data, function (index, data) {
					var rowIndex = $('#dataTables-example1').dataTable().fnAddData([
						data.category_name,
						editButtonHtml + "  " + deleteButtonHtml]
					);
					var row = $('#dataTables-example1').dataTable().fnGetNodes(rowIndex);
					$(row).attr('id', data.id);
				});
				
				
			}
			else {
				$.toast({
				    text: "Unable to Load Category Table",
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

$(document).on("click", "#btnAddCat", function (event) {
	event.preventDefault();
	if (($('#category_name').val() != '')){
		if($('#catid').val !='')
		{
			var category = {
					"id":$('#catid').val(),
					"category_name": $('#category_name').val()};
			saveCategory(category);
			
		}
			
	}
	else {
		if ($('#category_name').val() == '') {
			$('#category_name').addClass("field-red");
			$('#category_name').removeClass("field-ok");
		}
		else {
			$('#category_name').addClass("field-ok");
			$('#category_name').removeClass("field-red");
		}
		
	}

});



function saveCategory(category) {
	$.ajax({
		type: "POST",
		headers: {
			'content-type': 'application/json'
		},
		url: baseUrl + addCategory,
		dataType: "json",
		data:  JSON.stringify(category),
		processData: false,
		contentType: false,
		crossDomain: true,
		async: true,
		cache: false,
		timeout: 60000,
		success: function (data) {

			console.log("SUCCESS : ", data);
			if (data.status == 'OK') {
				$.toast({
				    text: "Category Operation Success",
				    hideAfter: 2000,
				    icon: 'success',
				    loader: false,
				    showHideTransition: 'fade',
				    stack: false,
				    position: 'top-left'
  	
				});
				$('.btn-default').click();
				$('.close').click();
				loadCategory();
				
				
			}
			else {
				$.toast({
				    text: "Unable to do Category Operation",
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

$(document).on("click", "#btnEditCat", function () {
	var pid = $(this).closest('tr').attr('id');
	var row = $(this).closest('tr');
	$('#catid').val(pid);
	$('#category_name').val(row.find("td:eq(0)").text());
});


$(document).on("click", "#btnDeleteCat", function () {
	var pid = $(this).closest('tr').attr('id');
	deleteCategorybyId(pid);
});

function deleteCategorybyId(pid) {
	$.ajax({
		type: "DELETE",
		headers: {
			'content-type': 'application/json'
		},
		url: baseUrl + deleteCategory+"/"+pid,
		crossDomain: true,
		async: true,
		timeout: 60000,
		success: function (data) {
			console.log("SUCCESS : ", data);
			if (data.status == 'OK') {
				$.toast({
				    text: "Category Deleted Successfully",
				    hideAfter: 2000,
				    icon: 'success',
				    loader: false,
				    showHideTransition: 'fade',
				    stack: false,
				    position: 'top-left'
  	
				});
				loadCategory();
				
			}
			else {
				$.toast({
				    text: "Unable to Delete Category",
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



