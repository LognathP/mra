var categorydata = JSON.parse(localStorage.getItem("categoryArray"));
$(document).ready(function () {

	console.log(categorydata);
	$.each(categorydata, function (index, data) {
		$('#product_catlist').append("<option value="+data.id+">"+data.text+"</option>");
	});
});

function clearSubCategory()
{
$('#subcatid').val('');
$('#subcategory_name').val('');
}

function loadSubCategory()
{
	$('#dataTables-example6').DataTable().clear();
	var editButtonHtml = "<a class=\"btn btn-primary btn-sm\" id=\"btnEditSubCat\" data-toggle=\"modal\" data-target=\"#myModal5\"  ><i class=\"fa fa-edit \"></i> Edit</a>";
	var deleteButtonHtml = "<a class=\"btn btn-danger btn-sm\" id=\"btnDeleteSubCat\" ><i class=\"fa fa-trash-o \"></i> Delete</a>";
	$.ajax({
		type: "GET",
		url: baseUrl + getAllSubCategories,
		data: { data: "" },
		cache: false,
		success: function (subresult) {
			subdata = JSON.parse(JSON.stringify(subresult.data));
			console.log(subresult);
			if (subresult.status == 'OK') {
				$.each(subresult.data, function (index, data) {
					var rowIndex = $('#dataTables-example6').dataTable().fnAddData([
						data.sub_category_name,
						editButtonHtml + "  " + deleteButtonHtml]
					);
					var row = $('#dataTables-example6').dataTable().fnGetNodes(rowIndex);
					$(row).attr('id', data.id);
				});
				
				
			}
			else {
				$.toast({
				    text: "Unable to Load Sub Category Table",
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

$(document).on("click", "#btnAddSubCat", function (event) {
	event.preventDefault();
	if (($('#subcategory_name').val() != '')){
		if($('#product_catlist').val != 0)
		{
			var subcategory = {
					"id":$('#product_catlist').val(),
					"sub_category_name": $('#subcategory_name').val()};
			console.log(subcategory);
			saveSubCategory(subcategory);
			
		}
			
	}
	else {
		if ($('#subcategory_name').val() == '') {
			$('#subcategory_name').addClass("field-red");
			$('#subcategory_name').removeClass("field-ok");
		}
		else {
			$('#subcategory_name').addClass("field-ok");
			$('#subcategory_name').removeClass("field-red");
		}
		if ($('#product_catlist').val() == 0) {
			$('#product_catlist').addClass("field-red");
			$('#product_catlist').removeClass("field-ok");
		}
		else {
			$('#product_catlist').addClass("field-ok");
			$('#product_catlist').removeClass("field-red");
		}
		
	}

});



function saveSubCategory(subcategory) {
	$.ajax({
		type: "POST",
		headers: {
			'content-type': 'application/json'
		},
		url: baseUrl + addSubCategory,
		dataType: "json",
		data:  JSON.stringify(subcategory),
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
				    text: "Sub Category Operation Success",
				    hideAfter: 2000,
				    icon: 'success',
				    loader: false,
				    showHideTransition: 'fade',
				    stack: false,
				    position: 'top-left'
  	
				});
				$('.btn-default').click();
				$('.close').click();
				loadSubCategory();
				
				
			}
			else {
				$.toast({
				    text: "Unable to do Sub Category Operation",
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

$(document).on("click", "#btnEditSubCat", function () {
	var pid = $(this).closest('tr').attr('id');
	var row = $(this).closest('tr');
	$('#subcatid').val(pid);
	$('#product_catlist').val(pid);
	$('#subcategory_name').val(row.find("td:eq(0)").text());
});


$(document).on("click", "#btnDeleteSubCat", function () {
	var pid = $(this).closest('tr').attr('id');
	deleteSubCategorybyId(pid);
});

function deleteSubCategorybyId(pid) {
	$.ajax({
		type: "DELETE",
		headers: {
			'content-type': 'application/json'
		},
		url: baseUrl + deleteSubCategory+"/"+pid,
		crossDomain: true,
		async: true,
		timeout: 60000,
		success: function (data) {
			console.log("SUCCESS : ", data);
			if (data.status == 'OK') {
				$.toast({
				    text: "Sub Category Deleted Successfully",
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
				    text: "Unable to Delete Sub Category",
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



